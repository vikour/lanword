/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
