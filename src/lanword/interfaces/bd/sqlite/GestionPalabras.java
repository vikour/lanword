package lanword.interfaces.bd.sqlite;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import lanword.interfaces.bd.IBDGestionPalabras;
import lanword.modelo.ClasePersistente;
import lanword.modelo.Grupo;
import lanword.modelo.Idioma;
import lanword.modelo.Palabra;


public class GestionPalabras implements IBDGestionPalabras {
    public static final Logger LOG = Logger.getLogger(IBDGestionPalabras.class.getName());
    
    private static class Statements {
        public static final String SELECT_ONLY_WORD = "SELECT * FROM palabras WHERE nombre = ? AND idioma = ?";
        public static final String SELECT_ALL_WORDS = "SELECT * FROM palabras ORDER BY idioma";
        public static final String SELECT_TRADUCTION_WORD = "SELECT traduccion, idioma_traduccion FROM traducciones WHERE palabra = ? AND idioma_palabra = ?";
        public static final String SELECT_GROUP_WORD = "SELECT grupo FROM agrupaciones WHERE palabra = ? AND idioma = ?";
        public static final String INSERT_WORD = "INSERT INTO palabras VALUES (?, ?)";
        public static final String INSERT_TRADUCTION_WORD = "INSERT INTO traducciones VALUES (?, ?, ?, ?)";
        public static final String DELETE_WORD = "DELETE FROM palabras WHERE nombre = ? AND idioma = ?";
        public static final String SELECT_FILTER_NAME = "SELECT * FROM palabras WHERE nombre like ? ORDER BY idioma";
        public static final String SELECT_FILTER_NAME_GROUP = "SELECT p.nombre, p.idioma FROM palabras p, agrupaciones a WHERE p.nombre like ? and a.grupo = ? and p.nombre = a.palabra and p.idioma = a.idioma ORDER BY p.idioma";
        public static final String SELECT_FILTER_NAME_LANGUAGE = "SELECT * FROM palabras WHERE nombre like ? and idioma = ? ORDER BY idioma";
        public static final String SELECT_FILTER_GROUP = "SELECT p.nombre, p.idioma FROM palabras p, agrupaciones a WHERE a.grupo = ? and p.nombre = a.palabra and p.idioma = a.idioma ORDER BY p.idioma";
        public static final String SELECT_FILTER_LANGUAGE = "SELECT * FROM palabras WHERE idioma = ? ORDER BY idioma";
        public static final String SELECT_FILTER_LANGUAGE_GROUP = "SELECT p.nombre, p.idioma FROM palabras p, agrupaciones a WHERE p.nombre = a.palabra AND a.grupo = ? AND p.idioma = ? ORDER BY p.idioma";
        public static final String SELECT_FILTER_NAME_GROUP_LANGUAGE = "SELECT p.nombre, p.idioma FROM palabras p, agrupaciones a WHERE p.nombre = a.palabra AND p.idioma = ? AND p.nombre like ? AND a.grupo = ? ORDER BY idioma";
        public static final String SELECT_WORD_WITHOUT_GROUP_X = "SELECT nombre, idioma FROM palabras " +
                                                                 "EXCEPT " + 
                                                                 "SELECT palabra AS nombre, idioma FROM agrupaciones WHERE grupo = ?";
        public static final String SELECT_PROBABLY_TRADUCTION = "SELECT * FROM palabras WHERE idioma = ? " + 
                                                                "EXCEPT " +
                                                                "SELECT traduccion AS nombre, idioma_traduccion AS idioma FROM traducciones WHERE palabra = ?";
        public static final String SELECT_RANDOM_LANGUAGE = "SELECT palabra AS nombre, idioma_palabra AS idioma FROM traducciones WHERE idioma_palabra = ? AND idioma_traduccion = ? ORDER BY RANDOM()";
        public static final String SELECT_RANDOM_GROUP = "SELECT palabra AS nombre, idioma_palabra AS idioma FROM traducciones WHERE idioma_traduccion = ? " +
                                                         "INTERSECT " +
                                                         "SEELCT palabra AS nombre, idioma FROM agrupaciones WHERE grupo = ?";
        public static final String UPDATE_NAME = "UPDATE palabras SET nombre = ? WHERE nombre = ? AND idioma = ? ";
        public static final String UPDATE_LANGUAGE = "UPDATE palabras SET idioma = ? WHERE nombre = ? AND idioma = ? ";
        public static final String UPDATE_NEW_TRADUCTION = "INSERT INTO traducciones VALUES (?, ?, ?, ?)";
        public static final String UPDATE_REMOVE_TRADUCTION = "DELETE FROM traducciones WHERE palabra = ? AND idioma_palabra = ? AND traduccion = ? AND idioma_traduccion = ?";
        public static final String UPDATE_NEW_GROUP = "INSERT INTO agrupaciones VALUES (?, ?,  ?)";
        public static final String UPDATE_REMOVE_GROUP = "DELETE FROM agrupaciones WHERE palabra = ? AND idioma = ? AND grupo = ?";
    }

