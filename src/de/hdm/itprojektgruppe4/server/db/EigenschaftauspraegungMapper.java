package de.hdm.itprojektgruppe4.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektgruppe4.client.EigenschaftAuspraegungWrapper;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;

/**
 * Mapper Klassen, die <code>Eigenschaftauspraegung</code>-Objekte auf einer
 * relationalen Datenbank abbildet.
 *
 */

public class EigenschaftauspraegungMapper {

	private static EigenschaftauspraegungMapper eigenschaftauspraegungMapper = null;

	protected EigenschaftauspraegungMapper() {
	};

	public static EigenschaftauspraegungMapper eigenschaftauspraegungMapper() {
		if (eigenschaftauspraegungMapper == null) {
			eigenschaftauspraegungMapper = new EigenschaftauspraegungMapper();
		}
		return eigenschaftauspraegungMapper;
	}

	/**
	 * Suchen einer Eigenschaftauspraegung mit vorgegebener ID.
	 * 
	 * @param id der Eigenschaftauspraegung
	 * @return die gesuchte Eigenschaftauspraegung
	 */

	public Eigenschaftauspraegung getAuspraegungByID(int id) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM eigenschaftsauspraegung " + " WHERE ID= " + id);

			if (rs.next()) {
				Eigenschaftauspraegung e = new Eigenschaftauspraegung();
				e.setID(rs.getInt("ID"));
				e.setWert(rs.getString("wert"));
				e.setStatus(rs.getInt("status"));
				e.setEigenschaftsID(rs.getInt("eigenschaftID"));
				e.setKontaktID(rs.getInt("kontaktID"));

				return e;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;

	}
	
	/**
	 * Eine Eigenschaftsausprägung anhand der Fremdschlüssel eigenschaftID und kontaktID auslesen
	 * 
	 * @param eigID Fremdschlüssel zur Tabelle Eigenschaft
	 * @param kontaktID Fremdschlüssel zur Tabelle Kontakt
	 * @return Eigenschaftauspraegung Objekt
	 */
	public Eigenschaftauspraegung getAuspraegungByEigID(int eigID, int kontaktID) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM eigenschaftsauspraegung WHERE eigenschaftID = " + eigID + " AND kontaktID = " + kontaktID);

			if (rs.next()) {
				Eigenschaftauspraegung e = new Eigenschaftauspraegung();
				e.setID(rs.getInt("ID"));
				e.setWert(rs.getString("wert"));
				e.setStatus(rs.getInt("status"));
				e.setEigenschaftsID(rs.getInt("eigenschaftID"));
				e.setKontaktID(rs.getInt("kontaktID"));

				return e;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;

	}

	public Vector<Eigenschaftauspraegung> getAuspraegungByKontaktID(int id) {
		Vector<Eigenschaftauspraegung> result = new Vector<Eigenschaftauspraegung>();

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT ID, wert, status, eigenschaftID, kontaktID FROM eigenschaftsauspraegung WHERE kontaktID= "
							+ id);

			while (rs.next()) {
				Eigenschaftauspraegung e = new Eigenschaftauspraegung();
				e.setID(rs.getInt("ID"));
				e.setWert(rs.getString("wert"));
				e.setStatus(rs.getInt("status"));
				e.setEigenschaftsID(rs.getInt("eigenschaftID"));
				e.setKontaktID(rs.getInt("kontaktID"));

				result.addElement(e);

			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return result;

	}

	/**
	 * Einfuegen eines neuen Objktes vom Typ Eigenschaftauspraegung in die DB der
	 * PK wird ueberprueft und korrigiert -> maxID +1
	 * 
	 * @param ea die zu speichernde Eigenschaftauspraegung
	 * @return die bereits  übergebene Eigenschaftauspraegung
	 */

	public Eigenschaftauspraegung insertAuspraegung(Eigenschaftauspraegung ea) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT MAX(ID) AS maxID " + " FROM eigenschaftsauspraegung ");

			if (rs.next()) {

				ea.setID(rs.getInt("maxID") + 1);

				stmt = con.createStatement();

				stmt.executeUpdate(

						"INSERT INTO `eigenschaftsauspraegung`(`ID`, `wert`, `status`,"
								+ " `eigenschaftID`, `kontaktID`) " + "VALUES ('" + ea.getID() + "', '" + ea.getWert()
								+ "', '" + "" + ea.getStatus() + "', '" + ea.getEigenschaftsID() + "', '" + ""
								+ ea.getKontaktID() + "')");

			}
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return ea;
	}

	/**
	 * Ueberschreiben eines <code>Eigenschaftauspraegung</code>-Objekts.
	 * 
	 * @param ea
	 * @return ea
	 */

	public Eigenschaftauspraegung updateAuspraegung(Eigenschaftauspraegung ea) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate(

					"UPDATE eigenschaftsauspraegung " + "SET ID=\"" + ea.getID() + "\", " + "wert=\"" + ea.getWert()
							+ "\", " + "status=\"" + ea.getStatus() + "\", " + "eigenschaftID=\""
							+ ea.getEigenschaftsID() + "\", " + "kontaktID=\"" + ea.getKontaktID() + "\" " + "WHERE ID="
							+ ea.getID());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ea;
	}

	/**
	 * ein Objekt vom Typ Eigenschaftauspraegung wird aus der DB geloescht
	 * 
	 * @param ea
	 *            die zu loeschende Eigenschaftauspraegung
	 * 
	 */
	public void deleteAuspraegung(Eigenschaftauspraegung ea) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM eigenschaftsauspraegung " + "WHERE ID=" + ea.getID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Eine Eigenschaftsauspraegung anhand des Wertes auslesen.
	 * 
	 * @param wert,
	 *            der die Auspraegung beschreibt
	 * @return Eigenschafts-Objekt mit gesuchtem Wert, null bei nicht
	 *         vorhandenem DB-Tulpel
	 */


