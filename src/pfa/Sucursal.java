package pfa;

import java.io.*;
import java.util.ArrayList;

public class Sucursal implements Serializable {
    private int codSuc;
    private String domSuc;
    private int cantEmpl;
    //private ArrayList <Integer> vigContratados;
    private ArrayList <Vigilante> vigContratados;
    
    public Sucursal (int codSuc, String domSuc) {
        this.codSuc = codSuc;
        this.domSuc = domSuc;
        cantEmpl = 0;
        vigContratados = new ArrayList <> ();
    }	
    
    /*public ArrayList <Integer> getVigContratados () {
        return vigContratados;
    }*/

    public int getCodSuc() {
        return codSuc;
    }

    public ArrayList<Vigilante> getVigContratados() {
        return vigContratados;
    }

    public void contratarVig (Vigilante v) {
        vigContratados.add(v);
    }
    
    public void sumarEmpl() {
        cantEmpl = cantEmpl + 1;
    }	

    /*public void contratarVig (int codVig) {
        Integer i = codVig;
        vigContratados.add(i);
    }*/
    
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


