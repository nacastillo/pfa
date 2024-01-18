package pfa.modelo;

import pfa.modelo.dto.Asalto;
import java.util.*;
import java.io.*;

public class ListaDeDelitos implements Serializable {
    
    private List <Asalto> delitos;
    
    public ListaDeDelitos () {
        delitos = new ArrayList <> ();    
    }
}
