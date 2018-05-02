package de.hdm.itprojektgruppe4.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektgruppe4.server.db.KontaktMapper;
import de.hdm.itprojektgruppe4.shared.KontaktAdministration;

public class KontaktAdministrationImpl extends RemoteServiceServlet 
	implements KontaktAdiministration{

	/**
     * Default constructor
     */
    public KontaktAdministrationImpl() {
    }

    /**
     * 
     */
    private KontaktMapper kontaktMapper;

    /**
     * 
     */
    private KontaktlisteMapper kontaktlisteMapper;

    /**
     * 
     */
    private EigenschaftMapper eigenschaftMapper;

    /**
     * 
     */
    private EigenschaftausprägungMapper eigenschaftsauspraegungMapper;

    /**
     * 
     */
    private TeilhaberschaftMapper teilhaberschaftMapper;

    /**
     * @return
     */
    public void init() {
        // TODO implement here
        return null;
    }

    /**
     * 
     */
    public void KontaktAdministrationImpl() {
        // TODO implement here
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
    public Eigenschaftsausprägung createEigenschaftsauspraegung(String bezeichnung) {
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
    public Kontakt findById(int id) {
        // TODO implement here
        return null;
    }

    /**
     * @param id 
     * @return
     */
    public Eigenschaft findById(int id) {
        // TODO implement here
        return null;
    }

    /**
     * @param id 
     * @return
     */
    public Kontaktliste findById(int id) {
        // TODO implement here
        return null;
    }

    /**
     * @param id 
     * @return
     */
    public Eigenschaftsauspraegung findById(int id) {
        // TODO implement here
        return null;
    }

    /**
     * @param id 
     * @return
     */
    public Teilhaberschaft findById(int id) {
        // TODO implement here
        return null;
    }

    /**
     * @param id 
     * @return
     */
    public Nutzer findById(int id) {
        // TODO implement here
        return null;
    }

    /**
     * @param kontakt 
     * @return
     */
    public void deleteKontakt(Kontakt kontakt) {
        // TODO implement here
        return null;
    }

    /**
     * @param eigenschaft 
     * @return
     */
    public void deleteEigenschaft(Eigenschaft eigenschaft) {
        // TODO implement here
        return null;
    }

    /**
     * @param kontaktliste 
     * @return
     */
    public void deleteKontaktliste(Kontaktliste kontaktliste) {
        // TODO implement here
        return null;
    }

    /**
     * @param eigenschaftsauspraegung 
     * @return
     */
    public void deleteEigenschaftsauspraegung(Eigenschaftsauspraegung eigenschaftsauspraegung) {
        // TODO implement here
        return null;
    }

    /**
     * @param teilhaberschaft 
     * @return
     */
    public void deleteTeilhaberschaft(Teilhaberschaft teilhaberschaft) {
        // TODO implement here
        return null;
    }

    /**
     * @param kontakt 
     * @return
     */
    public Kontakt updateKontakt(Kontakt kontakt) {
        // TODO implement here
        return null;
    }

    /**
     * @param eigenschaft 
     * @return
     */
    public Eigenschaft updateEigenschaft(Eigenschaft eigenschaft) {
        // TODO implement here
        return null;
    }

    /**
     * @param kontaktliste 
     * @return
     */
    public Kontaktliste updateKontaktliste(Kontaktliste kontaktliste) {
        // TODO implement here
        return null;
    }

    /**
     * @param eigenschaftsauspraegung 
     * @return
     */
    public Eigenschaftsauspraegung updateEigenschaftsauspraegung(Eigenschaftsauspraegung eigenschaftsauspraegung) {
        // TODO implement here
        return null;
    }

    /**
     * @param teilhaberschaft 
     * @return
     */
    public Teilhaberschaft updateTeilhaberschaft(Teilhaberschaft teilhaberschaft) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Vektor<Kontaktliste> getAllKontaktlisten() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Vektor<Kontakt> getAllKontakte() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Vektor<Eigenschaft> getAllEigenschaften() {
        // TODO implement here
        return null;
    }

    /**
     * @param k 
     * @return
     */
    public Vektor<Eigenschaft> getAllEigenschaftVonKontakt(Kontakt k) {
        // TODO implement here
        return null;
    }

    /**
     * @param k 
     * @return
     */
    public Vektor<Kontakt> getAllKontakteVonKontaktliste(Kontaktliste k) {
        // TODO implement here
        return null;
    }

}
}
