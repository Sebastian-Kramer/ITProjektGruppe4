package de.hdm.itprojektgruppe4.shared;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.bo.Person;
import de.hdm.itprojektgruppe4.shared.bo.Teilhaberschaft;
import de.hdm.itprojektgruppe4.shared.bo.KontaktKontaktliste;

@RemoteServiceRelativePath("kontaktmanager")
public interface KontaktAdministration extends RemoteService {

	
	
	
    /**
     * @return
     */ 
    public void init() throws IllegalArgumentException;
    
    public Kontakt insertKontakt(String name, Date erzeugungsdatum, Date modifikationsdatum, int status, int nutzerID) throws IllegalArgumentException;
    
    public Nutzer insertNutzer(String mail) throws IllegalArgumentException;
    
    public Kontakt deleteKontakt(Kontakt k) throws IllegalArgumentException;
    
    public Kontakt findKontaktByID(int id) throws IllegalArgumentException;
    
    public Kontakt findKontaktByName(String name) throws IllegalArgumentException;
    
    public Vector<Kontakt> findAllKontakte() throws IllegalArgumentException;
    
    public Kontakt updateKontakt(Kontakt k) throws IllegalArgumentException;
    
    public Person deletePerson(Person p) throws IllegalArgumentException;
    
    public Person findPersonByID(int ID) throws IllegalArgumentException;
    
    public Nutzer deleteNutzer(Nutzer n) throws IllegalArgumentException;
    
    public Nutzer updateNutzer(Nutzer n) throws IllegalArgumentException;
    
    public Nutzer findNutzerByEmail(String email) throws IllegalArgumentException;
    
    public Nutzer findNutzerByID(int id) throws IllegalArgumentException;
    
    public Vector<Nutzer> findAllNutzer() throws IllegalArgumentException;
    
    public Kontaktliste findKontaktlisteByID(int id) throws IllegalArgumentException;
    
    public Kontaktliste findKontaktlisteByBezeichnung(String bezeichnung) throws IllegalArgumentException;
    
    public Kontaktliste insertKontaktliste(Kontaktliste kl) throws IllegalArgumentException;
    
    public Kontaktliste updateKontaktliste(Kontaktliste k) throws IllegalArgumentException;
    
    public Kontaktliste deleteKontaktliste(Kontaktliste k) throws IllegalArgumentException;
    
    public Vector<Kontaktliste> findKontaktlisteAll() throws IllegalArgumentException;
    
    public Eigenschaft getEigenschaftByID(int id) throws IllegalArgumentException;
    
    public Eigenschaft insterEigenschaft(Eigenschaft e) throws IllegalArgumentException;
    
    public Eigenschaft updateEigenschaft(Eigenschaft e) throws IllegalArgumentException;
    
    public Eigenschaft deleteEigenschaft(Eigenschaft e) throws IllegalArgumentException;
    
    public Eigenschaftauspraegung insertAuspraegung(Eigenschaftauspraegung ea) throws IllegalArgumentException;
    
    public Eigenschaftauspraegung updateAuspraegung(Eigenschaftauspraegung ea) throws IllegalArgumentException;
    
    public Eigenschaftauspraegung deleteAuspraegung(Eigenschaftauspraegung ea) throws IllegalArgumentException;
    
    public Eigenschaftauspraegung getAuspraegungByWert(Eigenschaftauspraegung ea) throws IllegalArgumentException;
    
    public Eigenschaftauspraegung getAuspraegungByID(int id) throws IllegalArgumentException;
    
    public Teilhaberschaft findByID(int id) throws IllegalArgumentException;
    
    public Teilhaberschaft insertTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException;
    
    public Teilhaberschaft deleteTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException;
    
    public Teilhaberschaft deleteKontaktFromTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException; 
    
    public Teilhaberschaft deleteKontaktlisteFromTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException;
    
    public Teilhaberschaft deleteEigenschaftsauspraegungFromTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException;
    
    public KontaktKontaktliste insertKontaktKontaktliste(KontaktKontaktliste k) throws IllegalArgumentException;
    
