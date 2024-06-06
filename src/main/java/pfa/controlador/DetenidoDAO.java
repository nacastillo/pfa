package pfa.controlador;

import com.google.gson.Gson;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import spark.Route;
import spark.RouteGroup;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;
import pfa.modelo.Detenido;
import pfa.util.HibernateUtil;

public class DetenidoDAO {
    
    public static Route getAll = (req, res) -> {
        Gson g = new Gson();
        List <Detenido> resp = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            Query q = s.createQuery("from Detenido", Detenido.class);
            resp = q.getResultList();
            s.close();
            if (!resp.isEmpty()) {
                res.type("application/json");
                return g.toJson(resp);
            }
            else {
                res.status(404);
                return "No hay detenidos registrados.";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Excepción.";
        }
    };
    
    public static Route crear = (req, res) -> {
        Gson g = new Gson();
        Detenido x = g.fromJson(req.body(), Detenido.class);
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            s.persist(x);
            s.getTransaction().commit();
            s.close();
            res.type("application/json");
            return g.toJson(x);
        } 
        catch (Exception e) {
            res.status(500);
            e.printStackTrace();
            return "Excepción.";
        }
    };
    
    public static Route leer = (req, res) -> {
        Gson g = new Gson();
        Detenido x = null;
        try {
            long id = Long.parseLong(req.params(":id"));
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            x = s.get(Detenido.class, id);
            s.getTransaction().commit();
            s.close();
            if (x != null) {
                res.type("application/json");
                return g.toJson(x);
            } 
            else {
                res.status(404);
                return "No se encontró detenido.";
            }
        }         
        catch (Exception e) {
            res.status(500);
            e.printStackTrace();
            return "Excepción.";
        }        
    };
    
    public static Route actualizar = (req, res) -> {
        Gson g = new Gson();
        long id = Long.parseLong(req.params(":id"));
        Detenido x = g.fromJson(req.body(), Detenido.class);
        Detenido y = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            y = s.get(Detenido.class, id);
            if (y != null) { /* A actualizar: codigo, nombreCompleto, banda, fechaCondena */
                if (x.getCodigo() != null) {
                    y.setCodigo(x.getCodigo());
                }
                if (x.getNombre() != null) {
                    y.setNombre(x.getNombre());
                }            
                if (x.getBanda() != null) {
                    y.setBanda(x.getBanda());
                }
                if (x.getFechaCondena() != null) {
                    y.setFechaCondena(x.getFechaCondena());
                }
                s.merge(y);
                s.getTransaction().commit();
                s.close();
                res.type("application/json");
                return g.toJson(y);    
            }            
            else {
                res.status(404);
                return "No se encontró detenido.";
            }
        }
        catch (Exception e) {
            res.status(500);
            e.printStackTrace();
            return "Excepción.";
        }
    };
    
    public static Route borrar = (req, res) -> {
        Gson g = new Gson();
        long id = Long.parseLong(req.params(":id"));
        Detenido x = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            x = s.get(Detenido.class, id);
            if (x != null) {            
                s.remove(x);
                s.getTransaction().commit();
                s.close();
                res.type("application/json");
                return g.toJson(x);
            } 
            else {
                res.status(404);
                return "No se encontró detenido.";
            }
        }
        catch (Exception e) {
            res.status(500);
            e.printStackTrace();
            return "Excepción.";
        }
    };   
    
    public static RouteGroup detenidosRouter = () -> {
        get("", getAll);
        get("/", getAll);
        post("", crear);
        post("/", crear);
        get("/:id", leer);
        put("/:id", actualizar);
        delete("/:id", borrar);
    };   
}
