package de.hdm.itprojektgruppe4.shared.bo;


import java.util.*;

/**
 * 
 */
public class Eigenschaft extends BusinessObject {

	 /**
     * 
     */
	private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private String bezeichnung;

    /**
     * 
     */
    private boolean status;

    /**
     * @return
     */
    public String getBezeichnung() {
        // TODO implement here
        return this.bezeichnung;
    }

    /**
     * @param bez 
     * @return
     */
    public void setBezeichnung(String bez) {
        // TODO implement here
        this.bezeichnung = bez;
    }

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public boolean getStatus(){
		return this.status;
	}
    
    

}