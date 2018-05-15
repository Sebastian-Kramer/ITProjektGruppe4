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
    private String bez;

    /**
     * 
     */
    private int status;

    /**
     * @return
     */
    public String getBezeichnung() {
        // TODO implement here
        return this.bez;
    }

    /**
     * @param bez 
     * @return
     */
    public void setBezeichnung(String bez) {
        // TODO implement here
        this.bez = bez;
    }

	public int isStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getStatus(){
		return this.status;
	}

	@Override
	public String toString() {
		return "Eigenschaft [bezeichnung=" + bez + ", status=" + status + "]";
	}
    
    

}