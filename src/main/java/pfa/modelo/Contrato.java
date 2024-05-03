package pfa.modelo;

import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Data
@Entity
@Table (name = "contratos")

public class Contrato {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "id")
    private Long id;
    
    @Column (name = "codigo")
    private Integer codigo;
    
    @Column (name = "fecha" )
    private LocalDate fecha;
    
    @Column (name = "vigilante")
    private Usuario vigilante;
    
    @Column (name = "sucursal")
    private Sucursal sucursal;
    
    @Column (name = "armado")
    private Boolean armado;
       
}
