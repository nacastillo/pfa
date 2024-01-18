package pfa.modelo.dto;

import static pfa.modelo.EntradaSalida.*;
import java.io.*;
import lombok.Data;

public class Detenido implements Serializable {
    
    
    private Long id;
    private Integer codigo;
    private String nombreCompleto;
    private Banda banda;
        
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