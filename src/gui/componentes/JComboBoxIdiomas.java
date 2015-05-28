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
import javax.swing.MutableComboBoxModel;
import lanword.interfaces.bd.BDManagment;
import lanword.interfaces.bd.BDResolver;
import lanword.modelo.Idioma;

/**
 *
 * @author vikour
 */
public class JComboBoxIdiomas extends JComboBox<Idioma>{
    private ComboModel model;
    private boolean nullable;
    
    public JComboBoxIdiomas(boolean nullable) {
        try {
            model = new ComboModel();            
            this.nullable = nullable;
            model.updateIdiomas();
            setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(JComboBoxIdiomas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public JComboBoxIdiomas(boolean nullable, Idioma ignorado) {
        try {
            model = new ComboModel(ignorado);            
            this.nullable = nullable;
            model.updateIdiomas();
            setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(JComboBoxIdiomas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public JComboBoxIdiomas(boolean nullable, Idioma ignorado, Idioma valueNull) {
        try {
            model = new ComboModel(ignorado, valueNull);
            this.nullable = nullable;
            model.updateIdiomas();
            setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(JComboBoxIdiomas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Idioma getValueNullIdioma() {
        return model.getNulo();
    }
    
    @Override
    public void updateUI() {
        
        try {
            if (model != null) {
                model.updateIdiomas();
            }
            
            super.updateUI(); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(JComboBoxIdiomas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public class ComboModel extends AbstractListModel<Idioma> implements MutableComboBoxModel<Idioma>{
        private ArrayList<Idioma> idiomas;
        private Idioma selected;
        private Idioma nulo;
        private Idioma ignorado;

        public ComboModel() {
            idiomas = new ArrayList<>();
            selected = null;
            nulo = new Idioma("Ninguno");
            ignorado = null;
        }
        
        public ComboModel(Idioma ignorado) {
            idiomas = new ArrayList<>();
            selected = null;
            nulo = new Idioma("Ninguno");
            this.ignorado = ignorado;
        }
        
        public ComboModel(Idioma ignorado, Idioma nulo) {
            idiomas = new ArrayList<>();
            selected = null;
            this.nulo = nulo;
            this.ignorado = ignorado;
        }
        
        public void setNulo(Idioma idioma) {
            nulo = idioma;
        }
        
        public Idioma getNulo() {
            return nulo;
        }

        public ArrayList<Idioma> getIdiomas() {
            return idiomas;
        }
        
        public void updateIdiomas() throws SQLException {
            try {
                BDManagment bd = BDResolver.getInstance();
                System.out.println("ignorado = " + ignorado);
                
                idiomas = bd.idiomas.buscar();
                
                if (ignorado != null) 
                    idiomas.remove(ignorado);
                
                if (selected == null && nullable)
                    selected = nulo;
                else if (!idiomas.isEmpty())
                    selected = idiomas.get(0);
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(JComboBoxIdiomas.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(JComboBoxIdiomas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        @Override
        public int getSize() {
            
            if (nullable)
                return idiomas.size() + 1;
            else
                return idiomas.size();
        }

        @Override
        public Idioma getElementAt(int index) {
            
            if (nullable)
                
                if (index == 0 )
                    return nulo;
                else 
                    return idiomas.get(index - 1);
            
            else
                return idiomas.get(index);
        }
        
        @Override
        public void setSelectedItem(Object anItem) {
            
            if (nullable)
                
                if (anItem.equals(nulo))
                    selected = nulo;
                else
                    selected = (Idioma) anItem;

            else
                selected = (Idioma) anItem;
        }

        @Override
        public Object getSelectedItem() {
            return selected;
        }

        @Override
        public void addElement(Idioma item) {
            idiomas.add(item);
        }

        @Override
        public void removeElement(Object obj) {
            idiomas.remove((Idioma) obj);
        }

        @Override
        public void insertElementAt(Idioma item, int index) {
            idiomas.add(index, item);
        }

        @Override
        public void removeElementAt(int index) {
            idiomas.remove(index);
        }
        
    }
    
}
