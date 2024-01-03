/** @author Nicolas
 * clase agregada en 4to commit
*/
package pfa.modelo;

import java.util.*;

import static pfa.modelo.EntradaSalida.*;

import java.io.*;

public class ListaDeDelitos implements Serializable {
    
    private List <Delito> delitos;
    
    public ListaDeDelitos () {
        delitos = new ArrayList <> ();    
    }
    
    /**
     * Asigna una condena expresada en un int.
     * @throws NullPointerException
     */    
    public void asignarCondena(){
        try {
            // int cod = leerEntero("Ingrese el codigo del delito a buscar");
            Delito d = buscarDelito();
            int dias = leerEntero("Ingrese la cantidad de dias de condena: ");
            d.setCondena(true);
            d.setDiasCondena(dias);
        }
        catch (NullPointerException ex) {
            mostrarMsjLn("No se encuentra el delito con el codigo ingresado");
        }        
        ;
    }
    /**
     * Busca un detenido en la Lista de Detenidos
     * @return Delito
     * @throws NullPointerException
     */
    public Delito buscarDelito () throws NullPointerException {
        int cod = leerEntero("Ingrese el numero del delito a buscar: ");        
        Delito d = null;
        for (Delito e : delitos) {
            if (e.esDelito(cod)) {
                d = e;
                break;            
            }
        }    
        return d;
    }
    
    public void addDelito (int i, int j, int e, int s, Fecha f) {
        delitos.add(new Delito(i,j,e,s,f));
    }
    
    public void tieneCondena () {
    }    
}
