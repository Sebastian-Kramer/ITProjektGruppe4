package de.hdm.itprojektgruppe4.shared;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.EigenschaftAuspraegungHybrid;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft_Auspraegung;
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
     * @param modifikationsdatum, das Datum an dem Eigenschaften oder Auspraegungen des Kontaktes geï¿½ndert wurden
     * @param status, der Status ob der Kontakt geteilt wurde oder nicht
     * @param nutzerID, Fremdschlï¿½sselbeziehung zum Ersteller des Kontakes
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
     * Einen Kontakt lï¿½schen
     * 
     * @param k das zu lï¿½schende Kontakt-Objekt
     * @throws IllegalArgumentException
     */
    public void deleteKontakt(Kontakt k) throws IllegalArgumentException;
    
    /**
     * Einen Kontakt anhand seines Primï¿½rschlï¿½ssel anzeigen lassen.
     * 
     * @param id der Primï¿½rschlï¿½ssel des Objekts
     * @return Kontakt-Objekt mit dem ï¿½bergebenen Primï¿½rschlï¿½ssel
     * @throws IllegalArgumentException
     */
    public Kontakt findKontaktByID(int id) throws IllegalArgumentException;
    
    /**
     * Einen Kontakt anhand seines Namens anzeigen lassen.
     * 
     * @param name, der Name des Kontakts
     * @return Kontakt-Objekt mit dem ï¿½bergebenen Namen
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
     * ï¿½berschreiben eines <code>Kontakt</code>-Objekts.
     * 
     * @param k das zu bearbeitende Kontakt-Objekt
     * @return das bearbeitete Kontakt-Objekt
     * @throws IllegalArgumentException
     */
    public Kontakt updateKontakt(Kontakt k) throws IllegalArgumentException;
    
    /**
     * Lï¿½schen einer Person.
     * 
     * @param p das zu lï¿½schende Personen-Objekt
     * @throws IllegalArgumentException
     */
    public void deletePerson(Person p) throws IllegalArgumentException;
    
    /**
     * Eine Person anhand der ID auslesen.
     * 
     * @param ID, der Primï¿½rschlï¿½ssel
     * @throws IllegalArgumentException
     */
    public Person findPersonByID(int ID) throws IllegalArgumentException;
    
    /**
     * Lï¿½schen eines Nutzers.
     * 
     * @param n das zu lï¿½schende Nutzer-Objekt
     * @throws IllegalArgumentException
     */
    public void deleteNutzer(Nutzer n) throws IllegalArgumentException;
    
    /**
     * ï¿½berschreiben eines Nutzer-Objekts.
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
    

    /**
     * Einen Nutzer anhand seiner ID auslesen.
     * 
     * @param id der Primï¿½rschlï¿½ssel des Nutzer-Objekts
     * @return Nutzer-Objekt
     * @throws IllegalArgumentException
     */
    public Nutzer findNutzerByID(int id) throws IllegalArgumentException;

    Nutzer findNutzerByID(String string);

    
    /**
     * Alle Nutzer auslesen.
     * 
     * @return Vector sï¿½mtlicher Nutzer
     * @throws IllegalArgumentException
     */
    public Vector<Nutzer> findAllNutzer() throws IllegalArgumentException;
    
    /**
     * Eine Kontaktliste anhand der ID auslesen.
     * 
     * @param id der Primï¿½rschlï¿½ssel des Kontaktlisten-Objekts
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
    * @param nutzerID, der Fremdschlï¿½ssel stellt die Beziehung zum Ersteller dar
    * @return Kontaktlisten-Objekt
    * @throws IllegalArgumentException
    */
    public Kontaktliste insertKontaktliste(String bez, int status, int nutzerID ) throws IllegalArgumentException;
    
    /**
     * Eine Kontaktliste ï¿½berschreiben.
     * 
     * @param k das zu bearbeitende Kontaktlisten-Objekt
     * @return 
     * @throws IllegalArgumentException
     */
    public Kontaktliste updateKontaktliste(Kontaktliste k) throws IllegalArgumentException;
    
    /**
     * Eine Kontaktliste  lï¿½schen.
     * 
     * @param k das zu lï¿½schende Kontaktlisten-Objekt
     * @throws IllegalArgumentException
     */
    public void deleteKontaktliste(Kontaktliste k) throws IllegalArgumentException;
    
    /**
     * Alle Kontaktlisten auslesen.
     * 
     * @return Vector sï¿½mtlicher Kontaktlisten
     * @throws IllegalArgumentException
     */
    public Vector<Kontaktliste> findKontaktlisteAll() throws IllegalArgumentException;
    
    
	public Vector<Eigenschaft> getEigenschaftByBezeichnung(String bez) throws IllegalArgumentException;

    
    /**
     * Eine Eigenschaft anhand der ID auslesen.
     * 
     * @param id, Primï¿½rschlï¿½ssel der Eigenschaft
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
     * Eine Eigenschaft ï¿½berschreiben.
     * 
     * @param e das Eigenschaft-Objekt
     * @return
     * @throws IllegalArgumentException
     */
    public Eigenschaft updateEigenschaft(Eigenschaft e) throws IllegalArgumentException;
    
    /**
     * Eine Eigenschaft lï¿½schen.
     * 
     * @param e das zu lï¿½schende Eigenschaft-Objekt
     * @throws IllegalArgumentException
     */
    public void deleteEigenschaft(Eigenschaft e) throws IllegalArgumentException;
    
    /**
     *  Alle Objekte vom Typ Eigenschaft ausgeben
     * @return
     * @throws IllegalArgumentException
     */
    
    public Vector<Eigenschaft> findAllEigenschaft() throws IllegalArgumentException;
    
    
    public Vector<Eigenschaft> getEigenschaftbyKontaktID(int id);
   
    
