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
	
	//Kontakt anhand der Id finden
	
	public Kontakt findKontaktByID(int id){
		
		Connection con = DBConnection.connection();

		try {
			
		
		Statement stmt = con.createStatement();
		
		ResultSet rs = stmt.executeQuery(
				"SELECT id, nutzer_id, eigenschaft_id, name, erzeugungsdatum, modifikationsdatum, status FROM kontakt " + " WHERE id= " + id );
				 
		
		if (rs.next()) {
			Kontakt k = new Kontakt();
			k.setId(rs.getInt("id"));
			k.setNutzer_id(rs.getInt("nutzer_id"));
			k.setEigenschaft_id(rs.getInt("eigenschaft_id"));
			k.setName(rs.getString("name"));
			k.setErzeugungsdatum(rs.getDate("erzeugungsdatum"));
			k.setModifikationsdatum(rs.getDate("modifikationsdatum"));
			k.setStatus(rs.getInt("status"));
			
			return k;
		}
		
	}catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
	return null;
	
	
	
	}
	
	// einen neuen Kontakt in die Db einfügen
	
	public Kontakt insertKontakt(Kontakt k) {
		
		Connection con = DBConnection.connection(); 
		
		try{
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT MAX(ID) AS maxID " + " FROM kontakt ");
			
			if (rs.next()) {
				
				k.setId(rs.getInt("maxID") +1);
				
				stmt = con.createStatement();
				
				stmt.executeUpdate(
						" INSERT INTO kontakt (id, nutzer_id, eigenschaft_id, name, erzeugungsdatum, modifikationsdatum, status)"
						+ " VALUES (" + k.getId() + " ,'" + k.getNutzer_id() +  "' ,'" + k.getEigenschaft_id() + "' ,'" + k.getName() + "','"
						+  format.format(k.getErzeugungsdatum()) + "', '" + format.format(k.getModifikationsdatum()) +  "' ,'"  + k.getStatus() + "')");
						
			}
		}catch (SQLException e) {
			e.printStackTrace();

		}
		
		return k;
	}
	
	// einen Kontakt anhand des Objekt-namen finden
	
	public Kontakt findByProjekt(Kontakt k){
		return this.findKontaktByID(k.getId());
	
	
	}
	
	public void deleteKontakt(Kontakt k){
		Connection con = DBConnection.connection();
		
		try{
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM kontakt" + "WHERE id= " + k.getId());
			
		} catch (SQLException e){
			e.printStackTrace();
		}
		
	}

}
