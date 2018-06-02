/**
 * Die Mapper-Klasse <code>KontaktKontaktlistenMapper</cod> erm�glicht das Abbilden von Objekten der Klasse <code>KontaktKontakliste</code>
 * in einer relationalen Datenbank. Der Mapper enth�lt Methoden zum Erzeugen, L�schen und Modifizieren der Objekte.
 * @version 
 * @author Raphael
 */
package de.hdm.itprojektgruppe4.server.db;

import java.sql.*;
import java.util.Vector;

import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.KontaktKontaktliste;

public class KontaktKontaktlisteMapper {

	private static KontaktKontaktlisteMapper kontaktkontaktlisteMapper = null;
	
	protected KontaktKontaktlisteMapper(){
		
	};
	
	public static KontaktKontaktlisteMapper kontaktkontaktlistemapper(){
		if(kontaktkontaktlisteMapper == null){
			kontaktkontaktlisteMapper = new KontaktKontaktlisteMapper();
		}
		return kontaktkontaktlisteMapper;
	}
	
	
	
	
	/**
	 * Hinzuf�gen eines neuen Objektes der Klasse KontaktKontaktliste in die Datenbank.
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
	
	
	// 
	
	public Vector<KontaktKontaktliste> findKontaktKontaktlisteByKontaktlisteID (int i) {
			
			Vector<KontaktKontaktliste> result = new Vector<KontaktKontaktliste>();
			
			Connection con = DBConnection.connection();
			
			try{
				Statement stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery("SELECT ID, kontaktID, kontaktlisteID FROM kontaktkontaktliste "
						 + "WHERE kontaktlisteID = " + i);
				
				while (rs.next()){
					KontaktKontaktliste kk = new KontaktKontaktliste();
					kk.setID(rs.getInt("ID"));
					kk.setKontaktID(rs.getInt("kontaktID"));
					kk.setKontaktlisteID(rs.getInt("kontaktlisteID"));
					
					result.addElement(kk);
	
				}			
				}catch (SQLException e){
					e.printStackTrace();
				}
				
				return result;
		}
	
	
	
	public Vector<KontaktKontaktliste> findKontaktKontaktlisteByKontaktID (int i) {
		
		Vector<KontaktKontaktliste> result = new Vector<KontaktKontaktliste>();
		
		Connection con = DBConnection.connection();
		
		try{
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT ID, kontaktID, kontaktlisteID FROM kontaktkontaktliste "
					 + "WHERE kontaktID = " + i);
			
			while (rs.next()){
				KontaktKontaktliste kk = new KontaktKontaktliste();
				kk.setID(rs.getInt("ID"));
				kk.setKontaktID(rs.getInt("kontaktID"));
				kk.setKontaktlisteID(rs.getInt("kontaktlisteID"));
				
				result.addElement(kk);

			}			
			}catch (SQLException e){
				e.printStackTrace();
			}
			
			return result;
	}
	
	
	/**
	 * Erm�glicht das L�schen eines Objektes der Klasse KontaktKontaktliste anhand der ID.
	 * @param k
	 */
	public void deleteKontaktKontaktliste (KontaktKontaktliste k){
		Connection con = DBConnection.connection();
		
		try{
			
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("DELETE from kontaktkontaktliste " + "WHERE ID= " + k.getID());
			
			
		}catch(SQLException e){
			e.printStackTrace();
			
		}
		
	}
	/**
	 * Erm�glicht das L�schen eines Objektes der Klasse KontaktKontaktliste anhand des Fremdschl�ssels KontaktID
	 * @param k
	 */
	public void deleteKontaktKontaktlisteByKontaktID (int k){
		Connection con = DBConnection.connection();
		
		try{
			
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("DELETE FROM kontaktkontaktliste " + "WHERE kontaktID = " + k);
			
			
		}catch(SQLException e){
			e.printStackTrace();
			
		}
	}
	
	/**
	 * Erm�glicht das L�schen eines Objekts der Klasse KontaktKontaktliste anhand des Fremdschl�ssels KontaktlisteID
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
	
	/**
	 * Ausgabe eines Vectors mit allen KontaktKontaktlisten-Objekten mit dem Fremdschl�ssel der �bergebenen KontaktlisteID
	 * 
	 * @param kontaktlisteID
	 * @return Vector result mit allen KontaktKontaktlisten-Objekten mit der �bergebenen KontaktlistenID als Fremdschl�ssel
	 */
	public Vector<KontaktKontaktliste> getKontaktKontaktlisteByKontaktlisteID(int kontaktlisteID){
			Vector <KontaktKontaktliste> result = new Vector<KontaktKontaktliste>();
			
			Connection con = DBConnection.connection();
			
			try{
				
				Statement stmt = con.createStatement();
				
			ResultSet rs =	stmt.executeQuery("SELECT * FROM kontaktkontaktliste WHERE kontaktlisteID =" + kontaktlisteID);
				
			while (rs.next()){
				KontaktKontaktliste kk = new KontaktKontaktliste();
				kk.setID(rs.getInt("ID"));
				kk.setKontaktID(rs.getInt("kontaktID"));
				kk.setKontaktlisteID(rs.getInt("kontaktlisteID"));
				
				result.addElement(kk);
			}
				
			}catch (SQLException e){
				e.printStackTrace();
			}
			return result;
	}
	
}
