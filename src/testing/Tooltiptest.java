/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import gui.componentes.Tooltip;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

/**
 *
 * @author vikour
 */
public class Tooltiptest {
    
    public static void main(String[] args) throws InterruptedException {
        Tooltip tip = null;
        
        tip = new Tooltip("Hola", Tooltip.SUCCESS, null, 3000);
        tip.setMode(Tooltip.ERROR);
        tip.setVisible(true);
        Thread.sleep(5000);
        tip.setVisible(true);
    }
    
}
