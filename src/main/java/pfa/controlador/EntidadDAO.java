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
import pfa.modelo.Entidad;
import pfa.modelo.Sucursal;
import pfa.util.HibernateUtil;

public class EntidadDAO {

    public static Route getAll = (req, res) -> {
        Gson g = new Gson();
        List <Entidad> resp = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            Query q = s.createQuery("from Entidad", Entidad.class);
            resp = q.getResultList();
            s.close();
            if (!resp.isEmpty()) {
                res.type("application/json");
                return g.toJson(resp);
            }
            else {
                res.status(404);
                return "No hay entidades registradas.";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Excepción.";
        }
    };
    
    public static Route crear = (req, res) -> {
        Gson g = new Gson();
        Entidad x = g.fromJson(req.body(), Entidad.class);
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
            e.printStackTrace();
            return "Excepción.";
        }        
    };
    
    public static Route leer = (req, res) -> {
        Gson g = new Gson();
        Entidad x = null;
        try {
            long id = Long.parseLong(req.params(":id"));
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            x = s.get(Entidad.class, id);
            s.getTransaction().commit();
            s.close();
            if (x != null) {
                res.type("application/json");
                return g.toJson(x);
            } 
            else {
                res.status(404);
                return "No se encontró entidad.";
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
        Entidad x = g.fromJson(req.body(), Entidad.class);
        Entidad y = null; 
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            y = s.get(Entidad.class, id);
            if (y != null) { /* A actualizar: codigo, domicilio, nombre */
                if (x.getCodigo() != null) {
                    y.setCodigo(x.getCodigo());
                }
                if (x.getDomicilio() != null) {
                    y.setDomicilio(x.getDomicilio());
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
                return "No se encontró entidad.";
            }
        }
        catch (Exception e) {
            res.status(500);
            e.printStackTrace();
            return "Excepción.";
        }
    };

    public static Route cambiarSucursales = (req, res) -> {
        //delegar cambios de sucursales en la ruta de sucursal
        return "";
    };
    
    public static Route borrar = (req, res) -> {
        Gson g = new Gson();
        long id = Long.parseLong(req.params(":id"));
        Entidad x = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            x = s.get(Entidad.class, id);
            if (x != null) {            
                s.remove(x);
                s.getTransaction().commit();
                s.close();
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
