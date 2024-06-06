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
    private Integer clave;
    
    @Column (nullable = false)
    private String nombre;
    
    @Column (nullable = false)
    private Integer aniosServicio;
    
    @OneToMany(mappedBy = "juez")    
    private List<Asalto> asaltos;
    
}
