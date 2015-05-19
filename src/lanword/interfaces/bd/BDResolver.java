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
