package de.hdm.itprojektgruppe4.server.db;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Vector;
import de.hdm.itprojektgruppe4.shared.bo.*;

public class KontaktMapper {

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	private static KontaktMapper kontaktMapper = null;
	
	
	protected KontaktMapper() {
	};
	
	
	public static KontaktMapper kontaktMapper() {
		if (kontaktMapper == null) {
			kontaktMapper = new KontaktMapper();
		}
		return kontaktMapper;
	}	
	
	
	public Kontakt findKontaktByID(int ID){
		
		Connection con = DBConnection.connection();

		try {
			
		
		Statement stmt = con.createStatement();
		
		ResultSet rs = stmt.executeQuery(
				"SELECT ID, nutzerID, kontaktID, eigenschaftID, name, erzeugungsdatum, modifikationsdatum, status FROM kontakt " + " WHERE ID= " + ID );
				 
		
		if (rs.next()) {
			Kontakt k = new Kontakt();
			k.setID(rs.getInt("ID"));
			k.setNutzerID(rs.getInt("nutzerID"));
			k.setKontaktID(rs.getInt("kontaktID"));
			k.setEigenschaftID(rs.getInt("eigenschaftID"));
			k.setName(rs.getString("name"));
			k.setErzeugungsdatum(rs.getDate("erzeugungsdatum"));
			k.setModifikationsdatum(rs.getDate("modifikationsdatum"));
			k.setStatus(rs.getBoolean("status"));
			
			return k;
		}
		
	}catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
	return null;
	
	
	
	}
	
	public Kontakt findByProjekt(Kontakt k){
		return this.findKontaktByID(k.getID());
	}

}
