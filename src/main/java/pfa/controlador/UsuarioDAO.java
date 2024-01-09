package pfa.controlador;

import com.google.gson.Gson;
import pfa.modelo.BaseDeUsuarios;
import pfa.modelo.Sistema;
import pfa.modelo.dto.Usuario;
import spark.Route;
import spark.RouteGroup;
import static spark.Spark.get;
import static spark.Spark.path;

public class UsuarioDAO {    
    
    public static Route getAll = (req, res) -> {
        res.type("application/json");
        Gson gson = new Gson();
        String u = "ruta de get all";        
        return gson.toJson(u);    
    };
    
    public static Route crear = (req, res) -> {
        return "ruta de crear Usuario";
    };
    
    public static Route leer = (req, res) -> {
        return "ruta de leer Usuario";
    };
    
    public static Route actualizar = (req, res) -> {
        return "ruta de actualizar Usuario";
    };
    
    public static Route borrar = (req, res) -> {
        return "ruta de borrar Usuario";
    };    
    
    public static RouteGroup usuariosRouter = () -> {            
        get("", getAll);
        get("/c", crear);
        get("/l", leer);
        get("/a", actualizar);
        get("/b",borrar);        
    };     
         
    /** Usando el sistema viejo */    
    public static void rutaUsuarios (Sistema s) {        
        path("usuarios", () -> {
            get("", (req, res) -> {
                    res.type("application/json");
                    Gson gson = new Gson();
                    BaseDeUsuarios u = s.getUsuarios();               
                    return gson.toJson(u);                
                }                
            );
            get("/crear", (req, res) -> {
                return "<p>entrando en ruta1/crear </p>";               
            });
            get("/leer", (req, res) -> {
                return "<p>entrando en ruta1/leer </p>";               
            });
            get("/actualizar", (req, res) -> {
                return "<p>entrando en ruta1/actualizar </p>";               
            });
            get("/borrar", (req, res) -> {
                return "<p>entrando en ruta1/borrar </p>";               
            });                    
        });
    };      
}
