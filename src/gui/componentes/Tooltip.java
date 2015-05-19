/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.componentes;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 *
 * @author vikour
 */
public class Tooltip extends JDialog {
    public static final int ERROR = 0;
    public static final int SUCCESS = 1;
    
    private JLabel message;
    private Component componentLinked;
    private long delay;
    
    
    public Tooltip(String text, int modo, Component link, long delay) {
        componentLinked = link;
        this.delay = delay;
        
        // Desing of JLabel
        message = new JLabel(text);
        message.setForeground(Color.WHITE);
        
        // Desing of JDialog
        setUndecorated(true);

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        add(message);
        pack();
        setAlwaysOnTop(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getRootPane().putClientProperty("Window.shadow", Boolean.FALSE);
        
        // How look like.
        
        if (modo == ERROR)
            getContentPane().setBackground(new Color(191, 42, 42));
        else
            getContentPane().setBackground(new Color(35, 163, 88));            
        
    }
    
    public void setComponentLinked(Component component) {
        this.componentLinked = component;
    }

    public void setMessage(String message) {
        this.message.setText(message);
        setSize(20 + message.length() * this.message.getFont().getSize() * 3/4, 37);
    }
    
    public void setMode(int modo) {
        if (modo == ERROR)
            getContentPane().setBackground(new Color(191, 42, 42));
        else
            getContentPane().setBackground(new Color(35, 163, 88));            
    }
    
    @Override
    public void setVisible(boolean b) {
        int x, y;
    
        /* El tip se muestra siempre que no se esté viendo. Ya que si no, esto puede
         * hacer aparecer muchísimos tips, por cualquier motivo. Imagine que el usuario
         * se equivoca varias veces a la vez, y esta equivocación hace que aparezca un tip.
         * Entonces, sin esta condición aparecerían muchísimos tips.
         */
        
        if (!isShowing()) {
            
            if (componentLinked == null) {
                setLocationRelativeTo(null);
            }
            else {
                x = componentLinked.getX() + SwingUtilities.getWindowAncestor(componentLinked).getX() + 20 + componentLinked.getWidth();
                y = componentLinked.getY() + SwingUtilities.getWindowAncestor(componentLinked).getY() - 5;
                setLocation(x, y);
            }
            
            new ThreadCloseTip(delay, this).start();
            super.setVisible(b); 
        }
    }
    
    private class ThreadCloseTip extends Thread {
        private long delay;
        private Tooltip tip;

        public ThreadCloseTip(long delay, Tooltip tip) {
            this.delay = delay;
            this.tip = tip;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(delay);
                tip.dispatchEvent(new WindowEvent(tip, WindowEvent.WINDOW_CLOSING));
            } catch (InterruptedException ex) {
                Logger.getLogger(Tooltip.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
}
