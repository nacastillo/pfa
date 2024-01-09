package pfa.modelo.dto;

import pfa.modelo.Sistema;
import pfa.modelo.dto.Usuario;
import static pfa.modelo.Constantes.cadenaMenuVigis;
import static pfa.modelo.EntradaSalida.*;

public class Vigilante extends Usuario {
    private Integer codigo;
    private Integer edad;		
    
    public Vigilante (String user, String pass, int codVig, int edad) {
        super.setUsr(user);
        super.setPwd(pass);
        this.codigo = codVig;
        this.edad = edad;
    }
    
    public int getCodVig () {
        return codigo;
    }
    
    public boolean esCodVig (int cod) {
        return codigo == cod;
    }
    
    @Override
    /**
     * Implementacion propia de toString, imprimiendo nombre de usuario, codigo y edad.
     * @return String
     */
    public String toString () {
        return "Usuario: " + getUsr() +
                ", codigo: " + codigo +
                ", edad: " + edad +
                ".";
    }
    
    @Override
    public int menuPrincipal (Sistema s) {
        int i;
        do {
            mostrarMsjLn(cadenaMenuVigis);
            /*
            mostrarMsjLn("Ingrese una opcion\n"
                + "<1>\tConsultar sucursales asignadas\n"
                + "<2>\tConsultar fechas asignadas\n"
                + "<3>\tSalir del sistema\n"
                + "<4>\tes vigilante\n"
                + "<5>\tes inv\n"
                + "<6>\tes admin\n"
                + "\n"
                + "<0>\tCerrar sesión\n"
            );
            */
            try {
                i = leerEntero();
            }
            catch (Exception ex) {
                System.out.println("Por favor ingrese una opcion correcta");
                i = -1;
            }
            switch (i) {
                case 1, 2, 3 -> System.out.println("A implementar");
                case 4 -> System.out.println(esVigi());
                case 5 -> System.out.println(esInves());
                case 6 -> System.out.println(esAdmin());                
            }
        } while (i != 0);        
    return i;
    }    
}
