package pfa;

public class Juez {
    private final int claveInterna;
    private final String nombre;
    private int aniosServicio;
		
    public Juez (int claveInterna, String nombre, int aniosServicio) {
        this.claveInterna = claveInterna;
        this.nombre = nombre;
        this.aniosServicio = aniosServicio;			
    }
	
    public void asignarCondena(int dias,Delito d) {
        d.setCondena(true);
        d.setDiasCondena(dias);
    }
		
    public void tieneCondena (Delito d) {
        if (d.getCondena() == true) {
            System.out.println("El detenido tiene una condena de " + 
                    d.getDiasCondena() + " dias.");
	}
	else
            System.out.println("El detenido no tiene condena.");			
    } 
}
