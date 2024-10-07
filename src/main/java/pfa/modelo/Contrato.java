package pfa.modelo;

import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table (name = "contratos")

public class Contrato implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (nullable = false, unique = true, updatable = false, insertable = false)
    private Long id;
    
    @Column (nullable = false, unique = true)
    private Integer codigo;
    
    @Column (nullable = false)    
    private String fecha;    
        
    @Column
    private Long vigilante;    
    
    @Column    
    private Long sucursal;
    
    @Column (nullable = false)
    private Boolean armado;
       
}
