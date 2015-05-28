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
package lanword.interfaces.bd;

import java.sql.SQLException;
import java.util.ArrayList;
import lanword.modelo.Event;
import lanword.modelo.Idioma;

/**
 * Esta interfaz recoge toda las funciones que el sistema de Lanword necesita para
 * obtener información de la fuente de datos.
 *
 * @author Víctor Manuel Ortiz Guardeño
 * @version 1.0
 * @date 31/03/2015
 */

public interface IBDGestionIdiomas {

    /**
     * Permite añadir un idioma a la base de datos.
     * 
     * @param idioma Un nuevo idioma para la base de datos.
     * @throws SQLException Si el idioma existiera.
     */
    
    public abstract void anyadir(Idioma idioma) throws SQLException;
    
    /**
     * Permite borrar un idoma de la base de datos.
     * 
     * @param idioma   Idioma que se quiere borrar.
     * @throws SQLException Si el idioma no existiera.
     */
    
    public abstract void borrar(Idioma idioma) throws SQLException;
    
    /**
     * Permite conocer todos los idiomas de la base de datos.
     * 
     * @return Todos los idiomas de la base de datos.
     * @throws SQLException 
     */
    
    public abstract ArrayList<Idioma> buscar() throws SQLException;
    
    /**
     * Permite actualizar el nombre de un idioma.
     * 
     * @param nombre   Nombre nuevo del idioma.
     * @param nombre_anterior Nombre anterior del idioma.
     * 
     * @throws SQLException 
     * @throws IllegalArgumentException Si el evento fuera nulo o no tuviera dato nuevo ni antiguo.
     */
    
    public abstract void actualizarNombre(String nombre, String nombre_anterior) 
    throws SQLException, IllegalArgumentException;
    
}
