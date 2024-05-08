package pfa.modelo;

import lombok.Data;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Data
@Entity (name = "Investigador")
@DiscriminatorValue ("Investigador")

public class Investigador extends Usuario {    
    
    public Investigador (String usr, String pwd) {
        super(usr, pwd);        
    }
    
}