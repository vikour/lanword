package lanword.modelo;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Representa a una clase que permanecerá en memoria con un identificador establecido
 * por el constructor de la clase derivada.
 * 
 * Esta clase es útil para tener en memoria siempre una estancia de un objeto.
 *
 * @author Víctor Manuel Ortiz Guardeño
 * @version 1.1
 * @date 31/03/2015
 */

public abstract class ClasePersistente extends Observable {
    
    // Guarda todas las estancias de la clase.
    private static ArrayList<ObjetoData> instancias;
    // Guarda los datos de la estancia, contiene el id y el objeto.
    private ObjetoData data;
    
    public static class Events {
        public static String NEW_OBJECT = "NEW_OBJECT";
        public static String DESTROY_OBJECT = "DESTROY_OBJECT";
    }

    /**
     * Constructor de una clase persistente.
     */
    public ClasePersistente() { 
        
        if (instancias == null)
            instancias = new ArrayList<>();
        
    }
    
    /**
     * Busca un objeto en la lista de estancias por su id.
     * 
     * @param o  Identificador de la clase.
     * @return Objeto con identificado pasado como argumento o NULL si no existiera.
     */
    
    public static Object buscar(Object [] ids) {
        ObjetoData aux;
        boolean equals;
       
        if (instancias != null) 
            
            for (int i = 0 ; i < instancias.size() ; i++) {
                aux = instancias.get(i);
                equals = true;
                
                for (int j = 0 ; j < aux.ids.length && equals; j++)
                    
                    if (!aux.ids[j].equals(ids[j]))
                        equals = false;
                
                if (equals)
                    return aux.instance;
                
            }
        
        return null;
    }
    
    /**
     * Establece el ID del objeto y lo añade a la lista de estancias si no existiera.
     * Es necesario usarla en el constructor de la clase que quiere ser persistente.
     * 
     * @param ids Identificador del objeto.
     */
    protected void setId(Object[] ids) {
        
        if (data == null) {
            data = new ObjetoData();
            data.ids = ids;
            data.instance = this;
            
            if (buscar(this.data.ids) == null) {
                instancias.add(data);
                setChanged();
                notifyObservers(new Event(Events.NEW_OBJECT));
            }
            
        }
        else
            data.ids = ids;
    }
    
    /**
     * Destruye el objeto de la memoria.
     */
    
    public void destroy() {
        instancias.remove(data);
        setChanged();
        notifyObservers(new Event(Events.DESTROY_OBJECT));
        System.gc();
    }
    
    public void stored(boolean stored) {
        data.stored = stored;
    }
    
    public boolean isStored() { return data.stored; }
    
    
    
    private class ObjetoData {
        private Object [] ids;
        private Object instance;
        private boolean stored;
        
        public ObjetoData() { 

        }

    }
}
