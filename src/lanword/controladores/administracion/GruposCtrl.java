package lanword.controladores.administracion;

import lanword.modelo.Grupo;

/**
 * Esta clase sirve de soporte para todas las acciones previstas en el diseño, del 
 * manejo de los grupos. Esta clase permite marcar un grupo en la que se van a 
 * realizar varias operaciones entre distintas vistas. 
 * 
 * Debido a que esta clase va a ser utilizada por varias vistas, se ha elegido el 
 * diseño Singleton.
 *
 * @author Víctor Manuel Ortiz Guardeño
 * @version 1.0
 * @date 5 de Abril de 2014
 */
public class GruposCtrl {
    public static GruposCtrl instance;
    public Grupo seleccionado;
    
    
    public static GruposCtrl getInstance() {
        
        if (instance == null)
            instance = new GruposCtrl();
        
        return instance;
    }
    
    private GruposCtrl() {}

    /**
     * Permite conocer el grupo seleccionado para otra vista.
     * 
     * @return Grupo seleccionado.
     */
    
    public Grupo getSeleccionado() {
        return seleccionado;
    }
    
    /**
     * Hace visible el grupo seleccionado para todas las demás vistas.
     * 
     * @param seleccionado Grupo seleccionado.
     */

    public void setSeleccionado(Grupo seleccionado) {
        this.seleccionado = seleccionado;
    }
    
}
