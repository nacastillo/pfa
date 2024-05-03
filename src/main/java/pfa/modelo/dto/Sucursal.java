package pfa.modelo.dto;

import static pfa.modelo.EntradaSalida.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.*;
import lombok.Data;

@Data
@Entity
@Table (name = "sucursales")

public class Sucursal {
    //public class Sucursal implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "id")
    private Long id;

    @Column (name = "codigo")
    private Integer codigo;
    
    @Column (name = "cantidadEmpleados")
    private Integer cantidadEmpleados;
    
    @Column (name = "nombre")
    private String nombre;        

    /*
    
    public void sumarEmpl() {
        cantidadEmpleados++;
    }	
    
    public void mostrarSucursal () {
        mostrarMsjLn("Cod. Sucursal: " + codigo +                      
                     ", cantidad de empleados: " + cantidadEmpleados
                    );
    }

    public void serializar (String nom) throws IOException {
        ObjectOutputStream o = new ObjectOutputStream (new BufferedOutputStream (new FileOutputStream (nom)));
        o.writeObject(this);
        o.close();
    }
    
    public Sucursal deSerializar (String nom) throws IOException, ClassNotFoundException {
        ObjectInputStream o = new ObjectInputStream (new BufferedInputStream (new FileInputStream (nom)));
        Sucursal s = (Sucursal) o.readObject();
        o.close();
        return s;
    }
    
    */
}
