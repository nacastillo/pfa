/**
 * modificada la implementacion de delito, no esta mas compuesta por
 * detenido, juez y sucursal, sino que usa integers que relacionen las clases
 * se borran los getters y setters
 */

package pfa.modelo;

import java.io.*;
            
public class Delito implements Serializable {
    private int codImputado,
                codJuezAsignado,
                codEntidadAsaltada,
                codSucursalAsaltada,
                diasCondena;
    private boolean condena;
    private Fecha fechaDelito;
		
    public Delito (int codImputado, int codJuezAsignado, int codEntidadAsaltada, int codSucursalAsaltada, Fecha fechaDelito) {
        this.codImputado = codImputado;
        this.codJuezAsignado = codJuezAsignado;
        this.codEntidadAsaltada = codEntidadAsaltada;
        this.codSucursalAsaltada = codSucursalAsaltada;
        this.fechaDelito = fechaDelito;
        diasCondena = 0;
	condena = false;
    }   

    public void setCondena(boolean condena) {
        this.condena = condena;
    }

    public void setDiasCondena(int diasCondena) {
        this.diasCondena = diasCondena;
    }
    
    public Integer suCondenaNO () {
        return diasCondena;       
    }
    
    public String tieneCondena () {
        if (diasCondena != 0)                
            return ("La condena del imputado " + codImputado + 
                    "por asaltar la sucursal " + codSucursalAsaltada + 
                    "es de " + diasCondena + 
                    "días.");
        else
            return "No tiene condena.";
    }
    
    public boolean esDelito (int cod) {
        return codImputado == cod ;
    }    
    
    public void serializar (String nom) throws IOException {
        ObjectOutputStream o =
        new ObjectOutputStream (new BufferedOutputStream (new FileOutputStream (nom)));
        o.writeObject(this);
        o.close();
    }
    
    public Delito deSerializar (String nom) throws IOException , ClassNotFoundException {
        ObjectInputStream o =
        new ObjectInputStream (new BufferedInputStream (new FileInputStream (nom)));
        Delito d = (Delito) o.readObject();
        o.close();
        return d;
    }    
       
}