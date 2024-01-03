package pfa.modelo;

import static pfa.modelo.EntradaSalida.*;

import java.io.*;

public abstract class Usuario implements Serializable {
    private String user;
    private String pass;
    
    public void setUser (String u) {
        user = u;
    }
		
    public void setPass (String p) {
        pass = p;
    }
    
    public String getUser () {
        return user;
    }
    
    public void mostrarUsuarioConClave () {
        mostrarMsjLn("Usuario: " + user + ", clave: " + pass);
    }
    
    public void serializar (String nom) throws IOException {
        ObjectOutputStream o =
        new ObjectOutputStream (new BufferedOutputStream (new FileOutputStream (nom)));
        o.writeObject(this);
        o.close();    
    }
    
    public Usuario deSerializar (String nom) throws IOException, ClassNotFoundException {
        ObjectInputStream o =
        new ObjectInputStream (new BufferedInputStream (new FileInputStream (nom)));
        Usuario u = (Usuario) o.readObject();
        o.close();
        return u;    
    }    
    
    public boolean esUsr (String usr) {
        return user.equals(usr);
    }
    
    public boolean esPwd (String pwd) {
        return pass.equals(pwd);
    }   
    
    public String esDeRol() {
        return this.getClass().getSimpleName();
    }
    
    public void mostrarUsuarioConRol () {
        mostrarMsjLn("Usuario: " + user + ", rol: "  + esDeRol());
    }
    
    public void mostrarUsuario () {
        mostrarMsjLn(user);    
    }
    
    public boolean esVigi () {
        return this.getClass().getSimpleName().equals("Vigilante");
    }
    
    public boolean esInves () {
        return this.getClass().getSimpleName().equals("Investigador");
    }
    
    public boolean esAdmin () {
        return this.getClass().getSimpleName().equals("Administrador");
    }     
        
    public abstract int menuPrincipal(Sistema s);
}