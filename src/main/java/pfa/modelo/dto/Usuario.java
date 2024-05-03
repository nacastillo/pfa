package pfa.modelo.dto;

import pfa.modelo.Sistema;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import java.io.*;

import lombok.Data;
import static pfa.modelo.EntradaSalida.*;

@Data
@Entity
@Table (name = "usuarios")
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)


// public abstract class Usuario implements Serializable {
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "id")
    private Long id;

    @Column (name = "usr")
    private String usr;

    @Column (name = "pwd")
    private String pwd;

    @Column (name = "rol")
    private String rol;

    public void mostrarUsuarioConClave () {
        mostrarMsjLn("Usuario: " + usr + ", clave: " + pwd);
    }
    
    /*
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

    */
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