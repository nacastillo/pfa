package pfa;

import java.io.*;

public class Fecha implements Serializable {
    private int dia;
    private int mes;
    private int anio;
		
    public Fecha (int dia, int mes, int anio) {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;		
    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getAnio() {
        return anio;
    }
    
    
    
    public void mostrarFecha () {
        System.out.println(dia + "/" + mes + "/" + anio);
    }
    
    public void serializar (String nom) throws IOException {
        ObjectOutputStream o =
        new ObjectOutputStream (new BufferedOutputStream (new FileOutputStream (nom)));
        o.writeObject(this);
        o.close();
    }
    
    public Fecha deSerializar (String nom) throws IOException, ClassNotFoundException {
        ObjectInputStream o =
        new ObjectInputStream (new BufferedInputStream (new FileInputStream (nom)));
        Fecha f = (Fecha) o.readObject();
        o.close();
        return f;
    }
    
}