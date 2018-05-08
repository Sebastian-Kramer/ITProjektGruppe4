	package de.hdm.itprojektgruppe4.server.db;

import java.sql.*;
import java.util.Vector;

import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;

public class KontaktlisteMapper {
	
	
	/**
	 * 
	 */
	private static KontaktlisteMapper kontaktlisteMapper = null;
	
	
	/**
	 * 
	 */
	protected KontaktlisteMapper(){
		
	};
	
	/**
	 * 
	 */
	public static KontaktlisteMapper kontaktlisteMapper(){
		if(kontaktlisteMapper == null){
			kontaktlisteMapper = new KontaktlisteMapper();
		}
		
		return kontaktlisteMapper;
	}
	/**
	 * 	Suchen einer Kontakliste mit vorgegebener Listennummer.
	 * Da diese Nummer eindeutig ist, wird genau ein Objekt zurueck gegeben.
	 * @param id
	 * 
	 * @return Kontaktlistenobjekt, das dem uebergebenen Schluessel entspricht, null bei nicht vorhandenem DB-Tulpel.
	 */
	
		public Kontaktliste findbyid(int id){
			Connection con = DBConnection.connection();
			
			try{
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT ID, Bez FROM Kontaktliste "
	          + "WHERE ID=" + id);
				
				if(rs.next()){
					Kontaktliste k = new Kontaktliste();
					k.setID(rs.getInt("ID"));
					k.setBezeichnung(rs.getString("Bez"));
					return k;
				}
			}
			catch(SQLException e2){
				e2.printStackTrace();
				return null;
			}
			return null;
			
		}
		
		/**
		 * 
		 * @param bezeichnung
		 * @return Kontaktlistenobjekt, das dem uebergebenen Schluessel entspricht, null bei nicht vorhandenem DB-Tulpel.
		 */
	public Kontaktliste findbyBezeichnung(String bezeichnung){
		Connection con = DBConnection.connection();
		
		try{
			Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT ID, Bez, Eigentuemer_ID FROM Kontaktliste " + "WHERE Bez="
			+ bezeichnung);
		
		if(rs.next()){
			Kontaktliste k = new Kontaktliste();
			k.setID(rs.getInt("ID"));
			k.setBezeichnung(rs.getString("Bez"));
			k.setNutzerID(rs.getInt("Eigentuemer_ID"));
			return k ;
		
		
	
				}
			}
		catch(SQLException e2){
			e2.printStackTrace();
			return null;
		}
		return null;
	}
	
	/**
	 * Hinzufuegen eines neuen Objektes vom Typ Kontaktliste in die Datenbank.
	 * Der Primaerschluessel wird ueberprueft und korrigiert -> maxID +1
	 * @param k
	 * @return k
	 */
		public Kontaktliste createKontaktliste(Kontaktliste k) {
		
		Connection con = DBConnection.connection(); 
		
		try{
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT MAX(ID) AS maxID " + " FROM kontaktliste ");
			
			if (rs.next()) {
				
				k.setID(rs.getInt("maxID") +1);
				
				stmt = con.createStatement();
				
				stmt.executeUpdate(
						"INSERT INTO Kontaktliste (ID, Bez, Eigentuemer_ID)" + " VALUES (" + k.getID() + "x ,'" + k.getBezeichnung() 
						+ "' ,'" + k.getNutzerID() + "')");
					
						
			}
		}catch (SQLException e) {
			e.printStackTrace();

		}	
		
		return k;
}
		
		/**
		 * Ein Objekt vom Typ Kontaktliste wird in der Datenbank ueberschrieben
		 * @param k
		 * @return K
		 */
		
		public Kontaktliste updateKontaktliste(Kontaktliste k) {
			Connection con = DBConnection.connection();
			
			try{
				
				Statement stmt = con.createStatement();
				
				stmt.executeUpdate("UPDATE Kontaktliste " + "SET Bez=\"" 
				+ k.getBezeichnung() +"\", " 
				+ "Eigentuemer_ID=\"" 
				+ k.getNutzerID() + "\" " 
				+ "WHERE ID=" + k.getID());
				
				
				
			}catch (SQLException e){
				e.printStackTrace();
			}
			return k;
		}
		
		/**
		 * Ein Objekt vom Typ Kontaktliste wird aus der Datenbank geloescht
		 * @param k
		 * 
		 */
		
		public void deleteKontaktliste(Kontaktliste k){
			Connection con = DBConnection.connection();
			
			try {
				
				Statement stmt = con.createStatement();
				
				stmt.executeUpdate("DELETE FROM Kontaktliste " + "WHERE ID= " + k.getID());
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
}