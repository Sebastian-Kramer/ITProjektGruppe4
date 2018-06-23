/**
 * Die Mapper-Klasse <code>KontaktKontaktlistenMapper</cod> erm�glicht das Abbilden von Objekten der Klasse <code>KontaktKontakliste</code>
 * in einer relationalen Datenbank. Der Mapper enth�lt Methoden zum Erzeugen, L�schen und Modifizieren der Objekte.
 * @version 
 * @author Raphael
 */
package de.hdm.itprojektgruppe4.server.db;

import java.sql.*;
import java.util.Vector;

import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.KontaktKontaktliste;

public class KontaktKontaktlisteMapper {

	private static KontaktKontaktlisteMapper kontaktkontaktlisteMapper = null;

	protected KontaktKontaktlisteMapper() {

	};

	public static KontaktKontaktlisteMapper kontaktkontaktlistemapper() {
		if (kontaktkontaktlisteMapper == null) {
			kontaktkontaktlisteMapper = new KontaktKontaktlisteMapper();
		}
		return kontaktkontaktlisteMapper;
	}

	/**
	 * Hinzufuegen eines neuen Objektes der Klasse KontaktKontaktliste in die
	 * Datenbank.
	 * 
	 * @param k
	 * @return k
	 */

	public KontaktKontaktliste insertKontaktKontaktliste(KontaktKontaktliste k) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT MAX(ID) AS maxID " + " FROM kontaktkontaktliste ");

			if (rs.next()) {

				k.setID(rs.getInt("maxID") + 1);

				// stmt = con.createStatement();

				stmt.executeUpdate(
						"INSERT INTO `kontaktkontaktliste`(`ID`, `kontaktID`," + " `kontaktlisteID`) " + "VALUES ('"
								+ k.getID() + "', '" + k.getKontaktID() + "', '" + "" + k.getKontaktlisteID() + "')");

				// INSERT INTO kontaktkontaktliste (ID, kontaktID,
				// kontaktlisteID) " + "VALUES (" + k.getID() + " ,'" +
				// k.getKontaktID() + " ,'" + k.getKontaktlisteID() + "')");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return k;
	}

	//

	public Vector<KontaktKontaktliste> findKontaktKontaktlisteByKontaktlisteID(int i) {

		Vector<KontaktKontaktliste> result = new Vector<KontaktKontaktliste>();

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT ID, kontaktID, kontaktlisteID FROM kontaktkontaktliste " + "WHERE kontaktlisteID = " + i);

			while (rs.next()) {
				KontaktKontaktliste kk = new KontaktKontaktliste();
				kk.setID(rs.getInt("ID"));
				kk.setKontaktID(rs.getInt("kontaktID"));
				kk.setKontaktlisteID(rs.getInt("kontaktlisteID"));

				result.addElement(kk);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 
	 * @param kontaktID
	 * @param kListID
	 * @return Kontaktliste kk
	 */
	public KontaktKontaktliste findKontaktKontaktlisteByKontaktIDAndKlisteID(int kontaktID, int kListID) {
		KontaktKontaktliste kk = new KontaktKontaktliste();

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT ID, kontaktID, kontaktlisteID FROM kontaktkontaktliste " + "WHERE kontaktID = " + kontaktID + " AND kontaktlisteID = " + kListID);

			while (rs.next()) {
		
				kk.setID(rs.getInt("ID"));
				kk.setKontaktID(rs.getInt("kontaktID"));
				kk.setKontaktlisteID(rs.getInt("kontaktlisteID"));

				return kk;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return kk;
	}

	/**
	 * Auslesen eines Vectors mit KontaktKontaktliste-Objekten, die die übergebene kontaktID als Fremdschlüssel besitzen
	 * @param kontaktID
	 * @return Vector result mit KontaktKontaktliste-Objekten, die die übergebene kontaktID als Fremdschlüssel besitzen
	 */
	public Vector<KontaktKontaktliste> findKontaktKontaktlisteByKontaktID(int kontaktID) {

		Vector<KontaktKontaktliste> result = new Vector<KontaktKontaktliste>();

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT ID, kontaktID, kontaktlisteID FROM kontaktkontaktliste " + "WHERE kontaktID = " + kontaktID);

			while (rs.next()) {
				KontaktKontaktliste kk = new KontaktKontaktliste();
				kk.setID(rs.getInt("ID"));
				kk.setKontaktID(rs.getInt("kontaktID"));
				kk.setKontaktlisteID(rs.getInt("kontaktlisteID"));

				result.addElement(kk);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Ermoeglicht das Loeschen eines Objektes der Klasse KontaktKontaktliste
	 * anhand der ID.
	 * 
	 * @param k
	 */
	public void deleteKontaktKontaktliste(KontaktKontaktliste k) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE from kontaktkontaktliste " + "WHERE ID= " + k.getID());

		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

	/**
	 * Ermoeglicht das Loeschen eines Objektes der Klasse KontaktKontaktliste
	 * anhand des Fremdschluessels KontaktID
	 * 
	 * @param k
	 *            die ID des Kontaktes
	 */
	public void deleteKontaktKontaktlisteByKontaktID(int k) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM kontaktkontaktliste " + "WHERE kontaktID = " + k);

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	/**
	 * Ermoeglicht das Loeschen eines Objektes der Klasse KontaktKontaktliste
	 * anhand des Fremdschluessels KontaktID
	 * 
	 * @param k
	 */
	public void deleteKontaktKontaktlisteByKontaktIDAndByKListID(int kontaktID, int kontaktlisteID) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM kontaktkontaktliste " + "WHERE kontaktID = " + kontaktID
					+ " AND kontaktlisteID = " + kontaktlisteID);

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	/**
	 * Ermöglicht das Löschen eines Objekts der Klasse KontaktKontaktliste
	 * anhand des Fremdschlüssels KontaktlisteID
	 * 
	 * @param k
	 */

	public void deleteKontaktKontaktlisteByKontaktlisteID(KontaktKontaktliste k) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE from kontaktkontaktliste" + "WHERE ID= " + k.getKontaktlisteID());

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	/**
	 * Ausgabe eines Vectors mit allen KontaktKontaktlisten-Objekten mit dem
	 * Fremdschlüssel derübergebenen KontaktlisteID
	 * 
	 * @param kontaktlisteID
	 * @return Vector result mit allen KontaktKontaktlisten-Objekten mit der
	 *         übergebenen KontaktlistenID als Fremdschlüssel
	 */
	public Vector<KontaktKontaktliste> getKontaktKontaktlisteByKontaktlisteID(int kontaktlisteID) {
		Vector<KontaktKontaktliste> result = new Vector<KontaktKontaktliste>();

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT * FROM kontaktkontaktliste WHERE kontaktlisteID =" + kontaktlisteID);

			while (rs.next()) {
				KontaktKontaktliste kk = new KontaktKontaktliste();
				kk.setID(rs.getInt("ID"));
				kk.setKontaktID(rs.getInt("kontaktID"));
				kk.setKontaktlisteID(rs.getInt("kontaktlisteID"));

				result.addElement(kk);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
