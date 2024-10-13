package com.mycompany.ejercicio05;

import Controlador.controlador;
import Vista.vista;


/**
 *
 * @author HILTER
 */
public class Ejercicio05 {

    public static void main(String[] args) {
        // Inicializa la GUI en el Event Dispatch Thread
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                vista vista = new vista();  
                controlador controlador = new controlador(vista);
                vista.setVisible(true);
            }
        });
    }
}