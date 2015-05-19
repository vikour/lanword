package lanword.modelo;

import java.util.ArrayList;

/**
 * Esta clase representa una unidad organizativa de palabras. Por lo tanto, agrupa palabras,
 * y se quiere conocer su nombre y una descripción, opcionalmente.
 *
 * @author Víctor Manuel Ortiz Guardeño
 * @version 1.0
 * @date 31/03/2015
 */
public class Grupo extends ClasePersistente {
  
    private String nombre;
    private String descripcion;
    private ArrayList<Palabra> palabras;

    /**
     * Construye un grupo con un nombre y una descripción.
     * 
     * @param nombre         Nombre del grupo.
     * @param descripcion    Descripción del grupo (opcional).
     * @throws IllegalArgumentException 
     */
    
    public Grupo(String nombre, String descripcion) throws IllegalArgumentException {
        comprobarNombre(nombre);
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.palabras = new ArrayList<>();
        setId(new Object [] {this.nombre});
    }
    
    /**
     * Premite conocer el nombre del grupo.
     * 
     * @return Nombre del grupo.
     */

    public String getNombre() {
        return nombre;
    }

    /**
     * Permite cambiar el nombre.
     * 
     * @param nombre Nombre del grupo.
     * @throws IllegalArgumentException
     */
    
    public void setNombre(String nombre) throws IllegalArgumentException{
        comprobarNombre(nombre);
        this.nombre = nombre;
        setId(new Object[]{this.nombre});
    }
    
    /**
     * Permite conocer la descripción del grupo.
     * 
     * @return Descripción del grupo.
     */

    public String getDescripcion() {
        return descripcion;
    }
    
    /**
     * Permite cambiar la descripción.
     * 
     * @param descripcion Nueva descripción.
     */

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    /**
     * Permite agrupar una palabra, esta no podrá ser nula, ni no estar agrupada ya 
     * antes en el grupo.
     * 
     * @param palabra Nueva palabra para agrupar.
     * @throws IllegalArgumentException 
     */
    
    public void agrupar(Palabra palabra) throws IllegalArgumentException {
        
        if (palabra == null)
            throw new IllegalArgumentException("La palabra no puede ser nula.");
        else if (agrupa(palabra))
            throw new IllegalArgumentException("La palabra ya esta agrupada.");
        
        palabras.add(palabra);        
        
        if (!palabra.agrupado(this))
            palabra.agrupar(this);

    }
    
    /**
     * Permite desagrupar una palabra del grupo, esta no podrá ser ni nula, ni no estar
     * agrupada en el grupo.
     * 
     * @param palabra Palabra agrupada en el grupo.
     * @throws IllegalArgumentException 
     */
    
    public void desagrupar(Palabra palabra) throws IllegalArgumentException {
        
        if (palabra == null)
            throw new IllegalArgumentException("La palabra no puede ser nula.");
        else if (!agrupa(palabra))
            throw new IllegalArgumentException("La palabra no está agrupada.");
        
        palabras.remove(palabra);
        
        if (palabra.agrupado(this))
            palabra.desagrupar(this);

    }
    
    /**
     * Permite conocer si una palabra esta agrupada ya, en el grupo.
     * 
     * @param palabra Palabra que puede estar agrupada.
     * @return Verdadero o falso.
     */
    
    public boolean agrupa(Palabra palabra) {
        
        for (Palabra p : palabras)
            
            if (p.equals(palabra))
                return true;
        
        return false;
    }
    
    /**
     * Permite conocer todas las palabras agrupadas en el grupo.
     * 
     * @return Lista de palabras agrupadas.
     */
    
    public ArrayList<Palabra> getsPalabras() {
        return palabras;
    }

    @Override
    public boolean equals(Object obj) {
        
        if (obj instanceof Grupo) {
            Grupo g = (Grupo) obj;
            
            return g.getNombre().matches(nombre);
        }
        else
            return false;
        
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    public static boolean comprobarNombre(String nombre) {
        
        if (nombre == null)
            throw new IllegalArgumentException("El nombre no puede ser nulo.");
        else if (nombre.isEmpty())
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        
        return true;
    }
}
    
 
