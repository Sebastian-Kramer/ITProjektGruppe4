package de.hdm.itprojektgruppe4.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
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
					"SELECT `ID`, `kontaktlisteID`, `kontaktID`, `eigenschaftsauspraegungID`, `teilhaberID`, `nutzerID`  FROM teilhaberschaft "
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
					"SELECT `ID`, `kontaktlisteID`, `kontaktID`, `eigenschaftsauspraegungID`, `teilhaberID`, `nutzerID`   FROM teilhaberschaft "
							+ "WHERE kontaktID = " + kontaktID);

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
	
	public Vector<Teilhaberschaft> findTeilhaberschaftByKontaktIDAndNutzerID(int kontaktID, int nutzerID) {

		Vector<Teilhaberschaft> result = new Vector<Teilhaberschaft>();

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT `ID`, `kontaktlisteID`, `kontaktID`, `eigenschaftsauspraegungID`, `teilhaberID`, `nutzerID`   FROM teilhaberschaft "
							+ "WHERE kontaktID = " + kontaktID + " AND nutzerID = " + nutzerID);

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

	

	/**
	 * 
	 * @param kontaktID
	 * @param teilhaberID
	 * @return
	 */
	public Vector<Teilhaberschaft> findTeilhaberschaftByKontaktIDAndTeilhaberID(int kontaktID, int teilhaberID) {

		Vector<Teilhaberschaft> result = new Vector<Teilhaberschaft>();

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT `ID`, `kontaktlisteID`, `kontaktID`, `eigenschaftsauspraegungID`, `teilhaberID`, `nutzerID`   FROM teilhaberschaft "
							+ "WHERE kontaktID = " + kontaktID + " AND teilhaberID = " + teilhaberID);

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

	public Vector<Teilhaberschaft> findTeilhaberschaftByKontaktlisteIDAndTeilhaberID(int kontaktlisteID,
			int teilhaberID) {

		Vector<Teilhaberschaft> result = new Vector<Teilhaberschaft>();

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT `ID`, `kontaktlisteID`, `kontaktID`, `eigenschaftsauspraegungID`, `teilhaberID`, `nutzerID`   FROM teilhaberschaft "
							+ "WHERE kontaktID = " + kontaktlisteID + " AND teilhaberID = " + teilhaberID);

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

	public Vector<Teilhaberschaft> findTeilhaberschaftByTeilhaberIDAndNutzerID(int teilhaberID, int nutzerID) {

		Vector<Teilhaberschaft> result = new Vector<Teilhaberschaft>();

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT `ID`, `kontaktlisteID`, `kontaktID`, `eigenschaftsauspraegungID`, `teilhaberID`, `nutzerID`   FROM teilhaberschaft "
							+ "WHERE teilhaberID = " + teilhaberID + " AND nutzerID = " + nutzerID);

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

	/**
	 * Auslesen eines Teilhaberschaft-Objekts aus der Datenbank anhand der
	 * teilhaberID und der kontaktlisteID
	 * 
	 * @param teilhaberID
	 * @param kontaktlisteID
	 * @return Teilhaberschafts-Objekt
	 */
	public Teilhaberschaft findByTeilhaberIDKontaktlisteID(int teilhaberID, int kontaktlisteID) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(

					"SELECT * FROM `teilhaberschaft` " + "WHERE kontaktlisteID=" + kontaktlisteID + " "
							+ "AND teilhaberID=" + teilhaberID);

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

	/**
	 * 
	 * @param kontaktID
	 * @param kontaktlisteID
	 * @return
	 */
	public Teilhaberschaft findByTeilhaberschaftByKontaktlistIDAndTeilhaberID(int kontaktlisteID, int teilhaberID) {

		Teilhaberschaft th = new Teilhaberschaft();

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(

					"SELECT * FROM `teilhaberschaft` " + "WHERE kontaktlisteID=" + kontaktlisteID + " "
							+ "AND teilhaberID =" + teilhaberID);

			if (rs.next()) {
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

		return th;
	}

	/**
	 * Auslesen eines Teilhaberschaft-Objekts aus der Datenbank anhand der
	 * nutzerID und der kontaktlisteID
	 * 
	 * @param teilhaberID
	 * @param nutzerID
	 * @return Teilhaberschafts-Objekt
	 */
	public Teilhaberschaft findTeilhaberschaftByNutzerIDKontaktlisteID(int nutzerID, int kontaktlisteID) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(

					"SELECT `ID`, `kontaktlisteID`, `kontaktID`, `eigenschaftsauspraegungID`,"
							+ " `teilhaberID`, `nutzerID` FROM" + " `teilhaberschaft` " + "WHERE kontaktlisteID="
							+ kontaktlisteID + " " + "AND nutzerID=" + nutzerID);

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

	/**
	 * 
	 * @param nutzerID
	 * @param kontaktlisteID
	 * @return
	 */
	public Teilhaberschaft findTeilhaberschaftByAuspraegungIdAndTeilhaberId(int auspraegungID, int teilhaberID) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(

					"SELECT `ID`, `kontaktlisteID`, `kontaktID`, `eigenschaftsauspraegungID`,"
							+ " `teilhaberID`, `nutzerID` FROM" + " `teilhaberschaft` "
							+ "WHERE eigenschaftsauspraegungID=" + auspraegungID + " " + "AND teilhaberID="
							+ teilhaberID);

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

	/**
	 * 
	 * @param nutzerID
	 * @param kontaktlisteID
	 * @return
	 */
	public Vector<Teilhaberschaft> findTeilhaberschaftByAuspraegungIDAndTeilhaberID(int auspraegungID,
			int teilhaberID) {

		Vector<Teilhaberschaft> result = new Vector<Teilhaberschaft>();

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT `ID`, `kontaktlisteID`, `kontaktID`, `eigenschaftsauspraegungID`, `teilhaberID`, `nutzerID`   FROM teilhaberschaft "
							+ "WHERE eigenschaftsauspraegungID = " + auspraegungID + " " + "AND teilhaberID = "
							+ teilhaberID);

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

	/**
	 * Auslesen saemtlicher Teilhaberschaft-Objekte mit den uebergebenen
	 * Parametern als Fremdschluessel
	 * 
	 * @param nutzerID
	 *            die ID des Teilhaberschaft-Erstellers
	 * @param kontaktlisteID
	 *            die ID der Kontaktliste an der eine Teilhaberschaft besteht
	 * @return Vector mit s�mtlichen erstellten Teilhaberschaften eines Nutzers
	 *         an einer Kontaktliste
	 */
	public Vector<Teilhaberschaft> findTeilhaberschaftenByKontaktlisteIDNutzerID(int nutzerID, int kontaktlisteID) {

		Vector<Teilhaberschaft> result = new Vector<Teilhaberschaft>();

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT `ID`, `kontaktlisteID`, `kontaktID`, `eigenschaftsauspraegungID`, `teilhaberID`, `nutzerID`   FROM teilhaberschaft "
							+ "WHERE kontaktlisteID=" + kontaktlisteID + " " + "AND nutzerID=" + nutzerID);

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

	public void deleteTeilhaberschaftByKontaktID(int kontaktID) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM teilhaberschaft " + "WHERE kontaktID = " + kontaktID);

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}
	
	public void deleteTeilhaberschaftByAuspraegungID(int auspraegungID) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM teilhaberschaft " + "WHERE eigenschaftsauspraegungID = " + auspraegungID);

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	/**
	 * 
	 * @param kontaktID
	 * @param teilNutzerID
	 */
	public void deleteTeilhaberschaftByKontaktIDAndNutzerID(int kontaktID, int teilNutzerID) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM teilhaberschaft " + "WHERE kontaktID = " + kontaktID + " AND teilhaberID = "
					+ teilNutzerID);

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}
	
	/**
	 * 
	 * @param kontaktID
	 * @param teilNutzerID
	 */
	public void deleteTeilhaberschaftByAuspraegungIDAndTeilhaberID(int auspraegungID, int teilNutzerID) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM teilhaberschaft " + "WHERE eigenschaftsauspraegungID = " + auspraegungID + " AND teilhaberID = "
					+ teilNutzerID);

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	/**
	 * 
	 * @param kontaktID
	 * @param teilNutzerID
	 */
	public void deleteTeilhaberschaftByKontaktIDAndteilNutzerID(int kontaktID, int teilNutzerID) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM teilhaberschaft " + "WHERE kontaktID = " + kontaktID + " AND teilhaberID = "
					+ teilNutzerID);

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	/**
	 * Teilhaberschaft anhand der uebergebenen KontaktlisteID loeschen.
	 * 
	 * @param kontaktlisteID
	 *            die ID der Kontaktliste an der die Teilhaberschaft geloescht
	 *            werden soll
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
	 * Teilhaberschaft anhand der uebergebenen KontaktlisteID und teilhaberID loeschen.
	 * 
	 * @param kontaktlisteID
	 * @param teilhaberID
	 */
	public void deleteTeilhaberschaftByKontaktlisteIDAndTeilhaberID(int kontaktlisteID, int teilhaberID) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM teilhaberschaft " + "WHERE kontaktlisteID = " + kontaktlisteID + " AND teilhaberID = " +  teilhaberID);

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	/**
	 * Teilhaberschaft anhand der übergebenen EigenschaftsausprägungID löschen.
	 * 
	 * @param eigenschaftsauspraegungID
	 * 
	 *            die ID Eigenschaftsausprägung an der die Teilhaberschaft
	 *            geloescht werden soll
	 * 
	 */

	public void deleteTeilhaberschaftByAuspreaegungID(int eigenschaftsauspraegungID) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate(
					"DELETE FROM teilhaberschaft " + "WHERE eigenschaftsauspraegungID = " + eigenschaftsauspraegungID);

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
	 * Auslesen aller Teilhaberschaft-Objekte in einem Vector anhand der
	 * uebergebenen AuspraegungID
	 * 
	 * @param auspraegungID
	 *            die ID der Auspraegung
	 * @return Vector mit Teilhaberschaftobjekten
	 */
	public Vector<Teilhaberschaft> findTeilhaberschaftByAuspraegungID(int auspraegungID) {
		Vector<Teilhaberschaft> result = new Vector<Teilhaberschaft>();

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT * FROM teilhaberschaft WHERE eigenschaftsauspraegungID = " + auspraegungID);

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

	public Vector<Teilhaberschaft> findTeilhaberschaftenByKontaktlisteID(int kontaktlisteID) {
		Vector<Teilhaberschaft> result = new Vector<Teilhaberschaft>();

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM teilhaberschaft WHERE kontaktlisteID = " + kontaktlisteID);

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
								+ " VALUES ('" + t.getID() + "' ,'" + t.getKontaktListeID() + "' ,'" + t.getKontaktID()
								+ "' ,'" + t.getEigenschaftsauspraegungID() + "' ,'" + t.getTeilhaberID() + "' ,'"
								+ t.getNutzerID() + "')");

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return t;
	}

	/**
	 * Einfuegen eines neuen Teilhaberschaft-Objekts in der Datenbank. Wird beim
	 * teilen eines Kontaktes aufgerufen, daher wird die KontaktlisteID null
	 * gesetzt, da diese hier nicht ben�tigt wird.
	 * 
	 * @param t
	 *            die zu speichernde Teilhaberschaft
	 * @return
	 */
	public Teilhaberschaft insertTeilhaberschaftKontakt(Teilhaberschaft t) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(

					"SELECT MAX(ID) AS maxid " + "FROM teilhaberschaft ");

			if (rs.next()) {

				t.setID(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				stmt.executeUpdate(

						" INSERT INTO `teilhaberschaft` (`ID`, `kontaktlisteID`, `kontaktID`, `eigenschaftsauspraegungID`, `teilhaberID`, `nutzerID`) "
								+ " VALUES (" + t.getID() + ",NULL," + t.getKontaktID() + " ," + " NULL " + " ,"
								+ t.getTeilhaberID() + " ," + t.getNutzerID() + ")");
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
	public Teilhaberschaft insertTeilhaberschaftAuspraegung(Teilhaberschaft t) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(

					"SELECT MAX(ID) AS maxid " + "FROM teilhaberschaft ");

			if (rs.next()) {

				t.setID(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				stmt.executeUpdate(

						" INSERT INTO `teilhaberschaft` (`ID`, `kontaktlisteID`, `kontaktID`, `eigenschaftsauspraegungID`, `teilhaberID`, `nutzerID`) "
								+ " VALUES (" + t.getID() + ",NULL," + " NULL" + " , "
								+ t.getEigenschaftsauspraegungID() + " ," + t.getTeilhaberID() + " ," + t.getNutzerID()
								+ ")");
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return t;
	}

	/**
	 * Einfuegen eines neuen Teilhaberschaft-Objektes in die Datenbank. Wird
	 * beim teilen einer Kontaktliste aufgerufen, daher werden die KontaktID,
	 * sowie die EigenschaftauspraegungID null gesetzt. Diese werden beim Teilen
	 * einer Kontaktliste nicht benoetigt.
	 * 
	 * @param t
	 *            das Teilhaberschaft-Objekt
	 * @return
	 */
	public Teilhaberschaft insertTeilhaberschaftKontaktliste(Teilhaberschaft t) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(

					"SELECT MAX(ID) AS maxid " + "FROM teilhaberschaft ");

			if (rs.next()) {

				t.setID(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				stmt.executeUpdate(

						" INSERT INTO `teilhaberschaft` (`ID`, `kontaktlisteID`, `kontaktID`, `eigenschaftsauspraegungID`, `teilhaberID`, `nutzerID`) "
								+ " VALUES ('" + t.getID() + "' ,'" + t.getKontaktListeID() + "' ,NULL,NULL,'"
								+ t.getTeilhaberID() + "' ,'" + t.getNutzerID() + "')");
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
	 * Eine Teilhaberschaft an einer Kontaktliste l�schen.
	 * 
	 * @param t
	 *            das zu l�schende Teilhaberschaft-Objekt
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
	 * Eine Teilhaberschaft an einer Eigenschftausprägung loeschen.
	 * 
	 * @param t
	 *            das zu loeschende Teilhaberschaft-Objekt
	 * @throws IllegalArgumentException
	 */

	public void deleteEigenschaftsauspraegungFromTeilhaberschaft(Eigenschaftauspraegung ea, Nutzer n) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM `teilhaberschaft` WHERE `eigenschaftsauspraegungID`=" + ea.getID() + " "
					+ "AND `teilhaberID` =" + n.getID());

		} catch (SQLException e2) {

			e2.printStackTrace();

		}

	}

	/**
	 * Loeschen einer Teilhaberschaft anhand der teilhaberID
	 * 
	 * @param teilhaberID
	 *            die ID des Teilhabers, dessen Teilhaberschaft aufgel�st werden
	 *            soll
	 */
	public void deleteTeilhaberschaftByTeilhaberID(int teilhaberID) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate

			(

					"DELETE FROM `teilhaberschaft` WHERE `teilhaberID` = " + teilhaberID);

		} catch (SQLException e2) {

			e2.printStackTrace();

		}

	}

	public Vector<Teilhaberschaft> findGeteilteKontakteFromNutzerAndTeilhaber(int teilhaberID, int nutzerID) {
		Vector<Teilhaberschaft> result = new Vector<Teilhaberschaft>();

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT `ID`, `kontaktlisteID`, `kontaktID`, `eigenschaftsauspraegungID`, `teilhaberID`, `nutzerID`   FROM teilhaberschaft "
							+ "WHERE nutzerID = " + nutzerID + " AND teilhaberID = " + teilhaberID
							+ " AND kontaktID IS NOT NULL");

			while (rs.next()) {
				Teilhaberschaft t = new Teilhaberschaft();
				t.setID(rs.getInt("ID"));
				t.setKontaktListeID(rs.getInt("kontaktlisteID"));
				;
				t.setKontaktID(rs.getInt("kontaktID"));
				t.setTeilhaberID(rs.getInt("teilhaberID"));
				t.setNutzerID(rs.getInt("nutzerID"));

				result.addElement(t);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return result;
	}
}
