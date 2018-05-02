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

    /**
     * 
     */
    private int KontaktID;

    /**
     * 
     */
    private int NutzerID;

    /**
     * 
     */
    private String bezeichnung;

    /**
     * 
     */
    private boolean status;

    /**
     * 
     */
    public Set<Kontakt> Kontakt;


    /**
     * 
     */
    public Kontakt Nutzer;


    /**
     * @return
     */
    public int getKontaktID() {
        return this.KontaktID;
    }

    /**
     * @param id 
     * @return
     */
    public void setKontaktID(int id) {
        this.KontaktID = id; ;
    }

    /**
     * @return
     */
    public int getNutzerID() {
        return this.NutzerID;
    }

    /**
     * @param id 
     * @return
     */
    public void setNutzerID(int id) {
        this.NutzerID = id;;
    }
    
    /**
     * @return
     */
    public String getBezeichnung() {
        return this.bezeichnung;
    }

    /**
     * @param bez 
     * @return
     */
    public void setBezeichnung(String bez) {
        this.bezeichnung = bez;
    }

    /**
     * @return
     */
    public boolean getStatus() {
        return this.status;
    }

    /**
     * @param status 
     * @return
     */
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public String toString(){
    	return this.bezeichnung;
    }

}