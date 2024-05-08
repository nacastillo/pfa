package pfa.controlador;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import spark.Route;
import spark.RouteGroup;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;
import pfa.util.HibernateUtil;
import pfa.modelo.Usuario;
import pfa.modelo.Vigilante;
import pfa.modelo.Investigador;
import pfa.modelo.Administrador;

public class UsuarioDAO {    
    
    public static Route getAll = (req, res) -> {        
        Gson gson = new Gson();
        List <Usuario> resp = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();        
            Query query = s.createQuery("from Usuario");
            resp = query.getResultList();
            s.close();    
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Error accediendo a la DB";
        }
        if (resp == null) {
            return "resp es null";
        }
        if (!resp.isEmpty()) {
            res.type("application/json");
            return gson.toJson(resp);
        }    
        else {
            res.status(404);
            return "No hay usuarios registrados.";
        }
    };
    
    public static Route crear = (req, res) -> {
        Gson g = new Gson();
        Integer codigo = null, edad = null;
        Usuario u = g.fromJson(req.body(), Usuario.class);
        Usuario v = null;
        JsonElement je = JsonParser.parseString(req.body());
        JsonObject jo = je.getAsJsonObject();
        if (jo.has("codigo")) {
            codigo = jo.get("codigo").getAsInt();
        }
        if (jo.has("edad")) {
            edad = jo.get("edad").getAsInt();
        }        
        try {             
            Session s = HibernateUtil.getSessionFactory().openSession();
            String rol = u.getRol();
            if (rol.equals("Administrador")) {
                v = new Administrador (u.getUsr(), u.getPwd());
            }
            else if (rol.equals("Investigador")) {
                v = new Investigador (u.getUsr(), u.getPwd());
            }            
            else {
                v = new Vigilante(u.getUsr(), u.getPwd(),
                        codigo, edad);            
            }  
            s.beginTransaction();
            s.save(v);
            s.getTransaction().commit();
            s.close();
            res.type("application/json");
            return g.toJson(v);
        }        
        catch (Exception e) {
            e.printStackTrace();
            return "error";
        }        
    };
    
    public static Route leer = (req, res) -> {
        Gson g = new Gson();
        Usuario u = null;
        try {
            long id = Long.parseLong(req.params(":id"));
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            u = s.get(Usuario.class, id);
            s.getTransaction().commit();
            s.close();
        }
        catch (Exception e) {
            res.status(500);
            e.printStackTrace();
            return "error";
        }        
        if (u != null) {
            res.type("application/json");
            return g.toJson(u);
        }
        else {
            res.status(404);
            return "No se encontró usuario.";
        }
    };
    
    public static Route actualizar = (req, res) -> {
        return "ruta de actualizar Usuario";
    };
    
    public static Route borrar = (req, res) -> {
        Gson g = new Gson ();
        long id = Long.parseLong(":id");
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            Usuario u = s.get(Usuario.class, id);
            if (u != null) {
                s.delete(u);
                s.getTransaction().commit();
                s.close();
                res.type("application/json");
                return g.toJson(u);
            }
            else {
                res.status(404);
                return "No se encontró usuario.";
            }
        }
        catch (Exception e) {
            res.status(500);
            e.printStackTrace();
            return "error.";        
        }
    };    
    
    public static RouteGroup usuariosRouter = () -> {            
        get("", getAll);
        get("/", getAll);
        post("", crear);
        post("/", crear);
        get("/:id", leer);
        put("/:id", actualizar);
        delete("/:id", borrar);        
    };
          
}
