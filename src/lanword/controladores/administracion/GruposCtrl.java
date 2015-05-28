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
