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
            s.aniadirAdmin("admin","admin");
        }
        catch (IOException | ClassNotFoundException ex) {
            System.out.println("Problema al abrir el sistema.\nReiniciando sistema y generando default admin...");
            s.aniadirAdmin("admin","admin");
        }
        Usuario u = null;
        try { 
            u = s.login();
            u.menu(s);
        }
        catch (NullPointerException ex) {
            while (u == null) {
                System.out.println("Usuario o clave inválidos. Por favor intente nuevamente.");
                u = s.login();
            }
            u.menu(s);
        }
        try {
            s.serializar("hola.txt");
        }
        catch (IOException ex) {
            System.out.println("Problemas al grabar sistema");        
        }        
    }    
}
