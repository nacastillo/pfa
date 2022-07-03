package pfa;

import java.io.*;

public class Main {
    
    public static void main (String[] args) {
        Sistema s = new Sistema();
        try {
            s = s.deSerializar("hola.txt");
        }
        catch (FileNotFoundException ex) {
            System.out.println("Primer ingreso al sistema.\nGenerando default admin...");
            s.defaultAdmin();
        }
        catch (IOException | ClassNotFoundException ex) {
            System.out.println("Problema al abrir el sistema.\nReiniciando sistema y generando default admin...");
            s.defaultAdmin();
        }
        Usuario u = s.login2();
        u.menu(s);        
        try {
            s.serializar("hola.txt");
        }
        catch (IOException ex) {
            System.out.println("Problemas al grabar sistema");        
        }        
        /**
         * eliminado el bloque try catch de NullPointerException ya que login2 no puede arrojar dicha excepcion
         */
    }    
}
