/**
 * 4to commit: se borra uno de los constructores de detenido
 * que no recibe codbanda como parametro
 */

package pfa.modelo;

import static pfa.modelo.EntradaSalida.*;

import java.io.*;

public class Detenido implements Serializable {
    private int codigo;
    private String nombreCompleto;
    private int codBanda;
    		
    public Detenido(int codigo, String apellido, String nombre, int codBanda, Fecha fechaAsalto) {
        this.codigo = codigo;
        nombreCompleto = apellido + ", " + nombre;
        this.codBanda = codBanda;        
    }
    
    public void setBanda(int codBanda) {
        this.codBanda = codBanda;
    }
    
    /**
     * Muestra al detenido con su codigo, nombre, fecha de delito
     * y banda si es que tiene asignada alguna.
     * Se utiliza un ternario para simplificar codigo.
     */

    public void mostrarDetenido() {
        mostrarMsjLn("Codigo: " + codigo +
                    ", nombre: " + nombreCompleto +
                    //", fecha del delito: " + fechaAsalto +
                    ", banda asignada: " + 
                    (codBanda == 0 ? "ninguna." : codBanda)
                    );
    }

    public void serializar(String nom) throws IOException {
        ObjectOutputStream o =
        new ObjectOutputStream (new BufferedOutputStream (new FileOutputStream (nom)));
        o.writeObject(this);
        o.close();
    }
    
    public Detenido deSerializar (String nom) throws IOException, ClassNotFoundException {
        ObjectInputStream o =
        new ObjectInputStream (new BufferedInputStream (new FileInputStream (nom)));
        Detenido d = (Detenido) o.readObject();
        o.close();    
        return d;
    }   
}