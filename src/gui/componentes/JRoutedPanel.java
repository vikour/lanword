/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.componentes;

import gui.util.GestionPaneles;
import java.awt.Container;
import java.awt.Frame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Este componente representa a un JPanel de Swing que puede manejar la transición
 * de paneles, del panel superior. Esto consigue hacer un rastro que se puede seguir.
 * Por ejemplo, un rastro podría ser administración > clientes > nuevo. 
 *
 * @author Víctor Manuel Ortiz Guardeño
 * @version 1.0
 */
public class JRoutedPanel extends JPanel {
    private GestionPaneles gesPanel;

    public JRoutedPanel(GestionPaneles gesPanel) {
        this.gesPanel = gesPanel;
    }

    public GestionPaneles getGestorPaneles() {
        return gesPanel;
    }
    
}
