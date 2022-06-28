package pfa;

import static pfa.EntradaSalida.*;

public class Administrador extends Usuario {
    
    public Administrador (String user, String pass) {
        setUser(user);
        setPass(pass);    
    }
    
    @Override
    public void menu (Sistema s) {
        int i;
        do {
            System.out.println("Seleccione una opción\n" +
            "1) Agregar entidad\n" +
            "2) Listar entidades\n" +
            "3) Agregar vigilante\n" +        
            "4) Agregar investigador\n" +
            "5) Agregar administrador\n" +
            "6) Listar usuarios\n" +
            "7) Agregar detenido\n" +
            "8) Listar detenidos\n" +
            "9) Leer fecha\n" +
            "0) Salir");
            i = leerEntero();
            switch (i) {
                case 1: s.agregarEntidad();
                        break;
                case 2: s.listarEntidades();
                        break;
                case 3: s.aniadirVigilante();
                        break;
                case 4: s.aniadirInves();
                        break;
                case 5: s.aniadirAdmin();
                        break;
                case 6: s.listarUsuarios();
                        break;
                case 7: s.ingresarDetenido();
                        break;
                case 8: s.listarDetenidos();
                        break;
                case 9: Fecha f = leerFecha("Por favor, ingrese la fecha en formato DD/MM/AAAA");
                        f.mostrarFecha();
                        break;
            }            
        } while (i != 0);    
    }      
}
