package pfa.modelo.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.*;
import lombok.Data;
import static pfa.modelo.EntradaSalida.*;

@Data
@Entity
@Table (name = "jueces")

public class Juez {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "id")
    private Long id;

    @Column (name = "claveInterna")
    private Integer claveInterna;
    
    @Column (name = "nombreCompleto")
    private String nombreCompleto;
    
    @Column (name = "aniosServicio")
    private Integer aniosServicio;

    /*    
    public Juez () {
        claveInterna = leerEntero("Ingrese la clave interna del juez: ");
        String ape = leerCadena("Ingrese el apellido del juez: ");
        String nom = leerCadena("Ingrese el nombre del juez: ");
        aniosServicio = leerEntero("Ingrese los años en servicio del juez: ");
        nombreCompleto = ape + ", " + nom;    
    }   
    
    public boolean esEsteJuez(int cod) {
        return cod == claveInterna;
    }
    
    public void mostrarJuez () {
        mostrarMsjLn("Clave interna: " + claveInterna +
                     ", nombre completo: " + nombreCompleto +
                     ", años en servicio: " + aniosServicio +
                    ".");        
    }
    
    public void serializar (String nom) throws IOException {
        ObjectOutputStream o =
        new ObjectOutputStream (new BufferedOutputStream (new FileOutputStream (nom)));
        o.writeObject(this);
        o.close();    
    }
    
    public Juez deSerializar (String nom) throws IOException, ClassNotFoundException {
        ObjectInputStream o =
        new ObjectInputStream (new BufferedInputStream (new FileInputStream (nom)));
        Juez j = (Juez) o.readObject();    
        o.close();
        return j;
    }
    */
}
