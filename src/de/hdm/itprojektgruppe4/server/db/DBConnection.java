package de.hdm.itprojektgruppe4.server.db;

import java.sql.Connection;
import java.sql.DriverManager;

import com.google.appengine.api.utils.SystemProperty;


public class DBConnection {

    
    private static Connection con = null;

   
    private static String googleUrl = "jdbc:google:mysql://bankproject-154007:bankproject/bankproject?user=demo&password=demo";
    private static String localUrl = "jdbc:mysql://127.0.0.1:3306/itprojektgruppe4SS18?user=root&password=";

   
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

        
        return con;
    }

}

