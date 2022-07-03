package pfa;

import static pfa.EntradaSalida.*;

import java.io.*;

public class Investigador extends Usuario implements Serializable {
       
    public Investigador (String user, String pass) {
        setUser(user);
        setPass(pass);
    }
    
    @Override
    public void menu (Sistema s) {
        int i;
        do {
            System.out.println("Seleccione una opcion\n" +
            "1) Consultar sucursales asignadas a los vigilantes\n" +
            "2) Consultar fechas asignadas a los vigilantes\n" +
            "3) Consultar si los vigilantes tienen armas asignadas\n" +
            "4) Consultar datos personales de los vigilantes\n" +
            "5) Consultar bandas ingresadas\n" +
            "6) Consultar jueces ingresados\n" +
            "7) Consultar detenidos ingresados\n" +
            "8) Consultar delitos ingresados\n" +
            "9) esVigi\n" +
            "10) esInves\n" +
            "11) esAdmin\n" +                    
            "\n\n" +
            "0) Salir");
            try {
                i = leerEntero();
            }
            catch (Exception ex) {
                System.out.println("Por favor ingrese una opcion correcta");
                i = -1;
            }               
            switch (i) {
                case 1, 2, 3, 4, 5, 6, 7, 8 -> System.out.println("A implementar");
                case 9 ->  System.out.println(esVigi());
                case 10 -> System.out.println(esInves());
                case 11 -> System.out.println(esAdmin());
            }       
        } while (i != 0);
    }   
    /**
     * removido serializar y deserializar (ya implementado en Usuario)
     * @param nom
     * @throws IOException 
     */    
}