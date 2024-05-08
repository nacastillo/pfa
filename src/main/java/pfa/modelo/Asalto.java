package pfa.modelo;

import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table (name = "asaltos")

public class Asalto implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (nullable = false, unique = true, updatable = false, insertable = false)    
    private Long id;
    
    @Column (nullable = false, unique = true)
    private Integer codigo;
    
    @Column (nullable = false)
    //private LocalDate fechaAsalto;
    private String fechaAsalto;
    
    @ManyToOne 
    @JoinColumn (nullable = false)
    private Sucursal sucursalAsaltada;   
    
    @ManyToOne 
    @JoinColumn (nullable = false)
    private Juez juezAsignado;
    
    @ManyToMany
    @JoinTable(
        name = "asalto_detenido",
        joinColumns = @JoinColumn(name = "asalto_id"),
        inverseJoinColumns = @JoinColumn(name = "detenido_id")
    )
    private Set<Detenido> detenidos = new HashSet<>();
    
    @Column (nullable = false)
    //private LocalDate fechaCondena;   
    private String fechaCondena;
     
}