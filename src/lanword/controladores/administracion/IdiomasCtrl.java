package lanword.controladores.administracion;

import lanword.modelo.Idioma;

/**
 * Esta clase sirve de soporte para todas las acciones previstas en el diseño, del 
 * manejo de los idiomas. Esta clase permite marcar un idioma en la que se van a 
 * realizar varias operaciones entre distintas vistas. 
 * 
 * Debido a que esta clase va a ser utilizada por varias vistas, se ha elegido el 
 * diseño Singleton.
 *
 * @author Víctor Manuel Ortiz Guardeño
 * @version 1.0
 * @date 5 de Abril de 2014
 */
public class IdiomasCtrl {
    public static IdiomasCtrl instance;
    public Idioma seleccionado;
    
    
    public static IdiomasCtrl getInstance() {
        
        if (instance == null)
            instance = new IdiomasCtrl();
        
        return instance;
    }
    
    private IdiomasCtrl() {}

    /**
     * Permite conocer el idoma seleccionado para otra vista.
     * 
     * @return Idioma seleccionado.
     */
    
    public Idioma getSeleccionado() {
        return seleccionado;
    }
    
    /**
     * Hace visible el idioma seleccionado para todas las demás vistas.
     * 
     * @param seleccionado Idioma seleccionado.
     */

    public void setSeleccionado(Idioma seleccionado) {
        this.seleccionado = seleccionado;
    }
    
}

