package lanword.controladores.administracion;

import lanword.modelo.Palabra;

/**
 * Esta clase sirve de soporte para todas las acciones previstas en el diseño, del 
 * manejo de las palabras. Esta clase permite marcar una palabra en la que se van a 
 * realizar varias operaciones entre distintas vistas. 
 * 
 * También tiene un filtro, que será accesible por la vista. Así se podrá modificar
 * y obtener los parámetros de filtro.
 * 
 * Debido a que esta clase va a ser utilizada por varias vistas, se ha elegido el 
 * diseño Singleton.
 *
 * @author Víctor Manuel Ortiz Guardeño
 * @version 1.0
 * @date 5 de Abril de 2014
 */

public class PalabrasCtrl {
    public static PalabrasCtrl instance;

    public Palabra seleccionada;
    public FiltroPalabra filtro;
    

    /**
     * Permite acceder al controlador de palabras.
     * 
     * @return Instancia del controlador de palabras.
     */
    
    public static PalabrasCtrl getInstance() {
        
        if (instance == null)
            instance = new PalabrasCtrl();
        
        return instance;
    }
    
    private PalabrasCtrl() {
        seleccionada = null;
        filtro = new FiltroPalabra();
    }
    
    /**
     * Hace saber a todas las vistas una nueva palabra seleccionada.
     * 
     * @param seleccionada Palabra seleccionada.
     */

    public void setSeleccionada(Palabra seleccionada) {
        this.seleccionada = seleccionada;
    }
    
    /**
     * Permite conocer la palabra seleccionada.
     * 
     * @return Palabra seleccionada, o NULL si no lo hubiera.
     */

    public Palabra getSeleccionada() {
        return seleccionada;
    }
    
    /**
     * Permite acceder al filtro de palabras, este es compartida por todas las vistas
     * con el controlador de palabras.
     * 
     * @return Filtro de palabras.
     */

    public FiltroPalabra getFiltro() {
        return filtro;
    }

}
