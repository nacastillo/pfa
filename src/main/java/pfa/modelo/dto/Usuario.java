package pfa.modelo.dto;

import pfa.modelo.Sistema;
import static pfa.modelo.EntradaSalida.*;

import java.io.*;

public abstract class Usuario implements Serializable {
    private Long id;
    private String usr;
    private String pwd;
    private String rol;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }    
    
    public void setUsr (String u) {
        usr = u;
    }
		
    public void setPwd (String p) {
        pwd = p;
    }
    
    public String getUsr () {
        return usr;
    }
    
    public void mostrarUsuarioConClave () {
        mostrarMsjLn("Usuario: " + usr + ", clave: " + pwd);
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
        return this.usr.equals(usr);
    }
    
    public boolean esPwd (String pwd) {
        return this.pwd.equals(pwd);
    }   
    
    public String esDeRol() {
        return this.getClass().getSimpleName();
    }
    
    public void mostrarUsuarioConRol () {
        mostrarMsjLn("Usuario: " + usr + ", rol: "  + esDeRol());
    }
    
    public void mostrarUsuario () {
        mostrarMsjLn(usr);    
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