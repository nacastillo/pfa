/**
 * agregado mostrarMsjLn (sout ln) y mostrarMsjPf (printf)
 */

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
        mostrarMsj(texto);
        int i = s.nextInt();
        return i;
    }
    
    public static int leerDia (String texto) {
        Scanner s = new Scanner(System.in);
        mostrarMsj(texto);
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
        mostrarMsj(texto);
        String cad = s.nextLine();
        return cad;
    }
    
    public static Fecha leerFecha (String texto) {
        int dia;
        int mes;
        int anio;
        Fecha f;
        StringTokenizer s;
        mostrarMsj(texto);
        String fechaTexto = leerCadena();
        s = new StringTokenizer(fechaTexto,"/");
        dia = Integer.parseInt(s.nextToken());
        mes = Integer.parseInt(s.nextToken());
        anio = Integer.parseInt(s.nextToken());
        f = new Fecha(dia,mes,anio);
        return f;
    }
    
    public static void mostrarMsjLn (String cad) {
        System.out.println(cad);
    }
    
    public static void mostrarMsjPf (String cad, Object ... args) {
        System.out.printf(cad, args);
    }    
    
    public static void mostrarMsj (String cad) {
        System.out.print(cad);
    }
}