    @Override
    public void anyadir(Palabra palabra) throws SQLException {
        Connection con;
        PreparedStatement pstat;
        
        try {
            con = BDSQlite.getInstance().conectar();
            
            if (!palabra.isStored()) {
                
                // Si el idioma no está guardado, se añade también.
                if (!palabra.getIdioma().isStored())
                    BDSQlite.getInstance().idiomas.anyadir(palabra.getIdioma());
                
                pstat = con.prepareStatement(Statements.INSERT_WORD);
                pstat.setString(1, palabra.getNombre());
                pstat.setString(2, palabra.getIdioma().getNombre());
                pstat.execute();
                palabra.stored(true);
                
                // Si los grupos no están almacenados se guardan.
                for (Grupo grupo : palabra.getsGrupos())
                    
                    if (!grupo.isStored())
                        BDSQlite.getInstance().grupos.anyadir(grupo);
                
                /* Por último, se añaden las traducciones. Como la palabra no existía;
                 * las traducciones tampoco. */
                for (Palabra traduccion : palabra.getTraducciones()) {
                    
                    // Si la traducción no existe, se añade. (RECURSIVIDAD).
                    if (!traduccion.isStored())
                        anyadir(traduccion);
                    else {
                        /** ¡Ojo!, esto podría no estar en un else. Pero si esta, en la siguiente
                         * iteración se añadirá la traducción y al salir del ciclo, se volverá a intentar
                         * añadir; lo que provocaría un SQLException.
                         */
                        pstat = con.prepareStatement(Statements.INSERT_TRADUCTION_WORD);
                        pstat.setString(1, palabra.getNombre());
                        pstat.setString(2, palabra.getIdioma().getNombre());
                        pstat.setString(3, traduccion.getNombre());
                        pstat.setString(4, traduccion.getIdioma().getNombre());
                        pstat.execute();
                    }
                }
                    
            }
            
        } catch (ClassNotFoundException ex) {
            LOG.severe("No se encontró el driver para SQLite");
        } catch (IOException ex) {
            LOG.severe("No se pudo crear la base de datos.");
        }
        
    }

    @Override
    public void borrar(Palabra palabra) throws SQLException {
        Connection con;
        PreparedStatement pstat;
        
        try {
            con = BDSQlite.getInstance().conectar();
            
            if (palabra.isStored()) {
                pstat = con.prepareStatement(Statements.DELETE_WORD);
                pstat.setString(1, palabra.getNombre());
                pstat.setString(2, palabra.getIdioma().getNombre());
                pstat.execute();
                palabra.stored(false);
                palabra.destroy();
            }
            
        } catch (ClassNotFoundException ex) {
            LOG.severe("No se encontró el driver para SQLite");
        } catch (IOException ex) {
            LOG.severe("No se pudo crear la base de datos.");
        }
    }

