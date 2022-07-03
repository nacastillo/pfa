/**
 * @author Nicolas
 */

package pfa;

import java.util.*;
import java.io.*;
import static pfa.EntradaSalida.*;

public class BaseDeUsuarios implements Serializable {

    private List <Usuario> usuarios;
        
    public BaseDeUsuarios () {
        usuarios = new ArrayList <> ();
    }
    
    public void agregarVigi () {
        String usr = leerCadena("Ingrese el usuario: ");
        String pwd = leerCadena("Ingrese la clave: ");
        int cod = leerEntero("Ingrese el codigo del vigilante: ");
        int edad = leerEntero("Ingrese la edad del vigilante: ");
        Usuario u = new Vigilante (usr,pwd,cod,edad);
        usuarios.add(u);    
    }
       
    public void agregarInves () {
        String usr = leerCadena("Ingrese el usuario: ");
        String pwd = leerCadena("Ingrese la clave: ");
        Usuario u = new Investigador (usr,pwd);
        usuarios.add(u);
    }

    public void agregarAdmin () {
        String usr = leerCadena("Ingrese el usuario: ");
        String pwd = leerCadena("Ingrese la clave: ");
        Usuario u = new Administrador(usr,pwd);
        usuarios.add(u);    
    }
    
    public void defaultAdmin () {
        Usuario u = new Administrador("admin","admin");
        usuarios.add(u);    
    }    
    
    public void listarUsuariosConClave () {
        System.out.println("Lista de usuarios:");
        for (Usuario u : usuarios) {
            u.mostrarUsuarioConClave();
        }          
    }  
    
    public void listarUsuariosConRol () {
        System.out.println("Lista de usuarios:");
        for (Usuario u : usuarios) {
            u.mostrarUsuarioConRol();
            //u.esDeRol();
        }   
    }
    
    public Usuario buscarUsuario (String usr, String pwd) throws NullPointerException {
        Usuario u = null;
        for (Usuario v : usuarios) {
            if (v.esUsername(usr) && v.esSuPass(pwd) ) {
                u = v;
            break;
            }                
        }        
        return u;
    }
    
    public Vigilante buscarVig (Integer cod) throws NullPointerException {
        boolean flag = false;
        Vigilante v = null;
        for (Usuario u: usuarios) {
            if (u.esVigi()) {
                v = (Vigilante) u;
                if (v.esCodVig(cod)) {
                    flag = true;
                    break;                
                }              
            }                
        }    
        if (flag) {
            return v;
        }
        else {
            return null;        
        }            
    }
        
    public Usuario login2 () {
        boolean flag = false;
        String usr; 
        String pwd;
        Usuario u;
        usr = leerCadena("Ingrese el usuario: ");
        while (!flag) {
            for (Usuario v: usuarios) {
                if ((flag = v.esUsername(usr))) 
                    break;
            }
            if (!flag) {
                usr = leerCadena("Por favor ingrese un usuario válido: ");
            }
        }
        flag = false;
        pwd = leerCadena("Ingrese la contraseña: ");
        while (!flag) {
            for (Usuario v: usuarios) {
                if ((flag = v.esSuPass(pwd)))
                    break;
            }
            if (!flag) {
                pwd = leerCadena("Por favor ingrese una contraseña válida: ");
            }
        }
        u = buscarUsuario (usr, pwd);        
        return u;
    }    
    
    public void serializar (String nom) throws IOException {   
        ObjectOutputStream o = new ObjectOutputStream (new BufferedOutputStream (new FileOutputStream (nom)));
        o.writeObject(this);
        o.close();
    }
    
    public BaseDeUsuarios deSerializar (String nom) throws IOException, ClassNotFoundException {
        ObjectInputStream o = new ObjectInputStream (new BufferedInputStream (new FileInputStream (nom)));
        BaseDeUsuarios b = (BaseDeUsuarios) o.readObject();
        o.close();
        return b;
    }
}