    public KontaktKontaktliste deleteKontaktKontaktliste(KontaktKontaktliste k) throws IllegalArgumentException;
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * @param name 
     * @param erzeugungsdatum 
     * @param modifikationsdatum 
     * @param status 
     * @return
     */
    //public Kontakt createKontakt(String name, Date erzeugungsdatum, Date modifikationsdatum, boolean status)
    //	throws IllegalArgumentException;

    /**
     * @param bezeichnung 
     * @param status 
     * @return
     */
    //public Eigenschaft createEigenschaft(String bezeichnung, boolean status)
    //		throws IllegalArgumentException;

    /**
     * @param bezeichnung 
     * @param status 
     * @return
     */
    //public Kontaktliste createKontaktliste(String bezeichnung, boolean status)
    //		throws IllegalArgumentException;

    /**
     * @param bezeichnung 
     * @return
     */
    //public Eigenschaftsauspraegung createEigenschaftsauspraegung(String bezeichnung)
    //		throws IllegalArgumentException;

    /**
     * @param KontaktlisteID 
     * @param KontaktID 
     * @param NutzerID 
     * @param Eigenschaftsauspraegung 
     * @return
     */
    //public Teilhaberschaft createTeilhaberschaftKlist(int KontaktlisteID, int KontaktID, int NutzerID, int Eigenschaftsauspraegung)
    //		throws IllegalArgumentException;

    /**
     * @param KontaktID 
     * @param NutzerID 
     * @param Eigenschaftsauspraegung 
     * @return
     */
    //public Teilhaberschaft createTeilhaberschaftKontakt(int KontaktID, int NutzerID, int Eigenschaftsauspraegung)
    //		throws IllegalArgumentException;

    /**
     * @param id 
     * @return
     */
    //public Kontakt findKontaktById(int id);

    /**
     * @param id 
     * @return
     */
    //public Eigenschaft findEigenschaftById(int id);

    /**
     * @param id 
     * @return
     */
    //public Kontaktliste findKontaktlisteById(int id);

    /**
     * @param id 
     * @return
     */
    //public Eigenschaftsauspraegung findEigenschaftsauspraegungById(int id);

    /**
     * @param id 
     * @return
     */
    //public Teilhaberschaft findTeilhaberschaftById(int id);

    /**
     * @param id 
     * @return
     */
    //public Kontakt findNutzerById(int id);

    /**
     * @param kontakt 
     * @return
     */
    //public void deleteKontakt(Kontakt kontakt);

    /**
     * @param eigenschaft 
     * @return
     */
    //public void deleteEigenschaft(Eigenschaft eigenschaft);

    /**
     * @param kontaktliste 
     * @return
     */
    //public void deleteKontaktliste(Kontaktliste kontaktliste);

    /**
     * @param eigenschaftsauspraegung 
     * @return
     */
    //public void deleteEigenschaftsauspraegung(Eigenschaftsauspraegung eigenschaftsauspraegung);

    /**
     * @param teilhaberschaft 
     * @return
     */
    //public void deleteTeilhaberschaft(Teilhaberschaft teilhaberschaft);

    /**
     * @param kontakt 
     * @return
     */
    //public void updateKontakt(Kontakt kontakt);

    /**
     * @param eigenschaft 
     * @return
     */
    //public void updateEigenschaft(Eigenschaft eigenschaft);

    /**
     * @param kontaktliste 
     * @return
     */
    //public void updateKontaktliste(Kontaktliste kontaktliste);

    /**
     * @param eigenschaftsauspraegung 
     * @return
     */
    //public void updateEigenschaftsauspraegung(Eigenschaftsauspraegung eigenschaftsauspraegung);

    /**
     * @param teilhaberschaft 
     * @return
     */
    //public void updateTeilhaberschaft(Teilhaberschaft teilhaberschaft);

    /**
     * @return
     */
    //public Vector<Kontaktliste> getAllKontaktlisten();

    /**
     * @return
     */
    //public Vector<Kontakt> getAllKontakte();

    /**
     * @param k 
     * @return
     */
    //public Vector<Eigenschaft> getAllEigenschaftFromKontakt(Kontakt k);

    /**
     * @param k 
     * @return
     */
    //public Vector<Kontakt> getAllKontakteVonKontaktliste(Kontaktliste k);

    /**
     * @param n 
     * @return
     */
    //public Vector<Kontakt> getAllKontaktefromNutzer(Kontakt k);

}