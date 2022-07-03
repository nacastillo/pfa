package pfa;

import static pfa.EntradaSalida.*;

public class Administrador extends Usuario {
    
    public Administrador (String user, String pass) {
        setUser(user);
        setPass(pass);    
    }
    
    @Override
    public void menu (Sistema s) {
        int i,j;
        do {
            System.out.println("Seleccione una opción\n" +
            "1) Agregar entidad\n" +
            "2) Listar entidades\n" +
            "3) Agregar vigilante\n" +        
            "4) Agregar investigador\n" +
            "5) Agregar administrador\n" +
            "6) Listar usuarios con clave\n" +
            "7) Listar usuarios con rol\n" +
            "8) Agregar detenido\n" +
            "9) Listar detenidos\n" +
            "10) Leer fecha\n" +
            "11) Buscar vigilante por codigo\n" +
            "12) Agregar sucursal a entidad\n" +
            "13) Listar entidades con sucursales\n" +        
            "\n\n" +
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
                case 3 -> s.agregarVigi();
                case 4 -> s.agregarInves();
                case 5 -> s.agregarAdmin();
                case 6 -> s.listarUsuariosConClave();
                case 7 -> s.listarUsuariosConRol();
                case 8 -> s.agregarDetenido();
                case 9 -> s.listarDetenidos();
                case 10 -> {
                    Fecha f = leerFecha("Por favor, ingrese la fecha en formato DD/MM/AAAA");
                    f.mostrarFecha();
                }                
                case 11 -> {
                    try {
                        j = leerEntero("Ingrese un codigo de vigilante: ");
                        Vigilante v = s.buscarVig(j);                    
                        v.mostrarUsuario();
                    }
                    catch (NullPointerException ex) {
                        System.out.println("No se encontró vigilante con ese codigo");
                    }                
                }
                case 12 -> s.agregarSucursalAEntidad();
                case 13 -> s.listarEntidadesCompleto();
                
           }            
        } while (i != 0);    
    }      
}
