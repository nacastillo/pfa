/**
 *
 * @author Nicolas
 */

package pfa.modelo;

import java.util.*;

import static pfa.modelo.EntradaSalida.*;

import java.io.*;

public class ListaDeDetenidos implements Serializable {
    private List <Detenido> detenidos;
    
    public ListaDeDetenidos () {
        detenidos = new ArrayList <> ();    
    }
    
    public void agregarDetenido () {
        //pendiente de validacion
        detenidos.add(new Detenido (
                                   leerEntero("Ingrese el codigo del detenido: "),
                                   leerCadena("Ingrese el nombre del detenido: "),
                                   leerCadena("Ingrese el apellido del detenido: "),
                                   leerEntero("Ingrese el codigo de la banda a la que pertenece el detenido (de no corresponder, ingrese 0): "),
                                   leerFecha("Ingrese la fecha en la que fue detenido (formato DD/MM/AAAA): ")
                                    )
                    );        
    }
    
    public void listarDetenidos () {
        if (detenidos.isEmpty()) {
            mostrarMsjLn("No hay detenidos ingresados");
        }
        else {
            for (Detenido d : detenidos) {
                d.mostrarDetenido();            
            }        
        }
    }
    
    public void serializar (String nom) throws IOException {
        ObjectOutputStream o =
        new ObjectOutputStream (new BufferedOutputStream (new FileOutputStream (nom)));
        o.writeObject(this);
        o.close();
    }    
    
    public ListaDeDetenidos deSerializar (String nom) throws IOException, ClassNotFoundException {
        ObjectInputStream o =
        new ObjectInputStream (new BufferedInputStream (new FileInputStream (nom)));
        ListaDeDetenidos bd = (ListaDeDetenidos) o.readObject();
        o.close();
        return bd;    
    }
}
