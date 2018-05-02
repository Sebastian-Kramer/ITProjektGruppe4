package de.hdm.itprojektgruppe4.server.db;

import java.sql.*;
import java.util.Vector;

import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;

public class KontaktlisteMapper {
	
	
	private static KontaktlisteMapper kontaktlisteMapper = null;
	
		protected KontaktlisteMapper(){
		
		};
	
		public static KontaktlisteMapper kontaktlisteMapper(){
			if(kontaktlisteMapper == null){
				kontaktlisteMapper = new KontaktlisteMapper();
			}
			return kontaktlisteMapper;
		}
		
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

}