/**
 * 
 * @param wert, die Auspraegung der Eigenschaft
 * @param status, zeigt an ob die Auspraegung geteilt wurde oder nicht
 * @param eigenschaftsID, Fremschlï¿½sselbeziehung zur Eigenschaft
 * @param kontaktID, Fremdschlï¿½sselbeziehung zum Kontakt auf den sich die Eigenschaft bezieht
 * @return Eigenschaft-Objekt
 * @throws IllegalArgumentException
 */
    public Eigenschaftauspraegung insertAuspraegung(String wert, int status, int eigenschaftsID, int kontaktID) throws IllegalArgumentException;
    
    /**
     * Eine Eigenschaftsauspraegung ï¿½berschreiben.
     * 
     * @param ea
     * @return 
     * @throws IllegalArgumentException
     */
    public Eigenschaftauspraegung updateAuspraegung(Eigenschaftauspraegung ea) throws IllegalArgumentException;
    
    /**
     * Eine Eigenschaftsauspraegung lï¿½schen.
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
     * @param id der Primï¿½rschlï¿½ssel der Auspraegung
     * @return Eigenschaftsauspraegung-Objekt
     * @throws IllegalArgumentException
     */
    public Eigenschaftauspraegung getAuspraegungByID(int id) throws IllegalArgumentException;
    

    
    /**
     * Alle Eigenschaftsauspraegungen eines Kontaktes auslesen
     * 
     * @param kontaktID
     * @return Vector mit sï¿½mtlichen Eigenschaftsauspraegungen mit der uebergebenen KontaktID
     * @throws IllegalArgumentException
     */
    public Vector<Eigenschaftauspraegung> findEigenschaftauspraegungByKontaktID(int kontaktID ) throws IllegalArgumentException;

    public Vector<Eigenschaftauspraegung> getAuspraegungByKontaktID(int id) throws IllegalArgumentException;
    
    public Vector<EigenschaftAuspraegungHybrid> findHybrid(Person pers) throws IllegalArgumentException;
    
    public void deleteEigenschaftUndAuspraegung(EigenschaftAuspraegungHybrid ea) throws IllegalArgumentException;

    
    /**
     * Eine Teilhaberschaft anhand der ID auslesen.
     * 
     * @param id der Primï¿½rschlï¿½ssel der Teilhaberschaft
     * @return Teilhaberschaft-Objekt
     * @throws IllegalArgumentException
     */
    public Teilhaberschaft findByID(int id) throws IllegalArgumentException;

    /**
     * Eine Teilhaberschaft anlegen
     * 
     * @param kontaktID, Fremdschlï¿½sselbeziehung zum Kontakt
     * @param kontaktListeID, Fremdschlï¿½sselbeziehung zur Kontaktliste
     * @param eigenschaftsauspraegungID, Fremdschlï¿½sselbeziehung zur Eingenschaftsauspraegung
     * @param teilhaberID, Fremdschlï¿½sselbeziehung zum Teilhaber
     * @return Teilhaberschaft-Objekt
     * @throws IllegalArgumentException
     */
    public Teilhaberschaft insertTeilhaberschaft(int kontaktListeID, int kontaktID , int eigenschaftsauspraegungID, int teilhaberID, int nutzerID) throws IllegalArgumentException;
    
    /**
     * Eine Teilhaberschaft fÃ¼r ein geteilten Kontakt anlegen
     * 
     * @param kontaktID
     * @param eigenschaftsauspraegungID
     * @param teilhaberID
     * @return
     */
    public Teilhaberschaft insertTeilhaberschaftKontakt(int kontaktID, int eigenschaftsauspraegungID, int teilhaberID, int nutzerID);
    
    /**
     * Eine Teilhaberschaft lï¿½schen.
     * 
     * @param t das zu lï¿½schende Teilhaberschaft-Objekt
     * @throws IllegalArgumentException
     */
    public void deleteTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException;
    
    /**
     * Eine Teilhaberschaft an einem Kontakt lï¿½schen.
     * 
     * @param t das zur lï¿½schende Teilhaber-Objekt
     * @throws IllegalArgumentException
     */
    public void deleteKontaktFromTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException; 
    
    /**
     * Eine Teilhaberschaft an einer Kontaktliste lï¿½schen.
     * 
     * @param t das zu lï¿½schende Teilhaberschaft-Objekt
     * @throws IllegalArgumentException
     */
    public void deleteKontaktlisteFromTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException;
    
    /**
     * Eine Teilhaberschaft an einer Eigenschaftsauspraegung lï¿½schen.
     * 
     * @param t das zu lï¿½schende Teilhaberschaftobjekt
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
     * @param k das zu lï¿½schende KontaktKontaktliste-Objekt
     * @throws IllegalArgumentException
     */
    public void deleteKontaktKontaktliste(KontaktKontaktliste k) throws IllegalArgumentException;
    

    List<Kontakt> findKontaktByNutzerID(int nutzerID);

    /**
     * Auslesen der Kontaktlisten anhand der ID des Kontaktlistenerstellers
     * 
     * @param nutzerID
     * @return Vector mit sï¿½mtlichen Kontaktlisten mit der ï¿½bergebenen NutzerID
     * @throws IllegalArgumentException
     */
    public Vector<Kontaktliste> findKontaktlisteByNutzerID(int nutzerID) throws IllegalArgumentException;

	/**
	 * 
	 * @param i
	 * @return Vector mit sÃ¤mtlichen Kontakten einer ausgewÃ¤hlten Kontaktliste
	 * @throws IllegalArgumentException
	 */

    public Vector<Integer> findAllKontakteFromKontaktliste(int i) throws IllegalArgumentException;
    
   /**
    *
    * 
    * @param kontaktlisteID die ID der Kontaktliste
    * @return Vector mit sï¿½mtlichen Kontakten einer Kontaktliste
    * @throws IllegalArgumentException
    */
    public Vector<Kontakt> getAllKontakteFromKontaktliste(int kontaktlisteID) throws IllegalArgumentException;


    
    public Vector<Eigenschaftauspraegung> insertBasicAuspraegung(String wert, int status, int kontaktID) throws IllegalArgumentException;
    
    
    /**
     * 
     * @param nutzerID die ID des Nutzers
     * @return Vector mit sï¿½mtlichen erstellten und geteilten Kontaktlisten eines Users
     * @throws IllegalArgumentException
     */
    public Vector<Kontaktliste> getAllKontaktlistenFromUser (int nutzerID) throws IllegalArgumentException;

    /**
     * Ausgabe aller Teilhaberschaften eines Nutzers
     * 
     * @param nutzerID
     * @return Vector mit allen Teilhaberschaften eines Nutzers
     */
	public Vector<Teilhaberschaft> getAllTeilhaberschaftenFromUser(int nutzerID) throws IllegalArgumentException;
    
	/**
	 * 
	 * @param kontaktlisteID
	 * @return Vector mit KontaktKontaktlisten-Objekten die ï¿½bergebene KontaktlisteID als Fremdschlï¿½ssel besitzen
	 * @throws IllegalArgumentException
	 */
	public Vector<KontaktKontaktliste> getKontaktKontaktlisteFromKontaktliste(int kontaktlisteID) throws IllegalArgumentException;

	


	/**
	 * Dient zur Erstellung einer Basis-Kontaktliste namens "Meine Kontakte", die bei der Anmeldung eines Nutzers erstellt wird.
	 * 
	 * @param bez
	 * @param status
	 * @param nutzerID
	 * @return Kontaktliste mit der Bezeichnung "Meine Kontakte"
	 * @throws IllegalArgumentException
	 */
	public Kontaktliste insertMeineKontakte(String bez, int status, int nutzerID) throws IllegalArgumentException;
	
	/**
	 * Ausgabe eines Vectors mit sï¿½mtlichen geteilten und erstellten Kontakten eines Nutzers
	 * 
	 * @param nutzerID
	 * @return Vector mit sï¿½mtlichen geteilten und erstellten Kontakten des Nutzers
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> findAllKontaktFromNutzer(int nutzerID) throws IllegalArgumentException;
	
	/**
	 * Eine Teilhaberschaft an einer Kontaktliste löschen
	 * 
	 * @param kontaktlisteID die ID der Kontaktliste, an der die Teilhaberchaft aufgelöst werden soll
	 * @throws IllegalArgumentException
	 */
	public void deleteTeilhaberschaftByKontaktlisteID(int kontaktlisteID) throws IllegalArgumentException;

	
	public Kontaktliste findBasicKontaktliste(int nutzerID) throws IllegalArgumentException;
	
	
	public Kontaktliste findKontaktliste(int nutzerID, String bez) throws IllegalArgumentException;
	
	/**
	 * Ausgabe eines Vectors mit sämtlichen Kontakten, die mit einem bestimmten Nutzer geteilt wurden.
	 * 
	 * @param nutzerID die ID des Nutzers
	 * @return Vector mit sämtlichen geteilten Kontakten eines Nutzers
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> findAllSharedKontakteVonNutzer (int nutzerID) throws IllegalArgumentException;
	
	/**
	 * Erstellen einer Teilhaberschaft zu einer Kontaktliste
	 * 
	 * @param kontaktlisteID die ID der Kontaktliste die geteilt werden soll
	 * @param teilhaberID die ID des Nutzers, mit dem die Kontaktliste geteilt wird
	 * @param nutzerID die ID des Nutzers, der die Kontaktliste teilt
	 * @return Teilhaberschaft-Objekt
	 * @throws IllegalArgumentException
	 */
	public Teilhaberschaft insertTeilhaberschaftKontaktliste (int kontaktlisteID, int teilhaberID, int nutzerID) throws IllegalArgumentException;
	
	/**
	 * Ausgabe sämtlicher Teilhaberschaften an einer Kontaktliste
	 * 
	 * @param kontaktlisteID die ID der Kontaktliste deren Teilhaberschaften gesucht werden
	 * @return Vector mit sämtlichen Teilhaberschaften an einer Kontaktliste
	 * @throws IllegalArgumentException
	 */
	public Vector<Teilhaberschaft> findTeilhaberschaftByKontaktlisteID (int kontaktlisteID) throws IllegalArgumentException;
	
	/**
	 * Vector mit sämtlichen Nutzern, die eine Teilhaberschaft an einer Kontaktliste haben
	 * 
	 * @param nutzerID die ID des Nutzers
	 * @return Vector mit Nutzern die 
	 * @throws IllegalArgumentException
	 */
	public Vector<Nutzer> findAllTeilhaberFromKontaktliste (int kontaktlisteID) throws IllegalArgumentException;
	
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