    @Override
    public Palabra buscar(String nombre, Idioma idioma) throws SQLException {
        Palabra palabra = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;
        String read_idioma, in_nombre;
        Connection con = null;
        Grupo grupo;
        
        palabra = (Palabra) ClasePersistente.buscar(Palabra.class.getName(), nombre, idioma);
        
        if (palabra != null)
            return palabra;
        
        try {
            con = BDSQlite.getInstance().conectar();
            pstat = con.prepareStatement(Statements.SELECT_ONLY_WORD);
            pstat.setString(1, nombre);
            pstat.setString(2, idioma.getNombre());
            rs = pstat.executeQuery();
            rs.next();
            
            // Obtengo el idioma, para después buscalo en la memoria.
            read_idioma = rs.getString("idioma");
            in_nombre = rs.getString("nombre");
            // Si no esta, recargo los datos desde la base de datos.
            if (ClasePersistente.buscar(Idioma.class.getName(), read_idioma) == null)
                BDSQlite.getInstance().idiomas.buscar();

            palabra = new Palabra(in_nombre,
                                  (Idioma) ClasePersistente.buscar(Idioma.class.getName(), read_idioma));
            palabra.stored(true);
            
            // Busco traducciones y las creo.
            con = BDSQlite.getInstance().conectar();
            pstat = con.prepareStatement(Statements.SELECT_TRADUCTION_WORD);
            pstat.setString(1, nombre);
            pstat.setString(2, idioma.getNombre());
            rs = pstat.executeQuery();
            
            while (rs.next()) {
                Idioma in_idioma = new Idioma(rs.getString("idioma_traduccion"));
                
                Palabra traduccion = 
                        (Palabra) ClasePersistente.buscar(
                                Palabra.class.getName(), 
                                rs.getString("traduccion"), 
                                in_idioma
                        );
                
                if (traduccion == null)
                    traduccion = buscar(rs.getString("traduccion"), in_idioma);
                
                /* Como el método es recursivo, puede que se intente añadir dos veces.
                 * Eso causaría un IllegalArgumentException.
                */
                if (!palabra.seTraduceEn(traduccion))
                    palabra.anyadirTraduccion(traduccion);
            }
            
            /* Con respecto a las agrupaciones, delego esa operación a la clase encargada de ello.
             * Esta clase, agrupa ya a las palabras a nivel de clase. Así, que no hay que hacerlo.
             */
            pstat = con.prepareStatement(Statements.SELECT_GROUP_WORD);
            pstat.setString(1, nombre);
            pstat.setString(2, idioma.getNombre());
            rs = pstat.executeQuery();
            
            while (rs.next()) {
                in_nombre = rs.getString("grupo");
                grupo = (Grupo) ClasePersistente.buscar(Grupo.class.getName(), in_nombre);
                
                if (grupo == null)
                    //La palabra ya está en memoria, por lo que, si busco el grupo el método agrupa automáticamente.
                    BDSQlite.getInstance().grupos.buscar(in_nombre);
                // Si existe en memoria, quizás no esté agrupada con la palabra.
                else if (!palabra.agrupado(grupo))
                    palabra.agrupar(grupo);
                
            }
            
        } catch (ClassNotFoundException ex) {

        } catch (IOException ex) {

        }
        
        return palabra;
    }

