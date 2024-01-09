package pfa.modelo.dto;

import java.io.*;

public class Banda implements Serializable {

    private Long id;
    private Integer numero;
    private String nombre;
    private Integer cantidadMiembros;

    public Banda(Long id, Integer numero, String nombre, Integer cantidadMiembros) {
        this.id = id;
        this.numero = numero;
        this.nombre = nombre;
        this.cantidadMiembros = cantidadMiembros;
    }   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCantidadMiembros() {
        return cantidadMiembros;
    }

    public void setCantidadMiembros(Integer cantidadMiembros) {
        this.cantidadMiembros = cantidadMiembros;
    }

    

    
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
}
