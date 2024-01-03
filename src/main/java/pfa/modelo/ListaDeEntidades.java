/** @author Nicolas
 * Clase agregada en 4to commit
 */
package pfa.modelo;

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
            int cod = leerEntero("Ingrese el codigo de la entidad: ");
            String dom = leerCadena("Ingrese el domicilio de la entidad: ");
            Entidad e = new Entidad(cod,dom);
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