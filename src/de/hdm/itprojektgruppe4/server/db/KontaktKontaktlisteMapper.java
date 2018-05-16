/**
 * Die Mapper-Klasse <code>KontaktKontaktlistenMapper</cod> ermöglicht das Abbilden von Objekten der Klasse <code>KontaktKontakliste</code>
 * in einer relationalen Datenbank. Der Mapper enthält Methoden zum Erzeugen, Löschen und Modifizieren der Objekte.
 * @version 
 * @author Raphael
 */
package de.hdm.itprojektgruppe4.server.db;

import java.sql.*;
import java.util.Vector;
import de.hdm.itprojektgruppe4.shared.bo.KontaktKontaktliste;

public class KontaktKontaktlisteMapper {

	private static KontaktKontaktlisteMapper kontaktkontaktlisteMapper = null;
	
	protected KontaktKontaktlisteMapper(){
		
	};
	
	public KontaktKontaktlisteMapper kontaktkontaktlistemapper(){
		if(kontaktkontaktlisteMapper == null){
			kontaktkontaktlisteMapper = new KontaktKontaktlisteMapper();
		}
		return kontaktkontaktlisteMapper;
	}
	
	
	
	
	/**
	 * Hinzufügen eines neuen Objektes der Klasse KontaktKontaktliste in die Datenbank.
	 * @param k
	 * @return k
	 */
	
	public KontaktKontaktliste insertKontaktKontaktliste (KontaktKontaktliste k){
		
		Connection con = DBConnection.connection();
		
		try{
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT MAX(ID) AS maxID " + " FROM kontaktkontaktliste ");
			
			if(rs.next()){
				
				k.setID(rs.getInt("maxID") +1);
				
				stmt = con.createStatement();
				
				stmt.executeUpdate("INSERT INTO kontaktkontaktliste (ID, kontaktID, kontaktlisteID)" + "VALUES (" + k.getID() + " ,'" + k.getKontaktID() + " ,'" + k.getKontaktlisteID() + "')");
				
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return k;
	}
	
	/**
	 * Ermöglicht das Löschen eines Objektes der Klasse KontaktKontaktliste anhand der ID.
	 * @param k
	 */
	public void deleteKontaktKontaktliste (KontaktKontaktliste k){
		Connection con = DBConnection.connection();
		
		try{
			
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("DELETE from kontaktkontaktliste" + "WHERE ID= " + k.getID());
			
			
		}catch(SQLException e){
			e.printStackTrace();
			
		}
		
	}
	/**
	 * Ermöglicht das Löschen eines Objektes der Klasse KontaktKontaktliste anhand des Fremdschlüssels KontaktID
	 * @param k
	 */
	public void deleteKontaktKontaktlisteByKontaktID (KontaktKontaktliste k){
		Connection con = DBConnection.connection();
		
		try{
			
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("DELETE from kontaktkontaktliste" + "WHERE ID= " + k.getKontaktID());
			
			
		}catch(SQLException e){
			e.printStackTrace();
			
		}
	}
	
	/**
	 * Ermöglicht das Löschen eines Objekts der Klasse KontaktKontaktliste anhand des Fremdschlüssels KontaktlisteID
	 * @param k
	 */
	
	public void deleteKontaktKontaktlisteByKontaktlisteID (KontaktKontaktliste k){
		Connection con = DBConnection.connection();
		
		try{
			
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("DELETE from kontaktkontaktliste" + "WHERE ID= " + k.getKontaktlisteID());
			
			
		}catch(SQLException e){
			e.printStackTrace();
			
		}
	}
}