    @Override
    public ArrayList<Palabra> buscar(String nombre, Grupo grupo, Idioma idioma) throws SQLException {
        ArrayList<Palabra> palabras = new ArrayList<>();
        Connection con;
        PreparedStatement pstat = null;
        ResultSet rs;
        
        try {
            con = BDSQlite.getInstance().conectar();
            
            // Negociación del select según los argumentos.
            if (nombre == null && grupo == null && idioma != null) {
                pstat = con.prepareStatement(Statements.SELECT_FILTER_LANGUAGE);
                pstat.setString(1, idioma.getNombre());
            }
            else if (grupo == null && idioma == null && nombre != null) {
                pstat = con.prepareStatement(Statements.SELECT_FILTER_NAME);
                pstat.setString(1, nombre + "%");
            }
            else if (nombre == null && idioma == null && grupo != null) {
                pstat = con.prepareStatement(Statements.SELECT_FILTER_GROUP);
                pstat.setString(1, grupo.getNombre());
            }
            else if (nombre == null && idioma != null && grupo != null) {
                pstat = con.prepareStatement(Statements.SELECT_FILTER_LANGUAGE_GROUP);
                pstat.setString(1, grupo.getNombre());
                pstat.setString(2, idioma.getNombre());
            }
            else if (grupo == null && idioma != null && nombre != null) {
                pstat = con.prepareStatement(Statements.SELECT_FILTER_NAME_LANGUAGE);
                pstat.setString(1, nombre + "%");
                pstat.setString(2, idioma.getNombre());
            }
            else if (idioma == null && grupo != null && nombre != null) {
                pstat = con.prepareStatement(Statements.SELECT_FILTER_NAME_GROUP);
                pstat.setString(1, nombre + "%");
                pstat.setString(2, grupo.getNombre());
            }
            // Todos son nulos, lo que quiere decir todas las palabras.
            else 
                pstat = con.prepareStatement(Statements.SELECT_ALL_WORDS);

            
            rs = pstat.executeQuery();
            
            if (idioma != null) 
                
                while (rs.next()) 
                    palabras.add(buscar(rs.getString("nombre"), idioma));
            
            else {
                ArrayList<Idioma> idiomas = BDSQlite.getInstance().idiomas.buscar();
                Idioma idioma_in = null;
                
                while (rs.next()) {
                    // Selecciono el idioma de la palabra.
                    for (Idioma i : idiomas)
                        
                        if (i.getNombre().matches(rs.getString("idioma")))
                            idioma_in = i;
                    
                    // Busco la palabra.
                    palabras.add(buscar(rs.getString("nombre"), idioma_in));
                }
            }
                
                
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestionPalabras.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestionPalabras.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return palabras;
    }

    @Override
    public ArrayList<Palabra> buscarSinGrupo(Grupo grupo) throws SQLException {
        Connection con;
        ResultSet rs;
        PreparedStatement pstat;
        ArrayList<Palabra> palabras = new ArrayList<>();
        ArrayList<Idioma> idiomas = null;
        Idioma idioma = null;
        
        try {
            idiomas = BDSQlite.getInstance().idiomas.buscar();
            con = BDSQlite.getInstance().conectar();
            pstat = con.prepareStatement(Statements.SELECT_WORD_WITHOUT_GROUP_X);
            pstat.setString(1, grupo.getNombre());
            rs = pstat.executeQuery();
            
            
            while (rs.next()) {
                
                for (Idioma i : idiomas)
                    
                    if (i.getNombre().matches(rs.getString("idioma")))
                        idioma = i;
                
                palabras.add(buscar(rs.getString("nombre"), idioma));
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestionPalabras.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestionPalabras.class.getName()).log(Level.SEVERE, null, ex);
        }

        return palabras;
    }

    @Override
    public ArrayList<Palabra> buscarPosiblesTraducciones(Palabra palabra, Idioma idioma) throws SQLException {
        Connection con;
        ResultSet rs;
        PreparedStatement pstat;
        ArrayList<Palabra> palabras = new ArrayList<>();
        
        try {
            con = BDSQlite.getInstance().conectar();
            pstat = con.prepareStatement(Statements.SELECT_PROBABLY_TRADUCTION);
            pstat.setString(1, idioma.getNombre());
            pstat.setString(2, palabra.getNombre());
            rs = pstat.executeQuery();
            
            while (rs.next()) 
                palabras.add(buscar(rs.getString("nombre"), idioma));
                
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestionPalabras.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestionPalabras.class.getName()).log(Level.SEVERE, null, ex);
        }

        return palabras;
    }

    @Override
    public ArrayList<Palabra> buscarAleatoriamente(Idioma idioma, Idioma objetivo) throws SQLException {
        
        Connection con;
        ResultSet rs;
        PreparedStatement pstat;
        ArrayList<Palabra> palabras = new ArrayList<>();
        
        try {
            con = BDSQlite.getInstance().conectar();
            pstat = con.prepareStatement(Statements.SELECT_RANDOM_LANGUAGE);
            pstat.setString(1, idioma.getNombre());
            pstat.setString(2, objetivo.getNombre());
            rs = pstat.executeQuery();
            
            while (rs.next())
                palabras.add(buscar(rs.getString("nombre"), idioma));
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestionPalabras.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestionPalabras.class.getName()).log(Level.SEVERE, null, ex);
        }

