package de.hdm.itprojektgruppe4.shared;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojektgruppe4.client.EigenschaftAuspraegungWrapper;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.bo.Person;
import de.hdm.itprojektgruppe4.shared.bo.Teilhaberschaft;
import de.hdm.itprojektgruppe4.shared.bo.KontaktKontaktliste;

/**
 * <p>
 * Synchrone Schnittstelle für eine RPC-fähige Klasse zur Verwaltung von Banken.
 * </p>
 * <p>
 * <b>Frage:</b> Warum werden diese Methoden nicht als Teil der Klassen
 * {@link Bank}, {@link Customer}, {@link Account} oder {@link Transaction}
 * implementiert?<br>
 * <b>Antwort:</b> Z.B. das Löschen eines Kunden erfordert Kenntnisse über die
 * Verflechtung eines Kunden mit Konto-Objekten. Um die Klasse <code>Bank</code>
 * bzw. <code>Customer</code> nicht zu stark an andere Klassen zu koppeln, wird
 * das Wissen darüber, wie einzelne "Daten"-Objekte koexistieren, in der
 * vorliegenden Klasse gekapselt.
 * </p>
 * <p>
 * Natürlich existieren Frameworks wie etwa Hibernate, die dies auf eine andere
 * Weise realisieren. Wir haben jedoch ganz bewusst auf deren Nutzung
 * verzichtet, um in diesem kleinen Demoprojekt den Blick auf das Wesentliche
 * nicht unnötig zu verstellen.
 * </p>
 * <p>
 * <code>@RemoteServiceRelativePath("bankadministration")</code> ist bei der
 * Adressierung des aus der zugehörigen Impl-Klasse entstehenden
 * Servlet-Kompilats behilflich. Es gibt im Wesentlichen einen Teil der URL des
 * Servlets an.
 * </p>
 * 
 * @author Thies
 */

@RemoteServiceRelativePath("kontaktmanager")
public interface KontaktAdministration extends RemoteService {

	/**
	 * Initialisierung des Objekts. Wird direkt nach der Instantiierung
	 * aufgerufen.
	 * 
	 * @throws IllegalArgumentException
	 */
	public void init() throws IllegalArgumentException;

	/**
	 * Einen Kontakt anlegen
	 * 
	 * @param name,
	 *            der Name des Kontakts
	 * @param erzeugungsdatum,
	 *            das Erzeugungsdatum des Kontakts
	 * @param modifikationsdatum,
	 *            das Datum an dem Eigenschaften oder Auspraegungen des
	 *            Kontaktes geändert wurden
	 * @param status,
	 *            der Status ob der Kontakt geteilt wurde oder nicht
	 * @param nutzerID,
	 *            Fremdschlüsselbeziehung zum Ersteller des Kontakes
	 * @return Kontakt-Objekt
	 * @throws IllegalArgumentException
	 */
	public Kontakt insertKontakt(String name, Date erzeugungsdatum, Date modifikationsdatum, int status, int nutzerID)
			throws IllegalArgumentException;

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
	 * @param k
	 *            das zu l�schende Kontakt-Objekt
	 * @throws IllegalArgumentException
	 */
	public void deleteKontakt(Kontakt k) throws IllegalArgumentException;

	/**
	 * Einen Kontakt anhand seines Prim�rschl�ssel anzeigen lassen.
	 * 
	 * @param id
	 *            der Prim�rschl�ssel des Objekts
	 * @return Kontakt-Objekt mit dem �bergebenen Prim�rschl�ssel
	 * @throws IllegalArgumentException
	 */
	public Kontakt findKontaktByID(int id) throws IllegalArgumentException;

	/**
	 * Einen Kontakt anhand seines Namens anzeigen lassen.
	 * 
	 * @param name,
	 *            der Name des Kontakts
	 * @return Kontakt-Objekt mit dem �bergebenen Namen
	 * @throws IllegalArgumentException
	 */
	public Kontakt findKontaktByName(String name) throws IllegalArgumentException;

	/**
	 * Einen Kontakt anhand seiner KontaktID in der Auspraegung anzeigen lassen.
	 * 
	 * @param id,
	 * 
	 * @return Kontakt-Objekt mit der übergebenen kontaktID
	 * @throws IllegalArgumentException
	 */
	public Kontakt findKontaktByAuspraegungID(int id) throws IllegalArgumentException;

	/**
	 * Einen Kontaktnamen anhand seiner KontaktID in der Auspraegung anzeigen
	 * lassen.
	 * 
	 * @param id,
	 * 
	 * @return Kontakt-Objekt mit der übergebenen kontaktID
	 * @throws IllegalArgumentException
	 */
	public String findEinenKontaktByAuspraegungID(int id) throws IllegalArgumentException;

	/**
	 * Alle Kontakte auslesen und in einem Vector ausgeben lassen.
	 * 
	 * @return Vector aller Kontakte
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> findAllKontakte() throws IllegalArgumentException;

	/**
	 * Einen Kontakt anhand des Namens und des Nutzers finden.
	 * 
	 * @param kontakt
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> findKontaktByNameAndNutzerID(Kontakt kontakt) throws IllegalArgumentException;

	/**
	 * �berschreiben eines <code>Kontakt</code>-Objekts.
	 * 
	 * @param k
	 *            das zu bearbeitende Kontakt-Objekt
	 * @return das bearbeitete Kontakt-Objekt
	 * @throws IllegalArgumentException
	 */
	public Kontakt updateKontakt(Kontakt k) throws IllegalArgumentException;

	/**
	 * L�schen einer Person.
	 * 
	 * @param p
	 *            das zu löschende Personen-Objekt
	 * @throws IllegalArgumentException
	 */
	public void deletePerson(Person p) throws IllegalArgumentException;

	/**
	 * Eine Person anhand der ID auslesen.
	 * 
	 * @param ID,
	 *            der Primärschlüssel
	 * @throws IllegalArgumentException
	 */
	public Person findPersonByID(int ID) throws IllegalArgumentException;

