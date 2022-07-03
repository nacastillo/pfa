/**
* Se cambia la implementacion de las colecciones de:
* entidades, usuarios, detenidos a nivel dependencia de clases, 
* pero dichas implementaciones siguen usando ArrayList.
* agregarEntidad() y listarEntidades() se mueven 
* a la nueva clase BaseDeEntidades
* ingresarDetenido() y listarDetenidos() se mueven
* a la nueva clase BaseDeDetenidos
    /**
     * removido login(), reemplazado por login2 (validacion previa de username)
     * @return 
*/


package pfa;

import java.io.*;
import java.util.*;
import static pfa.EntradaSalida.*;

public class Sistema implements Serializable {
    private BaseDeEntidades entidades;
    private BaseDeUsuarios usuarios;
    private BaseDeDetenidos detenidos;
    private List <Banda> Bandas;
    private List <Juez> Jueces;
    private List <Delito> Delitos;
    
    public Sistema (){
        entidades = new BaseDeEntidades();
        usuarios = new BaseDeUsuarios();
        detenidos = new BaseDeDetenidos();
        Bandas = new ArrayList <> ();
        Jueces = new ArrayList <> ();
        Delitos = new ArrayList <> ();						
    }    
        
    public void agregarEntidad () {
        entidades.agregarEntidad();
    }
    
    public void listarEntidades () {
        entidades.listarEntidades();
    }

    public void agregarDetenido () {
        detenidos.agregarDetenido();
    }
    
    public void listarDetenidos () {
        detenidos.listarDetenidos();
    }
    
    public void ingresarDelito () {
    }
    
    public void altaBanda (ArrayList <Banda> Bandas) {
        int n = leerEntero("Ingrese el codigo numérico de la banda: ");
        Banda b = new Banda (n);
        Bandas.add(b);        
    }   
    
    public void agregarVigi() {
        usuarios.agregarVigi();
    }
    
    public void agregarInves () {
        usuarios.agregarInves();
    }
    
    public void agregarAdmin () {
        usuarios.agregarAdmin();
    }
    
    public void defaultAdmin () {
        usuarios.defaultAdmin();
    }
    
    public void listarUsuariosConClave() {
        usuarios.listarUsuariosConClave();
    }   
    
    public void listarUsuariosConRol () {
        usuarios.listarUsuariosConRol();
    }
    
    public Usuario login2 () {
        return usuarios.login2();
    }
    
    public void agregarSucursalAEntidad () {
        entidades.agregarSucursalAEntidad();
    }
    
    public void listarEntidadesCompleto () {
        entidades.listarEntidadesConSucursales();
    }
    
    public Vigilante buscarVig (Integer cod) {
        return usuarios.buscarVig(cod);
    }
        
    /**
     * todavia no se como implementar esto
     * @param nom
     * @throws IOException 
     */
    /*public void consultarSucursal (int cod) throws NullPointerException {
        int j = 0;
        for (Entidad e : entidades) {
            for (Sucursal s : e.getSucursales()) {
                for (Vigilante v : s.getVigContratados()) {
                    if (v.getCodVig() == cod) {
                        System.out.println("Entidad: " + e.getCodEnt() +
                                           ",sucursal " + s.getCodSuc() +
                                           ", " + v.infoVig());
                    }                        
                }
            }
        }        
    }*/
           
    public void serializar (String nom) throws IOException {
        ObjectOutputStream o =
        new ObjectOutputStream (new BufferedOutputStream (new FileOutputStream (nom)));
        o.writeObject(this);
        o.close();    
    } 
    
    public Sistema deSerializar (String nom) throws IOException, ClassNotFoundException {
        ObjectInputStream o =
        new ObjectInputStream (new BufferedInputStream (new FileInputStream (nom)));
        Sistema s = (Sistema) o.readObject();
        o.close();
        return s;    
    }    
}