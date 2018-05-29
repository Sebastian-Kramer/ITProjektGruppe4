package de.hdm.itprojektgruppe4.shared;

import java.util.Date;
import java.util.List;
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
     * Initialisierung des Objekts. Wird direkt nach der Instantiierung aufgerufen.
     * @throws IllegalArgumentException
     */
    public void init() throws IllegalArgumentException;
    
    /**
     * Einen Kontakt anlegen
     * 
     * @param name, der Name des Kontakts
     * @param erzeugungsdatum, das Erzeugungsdatum des Kontakts
     * @param modifikationsdatum, das Datum an dem Eigenschaften oder Auspraegungen des Kontaktes geändert wurden
     * @param status, der Status ob der Kontakt geteilt wurde oder nicht
     * @param nutzerID, Fremdschlüsselbeziehung zum Ersteller des Kontakes
     * @return Kontakt-Objekt
     * @throws IllegalArgumentException
     */
    public Kontakt insertKontakt(String name, Date erzeugungsdatum, Date modifikationsdatum, int status, int nutzerID) throws IllegalArgumentException;
    
    /**
     * Einen Nutzer anlegen
     * 
     * @param mail
     * @return Nutzer-Objekt
     * @throws IllegalArgumentException
     */
    public Nutzer insertNutzer(String mail) throws IllegalArgumentException;
    
    /**
     * Einen Kontakt löschen
     * 
     * @param k das zu löschende Kontakt-Objekt
     * @throws IllegalArgumentException
     */
    public void deleteKontakt(Kontakt k) throws IllegalArgumentException;
    
    /**
     * Einen Kontakt anhand seines Primärschlüssel anzeigen lassen.
     * 
     * @param id der Primärschlüssel des Objekts
     * @return Kontakt-Objekt mit dem übergebenen Primärschlüssel
     * @throws IllegalArgumentException
     */
    public Kontakt findKontaktByID(int id) throws IllegalArgumentException;
    
    /**
     * Einen Kontakt anhand seines Namens anzeigen lassen.
     * 
     * @param name, der Name des Kontakts
     * @return Kontakt-Objekt mit dem übergebenen Namen
     * @throws IllegalArgumentException
     */
    public Kontakt findKontaktByName(String name) throws IllegalArgumentException;
    
    /**
     * Alle Kontakte auslesen und in einem Vector ausgeben lassen.
     * 
     * @return Vector aller Kontakte
     * @throws IllegalArgumentException
     */
    public Vector<Kontakt> findAllKontakte() throws IllegalArgumentException;
    
    /**
     * Überschreiben eines <code>Kontakt</code>-Objekts.
     * 
     * @param k das zu bearbeitende Kontakt-Objekt
     * @return das bearbeitete Kontakt-Objekt
     * @throws IllegalArgumentException
     */
    public Kontakt updateKontakt(Kontakt k) throws IllegalArgumentException;
    
    /**
     * Löschen einer Person.
     * 
     * @param p das zu löschende Personen-Objekt
     * @throws IllegalArgumentException
     */
    public void deletePerson(Person p) throws IllegalArgumentException;
    
    /**
     * Eine Person anhand der ID auslesen.
     * 
     * @param ID, der Primärschlüssel
     * @throws IllegalArgumentException
     */
    public Person findPersonByID(int ID) throws IllegalArgumentException;
    
    /**
     * Löschen eines Nutzers.
     * 
     * @param n das zu löschende Nutzer-Objekt
     * @throws IllegalArgumentException
     */
    public void deleteNutzer(Nutzer n) throws IllegalArgumentException;
    
    /**
     * Überschreiben eines Nutzer-Objekts.
     * 
     * @param n das zu bearbeitende Nutzer-Objekt
     * @return n das bearbeitete Nutzer Objekt
     * @throws IllegalArgumentException
     */
    public Nutzer updateNutzer(Nutzer n) throws IllegalArgumentException;
    
    /**
     * Einen Nutzer anhand seiner E-Mail auslesen.
     * 
     * @param email
     * @return Nutzer-Objekt
     * @throws IllegalArgumentException
     */
    public Nutzer findNutzerByEmail(String email) throws IllegalArgumentException;
    
    Nutzer findNutzerByID(String string);
    
    /**
     * Alle Nutzer auslesen.
     * 
     * @return Vector sämtlicher Nutzer
     * @throws IllegalArgumentException
     */
    public Vector<Nutzer> findAllNutzer() throws IllegalArgumentException;
    
    /**
     * Eine Kontaktliste anhand der ID auslesen.
     * 
     * @param id der Primärschlüssel des Kontaktlisten-Objekts
     * @return Kontaktlisten-Objekt
     * @throws IllegalArgumentException
     */
    public Kontaktliste findKontaktlisteByID(int id) throws IllegalArgumentException;
    
    /**
     * Eine Kontaktliste anhand der Bezeichnung auslesen.
     * 
     * @param bezeichnung, die Bezeichnung der Kontaktliste
     * @return Kontaktlisten-Objekt
     * @throws IllegalArgumentException
     */
    public Kontaktliste findKontaktlisteByBezeichnung(String bezeichnung) throws IllegalArgumentException;
    
   /**
    * 
    * @param bez, die Bezeichnung der Kontaktliste
    * @param status, der Status, der anzeigt ob die Kontaktliste geteilt wurde oder nicht
    * @param nutzerID, der Fremdschlüssel stellt die Beziehung zum Ersteller dar
    * @return Kontaktlisten-Objekt
    * @throws IllegalArgumentException
    */
    public Kontaktliste insertKontaktliste(String bez, int status, int nutzerID ) throws IllegalArgumentException;
    
    /**
     * Eine Kontaktliste überschreiben.
     * 
     * @param k das zu bearbeitende Kontaktlisten-Objekt
     * @return 
     * @throws IllegalArgumentException
     */
    public Kontaktliste updateKontaktliste(Kontaktliste k) throws IllegalArgumentException;
    
    /**
     * Eine Kontaktliste  löschen.
     * 
     * @param k das zu löschende Kontaktlisten-Objekt
     * @throws IllegalArgumentException
     */
    public void deleteKontaktliste(Kontaktliste k) throws IllegalArgumentException;
    
    /**
     * Alle Kontaktlisten auslesen.
     * 
     * @return Vector sämtlicher Kontaktlisten
     * @throws IllegalArgumentException
     */
    public Vector<Kontaktliste> findKontaktlisteAll() throws IllegalArgumentException;
    
    /**
     * Eine Eigenschaft anhand der ID auslesen.
     * 
     * @param id, Primärschlüssel der Eigenschaft
     * @return Eigenschaft-Objekt
     * @throws IllegalArgumentException
     */
    public Eigenschaft getEigenschaftByID(int id) throws IllegalArgumentException;
    
   
    /**
     * Eine Eigenschaft anlegen.
     * 
     * @param bez, die Bezeichnung der Eigenschaft
     * @param status, Status der anzeigt, ob die Eigenschaft geteilt wurde oder nicht
     * @return Eigenschaft Objekt
     * @throws IllegalArgumentException
     */
    public Eigenschaft insertEigenschaft(String bez, int status) throws IllegalArgumentException;
    
    /**
     * Eine Eigenschaft überschreiben.
     * 
     * @param e das Eigenschaft-Objekt
     * @return
     * @throws IllegalArgumentException
     */
    public Eigenschaft updateEigenschaft(Eigenschaft e) throws IllegalArgumentException;
    
    /**
     * Eine Eigenschaft löschen.
     * 
     * @param e das zu löschende Eigenschaft-Objekt
     * @throws IllegalArgumentException
     */
    public void deleteEigenschaft(Eigenschaft e) throws IllegalArgumentException;
    
    /**
     *  Alle Objekte vom Typ Eigenschaft ausgeben
     * @return
     * @throws IllegalArgumentException
     */
    
    public Vector<Eigenschaft> findAllEigenschaft() throws IllegalArgumentException;
   
    
