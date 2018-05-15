package de.hdm.itprojektgruppe4.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;

/**
 * Mapper Klassen, die <code>Eigenschaft</code>-Objekte auf einer relationalen Datenbank abbildet.
 * @author Raphael
 *
 */

public class EigenschaftMapper {
	
	private static EigenschaftMapper eigenschaftMapper = null;
	
	protected EigenschaftMapper() {
	};
	
	
	public static EigenschaftMapper eigenschaftMapper() {
		if (eigenschaftMapper == null) {
			eigenschaftMapper = new EigenschaftMapper();
		}
		return eigenschaftMapper;
	}	
	
	/**
	 * Suchen einer Eigenschaft mit vorgegebener ID.
	 * @param id der Eigenschaft
	 * @return die gesuchte Eigenschaft
	 */
	public Eigenschaft getEigenchaftByID(int id){
		
	// Verbindung zur Datenbank aufbauen
		Connection con = DBConnection.connection();

		try {
			
	// Leeres SQL-Statement erzeugen
		Statement stmt = con.createStatement();
		
	// Statement befüllen und als Query an die Datenbank schicken	
		ResultSet rs = stmt.executeQuery(
				"SELECT ID, bez FROM eigenschaft " + " WHERE ID= " + id );
				 
		
		if (rs.next()) {
			Eigenschaft e = new Eigenschaft();
			e.setID(rs.getInt("ID"));
			e.setBezeichnung(rs.getString("bez"));
			
			return e;
		}
		
	}catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
	return null;
	
	}
	
	/**
	 * Speichern eines <code>Eigenschaft</code>-Objekts in der Datenbank.
	 * Der Primärschlüssel des Objekts
	 * @param e das zu übergebende Objekt
	 * @return das bereits übergebene Objekt mit korrigierter ID
	 */

	
	public Eigenschaft insertEigenschaft (Eigenschaft e){
		
		Connection con = DBConnection.connection();
		
		try{
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT MAX(ID) AS maxID " + "FROM eigenschaft");
			
			if(rs.next()){
				e.setID(rs.getInt("maxID") +1);
				
				stmt = con.createStatement();
				
				stmt.executeUpdate(
						
						"INSERT INTO `eigenschaft`(`ID`, `bez`, `status`) "
				        + "VALUES ('"+e.getID()+"', '"+e.getBezeichnung()+"', '"
				        + ""+e.getStatus()+"')");
			}
			
			
		}catch (SQLException k){
			k.printStackTrace();
		}
		return e;
	}
	
	public Eigenschaft updateEigenschaft(Eigenschaft e){
		
		Connection con = DBConnection.connection();
		
		try{
			
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate(
					
					"UPDATE eigenschaft " + "SET ID=\"" + e.getID() + "\", " 
					+ "bez=\""+ e.getBezeichnung() + "\", " + "status=\""
					+ e.getStatus() + "\" " + "WHERE id=" + e.getID());
			
		}catch (SQLException k){
			k.printStackTrace();
		}
		return e;
	}
	
	public Eigenschaft deleteEigenschaft(Eigenschaft e){
		Connection con = DBConnection.connection();
		
		try{
			
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate(
					
				      "DELETE FROM eigenschaft " + "WHERE id=" + e.getID());
			
		}catch (SQLException k){
			k.printStackTrace();
			
		}return e;
	}
	
}
