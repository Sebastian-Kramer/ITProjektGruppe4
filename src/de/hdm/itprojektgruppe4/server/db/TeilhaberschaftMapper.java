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
					
					"SELECT `ID`, `kontaktlisteID`, `kontaktID`, `eigenschaftsauspraegungID`,"
					+ " `teilhaberID` FROM"	+ " `teilhaberschaft` WHERE ID=" + id);

			if (rs.next()) {
				
				Teilhaberschaft t = new Teilhaberschaft();
				
				t.setID(rs.getInt("ID"));
				t.setKontaktListeID(rs.getInt("kontaktlisteID"));
				t.setKontaktID(rs.getInt("kontaktID"));
				t.setEigenschaftsauspraegungID(rs.getInt("eigenschaftsauspraegungID"));
				t.setTeilhaberID(rs.getInt("teilhaberID"));

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

		      ResultSet rs = stmt.executeQuery(
		    		  
		    "SELECT MAX(id) AS maxid " + "FROM teilhaberschaft ");

		      if (rs.next()) {
		
		        t.setID(rs.getInt("maxid") + 1);

		        stmt = con.createStatement();

		        stmt.executeUpdate(
		        		
		        		"INSERT INTO `teilhaberschaft`(`ID`, `kontaktlisteID`, `kontaktID`,"
		        		+ " `eigenschaftsauspraegungID`, `teilhaberID`) "
		        		+ "VALUES ('"+t.getID()+"', '"+t.getKontaktListeID()+"', '"
		        		+ ""+t.getKontaktID()+"', '"+t.getEigenschaftsauspraegungID()+"', '"+ ""+t.getTeilhaberID()+"')");
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

		      stmt.executeUpdate(
		    		  
		      "DELETE FROM teilhaberschaft " + "WHERE id=" + t.getID());

		    }
		    catch (SQLException e2) {
		    	
		      e2.printStackTrace();
		      
		    }
		    
		  }
	  
	  public void deleteKontaktFromTeilhaberschaft(Teilhaberschaft t) {
		  
		    Connection con = DBConnection.connection();

		    try {
		    	
		      Statement stmt = con.createStatement();

		      stmt.executeUpdate(
		    		  
		    	"DELETE FROM `teilhaberschaft` WHERE `kontaktID`=" + t.getKontaktID() 
		    	+" " + "AND `teilhaberID` =" + t.getTeilhaberID());

		    }
		    catch (SQLException e2) {
		    	
		      e2.printStackTrace();
		      
		    }
		    
		  }
	  
	  public void deleteKontaktkisteFromTeilhaberschaft(Teilhaberschaft t) {
		  
		    Connection con = DBConnection.connection();

		    try {
		    	
		      Statement stmt = con.createStatement();

		      stmt.executeUpdate
		      
		      (
		    		  
		        "DELETE FROM `teilhaberschaft` WHERE `kontaktlisteID`=" + t.getKontaktListeID() 
		    	+" " + "AND `teilhaberID` =" + t.getTeilhaberID());

		    }
		    catch (SQLException e2) {
		    	
		      e2.printStackTrace();
		      
		    }
		    
		  }

	  public void deleteEigenschaftsauspraegungFromTeilhaberschaft(Teilhaberschaft t) {
		  
		    Connection con = DBConnection.connection();

		    try {
		    	
		      Statement stmt = con.createStatement();

		      stmt.executeUpdate
		      
		      (
		    		  
		       "DELETE FROM `teilhaberschaft` WHERE `eigenschaftsauspraegungID`=" + t.getEigenschaftsauspraegungID() 
		    	+" " + "AND `teilhaberID` =" + t.getTeilhaberID());

		    }
		    catch (SQLException e2) {
		    	
		      e2.printStackTrace();
		      
		    }
		    
		  }

}

