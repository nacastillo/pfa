package pfa;

import java.util.Scanner;
import java.util.StringTokenizer;

public class EntradaSalida {
    
    public static int leerEntero () {
        Scanner s = new Scanner(System.in);
        int i = s.nextInt();
        return i;
    }
    
    public static int leerEntero (String texto) {
        Scanner s = new Scanner(System.in);
        System.out.print(texto);
        int i = s.nextInt();
        return i;
    }
    
    public static int leerDia (String texto) {
        Scanner s = new Scanner(System.in);
        System.out.print(texto);
        int i = s.nextInt();
        return i;    
    }
    
    public static String leerCadena () {
        Scanner s = new Scanner(System.in);
        String cad = s.nextLine();
        return cad;
    }
    
    public static String leerCadena (String texto) {
        Scanner s = new Scanner(System.in);
        System.out.print(texto);
        String cad = s.nextLine();
        return cad;
    }
    
    public static Fecha leerFecha (String texto) {
        int dia;
        int mes;
        int anio;
        Fecha f;
        StringTokenizer s;
        System.out.print(texto);
        String fechaTexto = leerCadena();
        s = new StringTokenizer(fechaTexto,"/");
        dia = Integer.parseInt(s.nextToken());
        mes = Integer.parseInt(s.nextToken());
        anio = Integer.parseInt(s.nextToken());
        f = new Fecha(dia,mes,anio);
        return f;
    }
}
