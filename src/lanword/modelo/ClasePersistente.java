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
import java.util.Observable;

/**
 * Representa a una clase que permanecerá en memoria con un identificador establecido
 * por el constructor de la clase derivada. También porporciona medios para marcar a las
 * clases para saber si están almacenadas.
 * 
 * Esta clase es útil para tener en memoria siempre una estancia de un objeto.
 * 
 * Para implementar los identificadores de la clase hija, debes de usar el método protegido setId(), y por
 * este se buscará en memoria.
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
    
    @Deprecated
    public static Object buscar(Object [] ids) {
        ObjetoData aux;
        boolean equals;
       
        // Si hay alguna instancia almacenadas en el array...
        if (instancias != null) 
            
            // Por cada instancia de los objetos almacenados ...
            for (int i = 0 ; i < instancias.size() ; i++) {
                /* Obtengo en una variable auxiliar la instancia (por comodidad) y establezco al inicio
                 * que la comparación es verdadera. Para después cambiarla si encuentro alguna diferencia. */
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
     * Buscae en el registro de memoria el objeto de la clase pasada como argumento, y con los ids pasados 
     * como argumentos.
     * 
     * @param nameClass  Nombre de la clase entero. (Es decir, ej. paquete.paquete.clase)
     * @param ids        Uno o más identificadores del objeto buscado.
     * @return El objeto buscado o NULL si no se encontrara.
     */
    
    public static Object buscar(String nameClass, Object ... ids) {
        ObjetoData aux;
        boolean equals;
       
        // Si hay alguna instancia almacenadas en el array, se busca el objeto.
        if (instancias != null) 
            
            // Por cada instancia de los objetos almacenados ...
            for (int i = 0 ; i < instancias.size() ; i++) {
                /* Obtengo en una variable auxiliar la instancia (por comodidad) y establezco al inicio
                 * que la comparación es verdadera. Para después cambiarla si encuentro alguna diferencia. */
                aux = instancias.get(i);
                equals = true;
                
                // Si la clase buscada no es la misma que la de la instancia, se rompe la comparación...
                if (!nameClass.matches(aux.instance.getClass().getName()))
                    equals = false;
                else
                // Si el número de ids buscado, no es igual al del objeto actual, no es el buscado.
                    if (aux.ids.length != ids.length)
                        equals = false;
                
                // Si aun son iguales, por cada id, se comparan y si hay alguno distinto, se rompe la comparación.
                for (int j = 0 ; j < aux.ids.length && equals; j++)

                    if (!aux.ids[j].equals(ids[j]))
                        equals = false;
                
                /* Al final, si todas las condiciones anteriores han sido superadas, significa que el objeto
                 * que el objeto actual es el buscaod. */
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
            
            if (buscar(this.getClass().getName(), this.data.ids) == null) {
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
    
    
    /**
     * Esta clase es una agrupación de información que se quiere conocer de cada objeto.
     * La información que se queire conocer es su id, su instancia y si está almacenada
     * o no.
     */
    
    private class ObjetoData {
        // guarda los identificadores del objeto.
        private Object [] ids;
        // Guarda la instancia del objeto.
        private Object instance;
        // Guarda la marca de almacenamiento.
        private boolean stored;
        
        /**
         * Constructor por defecto.
         */
        public ObjetoData() { 

        }

    }
}
