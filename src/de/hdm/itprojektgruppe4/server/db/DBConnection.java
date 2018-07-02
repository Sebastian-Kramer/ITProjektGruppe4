package de.hdm.itprojektgruppe4.server.db;

import java.sql.Connection;
import java.sql.DriverManager;

import com.google.appengine.api.utils.SystemProperty;

/**
 * Klasse zum Verwalten der Datenbankverbindung
 *
 */
public class DBConnection {

    /**
     * DBConnection ist ein Singelton.
     * Variable ist "static" und speichert die einzige Instanz dieser Klasse.
     */
    private static Connection con = null;

    /**
     * URL zum Ansprechen der Datenbank.
     */
    
    private static String googleUrl = "jdbc:google:mysql://itprojektgruppe4ss18:europe-west:itprojektgruppe4instanz/itprojektgruppe4db?user=root&password=1234";
    private static String localUrl = "jdbc:mysql://localhost:3306/itprojektgruppe4?user=root&password=";
    
    
/**
 * Diese Methode stellt Singelton Eigenschaft sicher. 
 * @return DBConnectoin Objekt
 */
   
    public static Connection connection() {
        
        if (con == null) {
            String url = null;
            try {
                if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
                    
                    Class.forName("com.mysql.jdbc.GoogleDriver");
                    url = googleUrl;
                } else {
                   
                    Class.forName("com.mysql.jdbc.Driver");
                    url = localUrl;
                }
                
                con = DriverManager.getConnection(url);
            } catch (Exception e) {
                con = null;
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
        
        
/**
 * Zurückgeben der Verbindung
 */
        
        return con;
    }

}

