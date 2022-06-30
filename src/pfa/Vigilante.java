package pfa;

import static pfa.EntradaSalida.*;

import java.io.*;

public class Vigilante extends Usuario implements Serializable {
    private int codVig;
    private int edad;		
    private Fecha fechaContr;
    private boolean portaArma;
    
    public Vigilante (String user, String pass, int codVig, int edad) {
        super.setUser(user);
        super.setPass(pass);
        this.codVig = codVig;
        this.edad = edad;
        fechaContr = null;
        portaArma = false;    
    }
    
    public Vigilante (int codVig, int edad, Fecha fechaContr, boolean portaArma) {
            this.codVig = codVig;
            this.edad = edad;
            this.fechaContr = fechaContr;
            this.portaArma = portaArma;			
    }		

    public void setArma (boolean portaArma) {
        this.portaArma = portaArma;
    } 

    public void setFechaContr(Fecha fechaContr) {
        this.fechaContr = fechaContr;
    }

    public Fecha getFechaContr() {
        return fechaContr;
    }

    public boolean getPortaArma() {
        return portaArma;
    }
    
    public int getCodVig () {
        return codVig;
    }
    
    public String infoVig () {
        if (portaArma) {
            return ("Codigo de vigilante: " + codVig +
                    ", fecha contratado: " + 
                    fechaContr.getDia() + "/" +
                    fechaContr.getMes() + "/" +
                    fechaContr.getAnio()+ "; porta arma: SI");
        }
        else {
            return ("Codigo de vigilante: " + codVig +
                    ", fecha contratado: " + 
                    fechaContr.getDia() + "/" +
                    fechaContr.getMes() + "/" +
                    fechaContr.getAnio()+ "; porta arma: NO");                   
        }
    }
    
    @Override
    public void menu (Sistema s) {
        int i;
        do {
            System.out.println("Ingrese una opcion");
            System.out.println("1) Consultar sucursales asignadas");
            System.out.println("2) Consultar fechas asignadas");
            System.out.println("0) Salir del sistema");
            try {
                i = leerEntero();
            }
            catch (Exception ex) {
                System.out.println("Por favor ingrese una opcion correcta");
                i = -1;
            }
            switch (i) {
                case 1: 
                case 2:
                case 3: System.out.println("A implementar");
                        break;
            }
        } while (i != 0);        
    }    
}
