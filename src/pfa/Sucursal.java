/**
 * 4to commit:
 * agregado mostrarSucursal()
 * eliminado getCodSuc() y getVigContratados()
 * modificado vigContratados a listaVig, de Vigilante a Integer
 */

package pfa;

import java.io.*;
import java.util.*;

public class Sucursal implements Serializable {
    private int codSuc;
    private String domSuc;
    private int cantEmpl;
    private List <Integer> listaVig;
    //private List <Vigilante> vigContratados;
    
    public Sucursal (int codSuc, String domSuc, int cantEmpl) {
        this.codSuc = codSuc;
        this.domSuc = domSuc;
        this.cantEmpl = cantEmpl;
        listaVig = new ArrayList <> ();
    }	
    
    public void contratarVig (Integer codV) {
        listaVig.add(codV);
    }
    
    public void sumarEmpl() {
        cantEmpl = cantEmpl + 1;
    }	
    
    public void mostrarSucursal () {
        System.out.println("Cod. Sucursal: " + codSuc + 
                           ", domicilio: " + domSuc +
                           ", cantidad de empleados: " + cantEmpl +
                           ", cantidad de vigilantes asignados: " + listaVig.size());
    }

    public void serializar (String nom) throws IOException {
        ObjectOutputStream o = new ObjectOutputStream (new BufferedOutputStream (new FileOutputStream (nom)));
        o.writeObject(this);
        o.close();
    }
    
    public Sucursal deSerializar (String nom) throws IOException, ClassNotFoundException {
        ObjectInputStream o = new ObjectInputStream (new BufferedInputStream (new FileInputStream (nom)));
        Sucursal s = (Sucursal) o.readObject();
        o.close();
        return s;
    }    

    
}
