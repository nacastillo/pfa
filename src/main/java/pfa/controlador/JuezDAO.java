package pfa.controlador;

import com.google.gson.Gson;
import java.util.List;
import org.hibernate.Session;
//import org.hibernate.query.Query;
import spark.Route;
import spark.RouteGroup;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;
import pfa.modelo.Juez;
import pfa.util.HibernateUtil;

public class JuezDAO {

    public static Route getAll = (req, res) -> {
        Gson g = new Gson();
        List <Juez> resp = null;        
        Session s = null;        
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            //Query q = s.createQuery("from Juez", Juez.class);
            var q = s.createQuery("from Juez", Juez.class);
            resp = q.getResultList();            
            if (!resp.isEmpty()) {                
                res.type("application/json");  
                res.body(g.toJson(resp));              
                s.close();
                return res.body();
            }
            else {
                res.status(404);
                return "No hay jueces registrados.";
            }
        }
        catch (Exception e) {
            res.status(500);
            return e.toString();
        }
        finally {
            if (s != null)
                s.close();
        }
    };

    public static Route crear = (req, res) -> {
        Gson g = new Gson();
        Juez j = g.fromJson(req.body(), Juez.class);
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            s.persist(j);
            s.getTransaction().commit();                        
            res.type("application/json");
            res.body(g.toJson(j));
            s.close();
            return res.body();
        } 
        catch (Exception e) {
            res.status(500);
            return e.toString();            
        }
        finally {
            if (s != null)
                s.close();            
        }
    };

    public static Route leer = (req, res) -> {
        Gson g = new Gson();
        Juez j = null;
        Session s = null;
        try {
            long id = Long.parseLong(req.params(":id"));
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            j = s.get(Juez.class, id);
            s.getTransaction().commit();                        
            if (j != null) {
                res.type("application/json");
                res.body(g.toJson(j));
                s.close();
                return res.body();
            } 
            else {
                res.status(404);
                return "Juez no encontrado";
            }
        }         
        catch (Exception e) {
            res.status(500);
            return e.toString();
        }      
        finally {
            if (s != null) 
                s.close();
        }  
    };

    public static Route actualizar = (req, res) -> {
        Gson g = new Gson();
        long id = Long.parseLong(req.params(":id"));
        Juez front = g.fromJson(req.body(), Juez.class);
        Juez db = null;
        Session s = null;
        try {        
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            db = s.get(Juez.class, id);
            if (db != null) {
                /* clave, nombre, aniosServicio, asaltos */
                if (front.getClave() == null || front.getClave().equals("")) {
                    front.setClave(db.getClave());
                }
                if (front.getNombre() == null || front.getNombre().equals("")) {
                    front.setNombre(db.getNombre());
                }
                if (front.getAniosServicio() == null) {
                    front.setAniosServicio(db.getAniosServicio());
                }
                if (front.getAsaltos() == null) {
                    front.setAsaltos(db.getAsaltos());
                }
                s.merge(front);
                s.getTransaction().commit();                   
                res.type("application/json");
                res.body(g.toJson(front));
                s.close();
                return res.body();
            }    
            else {
                res.status(404);
                return "No se encontró juez con id " + id + ".";
            }
        }
        catch (Exception e) {
            res.status(500);
            return e.toString();
        }
        finally {
            if (s != null) 
                s.close();            
        }
    };

    public static Route borrar = (req, res) -> {        
        Gson g = new Gson();
        long id = Long.parseLong(req.params(":id"));
        Juez j = null;
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            j = s.get(Juez.class, id);
            if (j != null) {
                res.type("application/json");
                res.body(g.toJson(j));
                s.remove(j);
                s.getTransaction().commit();
                s.close();         
                return res.body();
            } 
            else {
                res.status(404);
                return "No se encontró juez.";
            }
        }
        catch (Exception e) {
            res.status(500);
            return e.toString();
        }
        finally {
            if (s != null)
                s.close();            
        }
    };

    public static RouteGroup juecesRouter = () -> {
        get("", getAll);
        get("/", getAll);
        post("", crear);
        post("/", crear);
        get("/:id", leer);
        put("/:id", actualizar);
        delete("/:id", borrar);
    };
}
