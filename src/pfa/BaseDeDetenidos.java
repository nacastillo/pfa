/**
 *
 * @author Nicolas
 */

package pfa;

import java.util.*;
import java.io.*;
import static pfa.EntradaSalida.*;

public class BaseDeDetenidos implements Serializable {
    private List <Detenido> detenidos;
    
    public BaseDeDetenidos () {
        detenidos = new ArrayList <> ();    
    }
    
    public void agregarDetenido () {
        int codigo = leerEntero("Ingrese el codigo del detenido: ");
        String nombre = leerCadena("Ingrese el nombre del detenido: ");
        String apellido = leerCadena("Ingrese el apellido del detenido: ");
        int banda = leerEntero("Ingrese el codigo de la banda a la que pertenece el detenido (de no corresponder, ingrese 0): ");
        Fecha fecha = leerFecha("Ingrese la fecha en la que fue detenido (formato DD/MM/AAAA): ");
        Detenido d = new Detenido (codigo, apellido, nombre, banda, fecha);
        detenidos.add(d);   
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
    
    public BaseDeDetenidos deSerializar (String nom) throws IOException, ClassNotFoundException {
        ObjectInputStream o =
        new ObjectInputStream (new BufferedInputStream (new FileInputStream (nom)));
        BaseDeDetenidos bd = (BaseDeDetenidos) o.readObject();
        o.close();
        return bd;    
    }
}
