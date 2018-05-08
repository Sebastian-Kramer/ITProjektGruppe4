package de.hdm.itprojektgruppe4.server.db;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Vector;
import de.hdm.itprojektgruppe4.shared.bo.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	/**
	 * 
	 * 
	 * 
	 * @param id 
	 * 	Primärschlüssel der DB
	 * @return Kontakt-Objekt. welches dem PK entspricht, 
	 * 			falls nicht vorhanden, dann null
	 */
	
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
	
	/**
	 *  Ausgabe aller Kontakte sortiert nach ID 
	 * @return Ein Vektor mit allen Kontakt-Objektenn
	 * 			Im falle keiner Kontakte auf der DB wird eine Exception oder ein leerer Vektor zurückgegeben
	 */
	public Vector<Kontakt> findAll(){
		Vector<Kontakt> result = new Vector<Kontakt>();
		
		Connection con = DBConnection.connection();
		
		try{
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT ID, Name, Erzeugungsdatum, Modifikationsdatum, Status, GoogleMail, Kontaktliste_ID, Nutzer_ID "
					+ "FROM Kontakt " + "ORDER by ID");
			
					
		while (rs.next()){
			Kontakt k = new Kontakt();
			k.setID(rs.getInt("ID"));
			k.setName(rs.getString("Name"));
			k.setErzeugungsdatum(rs.getDate("Erzeugungsdatum"));
			k.setModifikationsdatum(rs.getDate("Modifikationsdatum"));
			k.setStatus(rs.getInt("Status"));
			k.setGoogleMail(rs.getString("GoogleMail"));
			k.setKontaktliste_id(rs.getInt("Kontaktliste_ID"));
			k.setNutzer_id(rs.getInt("Nutzer_ID"));
			
			result.addElement(k);
		}			
		}catch (SQLException e){
			e.printStackTrace();
		}
		
		return result;
				
		
		
	}
	
	/**
	 *  Einfügen eines neuen Objktes vom Typ Kontakt in die DB
	 *  der PK wird überprüft und korrigiert -> maxID +1 
	 * @param k 
	 * der zu speichernde Kontakt 
	 * @return
	 * der bereits übergebene Kontakt
	 */
	
	public Kontakt insertKontakt(Kontakt k) {
		
		Connection con = DBConnection.connection(); 
		
		try{
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT MAX(ID) AS maxID " + " FROM kontakt ");
			
			if (rs.next()) {
				
				k.setID(rs.getInt("maxID") +1);
				
				stmt = con.createStatement();
				
				stmt.executeUpdate(
						" INSERT INTO Kontakt (ID, Name, Erzeugungsdatum, Modifikationsdatum, Status, GoogleMail, Kontaktliste_ID, Nutzer_ID)"
						+ " VALUES (" + k.getID() + " ,'" + k.getName() + "' ,'"
						+ format.format(k.getErzeugungsdatum()) + "','" + format.format(k.getModifikationsdatum()) +  "' ,'"  
						+ k.getStatus() + "','"  + k.getGoogleMail() + "','" 
						+ k.getKontaktliste_id() + "','"  + k.getNutzer_id() + "')");
						
			}
		}catch (SQLException e) {
			e.printStackTrace();

		}
		
		return k;
	}
	
	
	
	/**
	 * 
	 * @param k
	 * @return
	 */
	
	public Kontakt updateKontakt(Kontakt k) {
		Connection con = DBConnection.connection();
		
		try{
			
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE Kontakt " + "SET Name=\"" 
			+ k.getName() +"\", " 
			+ "Modifikationsdatum=\"" 
			+ format.format(k.getErzeugungsdatum()) + "\", " 
			+ "Status=\"" 
			+ k.getStatus() + "\", " 
			+ "GoogleMail=\"" 
			+ k.getGoogleMail() + "\", " 
			+ "Kontaktliste_ID=\"" 
			+ k.getKontaktliste_id() + "\", " 
			+ "Nutzer_ID=\"" 
			+k.getNutzer_id() + "\" " 
			+ "WHERE ID=" + k.getID());
			
			
			
			
			
		}catch (SQLException e){
			e.printStackTrace();
		}
		return k;
	}
	
	
	
	
	/**
	 *  ein Objekt vom Typ Kontakt wird aus der DB gelöscht 
	 * @param k
	 * 	der zu löschende Kontakt
	 * 
	 */
	
	public void deleteKontakt(Kontakt k){
		Connection con = DBConnection.connection();
		
		try {
			
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("DELETE FROM Kontakt " + "WHERE id=" + k.getID());
		}catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	
	/** 
	 * einen Kontakt anhand des Objekt-namen finden
	 * @param k
	 * @return
	 */
	
	public Kontakt findKontaktByName(Kontakt k){
		return this.findKontaktByID(k.getID());
		}
	
	/**
	 * 
	 * @param k
	 */

	

}
