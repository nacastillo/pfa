/** @author Nicolas
 * clase agregada en 4to commit
*/
package pfa.modelo;

import pfa.modelo.dto.Asalto;
import java.util.*;

import static pfa.modelo.EntradaSalida.*;

import java.io.*;
import java.time.LocalDate;
import pfa.modelo.dto.Detenido;
import pfa.modelo.dto.Juez;
import pfa.modelo.dto.Sucursal;

public class ListaDeDelitos implements Serializable {
    
    private List <Asalto> delitos;
    
    public ListaDeDelitos () {
        delitos = new ArrayList <> ();    
    }
    
    /**
     * Asigna una condena expresada en un int.
     * @throws NullPointerException
     */    
    public void asignarCondena(){ // comentadas las lineas de setCondena
        try {
            // int cod = leerEntero("Ingrese el codigo del delito a buscar");
            Asalto d = buscarDelito();
            int dias = leerEntero("Ingrese la cantidad de dias de condena: ");
            //d.setCondena(true);
            //d.setDiasCondena(dias);
        }
        catch (NullPointerException ex) {
            mostrarMsjLn("No se encuentra el delito con el codigo ingresado");
        }        
        ;
    }
    /**
     * Busca un detenido en la Lista de Detenidos
     * @return Asalto
     * @throws NullPointerException
     */
    public Asalto buscarDelito () throws NullPointerException {
        Asalto d = null;
        int cod = leerEntero("Ingrese el numero del delito a buscar: ");                
        for (Asalto e : delitos) {
            if (cod == e.getCodigo()) {
                d = e;
                break;            
            }
        }    
        return d;
    }
    
    public void addDelito (Long i, Integer c, LocalDate fa, Sucursal sa, 
            Juez j, Detenido d, LocalDate dc) {
        delitos.add(new Asalto (i,c,fa,sa,j,d,dc));        
    }
    
    public void tieneCondena () {
    }    
}
