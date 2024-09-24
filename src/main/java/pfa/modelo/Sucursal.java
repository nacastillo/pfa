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
@Table (name = "sucursales")

public class Sucursal implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (nullable = false, unique = true, updatable = false, insertable = false)
    private Long id;

    @Column (nullable = false, unique = true)
    private Integer codigo;
    
    @Column
    private Integer cantidadEmpleados;    
    
    @Column (nullable = false, length = 64)
    private String nombre;

    @Column (nullable = false, length = 64)
    private String domicilio;    

    @Column
    private Long entidad;    
    
    @OneToMany (cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn (name = "sucursal")
    private List <Contrato> contratos;    
    
    @OneToMany (cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn (name = "sucursal")
    private List <Asalto> asaltos;
    
}
