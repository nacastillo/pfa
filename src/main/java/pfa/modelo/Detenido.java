package pfa.modelo;

import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table (name = "detenidos")

public class Detenido implements Serializable {    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (nullable = false, unique = true, updatable = false, insertable = false)
    private Long id;
    
    @Column (nullable = false, unique = true)
    private Integer codigo;
    
    @Column (nullable = false)
    private String nombreCompleto;
    
    @ManyToOne
    @JoinColumn (name = "banda_id")
    private Banda banda;        
    
    @ManyToMany(mappedBy = "detenidos")
    private Set<Asalto> asaltos = new HashSet<>();
      
}