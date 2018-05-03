package de.hdm.itprojektgruppe4.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;

public class EigenschaftauspraegungMapper {
	
	private static EigenschaftauspraegungMapper eigenschaftauspraegungMapper = null;
	
	protected EigenschaftauspraegungMapper() {
	};
	
	
	public static EigenschaftauspraegungMapper eigenschaftMapper() {
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
				"SELECT ID, Wert FROM Eigenschaft " + " WHERE ID= " + id );
				 
		
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

}
