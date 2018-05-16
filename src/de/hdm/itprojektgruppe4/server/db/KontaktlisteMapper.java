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
	
		public Kontaktliste findKontaktlistebyID(int id){
			Connection con = DBConnection.connection();
			
			try{
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT ID, bez, status FROM kontaktliste "
	          + "WHERE ID=" + id);
				
				if(rs.next()){
					Kontaktliste kl = new Kontaktliste();
					kl.setID(rs.getInt("ID"));
					kl.setBez(rs.getString("bez"));
					kl.setStatus(rs.getInt("status"));
					return kl;
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
	public Kontaktliste findKontaktlistebyBezeichnung(String bezeichnung){
		Connection con = DBConnection.connection();
		 
		try{
			Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT ID, bez, status FROM kontaktliste " + "WHERE bez ="
			+ bezeichnung);
		
		if(rs.next()){
			Kontaktliste kl = new Kontaktliste();
			kl.setID(rs.getInt("ID"));
			kl.setBez(rs.getString("bez"));

			return kl ;
		
		
	
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
		public Kontaktliste insertKontaktliste(Kontaktliste kl) {
		
		Connection con = DBConnection.connection(); 
		
		try{
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT MAX(ID) AS maxID " + " FROM kontaktliste ");
			
			if (rs.next()) {
				
				kl.setID(rs.getInt("maxID") +1);
				
				stmt = con.createStatement();
				
				stmt.executeUpdate(
						"INSERT INTO kontaktliste (ID, bez, status)" + " VALUES (" + kl.getID() + " ,'" + kl.getBez() + "' ,'" + kl.getStatus() + "')");
					
						
			}
		}catch (SQLException e) {
			e.printStackTrace();

		}	
		
		return kl;
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
				
				stmt.executeUpdate("UPDATE kontaktliste " + "SET bez=\"" 
				+ k.getBez() +"\", " 
				+ "status=\"" 
				+ k.getStatus() + "\" " 
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
				
				stmt.executeUpdate("DELETE FROM kontaktliste " + "WHERE ID= " + k.getID());
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
		
		
		public Vector<Kontaktliste> findKontaktlisteAll(){
			Vector<Kontaktliste> result = new Vector<Kontaktliste>();
			
			Connection con = DBConnection.connection();
			
			try{
				
				Statement stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery("SELECT ID, bez, status FROM kontaktliste" + " ORDER by ID");
			
			while (rs.next()){
				Kontaktliste kl = new Kontaktliste();
				kl.setID(rs.getInt("ID"));
				kl.setBez(rs.getString("bez"));
				kl.setStatus(rs.getInt("status"));
				
				result.addElement(kl);
			}
			}catch (SQLException e){
				e.printStackTrace();
			}
			
			return result;
		}
		
}