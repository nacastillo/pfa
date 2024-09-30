package pfa.controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
import pfa.modelo.Detenido;
import pfa.util.HibernateUtil;

public class DetenidoDAO {
    
    public static Route getAll = (req, res) -> {
        Gson g = new Gson();
        JsonArray ja = new JsonArray();
        List <Detenido> x = null;
        Session s = null;        
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            var q = s.createQuery("from Detenido", Detenido.class);
            //Query q = s.createQuery("from Detenido", Detenido.class);
            x = q.getResultList();            
            if (!x.isEmpty()) {
                for (Detenido det : x) {
                    JsonObject jo = g.toJsonTree(det).getAsJsonObject();
                    if (det.getBanda() != null) {
                        Banda b = s.get(Banda.class, det.getBanda());
                        jo.addProperty("nombreBanda", b.getNombre());
                    }
                    ja.add(jo);
                }
                res.type("application/json");
                //res.body(g.toJson(x));
                res.body(g.toJson(ja));
                s.close();
                return res.body();
            }
            else {
                res.status(404);
                s.close();
                return "No hay detenidos registrados.";
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
        Detenido x = g.fromJson(req.body(), Detenido.class);
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
        Detenido x = null;
        Session s = null;
        try {
            long id = Long.parseLong(req.params(":id"));
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            x = s.get(Detenido.class, id);
            s.getTransaction().commit();            
            if (x != null) {
                res.type("application/json");
                res.body(g.toJson(x));
                s.close();
                return res.body();
            } 
            else {
                res.status(404);
                return "No se encontró detenido.";
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
        Detenido front = g.fromJson(req.body(), Detenido.class);
        Detenido db = null;
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            db = s.get(Detenido.class, id);
            if (db != null) { 
                /* codigo, nombre, banda, asaltos */
                if (front.getCodigo() == null) {
                    front.setCodigo(db.getCodigo());
                }
                if (front.getNombre() == null || front.getNombre().equals("")) {
                    front.setNombre(db.getNombre());
                }            
                if (front.getBanda() == null) {
                    front.setBanda(db.getBanda());
                }                
                else 
                    if (front.getBanda() == -1) {
                        front.setBanda(null);
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
                return "No se encontró detenido.";
            }
        }
        catch (Exception e) {
            res.status(500);
            return e.toString();
        }
        finally {
            if (s != null) {
                s.close();
            }
        }
    };
    
    public static Route borrar = (req, res) -> {
        Gson g = new Gson();
        long id = Long.parseLong(req.params(":id"));
        Detenido x = null;
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            x = s.get(Detenido.class, id);
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
                return "No se encontró detenido.";
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
