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
     * @param modifikationsdatum, das Datum an dem Eigenschaften oder Auspraegungen des Kontaktes ge�ndert wurden
     * @param status, der Status ob der Kontakt geteilt wurde oder nicht
     * @param nutzerID, Fremdschl�sselbeziehung zum Ersteller des Kontakes
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
     * Einen Kontakt l�schen
     * 
     * @param k das zu l�schende Kontakt-Objekt
     * @throws IllegalArgumentException
     */
    public void deleteKontakt(Kontakt k) throws IllegalArgumentException;
    
    /**
     * Einen Kontakt anhand seines Prim�rschl�ssel anzeigen lassen.
     * 
     * @param id der Prim�rschl�ssel des Objekts
     * @return Kontakt-Objekt mit dem �bergebenen Prim�rschl�ssel
     * @throws IllegalArgumentException
     */
    public Kontakt findKontaktByID(int id) throws IllegalArgumentException;
    
    /**
     * Einen Kontakt anhand seines Namens anzeigen lassen.
     * 
     * @param name, der Name des Kontakts
     * @return Kontakt-Objekt mit dem �bergebenen Namen
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
     * �berschreiben eines <code>Kontakt</code>-Objekts.
     * 
     * @param k das zu bearbeitende Kontakt-Objekt
     * @return das bearbeitete Kontakt-Objekt
     * @throws IllegalArgumentException
     */
    public Kontakt updateKontakt(Kontakt k) throws IllegalArgumentException;
    
    /**
     * L�schen einer Person.
     * 
     * @param p das zu l�schende Personen-Objekt
     * @throws IllegalArgumentException
     */
    public void deletePerson(Person p) throws IllegalArgumentException;
    
    /**
     * Eine Person anhand der ID auslesen.
     * 
     * @param ID, der Prim�rschl�ssel
     * @throws IllegalArgumentException
     */
    public Person findPersonByID(int ID) throws IllegalArgumentException;
    
    /**
     * L�schen eines Nutzers.
     * 
     * @param n das zu l�schende Nutzer-Objekt
     * @throws IllegalArgumentException
     */
    public void deleteNutzer(Nutzer n) throws IllegalArgumentException;
    
    /**
     * �berschreiben eines Nutzer-Objekts.
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
     * @param id der Prim�rschl�ssel des Nutzer-Objekts
     * @return Nutzer-Objekt
     * @throws IllegalArgumentException
     */
    public Nutzer findNutzerByID(int id) throws IllegalArgumentException;
    
    /**
     * Alle Nutzer auslesen.
     * 
     * @return Vector s�mtlicher Nutzer
     * @throws IllegalArgumentException
     */
    public Vector<Nutzer> findAllNutzer() throws IllegalArgumentException;
    
    /**
     * Eine Kontaktliste anhand der ID auslesen.
     * 
     * @param id der Prim�rschl�ssel des Kontaktlisten-Objekts
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
    * @param nutzerID, der Fremdschl�ssel stellt die Beziehung zum Ersteller dar
    * @return Kontaktlisten-Objekt
    * @throws IllegalArgumentException
    */
    public Kontaktliste insertKontaktliste(String bez, int status, int nutzerID ) throws IllegalArgumentException;
    
    /**
     * Eine Kontaktliste �berschreiben.
     * 
     * @param k das zu bearbeitende Kontaktlisten-Objekt
     * @return 
     * @throws IllegalArgumentException
     */
    public Kontaktliste updateKontaktliste(Kontaktliste k) throws IllegalArgumentException;
    
    /**
     * Eine Kontaktliste  l�schen.
     * 
     * @param k das zu l�schende Kontaktlisten-Objekt
     * @throws IllegalArgumentException
     */
    public void deleteKontaktliste(Kontaktliste k) throws IllegalArgumentException;
    
    /**
     * Alle Kontaktlisten auslesen.
     * 
     * @return Vector s�mtlicher Kontaktlisten
     * @throws IllegalArgumentException
     */
    public Vector<Kontaktliste> findKontaktlisteAll() throws IllegalArgumentException;
    
    /**
     * Eine Eigenschaft anhand der ID auslesen.
     * 
     * @param id, Prim�rschl�ssel der Eigenschaft
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
     * Eine Eigenschaft �berschreiben.
     * 
     * @param e das Eigenschaft-Objekt
     * @return
     * @throws IllegalArgumentException
     */
    public Eigenschaft updateEigenschaft(Eigenschaft e) throws IllegalArgumentException;
    
    /**
     * Eine Eigenschaft l�schen.
     * 
     * @param e das zu l�schende Eigenschaft-Objekt
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
 * @param eigenschaftsID, Fremschl�sselbeziehung zur Eigenschaft
 * @param kontaktID, Fremdschl�sselbeziehung zum Kontakt auf den sich die Eigenschaft bezieht
 * @return Eigenschaft-Objekt
 * @throws IllegalArgumentException
 */
    public Eigenschaftauspraegung insertAuspraegung(String wert, int status, int eigenschaftsID, int kontaktID) throws IllegalArgumentException;
    
    /**
     * Eine Eigenschaftsauspraegung �berschreiben.
     * 
     * @param ea
     * @return 
     * @throws IllegalArgumentException
     */
    public Eigenschaftauspraegung updateAuspraegung(Eigenschaftauspraegung ea) throws IllegalArgumentException;
    
    /**
     * Eine Eigenschaftsauspraegung l�schen.
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
     * @param id der Prim�rschl�ssel der Auspraegung
     * @return Eigenschaftsauspraegung-Objekt
     * @throws IllegalArgumentException
     */
    public Eigenschaftauspraegung getAuspraegungByID(int id) throws IllegalArgumentException;
    

    
    /**
     * Alle Eigenschaftsauspraegungen eines Kontaktes auslesen
     * 
     * @param kontaktID
     * @return Vector mit s�mtlichen Eigenschaftsauspraegungen mit der uebergebenen KontaktID
     * @throws IllegalArgumentException
     */
    public Vector<Eigenschaftauspraegung> findEigenschaftauspraegungByKontaktID(int kontaktID ) throws IllegalArgumentException;

    public Vector<Eigenschaftauspraegung> getAuspraegungByKontaktID(int id) throws IllegalArgumentException;
    
    public Vector<EigenschaftAuspraegungHybrid> findHybrid(Person pers) throws IllegalArgumentException;
    
    public void deleteEigenschaftUndAuspraegung(EigenschaftAuspraegungHybrid ea) throws IllegalArgumentException;

    
    /**
     * Eine Teilhaberschaft anhand der ID auslesen.
     * 
     * @param id der Prim�rschl�ssel der Teilhaberschaft
     * @return Teilhaberschaft-Objekt
     * @throws IllegalArgumentException
     */
    public Teilhaberschaft findByID(int id) throws IllegalArgumentException;

    /**
     * Eine Teilhaberschaft anlegen
     * 
     * @param kontaktID, Fremdschl�sselbeziehung zum Kontakt
     * @param kontaktListeID, Fremdschl�sselbeziehung zur Kontaktliste
     * @param eigenschaftsauspraegungID, Fremdschl�sselbeziehung zur Eingenschaftsauspraegung
     * @param teilhaberID, Fremdschl�sselbeziehung zum Teilhaber
     * @return Teilhaberschaft-Objekt
     * @throws IllegalArgumentException
     */
    public Teilhaberschaft insertTeilhaberschaft(int kontaktID, int kontaktListeID, int eigenschaftsauspraegungID, int teilhaberID) throws IllegalArgumentException;
    
    /**
     * Eine Teilhaberschaft l�schen.
     * 
     * @param t das zu l�schende Teilhaberschaft-Objekt
     * @throws IllegalArgumentException
     */
    public void deleteTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException;
    
    /**
     * Eine Teilhaberschaft an einem Kontakt l�schen.
     * 
     * @param t das zur l�schende Teilhaber-Objekt
     * @throws IllegalArgumentException
     */
    public void deleteKontaktFromTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException; 
    
    /**
     * Eine Teilhaberschaft an einer Kontaktliste l�schen.
     * 
     * @param t das zu l�schende Teilhaberschaft-Objekt
     * @throws IllegalArgumentException
     */
    public void deleteKontaktlisteFromTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException;
    
    /**
     * Eine Teilhaberschaft an einer Eigenschaftsauspraegung l�schen.
     * 
     * @param t das zu l�schende Teilhaberschaftobjekt
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
     * @param k das zu l�schende KontaktKontaktliste-Objekt
     * @throws IllegalArgumentException
     */
    public void deleteKontaktKontaktliste(KontaktKontaktliste k) throws IllegalArgumentException;
    
    /**
     * Auslesen von Kontakten anhand der ID des Kontakterstellers.
     * 
     * @param nutzerID
     * @return Vector mit s�mtlichen Kontakten mit der �bergebenen NutzerID
     * @throws IllegalArgumentException
     */
    public Vector<Kontakt> findKontaktByNutzerID(int nutzerID) throws IllegalArgumentException;
    
    /**
     * Auslesen der Kontaktlisten anhand der ID des Kontaktlistenerstellers
     * 
     * @param nutzerID
     * @return Vector mit s�mtlichen Kontaktlisten mit der �bergebenen NutzerID
     * @throws IllegalArgumentException
     */
    public Vector<Kontaktliste> findKontaktlisteByNutzerID(int nutzerID) throws IllegalArgumentException;

	/**
	 * 
	 * @param i
	 * @return Vector mit sämtlichen Kontakten einer ausgewählten Kontaktliste
	 * @throws IllegalArgumentException
	 */
    
    public Vector<Integer> findAllKontakteFromKontaktliste(int i) throws IllegalArgumentException;
    

    
    
    
    
    
    
    
    
  
    
    
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