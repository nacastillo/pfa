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
@Table (name = "bandas")

public class Banda {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "id")
    private Long id;
    
    @Column (name = "numero")
    private Integer numero;
    
    @Column (name = "nombre")
    private String nombre;
    
    @Column (name = "cantidadMiembros")
    private Integer cantidadMiembros;      
    
}