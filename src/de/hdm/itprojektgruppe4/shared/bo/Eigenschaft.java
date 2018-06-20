package de.hdm.itprojektgruppe4.shared.bo;

/**
 * Realisierung einer exemplarischen Eigenschaft
 */

import java.util.*;


public class Eigenschaft extends BusinessObject {

	 
	private static final long serialVersionUID = 1L;

    /**
     * Bezeichnung der Eigenscahft
     */
    private String bez;

    /**
     *  Status der Eigenschaft, geteilt/nicht geteilt
     */
    private int status;

    /**Auslesen der Bezeichnung
     * @return bez
     */
    public String getBezeichnung() {
        // TODO implement here
        return this.bez;
    }

    /**
     * Setzen der Bezeichnung
     * @param bez  
     * @return bez
     */
    public void setBezeichnung(String bez) {
        // TODO implement here
        this.bez = bez;
    }
    
 

	/**
	 * Setzen des Statuses 
	 * @return status
	 */
	

	public void setStatus(int status) {
		this.status = status;
	}
	
	/**
	 * Setzen des Statuses 
	 * @return status
	 */
	
	public int getStatus(){
		return this.status;
	}

	  /**
	   * Erzeugen einer einfachen textuellen Repräsentation der jeweiligen
	   * Kontoinstanz.
	   */
	
	@Override
	public String toString() {
		return "Eigenschaft [bezeichnung=" + bez + ", status=" + status + "]";
	}
    
    

}