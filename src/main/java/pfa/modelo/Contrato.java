package pfa.modelo;

import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

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
    //private LocalDate fecha;
    private String fecha;
    
    @ManyToOne
    @JoinColumn (nullable = false) // borrado name con _id
    private Usuario vigilante;
    
    @ManyToOne
    @JoinColumn (nullable = false) // borrado name con _id
    private Sucursal sucursal;
    
    @Column (nullable = false)
    private Boolean armado;
       
}
