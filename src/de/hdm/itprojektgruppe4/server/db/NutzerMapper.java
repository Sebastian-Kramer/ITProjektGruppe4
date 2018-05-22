package de.hdm.itprojektgruppe4.server.db;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Vector;
import de.hdm.itprojektgruppe4.shared.bo.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Mapper Klassen, die <code>Kontakt</code>-Objekte auf einer relationalen Datenbank abbildet.
 *
 */


public class NutzerMapper extends PersonMapper {

	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	
private static NutzerMapper nutzerMapper = null;
	
	
	protected NutzerMapper() {
	};
	
	
	public static NutzerMapper nutzerMapper() {
		if (nutzerMapper == null) {
			nutzerMapper = new NutzerMapper();
		}
		return nutzerMapper;
	}	
	
	
	/**
	 * 
	 * @param id 
	 * 	Prim�rschl�ssel der DB
	 * @return Nutzer-Objekt. welches dem PK entspricht, 
	 * 			falls nicht vorhanden
	 */
	public Nutzer findNutzerByID(int id){
		
		Connection con = DBConnection.connection();
		
		try{
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT ID, email FROM nutzer " + " WHERE ID= " + id);
					
			if (rs.next()){
				Nutzer n = new Nutzer();
				n.setID(rs.getInt("ID"));
				n.setEmail(rs.getString("email"));
			
				return n;
			}
					
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		
		return null;
	}
	
	 /**
		 * alle Objekte vom Typ Nutzer auslesen
		 * @return Vektor sämtlicher Nutzer-Objekte
		 *
		 */
	
	public Vector<Nutzer> findAllNutzer(){
		Vector<Nutzer> result = new Vector<Nutzer>();
		
		Connection con = DBConnection.connection();
		
		try{
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT ID, email FROM nutzer " + "ORDER by ID");
			
			while (rs.next()){
				Nutzer n = new Nutzer();
				n.setID(rs.getInt("ID"));
				n.setEmail(rs.getString("email"));
				
				result.addElement(n);
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
     * Einen Nutzer anlegen
     * 
     * @param n
     * @return n
     */
	
	public Nutzer insertNutzer(Nutzer n) {
		
		Connection con = DBConnection.connection();
		
		try{
			
			Statement stmt = con.createStatement();
			
			n.setID(super.insertPerson(n));
			
			ResultSet rs = stmt.executeQuery("SELECT MAX(ID) AS maxID " + " FROM person");
		
			if (rs.next()) {
				
				stmt = con.createStatement();
				
				stmt.executeUpdate(
						
						"INSERT INTO nutzer (ID, email)"
						+ " VALUES (" + n.getID() + " ,'" + n.getEmail() + "')");
						
						
						
			}
		
		}catch (SQLException e){
			e.printStackTrace();
		}
		
		return n;
	}
	
	/** Ein Objekt vom Typ Nutzer wird überschrieben.
	 * 
	 * @param n der zu bearbeitende Nutzer
	 * @return n der bearbeitete Kontakt
	 */
	
	public Nutzer updateNutzer(Nutzer n) {
		Connection con = DBConnection.connection();
		
		
		try{
			
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE nutzer " + "SET email=\"" 
			+ n.getEmail()  + "\" " 
			+ "WHERE ID=" + n.getID());
			
			
			
			
		}catch (SQLException e){
			e.printStackTrace();
		}
		return n;
	}
	
	/**
	 *  ein Objekt vom Typ Nutzer wird aus der DB gel�scht 
	 * @param n
	 * 	der zu l�schende Nutzer
	 * 
	 */
	
	public void deleteNutzer(Nutzer n){
		Connection con = DBConnection.connection();
		
		try {
			
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("DELETE FROM nutzer " + "WHERE ID=" + n.getID());
		}catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	
	/**
     * Einen Nutzer anhand seiner E-Mail auslesen.
     * 
     * @param email
     * @return n
     */
	
	public Nutzer findNutzerByEmail(String email){
		
		Connection con = DBConnection.connection();

		try {
			
		
		Statement stmt = con.createStatement();
		
		ResultSet rs = stmt.executeQuery(
				"SELECT ID, email FROM nutzer " + " WHERE email= " + email );
				 
		
		if (rs.next()) {
			Nutzer n = new Nutzer();
			n.setID(rs.getInt("ID"));
			n.setEmail(rs.getString("email"));
			
			return n;
		}
		
	}catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
	return null;
	
	
	
	}
	
	
	
	
}
