package pfa.modelo;

import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Data
@Entity
@Table (name = "jueces")

public class Juez {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "id")
    private Long id;

    @Column (name = "claveInterna")
    private Integer claveInterna;
    
    @Column (name = "nombreCompleto")
    private String nombreCompleto;
    
    @Column (name = "aniosServicio")
    private Integer aniosServicio;
    
}
