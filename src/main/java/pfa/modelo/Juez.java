package pfa.modelo;

import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table (name = "jueces")

public class Juez implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (nullable = false, unique = true, updatable = false, insertable = false)
    private Long id;

    @Column (nullable = false, unique = true)
    private Integer claveInterna;
    
    @Column (nullable = false)
    private String nombreCompleto;
    
    @Column (nullable = false)
    private Integer aniosServicio;
    
    @OneToMany(mappedBy = "juezAsignado")    
    private List<Asalto> asaltos;
    
}
