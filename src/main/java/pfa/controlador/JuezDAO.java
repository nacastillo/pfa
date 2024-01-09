package pfa.controlador;

import com.google.gson.Gson;
import spark.Route;
import spark.RouteGroup;
import static spark.Spark.get;
import pfa.modelo.dto.Juez;

public class JuezDAO {

    public static Route getAll = (req, res) -> {
        return "ruta de getAll Juez";
    };
    
    public static Route crear = (req, res) -> {
        return "ruta de crear Juez";
    };
    
    public static Route leer = (req, res) -> {
        return "ruta de leer Juez";
    };
    
    public static Route actualizar = (req, res) -> {
        return "ruta de actualizar Juez";
    };
    
    public static Route borrar = (req, res) -> {
        return "ruta de borrar Juez";
    };   
    
    public static RouteGroup juecesRouter = () -> {            
        get("", getAll);
        get("/c", crear);
        get("/l", leer);
        get("/a", actualizar);
        get("/b",borrar);        
    }; 
}