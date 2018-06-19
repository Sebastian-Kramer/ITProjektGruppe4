package de.hdm.itprojektgruppe4.shared.bo;

/**
 * Realisierung einer exemplarischen Kontaktliste
 */
import java.util.*;


public class Kontaktliste extends BusinessObject {
	
	  /**
	   * Erzeugen einer einfachen textuellen Repräsentation der jeweiligen
	   * Kontoinstanz.
	   */
	
	 @Override
	public String toString() {
		return "Kontaktliste [bez=" + bez + ", status=" + status + ", nutzerID=" + nutzerID + "]";
	}

	
	private static final long serialVersionUID = 1L;

	/**
	 * Bezeichnung der Kontaktliste 
	 */
    private String bez;
    
    /**
     * Status der Kontaktliste
     */
    
    private int status;
    
    /**
     * Eindeutige Nutzer Id
     */
    
    private int nutzerID;
    
    /**
     * Auslesen der Nutzer Id
     * @return nutzer Id
     */

	public int getNutzerID() {
		return nutzerID;
	}

	/**
	 * Setzen der Nutzer Id
	 * @param nutzerID
	 */
	public void setNutzerID(int nutzerID) {
		this.nutzerID = nutzerID;
	}

	
	/**
	 * Auslesen der Bezeichnung
	 * @return bez
	 */
	public String getBez() {
		return bez;
	}

	/**
	 * Setzen der Bezeichnung
	 * @param bez
	 */
	public void setBez(String bez) {
		this.bez = bez;
	}

	/**
	 * Auslesen des Statuses 
	 * @return status
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

	
    
    
}