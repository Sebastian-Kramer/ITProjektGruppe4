package de.hdm.itprojektgruppe4.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Person;

/**
 * Mapper Klassen, die <code>Person</code>-Objekte auf einer relationalen Datenbank abbildet.
 *
 */

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
	
	/**
	 * Suchen einer Person mit vorgegebener ID.
	 * @param id der Person
	 * @return die gesuchte Person
	 */		
	
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
	
/**
 *  Einf�gen eines neuen Objktes vom Typ Person in die DB
 *  der PK wird �berpr�ft und korrigiert -> maxID +1 
 * @param p 
 * die zu speichernde Person
 * @return
 * die bereits �bergebene Person
 */
	
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
	
	/**
	 *  ein Objekt vom Typ Person wird aus der DB gel�scht 
	 * @param p
	 * 	die zu l�schende Person
	 */
		
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
	
	

