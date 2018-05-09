package de.hdm.itprojektgruppe4.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.itprojektgruppe4.shared.bo.Person;

public class PersonMapper {

	
	
	private static PersonMapper personMapper = null;
	
	
	protected PersonMapper() {
	};
	
	
	public static PersonMapper personMapper() {
		if (personMapper == null) {
			personMapper = new PersonMapper();
		}
		return personMapper;
	}	
	
	
	public int insertPerson (Person p){
		
Connection con = DBConnection.connection(); 
		
		try{
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT MAX(ID) AS maxID " + " FROM person ");
			
			if (rs.next()) {
				
				p.setID(rs.getInt("maxID") +1);
				
				stmt = con.createStatement();
				
				stmt.executeUpdate(
						" INSERT INTO Person (ID)"
						+ " VALUES (" + p.getID()+ ")");
						
			}
		}catch (SQLException e) {
			e.printStackTrace();

		}
		
		return p.getID();
	}
		
		
	}
	
	

