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
import lanword.modelo.Grupo;
import lanword.modelo.Palabra;

/**
 * Esta interfaz contiene las consultas necesarias para el sistema para adminsitrar
 * los grupos.
 *
 * @author Víctor Manuel Ortiz Guardeño
 * @version 1.0
 * @date 31/04/2015
 */

public interface IBDGestionGrupos {
    
    /**
     * Inserta en la base de datos un grupo si este no existiera.
     * 
     * @param grupo Grupo que se quiere insertar en la base de datos.
     * @throws SQLException 
     */
    
    public abstract void anyadir(Grupo grupo) throws SQLException;
    
    /**
     * Borra un grupo de la base de datos.
     * 
     * @param grupo Grupo que se quiere borrar.
     * @throws SQLException 
     */
    
    public abstract void borrar(Grupo grupo) throws SQLException;
    
    /**
     * Busca en la base de datos todos los grupos del sistema.
     * 
     * @return Lista con todos los grupos.
     * @throws SQLException 
     */
    
    public abstract ArrayList<Grupo> buscar() throws SQLException;
    
    /**
     * Busca en la base de datos un grupo por su nombre.
     * 
     * @param nombre Nombre del grupo.
     * @return Grupo con nombre pasado como argumento.
     * @throws SQLException 
     */
    
    public abstract Grupo buscar(String nombre) throws SQLException;
    
    /**
     * Actualiza el nombre del grupo, si este está escrito en la base de datos.
     * 
     * @param nombre     Nuevo nombre del grupo.
     * @param an_nombre  Antiguo nombre del grupo.
     */
    
    public abstract void actualizarNombre(String nombre, String an_nombre) throws SQLException;
    
    /**
     * Actualiza la descripción de un grupo. La descripción la obtiene del objeto grupo,
     * por lo tanto, actualiza mediante setDescripcion() antes de llamar a este método.
     * 
     * @param grupo Grupo con descripción nueva.
     */
    
    public abstract void actualizarDescripcion(Grupo grupo) throws SQLException;
    
    /**
     * Actualiza una nueva palabra agrupada en el grupo pasado como argumento. para ello
     * es necesario conocer tanto el grupo, como la palabra añadida.
     * 
     * @param grupo    Grupo al que se la ha añadido una palabra.
     * @param palabra  Palabra que se ha añadido al grupo.
     */
    
    public abstract void actualizarAgruparPalabra(Grupo grupo, Palabra palabra) throws SQLException;
    
    /**
     * Actualiza el grupo quitándole una palabra agrupada. Se tiene que conocer tanto
     * el grupo como la palabra eliminada, para borrarla de la fuente de datos.
     * 
     * @param grupo    Grupo al que se le ha quitado una palabra.
     * @param palabra  Palabra que se a eliminado del grupo.
     */
    
    public abstract void actualizarDesagruparPalabra(Grupo grupo, Palabra palabra) throws SQLException;
}
