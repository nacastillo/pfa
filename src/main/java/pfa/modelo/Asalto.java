package pfa.modelo;

import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Data
@Entity
@Table (name = "asaltos")

public class Asalto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "id")    
    private Long id;
    
    @Column (name = "codigo")
    private Integer codigo;
    
    @Column (name = "fechaAsalto")
    private LocalDate fechaAsalto;
    
    @Column (name = "sucursalAsaltada")
    private Sucursal sucursalAsaltada;
    
    @Column (name = "juezAsignado")
    private Juez juezAsignado;
    
    @Column (name = "detenido")
    private Detenido detenido;
    
    @Column (name = "fechaCondena")
    private LocalDate fechaCondena;   
     
}