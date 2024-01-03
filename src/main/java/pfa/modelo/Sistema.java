package pfa.modelo;

import static pfa.modelo.EntradaSalida.*;
import java.io.*;
import java.util.*;

public class Sistema implements Serializable {
    private ListaDeEntidades entidades;
    private BaseDeUsuarios usuarios;
    private ListaDeDetenidos detenidos;
    private List <Banda> bandas;
    private ListaDeJueces jueces;
    private ListaDeDelitos delitos;
    
    public Sistema (){
        entidades = new ListaDeEntidades();
        usuarios = new BaseDeUsuarios();
        detenidos = new ListaDeDetenidos();
        bandas = new ArrayList <> ();
        jueces = new ListaDeJueces();
        delitos = new ListaDeDelitos();
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
    
    public void agregarDelito () {
        int i, j, e, s;
        Fecha f;
        i = leerEntero("Ingrese el codigo del imputado: ");
        //pendiente validacion
        j = leerEntero("Ingrese el codigo del juez asignado: ");
        //pendiente validacion
        e = leerEntero("Ingrese la entidad asaltada: ");
        //validacion
        s = leerEntero("Ingrese la sucursal asaltada: ");
        //validacion
        f = leerFecha("Ingrese la fecha del asalto: ");
        //validacion
        delitos.addDelito(i, j, e, s, f);
    }
        
    public void altaBanda (ArrayList <Banda> Bandas) {
        int n = leerEntero("Ingrese el codigo numérico de la banda: ");
        Banda b = new Banda (n);
        Bandas.add(b);        
    }   
    
    public void agregarVigi() {
        usuarios.agregarVigi();
    }
    
    public void agregarVigi2() {
        usuarios.agregarVigi2();
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
    
    public void listarVigilantes () {
        usuarios.listarVigilantes();
    }
    
    public void agregarSucursalAEntidad () {
        entidades.agregarSucursalAEntidad();
    }

    public void listarBandas () {
        if (bandas.isEmpty()) {
            mostrarMsjLn("No hay bandas ingresadas");
        }
        else {
            for (Banda b : bandas) {
                mostrarMsjLn("Lista de bandas: ");
                b.toString();
            }
        }
    }
    
    public void listarEntidadesCompleto () {
        entidades.listarEntidadesConSucursales();
    }
    
    public Vigilante buscarVig (Integer cod) {
        return usuarios.buscarVig(cod);
    }
    
    public void listarJueces () {
        jueces.listarJueces();
    }

    public int menuLogin() {
        String pwd, usr = leerCadena("Ingrese su nombre de usuario: ");
        while(!usuarios.existeUsr(usr)) {
            usr = leerCadena("Por favor, ingrese un nombre de usuario válido: ");
        }
        Usuario u = usuarios.buscarUsuario(usr);
        pwd = leerCadena("Ingrese su contraseña: ");
        while(!u.esPwd(pwd)) {
            pwd = leerCadena("Por favor, ingrese correctamente su contraseña: ");
        }
        mostrarMsjLn("Ingreso correcto al sistema.");
        return u.menuPrincipal(this);
    }   
        
    public void grabarSistema () {
        try {
            serializar("hola.txt");
        }
        catch (IOException ex) {
            mostrarMsjLn("Problemas al grabar sistema"); 
        }
    }
    
    /*
    public Sistema cargarSistema () {
        Sistema s = new Sistema();
        try {
            deSerializar("hola.txt");
        }
        catch (FileNotFoundException ex) {
            mostrarMsjLn("Primer ingreso al sistema.\nGenerando default admin...");
            usuarios.defaultAdmin();
        }
        catch (IOException | ClassNotFoundException ex) {
            System.out.println("Problema al abrir el sistema.\nReiniciando sistema y generando default admin...");
            usuarios.defaultAdmin();
        }
        return s;
    }
    */   
           
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

    /* 
    public void cargarMenues () {
        // v.agregarListeners(new jojo());
    }
    

    private class jojo implements ActionListener {
    
        @Override
        public void actionPerformed (ActionEvent ae) {
            JOptionPane.showMessageDialog(null,"HOLA QUE TAL");        
        }
    }
    
    private class Servicio {
        private int codEnt;
        private int codSucu;
        private int codVig;
        private Fecha fechaContr;
        private boolean portaArma;
        
        public Servicio () {
            char c;
            codEnt = leerEntero("Ingrese el codigo de la entidad: ");
            codSucu = leerEntero("Ingrese el codigo de la sucursal: ");
            codVig = leerEntero("Ingrese el codigo del vigilante a contratar: ");
            fechaContr = leerFecha("Ingrese una fecha en formato DD/MM/AAAA");
            c = leerCadena("Presione S si el vigilante debe portar arma, de lo contrario pulse cualquier tecla para continuar: ").charAt(0);
            portaArma = (c == 's' || c == 'S');
        }    
    }
    
    */
}