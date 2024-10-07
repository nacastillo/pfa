package pfa.modelo;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table (name = "entidades")

public class Entidad implements Serializable { 
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (nullable = false, unique = true, updatable = false, insertable = false)
    private Long id;
    
    @Column (nullable = false, unique = true)
    private Integer codigo;
        
    @Column (nullable = false, length = 64) 
    private String domicilio;
    
    @Column (nullable = false, length = 64)
    private String nombre;     
    
    @OneToMany (cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn (name = "entidad")
    private List<Sucursal> sucursales;
    
    
    
}
