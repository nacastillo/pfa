package pfa;

import java.io.*;

public class Banda implements Serializable {
    private int numBanda;
    private int numMiemb;
	
    public Banda (int numBanda, int numMiemb) {
        this.numBanda = numBanda;
	this.numMiemb = numMiemb;			
    }
    
    public Banda (int numBanda) {
        this.numBanda = numBanda;
        numMiemb = 0;    
    }

    public int getNumBanda() {
        return numBanda;
    }

    public int getNumMiemb() {
        return numMiemb;
    }

    public void setNumBanda(int numBanda) {
        this.numBanda = numBanda;
    }

    public void setNumMiemb(int numMiemb) {
        this.numMiemb = numMiemb;
    }

    public void serializar (String nom) throws IOException {
        ObjectOutputStream o = 
        new ObjectOutputStream (new BufferedOutputStream (new FileOutputStream (nom)));
        o.writeObject(this);
        o.close();    
    }
    
}
