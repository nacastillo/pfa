package pfa.modelo;

import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table (name = "sucursales")

public class Sucursal implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (nullable = false, unique = true, updatable = false, insertable = false)
    private Long id;

    @Column (nullable = false, unique = true)
    private Integer codigo;
    
    @Column (nullable = false)
    private Integer cantidadEmpleados;    
    
    @Column (nullable = false, length = 64)
    private String nombre;
    
    /* relaciones con otras entidades*/
    
    @ManyToOne
    @JoinColumn (name = "entidad_id")
    private Entidad entidad;   
    
    @OneToMany (mappedBy = "sucursal", fetch = FetchType.EAGER)
    private List <Contrato> contratos;
    
    @OneToMany (mappedBy = "sucursalAsaltada", fetch = FetchType.EAGER)
    private List <Asalto> asaltos;
    
}
