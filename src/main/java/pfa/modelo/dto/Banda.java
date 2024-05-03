package pfa.modelo.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.*;
import lombok.Data;

@Data
@Entity
@Table (name = "bandas")

public class Banda {
// public class Banda implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "id")
    private Long id;
    
    @Column (name = "numero")
    private Integer numero;
    
    @Column (name = "nombre")
    private String nombre;
    
    @Column (name = "cantidadMiembros")
    private Integer cantidadMiembros;    
    
    /*
    public void serializar(String nom) throws IOException {
        ObjectOutputStream o
                = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(nom)));
        o.writeObject(this);
        o.close();
    }

    public Banda deSerializar(String nom) throws IOException, ClassNotFoundException {
        ObjectInputStream o
                = new ObjectInputStream(new BufferedInputStream(new FileInputStream(nom)));
        Banda b = (Banda) o.readObject();
        o.close();
        return b;
    }

    */
}
