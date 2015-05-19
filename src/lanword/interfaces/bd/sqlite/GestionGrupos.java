 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lanword.interfaces.bd.sqlite;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import lanword.interfaces.bd.IBDGestionGrupos;
import lanword.modelo.ClasePersistente;
import lanword.modelo.Grupo;
import lanword.modelo.Idioma;
import lanword.modelo.Palabra;

/**
 *
 * @author vikour
 */
public class GestionGrupos implements IBDGestionGrupos{
    
    public static Logger LOG = Logger.getLogger(GestionGrupos.class.getName());

    
    private static class Statements {
        public static final String SELECT_GROUP = "SELECT * FROM grupos";
        public static final String SELECT_GROUP_BY_NAME = "SELECT * FROM grupos WHERE nombre = ?";
        public static final String SELECT_WORD_OF_GROUP = "SELECT palabra, idioma FROM agrupaciones WHERE grupo = ?";
        public static final String INSERT_GROUP = "INSERT INTO grupos VALUES (?, ?)";
        public static final String INSERT_GROUP_WORD = "INSERT INTO agrupaciones VALUES (?, ?, ?)";
        public static final String DELETE_GROUP_WORD = "DELETE FROM agrupaciones WHERE palabra = ? AND idioma = ? AND grupo = ?";
        public static final String DELETE_GROUP = "DELETE FROM grupos WHERE nombre = ?";
        public static final String UPDATE_NAME = "UPDATE grupos SET nombre = ? WHERE nombre = ?";
        public static final String UPDATE_DESCRIPTION = "UPDATE grupos SET descripcion = ? WHERE nombre = ?";
    }

    @Override
    public void anyadir(Grupo grupo) throws SQLException {
        Connection con;
        PreparedStatement pstat;
        
        try {
            
            // Si el gurpo no esta guardado ya.
            if (!grupo.isStored()){
                con = BDSQlite.getInstance().conectar();
                pstat = con.prepareStatement(Statements.INSERT_GROUP);
                pstat.setString(1, grupo.getNombre());
                pstat.setString(2, grupo.getDescripcion());
                pstat.execute();
                grupo.stored(true);
                
                // Para cada palabra agrupada en el grupo, añadirla si no esta escrita.
                for (Palabra palabra : grupo.getsPalabras())
                    
                    if (!palabra.isStored()) {
                        BDSQlite.getInstance().palabras.anyadir(palabra);
                        pstat = con.prepareStatement(Statements.INSERT_GROUP_WORD);
                        pstat.setString(1, palabra.getNombre());
                        pstat.setString(2, palabra.getIdioma().getNombre());
                        pstat.setString(3, grupo.getNombre());
                        pstat.execute();
                    }
                
            }
            
        } catch (ClassNotFoundException ex) {
            LOG.severe("No se ha encontrado el driver SQLite");
        } catch (IOException ex) {
            LOG.severe("No se pudo crear la base de datos.");
        }
        
    }

    @Override
    public void borrar(Grupo grupo) throws SQLException {
        Connection con;
        PreparedStatement pstat;
        
        try {
            
            // Si el grupo esta guardado, se borra.
            if (grupo.isStored()){
                con = BDSQlite.getInstance().conectar();
                pstat = con.prepareStatement(Statements.DELETE_GROUP);
                pstat.setString(1, grupo.getNombre());
                pstat.execute();
                grupo.stored(false);
                grupo.destroy();
            }
            
        } catch (ClassNotFoundException ex) {
            LOG.severe("No se ha encontrado el driver SQLite");
        } catch (IOException ex) {
            LOG.severe("No se pudo crear la base de datos.");
        }
        
    }

    @Override
    public Grupo buscar(String nombre) throws SQLException {
        Connection con;
        PreparedStatement pstat;
        ResultSet rs;
        Grupo grupo = null;
        ArrayList<Grupo> grupos = new ArrayList<>();
        
        try {
            grupo = (Grupo) Grupo.buscar(new Object[] {nombre});            
            
            if (grupo == null) {
                con = BDSQlite.getInstance().conectar();
                pstat = con.prepareStatement(Statements.SELECT_GROUP_BY_NAME);
                pstat.setString(1, nombre);
                rs = pstat.executeQuery();
                
                rs.next();
                grupo = new Grupo(rs.getString("nombre"), 
                                  rs.getString("descripcion"));
                
                grupo.stored(true);
                buscarPalabras(con, grupo);
            }

           
        } catch (ClassNotFoundException ex) {
            LOG.severe("No se ha encontrado el driver de SQLite");
        } catch (IOException ex) {
            LOG.severe("No se pudo crear la base de datos.");
        }
        
        LOG.log(Level.FINE, "Se han leido {0} grupos.", grupos.size());
        
        return grupo;
    }
    