	/**
	 * Löschen eines Nutzers.
	 * 
	 * @param n
	 *            das zu löschende Nutzer-Objekt
	 * @throws IllegalArgumentException
	 */
	public void deleteNutzer(Nutzer n) throws IllegalArgumentException;

	/**
	 * �berschreiben eines Nutzer-Objekts.
	 * 
	 * @param n
	 *            das zu bearbeitende Nutzer-Objekt
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
	 * @param id
	 *            der Prim�rschl�ssel des Nutzer-Objekts
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
	 * @param id
	 *            der Primärschl�ssel des Kontaktlisten-Objekts
	 * @return Kontaktlisten-Objekt
	 * @throws IllegalArgumentException
	 */
	public Kontaktliste findKontaktlisteByID(int id) throws IllegalArgumentException;

	/**
	 * Eine Kontaktliste anhand der Bezeichnung auslesen.
	 * 
	 * @param bezeichnung,
	 *            die Bezeichnung der Kontaktliste
	 * @return Kontaktlisten-Objekt
	 * @throws IllegalArgumentException
	 */
	public Kontaktliste findKontaktlisteByBezeichnung(String bezeichnung) throws IllegalArgumentException;

	/**
	 * 
	 * @param bez,
	 *            die Bezeichnung der Kontaktliste
	 * @param status,
	 *            der Status, der anzeigt ob die Kontaktliste geteilt wurde oder
	 *            nicht
	 * @param nutzerID,
	 *            der Fremdschl�ssel stellt die Beziehung zum Ersteller dar
	 * @return Kontaktlisten-Objekt
	 * @throws IllegalArgumentException
	 */
	public Kontaktliste insertKontaktliste(String bez, int status, int nutzerID) throws IllegalArgumentException;

	/**
	 * Eine Kontaktliste �berschreiben.
	 * 
	 * @param k
	 *            das zu bearbeitende Kontaktlisten-Objekt
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Kontaktliste updateKontaktliste(Kontaktliste k) throws IllegalArgumentException;

	/**
	 * Eine Kontaktliste l�schen.
	 * 
	 * @param k
	 *            das zu l�schende Kontaktlisten-Objekt
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
	 * Eine Eigenschaft anhand der Bezeichnung auslesen.
	 * 
	 * @param bez
	 *            Bezeichnung der Eigenschaft
	 * @return Eigenschaft-Objekt
	 * @throws IllegalArgumentException
	 */

	public Vector<Eigenschaft> getEigenschaftByBezeichnung(String bez) throws IllegalArgumentException;

	/**
	 * Eine Eigenschaft anhand der ID auslesen.
	 * 
	 * @param id,
	 *            Prim�rschl�ssel der Eigenschaft
	 * @return Eigenschaft-Objekt
	 * @throws IllegalArgumentException
	 */
	public Eigenschaft getEigenschaftByID(int id) throws IllegalArgumentException;

	/**
	 * Eine Eigenschaft anlegen.
	 * 
	 * @param bez,
	 *            die Bezeichnung der Eigenschaft
	 * @param status,
	 *            Status der anzeigt, ob die Eigenschaft geteilt wurde oder
	 *            nicht
	 * @return Eigenschaft Objekt
	 * @throws IllegalArgumentException
	 */
	public Eigenschaft insertEigenschaft(String bez, int status) throws IllegalArgumentException;

	/**
	 * Eine Eigenschaft �berschreiben.
	 * 
	 * @param e
	 *            das Eigenschaft-Objekt
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Eigenschaft updateEigenschaft(Eigenschaft e) throws IllegalArgumentException;

	/**
	 * Eine Eigenschaft l�schen.
	 * 
	 * @param e
	 *            das zu l�schende Eigenschaft-Objekt
	 * @throws IllegalArgumentException
	 */
	public void deleteEigenschaft(Eigenschaft e) throws IllegalArgumentException;

	/**
	 * Alle Objekte vom Typ Eigenschaft ausgeben
	 * 
	 * @return
	 * @throws IllegalArgumentException
	 */

	public Vector<Eigenschaft> findAllEigenschaft() throws IllegalArgumentException;

	/**
	 * Auslesen aller Eigenschaftausprägung-Objekte
	 * 
	 * @return Vector mit sämtlichen Eingeschaftausprägung-Objekten
	 * @throws IllegalArgumentException
	 */
	public Vector<Eigenschaftauspraegung> findAllEigenschaftauspraegung() throws IllegalArgumentException;

	/**
	 * Alle Eigenschaften die zu einem bestimmten Kontakt gehören auslesen
	 * 
	 * @param id
	 *            die ID des Kontaktes, dessen Eigenschaften ausgelesen werden
	 *            sollen
	 * @return Vector mit sämtlichen Eigenschaften eines Kontaktes
	 */
	public Vector<Eigenschaft> getEigenschaftbyKontaktID(int id);

	/**
	 * 
	 * @param wert,
	 *            die Auspraegung der Eigenschaft
	 * @param status,
	 *            zeigt an ob die Auspraegung geteilt wurde oder nicht
	 * @param eigenschaftsID,
	 *            Fremschl�sselbeziehung zur Eigenschaft
	 * @param kontaktID,
	 *            Fremdschl�sselbeziehung zum Kontakt auf den sich die
	 *            Eigenschaft bezieht
	 * @return Eigenschaft-Objekt
	 * @throws IllegalArgumentException
	 */
	public Eigenschaftauspraegung insertAuspraegung(String wert, int status, int eigenschaftsID, int kontaktID)
			throws IllegalArgumentException;

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
	 * @param ea
	 *            das Eigenschaftsauspraegung-Objekt
	 * @throws IllegalArgumentException
	 */
	public void deleteAuspraegung(Eigenschaftauspraegung ea) throws IllegalArgumentException;

