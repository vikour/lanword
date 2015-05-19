/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
