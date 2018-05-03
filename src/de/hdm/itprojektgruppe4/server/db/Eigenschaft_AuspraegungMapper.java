package de.hdm.itprojektgruppe4.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft_Auspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;

public class Eigenschaft_AuspraegungMapper {
	
private static Eigenschaft_AuspraegungMapper eigenschaft_auspraegungMapper = null;
	
	protected Eigenschaft_AuspraegungMapper() {
	}
	
	
	public static Eigenschaft_AuspraegungMapper eigenschaft_auspraegungMapper() {
		if (eigenschaft_auspraegungMapper == null) {
			eigenschaft_auspraegungMapper = new Eigenschaft_AuspraegungMapper();
		}
		return eigenschaft_auspraegungMapper;
	}	
	
	public Eigenschaft_Auspraegung getEigenchaftAuspraegungByID(int id){
		
		Connection con = DBConnection.connection();

		try {
			
		
		Statement stmt = con.createStatement();
		
		ResultSet rs = stmt.executeQuery(
				"SELECT ID, Eigenschaft_ID, Eigenschaftauspraegung_ID, Kontakt_ID FROM Eigenschaft_Auspraegung " + " WHERE ID= " + id );
				 
		
		if (rs.next()) {
			Eigenschaft_Auspraegung e = new Eigenschaft_Auspraegung();
			e.setID(rs.getInt("ID"));
			e.setEigenschaft_id(rs.getInt("Eigenschaft_ID"));
			e.setEigenschaftauspraegung_id(rs.getInt("Eigenschaftauspraegung_ID"));
			e.setKontakt_id(rs.getInt("Kontakt_ID"));
			
			return e;
		}
		
	}catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
	return null;
	
	}

}
