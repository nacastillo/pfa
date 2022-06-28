package pfa;

public class Detenido {
    private int codigo;
    private String nombreCompleto;
    private Banda banda;
    private Fecha fechaAsalto;
		
    public Detenido (int codigo, String apellido, String nombre, Fecha fechaAsalto) {
        this.codigo = codigo;
	nombreCompleto = apellido + ", " + nombre;		
	this.fechaAsalto = fechaAsalto;
    }		

    public Detenido(int codigo, String apellido, String nombre, Banda banda, Fecha fechaAsalto) {
        this.codigo = codigo;
        nombreCompleto = apellido + ", " + nombre;
        this.banda = banda;
        this.fechaAsalto = fechaAsalto;
    }
    
    public void setBanda(Banda banda) {
        this.banda = banda;
    }
    
    public void mostrarDetenido() {
        if (banda == null) {
            System.out.println("Codigo: " + codigo + 
                               "; nombre: " + nombreCompleto + 
                               "; fecha del delito" + fechaAsalto +
                               "; banda asignada: ninguna.");
        }
        else {
            System.out.println("Codigo: " + codigo + 
                               "; nombre: " + nombreCompleto + 
                               "; fecha del delito" + fechaAsalto +
                               "; banda asignada:" + banda);
        }
    }
}