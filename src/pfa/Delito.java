package pfa;

import java.io.*;

public class Delito implements Serializable {
    private Detenido imputado;
    private Juez juezAsignado;
    private Sucursal sucursalAsaltada;
    private boolean condena;
    private int diasCondena;
		
    public Delito (Detenido imputado, Juez juezAsignado, Sucursal sucursalAsaltada) {
        this.imputado = imputado;
	this.juezAsignado = juezAsignado;			
	this.sucursalAsaltada = sucursalAsaltada;
        diasCondena = 0;
	condena = false;
    }

    public Detenido getImputado() {
        return imputado;
    }

    public Juez getJuezAsignado() {
        return juezAsignado;
    }

    public Sucursal getSucursalAsaltada() {
        return sucursalAsaltada;
    }
    
    public int getDiasCondena () {
        return diasCondena;        
    }
    
    public boolean getCondena () {
        return condena;
    }

    public void setImputado(Detenido imputado) {
        this.imputado = imputado;
    }

    public void setJuezAsignado(Juez juezAsignado) {
        this.juezAsignado = juezAsignado;
    }

    public void setSucursalAsaltada(Sucursal sucursalAsaltada) {
        this.sucursalAsaltada = sucursalAsaltada;
    }
    
    public void setCondena (boolean condena) {
        this.condena = condena;
    }
    
    public void setDiasCondena (int diasCondena) {
        this.diasCondena = diasCondena;    
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