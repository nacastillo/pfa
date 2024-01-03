package pfa.modelo;

import static pfa.modelo.EntradaSalida.*;

import java.io.*;

public class Sucursal implements Serializable {
    private int codSuc,
                cantEmpl;
    private String domSuc;    
        
    public Sucursal (int codSuc, String domSuc, int cantEmpl) {
        this.codSuc = codSuc;
        this.domSuc = domSuc;
        this.cantEmpl = cantEmpl;        
    }	 
    
    public void sumarEmpl() {
        cantEmpl++;
    }	
    
    public void mostrarSucursal () {
        mostrarMsjLn("Cod. Sucursal: " + codSuc + 
                     ", domicilio: " + domSuc +
                     ", cantidad de empleados: " + cantEmpl
                    );
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
