package de.hdm.itprojektgruppe4.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import de.hdm.itprojektgruppe4.shared.bo.Teilhaberschaft;

public class TeilhaberschaftMapper {
	
	private static TeilhaberschaftMapper teilhaberschaftMapper = null;
	
	protected TeilhaberschaftMapper(){
		
	};
	
	public static TeilhaberschaftMapper teilhaberschaftMapper(){
		
		if(teilhaberschaftMapper == null){
			
			teilhaberschaftMapper = new TeilhaberschaftMapper();
		}
		
		return teilhaberschaftMapper;
	}
	
	public Teilhaberschaft findByID(int id) {
		
		Connection con = DBConnection.connection();

		try {
			
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					
					"SELECT `ID`, `Kontaktliste_ID`, `Kontakt_ID`, `Eigenschaft_ID`,"
					+ " `Eigenschaftsauspraegung_ID`, `Teilhaber_ID` FROM `teilhaberschaft` WHERE ID=" + id);

			if (rs.next()) {
				
				Teilhaberschaft t = new Teilhaberschaft();
				
				t.setID(rs.getInt("id"));
				t.setKontaktListeID(rs.getInt("id"));
				t.setKontaktID(rs.getInt("id"));
				t.setEigenschaftsID(rs.getInt("id"));
				t.setEigenschaftsAuspraegungID(rs.getInt("id"));
				t.setTeilhaberID(rs.getInt("id"));

				return t;
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			
			return null;
		}
		
		return null;
	}

}
