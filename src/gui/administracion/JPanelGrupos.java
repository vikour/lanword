/*
 * Copyright 2015 Vikour.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gui.administracion;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import lanword.interfaces.bd.BDManagment;
import lanword.interfaces.bd.BDResolver;
import java.awt.Frame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import lanword.controladores.administracion.GruposCtrl;
import lanword.modelo.Grupo;

/**
 *
 * @author vikour
 */
public class JPanelGrupos extends javax.swing.JPanel {

    private BDManagment bd;
    private GruposCtrl gruposCtrl;

    /**
     * Creates new form JPanelPalabras
     */
    public JPanelGrupos() {
        try {
            bd = BDResolver.getInstance();
            gruposCtrl = GruposCtrl.getInstance();
            initComponents();
        } catch (SQLException ex) {
            Logger.getLogger(JPanelGrupos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JPanelGrupos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JPanelGrupos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JButtonNuevo = new javax.swing.JButton();
        JButtonModificar = new javax.swing.JButton();
        JButtonBorrar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new JTableGrupos();
        JButtonPalabras = new javax.swing.JButton();

        JButtonNuevo.setText("Nuevo");
        JButtonNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButtonNuevoActionPerformed(evt);
            }
        });

        JButtonModificar.setText("Modificar");
        JButtonModificar.setEnabled(false);
        JButtonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButtonModificarActionPerformed(evt);
            }
        });

        JButtonBorrar.setText("Borrar");
        JButtonBorrar.setEnabled(false);
        JButtonBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButtonBorrarActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(jTable1);

        JButtonPalabras.setText("Palabras");
        JButtonPalabras.setEnabled(false);
        JButtonPalabras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButtonPalabrasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(JButtonNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JButtonModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(JButtonBorrar, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(JButtonPalabras, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(JButtonNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JButtonBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JButtonPalabras, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(13, 13, 13))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void JButtonNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButtonNuevoActionPerformed
        new JDialogFormNuevoGrupo((Frame) SwingUtilities.getWindowAncestor(this).getParent(), true).setVisible(true);
        jTable1.updateUI();
    }//GEN-LAST:event_JButtonNuevoActionPerformed

    private void JButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButtonModificarActionPerformed
        try {
            new JDialogFormModificarGrupo((Frame) SwingUtilities.getWindowAncestor(this).getParent(), true).setVisible(true);
            jTable1.updateUI();
        } catch (Exception ex) {
            Logger.getLogger(JPanelGrupos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_JButtonModificarActionPerformed

    private void JButtonBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButtonBorrarActionPerformed
        int aceptarOperacion;
        
        aceptarOperacion = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres borrar el grupo?", "Borrar grupo", JOptionPane.YES_NO_OPTION);
        
        if (aceptarOperacion == JOptionPane.YES_OPTION) {
            try {
                bd.grupos.borrar(gruposCtrl.getSeleccionado());
                jTable1.updateUI();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_JButtonBorrarActionPerformed

    private void JButtonPalabrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButtonPalabrasActionPerformed
        try {
            new JDialogFormAdmPalabras((Frame) SwingUtilities.getWindowAncestor(this).getParent(), true).setVisible(true);
            jTable1.updateUI();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_JButtonPalabrasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JButtonBorrar;
    private javax.swing.JButton JButtonModificar;
    private javax.swing.JButton JButtonNuevo;
    private javax.swing.JButton JButtonPalabras;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables


    public class JTableGrupos extends JTable implements ListSelectionListener {
        private JTableModelGrupos model;

        public JTableGrupos() {
            model = new JTableModelGrupos();

            getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            try {
                model.updateGrupos();
            } catch (SQLException ex) {
                Logger.getLogger(JPanelGrupos.class.getName()).log(Level.SEVERE, null, ex);
            }
            setModel(model);
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            int index;
            
            if (e.getValueIsAdjusting()) {
                index = this.getSelectionModel().getLeadSelectionIndex();
                System.out.println(index);
                Grupo seleccionado = model.getsGrupos().get(index);
                
                gruposCtrl.setSeleccionado(seleccionado);
                JButtonModificar.setEnabled(true);
                JButtonBorrar.setEnabled(true);
                JButtonPalabras.setEnabled(true);
                super.valueChanged(e);
            }
            
        }
        
        @Override
        public void updateUI() {
            try {
                
                if (model != null) {
                    model.updateGrupos();
                    // Cuando se actualiza, se deselecciona el grupo seleccionada.
                    clearSelection();
                    gruposCtrl.setSeleccionado(null);
                    JButtonModificar.setEnabled(false);
                    JButtonBorrar.setEnabled(false);
                    JButtonPalabras.setEnabled(false);
                }
            } catch (SQLException ex) {
                Logger.getLogger(JPanelGrupos.class.getName()).log(Level.SEVERE, null, ex);
            }
            super.updateUI(); 
        }

    
    private class JTableModelGrupos extends DefaultTableModel {
        private ArrayList<Grupo> grupos;
        private final String header[] = new String [] {"Nombre", "Descripcion"};
        
        public JTableModelGrupos() {
            grupos = new ArrayList<>();
        }

        public void updateGrupos() throws SQLException {
            grupos = bd.grupos.buscar();
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
        
        public ArrayList<Grupo> getsGrupos() {
            return grupos;
        }

        @Override
        public int getColumnCount() {
            return header.length;
        }

        @Override
        public String getColumnName(int column) {
            return header[column];
        }

        @Override
        public int getRowCount() {
            
            if (grupos != null)
                return grupos.size();
            else
                return 0;
        }

        @Override
        public Object getValueAt(int row, int column) {
            String out = "";
            
            switch (column) {
                
                case 0: // Nombre del grupo
                    return grupos.get(row).getNombre();
                    
                case 1: // Descripción del grupo
                    return grupos.get(row).getDescripcion();
                    
                default:
                    return "NADA";
            }
            
        }
        
    }
    }
}