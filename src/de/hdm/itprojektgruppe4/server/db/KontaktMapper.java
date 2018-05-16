package de.hdm.itprojektgruppe4.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Vector;
import de.hdm.itprojektgruppe4.shared.bo.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KontaktMapper extends PersonMapper {

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
	 * 	Prim�rschl�ssel der DB
	 * @return Kontakt-Objekt. welches dem PK entspricht, 
	 * 			falls nicht vorhanden, dann null
	 */
	
	public Kontakt findKontaktByID(int id){
		
		Connection con = DBConnection.connection();

		try {
			
		
		Statement stmt = con.createStatement();
		
		ResultSet rs = stmt.executeQuery(
				"SELECT ID, name, erzeugungsdatum, modifikationsdatum, status, nutzerID FROM kontakt " + " WHERE ID= " + id );
				 
		
		if (rs.next()) {
			Kontakt k = new Kontakt();
			k.setID(rs.getInt("ID"));
			k.setName(rs.getString("name"));
			k.setErzeugungsdatum(rs.getDate("erzeugungsdatum"));
			k.setModifikationsdatum(rs.getDate("modifikationsdatum"));
			k.setStatus(rs.getInt("status"));
			k.setNutzerID(rs.getInt("nutzerID"));
			
			return k;
		}
		
	}catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
	return null;
	
	
	
	}
	
	/**
	 * 	Ein Kontakt anhand des Attributs Name finden. 
	 * @param name der Name als String
	 * @return der Kontakt bei dem der Name �bereinstimmte 
	 */
	
	
	public Kontakt findKontaktByName(String name){
		
		Connection con = DBConnection.connection();

		try {
			
		
		Statement stmt = con.createStatement();
		
		ResultSet rs = stmt.executeQuery(
				"SELECT ID, name, erzeugungsdatum, modifikationsdatum, status, nutzerID FROM kontakt " + " WHERE name= "+ "' "+ name +" '");
		

		if (rs.next()) {
			Kontakt k = new Kontakt();
			k.setID(rs.getInt("ID"));
			k.setName(rs.getString("name"));
			k.setErzeugungsdatum(rs.getDate("erzeugungsdatum"));
			k.setModifikationsdatum(rs.getDate("modifikationsdatum"));
			k.setStatus(rs.getInt("status"));
			k.setNutzerID(rs.getInt("nutzerID"));
			
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
	 * 			Im falle keiner Kontakte auf der DB wird eine Exception oder ein leerer Vektor zur�ckgegeben
	 */
	public Vector<Kontakt> findAllKontakte(){
		Vector<Kontakt> result = new Vector<Kontakt>();
		
		Connection con = DBConnection.connection();
		
		try{
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT ID, name, erzeugungsdatum, modifikationsdatum, status, nutzerID FROM kontakt "
					 + "ORDER by ID");
			
					
		while (rs.next()){
			Kontakt k = new Kontakt();
			k.setID(rs.getInt("ID"));
			k.setName(rs.getString("name"));
			k.setErzeugungsdatum(rs.getDate("erzeugungsdatum"));
			k.setModifikationsdatum(rs.getDate("modifikationsdatum"));
			k.setStatus(rs.getInt("status"));
			k.setNutzerID(rs.getInt("nutzerID"));
			
			result.addElement(k);
		}			
		}catch (SQLException e){
			e.printStackTrace();
		}
		
		return result;
				
		
		
	}
	
	/**
	 *  Einf�gen eines neuen Objktes vom Typ Kontakt in die DB
	 *  der PK wird �berpr�ft und korrigiert -> maxID +1 
	 * @param k 
	 * der zu speichernde Kontakt 
	 * @return
	 * der bereits �bergebene Kontakt
	 */
	
	public Kontakt insertKontakt(Kontakt k) {
		
		Connection con = DBConnection.connection(); 
		
		try{
			
			Statement stmt = con.createStatement();
			
			k.setID(super.insertPerson(k));
			
			ResultSet rs = stmt.executeQuery("SELECT MAX(ID) AS maxID " + " FROM person");
			 
			if (rs.next()) {
				
				stmt = con.createStatement();
				
				stmt.executeUpdate(
					
						" INSERT INTO kontakt (ID, name, erzeugungsdatum, modifikationsdatum, status, nutzerID)"
						+ " VALUES (" + k.getID() + " ,'" + k.getName() + "' ,'"
						+ format.format(k.getErzeugungsdatum()) + "','" + format.format(k.getModifikationsdatum()) +  "' ,'"  
						+ k.getStatus() + "','"  + k.getNutzerID() + "')");
						
			}
		}catch (SQLException e) {
			e.printStackTrace();

		}
		
		return k;
	}
	
	
	
	/** Ein Objekt vom Typ Kontakt wird geupdated.
	 * 
	 * @param k der zu bearbeitende Kontakt
	 * @return der bearbeitete Kontakt
	 */
	
	public Kontakt updateKontakt(Kontakt k) {
		Connection con = DBConnection.connection();
		
		
		try{
			
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE kontakt " + "SET name=\"" 
			+ k.getName() +"\", " 
			+ "modifikationsdatum=\"" 
			+ format.format(k.getModifikationsdatum()) + "\", " 
			+ "status=\"" 
			+ k.getStatus() + "\", " 
			+ "nutzerID=\"" 
			+k.getNutzerID() + "\" " 
			+ "WHERE ID=" + k.getID());
			
			
			
			
		}catch (SQLException e){
			e.printStackTrace();
		}
		return k;
	}
	
	
	
	
	/**
	 *  ein Objekt vom Typ Kontakt wird aus der DB gel�scht 
	 * @param k
	 * 	der zu l�schende Kontakt
	 * 
	 */
	
	public void deleteKontakt(Kontakt k){
		Connection con = DBConnection.connection();
		
		try {
			
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("DELETE FROM kontakt " + "WHERE ID=" + k.getID());
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

	public Vector<Kontakt> findKontaktByNutzerID(int nutzerID){
		Vector<Kontakt> result = new Vector<Kontakt>();
		
		Connection con = DBConnection.connection();
		
		try{
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT ID, name FROM kontakt "+ "WHERE nutzerID= " + nutzerID
					 + " ORDER by ID");
			
					
		while (rs.next()){
			Kontakt k = new Kontakt();
			k.setID(rs.getInt("ID"));
			k.setName(rs.getString("name"));
		
			result.addElement(k);
		}			
		}catch (SQLException e){
			e.printStackTrace();
		}
		
		return result;
	
	}
	

}
