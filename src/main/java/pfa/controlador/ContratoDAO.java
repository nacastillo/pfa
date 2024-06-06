package pfa.controlador;

import com.google.gson.Gson;
import spark.Route;
import spark.RouteGroup;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import pfa.modelo.Contrato;
import pfa.util.HibernateUtil;

public class ContratoDAO {

    public static Route getAll = (req, res) -> {
        Gson g = new Gson();
        List <Contrato> x = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            Query q = s.createQuery("from Contrato", Contrato.class);
            x = q.getResultList();
            s.close();
            if (!x.isEmpty()) {
                res.type("application/json");
                return g.toJson(x);
            }
            else {
                res.status(404);
                return "No hay contratos registrados.";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Excepción.";
        }
    };
    
    public static Route crear = (req, res) -> {
        Gson g = new Gson();
        Contrato x = g.fromJson(req.body(), Contrato.class);
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
        Contrato x = null;
        try {
            long id = Long.parseLong(req.params(":id"));
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            x = s.get(Contrato.class, id);
            s.getTransaction().commit();
            s.close();
            if (x != null) {
                res.type("application/json");
                return g.toJson(x);
            } 
            else {
                res.status(404);
                return "No se encontró contrato.";
            }
        }         
        catch (Exception e) {
            res.status(500);
            e.printStackTrace();
            return "Excepción.";
        }
    };
    
    public static Route actualizar = (req, res) -> {
        return "ruta de actualizar Contrato";
    };
    
    public static Route borrar = (req, res) -> {
        Gson g = new Gson();
        long id = Long.parseLong(req.params(":id"));
        Contrato x = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            x = s.get(Contrato.class, id);
            if (x != null) {            
                s.remove(x);
                s.getTransaction().commit();
                s.close();
                res.type("application/json");
                return "Elemento borrado: " + g.toJson(x);
            } 
            else {
                res.status(404);
                return "No se encontró contrato.";
            }
        }
        catch (Exception e) {
            res.status(500);
            e.printStackTrace();
            return "Excepción.";
        }
    };   
    
    public static RouteGroup contratosRouter = () -> {
        get("", getAll);
        get("/", getAll);
        post("", crear);
        post("/", crear);
        get("/:id", leer);
        put("/:id", actualizar);
        delete("/:id", borrar);        
    }; 
}