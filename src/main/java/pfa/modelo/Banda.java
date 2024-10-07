package pfa.modelo;

import lombok.Data;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table (name = "bandas")

public class Banda implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (nullable = false, unique = true, updatable = false, insertable = false)
    private Long id;
    
    @Column (nullable = false, unique = true)
    private Integer numero;
    
    @Column (nullable = false)
    private String nombre;            
    
    @OneToMany (cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})    
    @JoinColumn (name = "banda")
    private List <Detenido> miembros;      
    
}