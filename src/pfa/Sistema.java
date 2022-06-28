package pfa;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import static pfa.EntradaSalida.*;

public class Sistema implements Serializable {
    private ArrayList <Entidad> Entidades;
    private ArrayList <Usuario> Usuarios;
    private ArrayList <Detenido> Detenidos;
    private ArrayList <Banda> Bandas;
    private ArrayList <Juez> Jueces;
    //private ArrayList <Vigilante> Vigilantes;
    private ArrayList <Delito> Delitos;
		
    public Sistema (){
        Entidades = new ArrayList <> ();
        Usuarios = new ArrayList <> ();
        Detenidos = new ArrayList <> ();
        Bandas = new ArrayList <> ();
        Jueces = new ArrayList <> ();
    //    Vigilantes = new ArrayList <> ();
        Delitos = new ArrayList <> ();						
    }    
    
    public void agregarEntidad () {
        int codigo = leerEntero("Ingrese el codigo de la entidad");
        String domicilio = leerCadena("Ingrese el domicilio de la entidad");
        Entidad e;
        System.out.printf("Los datos a ingresar son:\nCodigo: %d\nDomicilio: %s.\n",codigo,domicilio);
        e = new Entidad(codigo,domicilio);
        Entidades.add(e);
    }
    
    public void listarEntidades () {
        if (Entidades.isEmpty()) {
            System.out.println("No hay entidades registradas");
        }
        else {
            System.out.println("Lista de entidades:");
            for (Entidad e : Entidades) {
                e.mostrarEntidad();       
            }
        }
    }

    public void ingresarDetenido () {
        int cod = leerEntero("Ingrese el codigo numerico del detenido");
        Fecha fecha;
        String nom = leerCadena("Ingrese el apellido del detenido");
        String ape = leerCadena("Ingrese el nombre del detenido");
        int dia = leerEntero("Ingrese el dia del asalto");
        int mes = leerEntero("Ingrese el mes del asalto");
        int anio = leerEntero("Ingrese el anio del asalto");
        fecha = new Fecha (dia,mes,anio);
        Detenido d = new Detenido (cod,ape,nom,fecha);
        Detenidos.add(d);
    }
    
    public void listarDetenidos () {
        if (Detenidos.isEmpty()) {
            System.out.println("No hay detenidos ingresados");
        }
        else {
            for (Detenido d : Detenidos) {
                d.mostrarDetenido();            
            }       
        }            
    }
    
    public void ingresarDelito () {
    }
    
    public void altaBanda (ArrayList <Banda> Bandas) {
        int n = leerEntero("Ingrese el codigo numérico de la banda");
        Banda b = new Banda (n);
        Bandas.add(b);        
    }   
    
    public void aniadirVigilante() {
        String usr = leerCadena("Ingrese el usuario");
        String pwd = leerCadena("Ingrese la clave");
        int cod = leerEntero("Ingrese el codigo del vigilante");
        int edad = leerEntero("Ingrese la edad del vigilante");
        Usuario u = new Vigilante(usr,pwd,cod,edad);   
        Usuarios.add(u);
    }
    
    public void aniadirInves () {
        String usr = leerCadena("Ingrese el usuario");
        String pwd = leerCadena("Ingrese la clave");
        Usuario u = new Investigador(usr,pwd);
        Usuarios.add(u);
    }
    
    public void aniadirAdmin () {
        String usr = leerCadena("Ingrese el usuario");
        String pwd = leerCadena("Ingrese la clave");
        Usuario u = new Administrador(usr,pwd);
        Usuarios.add(u);
    }
    
    public void aniadirAdmin (String usr, String pwd) {
        Usuario u = new Administrador(usr,pwd);
        Usuarios.add(u);
    }
    
    
    public void listarUsuarios () {
        System.out.println("Lista de usuarios:");
        for (Usuario u : Usuarios) {
            u.mostrarUsuario();        
        }    
    }
    
    public Usuario login () throws NullPointerException {
        String usr = leerCadena("Ingrese el usuario");
        String pwd = leerCadena("Ingrese la clave");
        Usuario u = buscarUsuario(usr,pwd);        
        return u;
    }
    
    public Usuario buscarUsuario (String user, String pass) throws NullPointerException {
        Usuario u = null;
        for (Usuario v : Usuarios) {
            if (user.equals(v.getUser()) && pass.equals(v.getPass())) {
                u = v;
            break;
            }                
        }        
        return u;
    }
    
    public void consultarSucursal (int cod) throws NullPointerException {
        int j = 0;
        for (Entidad e : Entidades) {
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
    }
    
    
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