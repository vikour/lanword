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

package lanword.modelo;

import java.util.ArrayList;

/**
 * Representa una palabra de la que se quiere conocer el nombre, el idioma, sus
 * grupos y sus traducciones.
 * 
 * @author Víctor Manuel Ortiz Guardeño
 * @version 1.1
 * @date 31/03/2015
 */
public class Palabra extends ClasePersistente {
    
    private String nombre;
    private Idioma idioma;
    private ArrayList<Grupo> grupos;
    private ArrayList<Palabra> traducciones;

    /**
     * Construye una palabra con un nombre y un idioma.
     * 
     * @param nombre   Nombre de la palabra.
     * @param idioma   Idioma de la palabra.
     * @throws IllegalArgumentException
     */
    public Palabra(String nombre, Idioma idioma) throws IllegalArgumentException {
        
        comprobarIdioma(idioma);
        comprobarNombre(nombre);
        this.nombre = firstLetterToUpper(nombre);
        this.idioma = idioma;
        this.grupos = new ArrayList<>();
        this.traducciones = new ArrayList<>();
        setId(new Object[] {this.nombre, this.idioma});
    }
    
    /**
     * Permite conocer el nombre de la palabra.
     * 
     * @return Nombre de la palabra.
     */
    
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Permite cambiar el nombre de la palabra, el nombre no puede ser ni nulo, ni vacío.
     * 
     * @param nombre  Nuevo nombre de la palbra.
     * @throws IllegalArgumentException 
     */

    public void setNombre(String nombre) throws IllegalArgumentException {
        comprobarNombre(nombre);
        this.nombre = firstLetterToUpper(nombre);
    }
    
    /**
     * Permite conocer el idioma de la palabra.
     * 
     * @return Idioma de la palabra.
     */

    public Idioma getIdioma() {
        return idioma;
    }

    /**
     * Permite cambiar el idioma de la palarba, el idioma no puede ser ni el mismo
     * que tiene la palabra, ni nulo.
     * 
     * PRECAUCION: Si cambias el idioma de la palabra, se borrar todas las traducciones.
     * 
     * @param idioma Nuevo idioma par a la palabra.
     */
    
    public void setIdioma(Idioma idioma) {
        comprobarIdioma(idioma);
        
        for (Palabra traduccion : traducciones)
            traduccion.quitarTraduccion(this);
            
        this.traducciones = new ArrayList<>();
        this.idioma = idioma;
    }
    
    /**
     * Permite añadir un grupo a la palabra. Este grupo no puede formar parte ya 
     * de la palabra, ni ser nulo.
     * 
     * @param grupo Nuevo grupo para la palabra.
     * @throws IllegalArgumentException 
     */
    
    public void agrupar(Grupo grupo) throws IllegalArgumentException {
        
        if (grupo == null)
            throw new IllegalArgumentException("El grupo no puede ser nulo.");
        else if (agrupado(grupo))
            throw new IllegalArgumentException("La palabra \"" + nombre + "\" ya está agrupada en el grupo \"" + grupo + "\"");

        grupos.add(grupo);
        
        if (!grupo.agrupa(this))
            grupo.agrupar(this);

    }
    
    /**
     * Permite conocer si el grupo esta agrupado en un gurpo pasado como argumento.
     * 
     * @param grupo  Grupo que se quiere saber si la palabra está agrupada.
     * @return Verdadero o falso.
     */
    
    public boolean agrupado(Grupo grupo) {
        
        for (Grupo g : grupos) 
            
            if (g.equals(grupo))
                return true;
        
        return false;
    }
    
    /**
     * Permite desagrupar una palabra de un grupo en concreto, este no podrá ser nulo,
     * ni la palabra no estar agrupada en él.
     * 
     * @param grupo Grupo que se quiere desagrupar.
     */
    
    public void desagrupar(Grupo grupo) {
        
        if (grupo == null)
            throw new IllegalArgumentException("El grupo no puede ser nulo.");
        else if (!agrupado(grupo))
            throw new IllegalArgumentException("La palabra no está agrupada en el grupo \"" + grupo + "\"");
        
        grupos.remove(grupo);
        
        if (grupo.agrupa(this))
            grupo.desagrupar(this);
        
    }
    