	/**
	 * Eine Eigenschaftsauspraegung anhand des Wertes auslesen.
	 * 
	 * @param wert,
	 *            der die Auspraegung beschreibt
	 * @return Eigenschafts-Objekt mit gesuchtem Wert
	 * @throws IllegalArgumentException
	 */
	public Vector<EigenschaftAuspraegungWrapper> getAuspraegungByWert(String wert) throws IllegalArgumentException;

	/**
	 * Eine Eigenschaftsauspraegung anhand der ID auslesen.
	 * 
	 * @param id
	 *            der Prim�rschl�ssel der Auspraegung
	 * @return Eigenschaftsauspraegung-Objekt
	 * @throws IllegalArgumentException
	 */
	public Eigenschaftauspraegung getAuspraegungByID(int id) throws IllegalArgumentException;

	/**
	 * Alle Eigenschaftsauspraegungen eines Kontaktes auslesen
	 * 
	 * @param kontaktID
	 * @return Vector mit sämtlichen Eigenschaftsauspraegungen mit der
	 *         uebergebenen KontaktID
	 * @throws IllegalArgumentException
	 */
	public Vector<Eigenschaftauspraegung> findEigenschaftauspraegungByKontaktID(int kontaktID)
			throws IllegalArgumentException;

	/**
	 * Es werden Ausprägungen eines bestimmten Kontaktes selektiert.
	 * 
	 * @param id
	 * @return
	 * @throws IllegalArgumentException
	 */

	public Vector<Eigenschaftauspraegung> getAuspraegungByKontaktID(int id) throws IllegalArgumentException;

	/**
	 * Ein Objekt vom Typ Eigenschaft-Ausprägung Wrapper anahnd von einem Perosn
	 * Objekt, der Elternklasse von Kontakt, finden.
	 * 
	 * @param pers
	 * @return Objekt vom Typ Eigenschaft-Ausprägung Wrapper
	 * @throws IllegalArgumentException
	 */

	public Vector<EigenschaftAuspraegungWrapper> findHybrid(Person pers) throws IllegalArgumentException;

	/**
	 * Ein Objekt vom Typ Ausprägung löschen.
	 * 
	 * @param ea
	 * @throws IllegalArgumentException
	 */

	public void deleteEigenschaftUndAuspraegung(EigenschaftAuspraegungWrapper ea) throws IllegalArgumentException;

	/**
	 * Eine Teilhaberschaft anhand der ID auslesen.
	 * 
	 * @param id
	 *            der Primärschlüssel der Teilhaberschaft
	 * @return Teilhaberschaft-Objekt
	 * @throws IllegalArgumentException
	 */
	public Teilhaberschaft findByID(int id) throws IllegalArgumentException;

	/**
	 * Eine Teilhaberschaft anlegen
	 * 
	 * @param kontaktID,
	 *            Fremdschl�sselbeziehung zum Kontakt
	 * @param kontaktListeID,
	 *            Fremdschl�sselbeziehung zur Kontaktliste
	 * @param eigenschaftsauspraegungID,
	 *            Fremdschl�sselbeziehung zur Eingenschaftsauspraegung
	 * @param teilhaberID,
	 *            Fremdschl�sselbeziehung zum Teilhaber
	 * @return Teilhaberschaft-Objekt
	 * @throws IllegalArgumentException
	 */
	public Teilhaberschaft insertTeilhaberschaft(int kontaktListeID, int kontaktID, int eigenschaftsauspraegungID,
			int teilhaberID, int nutzerID) throws IllegalArgumentException;

	/**
	 * Eine Teilhaberschaft für ein geteilten Kontakt anlegen
	 * 
	 * @param kontaktID
	 * @param eigenschaftsauspraegungID
	 * @param teilhaberID
	 * @return
	 */
	public Teilhaberschaft insertTeilhaberschaftKontakt(int kontaktID, int teilhaberID, int nutzerID);

	/**
	 * Es werden allen Ausprägungen des übergebenen Kontaktes an den Nutzer, der
	 * anahnd seiner Email gesucht wird, geteilt. Dazu werden alle Ausprägungen
	 * des Kontaktes gesucht und der Status wird auf 1 (geteilt) gesetzt. Danach
	 * wird die Kontaktliste "Mit mir geteitlte Kontakte" des entsprechenden
	 * Teilhabers gesucht und der geteitle Kontakt wird dieser Liste
	 * hinzugefügt. Es wird der Status des Kontaktes ebenfalls auf 1 gesetzt. Im
	 * letzten Schritt werden in der Tabelle Teilhaberschaft mehrere Tupel für
	 * alle Ausprägungen und den Kontakt angelegt.
	 * 
	 * @param kon
	 * @param selectedValue
	 * @param id
	 * @return
	 * @throws IllegalArgumentException
	 */

	public int insertTeilhaberschaftAuspraegungenKontakt(Kontakt kon, String selectedValue, int id)
			throws IllegalArgumentException;

	/**
	 * * Es werden allen ausgewählten Ausprägungen des übergebenen Kontaktes an
	 * den Nutzer, der anahnd seiner Email gesucht wird, geteilt. Dazu werden
	 * alle ausgewählten Ausprägungen des Kontaktes gesucht und der Status wird
	 * auf 1 (geteilt) gesetzt. Danach wird die Kontaktliste "Mit mir geteitlte
	 * Kontakte" des entsprechenden Teilhabers gesucht und der geteitle Kontakt
	 * wird dieser Liste hinzugefügt. Es wird der Status des Kontaktes ebenfalls
	 * auf 1 gesetzt. Im letzten Schritt werden in der Tabelle Teilhaberschaft
	 * mehrere Tupel für alle ausgewählten Ausprägungen und den Kontakt
	 * angelegt.
	 * 
	 * @param kon
	 * @param eaw
	 * @param selectedValue
	 * @param id
	 * @return
	 */

