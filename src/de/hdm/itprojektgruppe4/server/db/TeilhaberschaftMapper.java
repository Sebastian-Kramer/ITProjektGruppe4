package de.hdm.itprojektgruppe4.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import de.hdm.itprojektgruppe4.shared.bo.Teilhaberschaft;


public class TeilhaberschaftMapper {
	
	private static TeilhaberschaftMapper teilhaberschaftMapper = null;
	
	protected TeilhaberschaftMapper(){
		
	};
	
	public static TeilhaberschaftMapper teilhaberschaftMapper(){
		
		if(teilhaberschaftMapper == null){
			
			teilhaberschaftMapper = new TeilhaberschaftMapper();
		}
		
		return teilhaberschaftMapper;
	}
	
	public Teilhaberschaft findByID(int id) {
		
		Connection con = DBConnection.connection();

		try {
			
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					
					"SELECT `ID`, `Kontaktliste_ID`, `Kontakt_ID`, `Eigenschaft_ID`,"
					+ " `Eigenschaftsauspraegung_ID`, `Teilhaber_ID` FROM `teilhaberschaft` WHERE ID=" + id);

			if (rs.next()) {
				
				Teilhaberschaft t = new Teilhaberschaft();
				
				t.setID(rs.getInt("id"));
				t.setKontaktListeID(rs.getInt("id"));
				t.setKontaktID(rs.getInt("id"));
				t.setEigenschaftsID(rs.getInt("id"));
				t.setEigenschaftsAuspraegungID(rs.getInt("id"));
				t.setTeilhaberID(rs.getInt("id"));

				return t;
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			
			return null;
		}
		
		return null;
	}
	
	
	  public Teilhaberschaft insert(Teilhaberschaft t) {
		    Connection con = DBConnection.connection();

		    try {
		      Statement stmt = con.createStatement();

		      ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
		          + "FROM teilhaberschaft ");

		      if (rs.next()) {
		
		        t.setID(rs.getInt("maxid") + 1);

		        stmt = con.createStatement();

		        stmt.executeUpdate("INSERT INTO teilhaberschaft (`ID`, `Kontaktliste_ID`, `Kontakt_ID`, "
		        		+ "`Eigenschaft_ID`, `Eigenschaftsauspraegung_ID`, `Teilhaber_ID`) " + 
		        		"VALUES ("+ t.getID() + "," + t.getKontaktListeID() + 
		        		"," + t.getKontaktID() + "," + t.getEigenschaftsID() + 
		        		"," + t.getEigenschaftsAuspraegungID() + "," + t.getTeilhaberID() +")");
		      }
		    }
		    catch (SQLException e2) {
		      e2.printStackTrace();
		    }

		    return t;
		  }
	  
	
	  
	  public void delete(Teilhaberschaft t) {
		  
		    Connection con = DBConnection.connection();

		    try {
		    	
		      Statement stmt = con.createStatement();

		      stmt.executeUpdate("DELETE FROM teilhaberschaft " + "WHERE id=" + t.getID());

		    }
		    catch (SQLException e2) {
		    	
		      e2.printStackTrace();
		      
		    }
		    
		  }
	  
	  public void deleteKontaktFromTeilhaberschaft(Teilhaberschaft t) {
		  
		    Connection con = DBConnection.connection();

		    try {
		    	
		      Statement stmt = con.createStatement();

		      stmt.executeUpdate("DELETE FROM teilhaberschaft " + 
		      "WHERE Kontakt_ID=" + t.getKontaktID());

		    }
		    catch (SQLException e2) {
		    	
		      e2.printStackTrace();
		      
		    }
		    
		  }
	  
	  public void deleteKontaktkisteFromTeilhaberschaft(Teilhaberschaft t) {
		  
		    Connection con = DBConnection.connection();

		    try {
		    	
		      Statement stmt = con.createStatement();

		      stmt.executeUpdate("DELETE FROM teilhaberschaft " + 
		      "WHERE Kontaktliste_ID=" + t.getKontaktListeID());

		    }
		    catch (SQLException e2) {
		    	
		      e2.printStackTrace();
		      
		    }
		    
		  }
	  
	  public void deleteEigenschaftFromTeilhaberschaft(Teilhaberschaft t) {
		  
		    Connection con = DBConnection.connection();

		    try {
		    	
		      Statement stmt = con.createStatement();

		      stmt.executeUpdate("DELETE FROM teilhaberschaft " + 
		      "WHERE Eigenschaft_ID=" + t.getEigenschaftsID());

		    }
		    catch (SQLException e2) {
		    	
		      e2.printStackTrace();
		      
		    }
		    
		  }
	  
	  public void deleteEigenschaftsAuspraegungFromTeilhaberschaft(Teilhaberschaft t) {
		  
		    Connection con = DBConnection.connection();

		    try {
		    	
		      Statement stmt = con.createStatement();

		      stmt.executeUpdate("DELETE FROM teilhaberschaft " + 
		      "WHERE Eigenschaftsauspraegung_ID=" + t.getEigenschaftsAuspraegungID());

		    }
		    catch (SQLException e2) {
		    	
		      e2.printStackTrace();
		      
		    }
		    
		  }

}