    /**
     * Permite conocer todos los grupos de la palabra.
     * 
     * @return Grupos de la palabra.
     */
    
    public ArrayList<Grupo> getsGrupos() {
        return grupos;
    }
    
    /**
     * Permite añadir una traducción a la palabra actual, esta no podrá ser nula, ni
     * estar ya añadida.
     * 
     * @param traduccion  Traducción que se quiere añadir.
     * @throws IllegalArgumentException 
     */
    
    public void anyadirTraduccion(Palabra traduccion) throws IllegalArgumentException {
        
        if (traduccion == null)
            throw new IllegalArgumentException("La traducción no puede ser nula.");
        else if (traduccion.getIdioma().equals(idioma))
            throw new IllegalArgumentException("No se puede traducir al mismo idioma.");
        else if (seTraduceEn(traduccion))
            throw new IllegalArgumentException("Ya está traducida en \"" + traduccion + "\"");
        
        traducciones.add(traduccion);        
        
        if (!traduccion.seTraduceEn(this))
            traduccion.anyadirTraduccion(this);

    }
    
    /**
     * Permite conocer si la palabra se traduce en la pasada como argumento, esta no
     * podrá ser nula.
     * 
     * @param traduccion  Traducción.
     * @return Verdadero o falso.
     */
    
    public boolean seTraduceEn(Palabra traduccion) {
        
        if (traduccion == null)
            throw new IllegalArgumentException("La traducción no puede ser nula.");
        
        for (Palabra t : traducciones)
            
            if (traduccion.equals(t))
                return true;
        
        return false;
    }
    
    /**
     * Permite quitar una traducción de la palabra, esta no pude ser nula, ni no estar
     * la palabra traducida ya en la traducción pasada como argumento.
     * 
     * @param traduccion Traducción que se quiere eliminar.
     * @throws IllegalArgumentException 
     */
    
    public void quitarTraduccion(Palabra traduccion) throws IllegalArgumentException {
        
        if (traduccion == null)
            throw new IllegalArgumentException("La traducción no puede ser nula.");
        else if (!seTraduceEn(traduccion))
            throw new IllegalArgumentException("\"" + nombre + "\" no se traduce en \"" + traduccion +"\"");
        
        traducciones.remove(traduccion);        
        
        if (traduccion.seTraduceEn(traduccion))
            traduccion.quitarTraduccion(this);

    }
    
    /**
     * Permite conocer todas las traducciones.
     * 
     * @return Lista de traducciones.
     */
    
    public ArrayList<Palabra> getTraducciones()  {
        return traducciones;
    }
    
    /**
     * Permite conocer todas las traducciones de la palabra en un idioma concreto.
     * 
     * @param idioma Idioma del que se quiere conocer las traducciones.
     * @return Las traducciones de la palabra en el idioma especificado.
     */
    
    public ArrayList<Palabra> getTraducciones(Idioma idioma) {
        ArrayList<Palabra> palabras = new ArrayList<>();
        
        for (Palabra traduccion : traducciones)
            
            if (traduccion.getIdioma().equals(idioma))
                palabras.add(traduccion);
        
        return palabras;
    }

    @Override
    public boolean equals(Object obj) {
        
        if (obj instanceof Palabra) {
            Palabra p = (Palabra) obj;
        
            return p.getNombre().matches(nombre);
        }
        else
            return false;
        
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public void destroy() {
        super.destroy(); 
        
        // Cuando se destruye una palabra, se borran sus relaciones con las demás palabras.
        for (Palabra traduccion : traducciones)
            traduccion.quitarTraduccion(this);
        
    }
    
    private static boolean comprobarNombre(String nombre) throws IllegalArgumentException {
        
        if (nombre == null)
            throw new IllegalArgumentException("El nombre no puede ser nulo.");
        else if (nombre.isEmpty())
            throw new IllegalArgumentException("El nombre no puede estar vacío.");

        return true;
    }
    
    private static boolean comprobarIdioma(Idioma idioma) throws IllegalArgumentException {
        
        if (idioma == null)
            throw new IllegalArgumentException("El idioma no puede ser nulo.");

        return true;
    }
    
    private static String firstLetterToUpper(String cadena) {
        return Character.toUpperCase(cadena.charAt(0)) + cadena.substring(1);
    }
    
}
