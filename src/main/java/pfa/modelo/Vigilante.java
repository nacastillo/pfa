package pfa.modelo;

import jakarta.persistence.Column;
import lombok.Data;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity (name = "Vigilante")
@DiscriminatorValue (value = "Vigilante")

public class Vigilante extends Usuario {    
    
    @Column (unique = true)
    private Integer codigo;
    
    @Column 
    private Integer edad;    
    
    @OneToMany (mappedBy = "vigilante", fetch = FetchType.EAGER)
    private List <Contrato> contratos;
    
    public Vigilante () {
    }
    
    public Vigilante (String usr, String pwd, Integer codigo, Integer edad) {
        super(usr, pwd);
        this.codigo = codigo;
        this.edad = edad;
        this.contratos = new ArrayList <> ();
    }    
}