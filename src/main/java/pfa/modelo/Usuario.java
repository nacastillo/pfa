package pfa.modelo;

import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Data
@Entity
@Table (name = "usuarios")
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)

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
    
    public boolean esUsr (String usr) {
        return this.usr.equals(usr);
    }
    
    public boolean esPwd (String pwd) {
        return this.pwd.equals(pwd);
    }   
    
    public String esDeRol() {
        return this.getClass().getSimpleName();
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

}