package pfa.modelo.dto;

import static pfa.modelo.EntradaSalida.*;
import java.io.*;

public class Detenido implements Serializable {
    private Long id;
    private Integer codigo;
    private String nombreCompleto;
    private Banda banda;

    public Detenido(Long id, Integer codigo, String nombreCompleto, Banda banda) {
        this.id = id;
        this.codigo = codigo;
        this.nombreCompleto = nombreCompleto;
        this.banda = banda;
    }    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Banda getBanda () throws NullPointerException {
        return banda;        
    }

    public void setBanda(Banda banda) {
        this.banda = banda;
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
                    (banda == null ? "ninguna." : banda.getNombre())
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