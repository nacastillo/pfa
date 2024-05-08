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
import pfa.modelo.Sucursal;
import pfa.util.HibernateUtil;

public class SucursalDAO {

    public static Route getAll = (req, res) -> {
        Gson g = new Gson();
        List <Sucursal> resp = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("from Sucursal");
            resp = query.getResultList();
            session.close();
            res.type("application/json");
            return g.toJson(resp);          
        }
        catch (Exception e){
            e.printStackTrace();
            return "";
        }        
    };
    
    public static Route crear = (req, res) -> {
        Gson g = new Gson ();
        Sucursal s = g.fromJson (req.body(), Sucursal.class);
        try {
            Session t = HibernateUtil.getSessionFactory().openSession();
            t.beginTransaction();
            t.save(s);
            t.getTransaction().commit();
            t.close();
            res.type("application/json");
            return g.toJson(s);
        }
        catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    };
    
    public static Route leer = (req, res) -> {
        return "ruta de leer Sucursal";
    };
    
    public static Route actualizar = (req, res) -> {
        return "ruta de actualizar Sucursal";
    };
    
    public static Route borrar = (req, res) -> {
        return "ruta de borrar Sucursal";
    };   
    
    public static RouteGroup sucursalesRouter = () -> {
        get("", getAll);
        get("/", getAll);
        post("", crear);
        post("/", crear);
        get("/:id", leer);
        put("/:id", actualizar);
        delete("/:id", borrar);
        
    }; 
}