	public int insertTeilhaberschaftAusgewaehlteAuspraegungenKontakt(Kontakt kon,
			Vector<EigenschaftAuspraegungWrapper> eaw, String selectedValue, int id);

	/**
	 * Eine Teilhaberschaft l�schen.
	 * 
	 * @param t
	 *            das zu l�schende Teilhaberschaft-Objekt
	 * @throws IllegalArgumentException
	 */
	public void deleteTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException;

	/**
	 * Eine Teilhaberschaft an einem Kontakt l�schen.
	 * 
	 * @param t
	 *            das zur l�schende Teilhaber-Objekt
	 * @throws IllegalArgumentException
	 */
	public void deleteKontaktFromTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException;

	/**
	 * Eine Teilhaberschaft an einer Kontaktliste l�schen.
	 * 
	 * @param t
	 *            das zu l�schende Teilhaberschaft-Objekt
	 * @throws IllegalArgumentException
	 */
	public void deleteKontaktlisteFromTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException;

	/**
	 * Objekte vom Typ Teilhaberschaft anhand bestimmter Ausprägungen und
	 * Teilhaber löschen.
	 * 
	 * @param ea
	 * @param n
	 * @throws IllegalArgumentException
	 */

	public void deleteEigenschaftsauspraegungFromTeilhaberschaft(Eigenschaftauspraegung ea, Nutzer n)
			throws IllegalArgumentException;

	/**
	 * Auslesen eines Vectors mit sämtlichen Teilhabern an einer
	 * Eigenschaftsausprägung
	 * 
	 * @param auspraegungID
	 *            die ID der Eigenschaftsausprägung
	 * @return Vector mit Nutzern, die Teilhaber an Ausprägung sind
	 * @throws IllegalArgumentException
	 */
	public Vector<Nutzer> getAllTeilhaberfromAuspraegung(int auspraegungID) throws IllegalArgumentException;

	/**
	 * Auslesen aller Teilhaber an einem Kontakt
	 * 
	 * @param kontaktID
	 *            die ID des Kontaktes
	 * @return Vector mit sämtlichen Nutzer, die Teilhaber an einem Kontakt sind
	 * @throws IllegalArgumentException
	 */
	public Vector<Nutzer> getAllTeilhaberFromKontakt(int kontaktID) throws IllegalArgumentException;

	/**
	 * Anlegen eines Objekts der Klasse KontaktKontaktliste um eine Beziehung
	 * zwischen Kontakt und Kontaktliste in der Datenbank herzustellen.
	 * 
	 * @param k
	 * @return KontaktKontaktliste-Objekt
	 */
	public KontaktKontaktliste insertKontaktKontaktliste(int kontaktID, int kontaktlisteID)
			throws IllegalArgumentException;

	/**
	 * Aufhebung der Beziehung zwischen Kontakt und Kontaktliste.
	 * 
	 * @param k
	 *            das zu l�schende KontaktKontaktliste-Objekt
	 * @throws IllegalArgumentException
	 */
	public void deleteKontaktKontaktliste(KontaktKontaktliste k) throws IllegalArgumentException;

	/**
	 * Ein Objekt vom Typ Kontakt anhand der NutzerID selektieren.
	 * 
	 * @param nutzerID
	 * @return Kontakt Objekt
	 */

	public List<Kontakt> findKontaktByNutzerID(int nutzerID);

	/**
	 * Auslesen der Kontaktlisten anhand der ID des Kontaktlistenerstellers
	 * 
	 * @param nutzerID
	 * @return Vector mit s�mtlichen Kontaktlisten mit der �bergebenen NutzerID
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontaktliste> findKontaktlisteByNutzerID(int nutzerID) throws IllegalArgumentException;

	/**
	 * @param kontaktlisteID
	 *            die ID der Kontaktliste
	 * @return Vector mit sämtlichen Kontakten einer Kontaktliste
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> getAllKontakteFromKontaktliste(int kontaktlisteID) throws IllegalArgumentException;

	/**
	 * Erstellung der Eigenschaftauspraegungen, die standardmäßig bei
	 * Kontakterstellung angelegt werden
	 * 
	 * @param wert
	 *            der Wert der Ausprägung
	 * @param status
	 *            Status, der anzeigt ob die Ausprägung geteilt wurde
	 * @param kontaktID
	 *            die ID des Kontakts zu der die Ausprägung gehört
	 * @return das erstellte Eigenschaftausprägung-Objekt
	 * @throws IllegalArgumentException
	 */
	public Vector<Eigenschaftauspraegung> insertBasicAuspraegung(String wert, int status, int kontaktID)
			throws IllegalArgumentException;

	/**
	 * 
	 * @param nutzerID
	 *            die ID des Nutzers
	 * @return Vector mit s�mtlichen erstellten und geteilten Kontaktlisten
	 *         eines Users
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontaktliste> getAllKontaktlistenFromUser(int nutzerID) throws IllegalArgumentException;

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
	 * @return Vector mit KontaktKontaktlisten-Objekten die �bergebene
	 *         KontaktlisteID als Fremdschl�ssel besitzen
	 * @throws IllegalArgumentException
	 */
	public Vector<KontaktKontaktliste> getKontaktKontaktlisteFromKontaktliste(int kontaktlisteID)
			throws IllegalArgumentException;

	/**
	 * Dient zur Erstellung einer Basis-Kontaktliste namens "Meine Kontakte",
	 * die bei der Anmeldung eines Nutzers erstellt wird.
	 * 
	 * @param bez
	 * @param status
	 * @param nutzerID
	 * @return Kontaktliste mit der Bezeichnung "Meine Kontakte"
	 * @throws IllegalArgumentException
	 */
	public Kontaktliste insertMeineKontakte(String bez, int status, int nutzerID) throws IllegalArgumentException;

