/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lanword.interfaces.bd.sqlite;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;
import lanword.interfaces.bd.IBDGestionIdiomas;
import lanword.modelo.Event;
import lanword.modelo.Idioma;


public class GestionIdiomas implements IBDGestionIdiomas {

    public static class Statements  {
        public static final String NEW = "INSERT INTO idiomas VALUES (?)";
        public static final String REMOVE = "DELETE FROM idiomas WHERE nombre = ?";
        public static final String UPDATE_NAME = "UPDATE idiomas SET nombre = ? WHERE nombre = ?";
    }
    
    @Override
    public void anyadir(Idioma idioma) throws SQLException {
        Connection con;
        
        try {
            con = BDSQlite.getInstance().conectar();
            con.createStatement().execute("INSERT INTO idiomas VALUES ('" + idioma.getNombre() + "')");
            idioma.stored(true);
            Logger.getLogger(GestionIdiomas.class.getName()).fine("Idioma escrito en la base de datos.");
        }
        catch (SQLException ex) {
            throw ex;
        }
        catch (Exception ex) {}
    }

    @Override
    public void borrar(Idioma idioma) throws SQLException {
        Connection con;
        
        try {
            con = BDSQlite.getInstance().conectar();
            con.createStatement().execute("DELETE FROM idiomas WHERE nombre = '" + idioma.getNombre() + "'");
            idioma.stored(false);
            idioma.destroy();
            System.out.println("Idioma borrado de la base de datos.");
        }
        catch (SQLException ex) {
            throw ex;
        }
        catch (Exception ex) {}
    }

    @Override
    public ArrayList<Idioma> buscar() throws SQLException {
        ArrayList<Idioma> idiomas = new ArrayList<>();
        Connection con;
        ResultSet rs;
        PreparedStatement pstat ;
        Idioma idioma;

        try {
            con = BDSQlite.getInstance().conectar();
            pstat = con.prepareStatement("SELECT * FROM idiomas");
            rs = pstat.executeQuery();
            
            while (rs.next()) {
                idioma = (Idioma) Idioma.buscar(new Object[] {rs.getString("nombre")});
                
                if (idioma == null) {
                    idioma = new Idioma(rs.getString("nombre"));
                    idioma.stored(true);
                    idiomas.add(idioma);
                }
                else
                    idiomas.add(idioma);
                
            }
            
        } catch (ClassNotFoundException ex) { } 
        catch (IOException ex) { }
        
        return idiomas;
    }

    @Override
    public void actualizarNombre(String nombre, String nombre_anterior) throws SQLException {
        Connection con;
        PreparedStatement pstat ;

        try {
            con = BDSQlite.getInstance().conectar();
            pstat = con.prepareStatement(Statements.UPDATE_NAME);
            pstat.setString(1, (String) nombre);
            pstat.setString(2, (String) nombre_anterior);
            pstat.execute();
            
        } catch (ClassNotFoundException ex) { } 
        catch (IOException ex) { }
    }
    
}
