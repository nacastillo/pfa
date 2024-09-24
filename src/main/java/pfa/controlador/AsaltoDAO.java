package pfa.controlador;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
import pfa.modelo.Detenido;
import pfa.modelo.Entidad;
import pfa.modelo.Juez;
import pfa.modelo.Sucursal;
import pfa.util.HibernateUtil;

public class AsaltoDAO {
    
    public static Route getAll = (req, res) -> {
        Gson g = new Gson();
        List <Asalto> x = null;
        Session s = null;        
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            Query q = s.createQuery("from Asalto", Asalto.class);
            x = q.getResultList();            
            if (!x.isEmpty()) {
                res.type("application/json");
                res.body(g.toJson(x));
                s.close();
                return res.body();
            }
            else {
                res.status(404);
                res.type("application/json");
                return "No hay asaltos registrados.";
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
        Asalto x = g.fromJson(req.body(), Asalto.class);
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
        JsonObject jo = new JsonObject();
        Sucursal suc = null;
        Juez juez = null;
        Detenido det = null;
        Entidad ent = null;
        Asalto x = null;
        Session s = null;
        try {
            long id = Long.parseLong(req.params(":id"));
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            x = s.get(Asalto.class, id);
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
                if (x.getJuez() != null) {
                    juez = s.get(Juez.class, x.getJuez());
                    jo.addProperty("juez", x.getJuez());
                    jo.addProperty("claveJuez",juez.getClave());
                    jo.addProperty("nombreJuez",juez.getNombre());
                }
                if (x.getDetenido() != null) {
                    det = s.get(Detenido.class, x.getDetenido());
                    jo.addProperty("detenido", x.getDetenido());
                    jo.addProperty("codigoDetenido",det.getCodigo());
                    jo.addProperty("nombreDetenido",det.getNombre());
                }                
                if (x.getFechaCondena() != null) {
                    jo.addProperty("fechaCondena", x.getFechaCondena());
                }
                res.type("application/json");                
                s.close();                
                return jo;
            }
            else {
                res.status(404);
                res.type("application/json");
                return "No se encontró asalto.";
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
        Asalto front = g.fromJson(req.body(), Asalto.class);
        Asalto db = null;
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            db = s.get(Asalto.class, id);
            if (db != null) {
                 /* codigo, fecha, sucursal, juez, detenido, fechaCondena */
                if (front.getCodigo() == null) {
                    front.setCodigo(db.getCodigo());
                }
                if (front.getFecha() == null || front.getFecha().equals("")) {
                    front.setFecha(db.getFecha());
                }
                if (front.getSucursal() == null) {
                    front.setSucursal(db.getSucursal());
                }
                else 
                    if (front.getSucursal() == -1) {
                        front.setSucursal(null);
                    }
                if (front.getJuez() == null) {
                    front.setJuez(db.getJuez());
                }
                else 
                    if (front.getJuez() == -1) {
                        front.setJuez(null);
                    }
                if (front.getDetenido() == null) {
                    front.setDetenido(db.getDetenido());
                }
                else 
                    if (front.getDetenido() == -1) {
                        front.setDetenido(null);
                    }
                if (front.getFechaCondena() == null || front.getFechaCondena().equals("")) {
                    front.setFechaCondena(db.getFechaCondena());
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
                res.type("application/json");
                return "No se encontró Asalto.";
            }
        }
        catch (Exception e) {
            res.status(500);
            return e.toString();
        }
        finally {
            s.close();
        }        
    };

    public static Route borrar = (req, res) -> {
        Gson g = new Gson();
        long id = Long.parseLong(req.params(":id"));
        Asalto x = null;
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            x = s.get(Asalto.class, id);
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
                return "No se encontró asalto.";
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
