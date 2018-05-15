package de.hdm.itprojektgruppe4.server;

import java.util.Date;
import java.util.Vector;
import java.text.SimpleDateFormat;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektgruppe4.server.db.*;
import de.hdm.itprojektgruppe4.server.db.KontaktlisteMapper;
import de.hdm.itprojektgruppe4.shared.KontaktAdministration;
import de.hdm.itprojektgruppe4.shared.bo.*;

public class KontaktAdministrationImpl extends RemoteServiceServlet 
	implements KontaktAdministration{

	/**
     * Default constructor
     */
    public KontaktAdministrationImpl() {
    }

    private NutzerMapper nutzerMapper = null;
    
    /**
     * 
     */
    private KontaktMapper konMapper = null;

    /**
     * 
     */
    //private KontaktlisteMapper kontaktlisteMapper;
    private KontaktlisteMapper konlistMapper = null;
    /**
     * 
     */
    //private EigenschaftMapper eigenschaftMapper;
    private EigenschaftMapper eigMapper = null;
    
    /**
     * 
     */
    //private EigenschaftauspraegungMapper eigenschaftsauspraegungMapper;
    
    /**
     * 
     */
    //private TeilhaberschaftMapper teilhaberschaftMapper;
    
    /*
	   * ***************************************************************************
	   * ABSCHNITT, Beginn: Initialisierung
	   * ***************************************************************************
	   */

//    /**
//     * No-Argument-Constructor
//     */
//    public void KontaktAdministrationImpl() throws IllegalArgumentException {
//    }
    
    /**
     * @return
     */
    public void init() {
        this.konMapper = KontaktMapper.kontaktMapper();
        this.nutzerMapper = NutzerMapper.nutzerMapper();
    }

    /**
     * @param name 
     * @param erzeugungsdatum 
     * @param modifikationsdatum 
     * @param status 
     * @return
     */
    
    /*##########################################################
     * START Kontakt
     #########################################################*/

    
    
    public Kontakt insertKontakt(String name, Date erzeugungsdatum, Date modifikationsdatum, int status, int nutzerID)  	throws IllegalArgumentException {
        // TODO implement here
 
    	Kontakt k = new Kontakt();
    	
    	k.setID(1);
    	k.setName(name);
    	k.setErzeugungsdatum(erzeugungsdatum);
    	k.setModifikationsdatum(modifikationsdatum);
    	k.setStatus(status);
    	k.setNutzerID(nutzerID);
    	
    	
    	
        return this.konMapper.insertKontakt(k);
    }

    /**
     * @param bezeichnung 
     * @param status 
     * @return
     */
    public Eigenschaft createEigenschaft(String bezeichnung, boolean status) {
        // TODO implement here
        return null;
    }

    /**
     * @param bezeichnung 
     * @param erzeugungsdatum 
     * @param modifikationsdatum 
     * @param status 
     * @return
     */
    public Kontaktliste createKontaktliste(String bezeichnung, Date erzeugungsdatum, Date modifikationsdatum, boolean status) {
        // TODO implement here
        return null;
    }

    /**
     * @param bezeichnung 
     * @return
     */
    public Eigenschaftauspraegung createEigenschaftsauspraegung(String bezeichnung) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Teilhaberschaft createTeilhaberschaft() {
        // TODO implement here
        return null;
    }

    /**
     * @param id 
     * @return
     */
    public Kontakt findKontaktById(int id) {
        // TODO implement here
        return null;
    }

    /**
     * @param id 
     * @return
     */
    public Eigenschaft findEigenschaftById(int id) {
        // TODO implement here
        return null;
    }

    /**
     * @param id 
     * @return
     */
    public Kontaktliste findKontaktlisteById(int id) {
        // TODO implement here
        return null;
    }

    /**
     * @param id 
     * @return
     */
    public Eigenschaftauspraegung findEigenschaftsauspraegungById(int id) {
        // TODO implement here
        return null;
    }

    /**
     * @param id 
     * @return
     */
    public Teilhaberschaft findTeilhaberschaftById(int id) {
        // TODO implement here
        return null;
    }

    /**
     * @param id 
     * @return
     */
    public Kontakt findNutzerById(int id) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Vector<Kontaktliste> getAllKontaktlisten() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Vector<Kontakt> getAllKontakte() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Vector<Eigenschaft> getAllEigenschaften() {
        // TODO implement here
        return null;
    }

    /**
     * @param k 
     * @return
     */
    public Vector<Eigenschaft> getAllEigenschaftVonKontakt(Kontakt k) {
        // TODO implement here
        return null;
    }

    /**
     * @param k 
     * @return
     */
    public Vector<Kontakt> getAllKontakteVonKontaktliste(Kontaktliste k) {
        // TODO implement here
        return null;
    }

	@Override
	public Nutzer insertNutzer(String mail) throws IllegalArgumentException {
		Nutzer nutzer = new Nutzer();
		nutzer.setEmail(mail);
		return this.nutzerMapper.insertNutzer(nutzer);
	}

	@Override
	public Kontakt deleteKontakt(Kontakt k) throws IllegalArgumentException {
		
		
		return null;
	}

	

    
    
}
