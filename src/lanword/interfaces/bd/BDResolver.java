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

import java.io.IOException;
import java.sql.SQLException;
import lanword.interfaces.bd.sqlite.BDSQlite;

/**
 * Esta clase selecciona la interfaz que se va a usar y la hace accesible para 
 * el sistema.
 *
 * @author Víctor Manuel Ortiz Guardeño.
 * @version 1.0
 * @date 31/04/2014
 */
public class BDResolver {
    private static BDManagment instance;
    
    public static BDManagment getInstance() 
    throws SQLException, ClassNotFoundException, IOException {
        
        if (instance == null)
            instance = BDSQlite.getInstance();
        
        return instance;
    }
    
}
