package de.hdm.itprojektgruppe4.shared.bo;


import java.util.*;

/**
 * 
 */
public class Kontaktliste extends BusinessObject {
	
	 /**
     * 
     */
	private static final long serialVersionUID = 1L;

    private String bez;
    
    private int status;

	public String getBez() {
		return bez;
	}

	public void setBez(String bez) {
		this.bez = bez;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Kontaktliste [bez=" + bez + ", status=" + status + "]";
	}
    
    
}