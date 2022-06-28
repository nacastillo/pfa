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
            "8) Consultar delitos ingresados\n\n\n" +
            "0) Salir");
            i = leerEntero();
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8: System.out.println("A implementar");
                        break;
            }       
        } while (i != 0);
    }   
    
    @Override
    public void serializar (String nom) throws IOException {
        ObjectOutputStream o = 
        new ObjectOutputStream (new BufferedOutputStream (new FileOutputStream (nom)));
        o.writeObject(this);
        o.close();
    }  
    
    public Investigador deSerializar (String nom) throws IOException, ClassNotFoundException {
        ObjectInputStream o = 
        new ObjectInputStream (new BufferedInputStream (new FileInputStream (nom)));
        Investigador i = (Investigador) o.readObject();
        o.close();
        return i;
    }
}
