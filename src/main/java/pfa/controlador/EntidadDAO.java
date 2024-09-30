package pfa.controlador;

import com.google.gson.Gson;
import java.util.List;
import org.hibernate.Session;
//import org.hibernate.query.Query;
import spark.Route;
import spark.RouteGroup;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import pfa.modelo.Entidad;
import pfa.util.HibernateUtil;

public class EntidadDAO {

    public static Route getAll = (req, res) -> {
        Gson g = new Gson();
        List <Entidad> resp = null;
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            var q = s.createQuery("from Entidad", Entidad.class);
            //Query q = s.createQuery("from Entidad", Entidad.class);
            resp = q.getResultList();            
            if (!resp.isEmpty()) {
                res.type("application/json");
                res.body(g.toJson(resp));
                s.close();
                return res.body();
            }
            else {
                res.status(404);
                return "No hay entidades registradas.";
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
        Entidad x = g.fromJson(req.body(), Entidad.class);
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
        Entidad x = null;
        Session s = null;
        try {
            long id = Long.parseLong(req.params(":id"));
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            x = s.get(Entidad.class, id);
            s.getTransaction().commit();            
            if (x != null) {
                res.type("application/json");
                res.body(g.toJson(x));
                s.close();
                return res.body();
            } 
            else {
                res.status(404);
                return "No se encontró entidad.";
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
        Entidad front = g.fromJson(req.body(), Entidad.class);
        Entidad db = null; 
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            db = s.get(Entidad.class, id);
            if (db != null) { // actualizar: codigo, domicilio, nombre, sucursales
                if (front.getCodigo() == null) {
                    front.setCodigo(db.getCodigo());
                }
                if (front.getDomicilio() == null || front.getDomicilio().equals("")) {
                    front.setDomicilio(db.getDomicilio());
                }
                if (front.getNombre() == null || front.getNombre().equals("")) {
                    front.setNombre(db.getNombre());
                }
                if (front.getSucursales() == null) {
                    front.setSucursales(db.getSucursales());
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
                return "No se encontró entidad.";
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
        Entidad x = null;
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            x = s.get(Entidad.class, id);
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
    
    public static RouteGroup entidadesRouter = () -> {
        get("", getAll);
        get("/", getAll);
        post("", crear);
        post("/", crear);
        get("/:id", leer);
        put("/:id", actualizar);
        delete("/:id", borrar);
    }; 
}
