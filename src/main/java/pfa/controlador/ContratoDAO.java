package pfa.controlador;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import spark.Route;
import spark.RouteGroup;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import pfa.modelo.Contrato;
import pfa.modelo.Entidad;
import pfa.modelo.Vigilante;
import pfa.modelo.Sucursal;
import pfa.util.HibernateUtil;

public class ContratoDAO {

    public static Route getAll = (req, res) -> {
        Gson g = new Gson();
        List <Contrato> x = null;
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            Query q = s.createQuery("from Contrato", Contrato.class);
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
                return "No hay contratos registrados.";
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
        Contrato x = g.fromJson(req.body(), Contrato.class);
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            s.persist(x);
            s.getTransaction().commit();
            res.type("application/json");
            res.body(g.toJson(x));
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
        JsonObject jo = new JsonObject();
        Vigilante vig = null;
        Sucursal suc = null;
        Entidad ent = null;
        Contrato x = null;
        Session s = null;
        try {
            long id = Long.parseLong(req.params(":id"));
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            x = s.get(Contrato.class, id);
            s.getTransaction().commit();            
            if (x != null) {
                jo.addProperty("id",x.getId());
                jo.addProperty("codigo",x.getCodigo());
                jo.addProperty("fecha",x.getFecha());                
                if (x.getSucursal() != null) {
                    suc = s.get(Sucursal.class, x.getSucursal());
                    jo.addProperty("sucursal",x.getSucursal());
                    jo.addProperty("codigoSucursal",suc.getCodigo());
                    jo.addProperty("nombreSucursal",suc.getNombre());
                    if (suc.getEntidad() != null) {
                        ent = s.get(Entidad.class, suc.getEntidad());
                        jo.addProperty("entidad", suc.getEntidad());
                        jo.addProperty("codigoEntidad", ent.getCodigo());
                        jo.addProperty("nombreEntidad", ent.getNombre());
                    }                    
                }
                if (x.getVigilante() != null) {
                    vig = s.get(Vigilante.class, x.getVigilante());
                    jo.addProperty("vigilante", x.getVigilante());
                    jo.addProperty("usrVigilante", vig.getUsr());
                    jo.addProperty("codigoVigilante", vig.getCodigo());
                    jo.addProperty("edadVigilante", vig.getEdad());
                }                
                jo.addProperty("armado",x.getArmado());
                res.type("application/json");                
                s.close();
                return jo;
            } 
            else {
                res.status(404);
                return "No se encontró contrato.";
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
        long id = Long.parseLong(req.params(":id"));
        Contrato front = g.fromJson(req.body(), Contrato.class);
        Contrato db = null;
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            db = s.get(Contrato.class, id);
            if (db != null) {
                /* codigo, fecha, vigilante, sucursal, armado */
                if (front.getCodigo() == null) {
                    front.setCodigo(db.getCodigo());
                }
                if (front.getFecha() == null || front.getFecha().equals("")) {
                    front.setFecha(db.getFecha());
                }
                if (front.getVigilante() == null) { 
                    front.setVigilante(db.getVigilante());
                }
                else
                    if (front.getVigilante() == -1) {
                        front.setVigilante(null);
                    }
                if (front.getSucursal() == null) { 
                    front.setSucursal(db.getSucursal());
                }
                else
                    if (front.getSucursal() == -1) {
                        front.setSucursal(null);
                    }
                if (front.getArmado() == null) {
                    front.setArmado(db.getArmado());
                }     
                s.merge(front);
                s.getTransaction().commit();
                res.type("application/json");
                res.body(g.toJson(front));
                s.close();
                return res.body();
            }
            else {
                res.status(404);
                return "No se encontró contrato con ese id (" + id + ").";
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
        Gson g = new Gson();
        long id = Long.parseLong(req.params(":id"));
        Contrato x = null;
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            x = s.get(Contrato.class, id);
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
                return "No se encontró contrato.";
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