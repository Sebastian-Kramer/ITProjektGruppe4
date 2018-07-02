package de.hdm.itprojektgruppe4.server.db;

import java.sql.*;
import java.util.Vector;

import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;

/**
 * Mapper Klassen, die <code>Kontaktliste</code>-Objekte auf einer relationalen
 * Datenbank abbildet.
 *
 */

public class KontaktlisteMapper {
	
	/**
     * Der Mapper ist ein Singelton.
     * Variable ist "static" und speichert die einzige Instanz dieser Klasse.
     */

	private static KontaktlisteMapper kontaktlisteMapper = null;

	/**
	 * geschützter Konstruktor, der verhindert, dass Klasse mit "new" instanziert wird.
	 */
	protected KontaktlisteMapper() {

	};

	public static KontaktlisteMapper kontaktlisteMapper() {
		if (kontaktlisteMapper == null) {
			kontaktlisteMapper = new KontaktlisteMapper();
		}

		return kontaktlisteMapper;
	}

	/**
	 * Suchen einer Kontakliste mit vorgegebener Listennummer. Da diese Nummer
	 * eindeutig ist, wird genau ein Objekt zurueck gegeben.
	 * 
	 * @param id
	 * 
	 * @return Kontaktlistenobjekt, das dem uebergebenen Schluessel entspricht
	 */

	public Kontaktliste findKontaktlistebyID(int id) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT ID, bez, status, nutzerID FROM kontaktliste " + "WHERE ID=" + id);

			if (rs.next()) {
				Kontaktliste kl = new Kontaktliste();
				kl.setID(rs.getInt("ID"));
				kl.setBez(rs.getString("bez"));
				kl.setStatus(rs.getInt("status"));
				kl.setNutzerID(rs.getInt("nutzerID"));
				return kl;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;

	}

	/**
	 * Eine Kontaktliste anhand der bezichnung auslesen
	 * 
	 * @param bezeichnung
	 * @return Kontaktlistenobjekt, das dem uebergebenen Schluessel entspricht
	 */
	public Kontaktliste findKontaktlistebyBezeichnung(String bezeichnung) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT ID, bez, status, nutzerID FROM kontaktliste " + "WHERE bez = " + "'" + bezeichnung + "'");

			if (rs.next()) {
				Kontaktliste kl = new Kontaktliste();
				kl.setID(rs.getInt("ID"));
				kl.setBez(rs.getString("bez"));
				kl.setStatus(rs.getInt("status"));
				kl.setNutzerID(rs.getInt("nutzerID"));
				return kl;

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * Auslesen der Standardkontaktliste "Meine geteilten Kontakte"
	 * 
	 * @param bezeichnung
	 * @param nutzerID die ID des Nutzers, dessen Standardkontaktliste gesucht ist
	 * @return Kontaktliste-Objekt mit den übergebenen Parametern
	 */
	public Kontaktliste findKontaktlisteMeineGeteiltenKontakte(String bezeichnung, int nutzerID) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT ID, bez, status, nutzerID FROM kontaktliste " + "WHERE bez = "
					+ "'" + bezeichnung + "'" + " AND nutzerID = " + nutzerID);

			if (rs.next()) {
				Kontaktliste kl = new Kontaktliste();
				kl.setID(rs.getInt("ID"));
				kl.setBez(rs.getString("bez"));
				kl.setStatus(rs.getInt("status"));
				kl.setNutzerID(rs.getInt("nutzerID"));
				return kl;

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * Hinzufuegen eines neuen Objektes vom Typ Kontaktliste in die Datenbank.
	 * Der Primaerschluessel wird ueberprueft und korrigiert -> maxID +1
	 * 
	 * @param k
	 * @return k
	 */
	public Kontaktliste insertKontaktliste(Kontaktliste kl) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT MAX(ID) AS maxID " + " FROM kontaktliste ");

			if (rs.next()) {

				kl.setID(rs.getInt("maxID") + 1);

				stmt = con.createStatement();

				stmt.executeUpdate("INSERT INTO kontaktliste (ID, bez, status, nutzerID)" + " VALUES (" + kl.getID()
						+ " ,'" + kl.getBez() + "' ,'" + kl.getStatus() + "' ,'" + kl.getNutzerID() + "')");

			}
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return kl;
	}

