package pfa.modelo.dto;

import pfa.modelo.Sistema;
import pfa.modelo.dto.Usuario;
import java.io.IOException;
import pfa.modelo.Fecha;

import static pfa.modelo.Constantes.cadenaMenuAdmin;
import static pfa.modelo.Constantes.cadenaMenuGestionUsuarios;
import static pfa.modelo.Constantes.menuPrincipalAdmin;
import static pfa.modelo.EntradaSalida.*;

public class Administrador extends Usuario {
    
    public Administrador (String user, String pass) {
        setUsr(user);
        setPwd(pass);    
    }    
    
    public void menu (Sistema s) {
        int i,j;
        do {
            mostrarMsjLn(cadenaMenuAdmin);
            try {
                i = leerEntero();
            }
            catch (Exception ex) {
                System.out.println("Por favor, ingrese una opcion correcta");
                i = -1;
            }
            switch (i) {
                case 0 -> System.exit(0);
                case 1 -> s.agregarEntidad();
                case 2 -> s.listarEntidades();
                case 3 -> s.agregarVigi2();
                case 4 -> s.agregarInves();
                case 5 -> s.agregarAdmin();
                case 6 -> s.listarUsuariosConClave();
                case 7 -> s.listarUsuariosConRol();
                // case 8 -> s.agregarDetenido();
                // case 9 -> s.listarDetenidos();
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
                case 14 -> s.agregarDelito();
                case 15 -> s.listarVigilantes();
                
                
                
           }            
        } while (i != 0);    
    }  

    public int menuPrincipal (Sistema s) {
        int op = -1;
        do {
            mostrarMsjLn(menuPrincipalAdmin);
            try {
                op = leerEntero();
            } 
            catch (Exception ex) {
                mostrarMsjLn("Por favor, ingrese una opcion correcta");

            }    
            switch (op) {
                
                case 0:                        
                        break;                
                case 1:
                        menuGestionUsuarios(s);
                        break;
                case 2:
                        menuGestionEntidades();
                        break;
                case 3:
                        menuGestionSucursales();
                        break;                     
                case 4:
                        menuGestionDetenidos(s);
                        break;
                case 9: 
                        try {
                            s.serializar("sistema.bin");
                        }
                        catch (IOException ex) {
                            mostrarMsjLn("Error al grabar sistema.");                            
                        }
                            System.exit(0);
                        break;
                default:
                        mostrarMsjLn("Por favor, ingrese una opcion valida.");
                        break;
            }
        } while (1 <= op && op <= 4);
        return op;
    }
    
    public int menuGestionUsuarios (Sistema s) {
        int op = -1;
        do {
            mostrarMsjLn(cadenaMenuGestionUsuarios);
            try {
                op = leerEntero();
            }            
            catch (Exception ex) {
                mostrarMsjLn("Ingrese una opcion correcta.");
            }            
            switch (op) {
                case 1 -> s.agregarVigi2();
                case 2 -> s.agregarInves();
                case 3 -> s.agregarAdmin();
                case 4 -> s.listarUsuariosConRol();
                case 5 -> s.listarUsuariosConClave();
                case 6 -> mostrarMsjLn("Pendiente de implementacion.");
                case 7 -> {
                    try {
                        Vigilante v = s.buscarVig(leerEntero("Ingrese un codigo de vigilante: "));
                        mostrarMsjLn(v.toString());
                    }
                    catch (NullPointerException ex) {
                        mostrarMsjLn("No se encontró vigilante con ese código");
                    }                
                }
                case 8 -> s.listarVigilantes();
                case 0 -> {
                }
                default -> mostrarMsjLn("Por favor, ingrese una opcion valida.");
            }
        } while (op != 0);
        return op;
    }

    public void menuGestionEntidades() {
        mostrarMsjLn("A implementar");
    };
    public void menuGestionSucursales() {
        mostrarMsjLn("A implementar");
    };    
    
    public void menuGestionDetenidos(Sistema s) {
        int op;
        do {
            mostrarMsjLn(
                        "MENU DE GESTION DE DETENIDOS:\n\n" +
                        "<1>\tAgregar vigilante \n" +
                        "<2>\tListar detenidos \n" +
                        "\n" +
                        "<0>\tVolver al menú principal"        
                        );

            op = leerEntero("");
            switch (op) {
                case 1:
                //        s.agregarDetenido();
                        break;
                case 2:
                        //s.listarDetenidos();
                        break;
                
                default:
                        mostrarMsjLn("Ingrese una opcion correcta");

            } 
        } while (op != 0);
    }
}