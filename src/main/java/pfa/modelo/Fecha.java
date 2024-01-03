package pfa.modelo;

import static pfa.modelo.EntradaSalida.*;

import java.io.*;
import java.util.StringTokenizer;

public class Fecha implements Serializable {
    private static int[] cantDiasXMes = {31,28,31,30,31,30,31,31,30,31,30,31};
    
    private int dia;
    private int mes;
    private int anio;
		
    public Fecha (int dia, int mes, int anio) {        
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;		
    }
    
    public Fecha () {
        String f = leerCadena("Ingrese la fecha en formato DD/MM/AAAA: ");
        StringTokenizer s = new StringTokenizer(f,"/");
        int d = Integer.parseInt(s.nextToken());
        int m = Integer.parseInt(s.nextToken());
        int a = Integer.parseInt(s.nextToken());
        if (validarFecha(d,m,a)) {
            dia = d;
            mes = m;
            anio = a;
        }
        else {
            mostrarMsjLn("Por favor, ingrese una fecha válida");            
        }    
    }

    public static boolean validarFecha (int d, int m, int a) {
        if (a % 4 == 0) {
            cantDiasXMes[1] = 29;
        } 
        return 0 < d && d <= cantDiasXMes[m-1] && m-1 < 12;        
    }   
    
    public void mostrarFecha () {
        mostrarMsjLn(dia + "/" + mes + "/" + anio);
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