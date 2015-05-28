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
package gui.componentes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import lanword.interfaces.bd.BDManagment;
import lanword.interfaces.bd.BDResolver;
import lanword.modelo.Grupo;

/**
 *
 * @author vikour
 */
public class JComboBoxGrupos extends JComboBox {
    public ComboModel model;
    
    public JComboBoxGrupos() {
        try {
            model = new ComboModel();
            model.updateGrupos();
            setModel(model);
            model.setSelectedItem(model.NULO);
        } catch (SQLException ex) {
            Logger.getLogger(JComboBoxGrupos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateUI() {
        
        try {
            if (model != null) {
                model.updateGrupos();
                model.setSelectedItem(model.NULO);
            }
            
            super.updateUI();
        } catch (SQLException ex) {
            Logger.getLogger(JComboBoxGrupos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private class ComboModel extends AbstractListModel<Grupo> implements ComboBoxModel<Grupo> {
        private ArrayList<Grupo> grupos;
        private Grupo selected;
        private final Grupo NULO = new Grupo("Ninguno", null);
        
        public ComboModel() {
            grupos = new ArrayList<>();
            
        }
        
        @Override
        public int getSize() {
            return grupos.size() + 1;
        }

        @Override
        public Grupo getElementAt(int index) {
            
            if (index == 0)
                return NULO;
            else
                return grupos.get(index - 1);
        }

        @Override
        public void setSelectedItem(Object anItem) {
            selected = (Grupo) anItem;
        }

        @Override
        public Object getSelectedItem() {
            return selected;
        }

        private void updateGrupos() throws SQLException {
            try {
                BDManagment bd = BDResolver.getInstance();
                
                grupos = bd.grupos.buscar();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(JComboBoxGrupos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(JComboBoxGrupos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
