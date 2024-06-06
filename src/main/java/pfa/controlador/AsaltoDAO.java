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
import pfa.modelo.Asalto;
import pfa.util.HibernateUtil;

public class AsaltoDAO {
    
    public static Route getAll = (req, res) -> {
        Gson g = new Gson();
        List <Asalto> x = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            Query q = s.createQuery("from Asalto", Asalto.class);
            x = q.getResultList();
            s.close();
            if (!x.isEmpty()) {
                res.type("application/json");
                return g.toJson(x);
            }
            else {
                res.status(404);
                return "No hay asaltos registrados.";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Excepción.";
        }        
    };
    
    public static Route crear = (req, res) -> {
        Gson g = new Gson();        
        Asalto x = g.fromJson(req.body(), Asalto.class);
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
        Asalto x = null;
        try {
            long id = Long.parseLong(req.params(":id"));
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            x = s.get(Asalto.class, id);
            s.getTransaction().commit();
            s.close();
            if (x != null) {
                res.type("application/json");
                return g.toJson(x);
            }
            else {
                res.status(404);
                return "No se encontró asalto.";
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
        Asalto x = g.fromJson(req.body(), Asalto.class);
        Asalto y = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            y = s.get(Asalto.class, id);
            if (y != null) { /* A actualizar: codigo, fecha, 
                sucursal, juez */
                if (x.getCodigo() != null) {
                    y.setCodigo(x.getCodigo());
                }
                if (x.getFecha() != null) {
                    y.setFecha(x.getFecha());
                }
                if (x.getSucursal() != null) {
                    y.setSucursal(x.getSucursal());
                }
                if (x.getJuez() != null) {
                    y.setJuez(x.getJuez());
                }
                s.merge(y);
                s.getTransaction().commit();
                s.close();
                res.type("application/json");
                return g.toJson(y);
            }
            else {
                res.status(404);
                return "No se encontró Asalto.";
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
        Asalto x = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            x = s.get(Asalto.class, id);
            if (x != null) {
                s.remove(x);
                s.getTransaction().commit();
                s.close();
                res.type("application/json");
                return "Elemento borrado: " + g.toJson(x);
            }
            else {
                res.status(404);
                return "No se encontró asalto.";
            }
        }
        catch (Exception e) {
            res.status(500);
            e.printStackTrace();
            return "Excepción.";
        }        
    };   
    
    public static RouteGroup asaltosRouter = () -> {            
        get("", getAll);
        get("/", getAll);
        post("", crear);
        post("/", crear);
        get("/:id", leer);
        put("/:id", actualizar);
        delete("/:id", borrar);      
    };    
}
