/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.administracion.JDialogMainAdministracion;
import gui.juegos.traducir_palabras.JDialogMain;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import lanword.interfaces.bd.BDManagment;
import lanword.interfaces.bd.BDResolver;
import lanword.interfaces.bd.IBDGestionPalabras;
import lanword.modelo.Idioma;
import lanword.modelo.Palabra;

/**
 *
 * @author vikour
 */
public class AppStartMenu extends javax.swing.JFrame {

    /**
     * Creates new form AppStartMenu
     */
    public AppStartMenu() {
        initComponents();
        this.addWindowListener(new AppWindowListener());
        setIconImage(Toolkit.getDefaultToolkit().getImage(AppStartup.class.getClassLoader().getResource("gui/images/icono.png")));        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel1.setText("LANWORD");

        jButton1.setText("Jugar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Administrar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Salir");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(28, 28, 28)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setVisible(false);
        new JDialogMain(this, true).setVisible(true);
        setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        setVisible(false);
        new JDialogMainAdministracion(this, true).setVisible(true);
        setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AppStartMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AppStartMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AppStartMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AppStartMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                try {
                    BDResolver.getInstance().conectar();
                    
                    if (checkIfAnotherLanwordRuns())
                        JOptionPane.showMessageDialog(null, "Lanword ya se está ejecutando.");
                    else {

                        if (BDResolver.getInstance().idiomas.buscar().isEmpty()) {
                            int choice = 
                                    JOptionPane.showConfirmDialog(null, "No hay palabras en la base de datos, \n"
                                            + "¿quieres añadir las palabras predeterminadas?", "Palabras predeterminadas",
                                            JOptionPane.YES_NO_OPTION);

                            if (choice == JOptionPane.YES_OPTION) {
                                generarPalabras();
                            }
                        }
                        new AppStartMenu().setVisible(true);
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(AppStartMenu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AppStartMenu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(AppStartMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private static boolean checkIfAnotherLanwordRuns() throws IOException {
        boolean otherLanwordRuns = false;
        File init = new File(".lanword");
        
        if (init.exists())
            otherLanwordRuns = true;
        else
            init.createNewFile();
        
        return otherLanwordRuns;
    };
    
    private static void generarPalabras() throws SQLException, ClassNotFoundException, IOException {
        IBDGestionPalabras bd = BDResolver.getInstance().palabras;
        Idioma espanol = new Idioma("Español");
        Idioma ingles = new Idioma("Inglés");
        
        Palabra laptop = new Palabra("Laptop", ingles);
        Palabra pc = new Palabra("Personal Computer", ingles);
        Palabra hub = new Palabra("Hub", ingles);
        Palabra router = new Palabra("Router", ingles);
        Palabra monitor = new Palabra("Monitor", ingles);
        Palabra mouse = new Palabra("Mouse", ingles);
        Palabra keyboard = new Palabra("Keyboard", ingles);
        Palabra motherboard = new Palabra("Motherboard", ingles);
        Palabra harddisk = new Palabra("Hard disk", ingles);
        Palabra opticaldrive = new Palabra("Optical drive", ingles);
        Palabra printer = new Palabra("Printer", ingles);
        Palabra codification = new Palabra("Codificacion", ingles);
        Palabra software = new Palabra("Software", ingles);
        Palabra hardware = new Palabra("hardware", ingles);
        
        Palabra portatil = new Palabra("Portátil", espanol);
        Palabra pc_es = new Palabra("Ordenador personal", espanol);
        Palabra repetidor = new Palabra("Repetidor", espanol);
        Palabra encaminador = new Palabra("Encaminador", espanol);
        Palabra monitor_es = new Palabra("Monitor", espanol);
        Palabra raton = new Palabra("Ratón", espanol);
        Palabra teclado = new Palabra("Teclado", espanol);
        Palabra placabase = new Palabra("Placa base", espanol);
        Palabra discoduro = new Palabra("Disco duro", espanol);
        Palabra lectoroptico = new Palabra("Lector óptico", espanol);
        Palabra impresora = new Palabra("Impresora", espanol);
        Palabra codificacion = new Palabra("Codificación", espanol);
        Palabra software_es = new Palabra("Software", espanol);
        Palabra hardware_es = new Palabra("Hardware", espanol);
        
        laptop.anyadirTraduccion(portatil);
        pc.anyadirTraduccion(pc_es);
        hub.anyadirTraduccion(repetidor);
        router.anyadirTraduccion(encaminador);
        monitor.anyadirTraduccion(monitor_es);
        mouse.anyadirTraduccion(raton);
        keyboard.anyadirTraduccion(teclado);
        motherboard.anyadirTraduccion(placabase);
        harddisk.anyadirTraduccion(discoduro);
        opticaldrive.anyadirTraduccion(lectoroptico);
        printer.anyadirTraduccion(impresora);
        codification.anyadirTraduccion(codificacion);
        software.anyadirTraduccion(software_es);
        hardware.anyadirTraduccion(hardware_es);
        
        bd.anyadir(laptop);
        bd.anyadir(pc);
        bd.anyadir(hub);
        bd.anyadir(router);
        bd.anyadir(monitor);
        bd.anyadir(mouse);
        bd.anyadir(keyboard);
        bd.anyadir(motherboard);
        bd.anyadir(harddisk);
        bd.anyadir(opticaldrive);
        bd.anyadir(printer);
        bd.anyadir(codification);
        bd.anyadir(software);
        bd.anyadir(hardware);
    };

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

    private class AppWindowListener implements WindowListener {

        @Override
        public void windowOpened(WindowEvent e) {
            // do nothing.
        }

        @Override
        public void windowClosing(WindowEvent e) {
            // Se borra el fichero .lanword.
            File f = new File(".lanword");
            f.delete();
        }

        @Override
        public void windowClosed(WindowEvent e) {
            // do nothing
        }

        @Override
        public void windowIconified(WindowEvent e) {
            // do nothing
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
            // do nothing
        }

        @Override
        public void windowActivated(WindowEvent e) {
            // do nothing.
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
            // do nothing
        }
        
    };

}
