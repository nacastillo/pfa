package pfa;

import java.io.*;
import pfa.modelo.Sistema;
import static pfa.modelo.EntradaSalida.*;

import static pfa.controlador.UsuarioDAO.usuariosRouter;
import static pfa.controlador.SucursalDAO.sucursalesRouter;
import static pfa.controlador.JuezDAO.juecesRouter;
import static pfa.controlador.BandaDAO.bandasRouter;
import static pfa.controlador.ContratoDAO.contratosRouter;
import static pfa.controlador.EntidadDAO.entidadesRouter;
import static pfa.controlador.AsaltoDAO.asaltosRouter;
import static pfa.controlador.DetenidoDAO.detenidosRouter;

import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.port;

public class Main {    
    public static void main (String[] args) {
        int p = 2000;
        port(p);
        path("u", usuariosRouter);
        path("s", sucursalesRouter);
        path("j", juecesRouter);
        path("b", bandasRouter);
        path("e", entidadesRouter);
        path("c", contratosRouter);   
        path("a", asaltosRouter);
        path("d", detenidosRouter);
        System.out.println("Corriendo back en puerto: " + p);
    }
    
    /*
    public static void main (String[] args) {
        Sistema s = new Sistema();
        int op = 1;        
        try {
            s = s.deSerializar("sistema.bin");
        }
        catch (FileNotFoundException ex) {
            mostrarMsjLn("Primer ingreso al sistema.\nGenerando default admin...");
            s.defaultAdmin();
        }
        catch (IOException | ClassNotFoundException ex) {
            mostrarMsjLn("Problema al abrir el sistema.\nReiniciando sistema y generando default admin...");
            s.defaultAdmin();
        }
        do {        
            port(2000);                        
            path("usuarios",usuariosRouter);            
            get("/*", (req, res) -> {
                return "<h>error fatal</h>";
            });
            op = s.menuLogin();
        }
        while (op == 0);
        s.grabarSistema();        
    } 
    */
}
