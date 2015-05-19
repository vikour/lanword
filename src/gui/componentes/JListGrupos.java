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
import lanword.modelo.Grupo;

/**
 *
 * @author vikour
 */

public abstract class JListGrupos extends JList<Grupo>{
    private Model model;

    public JListGrupos() {
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
    
    public abstract ArrayList<Grupo> getsGrupos();
    
    public String getFormatDisplay(Grupo grupo) {
        return grupo.getNombre();
    }
    
    private class Model implements ListModel<Grupo> {
        ArrayList<Grupo> grupos;

        public Model() {
            grupos = getsGrupos();
        }
        
        @Override
        public int getSize() {
            return grupos.size();
        }

        @Override
        public Grupo getElementAt(int index) {
            return grupos.get(index);
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
