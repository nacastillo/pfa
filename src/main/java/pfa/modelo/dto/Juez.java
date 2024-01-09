/**
 * asignarcondena y tiene condena se mueve a ListaDeDelitos
 */
package pfa.modelo.dto;

import static pfa.modelo.EntradaSalida.*;

import java.io.*;

public class Juez {
    private Long id;
    private Integer claveInterna;
    private String nombreCompleto;
    private Integer aniosServicio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getClaveInterna() {
        return claveInterna;
    }

    public void setClaveInterna(Integer claveInterna) {
        this.claveInterna = claveInterna;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Integer getAniosServicio() {
        return aniosServicio;
    }

    public void setAniosServicio(Integer aniosServicio) {
        this.aniosServicio = aniosServicio;
    }
    
    public Juez(int claveInterna, String nombreCompleto, int aniosServicio) {
        this.claveInterna = claveInterna;
        this.nombreCompleto = nombreCompleto;
        this.aniosServicio = aniosServicio;
    } 
    
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
    
}
