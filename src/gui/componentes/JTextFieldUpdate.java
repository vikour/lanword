package gui.componentes;

import javax.swing.JTextField;

/**
 * Esta clase es una derivación de JTextField para hacer soporte a la actualización.
 * Permite establecer un valor inicial y mediante el metodo isChangedValue(), se puede
 * ver si se ha cambiado el valor.
 *
 * @author Víctor Manuel Ortiz Guardeño
 * @version 1.0
 * @date 6 de Abril del 2015
 */

public class JTextFieldUpdate extends JTextField {
    private String initial;

    public JTextFieldUpdate() {
        initial = null;
    }

    /**
     * Permite conocer si el campo ha sido actualizado. Para ello, compara el valor
     * inical con el actual del campo de texto.
     * 
     * @return Verdadero si ha sido cambiado; falso si no.
     */
    
    public boolean isChangedValue() {
        
        if (!getText().matches(initial))
            return true;
        
        return false;
    }
    
    /**
     * Permite establecer el valor inicial y este valor es el que se muestra.
     * 
     * @param inital Valor inicial del campo de texto.
     */

    public void setInitalValue(String initial) {
        this.initial = initial;
        setText(this.initial);
    }

    /**
     * Permite conocer el valor inicial del campo de texto.
     * 
     * @return Valor inicial.
     */
    
    public String getInitalValue() {
        return initial;
    }
    
}
