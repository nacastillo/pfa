package pfa.modelo;

import java.io.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Collection;
import lombok.Data;

@Data
@Entity
@Table (name = "entidades")

public class Entidad { 
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "id")
    private Long id;
    
    @Column (name = "codigo")
    private Integer codigo;
        
    @Column (name = "domicilio") 
    private String domicilio;
    
    @Column (name = "nombre")
    private String nombre;
    
    @Column (name = "sucursales") // VER BIEN ESTO
    private Collection<Sucursal> sucursales;
    
    // ojo constructor, yo quiero composicion y no agregacion
}
