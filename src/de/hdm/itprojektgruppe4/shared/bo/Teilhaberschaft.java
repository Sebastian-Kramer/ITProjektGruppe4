package de.hdm.itprojektgruppe4.shared.bo;

public class Teilhaberschaft extends BusinessObject {

	/**
	 * Realisierung einer exemplarischen Teilhaberscahft
	 *
	 */

	private static final long serialVersionUID = 1L;

	/**
	 * eindeutige Id des Kontakts
	 * 
	 */

	private int kontaktID;

	/**
	 * eindeutige Id der Kontaktliste
	 */

	private int kontaktListeID;

	/**
	 * eindeutige Eigenschaftsauspraegung Id
	 */

	private int eigenschaftsauspraegungID;

	/**
	 * eindeutige Id des Teilhabers
	 */

	private int teilhaberID;

	/**
	 * eindeutige Id des Nutzers
	 */

	private int nutzerID;

	/**
	 * auslesen der KontaktId
	 * 
	 * @return kontaktID
	 */

	public int getKontaktID() {
		return kontaktID;
	}

	/**
	 * Setzen der Kontakt Id
	 * 
	 * @param kontaktID
	 */
	public void setKontaktID(int kontaktID) {
		this.kontaktID = kontaktID;
	}

	/**
	 * Auslesen der Kontaktlisten Id
	 * 
	 * @return kontaktlisteID
	 */
	public int getKontaktListeID() {
		return kontaktListeID;
	}

	/**
	 * Setzen der Kontaklisten Id
	 * 
	 * @param kontaktListeID
	 */
	public void setKontaktListeID(int kontaktListeID) {
		this.kontaktListeID = kontaktListeID;
	}

	/**
	 * Auslesen der Eigenschaftsausprägungs Id
	 * 
	 * @return eigenscahftsauspraegungID
	 */

	public int getEigenschaftsauspraegungID() {

		return eigenschaftsauspraegungID;
	}

	/**
	 * Setzen der Eigenschaftsauspraegungs Id
	 * 
	 * @param eigenschaftsauspraegungID
	 */

	public void setEigenschaftsauspraegungID(int eigenschaftsauspraegungID) {
		this.eigenschaftsauspraegungID = eigenschaftsauspraegungID;
	}

	/**
	 * Auslesen der Teilhaber Id
	 * 
	 * @return teilhaber Id
	 */
	public int getTeilhaberID() {
		return teilhaberID;
	}

	/**
	 * Setzen der Teilhaber Id
	 * 
	 * @param teilhaberID
	 */

	public void setTeilhaberID(int teilhaberID) {
		this.teilhaberID = teilhaberID;
	}

	/**
	 * Auslesen der Nutzer Id
	 * 
	 * @return nutzerID
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
	 * Es wird eine einfache textuelle Darstellung der Teilhaberschaft erzeugt.
	 * 
	 */

	@Override
	public String toString() {
		return "Teilhaberschaft [kontaktID=" + kontaktID + ", kontaktListeID=" + kontaktListeID
				+ ", eigenschaftsauspraegungID=" + eigenschaftsauspraegungID + ", teilhaberID=" + teilhaberID
				+ ", nutzerID=" + nutzerID + "]";
	}

}
