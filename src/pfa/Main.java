package pfa;

import static pfa.modelo.EntradaSalida.*;

import java.io.*;

import pfa.modelo.Sistema;
import pfa.vista.Vista;

public class Main {
    
    public static void main (String[] args) {
        Vista v = new Vista();
        Sistema s = new Sistema();
        int op;        
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
            op = s.menuLogin();
        }
        while (op == 0);
        s.grabarSistema();
        /*
        try {
            s.serializar("hola.txt");
        }
        catch (IOException ex) {
            System.out.println("Problemas al grabar sistema");        
        } 
        */
        /**
         * eliminado el bloque try catch de NullPointerException ya que login2 no puede arrojar dicha excepcion
         */
    }    
}