        return palabras;
    }

    @Override
    public ArrayList<Palabra> buscarAleatoriamente(Grupo grupo, Idioma objetivo) throws SQLException {
        
        Connection con;
        ResultSet rs;
        PreparedStatement pstat;
        ArrayList<Palabra> palabras = new ArrayList<>();
        ArrayList<Idioma> idiomas = null;
        Idioma idioma = null;
        
        try {
            idiomas = BDSQlite.getInstance().idiomas.buscar();
            con = BDSQlite.getInstance().conectar();
            pstat = con.prepareStatement(Statements.SELECT_RANDOM_GROUP);
            pstat.setString(1, objetivo.getNombre());
            pstat.setString(2, grupo.getNombre());
            rs = pstat.executeQuery();
            
            while (rs.next()) {
                
                for (Idioma i : idiomas)
                    
                    if (i.getNombre().matches(rs.getString("idioma")))
                        idioma = i;
                
                palabras.add(buscar(rs.getString("nombre"), idioma));
            }
                
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestionPalabras.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestionPalabras.class.getName()).log(Level.SEVERE, null, ex);
        }

        return palabras;
    }

    @Override
    public void actualizarNombre(Palabra palabra, String nombre_anterior) throws SQLException {
        Connection con;
        PreparedStatement pstat;
        
        try {
            con = BDSQlite.getInstance().conectar();
            pstat = con.prepareStatement(Statements.UPDATE_NAME);
            pstat.setString(1, palabra.getNombre());
            pstat.setString(2, nombre_anterior);
            pstat.setString(3, palabra.getIdioma().getNombre());
            pstat.execute();
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestionPalabras.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestionPalabras.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void actualizarIdioma(Palabra palabra, Idioma idioma_anterior) throws SQLException {
        Connection con;
        PreparedStatement pstat;
        
        try {
            
            if (palabra.isStored()) {
                con = BDSQlite.getInstance().conectar();
                pstat = con.prepareStatement(Statements.UPDATE_LANGUAGE);
                pstat.setString(1, palabra.getIdioma().getNombre());
                pstat.setString(2, palabra.getNombre());
                pstat.setString(3, idioma_anterior.getNombre());
                pstat.execute();
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestionPalabras.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestionPalabras.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actualizarNuevaTraduccion(Palabra palabra, Palabra traduccion) throws SQLException {
        Connection con;
        PreparedStatement pstat;
        
        try {
            
            if (palabra.isStored()) {
                con = BDSQlite.getInstance().conectar();
                
                if (!traduccion.isStored())
                    anyadir(traduccion);
                
                pstat = con.prepareStatement(Statements.UPDATE_NEW_TRADUCTION);
                pstat.setString(1, palabra.getNombre());
                pstat.setString(2, palabra.getIdioma().getNombre());
                pstat.setString(3, traduccion.getNombre());
                pstat.setString(4, traduccion.getIdioma().getNombre());
                pstat.execute();
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestionPalabras.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestionPalabras.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void actualizarTraduccionEliminada(Palabra palabra, Palabra traduccion) throws SQLException {
        Connection con;
        PreparedStatement pstat;
        
        try {
            
            if (palabra.isStored()) {
                con = BDSQlite.getInstance().conectar();
                pstat = con.prepareStatement(Statements.UPDATE_REMOVE_TRADUCTION);
                pstat.setString(1, palabra.getNombre());
                pstat.setString(2, palabra.getIdioma().getNombre());
                pstat.setString(3, traduccion.getNombre());
                pstat.setString(4, traduccion.getIdioma().getNombre());
                pstat.execute();
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestionPalabras.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestionPalabras.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void actualizarNuevoGrupo(Palabra palabra, Grupo grupo) throws SQLException {
        Connection con;
        PreparedStatement pstat;
        
        try {
            
            if (palabra.isStored()) {
                
                if (!grupo.isStored())
                    BDSQlite.getInstance().grupos.anyadir(grupo);
                
                con = BDSQlite.getInstance().conectar();
                pstat = con.prepareStatement(Statements.UPDATE_NEW_GROUP);
                pstat.setString(1, palabra.getNombre());
                pstat.setString(2, palabra.getIdioma().getNombre());
                pstat.setString(3, grupo.getNombre());
                pstat.execute();
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestionPalabras.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestionPalabras.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void actualizarGrupoEliminado(Palabra palabra, Grupo grupo) throws SQLException {
        Connection con;
        PreparedStatement pstat;
        
        try {
            
            if (palabra.isStored()) {
                con = BDSQlite.getInstance().conectar();
                pstat = con.prepareStatement(Statements.UPDATE_REMOVE_GROUP);
                pstat.setString(1, palabra.getNombre());
                pstat.setString(2, palabra.getIdioma().getNombre());
                pstat.setString(3, grupo.getNombre());
                pstat.execute();
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestionPalabras.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestionPalabras.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
