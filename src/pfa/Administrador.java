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
            "10) prguntar rol del user " +
            "0) Salir");
            try {
                i = leerEntero();
            }
            catch (Exception ex) {
                System.out.println("Por favor, ingrese una opcion correcta");
                i = -1;
            }
            switch (i) {
                case 1 -> s.agregarEntidad();
                case 2 -> s.listarEntidades();
                case 3 -> s.aniadirVigilante();
                case 4 -> s.aniadirInves();
                case 5 -> s.aniadirAdmin();
                case 6 -> s.listarUsuarios();
                case 7 -> s.ingresarDetenido();
                case 8 -> s.listarDetenidos();
                case 9 -> {
                    Fecha f = leerFecha("Por favor, ingrese la fecha en formato DD/MM/AAAA");
                    f.mostrarFecha();
                }
            }            
        } while (i != 0);    
    }      
}
