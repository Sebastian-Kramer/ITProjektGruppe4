package de.hdm.itprojektgruppe4.server.db;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Vector;
import de.hdm.itprojektgruppe4.shared.bo.*;
import java.text.SimpleDateFormat;
import java.util.Date;




public class NutzerMapper extends PersonMapper {

	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	
private static NutzerMapper nutzerMapper = null;
	
	
	protected NutzerMapper() {
	};
	
	
	public static NutzerMapper nutzerMapper() {
		if (nutzerMapper == null) {
			nutzerMapper = new NutzerMapper();
		}
		return nutzerMapper;
	}	
	
	public Nutzer findNutzerByID(int id){
		
		Connection con = DBConnection.connection();
		
		try{
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT ID, email FROM nutzer " + " WHERE ID= " + id);
					
			if (rs.next()){
				Nutzer n = new Nutzer();
				n.setID(rs.getInt("ID"));
				n.setEmail(rs.getString("email"));
			
				return n;
			}
					
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		
		return null;
	}
	
	
}
