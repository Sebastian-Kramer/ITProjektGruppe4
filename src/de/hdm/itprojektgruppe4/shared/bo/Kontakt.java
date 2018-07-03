package de.hdm.itprojektgruppe4.shared.bo;

/**
 * Realisieren einer exemplarischen Eigenschaft
 */
import java.util.Date;

public class Kontakt extends Person {

	private static final long serialVersionUID = 1L;

	/**
	 * Name des Kontakts
	 */

	private String name;

	/**
	 * Datum der Erzeugung des Kontaktes
	 */

	private Date erzeugungsdatum;

	/**
	 * Datum der Bearbeitung des Kontaktes
	 */

	private Date modifikationsdatum;

	/**
	 * Status des Kontakts, geteilt/nicht geteilt
	 */

	private int status;

	/**
	 * Eindeutige ID der verknüpften Kontaktliste
	 */

	private int kontaktlisteID;

	/**
	 * Eindeutige ID des Nutzers
	 */

	private int nutzerID;

	/**
	 * Auslesen des Namens
	 * 
	 * @return name
	 */

	public String getName() {
		return name;
	}

	/**
	 * Setzen des Namens
	 * 
	 * @param name
	 */

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Auslesen des Erzeugungsdatums
	 * 
	 * @return erzeugungsdatum
	 */

	public Date getErzeugungsdatum() {
		return erzeugungsdatum;
	}

	/**
	 * Setzen des Erzeugungsdatum
	 * 
	 * @param erzeugungsdatum
	 */

	public void setErzeugungsdatum(Date erzeugungsdatum) {
		this.erzeugungsdatum = erzeugungsdatum;
	}

	/**
	 * Auslesen des Modifikationsdatums
	 * 
	 * @return modifikationsdatum
	 */

	public Date getModifikationsdatum() {
		return modifikationsdatum;
	}

	/**
	 * Setzen des Modifikationsdatum
	 * 
	 * @param modifikationsdatum
	 */

	public void setModifikationsdatum(Date modifikationsdatum) {
		this.modifikationsdatum = modifikationsdatum;
	}

	/**
	 * Auslesen des Statuses
	 * 
	 * @return status
	 */

	public int getStatus() {
		return status;
	}

	/**
	 * Setzen des Statuses
	 * 
	 * @param status
	 */

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Auslesen der Kontaktlisten Id
	 * 
	 * @return kontaktlisteID
	 */

	public int getKontaktlisteID() {
		return kontaktlisteID;
	}

	/**
	 * Setzen der Kontakt Id
	 * 
	 * @param kontaktlisteID
	 */

	public void setKontaktlisteID(int kontaktlisteID) {
		this.kontaktlisteID = kontaktlisteID;
	}

	/**
	 * Auslesen der Nutzer Id
	 * 
	 * @return
	 */

	public int getNutzerID() {
		return nutzerID;
	}

	/**
	 * Setzen der Nutzer Id
	 * 
	 * @param nutzerID
	 */

	public void setNutzerID(int nutzerID) {
		this.nutzerID = nutzerID;
	}

	/**
	 * Es wird eine einfache textuelle Darstellung des Kontaktes erzeugt.
	 */

	@Override
	public String toString() {
		return "Kontakt [name=" + name + ", erzeugungsdatum=" + erzeugungsdatum + ", modifikationsdatum="
				+ modifikationsdatum + ", status=" + status + ", kontaktlisteID=" + kontaktlisteID + ", nutzerID="
				+ nutzerID + "]";

	}

}
