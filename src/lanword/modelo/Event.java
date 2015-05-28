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

/**
 * Representa a un evento lanzado por un objeto en su cambio de estado. En este evento
 * se guarda el nombre del evento, el dato nuevo y el dato antiguo si lo hubiera.
 * 
 * Ambos datos del evento son opcionales. Ya que el cambio de estado de un objeto 
 * puede ser su creación o desctrucción.
 *
 * @author Víctor Manuel Ortiz Guardeño
 * @version 1.0
 * @date 31/03/2015
 */

public class Event {
    private String nombre;
    private Object data;
    private Object dataOld;

    /**
     * Construye un evento con un nombre.
     * 
     * @param nombre Nombre del evento.
     */
    
    public Event(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Construye un evento con un nombre, un dato nuevo y un dato antiguo.
     * 
     * @param nombre   Nombre del evento.
     * @param data     Dato nuevo.
     * @param dataOld  Dato antiguo.
     */
    
    public Event(String nombre, Object data, Object dataOld) {
        this.nombre = nombre;
        this.data = data;
        this.dataOld = dataOld;
    }

    /**
     * Permite conocer el nombre del evento.
     * 
     * @return Nombre del evento.
     */
    
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Permite conocer el dato nuevo del objeto.
     * 
     * @return Dato nuevo.
     */

    public Object getData() {
        return data;
    }
    
    /**
     * Permite conocer el dato antiguo del objeto.
     * 
     * @return Dato antiguo.
     */

    public Object getDataOld() {
        return dataOld;
    }
    
}