    @Override
    public ArrayList<Grupo> buscar() throws SQLException {
        Connection con;
        ResultSet rs;
        Grupo grupo;
        ArrayList<Grupo> grupos = new ArrayList<>();
        
        try {
            con = BDSQlite.getInstance().conectar();
            rs = con.createStatement().executeQuery(Statements.SELECT_GROUP);
            
            while (rs.next()) {
                // Primero busco en la memoria.
                grupo = (Grupo) Grupo.buscar(new Object[] {rs.getString("nombre")});

                // Si no existe, construyo el objeto.
                if (grupo == null) {
                    grupo = new Grupo(rs.getString("nombre"),
                                      rs.getString("descripcion"));
                    buscarPalabras(con, grupo);
                }

                grupo.stored(true);
                grupos.add(grupo);
            }
           
        } catch (ClassNotFoundException ex) {
            LOG.severe("No se ha encontrado el driver de SQLite");
        } catch (IOException ex) {
            LOG.severe("No se pudo crear la base de datos.");
        }
        
        LOG.log(Level.FINE, "Se han leido {0} grupos.", grupos.size());
        
        return grupos;
    }
    
    private void buscarPalabras(Connection con, Grupo grupo) throws SQLException, ClassNotFoundException, IOException {
       PreparedStatement pstat = con.prepareStatement(Statements.SELECT_WORD_OF_GROUP);
       ResultSet rs;
       String nombre, idioma;
       Palabra palabra;
       ArrayList<Idioma> idiomas = BDSQlite.getInstance().idiomas.buscar();
       Idioma idioma_o = null;

       pstat.setString(1, grupo.getNombre());
       rs = pstat.executeQuery();
       
       while (rs.next()) {
           nombre = rs.getString("palabra");
           idioma = rs.getString("idioma");
           
           for (Idioma i : idiomas )
               
               if (i.getNombre().matches(idioma))
                   idioma_o = i;
           
           palabra = (Palabra) Palabra.buscar(new Object[] {nombre, idioma_o});
           
           if (palabra == null) 
               palabra = BDSQlite.getInstance().palabras.buscar(nombre, idioma_o);

           if (!palabra.agrupado(grupo))
               grupo.agrupar(palabra);
           
           palabra.stored(true);
       }
       
    }

    @Override
    public void actualizarNombre(String nombre, String an_nombre) throws SQLException {
        Connection con;
        PreparedStatement pstat;
        
        try {
            con = BDSQlite.getInstance().conectar();
            pstat = con.prepareStatement(Statements.UPDATE_NAME);
            pstat.setString(1, nombre);
            pstat.setString(2, an_nombre);
            pstat.execute();
        } catch (ClassNotFoundException ex) {
            LOG.severe("No se ha encontrado el driver SQLite");
        } catch (IOException ex) {
            LOG.severe("No se pudo crear la base de datos.");
        }

    }

    @Override
    public void actualizarDescripcion(Grupo grupo) throws SQLException {
        Connection con;
        PreparedStatement pstat;
        
        try {
            
            if (grupo.isStored()) {
                con = BDSQlite.getInstance().conectar();
                pstat = con.prepareStatement(Statements.UPDATE_DESCRIPTION);
                pstat.setString(1, grupo.getDescripcion());
                pstat.setString(2, grupo.getNombre());
                pstat.execute();
            }
            
        } catch (ClassNotFoundException ex) {
            LOG.severe("No se ha encontrado el driver SQLite");
        } catch (IOException ex) {
            LOG.severe("No se pudo crear la base de datos.");
        }
    }

    @Override
    public void actualizarAgruparPalabra(Grupo grupo, Palabra palabra) throws SQLException{
        Connection con;
        PreparedStatement pstat;
        
        try {
            
            if (grupo.isStored()) {
                
                if (!palabra.isStored())
                    BDSQlite.getInstance().palabras.anyadir(palabra);
                
                con = BDSQlite.getInstance().conectar();
                pstat = con.prepareStatement(Statements.INSERT_GROUP_WORD);
                pstat.setString(1, palabra.getNombre());
                pstat.setString(2, palabra.getIdioma().getNombre());
                pstat.setString(3, grupo.getNombre());
                pstat.execute();
            }

        } catch (ClassNotFoundException ex) {
            LOG.severe("No se ha encontrado el driver SQLite");
        } catch (IOException ex) {
            LOG.severe("No se pudo crear la base de datos.");
        }
    }

    @Override
    public void actualizarDesagruparPalabra(Grupo grupo, Palabra palabra) throws SQLException{
        Connection con;
        PreparedStatement pstat;
        
        try {
            
            if (grupo.isStored() && palabra.isStored()) {
                con = BDSQlite.getInstance().conectar();
                pstat = con.prepareStatement(Statements.DELETE_GROUP_WORD);
                pstat.setString(1, palabra.getNombre());
                pstat.setString(2, palabra.getIdioma().getNombre());
                pstat.setString(3, grupo.getNombre());
                pstat.execute();
            }
            
        } catch (ClassNotFoundException ex) {
            LOG.severe("No se ha encontrado el driver SQLite");
        } catch (IOException ex) {
            LOG.severe("No se pudo crear la base de datos.");
        }
    }
    
}
