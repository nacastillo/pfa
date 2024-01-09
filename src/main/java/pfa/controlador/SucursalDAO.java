package pfa.controlador;

import com.google.gson.Gson;
import spark.Route;
import spark.RouteGroup;
import static spark.Spark.get;
import pfa.modelo.dto.Sucursal;

public class SucursalDAO {

    public static Route getAll = (req, res) -> {
        return "ruta de getAll Sucursal";
    };
    
    public static Route crear = (req, res) -> {
        return "ruta de crear Sucursal";
    };
    
    public static Route leer = (req, res) -> {
        return "ruta de leer Sucursal";
    };
    
    public static Route actualizar = (req, res) -> {
        return "ruta de actualizar Sucursal";
    };
    
    public static Route borrar = (req, res) -> {
        return "ruta de borrar Sucursal";
    };   
    
    public static RouteGroup sucursalesRouter = () -> {
        get("", getAll);
        get("/c", crear);
        get("/l", leer);
        get("/a", actualizar);
        get("/b",borrar);        
    }; 
}