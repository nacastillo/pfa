package pfa.controlador;

import com.google.gson.Gson;
import java.util.List;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import spark.Route;
import spark.RouteGroup;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;
import pfa.modelo.Sucursal;
import pfa.util.HibernateUtil;

/* TODO: ver cómo editar la entidad en actualizar */

public class SucursalDAO {

    public static Route getAll = (req, res) -> {
        Gson g = new Gson();
        List <Sucursal> resp = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            Query q = s.createQuery("from Sucursal", Sucursal.class);
            resp = q.getResultList();
            s.close();
            if (!resp.isEmpty()) {
                res.type("application/json");
                return g.toJson(resp);
            }
            else {
                res.status(404);
                return "No hay sucursales registradas.";
            }          
        }
        catch (Exception e){
            e.printStackTrace();
            return "Error.";
        }        
    };
    
    public static Route crear = (req, res) -> {
        Gson g = new Gson ();
        Sucursal s = g.fromJson (req.body(), Sucursal.class);
        try {
            Session t = HibernateUtil.getSessionFactory().openSession();
            t.beginTransaction();
            t.persist(s);
            t.getTransaction().commit();
            t.close();
            res.type("application/json");
            return g.toJson(s);
        } /*
        catch (PropertyValueException e) {
            e.printStackTrace();
            return "Excepción: el campo " + e.getPropertyName() + " es nulo.";
        } */
        catch (ConstraintViolationException e) {
            e.printStackTrace();            
            return "Valor duplicado: " + e.getConstraintName();            
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Excepción";
        }
    };
    
    public static Route leer = (req, res) -> {
        Gson g = new Gson();
        Sucursal t = null;
        try {
            long id = Long.parseLong(req.params(":id"));
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            t = s.get(Sucursal.class, id);
            s.getTransaction().commit();
            s.close();
            if (t != null) {
                res.type("application/json");
                return g.toJson(t);
            }            
            else { 
                res.status(404);
                return "No se encontró sucursal con ese id (" + id + ").";
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
        Sucursal tu = g.fromJson(req.body(), Sucursal.class);
        Sucursal t = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            t = s.get(Sucursal.class, id);            
            if (t != null) { /* Campos a actualizar: codigo, cantidadEmpleados, nombre, entidad */
                if (tu.getCodigo() != null) {
                    t.setCodigo(tu.getCodigo());
                }
                if (tu.getCantidadEmpleados() != null) {
                    t.setCantidadEmpleados(tu.getCantidadEmpleados());
                }
                if (tu.getNombre() != null) {
                    t.setNombre(tu.getNombre());
                }
                s.update(t);
                s.getTransaction().commit();
                s.close();
                res.type("application/json");
                return g.toJson(t);
            }
            else {
                res.status(404);
                return "No se encontró sucursal con ese id (" + id + ").";
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
        Sucursal t = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            t = s.get(Sucursal.class, id);
            if (t != null) {
                s.remove(t);
                s.getTransaction().commit();
                s.close();
                res.type("application/json");
                return g.toJson(t);
            }
            else {
                res.status(404);
                return "No se encontró sucursal.";
            }            
        }
        catch (Exception e) {
            res.status(500);
            e.printStackTrace();
            return "Excepción.";
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