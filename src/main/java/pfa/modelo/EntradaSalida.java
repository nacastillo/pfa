/**
 * agregado mostrarMsjLn (sout ln) y mostrarMsjPf (printf)
 */

package pfa.modelo;

import java.util.Scanner;
import java.util.StringTokenizer;

public class EntradaSalida {
    
    private static Scanner s = new Scanner(System.in);
    
    private EntradaSalida () {    
    }    
    
    /**
     * Lee una cadena ingresada por teclado
     * @return String La cadena ingresada
     */
    public static String leerCadena () {
        return s.nextLine();
    }
    
    /**
     * Lee una cadena ingresada por teclado, mostrando
     * previamente un mensaje por pantalla
     * @param mensaje El mensaje a mostrar
     * @return String La cadena ingresada
     */
    public static String leerCadena (String mensaje) {
        mostrarMsj(mensaje);
        return s.nextLine();
    }
    
    public static int leerEntero () {
        //Scanner s = new Scanner(System.in);
        //int i = s.nextInt();
        //return i;
        //return s.nextInt();
        return Integer.parseInt(leerCadena());
    }
    
    /**
     * Lee un numero entero desde la consola, mostrando antes un mensaje por consola
     * @param mensaje El mensaje a mostrar
     * @return int
     */
    public static int leerEntero (String mensaje) {
        //Scanner s = new Scanner(System.in);
        mostrarMsj(mensaje);
        //int i = s.nextInt();
        //return i;
        return Integer.parseInt(s.nextLine());
    }
    
    public static int leerEntero2 (String texto) {
        int i = -1;
        boolean flag = true;
        //Scanner s = new Scanner(System.in);
        mostrarMsj(texto);
        do {
            try {
                i = s.nextInt();
                flag = false;
            }
            catch (Exception ex) {
                mostrarMsj("Por favor, ingrese un numero entero: ");
                i = s.nextInt();
            }
        } while (flag);
        return i;
    }
    
    public static int leerEntero3 (String texto) {
        int i = 0;
        //Scanner s = new Scanner(System.in);
        mostrarMsj(texto);
        try {
            i = s.nextInt();
        }
        catch (Exception ex) {
            mostrarMsjLn("Por favor, la proxima vez ingrese un numero entero");
        }
        return i;
    }
    
    public static int leerDia (String texto) {
        //Scanner s = new Scanner(System.in);
        mostrarMsj(texto);
        int i = s.nextInt();
        return i;    
    }
        
    public static Fecha leerFecha (String texto) {
        int dia,
            mes,
            anio;
        StringTokenizer s;
        mostrarMsj(texto);
        String fechaTexto = leerCadena();
        s = new StringTokenizer(fechaTexto,"/");
        dia = Integer.parseInt(s.nextToken());
        mes = Integer.parseInt(s.nextToken());
        anio = Integer.parseInt(s.nextToken());
        return (new Fecha(dia,mes,anio));        
    }
    
    /**
     * Muestra por pantalla una cadena seguida de un salto de linea
     * @param cad La cadena a mostrar 
     */
    public static void mostrarMsjLn (String cad) {
        System.out.println(cad);
    }
    
    /**
     * Muestra por pantalla una cadena, formateada en estilo C
     * @param cad La cadena a mostrar
     * @param args Especificadores de formato correspondientes
     */
    public static void mostrarMsjPf (String cad, Object ... args) {
        System.out.printf(cad, args);
    }    
    
    /**
     * Muestra por pantalla una cadena, SIN salto de linea al final
     * @param cad La cadena a mostrar 
     */
    public static void mostrarMsj (String cad) {
        System.out.print(cad);
    }
}