	/**
	 * Ausgabe eines Vectors mit s�mtlichen geteilten und erstellten Kontakten
	 * eines Nutzers
	 * 
	 * @param nutzerID
	 * @return Vector mit s�mtlichen geteilten und erstellten Kontakten des
	 *         Nutzers
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> findAllKontaktFromNutzer(int nutzerID) throws IllegalArgumentException;

	/**
	 * Eine Teilhaberschaft an einer Kontaktliste l�schen
	 * 
	 * @param kontaktlisteID
	 *            die ID der Kontaktliste, an der die Teilhaberchaft aufgel�st
	 *            werden soll
	 * @throws IllegalArgumentException
	 */
	public void deleteTeilhaberschaftByKontaktlisteID(int kontaktlisteID) throws IllegalArgumentException;

	/**
	 * Die Basis-Kontaktliste "Meine Kontakte" anhand der NutzerID finden.
	 * 
	 * @param nutzerID
	 * @return Objekt vom Typ Kontaktliste
	 * @throws IllegalArgumentException
	 */

	public Kontaktliste findBasicKontaktliste(int nutzerID) throws IllegalArgumentException;

	/**
	 * Eine Kontaktliste anhand der Bezeichnung und der NutzerID selektieren.
	 * 
	 * @param nutzerID
	 * @param bez
	 * @return Objekt vom Typ Kontaktliste
	 * @throws IllegalArgumentException
	 */

	public Kontaktliste findKontaktliste(int nutzerID, String bez) throws IllegalArgumentException;

	/**
	 * Ausgabe eines Vectors mit s�mtlichen Kontakten, die mit einem bestimmten
	 * Nutzer geteilt wurden.
	 * 
	 * @param nutzerID
	 *            die ID des Nutzers
	 * @return Vector mit s�mtlichen geteilten Kontakten eines Nutzers
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> findAllSharedKontakteVonNutzer(int nutzerID) throws IllegalArgumentException;

	/**
	 * Ein Objekt vom Typ Teilhaberschaft für eine Kontaktliste anlegen.
	 * 
	 * @param kontaktlisteID
	 * @param email
	 * @param nutzerID
	 * @return
	 */

	public Teilhaberschaft insertTeilhaberschaftKontaktliste(int kontaktlisteID, String email, int nutzerID);

	/**
	 * Ausgabe s�mtlicher Teilhaberschaften an einer Kontaktliste
	 * 
	 * @param kontaktlisteID
	 *            die ID der Kontaktliste deren Teilhaberschaften gesucht werden
	 * @return Vector mit s�mtlichen Teilhaberschaften an einer Kontaktliste
	 * @throws IllegalArgumentException
	 */
	public Vector<Teilhaberschaft> findTeilhaberschaftByKontaktlisteID(int kontaktlisteID)
			throws IllegalArgumentException;

	/**
	 * Vector mit s�mtlichen Nutzern, die eine Teilhaberschaft an einer
	 * Kontaktliste haben
	 * 
	 * @param nutzerID
	 *            die ID des Nutzers
	 * @return Vector mit Nutzern die
	 * @throws IllegalArgumentException
	 */
	public Vector<Nutzer> findAllTeilhaberFromKontaktliste(int kontaktlisteID) throws IllegalArgumentException;

	/**
	 * L�schen eines Kontaktes aus einer Kontaktliste
	 * 
	 * @param kontaktID
	 *            die ID des zu l�schenden Kontaktes
	 * @throws IllegalArgumentException
	 */
	public void deleteKontaktKontaktlisteByKontaktID(int kontaktID) throws IllegalArgumentException;

	/**
	 * L�schen einer Teilhaberschaft anhand der TeilhaberID
	 * 
	 * @param teilhaberID
	 *            die ID des Teilhabers dessen ID gel�scht werden soll
	 * @throws IllegalArgumentException
	 */
	public void deleteTeilhaberschaftByTeilhaberID(int teilhaberID) throws IllegalArgumentException;

	/**
	 * Objekte vom Typ Kontaktliste mit Ausnamhe der Basis Listen "Meine
	 * Kontakte" und "Meine geteilten Kontakte" anahnd eines Nutzer finden.
	 * 
	 * @param nutzerID
	 * @return Objekte vom Typ Kontaktliste
	 * @throws IllegalArgumentException
	 */

	public Vector<Kontaktliste> findKontaktlisteByNutzerIDexceptBasicList(int nutzerID) throws IllegalArgumentException;

	/**
	 * Auslesen einer Teilhaberschaft anhand einer TeilhaberID und einer
	 * KontaktlisteID
	 * 
	 * @param teilhaberID
	 *            die ID des Teilhabers
	 * @return gesuchte Teilhaberschaft
	 * @throws IllegalArgumentException
	 */
	public Teilhaberschaft findTeilhaberschaftByTeilhaberID(int teilhaberID, int kontaktlisteID)
			throws IllegalArgumentException;

	/**
	 * Auslesen eines Objektes vom Typ Eigenschaft anhand der Bezeichnung
	 * 
	 * @param bez
	 * @return ein Objekt vom Typ Eigenschaft
	 * @throws IllegalArgumentException
	 */

	public Eigenschaft findEigByBezeichnung(String bez) throws IllegalArgumentException;

	/**
	 * Auslesen aller Nutzer, mit den der angemeldete Nutzer eine Kontaktliste
	 * geteilt hat
	 * 
	 * @param nutzerID
	 *            die ID des angemeldeten Nutzers
	 * @param kontaktlisteID
	 *            die ID der Kontaktliste, deren Teilhaberschaften verwaltet
	 *            werden sollen
	 * @return Vector mit s�mtlichen Nutzern, mit denen der angemeldete Nutzer
	 *         die Kontaktliste geteilt hat
	 * @throws IllegalArgumentException
	 */
	public Vector<Nutzer> findSharedWithNutzer(int nutzerID, int kontaktlisteID) throws IllegalArgumentException;

