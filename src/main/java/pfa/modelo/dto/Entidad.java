package pfa.modelo.dto;

import static pfa.modelo.EntradaSalida.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Data;

@Data
public class Entidad implements Serializable {

    private Long id;
    private Integer codigo;
    private String domicilio;
    private String nombre;
    private Collection<Sucursal> sucursales;

    public Entidad(Long id, Integer codigo, String domicilio, String nombre) {
        this.id = id;
        this.codigo = codigo;
        this.domicilio = domicilio;
        this.nombre = nombre;
        sucursales = new ArrayList<Sucursal>();
    }

    public void agregarSucursal() {
        int cod = leerEntero("Ingrese el codigo de la sucursal: ");
        String dom = leerCadena("Ingrese el domicilio de la sucursal: ");
        int cantEmpl = leerEntero("Ingrese la cantidad de empleados de la sucursal: ");
        //sucursales.add(new Sucursal(cod,dom,cantEmpl));

    }

    public boolean esEntidad(int cod) {
        return (cod == codigo);
    }

    public void mostrarEntidad() {
        System.out.println("Codigo de entidad: " + codigo
                + ", Domicilio: " + domicilio
                + ".");
    }

    public void listarSucursales() {
        if (sucursales.isEmpty()) {
            System.out.println("No hay sucursales ingresadas");
        } else {
            System.out.println("Sucursales:");
            for (Sucursal s : sucursales) {
//                s.mostrarSucursal();
            }
        }
    }

    public void serializar(String nom) throws IOException {
        ObjectOutputStream o = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(nom)));
        o.writeObject(this);
        o.close();
    }

    public Entidad deSerializar(String nom) throws IOException, ClassNotFoundException {
        ObjectInputStream o = new ObjectInputStream(new BufferedInputStream(new FileInputStream(nom)));
        Entidad e = (Entidad) o.readObject();
        o.close();
        return e;
    }
}
