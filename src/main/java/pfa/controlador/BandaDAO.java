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
import pfa.modelo.Banda;
import pfa.util.HibernateUtil;

public class BandaDAO {

    public static Route getAll = (req, res) -> {
        Gson g = new Gson();
        List <Banda> x = null;
        Session s = null;        
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();            
            var q = s.createQuery("from Banda", Banda.class);
            //Query q = s.createQuery("from Banda", Banda.class);
            x = q.getResultList();            
            if (!x.isEmpty()) {
                res.type("application/json");
                res.body(g.toJson(x));
                s.close();
                return res.body();
            }
            else {
                res.status(404);
                return "No hay bandas registradas.";
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
        Banda x = g.fromJson(req.body(), Banda.class);
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            s.persist(x);
            s.getTransaction().commit();            
            res.type("application/json");
            res.body(g.toJson(x));
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
        Banda x = null;
        Session s = null;
        try {
            long id = Long.parseLong(req.params(":id"));
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            x = s.get(Banda.class, id);
            s.getTransaction().commit();            
            if (x != null) {
                res.type("application/json");
                res.body(g.toJson(x));
                s.close();
                return res.body();
            } 
            else {
                res.status(404);
                return "No se encontró banda.";
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
        Banda front = g.fromJson(req.body(), Banda.class);
        Banda db = null;
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            db = s.get(Banda.class, id);
            if (db != null) { /* numero, nombre, miembros */
                if (front.getNumero() == null) {
                    front.setNumero(db.getNumero());
                }
                if (front.getNombre() == null || front.getNombre().equals("")) {
                    front.setNombre(db.getNombre());
                }            
                if (front.getMiembros() == null) {
                    front.setMiembros(db.getMiembros());
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
                return "No se encontró banda.";
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
        Banda x = null;
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            x = s.get(Banda.class, id);
            if (x != null) {            
                res.type("application/json");
                res.body(g.toJson(x));
                s.remove(x);
                s.getTransaction().commit();                
                s.close();
                return res.body();                
            } 
            else {
                res.status(404);
                return "No se encontró banda.";
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

    public static RouteGroup bandasRouter = () -> {
        get("", getAll);
        get("/", getAll);
        post("", crear);
        post("/", crear);
        get("/:id", leer);
        put("/:id", actualizar);
        delete("/:id", borrar);
    };
}
