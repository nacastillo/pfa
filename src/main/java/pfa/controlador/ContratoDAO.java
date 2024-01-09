package pfa.controlador;

import com.google.gson.Gson;
import spark.Route;
import spark.RouteGroup;
import static spark.Spark.get;
import pfa.modelo.dto.Contrato;

public class ContratoDAO {

    public static Route getAll = (req, res) -> {
        return "ruta de getAll Contrato";
    };
    
    public static Route crear = (req, res) -> {
        return "ruta de crear Contrato";
    };
    
    public static Route leer = (req, res) -> {
        return "ruta de leer Contrato";
    };
    
    public static Route actualizar = (req, res) -> {
        return "ruta de actualizar Contrato";
    };
    
    public static Route borrar = (req, res) -> {
        return "ruta de borrar Contrato";
    };   
    
    public static RouteGroup contratosRouter = () -> {
        get("", getAll);
        get("/c", crear);
        get("/l", leer);
        get("/a", actualizar);
        get("/b",borrar);        
    }; 
}