/**
 * 
 * @param wert, die Auspraegung der Eigenschaft
 * @param status, zeigt an ob die Auspraegung geteilt wurde oder nicht
 * @param eigenschaftsID, Fremschlüsselbeziehung zur Eigenschaft
 * @param kontaktID, Fremdschlüsselbeziehung zum Kontakt auf den sich die Eigenschaft bezieht
 * @return Eigenschaft-Objekt
 * @throws IllegalArgumentException
 */
    public Eigenschaftauspraegung insertAuspraegung(String wert, int status, int eigenschaftsID, int kontaktID) throws IllegalArgumentException;
    
    /**
     * Eine Eigenschaftsauspraegung überschreiben.
     * 
     * @param ea
     * @return 
     * @throws IllegalArgumentException
     */
    public Eigenschaftauspraegung updateAuspraegung(Eigenschaftauspraegung ea) throws IllegalArgumentException;
    
    /**
     * Eine Eigenschaftsauspraegung löschen.
     * 
     * @param ea das Eigenschaftsauspraegung-Objekt
     * @throws IllegalArgumentException
     */
    public void deleteAuspraegung(Eigenschaftauspraegung ea) throws IllegalArgumentException;
    
    /**
     * Eine Eigenschaftsauspraegung anhand des Wertes auslesen.
     * 
     * @param wert, der die Auspraegung beschreibt
     * @return Eigenschafts-Objekt mit gesuchtem Wert
     * @throws IllegalArgumentException
     */
    public Eigenschaftauspraegung getAuspraegungByWert(String wert) throws IllegalArgumentException;
    
    /**
     * Eine Eigenschaftsauspraegung anhand der ID auslesen.
     * 
     * @param id der Primärschlüssel der Auspraegung
     * @return Eigenschaftsauspraegung-Objekt
     * @throws IllegalArgumentException
     */
    public Eigenschaftauspraegung getAuspraegungByID(int id) throws IllegalArgumentException;
    
    /**
     * Eine Teilhaberschaft anhand der ID auslesen.
     * 
     * @param id der Primärschlüssel der Teilhaberschaft
     * @return Teilhaberschaft-Objekt
     * @throws IllegalArgumentException
     */
    public Teilhaberschaft findByID(int id) throws IllegalArgumentException;

    /**
     * Eine Teilhaberschaft anlegen
     * 
     * @param kontaktID, Fremdschlüsselbeziehung zum Kontakt
     * @param kontaktListeID, Fremdschlüsselbeziehung zur Kontaktliste
     * @param eigenschaftsauspraegungID, Fremdschlüsselbeziehung zur Eingenschaftsauspraegung
     * @param teilhaberID, Fremdschlüsselbeziehung zum Teilhaber
     * @return Teilhaberschaft-Objekt
     * @throws IllegalArgumentException
     */
    public Teilhaberschaft insertTeilhaberschaft(int kontaktID, int kontaktListeID, int eigenschaftsauspraegungID, int teilhaberID) throws IllegalArgumentException;
    
    /**
     * Eine Teilhaberschaft löschen.
     * 
     * @param t das zu löschende Teilhaberschaft-Objekt
     * @throws IllegalArgumentException
     */
    public void deleteTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException;
    
    /**
     * Eine Teilhaberschaft an einem Kontakt löschen.
     * 
     * @param t das zur löschende Teilhaber-Objekt
     * @throws IllegalArgumentException
     */
    public void deleteKontaktFromTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException; 
    
    /**
     * Eine Teilhaberschaft an einer Kontaktliste löschen.
     * 
     * @param t das zu löschende Teilhaberschaft-Objekt
     * @throws IllegalArgumentException
     */
    public void deleteKontaktlisteFromTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException;
    
    /**
     * Eine Teilhaberschaft an einer Eigenschaftsauspraegung löschen.
     * 
     * @param t das zu löschende Teilhaberschaftobjekt
     * @throws IllegalArgumentException
     */
    public void deleteEigenschaftsauspraegungFromTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException;
    
    /**
     * 
     * 
     * @return
     * @throws IllegalArgumentException
     */
	public List<Kontakt> findAllKontaktNames() throws IllegalArgumentException;
    
	/**
	 * Anlegen eines Objekts der Klasse KontaktKontaktliste um eine Beziehung zwischen Kontakt und Kontaktliste in der Datenbank herzustellen.
	 * 
	 * @param k
	 * @return KontaktKontaktliste-Objekt
	 */
    public KontaktKontaktliste insertKontaktKontaktliste(int kontaktID, int kontaktlisteID) throws IllegalArgumentException;
    
    /**
     * Aufhebung der Beziehung zwischen Kontakt und Kontaktliste.
     * 
     * @param k das zu löschende KontaktKontaktliste-Objekt
     * @throws IllegalArgumentException
     */
    public void deleteKontaktKontaktliste(KontaktKontaktliste k) throws IllegalArgumentException;
    
    
    
    
    List<Kontakt> findKontaktByNutzerID(int nutzerID);
    


    
    
    
    
    
    
    
    
  
    
    
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