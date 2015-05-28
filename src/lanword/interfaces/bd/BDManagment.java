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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Observable;
import java.sql.*;

/**
 * Esta clase representa un punto de administración básico abstracto para una base de datos. 
 * Contiene funcionalidad para conectarse, desconectarse, obtener una conexión y crear la 
 * base de datos desde un fichero.
 *
 * @author Víctor Manuel Ortiz Guardeño
 * @date 30 de Marzo del 2015
 * @version 1.0
 */
public abstract class BDManagment extends Observable {
    
    // Configuración para las clases hijas. Es necesario.
    protected static class Configuration {
        // Nombre del fichero de la base de datos, si lo hubiere.
        public static String FILE_BD;
        // URL del driver que se va a usar.
        public static String DRIVER_URL;
        // URL de la base de datos para JDBC.
        public static String URL_DB;
        // Fichero DDL de la base de datos.
        public static String FILE_DDL;
    }
    
    // Intefaz de gestión de grupos.
    public IBDGestionGrupos grupos;
    // Interfaz de gestión de idiomas.
    public IBDGestionIdiomas idiomas;
    // Interfaz de gestión de palabras.
    public IBDGestionPalabras palabras;
    // Conexión JDBC.
    private Connection con;
    
    /**
     * Constructor por defecto, crea la base de datos si no existe.
     * 
     * @throws SQLException  Si hay algún error en el fichero DDL.
     * @throws ClassNotFoundException  Si no se encuentra el driver de la base de datos.
     * @throws IOException Si hay algún problema mientras se lee el fichero DDL.
     */
    protected BDManagment() 
    throws SQLException, ClassNotFoundException, IOException {
        File f;
        
        // Si hay un fichero de la base de datos, se crea si no existe.
        if (Configuration.FILE_BD != null) {
            f = new File(Configuration.FILE_BD);
        
            if (!f.exists())
                crearBD();
        }
        //****************************** NOTA ******************************
        // Aquí iría más código de comprobación para una base de datos en red.
        //******************************************************************
        
    }
    
    /**
     * Conecta con la base de datos y devuelve la conexión de tipo Connection.
     * 
     * @return Conexión con la base de datos.
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    
    public Connection conectar() throws SQLException, ClassNotFoundException {
        
        if (con == null) {
           Class.forName(Configuration.DRIVER_URL);
           con = DriverManager.getConnection(Configuration.URL_DB);
        }
        
        return con;
    }
    
    /**
     * Permite desconectarse de la base de datos.
     * 
     * @throws SQLException 
     */
    
    public void desconectar() throws SQLException {       
        con.close();
        con = null;
    }
    
    /**
     * Crea la estructura de la base de datos.
     * 
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    
    protected void crearBD() 
    throws IOException, SQLException, ClassNotFoundException {
        String line, statement = "";
        BufferedReader br;
        boolean stm_begin = false;
        String end_statement = ";";
        // El fichero en resources debe de ser pasado a temporal.
        File temp = volcarRecursoTemporal(Configuration.FILE_DDL, "ddl_sql");
        
        // Ahora se lee del fichero temporal el DDL.
        br = new BufferedReader(new FileReader(temp));
        conectar(); 
        
        while ( (line = br.readLine()) != null) {
            
            // Si la linea empieza por "--" o está vacía, no se hace nada.
            if (!line.startsWith("--") && !line.isEmpty()) {
                
                /* La sentencia puede tener un BEGIN, entonces, ésta no termina con los ";" que halla
                 * hasta que no se encuentre el END. Por lo tanto, los dos if siguientes sirven para 
                 * controlar esto.*/

                if (line.contains("BEGIN"))
                    stm_begin = true;
                
                if (stm_begin && line.contains("END"))
                    stm_begin = false;
                
                // Se concatena la línea a la sentencia en construcción.
                statement += " " + line;
                
                /* Si la línea contiene ";" y éste carácter no está dentro de un BEGIN, 
                 * se ejecuta la sentencia */
                if (line.contains(";") && !stm_begin) {
                    System.out.println(statement);
                    con.createStatement().execute(statement);
                    // Se resetea la sentencia para la siguiente.
                    statement = "";
                }
                
            }
        }
        
        desconectar();
    }

    /**
     * Vuelca desde un fichero interno de la clase, a uno temporal para ser tratado posteriormente.
     * 
     * @param resource_name  PATH del recurso dentro del ejecutable.
     * @param temp_name      Nombre del fichero temporal.
     * @return El fichero temporal con el contenido del recurso.
     * @throws IOException Si hay algún problema al crear el fichero temporal.
     */
    
    private File volcarRecursoTemporal(String resource_name, String temp_name) 
    throws IOException {
        int data;
        File temp = File.createTempFile(temp_name, "tmp");
        InputStream is = getClass().getClassLoader().getResourceAsStream(resource_name);
        OutputStream os = new FileOutputStream(temp);
        
        while ( (data = is.read()) != -1)
            os.write(data);
        
        os.close();
        is.close();
        return temp;
    }
    
}
