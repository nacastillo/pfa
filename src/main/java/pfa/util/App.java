package pfa.util;

import static io.leego.banana.BananaUtils.bananaify;
import static spark.Spark.before;
import static spark.Spark.post;
import static spark.Spark.options;
import static spark.Spark.path;
import static spark.Spark.port;
import static spark.Spark.halt;
import static pfa.controlador.UsuarioDAO.usuariosRouter;
import static pfa.controlador.SucursalDAO.sucursalesRouter;
import static pfa.controlador.JuezDAO.juecesRouter;
import static pfa.controlador.BandaDAO.bandasRouter;
import static pfa.controlador.ContratoDAO.contratosRouter;
import static pfa.controlador.EntidadDAO.entidadesRouter;
import static pfa.controlador.AsaltoDAO.asaltosRouter;
import static pfa.controlador.DetenidoDAO.detenidosRouter;
import static pfa.controlador.Login.loginRuta;
import static pfa.util.HibernateUtil.getSessionFactory;
import static pfa.util.JWTUtil.verificar;
import static pfa.util.JWTUtil.extraerRol;
import static pfa.util.JWTUtil.limpiar;

public class App {    

    public static void ejecutar (int puerto) {
        port(puerto);
        habilitarCORS();
        var s = getSessionFactory();
        if (s == null) {
            System.out.println("Problemas con sessionFactory. Compruebe si se ejecuta mysql en xampp.");
            System.exit(0);
        }
        System.out.println((bananaify("Policia\nFederal\nArgentina")));
        System.out.println("Corriendo back en puerto: " + puerto);
        cargarRutas();
    }        

    public static void cargarRutas () {
        before ("/*", (req, res) -> {
            if (req.uri().equals("/login")) {
                return;
            }
            var token = req.headers("Authorization");        
            if (token == null) {
                halt(400, "token nulo");            
            }
            else {            
                if (!verificar(limpiar(token))) {
                    halt(401, "token no válido");                
                }            
            }            
            var rol = extraerRol(limpiar(req.headers("Authorization")));
            if (rol.equals("Vigilante")) {
                halt(403, "Acceso denegado a Vigilante");
            }
            if (rol.equals("Investigador") && !req.requestMethod().equals("GET")) {
                halt(403, "Acceso denegado a Investigador");
            }
        });
        post("/login", loginRuta);
        path("u", usuariosRouter);
        path("s", sucursalesRouter);
        path("j", juecesRouter);
        path("b", bandasRouter);
        path("e", entidadesRouter);
        path("c", contratosRouter);
        path("a", asaltosRouter);
        path("d", detenidosRouter);
    }

    public static void habilitarCORS () {
        
        options("/*", (req, res) -> {            
            String headers = req.headers("Access-Control-Request-Headers");
            if (headers != null) {
                res.header("Access-Control-Allow-Headers", headers);
            }
            String method = req.headers("Access-Control-Request-Method");
            if (method != null) {
                res.header("Access-Control-Allow-Methods", method);
            }
            res.status(200);
            return "OK";
        });        
        
        before((req, res) -> {            
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            res.header("Access-Control-Allow-Headers", "Content-Type, Authorization");                        
            if (req.requestMethod().equalsIgnoreCase("OPTIONS")) {
                halt(200);
            }            
        });
    }
}