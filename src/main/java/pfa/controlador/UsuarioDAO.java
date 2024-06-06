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
        Gson g = new Gson();
        List <Usuario> resp = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();        
            Query q = s.createQuery("from Usuario", Usuario.class);
            resp = q.getResultList();
            s.close();
            if (!resp.isEmpty()) {
                res.type("application/json");
                return g.toJson(resp);
            }    
            else {
                res.status(404);
                return "No hay usuarios registrados.";
            }    
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Excepción.";
        }        
    };
    
    public static Route crear = (req, res) -> {
        Gson g = new Gson();
        Integer codigo = null, edad = null;
        String rol = null;
        Usuario u = g.fromJson(req.body(), Usuario.class), v = null;
        JsonElement je = JsonParser.parseString(req.body());
        JsonObject jo = je.getAsJsonObject();
        /* Construcción del usuario a persistir */
        if (jo.has("rol")) {
            rol = jo.get("rol").getAsString();
        }
        else {
            rol = "Vigilante";
        }
        if (jo.has("codigo")) {
            codigo = jo.get("codigo").getAsInt();
        }
        if (jo.has("edad")) {
            edad = jo.get("edad").getAsInt();
        }        
        if (rol.equals("Administrador")) {
            v = new Administrador (u.getUsr(), u.getPwd());
        }
        else if (rol.equals("Investigador")) {
            v = new Investigador (u.getUsr(), u.getPwd());
        }            
        else {
            v = new Vigilante(u.getUsr(), u.getPwd(), codigo, edad);            
        }  
        try {             
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            s.persist(v);
            s.getTransaction().commit();
            s.close();
            res.type("application/json");
            return g.toJson(v);
        }        
        catch (Exception e) {
            e.printStackTrace();
            return "Excepción.";
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
            if (u != null) {
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
            return "Excepción.";
        }            
    };
    
    public static Route actualizar = (req, res) -> {
        Gson g = new Gson();        
        JsonElement je = JsonParser.parseString(req.body());
        JsonObject jo = je.getAsJsonObject();
        long id = Long.parseLong(req.params(":id"));
        Usuario u = g.fromJson(req.body(), Usuario.class);
        Usuario v = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            v = s.get(Usuario.class, id);
            if (v != null) {  
                res.type("application/json");          
                if (u.getPwd() != null) {
                    v.setPwd(u.getPwd());
                }                
                if (v instanceof Vigilante) {
                    Vigilante vig = (Vigilante) v;
                    if (jo.has("codigo")) {
                        vig.setCodigo(jo.get("codigo").getAsInt());
                    }
                    if (jo.has("edad")) {
                        vig.setEdad(jo.get("edad").getAsInt());                    
                    }
                    s.merge(vig);
                    s.getTransaction().commit();
                    s.close();                    
                    return g.toJson(vig);
                }
                else {
                    s.merge(v);
                    s.getTransaction().commit();
                    s.close();                    
                    return g.toJson(v);
                }                
            }
            else {
                res.status(404);
                return "No se encontró usuario.";
            }         
        }
        catch (Exception e) {
            res.status(500);
            e.printStackTrace();
            return "Excepción.";
        }
    };
    
    public static Route borrar = (req, res) -> {
        Gson g = new Gson ();
        long id = Long.parseLong(req.params(":id"));
        Usuario x = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            x = s.get(Usuario.class, id);
            if (x != null) {
                s.remove(x);
                s.getTransaction().commit();
                s.close();
                res.type("application/json");
                return "Elemento borrado: " + g.toJson(x);
            }
            else {
                res.status(404);
                return "No se encontró usuario.";
            }
        }
        catch (Exception e) {
            res.status(500);
            e.printStackTrace();
            return "Excepción.";        
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
