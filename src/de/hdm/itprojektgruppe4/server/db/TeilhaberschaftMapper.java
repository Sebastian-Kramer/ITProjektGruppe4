package de.hdm.itprojektgruppe4.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektgruppe4.shared.bo.KontaktKontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Teilhaberschaft;

/**
 * Mapper Klassen, die <code>Teilhaberschaft</code>-Objekte auf einer
 * relationalen Datenbank abbildet.
 *
 */

public class TeilhaberschaftMapper {

	private static TeilhaberschaftMapper teilhaberschaftMapper = null;

	protected TeilhaberschaftMapper() {

	};

	public static TeilhaberschaftMapper teilhaberschaftMapper() {

		if (teilhaberschaftMapper == null) {

			teilhaberschaftMapper = new TeilhaberschaftMapper();
		}

		return teilhaberschaftMapper;
	}

	/**
	 * Suchen einer Teilhaberschaft mit vorgegebener ID.
	 * 
	 * @param id
	 *            der Teilhaberschaft
	 * @return die gesuchte Teilhaberschaft
	 */

	public Teilhaberschaft findByID(int id) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(

					"SELECT `ID`, `kontaktlisteID`, `kontaktID`, `eigenschaftsauspraegungID`,"
							+ " `teilhaberID`, 'nutzerID' FROM" + " `teilhaberschaft` WHERE ID=" + id);

