package de.hdm.itprojektgruppe4.shared.bo;

/**
 * Die Klasse KontaktKontaktliste stellt die Verbindungstabelle zwischen Kontakt und Kontaktliste dar, da hier eine
 * n:n-Beziehung besteht.
 * Neben der eigenen ID, sind die Fremdschlüssel der Klasse Kontakt und Kontaktliste enthalten
 * @author Raphael
 *
 */

public class KontaktKontaktliste extends BusinessObject {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Fremdschlüsselbeziehung zum Kontakt
	 */
	
	private int kontaktID;
	
	/**
	 * Fremdschlüsselbeziehung zur Kontaktliste
	 */
	
	private int kontaktlisteID;

	/**Auslesen des Fremdschlüssels des Kontaktes
	 * @return kontaktID
	 */
	public int getKontaktID() {
		return kontaktID;
	}

	/** Setzen der Fremdschlüsselbeziehung zum Kontakt
	 * @param kontaktID
	 */
	public void setKontaktID(int kontaktID) {
		this.kontaktID = kontaktID;
	}

	/** Auslesen des Fremdschlüssel zur Kontaktliste
	 * @return kontaktlisteID
	 */
	public int getKontaktlisteID() {
		return kontaktlisteID;
	}

	/**Setzen der Fremdschlsselbeziehung zur Kontaktliste
	 * @param kontaktlisteID
	 */
	public void setKontaktlisteID(int kontaktlisteID) {
		this.kontaktlisteID = kontaktlisteID;
	}
	
	
	

}
