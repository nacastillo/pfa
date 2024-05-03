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
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Query q = s.createQuery("from Juez");
        List<Juez> resp = q.getResultList();
        s.close();
        res.type("application/json");
        return g.toJson(resp);
    };

    public static Route crear = (req, res) -> {
        Gson g = new Gson();
        Juez j = g.fromJson(req.body(), Juez.class);
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            s.save(j);
            s.getTransaction().commit();
            s.close();
            res.type("application/json");
            return g.toJson(j);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
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
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        if (j != null) {
            res.type("application/json");
            return g.toJson(j);
        } else {
            res.status(404);
            return "Banda no encontrada";
        }
    };

    public static Route actualizar = (req, res) -> {
        Gson g = new Gson();
        long id = Long.parseLong(req.params(":id"));
        Juez ju = g.fromJson(req.body(), Juez.class);
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Juez j = s.get(Juez.class, id);
        if (j != null) {
            if (ju.getClaveInterna() != null) {
                j.setClaveInterna(ju.getClaveInterna());
            }
            if (ju.getNombreCompleto() != null) {
                j.setNombreCompleto(ju.getNombreCompleto());
            }
            if (ju.getAniosServicio() != null) {
                j.setAniosServicio(ju.getAniosServicio());
            }
            s.update(j);
            s.getTransaction().commit();
            s.close();
            res.type("application/json");
            return g.toJson(j);
        } else {
            res.status(404);
            return "No se encontró juez.";
        }
    };

    public static Route borrar = (req, res) -> {
        res.type("application/json");
        Gson g = new Gson();
        long id = Long.parseLong(req.params(":id"));
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Juez j = s.get(Juez.class, id);
        if (j != null) {
            s.delete(j);
            s.getTransaction().commit();
            s.close();
            return g.toJson(j);
        } else {
            res.status(404);
            return "No se encontró juez.";
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
