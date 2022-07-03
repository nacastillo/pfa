/**
 * 4to commit: se borra uno de los constructores de detenido
 * que no recibe codbanda como parametro
 */

package pfa;

import java.io.*;

public class Detenido implements Serializable {
    private int codigo;
    private String nombreCompleto;
    private int codBanda;
    private Fecha fechaAsalto;
		
    public Detenido(int codigo, String apellido, String nombre, int codBanda, Fecha fechaAsalto) {
        this.codigo = codigo;
        nombreCompleto = apellido + ", " + nombre;
        this.codBanda = codBanda;
        this.fechaAsalto = fechaAsalto;
    }
    
    public void setBanda(int codBanda) {
        this.codBanda = codBanda;
    }
    
    public void mostrarDetenido() {
        if (codBanda == 0) {
            System.out.println("Codigo: " + codigo + 
                               "; nombre: " + nombreCompleto + 
                               "; fecha del delito" + fechaAsalto +
                               "; banda asignada: ninguna.");
        }
        else {
            System.out.println("Codigo: " + codigo + 
                               "; nombre: " + nombreCompleto + 
                               "; fecha del delito" + fechaAsalto +
                               "; banda asignada:" + codBanda);
        }
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