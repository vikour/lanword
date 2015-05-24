/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    private ArrayList<ArrayList<Palabra>> traducciones;

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
        
        for (ArrayList<Palabra> array : traducciones)
            
            for (Palabra traduccion : array)
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
        boolean traduccionAnyadida = false;
        
        if (traduccion == null)
            throw new IllegalArgumentException("La traducción no puede ser nula.");
        else if (traduccion.getIdioma().equals(idioma))
            throw new IllegalArgumentException("No se puede traducir al mismo idioma.");
        else if (seTraduceEn(traduccion))
            throw new IllegalArgumentException("Ya está traducida en \"" + traduccion + "\"");
        
        for (ArrayList<Palabra> array : traducciones) 
            
            if (array.get(0).getIdioma().equals(traduccion.getIdioma()) && !array.contains(traduccion)){
                array.add(traduccion);
                traduccionAnyadida = true;
            }
        
        if (!traduccionAnyadida) {
            ArrayList<Palabra> array = new ArrayList<>();
            array.add(traduccion);
            traducciones.add(array);
        }
        
        // Aquí se realiza la traducción bidireccional.
        if (!traduccion.seTraduceEn(this))
            traduccion.anyadirTraduccion(this);

        // Comprobación de transitividad.
        if (traducciones.size() > 1)
            
            for (int i = 0 ; i < traducciones.size() ; i++)
                
                for (int j = i + 1 ; j < traducciones.size() ; j++)
                        propagarTransitividad(traducciones.get(i), traducciones.get(j));
        
    }
    
    private void propagarTransitividad(ArrayList<Palabra> b, ArrayList<Palabra> c) {
        
        for (Palabra pb : b)
            
            for (Palabra pc : c)
                
                if (!pb.seTraduceEn(pc))
                    pb.anyadirTraduccion(pc);
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
        
        for (ArrayList<Palabra> array : traducciones)
            
            for (Palabra t : array)
                
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
        boolean rowDeleted = false;
        
        if (traduccion == null)
            throw new IllegalArgumentException("La traducción no puede ser nula.");
        else if (!seTraduceEn(traduccion))
            throw new IllegalArgumentException("\"" + nombre + "\" no se traduce en \"" + traduccion +"\"");
        
        for (int i = 0 ; !traducciones.isEmpty() && i < traducciones.size() ; i++) {
            rowDeleted = false;
        
            for (int j = 0 ;!rowDeleted && j < traducciones.get(i).size() ; j++) {
                
                if (traducciones.get(i).get(j).equals(traduccion)) {
                    traducciones.get(i).remove(j);
                    traduccion.borrarTraduccion(this);
                }
                else {
                    
                    if (traducciones.get(i).get(j).seTraduceEn(traduccion)) {
                        traducciones.get(i).get(j).borrarTraduccion(traduccion);
                        traduccion.borrarTraduccion(traducciones.get(i).get(j));
                    }
                    
                    if (traducciones.get(i).get(j).seTraduceEn(this)) {
                        traducciones.get(i).get(j).borrarTraduccion(this);
                        traducciones.get(i).remove(j);
                    }
                }
                
                if (traducciones.get(i).isEmpty()) {
                    traducciones.remove(i);
                    i--;
                    rowDeleted = true;
                }
            }

        }
    }
    
    /**
     * Borra una traducción de la palabra. Si cuando borra la traducción se borra el array, devuelve true. Esto 
     * es dato útil de conocer cuando se está recorriendo un for. Es decir, si devuelve true, decrementa en 1 la
     * variable contadora.
     * 
     * @param traduccion Traducción que se quiere borrar.
     * @return True si se borra el array de idiomas y false si no.
     */
    
    private boolean borrarTraduccion(Palabra traduccion) {
        boolean founded = false;
        boolean rowDeleted = false;
        
        for (int i = 0; !founded && i < traducciones.size() ; i++)
            
            if (traducciones.get(i).get(0).getIdioma().equals(traduccion.getIdioma())) {
                traducciones.get(i).remove(traduccion);
                
                if (traducciones.get(i).isEmpty()) {
                    traducciones.remove(i);
                    rowDeleted = true;
                }
            }

        return rowDeleted;
    }
    
    /**
     * Permite conocer todas las traducciones.
     * 
     * @return Lista de traducciones.
     */
    
    public ArrayList<Palabra> getTraducciones()  {
        ArrayList<Palabra> out = new ArrayList<>();
        
        for (ArrayList<Palabra> array : traducciones)
            
            for (Palabra traduccion : array)
                out.add(traduccion);
        
        return out;
    }
    
    /**
     * Permite conocer todas las traducciones de la palabra en un idioma concreto.
     * 
     * @param idioma Idioma del que se quiere conocer las traducciones.
     * @return Las traducciones de la palabra en el idioma especificado.
     */
    
    public ArrayList<Palabra> getTraducciones(Idioma idioma) {
        
        for (ArrayList<Palabra> array : traducciones)
            
            if (array.get(0).getIdioma().equals(idioma))
                return array;
        
        return null;
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
        
        for (ArrayList<Palabra> array : traducciones)
            
            for (Palabra traduccion : array)
                traduccion.borrarTraduccion(this);
                
        
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
 
