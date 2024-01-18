package pfa.modelo.dto;

import java.io.*;
import java.time.LocalDate;
import lombok.Data;

public class Asalto implements Serializable {
    
    private Long id;
    private Integer codigo;
    private LocalDate fechaAsalto;
    private Sucursal sucursalAsaltada;
    private Juez juezAsignado;
    private Detenido detenido;
    private LocalDate fechaCondena;
  
    public void serializar(String nom) throws IOException {
        ObjectOutputStream o
                = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(nom)));
        o.writeObject(this);
        o.close();
    }

    public Asalto deSerializar(String nom) throws IOException, ClassNotFoundException {
        ObjectInputStream o
                = new ObjectInputStream(new BufferedInputStream(new FileInputStream(nom)));
        Asalto d = (Asalto) o.readObject();
        o.close();
        return d;
    }
}
