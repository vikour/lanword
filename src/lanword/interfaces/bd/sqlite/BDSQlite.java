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

package lanword.interfaces.bd.sqlite;

import lanword.interfaces.bd.*;
import java.io.IOException;
import java.sql.*;

/**
 *
 * @author vikour
 */
public class BDSQlite extends BDManagment {
    
    private static BDSQlite instance;

    
    public static BDSQlite getInstance() 
    throws SQLException, ClassNotFoundException, IOException { 
        
        if (instance == null) {
            Configuration.DRIVER_URL = "org.sqlite.JDBC";
            Configuration.FILE_DDL = "lanword/resources/sqlite_ddl.sql";
            Configuration.FILE_BD = "palabras.db3";
            Configuration.URL_DB = "jdbc:sqlite:" + Configuration.FILE_BD;
            instance = new BDSQlite();
        }
        
        return instance;
    }
    
    private BDSQlite() 
    throws SQLException, ClassNotFoundException, IOException {
        super();
        idiomas = new GestionIdiomas();
        palabras = new GestionPalabras();
        grupos = new GestionGrupos();
    }

    @Override
    public Connection conectar() throws SQLException, ClassNotFoundException {
        Connection con =  super.conectar(); 
        
        // Con esta sentencia se activa el soporte para las claves foráneas.
        con.createStatement().execute("PRAGMA foreign_keys = ON");
        
        return con;
    }
    
    
}
