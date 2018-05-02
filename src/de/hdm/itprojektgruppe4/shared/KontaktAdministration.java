package de.hdm.itprojektgruppe4.shared;

import com.google.gwt.user.client.rpc.RemoteService;

public interface KontaktAdministration extends RemoteService {

    /**
     * @return
     */
    public void init() throws IllegalArgumentException;
    
    
    /**
     * @param name 
     * @param erzeugungsdatum 
     * @param modifikationsdatum 
     * @param status 
     * @return
     */
    public Kontakt createKontakt(String name, Date erzeugungsdatum, Date modifikationsdatum, boolean status)
    	throws IllegalArgumentException;

    /**
     * @param bezeichnung 
     * @param status 
     * @return
     */
    public Eigenschaft createEigenschaft(String bezeichnung, boolean status)
    		throws IllegalArgumentException;

    /**
     * @param bezeichnung 
     * @param status 
     * @return
     */
    public Kontaktliste createKontaktliste(String bezeichnung, boolean status)
    		throws IllegalArgumentException;

    /**
     * @param bezeichnung 
     * @return
     */
    public Eigenschaftsauspraegung createEigenschaftsauspraegung(String bezeichnung)
    		throws IllegalArgumentException;

    /**
     * @param KontaktlisteID 
     * @param KontaktID 
     * @param NutzerID 
     * @param Eigenschaftsauspraegung 
     * @return
     */
    public Teilhaberschaft createTeilhaberschaftKlist(int KontaktlisteID, int KontaktID, int NutzerID, int Eigenschaftsauspraegung)
    		throws IllegalArgumentException;

    /**
     * @param KontaktID 
     * @param NutzerID 
     * @param Eigenschaftsauspraegung 
     * @return
     */
    public Teilhaberschaft createTeilhaberschaftKontakt(int KontaktID, int NutzerID, int Eigenschaftsauspraegung)
    		throws IllegalArgumentException;

    /**
     * @param id 
     * @return
     */
    public Kontakt findById(int id);

    /**
     * @param id 
     * @return
     */
    public Eigenschaft findById(int id);

    /**
     * @param id 
     * @return
     */
    public Kontaktliste findById(int id);

    /**
     * @param id 
     * @return
     */
    public Eigenschaftsauspraegung findById(int id);

    /**
     * @param id 
     * @return
     */
    public Teilhaberschaft findById(int id);

    /**
     * @param id 
     * @return
     */
    public Nutzer findById(int id);

    /**
     * @param kontakt 
     * @return
     */
    public void deleteKontakt(Kontakt kontakt);

    /**
     * @param eigenschaft 
     * @return
     */
    public void deleteEigenschaft(Eigenschaft eigenschaft);

    /**
     * @param kontaktliste 
     * @return
     */
    public void deleteKontaktliste(Kontaktliste kontaktliste);

    /**
     * @param eigenschaftsauspraegung 
     * @return
     */
    public void deleteEigenschaftsauspraegung(Eigenschaftsauspraegung eigenschaftsauspraegung);

    /**
     * @param teilhaberschaft 
     * @return
     */
    public void deleteTeilhaberschaft(Teilhaberschaft teilhaberschaft);

    /**
     * @param kontakt 
     * @return
     */
    public void updateKontakt(Kontakt kontakt);

    /**
     * @param eigenschaft 
     * @return
     */
    public void updateEigenschaft(Eigenschaft eigenschaft);

    /**
     * @param kontaktliste 
     * @return
     */
    public void updateKontaktliste(Kontaktliste kontaktliste);

    /**
     * @param eigenschaftsauspraegung 
     * @return
     */
    public void updateEigenschaftsauspraegung(Eigenschaftsauspraegung eigenschaftsauspraegung);

    /**
     * @param teilhaberschaft 
     * @return
     */
    public void updateTeilhaberschaft(Teilhaberschaft teilhaberschaft);

    /**
     * @return
     */
    public Vektor<Kontaktliste> getAllKontaktlisten();

    /**
     * @return
     */
    public Vektor<Kontakt> getAllKontakte();

    /**
     * @param k 
     * @return
     */
    public Vektor<Eigenschaft> getAllEigenschaftFromKontakt(Kontakt k);

    /**
     * @param k 
     * @return
     */
    public Vektor<Kontakt> getAllKontakteVonKontaktliste(Kontaktliste k);

    /**
     * @param n 
     * @return
     */
    public Vektor<Kontakt> getAllKontaktefromNutzer(Nutzer n);

}