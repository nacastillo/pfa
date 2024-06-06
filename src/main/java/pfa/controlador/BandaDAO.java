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
import pfa.modelo.Banda;
import pfa.util.HibernateUtil;

public class BandaDAO {

    public static Route getAll = (req, res) -> {
        Gson g = new Gson();
        List <Banda> x = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            Query q = s.createQuery("from Banda", Banda.class);
            x = q.getResultList();
            s.close();
            if (!x.isEmpty()) {
                res.type("application/json");
                return g.toJson(x);
            }
            else {
                res.status(404);
                return "No hay bandas registradas.";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Excepción.";
        }
    };

    public static Route crear = (req, res) -> {
        Gson g = new Gson();
        Banda x = g.fromJson(req.body(), Banda.class);
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
        Banda x = null;
        try {
            long id = Long.parseLong(req.params(":id"));
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            x = s.get(Banda.class, id);
            s.getTransaction().commit();
            s.close();
            if (x != null) {
                res.type("application/json");
                return g.toJson(x);
            } 
            else {
                res.status(404);
                return "No se encontró banda.";
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
        Banda x = g.fromJson(req.body(), Banda.class);
        Banda y = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            y = s.get(Banda.class, id);
            if (y != null) { /* A actualizar: numero y nombre */
                if (x.getNumero() != null) {
                    y.setNumero(x.getNumero());
                }
                if (x.getNombre() != null) {
                    y.setNombre(x.getNombre());
                }            
                s.merge(y);
                s.getTransaction().commit();
                s.close();
                res.type("application/json");
                return g.toJson(y);    
            }            
            else {
                res.status(404);
                return "No se encontró banda.";
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
        Banda x = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            x = s.get(Banda.class, id);
            if (x != null) {            
                s.remove(x);
                s.getTransaction().commit();
                s.close();
                res.type("application/json");
                return "Elemento borrado: " + g.toJson(x);
            } 
            else {
                res.status(404);
                return "No se encontró banda.";
            }
        }
        catch (Exception e) {
            res.status(500);
            e.printStackTrace();
            return "Excepción.";
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
