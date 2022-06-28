package pfa;

import java.io.*;
import java.util.ArrayList;

public class Entidad implements Serializable {
    private final int codEnt;
    private final String domEnt;
    private ArrayList <Sucursal> Sucursales;

    public Entidad (int codEnt, String domEnt) {
            this.codEnt = codEnt;
            this.domEnt = domEnt;
            Sucursales = new ArrayList <> ();
    }

    public int getCodEnt() {
        return codEnt;
    }

    public void agregarSucursal (int c, String d) {
        Sucursal s = new Sucursal(c,d);
        Sucursales.add(s);			
    }
    
    public ArrayList <Sucursal> getSucursales () {
        return Sucursales;
    }
    
    public void mostrarEntidad () {
        System.out.println("Codigo de entidad: " + codEnt + ", Domicilio: " + domEnt + ".");
    }
    
    public void serializar (String nom) throws IOException {
        ObjectOutputStream o = new ObjectOutputStream (new BufferedOutputStream (new FileOutputStream(nom)));
        o.writeObject(this);
        o.close();    
    }
    
    public Entidad deSerializar (String nom) throws IOException, ClassNotFoundException {
        ObjectInputStream o = new ObjectInputStream (new BufferedInputStream (new FileInputStream (nom)));        
        Entidad e = (Entidad) o.readObject();
        o.close();
        return e;
    }
    
}