	/**
	 * Ein Objekt vom Typ Kontaktliste wird in der Datenbank ueberschrieben
	 * 
	 * @param k die zu überschreibende Kontaktliste
	 * @return K das überschriebene Kontaktliste-Objekt
	 */

	public Kontaktliste updateKontaktliste(Kontaktliste k) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE kontaktliste " + "SET bez=\"" + k.getBez() + "\", " + "status=\"" + k.getStatus()
					+ "\" " + "WHERE ID=" + k.getID());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return k;
	}

	/**
	 * Ein Objekt vom Typ Kontaktliste wird aus der Datenbank geloescht
	 * 
	 * @param k das zu löschende Kontaktliste-Objekt
	 * 
	 */

	public void deleteKontaktliste(Kontaktliste k) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM kontaktliste " + "WHERE ID= " + k.getID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * alle Objekte vom Typ Kontaktliste auslesen
	 * 
	 * @return Vektor sämtlicher Eigenschaften
	 */

	public Vector<Kontaktliste> findKontaktlisteAll() {
		Vector<Kontaktliste> result = new Vector<Kontaktliste>();

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT ID, bez, status, nutzerID FROM kontaktliste" + " ORDER by ID");

			while (rs.next()) {
				Kontaktliste kl = new Kontaktliste();
				kl.setID(rs.getInt("ID"));
				kl.setBez(rs.getString("bez"));
				kl.setStatus(rs.getInt("status"));
				kl.setNutzerID(rs.getInt("nutzerID"));

				result.addElement(kl);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Alle erstellten Kontaktlisten eines Nutzers auslesen
	 * 
	 * @param int nutzerID die ID des Nutzers, desse Kontaktlisten ausgelesen werden sollen
	 * @return Vector mit allen erstellten Kontaktlisten eines Nutzers
	 */

	public Vector<Kontaktliste> findKontaktlisteByNutzerID(int nutzerID) {
		Vector<Kontaktliste> result = new Vector<Kontaktliste>();

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT ID, bez, status, nutzerID FROM kontaktliste " + "WHERE nutzerID= "
					+ nutzerID + " ORDER by ID");

			while (rs.next()) {
				Kontaktliste kl = new Kontaktliste();
				kl.setID(rs.getInt("ID"));
				kl.setBez(rs.getString("bez"));
				kl.setStatus(rs.getInt("status"));
				kl.setNutzerID(rs.getInt("nutzerID"));

				result.addElement(kl);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}

	/**
	 * Die Basis Kontaktliste "Meine Kontakte" des Nutzers, in welcher alle
	 * Kontakte gepsiechert werden, finden.
	 * 
	 * @param nutzerID
	 * @return "Meine Kontakte" Liste des Nutzers
	 */

	public Kontaktliste findBasicKontaktliste(int nutzerID) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(

					"SELECT ID, bez, status, nutzerID FROM `kontaktliste` WHERE `bez`=" + "'Meine Kontakte'" + " "
							+ "AND `nutzerID` =" + nutzerID);

			if (rs.next()) {
				Kontaktliste kl = new Kontaktliste();
				kl.setID(rs.getInt("ID"));
				kl.setBez(rs.getString("bez"));
				kl.setStatus(rs.getInt("status"));
				kl.setNutzerID(rs.getInt("nutzerID"));

				return kl;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;

	}
	
	/**
	 * Suchen einer Kontaktliste nach Nutzer und Bezeichnung
	 * @param nutzerID
	 * @param bez
	 * @return result
	 */

	public Kontaktliste findKontaktliste(int nutzerID, String bez) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(

					"SELECT ID, bez, status, nutzerID FROM kontaktliste WHERE bez='" + bez + "' and nutzerID="
							+ nutzerID);

			if (rs.next()) {
				Kontaktliste kl = new Kontaktliste();
				kl.setID(rs.getInt("ID"));
				kl.setBez(rs.getString("bez"));
				kl.setStatus(rs.getInt("status"));
				kl.setNutzerID(rs.getInt("nutzerID"));

				return kl;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;

	}

}