/**
 *  ein Objekt vom Typ Eigenschaftauspraegung wird aus der DB gel�scht 
 * @param ea
 * 	die zu l�schende Eigenschaftauspraegung
 * 
 */


/**
 * Eine Eigenschaftsauspraegung anhand des Wertes auslesen.
 * 
 * @param wert, der die Auspraegung beschreibt
 * @return Eigenschafts-Objekt mit gesuchtem Wert, null bei nicht vorhandenem DB-Tulpel
 */

//pr�fen
public Vector<Eigenschaftauspraegung> getAuspraegungByWert(String wert){
	Vector<Eigenschaftauspraegung> result = new Vector<Eigenschaftauspraegung>();
	Connection con = DBConnection.connection();

	try {
		
	
	Statement stmt = con.createStatement();
	
	ResultSet rs = stmt.executeQuery(
			
	"SELECT * FROM `eigenschaftsauspraegung` WHERE `wert` LIKE " + "'" + wert + "'" +" ORDER by ID");
	
	
	
			
	while (rs.next()) {
		Eigenschaftauspraegung ea = new Eigenschaftauspraegung();
		ea.setID(rs.getInt("ID"));
		ea.setEigenschaftsID(rs.getInt("eigenschaftID"));
		ea.setKontaktID(rs.getInt("kontaktID"));
		ea.setWert(rs.getString("wert"));
		ea.setStatus(rs.getInt("status"));
	
		
		result.addElement(ea);
	}
		
}catch (SQLException e) {
	e.printStackTrace();
	
}
return result;

}



	public Vector<Eigenschaftauspraegung> findAuspraegungByKontaktID(int kontaktID) {

		Vector<Eigenschaftauspraegung> result = new Vector<Eigenschaftauspraegung>();

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT ID, wert, status, eigenschaftID, kontaktID from eigenschaftsauspraegung "
							+ "WHERE kontaktID= " + kontaktID + " ORDER by ID");

			while (rs.next()) {
				Eigenschaftauspraegung ea = new Eigenschaftauspraegung();
				ea.setID(rs.getInt("ID"));
				ea.setWert(rs.getString("wert"));
				ea.setStatus(rs.getInt("status"));
				ea.setEigenschaftsID(rs.getInt("eigenschaftID"));
				ea.setKontaktID(rs.getInt("kontaktID"));

				result.addElement(ea);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * @return
	 */
	public Vector<Eigenschaftauspraegung> findAllAuspraegungn() {

		Vector<Eigenschaftauspraegung> result = new Vector<Eigenschaftauspraegung>();

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT ID, wert, status, eigenschaftID, kontaktID FROM eigenschaftsauspraegung" + " ORDER BY ID");

			while (rs.next()) {
				Eigenschaftauspraegung e = new Eigenschaftauspraegung();
				e.setID(rs.getInt("ID"));
				e.setWert(rs.getString("wert"));
				e.setStatus(rs.getInt("status"));
				e.setKontaktID(rs.getInt("kontaktID"));

				result.addElement(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}
	
	/**
	* Auslesen aller geteilten Eigenschaftsausprägungen eines bestimmten Kontakts
	 * 
	 * @param kontaktID die ID des Kontaktes
	 * @return Vector mit sämtlichen geteilten Eigenschaftsausprägungen des Kontaktes
	 */
	public Vector<Eigenschaftauspraegung> findAllSharedAuspraegungenFromKontaktID(int kontaktID) {

		Vector<Eigenschaftauspraegung> result = new Vector<Eigenschaftauspraegung>();

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT ID, wert, status, eigenschaftID, kontaktID FROM eigenschaftsauspraegung "
							+ " WHERE status = 1 "  + " AND kontaktID = " + kontaktID);

			while (rs.next()) {
				Eigenschaftauspraegung e = new Eigenschaftauspraegung();
				e.setID(rs.getInt("ID"));
				e.setWert(rs.getString("wert"));
				e.setStatus(rs.getInt("status"));
				e.setEigenschaftsID(rs.getInt("eigenschaftID"));
				e.setKontaktID(rs.getInt("kontaktID"));

				result.addElement(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}

	/**
	 * 
	 * @param wert
	 * @return
	 */
	public Vector<Eigenschaftauspraegung> findAllAuspraegungnByWert(String wert) {

		Vector<Eigenschaftauspraegung> result = new Vector<Eigenschaftauspraegung>();

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT ID, wert, status, eigenschaftID, kontaktID FROM eigenschaftsauspraegung "
							+ " WHERE wert= " + "'" + wert + "'");

			while (rs.next()) {
				Eigenschaftauspraegung e = new Eigenschaftauspraegung();
				e.setID(rs.getInt("ID"));
				e.setWert(rs.getString("wert"));
				e.setStatus(rs.getInt("status"));
				e.setKontaktID(rs.getInt("kontaktID"));

				result.addElement(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}

}
