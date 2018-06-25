package de.hdm.itprojektgruppe4.shared.bo;

/**
 * Realisierung einer exemplarischen Eigenschaftsauspraegung
 */

public class Eigenschaftauspraegung extends BusinessObject{
	
	
	private static final long serialVersionUID = 1L;
	
	/**
	 *  Wert der Eigenschaftsauspraegung
	 */

	private String wert;
	
	/**
	 * Status der Eigenschaftsauspraegung, geteilt oder nicht geteilt
	 */
	
	private int status; 
	
	/**
	 * Eindeutige Id der Eigenschaft
	 */
	
	private int eigenschaftsID; 
	
	/**
	 * EIndeutige Id des Kontaktes
	 */
	
	private int kontaktID;
	
	/**
	 * Auslesen des Wertes
	 * @return wert
	 */

	public String getWert() {
		return wert;
	}
	
	/**
	 * Setzen des Wertes
	 * @param wert
	 */

	public void setWert(String wert) {
		this.wert = wert;
	}
	
	/**
	 * Auslesen des Statuses
	 * @return
	 */

	public int getStatus() {
		return status;
	}
	
	/**
	 * Setzen des Statuses
	 * @param status
	 */

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Auslesen der Eigenschafts Id
	 * @return eigenschaftsID
	 */
	public int getEigenschaftsID() {
		return eigenschaftsID;
	}
	
	/**
	 * Setzen der Eigenschafts Id
	 * @param eigenschaftsID
	 */

	public void setEigenschaftsID(int eigenschaftsID) {
		this.eigenschaftsID = eigenschaftsID;
	}

	/**
	 * Auslesen der Kontakt Id
	 * @return kontaktID
	 */
	public int getKontaktID() {
		return kontaktID;
	}
	
	/**
	 * Setzen der Kontakt Id
	 * @param kontaktID
	 */

	public void setKontaktID(int kontaktID) {
		this.kontaktID = kontaktID;
	}
	
	 /**
	   * Es wird eine einfache textuelle Darstellung der Eigenschaftsausprägung erzeugt.
	   */
	
	

	@Override
	public String toString() {
		return "Eigenschaftauspraegung [wert=" + wert + ", status=" + status + ", eigenschaftsID=" + eigenschaftsID
				+ ", kontaktID=" + kontaktID + "]";
	} 
	
	
	


	
	
}
