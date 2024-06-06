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
import pfa.modelo.Juez;
import pfa.util.HibernateUtil;

public class JuezDAO {

    public static Route getAll = (req, res) -> {
        Gson g = new Gson();
        List <Juez> resp = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            Query q = s.createQuery("from Juez", Juez.class);
            resp = q.getResultList();
            s.close();
            if (!resp.isEmpty()) {
                res.type("application/json");
                return g.toJson(resp);
            }
            else {
                res.status(404);
                return "No hay jueces registrados.";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Excepción.";
        }    
    };

    public static Route crear = (req, res) -> {
        Gson g = new Gson();
        Juez j = g.fromJson(req.body(), Juez.class);
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            s.persist(j);
            s.getTransaction().commit();
            s.close();
            res.type("application/json");
            return g.toJson(j);
        } 
        catch (Exception e) {
            e.printStackTrace();
            return "Excepción.";
        }
    };

    public static Route leer = (req, res) -> {
        Gson g = new Gson();
        Juez j = null;
        try {
            long id = Long.parseLong(req.params(":id"));
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            j = s.get(Juez.class, id);
            s.getTransaction().commit();
            s.close();
            if (j != null) {
                res.type("application/json");
                return g.toJson(j);
            } 
            else {
                res.status(404);
                return "Banda no encontrada";
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
        Juez ju = g.fromJson(req.body(), Juez.class);
        Juez j = null;
        try {        
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            j = s.get(Juez.class, id);
            if (j != null) { /* Campos a actualizar: clave, nombre, anios */
                if (ju.getClave() != null) {
                    j.setClave(ju.getClave());
                }
                if (ju.getNombre() != null) {
                    j.setNombre(ju.getNombre());
                }
                if (ju.getAniosServicio() != null) {
                    j.setAniosServicio(ju.getAniosServicio());
                }
                s.merge(j);
                s.getTransaction().commit();
                s.close();
                res.type("application/json");
                return g.toJson(j);
            }    
            else {
                res.status(404);
                return "No se encontró juez.";
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
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            Juez j = s.get(Juez.class, id);
            if (j != null) {
                s.remove(j);
                s.getTransaction().commit();
                s.close();
                res.type("application/json");
                return g.toJson(j);
            } 
            else {
                res.status(404);
                return "No se encontró juez.";
            }
        }
        catch (Exception e) {
            res.status(500);
            e.printStackTrace();
            return "Excepción.";
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
