/**
 * @author Nicolas
 */

package pfa.modelo;

import java.util.*;

import static pfa.modelo.EntradaSalida.*;

import java.io.*;

public class BaseDeUsuarios implements Serializable {

    private List <Usuario> usuarios;
    
    public BaseDeUsuarios () {
        usuarios = new ArrayList <> ();
    }   
    
    /**
     * Agrega un Vigilante a la base de Usuarios con los datos
     * deseados a partir del constructor de Vigilante
     */
    public void agregarVigi () {
        int edad, cod;
        String pwd, 
               usr = leerCadena("Ingrese el nombre de usuario a crear: ");       
        while (existeUsr(usr)) {
            usr = leerCadena("Nombre de usuario ya registrado, por favor intente con otro.");            
        }
        pwd = leerCadena("Ingrese la clave: ");
        cod = leerEntero("Ingrese el codigo del vigilante: ");
        while (existeVig(cod)) {
            cod = leerEntero("Codigo ya registado, por favor intente con otro codigo.");
        }        
        edad = leerEntero("Ingrese la edad del vigilante: ");
        usuarios.add(new Vigilante (usr,pwd,cod,edad));
        return;        
    }
    
    /**
     * Agrega un Vigilante a la base de Usuarios con los datos
     * deseados a partir del constructor de Vigilante.
     */    
    public void agregarVigi2 () {
        int edad, 
            cod;
        String pwd, 
               usr = leerCadena("Ingrese el nombre de usuario a crear: ");       
        while (existeUsr(usr)) {
            usr = leerCadena("Nombre de usuario ya registrado, por favor intente con otro: ");            
        }
        cod = leerEntero("Ingrese el codigo del vigilante: ");
        while (existeVig(cod)) {
            cod = leerEntero("Codigo ya registado, por favor intente con otro codigo: ");
        }
        pwd = leerCadena("Ingrese la clave: ");
        edad = leerEntero("Ingrese la edad del vigilante: ");
        usuarios.add(new Vigilante (usr,pwd,cod,edad));
    }
       
    /**
     * Agrega un Investigador a la base de Usuarios con los
     * datos deseados a partir del constructor de Investigador.
     */
    public void agregarInves () {
        String pwd, 
               usr = leerCadena("Ingrese el nombre de usuario a crear: ");
        while (existeUsr(usr)) {
            usr = leerCadena("Nombre de usuario ya registrado, por favor intente con otro: ");            
        }
        pwd = leerCadena("Ingrese la clave: ");
        usuarios.add(new Investigador (usr,pwd));
    }

    /**
     * Agrega un Administrador a la base de Usuarios con los
     * datos deseados a partir del constructor de Administrador.
     */
    public void agregarAdmin () {
        String pwd, 
               usr = leerCadena("Ingrese el nombre de usuario a crear: ");
        while (existeUsr(usr)) {
            usr = leerCadena("Nombre de usuario ya registrado, por favor intente con otro: ");            
        }
        pwd = leerCadena("Ingrese la clave: ");
        usuarios.add(new Administrador(usr,pwd));    
    }
    
    /**
    * Agrega el admin por default a la base de usuarios
    * a partir del constructor de Administrador. 
    * Generalmente se usa cuando no se puede localizar el sistema serializado.
    */
    
    /*
    public void agregarUsuario () {
        char c = leerCadena("Ingrese el tipo de usuario a crear\n"
                    + "(V para Vigilante,"
                    + "I para investigador,"
                    + "A para administrador").charAt(0);
        
        switch(c) {
            case 'V':
            case 'v':
                    
        
        }
    }
    */
    
    public void defaultAdmin () {
        Usuario u = new Administrador("admin","admin");
        usuarios.add(u);    
    }    
    
    /**
     * Imprime la lista de usuarios, listándolos con su contraseña a la vista
     */

    public void listarUsuariosConClave () {
        System.out.println("Lista de usuarios:");
        for (Usuario u : usuarios) {
            u.mostrarUsuarioConClave();
        }          
    }  
    
    /**
     * Imprime la lista de usuarios, listándolos con su rol.
     */

    public void listarUsuariosConRol () {
        mostrarMsjLn("Lista de usuarios:");
        for (Usuario u : usuarios) {
            u.mostrarUsuarioConRol();
            //u.esDeRol();
        }   
    }
    
    /**
     * Busca un usuario dentro de la base de usuarios.
     * @param usr Es el nombre del usuario
     * @param pwd Es la contraseña del usuario
     * @return Devuelve una instancia de Usuario
     * @throws NullPointerException Si el usuario no se encuentra dentro de la base de usuarios.
     */

    public Usuario buscarUsuario (String usr, String pwd) throws NullPointerException {
        Usuario u = null;
        for (Usuario v : usuarios) {
            if (v.esUsr(usr) && v.esPwd(pwd) ) {
                u = v;
                break;
            }                
        }        
        return u;
    }
    /**
     * Utilizada para el login desde Sistema, trae el usuario a la espera
     * de que coincida la contraseña.
     * @param usr
     * @return Usuario
     * @throws NullPointerException 
     */
    public Usuario buscarUsuario (String usr) throws NullPointerException {
        Usuario u = null;
        for (Usuario v : usuarios) {
            if (v.esUsr(usr)) {
                u = v;
                break;
            }                
        }        
        return u;
    }

    /**
     * Busca un vigilante dentro de la base de usuarios.
     * @param usr Es el nombre del usuario del vigilante
     * @param pwd Es la contraseña del usuario del vigilante
     * @return Devuelve una instancia de Vigilante
     * @throws NullPointerException Si no encuentra al vigilante deseado dentro de la base de usuarios.
     */

    public Vigilante buscarVig (int cod) throws NullPointerException {
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
    
    public boolean existeVig (int cod) {
        boolean flag = false;
        Vigilante v = null;
        for (Usuario u : usuarios) {
            if (u.esVigi()) {
                v = (Vigilante) u;
                if (v.esCodVig(cod)) {
                    flag = true;
                    break;
                }                
            }        
        }
        return flag;  
    }
    
    public boolean existeUsr (String s) {
        boolean flag = false;
        for (Usuario u : usuarios) {
            if (u.esUsr(s)) {
                flag = true;
                break;
            }
        }        
        return flag;
    }
    
    /**
     * Lista los vigilantes ingresados mediante
     * el metodo toString
     */
    public void listarVigilantes () {
        for (Usuario u: usuarios) {
            if (u.esVigi()) {
                mostrarMsjLn(u.toString());
            }       
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
                if ((flag = v.esUsr(usr))) 
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
                if ((flag = v.esPwd(pwd)))
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
