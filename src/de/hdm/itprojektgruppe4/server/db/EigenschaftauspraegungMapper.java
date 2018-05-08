package de.hdm.itprojektgruppe4.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;

public class EigenschaftauspraegungMapper {
	
	private static EigenschaftauspraegungMapper eigenschaftauspraegungMapper = null;
	
	protected EigenschaftauspraegungMapper() {
	};
	
	
	public static EigenschaftauspraegungMapper eigenschaftauspraegungMapper() {
		if (eigenschaftauspraegungMapper == null) {
			eigenschaftauspraegungMapper = new EigenschaftauspraegungMapper();
		}
		return eigenschaftauspraegungMapper;
	}	
	
	public Eigenschaftauspraegung getEigenchaftauspraegungByID(int id){
		
		Connection con = DBConnection.connection();

		try {
			
		
		Statement stmt = con.createStatement();
		
		ResultSet rs = stmt.executeQuery(
				"SELECT ID, Wert FROM Eigenschaftsauspraegung " + " WHERE ID= " + id );
				 
		
		if (rs.next()) {
			Eigenschaftauspraegung e = new Eigenschaftauspraegung();
			e.setID(rs.getInt("ID"));
			e.setWert(rs.getString("Wert"));
			
			return e;
		}
		
	}catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
	return null;
	
	}
	
	/**
	 *  Einfügen eines neuen Objktes vom Typ Eigenschaftauspraegung in die DB
	 *  der PK wird überprüft und korrigiert -> maxID +1 
	 * @param ea 
	 * die zu speichernde Eigenschaftauspraegung 
	 * @return
	 * die bereits übergebene Eigenschaftauspraegung
	 */
public Eigenschaftauspraegung insertAuspraegung(Eigenschaftauspraegung ea) {
		
		Connection con = DBConnection.connection(); 
		
		try{
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT MAX(ID) AS maxID " + " FROM eigenschaftsauspraegung ");
			
			if (rs.next()) {
				
				ea.setID(rs.getInt("maxID") +1);
				
				stmt = con.createStatement();
				
				stmt.executeUpdate(
						" INSERT INTO Eigenschaftsauspraegung (ID, Wert, Eigentuemer_ID)"
						+ " VALUES (" + ea.getID() + " ,'" + ea.getWert() + "' ,'"
						+ ea.getEigentuemer_id() + "')");
						
			}
		}catch (SQLException e) {
			e.printStackTrace();

		}
		
		return ea;
	}
/**
 * 
 * @param ea
 * @return
 */

public Eigenschaftauspraegung updateAuspraegung(Eigenschaftauspraegung ea) {
	Connection con = DBConnection.connection();
	
	try{
		
		Statement stmt = con.createStatement();
		
		stmt.executeUpdate("UPDATE Eigenschaftsauspraegung " + "SET Wert=\"" 
		+ ea.getWert() +"\", " 
		+ "Eigentuemer_ID=\"" 
		+ ea.getEigentuemer_id() + "\" " 
		+ "WHERE ID=" + ea.getID());
	
	}catch (SQLException e){
		e.printStackTrace();
	}
	return ea;
}

/**
 *  ein Objekt vom Typ Eigenschaftauspraegung wird aus der DB gelöscht 
 * @param ea
 * 	die zu löschende Eigenschaftauspraegung
 * 
 */
public void deleteAuspraegung(Eigenschaftauspraegung ea){
	Connection con = DBConnection.connection();
	
	try {
		
		Statement stmt = con.createStatement();
		
		stmt.executeUpdate("DELETE FROM Eigenschaftsauspraegung " + "WHERE ID=" + ea.getID());
	}catch (SQLException e){
		e.printStackTrace();
	}
}

}
