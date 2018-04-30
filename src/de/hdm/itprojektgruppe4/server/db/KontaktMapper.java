package de.hdm.itprojektgruppe4.server.db;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Vector;
import de.hdm.itprojektgruppe4.shared.bo.*;

public class KontaktMapper {

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	private static KontaktMapper kontaktMapper = null;
	
	
	protected KontaktMapper() {
	};
	
	
	public static KontaktMapper kontaktMapper() {
		if (kontaktMapper == null) {
			kontaktMapper = new KontaktMapper();
		}
		return kontaktMapper;
	}	
	
	
	public Kontakt findKontaktByID(int ID){
		
		Connection con = DBConnection.connection();

		try {
			
		
		Statement stmt = con.createStatement();
		
		ResultSet rs = stmt.executeQuery(
				"SELECT ID, nutzerID, kontaktID, eigenschaftID, name, erzeugungsdatum, modifikationsdatum, status FROM kontakt " + " WHERE ID= " + ID );
				 
		
		if (rs.next()) {
			Kontakt k = new Kontakt();
			k.setID(rs.getInt("ID"));
			k.setNutzerID(rs.getInt("nutzerID"));
			k.setKontaktID(rs.getInt("kontaktID"));
			k.setEigenschaftID(rs.getInt("eigenschaftID"));
			k.setName(rs.getString("name"));
			k.setErzeugungsdatum(rs.getDate("erzeugungsdatum"));
			k.setModifikationsdatum(rs.getDate("modifikationsdatum"));
			k.setStatus(rs.getBoolean("status"));
			
			return k;
		}
		
	}catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
	return null;
	
	
	
	}
	
	
	public Kontakt insertKontakt(Kontakt k) {
		
		Connection con = DBConnection.connection(); 
		
		try{
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT MAX(ID) AS maxID " + " FROM kontakt ");
			
			if (rs.next()) {
				
				k.setID(rs.getInt("maxID") +1);
				
				stmt = con.createStatement();
				
				stmt.executeUpdate(
						" INSERT INTO kontakt (ID, nutzerID, kontaktID, eigenschaftID, name, erzeugungsdatum, modifikationsdatum, status)"
						+ " VALUES (" + k.getID() + " ,'" + k.getNutzerID() + "' ,'" + k.getKontaktID() + "' ,'" + k.getEigenschaftID() + "' ,'" + k.getName() + "','"
						+  format.format(k.getErzeugungsdatum()) + "', '" + format.format(k.getModifikationsdatum()) +  "' ,'"  + k.isStatus() + "')");
						
			}
		}catch (SQLException e) {
			e.printStackTrace();

		}
		
		return k;
	}
	
	
	public Kontakt findByProjekt(Kontakt k){
		return this.findKontaktByID(k.getID());
	}

}
