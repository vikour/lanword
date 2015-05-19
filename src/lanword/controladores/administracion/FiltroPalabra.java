package lanword.controladores.administracion;

import java.util.Observable;
import lanword.modelo.Grupo;
import lanword.modelo.Idioma;

/**
 * Esta clase agrupa lo que se puede filtrar en un listado de palabras. En este caso,
 * se podrá filtrar por cadena en el nombre de la palabra, su grupo y su idioma.
 *
 * @author Víctor Manuel Ortiz Guardeño
 * @version 1.0
 * @date 5 de Abril del 2014
 */

public class FiltroPalabra extends Observable {
    private String nombre;
    private Idioma idioma;
    private Grupo grupo;

    /**
     * Construye un filtro vacío.
     */
    public FiltroPalabra() {
    }

    /**
     * Construye un filtro con todos sus parámetros.
     * 
     * @param nombre  Cadena que contiene el nombre de las palabras.
     * @param idioma  Idioma a filtrar.
     * @param grupo   Grupo a filtrar.
     */
    
    public FiltroPalabra(String nombre, Idioma idioma, Grupo grupo) {
        this.nombre = nombre;
        this.idioma = idioma;
        this.grupo = grupo;
    }
    
    /**
     * Permite conocer la cadena que contiene el nombre.
     * 
     * @return Cadena para el nombre del filtro.
     */

    public String getNombre() {
        return nombre;
    }
    
    /**
     * Permite establecer la cadena de filtro por nombre.
     * 
     * @param nombre Cadena de filtro por nombre.
     */

    public void setNombre(String nombre) {
        this.nombre = nombre;
        setChanged();
        notifyObservers();
    }
    
    /**
     * Permite conocer el idioma del filtro.
     * 
     * @return Idioma del filtro.
     */

    public Idioma getIdioma() {
        return idioma;
    }
    
    /**
     * Permite establecer un nuevo idioma para el filtro.
     * 
     * @param idioma Idioma por el que se va a filtrar.
     */

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
        setChanged();
        notifyObservers();
    }
    
    /**
     * Permite conocer el grupo del filtro.
     * 
     * @return Grupo filtrado.
     */

    public Grupo getGrupo() {
        return grupo;
    }
    
    /**
     * Permite establecer un nuevo grupo para el filtro.
     * 
     * @param grupo Nuevo grupo para el filtro.
     */

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
        setChanged();
        notifyObservers();
    }
    
}
