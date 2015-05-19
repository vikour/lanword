/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import lanword.interfaces.bd.BDManagment;
import lanword.interfaces.bd.BDResolver;
import lanword.modelo.Grupo;
import lanword.modelo.Idioma;
import lanword.modelo.Palabra;

/**
 *
 * @author vikour
 */
public class Sample {
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException { 
        BDManagment bd = BDResolver.getInstance();
        bd.idiomas.buscar();
        Palabra sanwich = bd.palabras.buscar("Sanwich", (Idioma) Idioma.buscar(new Object[] {"Español"}));
        
        
        for (Palabra palabra : bd.palabras.buscarPosiblesTraducciones(sanwich, (Idioma) Idioma.buscar(new Object[]{"Inglés"})))
            System.out.println(palabra);

    }
    
}
