/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lanword.interfaces.bd;

import java.sql.SQLException;
import java.util.ArrayList;
import lanword.modelo.Grupo;
import lanword.modelo.Idioma;
import lanword.modelo.Palabra;

/**
 * Esta inferfaz guarda todas las funciones requeridas por el sistema para gestionar
 * las palabras.
 *
 * @author Víctor Manuel Ortiz Guardeño
 * @verion 1.0
 * @date 31/05/2015
 */

public interface IBDGestionPalabras {
    
    /**
     * Inserta una palabra en la base de datos.
     * 
     * @param palabra Nueva palabra.
     * 
     * @throws SQLException 
     */
    
    public abstract void anyadir(Palabra palabra) throws SQLException;
    
    /**
     * Borra de la base de datos una palabra.
     * 
     * @param palabra Palabra existente.
     * @throws SQLException 
     */
    
    public abstract void borrar(Palabra palabra) throws SQLException;

    /**
     * Busca en la base de datos una palabra por su nombre.
     * 
     * @param nombre Nombre de la palabra.
     * @return Palabra con nombre buscado o null.
     * @throws SQLException 
     */
    
    public abstract Palabra buscar(String nombre, Idioma idioma) throws SQLException;
    
    /**
     * Busca en la base de datos todas las palabras filtradas por nombre, grupo o idioma.
     * Si no se quiere buscar por alguno de los parámetros, se deja a NULL.
     * 
     * @param nombre   Nombre para filtrar las palabras.
     * @param grupo    Grupo para filtrar las palabras.
     * @param idioma   Idioma para filtrar las palabras.
     * 
     * @return  Lista de palabras filtradas.
     * 
     * @throws SQLException 
     */
    
    public abstract ArrayList<Palabra> buscar(String nombre, Grupo grupo, Idioma idioma)
            throws SQLException;
    
    /**
     * Busca en la base de datos todas las palabras que no contengan el grupo
     * pasado como argumento.
     * 
     * @param grupo  Grupo en el que no estén agrupadas las palabras.
     * @return Lista de palabras filtradas.
     * @throws SQLException 
     */
    
    public abstract ArrayList<Palabra> buscarSinGrupo(Grupo grupo)
            throws SQLException;
    
    /**
     * Buscar en la base de datos las posibles traducciones de la palabra en un 
     * idioma. Estas son las palabras que no están traducidas en el idioma pasado 
     * como argumento, de la palabra pasada como argumento.
     * 
     * @param palabra  Palabra de la que se quiere conocer las posibles traducciones.
     * @param idioma   Idioma del que se quieren conocer las posibles traducciones.
     * @return Lista de posibles traducciones.
     * @throws SQLException 
     */
    
    public abstract ArrayList<Palabra> buscarPosiblesTraducciones(Palabra palabra, Idioma idioma)
            throws SQLException;
    
    /**
     * Buscar en la base de datos palabras aleatorias del idioma pasado como argumento,
     * que tengan traducción en el idioma objetivo pasado como argumento.
     * 
     * @param idioma     Idioma de las palabras.
     * @param objetivo   Idioma de las traducciones de la palabra.
     * @return Lista de palabras con traducción en el idioma objetivo.
     * @throws SQLException 
     */
    
    public abstract ArrayList<Palabra> buscarAleatoriamente(Idioma idioma, Idioma objetivo)
            throws SQLException;
    
    /**
     * Busca en la base de datos palabras aleatorias del grupo pasado como argumento,
     * que tengan traducción en el idioma objetivo pasado como argumento.
     * 
     * @param grupo     Grupo de las palabras.
     * @param objetivo  Idioma de las traducciones de las palabra.
     * @return  Lista de palabras con traducción en el idioma objetivo.
     * @throws SQLException 
     */
    
    public abstract ArrayList<Palabra> buscarAleatoriamente(Grupo grupo, Idioma objetivo)
            throws SQLException;
    
    /**
     * Actualiza el nombre de la palabra en la base de datos.
     * 
     * @param palabra Palabra actualizada previamente.
     * @param nombre_anterior Nombre anterior.
     * @throws SQLException
     */
    
    public abstract void actualizarNombre(Palabra palabra, String nombre_anterior)
            throws SQLException;
    
    /**
     * Actualiza el idioma de la palabra en la base de datos. Esto provocará que se borren 
     * las traducciones del nuevo idioma.
     * 
     * @param palabra Palabra de la que se quiere actualizar el idioma.
     * @param idioma_anterior El idioma anterior.
     * @throws SQLException 
     */
    
    public abstract void actualizarIdioma(Palabra palabra, Idioma idioma_anterior)
            throws SQLException;
    
    /**
     * Añade una nueva traducción a la palabra en la base de datos.
     * 
     * @param palabra    Palabra que se ha añadido una traducción
     * @param traduccion Nueva traducción
     * @throws SQLException 
     */
    
    public abstract void actualizarNuevaTraduccion(Palabra palabra, Palabra traduccion)
            throws SQLException;
    
    /**
     * Elimina una traducción de la palabra en la base de datos.
     * 
     * @param palabra     Palabra que se ha eliminado una traducción.
     * @param traduccion  Traducción eliminada.
     * @throws SQLException 
     */
    
    public abstract void actualizarTraduccionEliminada(Palabra palabra, Palabra traduccion)
            throws SQLException;
    
    /**
     * Agrupa la palabra en la base de datos.
     * 
     * @param palabra  Palabra que ha sido agrupada.
     * @param grupo    Grupo en el que se a agrupado.
     * @throws SQLException 
     */
    
    public abstract void actualizarNuevoGrupo(Palabra palabra, Grupo grupo)
            throws SQLException;
    
    /**
     * Desagrupa la palabra en la base de datos.
     * 
     * @param palabra  Palabra que ha sido desagrupada.
     * @param grupo    Grupo en el que se ha desagrupado la palabra.
     * @throws SQLException 
     */
    
    public abstract void actualizarGrupoEliminado(Palabra palabra, Grupo grupo)
            throws SQLException;
    
}
