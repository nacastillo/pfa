package pfa.controlador;

import com.google.gson.Gson;
import spark.Route;
import spark.RouteGroup;
import static spark.Spark.get;
import pfa.modelo.dto.Banda;

public class BandaDAO {

    public static Route getAll = (req, res) -> {
        return "ruta de getAll Banda";
    };
    
    public static Route crear = (req, res) -> {
        return "ruta de crear Banda";
    };
    
    public static Route leer = (req, res) -> {
        return "ruta de leer Banda";
    };
    
    public static Route actualizar = (req, res) -> {
        return "ruta de actualizar Banda";
    };
    
    public static Route borrar = (req, res) -> {
        return "ruta de borrar Banda";
    };   
    
    public static RouteGroup bandasRouter = () -> {            
        get("", getAll);
        get("/c", crear);
        get("/l", leer);
        get("/a", actualizar);
        get("/b",borrar);        
    }; 
}
