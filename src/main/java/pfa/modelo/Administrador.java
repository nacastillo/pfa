package pfa.modelo;

import lombok.Data;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Data
@Entity (name = "Administrador")
@DiscriminatorValue ("Administrador")

public class Administrador extends Usuario {    
    
    public Administrador () {
    }
    
    public Administrador (String usr, String pwd) {
        super(usr, pwd);        
    }
    
}