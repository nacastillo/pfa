package pfa.modelo;

import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import java.io.Serializable;

@Data
@Entity
@Table (name = "usuarios")
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn (name = "rol")

public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (nullable = false, unique = true, updatable = false, insertable = false)
    private Long id;

    @Column (nullable = false, unique = true, length = 64)
    private String usr;

    @Column (nullable = false, length = 64)
    private String pwd;

    @Column (insertable = false, updatable = false)
    private String rol;
    
    public Usuario () {
    }
    
    public Usuario (String usr, String pwd) {
        this.usr = usr;
        this.pwd = pwd;        
    }    
    
}