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
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Query q = s.createQuery("from Banda");
        List<Banda> resp = q.getResultList();
        s.close();
        res.type("application/json");
        return g.toJson(resp);
    };

    public static Route crear = (req, res) -> {
        Gson g = new Gson();
        Banda b = g.fromJson(req.body(), Banda.class);
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            s.save(b);
            s.getTransaction().commit();
            s.close();
            res.type("application/json");
            return g.toJson(b);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    };

    public static Route leer = (req, res) -> {
        Gson g = new Gson();
        Banda b = null;
        try {
            long id = Long.parseLong(req.params(":id"));
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            b = s.get(Banda.class, id);
            s.getTransaction().commit();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (b != null) {
            res.type("application/json");
            return g.toJson(b);
        } else {
            res.status(404);
            return "No se encontró banda.";
        }
    };

    public static Route actualizar = (req, res) -> {
        Gson g = new Gson();
        long id = Long.parseLong(req.params(":id"));
        Banda bU = g.fromJson(req.body(), Banda.class);
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Banda b = s.get(Banda.class, id);
        if (b != null) {
            if (bU.getNumero() != null) {
                b.setNumero(bU.getNumero());
            }
            if (bU.getNombre() != null) {
                b.setNombre(bU.getNombre());
            }
            if (bU.getCantidadMiembros() != null) {
                b.setCantidadMiembros(bU.getCantidadMiembros());
            }
            s.update(b);
            s.getTransaction().commit();
            s.close();
            res.type("application/json");
            return g.toJson(b);
        } else {
            res.status(404);
            return "No se encontró banda.";
        }
    };

    public static Route borrar = (req, res) -> {
        res.type("application/json");
        Gson g = new Gson();
        long id = Long.parseLong(req.params(":id"));
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Banda b = s.get(Banda.class, id);
        if (b != null) {
            s.delete(b);
            s.getTransaction().commit();
            s.close();
            return g.toJson(b);
        } else {
            res.status(404);
            return "No se encontró banda.";
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
