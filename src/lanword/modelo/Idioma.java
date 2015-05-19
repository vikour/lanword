package lanword.modelo;

import java.util.Observer;
import lanword.interfaces.bd.BDResolver;

/**
 * Representa a un idioma, de este queremos saber su nombre.
 *
 * @author Víctor Manuel Ortiz Guardeño
 * @version 1.0
 * @date 31/03/2015
 */

public class Idioma extends ClasePersistente {
    private String nombre;
    
    public static class Events {
        public static final String UPDATE_NOMBRE = "UPDATE_NOMBRE";
    }
    
    /**
     * Constructor por defecto.
     */
    public Idioma() {};
    
    /**
     * Construye un idoma con un nombre.
     * 
     * @param nombre Nombre del idioma.
     */
    
    public Idioma(String nombre) throws IllegalArgumentException {
        comprobarNombre(nombre);
        this.nombre = nombre;
        setId(new Object [] {this.nombre});
    }
    
    /**
     * Establece un nuevo nombre al idioma.
     * 
     * @param nombre Nombre del idioma.
     */
    
    public void setNombre(String nombre) throws IllegalArgumentException {
        comprobarNombre(nombre);
        
        if (!nombre.matches(this.nombre)) {
            setChanged();
            setId(new Object[] {this.nombre});
            this.nombre = nombre;
        }
    }
    
    /**
     * Permite conocer el nombre del idioma.
     * 
     * @return Nombre del idioma.
     */
    
    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    @Override
    public boolean equals(Object obj) {
        Idioma i;
        
        if (obj instanceof Idioma) {
            i = (Idioma) obj;
            
            if (i.getNombre().matches(nombre))
                return true;
            else
                return false;
            
        }
        else
            return false;

    }
    
    private static void comprobarNombre(String nombre) throws IllegalArgumentException {
        
        if (nombre == null) throw new IllegalArgumentException("El nombre no puede ser nulo");
        
        if (nombre.isEmpty()) throw new IllegalArgumentException("El nombre no puede estar vacío");
        
    }

    
}
