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

import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import lanword.modelo.Palabra;

/**
 *
 * @author vikour
 */

public abstract class JListPalabras extends JList<Palabra>{
    private Model model;

    public JListPalabras() {
        model = new Model();
        setModel(model);
    }
    
    @Override
    public void updateUI() {
        model = new Model();
        setModel(model);
        super.updateUI();
        clearSelection();
    }
    
    public abstract ArrayList<Palabra> getsPalabras();
    
    public String getFormatDisplay(Palabra palabra) {
        return palabra.getNombre();
    }
    
    private class Model implements ListModel<Palabra> {
        ArrayList<Palabra> palabras;

        public Model() {
            palabras = getsPalabras();
        }
        
        @Override
        public int getSize() {
            return palabras.size();
        }

        @Override
        public Palabra getElementAt(int index) {
            return palabras.get(index);
        }
        
        public Palabra getWordAt(int index) {
            return palabras.get(index);
        }

        @Override
        public void addListDataListener(ListDataListener l) {
            // Do nothing
        }

        @Override
        public void removeListDataListener(ListDataListener l) {
            // Do nothing
        }
        
    }
}
