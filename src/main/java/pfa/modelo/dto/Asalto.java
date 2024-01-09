package pfa.modelo.dto;

import java.io.*;
import java.time.LocalDate;

public class Asalto implements Serializable {

    private Long id;
    private Integer codigo;
    private LocalDate fechaAsalto;
    private Sucursal sucursalAsaltada;
    private Juez juezAsignado;
    private Detenido detenido;
    private LocalDate fechaCondena;

    public Asalto(Long id, Integer codigo, LocalDate fechaAsalto, Sucursal sucursalAsaltada, Juez juezAsignado, Detenido detenido, LocalDate fechaCondena) {
        this.id = id;
        this.codigo = codigo;
        this.fechaAsalto = fechaAsalto;
        this.sucursalAsaltada = sucursalAsaltada;
        this.juezAsignado = juezAsignado;
        this.detenido = detenido;
        this.fechaCondena = fechaCondena;
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

    public LocalDate getFechaAsalto() {
        return fechaAsalto;
    }

    public void setFechaAsalto(LocalDate fechaAsalto) {
        this.fechaAsalto = fechaAsalto;
    }

    public Detenido getDetenido() {
        return detenido;
    }

    public void setDetenido(Detenido detenido) {
        this.detenido = detenido;
    }

    public Sucursal getSucursalAsaltada() {
        return sucursalAsaltada;
    }

    public void setSucursalAsaltada(Sucursal sucursalAsaltada) {
        this.sucursalAsaltada = sucursalAsaltada;
    }

    public Juez getJuezAsignado() {
        return juezAsignado;
    }

    public void setJuezAsignado(Juez juezAsignado) {
        this.juezAsignado = juezAsignado;
    }

    public LocalDate getFechaCondena() {
        return fechaCondena;
    }

    public void setFechaCondena(LocalDate fechaCondena) {
        this.fechaCondena = fechaCondena;
    }

    public String tieneCondena() {
        if (fechaCondena != null) {
            return ("La condena del imputado " + detenido.getNombreCompleto()
                    + "por asaltar la sucursal " + sucursalAsaltada.getNombre()
                    + "finaliza el " + fechaCondena);
        } else {
            return "No tiene condena.";
        }
    }

    public void serializar(String nom) throws IOException {
        ObjectOutputStream o
                = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(nom)));
        o.writeObject(this);
        o.close();
    }

    public Asalto deSerializar(String nom) throws IOException, ClassNotFoundException {
        ObjectInputStream o
                = new ObjectInputStream(new BufferedInputStream(new FileInputStream(nom)));
        Asalto d = (Asalto) o.readObject();
        o.close();
        return d;
    }
}