	/**
	 * Auslesen aller Teilhaberschaften, mit der �bergebenen nutzerID und
	 * kontaktlisteID
	 * 
	 * @param nutzerID
	 *            die ID des angemeldeten Nutzers
	 * @param kontaktlisteID
	 *            die ID der gew�hlten kontaktlisteID
	 * @return Vector mit sämtlichen Teilhaberschaften, mit den uebergebenen
	 *         Parametern
	 * @throws IllegalArgumentException
	 */
	public Vector<Teilhaberschaft> findTeilhaberschaftByNutzerIDKontaktlisteID(int nutzerID, int kontaktlisteID)
			throws IllegalArgumentException;

	/**
	 * Alle Objekte vom Typ Teilhaberschaft anhand der auspraegungID finden
	 * 
	 * @param auspraegungID
	 * @return Vector mit allen Teilhaberschaften zu dieser Ausprägung
	 */

	public Vector<Teilhaberschaft> findTeilhaberschaftByAuspraegungID(int auspraegungID);

	/**
	 * Alle Objekte vom Typ Ausprägungen anhand der Kontakt und der Nutzer ID
	 * selektieren. Es werden die Ausprägungen gefunden welche mit diesem Nutzer
	 * von diesem Kontatk geteitl wurden.
	 * 
	 * @param kontaktID
	 * @param nutzerID
	 * @return Vector mit allen Ausprägungen zu dem bestimmten Kontakt und
	 *         Nutzer.
	 * @throws IllegalArgumentException
	 */
	public Vector<EigenschaftAuspraegungWrapper> findSharedAuspraegung(int kontaktID, int nutzerID)
			throws IllegalArgumentException;

	/**
	 * Es werden alle Objekte vom Typ Teilhaberschaft für gewählte Ausprägungen
	 * mit einem bestimmten Teilhaber gelöscht. Nachfolgend wird, sofern mit
	 * keinem weiteren Nutzer für diese Ausprägungen Teilhaberschaften bestehen,
	 * der Status auf 0 gesetzt. Es wird ebenfalls überprüft ob weitere
	 * Teilhaberschaften an diesem Kontakt vorhanden sind. Gegebenenfalls,
	 * Statusänderung.
	 * 
	 * @param ea
	 * @param teilhaber
	 * @param n
	 * @param k
	 * @throws IllegalArgumentException
	 */
	public void deleteUpdateTeilhaberschaft(Eigenschaftauspraegung ea, Nutzer teilhaber, Nutzer n, Kontakt k)
			throws IllegalArgumentException;

	/**
	 * Alle Objekte vom Typ Teilhaberschaft anhand der KontaktID finden.
	 * 
	 * @param kontaktID
	 * @return Vector mit allen Teilhaberschaften an diesem Kontakt.
	 * @throws IllegalArgumentException
	 */

	public Vector<Teilhaberschaft> findTeilhaberschaftByKontaktID(int kontaktID) throws IllegalArgumentException;

	/**
	 * Anlegen eines Objektes vom Typ Teilhaberschaft für bestimmte Ausprägungen
	 * an einen bestimmten Nutzer.
	 * 
	 * @param eigenschaftauspraegungID
	 * @param teilhaberID
	 * @param nutzerID
	 * @return
	 */
	public Teilhaberschaft insertTeilhaberschaftAuspraegung(int eigenschaftauspraegungID, int teilhaberID,
			int nutzerID);

	/**
	 * Löschen eines KontaktKontaktliste-Objekts
	 * 
	 * @param kontaktID
	 * @param kontaktlisteID
	 * @throws IllegalArgumentException
	 */
	public void deleteKontaktKontaktlisteByKontaktIDAndByKListID(int kontaktID, int kontaktlisteID)
			throws IllegalArgumentException;

	/**
	 * Selektieren der Basis Kontaktliste - "Mir geteitelte Kontakte" eines
	 * Nutzers
	 * 
	 * @param kList
	 * @param nutzerID
	 * @return Basis Kontaktliste - "Mir geteitelte Kontakte"
	 * @throws IllegalArgumentException
	 */

	public Kontaktliste findKontaktlisteMeineGeteiltenKontakte(String kList, int nutzerID)
			throws IllegalArgumentException;

	/**
	 * Ein Objekt vom Typ Teilhaberschaft anhand des Kontaktes und des Teilhaber
	 * löschen.
	 * 
	 * @param kontaktID
	 * @param teilNutzerID
	 * @throws IllegalArgumentException
	 */

	public void deleteTeilhaberschaftByKontaktIDAndNutzerID(int kontaktID, int teilNutzerID)
			throws IllegalArgumentException;

	/**
	 * Bearbeiten des Statutes eines Kontaktes
	 * 
	 * @param k
	 * @param n
	 * @throws IllegalArgumentException
	 */

	public void updateKontaktStatus(Kontakt k, Nutzer n) throws IllegalArgumentException;

	/**
	 * Objekte vom Typ Teilhaberschaft anhand der Auspägung und des Teilhabers
	 * finden.
	 * 
	 * @param auspraegungID
	 * @param teilhaberID
	 * @return Das Objekt vom Typ Teilhaberschaft
	 * @throws IllegalArgumentException
	 */

	public Vector<Teilhaberschaft> findTeilhaberschaftByAuspraegungIDAndTeilhaberID(int auspraegungID, int teilhaberID)
			throws IllegalArgumentException;

	/**
	 * Objekte vom Typ Teilhaberschaft anhand des Kontaktes und des Teilhabers
	 * finden.
	 * 
	 * @param auspraegungID
	 * @param teilhaberID
	 * @return Das Objekt vom Typ Teilhaberschaft
	 * @throws IllegalArgumentException
	 */

	public Vector<Teilhaberschaft> findTeilhaberschaftByKontaktIDAndTeilhaberID(int kontaktID, int teilhaberID)
			throws IllegalArgumentException;

