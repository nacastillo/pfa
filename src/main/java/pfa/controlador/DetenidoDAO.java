package pfa.controlador;

import com.google.gson.Gson;
import spark.Route;
import spark.RouteGroup;
import static spark.Spark.get;
import pfa.modelo.Entidad;

public class DetenidoDAO {
    
    public static Route getAll = (req, res) -> {
        return "ruta de getAll Detenido";
    };
    
    public static Route crear = (req, res) -> {
        return "ruta de crear Detenido";
    };
    
    public static Route leer = (req, res) -> {
        return "ruta de leer Detenido";
    };
    
    public static Route actualizar = (req, res) -> {
        return "ruta de actualizar Detenido";
    };
    
    public static Route borrar = (req, res) -> {
        return "ruta de borrar Detenido";
    };   
    
    public static RouteGroup detenidosRouter = () -> {            
        get("", getAll);
        get("/c", crear);
        get("/l", leer);
        get("/a", actualizar);
        get("/b",borrar);        
    };    
}
