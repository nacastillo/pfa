package pfa.modelo.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class Contrato {
    
    private Long id;
    private Integer codigo;
    private LocalDate fecha;
    private Usuario vigilante;
    private Sucursal sucursal;
    private Boolean armado;

    public Contrato(Long id, Integer codigo, LocalDate fecha, Usuario vigilante, Sucursal sucursal, Boolean armado) {
        this.id = id;
        this.codigo = codigo;
        this.fecha = fecha;
        this.vigilante = vigilante;
        this.sucursal = sucursal;
        this.armado = armado;
    }    
}
