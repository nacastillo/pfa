package pfa.modelo;

import pfa.modelo.dto.Detenido;
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
        Integer i = leerEntero("Ingrese el ID del detenido: ");
        detenidos.add(new Detenido (
                                   i.longValue(),
                                   leerEntero("Ingrese el codigo del detenido: "),
                                   leerCadena("Ingrese el nombre del detenido: "),
                                   null)
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
