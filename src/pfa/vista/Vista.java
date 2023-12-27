package pfa.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Vista {
    
    private JFrame marco;
    private JPanel panelCen;
    private JMenuBar barraMenu;
    private JMenu menuArchivo;
    private JMenuItem itemIniciarSesion;
                    
    public Vista () {
        marco = new JFrame ("Policia Federal Argentina");
        panelCen = new JPanel();
        barraMenu = new JMenuBar();
        menuArchivo = new JMenu("Archivo");
        itemIniciarSesion = new JMenuItem("Iniciar sesion");
        
        /**
         * CONFIG
         */
        marco.setLayout(new BorderLayout());
        marco.setSize(800,600);
        
        panelCen.setBackground(Color.BLUE);
        //marco.setDefaultCloseOperation(marco.EXIT_ON_CLOSE);
        marco.setLocationRelativeTo(null);
        marco.setUndecorated(true);
        
        
        menuArchivo.add(itemIniciarSesion);
        barraMenu.add(menuArchivo);        
        
        
        
        marco.add(panelCen,BorderLayout.CENTER);
        marco.add(barraMenu,BorderLayout.NORTH);
        
    }
    
    public void mostrar() {
        marco.setVisible(true);
    }
    
    public void agregarListeners (ActionListener al) {
        itemIniciarSesion.addActionListener(al);
    }
    
    
}
