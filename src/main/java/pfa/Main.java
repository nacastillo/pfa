package pfa;

import io.leego.banana.BananaUtils;
import static spark.Spark.path;
import static spark.Spark.port;
import static spark.Spark.options;
import static spark.Spark.before;
import static pfa.controlador.UsuarioDAO.usuariosRouter;
import static pfa.controlador.SucursalDAO.sucursalesRouter;
import static pfa.controlador.JuezDAO.juecesRouter;
import static pfa.controlador.BandaDAO.bandasRouter;
import static pfa.controlador.ContratoDAO.contratosRouter;
import static pfa.controlador.EntidadDAO.entidadesRouter;
import static pfa.controlador.AsaltoDAO.asaltosRouter;
import static pfa.controlador.DetenidoDAO.detenidosRouter;
import pfa.util.HibernateUtil;

public class Main {    
    public static void main (String[] args) {        
        int p = 2000;
        port(p);
        enableCORS(); /* para correr front y back en la misma pc*/
        path("u", usuariosRouter);
        path("s", sucursalesRouter);
        path("j", juecesRouter);
        path("b", bandasRouter);
        path("e", entidadesRouter);
        path("c", contratosRouter);   
        path("a", asaltosRouter);
        path("d", detenidosRouter);
        System.out.println((BananaUtils.bananaify("Policia\nFederal\nArgentina")));
        System.out.println("Corriendo back en puerto: " + p);
        HibernateUtil.getSessionFactory();
    }

    public static void enableCORS () {
        options("/*", (req, res) -> {
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
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Request-Method", "GET, POST, PUT, DELETE, OPTIONS");
            res.header("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization");        
        });
    }
}   
