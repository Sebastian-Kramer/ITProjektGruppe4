package de.hdm.itprojektgruppe4.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;

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
	
	public Eigenschaft getEigenchaftByID(int id){
		
		Connection con = DBConnection.connection();

		try {
			
		
		Statement stmt = con.createStatement();
		
		ResultSet rs = stmt.executeQuery(
				"SELECT ID, Bez FROM Eigenschaft " + " WHERE ID= " + id );
				 
		
		if (rs.next()) {
			Eigenschaft e = new Eigenschaft();
			e.setID(rs.getInt("id"));
			e.setBezeichnung(rs.getString("Bez"));
			
			return e;
		}
		
	}catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
	return null;
	
	}
	
}
