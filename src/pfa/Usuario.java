package pfa;

import java.io.*;

public abstract class Usuario implements Serializable {
    private String user;
    private String pass;
    
    public void setUser (String u) {
        user = u;
    }
		
    public void setPass (String p) {
        pass = p;
    }
    
    public String getUser () {
        return user;    
    }
    
    public String getPass () {
        return pass;
    }
    
    public void mostrarUsuario () {
        System.out.println("Usuario: " + getUser() + ", Clave: " + getPass());
    }
    
    public void serializar (String nom) throws IOException {
        ObjectOutputStream o =
        new ObjectOutputStream (new BufferedOutputStream (new FileOutputStream (nom)));
        o.writeObject(this);
        o.close();    
    }
    
    public Usuario deSerializar (String nom) throws IOException, ClassNotFoundException {
        ObjectInputStream o =
        new ObjectInputStream (new BufferedInputStream (new FileInputStream (nom)));
        Usuario u = (Usuario) o.readObject();
        o.close();
        return u;    
    }
    
    public abstract void menu(Sistema s);
}