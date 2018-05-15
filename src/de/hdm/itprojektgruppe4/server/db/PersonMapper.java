package de.hdm.itprojektgruppe4.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
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
	
	
public Person findPersonByID(int id){
		
		Connection con = DBConnection.connection();

		try {
			
		
		Statement stmt = con.createStatement();
		
		ResultSet rs = stmt.executeQuery(
				"SELECT ID FROM person " + " WHERE ID= " + id );
				 
		
		if (rs.next()) {
			Person p = new Person();
			p.setID(rs.getInt("ID"));
			
			return p;
		}
		
	}catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
	return null;
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
		
	public void deletePerson(Person p){
		Connection con = DBConnection.connection();
		
		try {
			
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("DELETE FROM person " + "WHERE ID=" + p.getID());
		}catch (SQLException e){
			e.printStackTrace();
		}
	}
	
		
	}
	
	

