/** @author Nicolas
 * Clase agregada en 4to commit
 */
package pfa.modelo;

import pfa.modelo.dto.Entidad;
import java.util.*;

import static pfa.modelo.EntradaSalida.*;

import java.io.*;

public class ListaDeEntidades implements Serializable {
    private List <Entidad> entidades;
    
    public ListaDeEntidades () {
        entidades = new ArrayList <> ();    
    }

    public void agregarEntidad () {
        try {
            Integer i = leerEntero ("Ingrese el ID del banco: ");
            Integer cod = leerEntero("Ingrese el codigo del banco: ");
            String dom = leerCadena("Ingrese el domicilio del banco: ");
            String n = leerCadena("Ingrese el nombre del banco: ");
            Entidad e = new Entidad(i.longValue(),cod,dom,n);
            mostrarMsjPf("Los datos a ingresar son:\n" +
                    "Codigo: %d\n" +
                    "Domicilio: %s.\n",cod,dom);
            entidades.add(e);
        }
        catch (Exception ex) {
            mostrarMsjLn("Ingrese un numero que sea correcto");
        }
    }
    
    public Entidad buscarEntidad () throws NullPointerException {
        int cod = leerEntero("Ingrese el numero de la entidad a buscar: ");
        Entidad e = null;
        for (Entidad f : entidades) {
            if (f.esEntidad(cod)) {
                e = f;
                break;      
            }       
        }
        return e;
    }
    
    public void agregarSucursalAEntidad () {
        try {
            Entidad e = buscarEntidad();
            e.agregarSucursal();
        }
        catch (NullPointerException ex) {
            mostrarMsjLn("No existe entidad con el código ingresado");
        }       
    }        

    
    public void listarEntidades () {
        if (entidades.isEmpty()) {
            mostrarMsjLn("No hay entidades registradas.");
        }
        else {
            mostrarMsjLn("Entidades:");
            for (Entidad e : entidades) {
                e.mostrarEntidad();       
            }
        }    
    }
    
    public void listarEntidadesConSucursales () {
        if (entidades.isEmpty()) {
            mostrarMsjLn("No hay entidades registradas");
        }
        else {
            mostrarMsjLn("Entidades:");
            for (Entidad e : entidades) {
                e.mostrarEntidad();
                e.listarSucursales();            
            }
        }
    }
}