package pfa.controlador;

import com.google.gson.Gson;
import spark.Route;
import spark.RouteGroup;
import static spark.Spark.get;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import pfa.modelo.dto.Banda;
import pfa.modelo.dto.Entidad;

public class AsaltoDAO {
    
    public static Route getAll = (req, res) -> {
        List <Banda> bandas = null;
        Transaction transaction = null;        
        return "ruta de getAll Asalto";
    };
    
    public static Route crear = (req, res) -> {
        return "ruta de crear Asalto";
    };
    
    public static Route leer = (req, res) -> {
        return "ruta de leer Asalto";
    };
    
    public static Route actualizar = (req, res) -> {
        return "ruta de actualizar Asalto";
    };
    
    public static Route borrar = (req, res) -> {
        return "ruta de borrar Asalto";
    };   
    
    public static RouteGroup asaltosRouter = () -> {            
        get("", getAll);
        get("/c", crear);
        get("/l", leer);
        get("/a", actualizar);
        get("/b",borrar);        
    };    
}
