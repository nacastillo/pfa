package pfa.util;

import static io.leego.banana.BananaUtils.bananaify;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import static com.google.gson.JsonParser.parseString;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Filter;
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
import static pfa.controlador.UsuarioDAO.getByUsrAndPwd;
import static pfa.util.HibernateUtil.getSessionFactory;
import static pfa.util.JWTUtil.generar;
import static pfa.util.JWTUtil.verificar;
import static pfa.util.JWTUtil.limpiar;
import static pfa.util.JWTUtil.extraerRol;

public class App {    

    public static Filter filtrar = (req, res) -> {
        if (req.uri().equals("/login")) {
            return;
        }
        final String token = req.headers("Authorization");
        System.out.println("token es:\n" + token);
        if (token == null) {
            halt(400, "token nulo");
            /*
            final Gson g = new Gson();            
            final JsonObject jo = parseString(login(req)).getAsJsonObject();
            if (noEsUserValido(jo)) {                        
                halt(401, "Usuario no válido");
            }            
            else {                
                //res.body(generar(jo));
                //res.header("Authorization",generar(jo));
                //halt(200, "Usuario:\n" + g.toJson(jo));
                halt(200, generar(jo));
            }
            */
        }
        else {
            if (verificar(limpiar(token))) {            
                System.out.println("verificarToken dio verdadero, token es:\n" + token);
                System.out.println("rol es:\n" + extraerRol(limpiar(token)));                
            }
            else {
                System.out.println("verificarToken dio falso, token es:\n" + token);
                halt(401, "token no válido");
            }
        }
    };    

    public static void ejecutar (int puerto) {
        port(puerto);
        habilitarCORS();
        SessionFactory s = getSessionFactory();
        if (s == null) {
            System.out.println("Problemas con sessionFactory. Compruebe si se ejecuta mysql en xampp.");
            System.exit(0);
        }
        System.out.println((bananaify("Policia\nFederal\nArgentina")));                
        System.out.println("Corriendo back en puerto: " + puerto);        
        post("/login", loginRuta);
        before ("/*", filtrar);        
        cargarPaths();
    }    

    public static String login (Request req) {        
        final JsonObject jo = parseString(req.body()).getAsJsonObject();
        final String a = getByUsrAndPwd(jo.get("usr").getAsString(),jo.get("pwd").getAsString());
        System.out.println("a es:\n" + a);
        return a;
    }

    public static boolean noEsUserValido (JsonObject jo) {
        return (jo.get("usr").getAsString().equals("-1"));
    }

    public static void cargarPaths () {        
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

            res.status(200);
            String headers = req.headers("Access-Control-Request-Headers");
            if (headers != null) {
                res.header("Access-Control-Allow-Headers", headers);
            }
            String method = req.headers("Access-Control-Request-Method");
            if (method != null) {
                res.header("Access-Control-Allow-Methods", method);
            }
            return "OK";
        });
        before((req, res) -> {
            //res.header("Access-Control-Allow-Origin", "http://localhost:5173");
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Request-Method", "GET, POST, PUT, DELETE, OPTIONS");
            res.header("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization");
            //res.header("Access-Control-Allow-Credentials","false");
            if (req.requestMethod().equalsIgnoreCase("OPTIONS")) {
                halt(200);  // Responder OK sin hacer más validaciones
            }
        });
    }
}