package pfa.controlador;

import com.google.gson.Gson;
import spark.Route;
import spark.RouteGroup;
import static spark.Spark.get;
import pfa.modelo.Entidad;

public class EntidadDAO {

    public static Route getAll = (req, res) -> {
        return "ruta de getAll Entidad";
    };
    
    public static Route crear = (req, res) -> {
        return "ruta de crear Entidad";
    };
    
    public static Route leer = (req, res) -> {
        return "ruta de leer Entidad";
    };
    
    public static Route actualizar = (req, res) -> {
        return "ruta de actualizar Entidad";
    };
    
    public static Route borrar = (req, res) -> {
        return "ruta de borrar Entidad";
    };   
    
    public static RouteGroup entidadesRouter = () -> {            
        get("", getAll);
        get("/c", crear);
        get("/l", leer);
        get("/a", actualizar);
        get("/b",borrar);        
    }; 
}
