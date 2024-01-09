/**
 * @author Nicolas
 * agregado 5to commit
 */
package pfa.modelo;

import pfa.modelo.dto.Juez;
import java.util.*;

import static pfa.modelo.EntradaSalida.*;

import java.io.*;

public class ListaDeJueces implements Serializable {

    private List <Juez> jueces; 
   
    public ListaDeJueces () {
       jueces = new ArrayList <> ();
    }
    
    public void agregarJuez () {
        jueces.add(new Juez());
    }
    
    /**
     * Busca un juez con el numero de clave interna. Arroja NPE por inicializacion
     * @return Juez, si lo encuentra
     * @throws NullPointerException 
     */
    public Juez buscarJuez () throws NullPointerException {
        int clave = leerEntero("Ingrese el codigo del juez a buscar");
        Juez j = null;
        for (Juez k : jueces) {
            if(k.esEsteJuez(clave)) {
                j = k;
                break;
            }
        }
        return j;
    } 
    
    public void listarJueces () {
        if (jueces.isEmpty()) {
            mostrarMsjLn("No hay jueces ingresados");
        }
        else {
            for (Juez j : jueces) {
                mostrarMsjLn("Lista de jueces:");
                j.mostrarJuez();            
            }
        }        
    }
    
    
    
    
}
