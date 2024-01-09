package pfa.modelo.dto;

import static pfa.modelo.EntradaSalida.*;

import java.io.*;

public class Sucursal implements Serializable {
    private Long id;
    private Integer codigo;
    private Integer cantidadEmpleados;
    private String nombre;

    public Sucursal(Long id, Integer codigo, Integer cantidadEmpleados, String nombre) {
        this.id = id;
        this.codigo = codigo;
        this.cantidadEmpleados = cantidadEmpleados;
        this.nombre = nombre;
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

    public Integer getCantidadEmpleados() {
        return cantidadEmpleados;
    }

    public void setCantidadEmpleados(Integer cantidadEmpleados) {
        this.cantidadEmpleados = cantidadEmpleados;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    
    
    public void sumarEmpl() {
        cantidadEmpleados++;
    }	
    
    public void mostrarSucursal () {
        mostrarMsjLn("Cod. Sucursal: " + codigo +                      
                     ", cantidad de empleados: " + cantidadEmpleados
                    );
    }

    public void serializar (String nom) throws IOException {
        ObjectOutputStream o = new ObjectOutputStream (new BufferedOutputStream (new FileOutputStream (nom)));
        o.writeObject(this);
        o.close();
    }
    
    public Sucursal deSerializar (String nom) throws IOException, ClassNotFoundException {
        ObjectInputStream o = new ObjectInputStream (new BufferedInputStream (new FileInputStream (nom)));
        Sucursal s = (Sucursal) o.readObject();
        o.close();
        return s;
    }     
}
