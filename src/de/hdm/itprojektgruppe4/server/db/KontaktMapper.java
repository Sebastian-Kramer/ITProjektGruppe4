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
				"SELECT ID, Nutzer_ID, Name, Erzeugungsdatum, Modifikationsdatum, Status FROM Kontakt " + " WHERE ID= " + id );
				 
		
		if (rs.next()) {
			Kontakt k = new Kontakt();
			k.setID(rs.getInt("id"));
			k.setNutzer_id(rs.getInt("Nutzer_ID"));
			k.setName(rs.getString("Name"));
			k.setErzeugungsdatum(rs.getDate("Erzeugungsdatum"));
			k.setModifikationsdatum(rs.getDate("Modifikationsdatum"));
			k.setStatus(rs.getInt("Status"));
			
			return k;
		}
		
	}catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
	return null;
	
	
	
	}
	
	// einen neuen Kontakt in die Db einf�gen
	
	public Kontakt insertKontakt(Kontakt k) {
		
		Connection con = DBConnection.connection(); 
		
		try{
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT MAX(ID) AS maxID " + " FROM kontakt ");
			
			if (rs.next()) {
				
				k.setID(rs.getInt("maxID") +1);
				
				stmt = con.createStatement();
				
				stmt.executeUpdate(
						" INSERT INTO Kontakt (id, nutzer_id, eigenschaft_id, name, erzeugungsdatum, modifikationsdatum, status)"
						+ " VALUES (" + k.getID() + " ,'" + k.getNutzer_id() +  "' ,'" + "' ,'" + k.getName() + "','"
						+  format.format(k.getErzeugungsdatum()) + "', '" + format.format(k.getModifikationsdatum()) +  "' ,'"  + k.getStatus() + "')");
						
			}
		}catch (SQLException e) {
			e.printStackTrace();

		}
		
		return k;
	}
	
	/** 
	 * einen Kontakt anhand des Objekt-namen finden
	 * @param k
	 * @return
	 */
	
	public Kontakt findByProjekt(Kontakt k){
		return this.findKontaktByID(k.getID());
		}
	
	/**
	 * 
	 * @param k
	 */
	public void deleteKontakt(Kontakt k){
		Connection con = DBConnection.connection();
		
		try{
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Kontakt" + " WHERE id= " + k.getID());
			
		} catch (SQLException e){
			e.printStackTrace();
		}
		
	}
	

}
