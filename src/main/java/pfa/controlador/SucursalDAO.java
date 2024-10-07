package pfa.controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import org.hibernate.Session;
//import org.hibernate.query.Query;
import spark.Route;
import spark.RouteGroup;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;
import pfa.modelo.Sucursal;
import pfa.modelo.Entidad;
import pfa.util.HibernateUtil;

public class SucursalDAO {

    public static Route getAll = (req, res) -> {
        Gson g = new Gson();
        JsonArray ja = new JsonArray();        
        List <Sucursal> resp = null;
        Session s = null;        
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            var q = s.createQuery("from Sucursal", Sucursal.class);
            resp = q.getResultList();
            if (!resp.isEmpty()) {                
                for (Sucursal suc : resp) {
                    JsonObject jo = g.toJsonTree(suc).getAsJsonObject();
                    if (suc.getEntidad() != null) {
                        Entidad e = s.get(Entidad.class, suc.getEntidad());
                        jo.addProperty("nombreEntidad",e.getNombre());
                    }
                    ja.add(jo);
                }
                //res.body(g.toJson(resp));
                res.type("application/json");
                res.body(g.toJson(ja));
                s.close();
                return res.body();
            }
            else {
                res.status(404);
                s.close();
                return "No hay sucursales registradas.";
            }          
        }
        catch (Exception e){
            res.status(500);
            return e.toString();
        }        
        finally {
            if (s != null)
                s.close();
        }
    };
    
    public static Route crear = (req, res) -> {
        Gson g = new Gson ();
        Sucursal suc = g.fromJson (req.body(), Sucursal.class);
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            s.persist(suc);
            s.getTransaction().commit();            
            res.type("application/json");
            res.body(g.toJson(suc));
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
        Sucursal suc = null;       
        Session s = null; 
        try {
            long id = Long.parseLong(req.params(":id"));
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            suc = s.get(Sucursal.class, id);
            s.getTransaction().commit();            
            if (suc != null) {
                res.type("application/json");
                res.body(g.toJson(suc));
                s.close();
                return res.body();
            }            
            else { 
                res.status(404);
                s.close();
                return "No se encontró sucursal con ese id (" + id + ").";
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
        Sucursal front = g.fromJson(req.body(), Sucursal.class);
        Sucursal db = null;
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            db = s.get(Sucursal.class, id);            
            if (db != null) {
                /* codigo, cantidadEmpleados, nombre, domicilio,
                entidad, contratos, asaltos 
                */
                if (front.getCodigo() == null) {
                    front.setCodigo(db.getCodigo());
                }
                if (front.getCantidadEmpleados() == null) {
                    front.setCantidadEmpleados(db.getCantidadEmpleados());
                }
                if (front.getNombre() == null || front.getNombre().equals("")) {
                    front.setNombre(db.getNombre());
                }
                if (front.getDomicilio() == null || front.getDomicilio().equals("")) {
                    front.setDomicilio(db.getDomicilio());
                }
                if (front.getEntidad() == null) { 
                    front.setEntidad(db.getEntidad());
                }
                else  // logica para desasociar
                    if (front.getEntidad() == -1) {
                        front.setEntidad(null);
                    }                
                if (front.getContratos() == null) {
                    front.setContratos(db.getContratos());
                }
                if (front.getAsaltos() == null) {
                    front.setAsaltos(db.getAsaltos());
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
                s.close();
                return "No se encontró sucursal con ese id (" + id + ").";
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
        Sucursal suc = null;
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            suc = s.get(Sucursal.class, id);
            if (suc != null) {
                res.type("application/json");
                res.body(g.toJson(suc));
                s.remove(suc);
                s.getTransaction().commit();                
                s.close();
                return res.body();
            }
            else {
                res.status(404);
                s.close();
                return "No se encontró sucursal.";
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