package de.hdm.itprojektgruppe4.server;

import java.sql.Date;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektgruppe4.server.db.KontaktMapper;
import de.hdm.itprojektgruppe4.shared.KontaktAdministration;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftsauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Teilhaberschaft;

public class KontaktAdministrationImpl extends RemoteServiceServlet 
	implements KontaktAdministration{

	/**
     * Default constructor
     */
    public KontaktAdministrationImpl() {
    }

    /**
     * 
     */
    private KontaktMapper kontaktMapper = null;

    /**
     * 
     */
    //private KontaktlisteMapper kontaktlisteMapper;

    /**
     * 
     */
    //private EigenschaftMapper eigenschaftMapper;

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

    /**
     * No-Argument-Constructor
     */
    public void KontaktAdministrationImpl() throws IllegalArgumentException {
    }
    
    /**
     * @return
     */
    public void init() {
        this.kontaktMapper = KontaktMapper.kontaktMapper();
    }

    /**
     * @param name 
     * @param erzeugungsdatum 
     * @param modifikationsdatum 
     * @param status 
     * @return
     */
    public Kontakt createKontakt(String name, Date erzeugungsdatum, Date modifikationsdatum, boolean status) {
        // TODO implement here
        return null;
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
    public Eigenschaftsauspraegung createEigenschaftsauspraegung(String bezeichnung) {
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
    public Eigenschaftsauspraegung findEigenschaftsauspraegungById(int id) {
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

}
