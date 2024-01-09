package pfa.modelo.dto;

import java.time.LocalDate;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Usuario getVigilante() {
        return vigilante;
    }

    public void setVigilante(Usuario vigilante) {
        this.vigilante = vigilante;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Boolean getArmado() {
        return armado;
    }

    public void setArmado(Boolean armado) {
        this.armado = armado;
    }    
    
}
