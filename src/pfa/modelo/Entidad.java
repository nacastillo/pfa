/**
     * agregado 4to commit:
     * borrados getSucursales() y getCodEnt()
     * agregados listarSucursales()
     */

package pfa.modelo;

import static pfa.modelo.EntradaSalida.*;

import java.io.*;
import java.util.*;

public class Entidad implements Serializable {
    private int codEnt;
    private String domEnt;
    private List <Sucursal> sucursales;

    public Entidad (int codEnt, String domEnt) {
            this.codEnt = codEnt;
            this.domEnt = domEnt;
            sucursales = new ArrayList <> ();
    }

    public void agregarSucursal () {
        int cod = leerEntero("Ingrese el codigo de la sucursal: ");
        String dom = leerCadena("Ingrese el domicilio de la sucursal: ");
        int cantEmpl = leerEntero("Ingrese la cantidad de empleados de la sucursal: ");
        sucursales.add(new Sucursal(cod,dom,cantEmpl));
        	
    }
    
    public boolean esEntidad (int cod) {
        return (cod == codEnt);
    }
        
    public void mostrarEntidad () {
        System.out.println("Codigo de entidad: " + codEnt +
                           ", Domicilio: " + domEnt + 
                           ".");
    }
    
    public void listarSucursales () {
        if (sucursales.isEmpty()) {
            System.out.println("No hay sucursales ingresadas");
        }
        else {
            System.out.println("Sucursales:");
            for (Sucursal s : sucursales) {
                s.mostrarSucursal();
            }
        }   
    }
    
    public void serializar (String nom) throws IOException {
        ObjectOutputStream o = new ObjectOutputStream (new BufferedOutputStream (new FileOutputStream(nom)));
        o.writeObject(this);
        o.close();    
    }
    
    public Entidad deSerializar (String nom) throws IOException, ClassNotFoundException {
        ObjectInputStream o = new ObjectInputStream (new BufferedInputStream (new FileInputStream (nom)));        
        Entidad e = (Entidad) o.readObject();
        o.close();
        return e;
    }    
}