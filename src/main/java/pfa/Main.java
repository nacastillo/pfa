package pfa;

import static pfa.modelo.EntradaSalida.*;

import static spark.Spark.*;

import java.io.*;

import pfa.modelo.Sistema;
//import pfa.vista.Vista;

public class Main {
    
    public static void main (String[] args) {
        // Vista v = new Vista();
        Sistema s = new Sistema();
        int op = 1;        
        try {
            s = s.deSerializar("hola.txt");
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
            get("/", (req, res) -> {
                return "<p>TEST</p>";
            });
            op = s.menuLogin();
        }
        while (op == 0);
        s.grabarSistema();        
    }    
}
