package de.hdm.itprojektgruppe4.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;


/**
 * Mapper Klassen, die <code>Eigenschaft</code>-Objekte auf einer relationalen
 * Datenbank abbildet.
 * 
 * @author Raphael
 *
 */

public class EigenschaftMapper {
	
	/**
     * Der Mapper ist ein Singelton.
     * Variable ist "static" und speichert die einzige Instanz dieser Klasse.
     */

	private static EigenschaftMapper eigenschaftMapper = null;

	/**
	 * geschützter Konstruktor, der verhindert, dass Klasse mit "new" instanziert wird.
	 */
	
	protected EigenschaftMapper() {
	};

	public static EigenschaftMapper eigenschaftMapper() {
		if (eigenschaftMapper == null) {
			eigenschaftMapper = new EigenschaftMapper();
		}
		return eigenschaftMapper;
	}

	/**
	 * Suchen von Eigenschaften nach Bezeichnung
	 * @param bez
	 * @return result
	 */
	public Vector<Eigenschaft> getEigenschaftByBezeichnung(String bez) {

		// Verbindung zur Datenbank aufbauen
		Vector<Eigenschaft> result = new Vector<Eigenschaft>();

		Connection con = DBConnection.connection();

		try {

			// Leeres SQL-Statement erzeugen
			Statement stmt = con.createStatement();

			// Statement bef�llen und als Query an die Datenbank schicken
			ResultSet rs = stmt.executeQuery("SELECT * FROM `eigenschaft` " + " WHERE `bez`='" + bez + "'");

			while (rs.next()) {
				Eigenschaft e = new Eigenschaft();
				e.setID(rs.getInt("ID"));
				e.setBezeichnung(rs.getString("bez"));
				e.setStatus(rs.getInt("status"));

				result.addElement(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}
	
	

	public Eigenschaft findEigByBezeichnung(String bez) {

		// Verbindung zur Datenbank aufbauen
		Connection con = DBConnection.connection();

		try {

			// Leeres SQL-Statement erzeugen
			Statement stmt = con.createStatement();

			// Statement bef�llen und als Query an die Datenbank schicken
			ResultSet rs = stmt.executeQuery("SELECT * FROM `eigenschaft` " + " WHERE `bez`='" + bez + "'");

			if (rs.next()) {
				Eigenschaft e = new Eigenschaft();
				e.setID(rs.getInt("ID"));
				e.setBezeichnung(rs.getString("bez"));
				e.setStatus(rs.getInt("status"));
				
				return e;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;

	}
	
	/**
	 * Suchen einer Eigenschaft mit vorgegebener ID.
	 * 
	 * @param id
	 *            der Eigenschaft
	 * @return die gesuchte Eigenschaft
	 */
	public Eigenschaft getEigenchaftByID(int id) {

		// Verbindung zur Datenbank aufbauen
		Connection con = DBConnection.connection();

		try {

			// Leeres SQL-Statement erzeugen
			Statement stmt = con.createStatement();

			// Statement bef�llen und als Query an die Datenbank schicken
			ResultSet rs = stmt.executeQuery("SELECT ID, bez, status FROM eigenschaft " + " WHERE ID= " + id);

			if (rs.next()) {
				Eigenschaft e = new Eigenschaft();
				e.setID(rs.getInt("ID"));
				e.setBezeichnung(rs.getString("bez"));
				e.setStatus(rs.getInt("status"));
				
				return e;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;

	}

	/**
	 * Speichern eines <code>Eigenschaft</code>-Objekts in der Datenbank. Der
	 * Prim�rschl�ssel des Objekts
	 * 
	 * @param e
	 *            das zu �bergebende Objekt
	 * @return das bereits �bergebene Objekt mit korrigierter ID
	 */

	public Eigenschaft insertEigenschaft(Eigenschaft e) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT MAX(ID) AS maxID " + "FROM eigenschaft");

			if (rs.next()) {
				e.setID(rs.getInt("maxID") + 1);

				stmt = con.createStatement();

				stmt.executeUpdate(

						"INSERT INTO `eigenschaft`(`ID`, `bez`, `status`) " + "VALUES ('" + e.getID() + "', '"
								+ e.getBezeichnung() + "', '" + "" + e.getStatus() + "')");
			}

		} catch (SQLException k) {
			k.printStackTrace();
		}
		return e;
	}

	/**
	 * Überschreiben eines Eigenschaft-Objekts
	 * 
	 * @param e
	 * @return e
	 */

	public Eigenschaft updateEigenschaft(Eigenschaft e) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate(

					"UPDATE eigenschaft " + "SET ID=\"" + e.getID() + "\", " + "bez=\"" + e.getBezeichnung() + "\", "
							+ "status=\"" + e.getStatus() + "\" " + "WHERE id=" + e.getID());

		} catch (SQLException k) {
			k.printStackTrace();
		}
		return e;
	}

	/**
	 * Ein Objekt des Typ Eigenschaft wird aus der Datenbank gelöscht
	 * 
	 * @param e
	 * @return
	 */

	public Eigenschaft deleteEigenschaft(Eigenschaft e) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate(

					"DELETE FROM eigenschaft " + "WHERE id=" + e.getID());

		} catch (SQLException k) {
			k.printStackTrace();

		}
		return e;
	}

	/**
	 * alle Objekte vom Typ Eigenschaft auslesen
	 * 
	 * @return Vektor sämtlicher Eigenschaften
	 */

	public Vector<Eigenschaft> findAllEigenschaft() {

		Vector<Eigenschaft> result = new Vector<Eigenschaft>();

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT ID, bez, status FROM eigenschaft " + "ORDER by ID");

			while (rs.next()) {
				Eigenschaft e = new Eigenschaft();
				e.setID(rs.getInt("ID"));
				e.setBezeichnung(rs.getString("bez"));
				e.setStatus(rs.getInt("status"));

				result.addElement(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}
/**
 * suchen einer EIgenschaftsausprägung anhand der ID
 * 
 * @param id
 * @return result
 */
	
	public Vector<Eigenschaft> findAllEigenschaftFromAuspraegungID(int id) {

		Vector<Eigenschaft> result = new Vector<Eigenschaft>();

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT ID, bez, status FROM eigenschaft WHERE ID =" + id);

			while (rs.next()) {
				Eigenschaft e = new Eigenschaft();
				e.setID(rs.getInt("ID"));
				e.setBezeichnung(rs.getString("bez"));
				e.setStatus(rs.getInt("status"));

				result.addElement(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}

	/**
	 * Methode zum Auslesen der Basiseigenschaften
	 * (Vorname, Nachname, Email, Telefonnummer)
	 * @return
	 */
	public Vector<Eigenschaft> findGivenEigenschaft() {

		Vector<Eigenschaft> result = new Vector<Eigenschaft>();

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(

					"SELECT ID, bez, status FROM eigenschaft WHERE ID= ('1')  " + "UNION "
							+ "SELECT ID, bez, status FROM eigenschaft WHERE ID= ('2')  " + "UNION " +

							"SELECT ID, bez, status FROM eigenschaft WHERE ID= ('3')  " + "UNION " +

							"SELECT ID, bez, status FROM eigenschaft  WHERE ID= ('4')");


			while (rs.next()) {
				Eigenschaft e = new Eigenschaft();
				e.setID(rs.getInt("ID"));
				e.setBezeichnung(rs.getString("bez"));
				e.setStatus(rs.getInt("status"));

				result.addElement(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}

}