			if (rs.next()) {

				Teilhaberschaft th = new Teilhaberschaft();

				th.setID(rs.getInt("ID"));
				th.setKontaktListeID(rs.getInt("kontaktlisteID"));
				th.setKontaktID(rs.getInt("kontaktID"));
				th.setEigenschaftsauspraegungID(rs.getInt("eigenschaftsauspraegungID"));
				th.setTeilhaberID(rs.getInt("teilhaberID"));
				th.setNutzerID(rs.getInt("nutzerID"));

				return th;
			}
		} catch (SQLException e) {

			e.printStackTrace();

			return null;
		}

		return null;
	}

	public Vector<Teilhaberschaft> findTeilhaberschaftByKontaktlisteID(int kontaktlisteID) {

		Vector<Teilhaberschaft> result = new Vector<Teilhaberschaft>();

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT `ID`, `kontaktlisteID`, `kontaktID`, `eigenschaftsauspraegungID`, `teilhaberID`, 'nutzerID'  FROM teilhaberschaft "
							+ "WHERE kontaktlisteID = " + kontaktlisteID);

			while (rs.next()) {
				Teilhaberschaft th = new Teilhaberschaft();
				th.setID(rs.getInt("ID"));
				th.setKontaktListeID(rs.getInt("kontaktlisteID"));
				th.setKontaktID(rs.getInt("kontaktID"));
				th.setEigenschaftsauspraegungID(rs.getInt("eigenschaftsauspraegungID"));
				th.setTeilhaberID(rs.getInt("teilhaberID"));
				th.setNutzerID(rs.getInt("nutzerID"));

				result.addElement(th);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public Vector<Teilhaberschaft> findTeilhaberschaftByKontaktID(int kontaktID) {

		Vector<Teilhaberschaft> result = new Vector<Teilhaberschaft>();

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT `ID`, `kontaktlisteID`, `kontaktID`, `eigenschaftsauspraegungID`, `teilhaberID`  FROM teilhaberschaft "
							+ "WHERE kontaktID = " + kontaktID);

			while (rs.next()) {
				Teilhaberschaft th = new Teilhaberschaft();
				th.setID(rs.getInt("ID"));
				th.setKontaktListeID(rs.getInt("kontaktlisteID"));
				th.setKontaktID(rs.getInt("kontaktID"));
				th.setEigenschaftsauspraegungID(rs.getInt("eigenschaftsauspraegungID"));
				th.setTeilhaberID(rs.getInt("teilhaberID"));

				result.addElement(th);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public void deleteTeilhaberschaftByKontaktID(int kontaktID) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM teilhaberschaft " + "WHERE kontaktID = " + kontaktID);

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}
	
	/**
	 * Teilhaberschaft anhand der uebergebenen KontaktlisteID löschen.
	 * @param kontaktlisteID die ID der Kontaktliste an der die Teilhaberschaft gelöscht werden soll
	 */
	public void deleteTeilhaberschaftByKontaktlisteID(int kontaktlisteID) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM teilhaberschaft " + "WHERE kontaktlisteID = " + kontaktlisteID);

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	/**
	 * Ausgabe von Teilhaberschaft-Objekten anhand der TeilhaberID
	 * 
	 * @param teilhaberID
	 * @return Vector mit saemtlichen Teilhaberschaften
	 */
	public Vector<Teilhaberschaft> findTeilhaberschaftByTeilhaberID(int teilhaberID) {
		Vector<Teilhaberschaft> result = new Vector<Teilhaberschaft>();

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM teilhaberschaft WHERE teilhaberID = " + teilhaberID);

			while (rs.next()) {
				Teilhaberschaft teilhaberschaft = new Teilhaberschaft();
				teilhaberschaft.setID(rs.getInt("ID"));
				teilhaberschaft.setKontaktListeID(rs.getInt("kontaktlisteID"));
				teilhaberschaft.setKontaktID(rs.getInt("kontaktID"));
				teilhaberschaft.setEigenschaftsauspraegungID(rs.getInt("eigenschaftsauspraegungID"));
				teilhaberschaft.setTeilhaberID(rs.getInt("teilhaberID"));
				teilhaberschaft.setNutzerID(rs.getInt("nutzerID"));

				result.addElement(teilhaberschaft);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}

	/**
	 * Einfuegen eines neuen Objektes vom Typ Teilhaberschaft in die DB der PK
	 * wird ueberprueft und korrigiert -> maxID +1
	 * 
	 * @param t
	 *            die zu speichernde Teilhaberschaft
	 * @return die bereits uebergebene Teilhaberschaft
	 */

	public Teilhaberschaft insertTeilhaberschaft(Teilhaberschaft t) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(

					"SELECT MAX(id) AS maxid " + "FROM teilhaberschaft ");

			if (rs.next()) {

				t.setID(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				stmt.executeUpdate(
						" INSERT INTO `teilhaberschaft` (`ID`, `kontaktlisteID`, `kontaktID`, `eigenschaftsauspraegungID`, `teilhaberID`, `nutzerID`) "
								+ " VALUES ('" + t.getID() + "' ,'" + t.getKontaktListeID() + "' ,'"
								+ t.getKontaktID() + "' ,'" + t.getEigenschaftsauspraegungID() +  "' ,'"  
								+ t.getTeilhaberID() + "' ,'"  +  t.getNutzerID() + "')");


			
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return t;
	}

	/**
	 * 
	 * @param t
	 * @return
	 */
	public Teilhaberschaft insertTeilhaberschaftKontakt(Teilhaberschaft t) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(

					"SELECT MAX(id) AS maxid " + "FROM teilhaberschaft ");

			if (rs.next()) {

				t.setID(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				stmt.executeUpdate(

						" INSERT INTO `teilhaberschaft` (`ID`, `kontaktlisteID`, `kontaktID`, `eigenschaftsauspraegungID`, `teilhaberID`, `nutzerID`) "
								+ " VALUES ('" + t.getID() + "' ,'" + t.getKontaktListeID() + "' ,'"
								+ t.getKontaktID() + "' ,'" + t.getEigenschaftsauspraegungID() +  "' ,'"  
								+ t.getTeilhaberID() + "' ,'"  +  t.getNutzerID() + "')");
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return t;
	}

	/**
	 * ein Objekt vom Typ Teilhaberschaft wird aus der DB geloescht
	 * 
	 * @param t
	 * @return die zu loeschende Person
	 */

	public void deleteTeilhaberschaft(Teilhaberschaft t) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate(

					"DELETE FROM teilhaberschaft " + "WHERE id=" + t.getID());

		} catch (SQLException e2) {

			e2.printStackTrace();

		}

	}

	/**
	 * Eine Teilhaberschaft an einem Kontakt loeschen.
	 * 
	 * @param t
	 *            das zu loeschende Teilhaber-Objekt
	 */

	public void deleteKontaktFromTeilhaberschaft(Teilhaberschaft t) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate(

					"DELETE FROM `teilhaberschaft` WHERE `kontaktID`=" + t.getKontaktID() + " " + "AND `teilhaberID` ="
							+ t.getTeilhaberID());

		} catch (SQLException e2) {

			e2.printStackTrace();

		}

	}

	/**
	 * Eine Teilhaberschaft an einer Kontaktliste lï¿½schen.
	 * 
	 * @param t
	 *            das zu lï¿½schende Teilhaberschaft-Objekt
	 * @throws IllegalArgumentException
	 */

	public void deleteKontaktlisteFromTeilhaberschaft(Teilhaberschaft t) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate

			(

					"DELETE FROM `teilhaberschaft` WHERE `kontaktlisteID`=" + t.getKontaktListeID() + " "
							+ "AND `teilhaberID` =" + t.getTeilhaberID());

		} catch (SQLException e2) {

			e2.printStackTrace();

		}

	}

	/**
	 * Eine Teilhaberschaft an einer EigenschftausprÃ¤gung loeschen.
	 * 
	 * @param t
	 *            das zu loeschende Teilhaberschaft-Objekt
	 * @throws IllegalArgumentException
	 */

	public void deleteEigenschaftsauspraegungFromTeilhaberschaft(Teilhaberschaft t) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate

			(

					"DELETE FROM `teilhaberschaft` WHERE `eigenschaftsauspraegungID`="
							+ t.getEigenschaftsauspraegungID() + " " + "AND `teilhaberID` =" + t.getTeilhaberID());

		} catch (SQLException e2) {

			e2.printStackTrace();

		}

	}

}
