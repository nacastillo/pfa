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
import pfa.modelo.Usuario;
import pfa.modelo.Vigilante;
import pfa.modelo.Investigador;
import pfa.modelo.Administrador;
import pfa.util.HibernateUtil;

public class UsuarioDAO {    
    
    public static Route getAll = (req, res) -> {        
        Gson g = new Gson();
        List <Usuario> x = null;
        Session s = null;        
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();        
            var q = s.createQuery("from Usuario", Usuario.class);
            //Query q = s.createQuery("from Usuario", Usuario.class);
            x = q.getResultList();            
            if (!x.isEmpty()) {
                res.type("application/json");
                res.body(g.toJson(x));
                s.close();
                return res.body();
            }    
            else {
                res.status(404);
                s.close();
                return "No hay usuarios registrados.";
            }    
        }
        catch (Exception e) {
            res.status(500);
            return e.toString();
        }       
        finally {
            if (s != null)
                s.close();
        } 
    };
    
    public static Route crear = (req, res) -> {
        Gson g = new Gson();
        JsonElement je = JsonParser.parseString(req.body());
        JsonObject jo = je.getAsJsonObject();
        Integer codigo = null, edad = null;
        String rol = null;
        Usuario u = g.fromJson(req.body(), Usuario.class), v = null;
        Session s = null;            
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
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            s.persist(v);
            s.getTransaction().commit();            
            res.type("application/json");
            res.body(g.toJson(v));
            s.close();
            return res.body();
        }        
        catch (Exception e) {
            res.status(500);
            return e.toString();
        }        
        finally {
            if (s != null)
                s.close();
        }
    };
    
    public static Route leer = (req, res) -> {
        Gson g = new Gson();
        Usuario u = null;
        Session s = null;        
        try {
            long id = Long.parseLong(req.params(":id"));
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            u = s.get(Usuario.class, id);
            s.getTransaction().commit();            
            if (u != null) {                
                res.type("application/json");
                res.body(g.toJson(u));
                s.close();
                return res.body();
            }
            else {
                res.status(404);
                s.close();
                return "No se encontró usuario.";
            }      
        }
        catch (Exception e) {
            res.status(500);
            return e.toString();
        }            
        finally {
            if (s != null)
                s.close();
        }
    };
    
    public static Route actualizar = (req, res) -> {
        Gson g = new Gson();        
        /*
        JsonElement je = JsonParser.parseString(req.body());
        JsonObject jo = je.getAsJsonObject();
        String rol = jo.get("rol").getAsString();
        */
        Long id = Long.parseLong(req.params(":id"));        
        Usuario front, db = null;        
        Vigilante vigFront, vigDB = null;
        Session s = null;                       
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            db = s.get(Usuario.class, id);
            if (db != null) {                                
                //if (rol.equals("Administrador")) {                    
                if (db instanceof Administrador) {
                    front = g.fromJson(req.body(), Administrador.class);
                }                      
                //else if (rol.equals("Investigador")) {                    
                else if (db instanceof Investigador) {
                    front = g.fromJson(req.body(), Investigador.class);
                }
                else {                   
                    front = g.fromJson(req.body(), Vigilante.class);                    
                }
                /* usr, pwd, codigo (vig), edad (vig), contratos (vig) */ 
                /*                
                if (front.getId() == null)  {
                    front.setId(db.getId());
                }
                */                    
                if (front.getUsr() == null || front.getUsr().equals("")) {
                    front.setUsr(db.getUsr());                    
                }
                if (front.getPwd() == null || front.getPwd().equals("")) {
                    front.setPwd(db.getPwd());
                }
                if (front.getRol() == null || front.getRol().equals("")) {
                    front.setRol(db.getRol());
                }
                //if (rol.equals("Vigilante") && db instanceof Vigilante) {
                if (db instanceof Vigilante) {
                    vigFront = (Vigilante) front;
                    vigDB = (Vigilante) db;
                    if (vigFront.getCodigo() == null) {
                        vigFront.setCodigo(vigDB.getCodigo());
                    }
                    if (vigFront.getEdad() == null) {
                        vigFront.setEdad(vigDB.getEdad());
                    }
                    if (vigFront.getContratos() == null) {
                        vigFront.setContratos(vigDB.getContratos());
                    }
                    s.merge(vigFront);
                    res.body(g.toJson(vigFront));
                }                    
                else {
                    s.merge(front);
                    res.body(g.toJson(front));
                }
                s.getTransaction().commit();
                res.type("application/json");
                s.close();
                return res.body();
            }
            else {
                res.status(404);
                s.close();
                return "No se encontró usuario.";
            }         
        }
        catch (Exception e) {
            res.status(500);
            return e.toString();
        }
        finally {
            if (s != null) 
                s.close();
        }
    };
    
    public static Route borrar = (req, res) -> {
        Gson g = new Gson ();
        long id = Long.parseLong(req.params(":id"));
        Usuario x = null;
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            x = s.get(Usuario.class, id);
            if (x != null) {
                res.type("application/json");
                res.body(g.toJson(x));
                s.remove(x);
                s.getTransaction().commit();
                s.close();                
                return res.body();
            }
            else {
                res.status(404);
                s.close();
                return "No se encontró usuario.";
            }
        }
        catch (Exception e) {
            res.status(500);
            return e.toString();   
        }
        finally {
            if (s != null)
                s.close(); 
        }
    };    

    public static String getByUsrAndPwd (String u, String p) {
        Gson g = new Gson();
        String x, hql = String.format("FROM Usuario WHERE usr = '%s' AND pwd = '%s'", u, p);
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Query <Usuario> query = s.createQuery(hql, Usuario.class);
        Usuario usr = query.uniqueResult(); 
        if (usr != null) {
            x = g.toJson(usr);
        }
        else {            
            x = "{\"usr\":\"-1\"}";
        }
        s.getTransaction().commit();
        s.close();
        return x;        
    }

    /*

    public static Usuario getByUsrAndPwd2 (String u, String p) {
        Gson g = new Gson();
        String x, hql = String.format("FROM Usuario WHERE usr = '%s' AND pwd = '%s'", u, p);
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Query <Usuario> query = s.createQuery(hql, Usuario.class);
        Usuario usr = query.uniqueResult();         
        s.getTransaction().commit();
        s.close();
        return usr;
    }
  
    */

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
