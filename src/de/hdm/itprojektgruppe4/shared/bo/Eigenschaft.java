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
    public Kontakt Kontakt;


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

}