	/**
	 * Objekte vom Typ Teilhaberschaft anhand des Teilhabers und des Eigentümers
	 * finden.
	 * 
	 * @param auspraegungID
	 * @param teilhaberID
	 * @return Das Objekt vom Typ Teilhaberschaft
	 * @throws IllegalArgumentException
	 */

	public Vector<Teilhaberschaft> findTeilhaberschaftByTeilhaberIDAndNutzerID(int teilhaberID, int nutzerID)
			throws IllegalArgumentException;

	/**
	 * Auslesen eines KontaktKontaktliste-Objekts anhand der Fremdschlüssel
	 * kontaktID und kontaktlisteID
	 * 
	 * @param kontaktID
	 *            der Fremdschlüssel kontaktID
	 * @param kListID
	 *            der Fremdschlüssel kontaktlisteID
	 * @return KontaktKontaktliste-Objekt
	 * @throws IllegalArgumentException
	 */
	public KontaktKontaktliste findKontaktKontaktlisteByKontaktIDAndKlisteID(int kontaktID, int kListID)
			throws IllegalArgumentException;

	/**
	 * Objekte vom Typ Kontakt anhand des Eigentümers und des Teilhabers
	 * selektieren.
	 * 
	 * @param nutzerID
	 * @param teilhaberID
	 * @return Ein Vektor mit den Kontakt-Objekten
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> findGeteilteKontakteFromNutzerAndTeilhaber(int nutzerID, int teilhaberID)
			throws IllegalArgumentException;

	/**
	 * Objekte vom Typ Kontakt anhand des Eigentümers, der Eigenschaft und der
	 * Ausprägung finden.
	 * 
	 * @param nutzerID
	 * @param teilhaberID
	 * @return Ein Vektor mit den Kontakt-Objekten
	 * @throws IllegalArgumentException
	 */

	public Vector<Kontakt> findKontakteByEigAus(int NutzerID, String bez, String wert) throws IllegalArgumentException;

	/**
	 * Objekte vom Typ Kontakt anhand des Eigentümers und der Eigenschaft
	 * finden.
	 * 
	 * @param nutzerID
	 * @param teilhaberID
	 * @return Ein Vektor mit den Kontakt-Objekten
	 * @throws IllegalArgumentException
	 */

	public Vector<Kontakt> findKontakeByEig(int NutzerID, String bez) throws IllegalArgumentException;

	/**
	 * Objekte vom Typ Kontakt anhand des Eigentümers und der Ausprägung finden.
	 * 
	 * @param nutzerID
	 * @param teilhaberID
	 * @return Ein Vektor mit den Kontakt-Objekten
	 * @throws IllegalArgumentException
	 */

	public Vector<Kontakt> findKontakteByAus(int NutzerID, String wert) throws IllegalArgumentException;

	/**
	 * 
	 * 
	 * @param kontaktlisteID
	 * @return Kontaktliste-Objekt mit geändertem Status
	 * @throws IllegalArgumentException
	 */
	public Kontaktliste updateKontaktlisteStatus(int kontaktlisteID) throws IllegalArgumentException;

	/**
	 * Auslesen eines Vectors mit allen Nutzern, mit denen eine Kontaktliste
	 * noch nicht geteilt wurde.
	 * 
	 * @param kontaktlisteID
	 *            die ID der Kontaktliste, die geteilt werden soll
	 * @return Vector mit Nutzer-Objekten
	 * @throws IllegalArgumentException
	 */
	public Vector<Nutzer> findNutzerToShareListWith(int kontaktlisteID, int nutzerID) throws IllegalArgumentException;

	/**
	 * Löschen einer Teilhaberschaft an einer Kontaktliste. Gleichzeitig wird
	 * überprüft, ob noch Teilhaberschaften an der Kontaktliste bestehen,
	 * ansonsten wird der Status der Kontaktliste auf 0 (= nicht geteilt)
	 * gesetzt und die Kontaktliste geupdated.
	 * 
	 * @param teilhaberID,
	 *            die ID des Teilhabers, dessen Teilhaberschaft aufgelöst werden
	 *            muss
	 * @param kontaktlisteID,
	 *            die ID der Liste, an der die Teilhaberschaft gelöscht werden
	 *            soll
	 * @throws IllegalArgumentException
	 */
	public void deleteTeilhaberschaftAnKontaktliste(int teilhaberID, int kontaktlisteID)
			throws IllegalArgumentException;

	/**
	 * Ein Objekt vom Typ Teilhaberschaft anahnd der Kontaktliste und des
	 * Teilhabers finden. Es wird das Objekt gesucht welches die Teilhaberschaft
	 * einer bestimmten Liste mit einem bestimmten Nutzer darstellt.
	 * 
	 * @param kontaktlisteID
	 * @param teilhaberID
	 * @return Das Objekt vom Typ Teilhaberschaft
	 * @throws IllegalArgumentException
	 */

	public Teilhaberschaft findByTeilhaberschaftByKontaktlistIDAndTeilhaberID(int kontaktlisteID, int teilhaberID)
			throws IllegalArgumentException;

	/**
	 * Auslesen aller geteilten Eigenschaftsausprägungen eines bestimmten
	 * Kontakts
	 * 
	 * @param kontaktID
	 *            die ID des Kontaktes
	 * @return Vector mit sämtlichen geteilten Eigenschaftsausprägungen des
	 *         Kontaktes
	 */
	public Vector<Eigenschaftauspraegung> findAllSharedAuspraegungenFromKontaktID(int kontaktID)
			throws IllegalArgumentException;

	/**
	 * Vector mit allen Kontakten, die zu einer Kontaktliste hinzugefügt werden
	 * können. Dabei sollen nur Kontakte angezeigt werden, die der Nutzer
	 * erstellt oder empfangen hat und die noch nicht Teil der Kontaktliste sind
	 * 
	 * @param nutzerID
	 *            die ID des angemeldeten Nutzers
	 * @param kontaktlisteID
	 *            die ID der Kontaktliste, zu der Kontakte hinzugefügt werden
	 *            sollen
	 * @return Vector mit allen Kontakten, die zu einer Kontaktliste
	 *         hinzugefuegt werden können.
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> hinzuzufuegendeKontakte(int nutzerID, int kontaktlisteID) throws IllegalArgumentException;

	/**
	 * Ein Objekt vom Typ Teilhaberschaft anahnd der Ausprägung und des
	 * Teilhabers finden. Es wird das Objekt gesucht welches die Teilhaberschaft
	 * einer bestimmten Ausprägung mit einem bestimmten Nutzer darstellt.
	 * 
	 * @param auspraegungID
	 * @param nutzerID
	 * @return Das Objekt vom Typ Teilhaberschaft
	 * @throws IllegalArgumentException
	 */

	public Teilhaberschaft findTeilhaberschaftByAuspraegungIdAndTeilhaberId(int auspraegungID, int nutzerID)
			throws IllegalArgumentException;

	/**
	 * Eine Eigenschaftsausprägung anhand der Fremdschlüssel eigenschaftID und
	 * kontaktID auslesen
	 * 
	 * @param eigID
	 *            Fremdschlüssel eigenschaftID
	 * @param kontaktID
	 *            Fremdschlüssel kontaktID
	 * @return Eigenschaftausprägung Objekt mit übergebenen Parametern als
	 *         Fremdschlüssel
	 * @throws IllegalArgumentException
	 */
	public Eigenschaftauspraegung getAuspraegungByEigID(int eigID, int kontaktID) throws IllegalArgumentException;

	/**
	 * Auslesen eines Vectors mit KontaktKontaktliste-Objekten, die die
	 * übergebene kontaktID als Fremdschlüssel besitzen
	 * 
	 * @param kontaktID
	 *            die ID eines Kontaktes, dessen Zugehörigkeiten an
	 *            Kontaktlisten ausgelesen werden sollen
	 * @return Vector mit KontaktKontaktliste-Objekten, die die übergebene
	 *         kontaktID als Fremdschlüssel besitzen
	 * @throws IllegalArgumentException
	 */
	public Vector<KontaktKontaktliste> findKontaktKontaktlisteByKontaktID(int kontaktID)
			throws IllegalArgumentException;

	/**
	 * Auslesen eines Vectors mit allen Kontaktlisten, zu denen ein Kontakt
	 * hinzugefügt werden kann.
	 * 
	 * @param kontaktID
	 *            die ID des Kontaktes der zu einer Kontaktliste hinzugefügt
	 *            werden soll
	 * @param nutzerID
	 *            die ID des angemeldeten Nutzers
	 * @return Vector mit allen Kontaktlisten-Objekten, zu denen der Kontakt
	 *         noch nicht hinzugefügt wurde
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontaktliste> findKontaktlistenToAddKontakt(int kontaktID, int nutzerID)
			throws IllegalArgumentException;

	/**
	 * Auslesen aller Nutzer, die eine Berechtigung haben eine Teilhaberschaft
	 * zu verwalten
	 * 
	 * @param auspraegung
	 * @param telhaberID
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Vector<Nutzer> getAllTeilhaberfromAuspraegungBerechtigung(int auspraegung, int teilhaberID)
			throws IllegalArgumentException;

	/**
	 * Methode zum Löschen einer Teilhaberschaft eines Kontakts und aller
	 * vorhandenen Teilhaberschaften an den Ausprägungen
	 * 
	 * @param kontaktID
	 * @param teilhaberID
	 */
	public void deleteTeilhaberschaftAllByKontaktIDAndTeilhaberID(int kontaktID, int teilhaberID)
			throws IllegalArgumentException;

	/**
	 * Objekte vom Typ Teilhaberschaft anahnd des Kontaktes und des Teilhabers
	 * finden. Es werden Objekte gesucht welche die Teilhaberschaft eines
	 * bestimmten Kontaktes mit einem bestimmten Nutzer darstellen.
	 * 
	 * @param kontaktID
	 * @param nutzerID
	 * @return Vektor mit Teilhaberschaftsobjekten
	 * @throws IllegalArgumentException
	 */

	public Vector<Teilhaberschaft> findTeilhaberschaftByKontaktIDAndNutzerID(int kontaktID, int nutzerID)
			throws IllegalArgumentException;

	/**
	 * Hinzufügen eines oder mehrerer Kontakte zu einer Kontaktliste. Es werden
	 * KontaktKontaktliste-Objekte erstellt die die Zugehörigkeit von Kontakten
	 * zu einer Kontaktlsite repräsentieren
	 * 
	 * @param kontakt
	 *            Vector der die ausgewählten Kontakt-Objekte enthält
	 * @param kontaktlisteID
	 *            die ID der Kontaktliste, zu der die Kontakte hinzugefügt
	 *            werden sollen
	 * @return KontaktKontaktliste-Objekt
	 * @throws IllegalArgumentException
	 */
	public KontaktKontaktliste addKontakteToKontaktliste(Vector<Kontakt> kontakt, int kontaktlisteID)
			throws IllegalArgumentException;

	/**
	 * Es werden alle Teilhaberschaften an einem Kontakt gelöscht. Der Kontakt
	 * ist daraufhin mti keinem Nutzer mehr geteilt. Jegliche Teilhaberschaften
	 * an dem Kontakt und den dazugehörigen Ausprägungen wurden entfernt.
	 * 
	 * @param k
	 * @param n
	 * @throws IllegalArgumentException
	 */
	public void deleteAllTeilhaberschaftenKontakt(Kontakt k, Nutzer n) throws IllegalArgumentException;

	/**
	 * Auslesen aller eigenen und geteilten Kontakte durch name und nutzerid
	 * 
	 * @param kontaktobjekt
	 * @return vector aller eigenen und geteilten kontakten mit dem gesuchten
	 *         name
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> findGesuchteKontakte(Kontakt k) throws IllegalArgumentException;

}