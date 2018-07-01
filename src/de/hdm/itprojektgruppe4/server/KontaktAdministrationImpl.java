package de.hdm.itprojektgruppe4.server;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Collections;
import java.text.SimpleDateFormat;

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektgruppe4.client.EigenschaftAuspraegungWrapper;
import de.hdm.itprojektgruppe4.server.db.*;
import de.hdm.itprojektgruppe4.shared.KontaktAdministration;
import de.hdm.itprojektgruppe4.shared.bo.*;

@SuppressWarnings("serial")
public class KontaktAdministrationImpl extends RemoteServiceServlet implements KontaktAdministration {

	/**
	 * Default constructor
	 */
	public KontaktAdministrationImpl() {
	}

	/**
	 * Referenz auf den NutzerMapper, der Nutzer-Objekte mit der Datenbank
	 * abgleicht.
	 */
	private NutzerMapper nutzerMapper = null;

	/**
	 * Referenz auf den KontaktMapper, der Kontakt-Objekte mit der Datenbank
	 * abgleicht.
	 */
	private KontaktMapper konMapper = null;

	/**
	 * Referenz auf den PersonMapper, der Person-Objekte mit der Datenbank
	 * abgleicht.
	 */
	private PersonMapper persMapper = null;

	/**
	 * Referenz auf den KontaktlisteMapper, der Kontakt-Objekte mit der
	 * Datenbank abgleicht.
	 */
	private KontaktlisteMapper konlistMapper = null;

	/**
	 * Referenz auf den EigenschaftMapper, der Eigenschaft-Objekte mit der
	 * Datenbank abgleicht.
	 */
	private EigenschaftMapper eigMapper = null;

	/**
	 * Referenz auf den EigenschaftauspragungMapper, der
	 * Eigenschaftauspraegung-Objekte mit der Datenbank abgleicht.
	 */
	private EigenschaftauspraegungMapper eigenschaftauspraegungMapper = null;

	/**
	 * Referenz auf den TeilhaberschaftMapper, der Teilhaberschaft-Objekte mit
	 * der Datenbank abgleicht.
	 */
	private TeilhaberschaftMapper teilhaberschaftMapper = null;

	/**
	 * Referenz auf den KontaktKontaktlisteMapper, der
	 * KontaktKontaktliste-Objekte mit der Datenbank abgleicht.
	 */
	private KontaktKontaktlisteMapper kontaktKontaktlisteMapper = null;

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Initialisierung
	 * *************************************************************************
	 * **
	 */

	/**
	 * @return
	 */
	public void init() throws IllegalArgumentException {
		this.konMapper = KontaktMapper.kontaktMapper();
		this.nutzerMapper = NutzerMapper.nutzerMapper();
		this.persMapper = PersonMapper.personMapper();
		this.konlistMapper = KontaktlisteMapper.kontaktlisteMapper();
		this.eigenschaftauspraegungMapper = EigenschaftauspraegungMapper.eigenschaftauspraegungMapper();
		this.eigMapper = EigenschaftMapper.eigenschaftMapper();
		this.kontaktKontaktlisteMapper = KontaktKontaktlisteMapper.kontaktkontaktlistemapper();
		this.teilhaberschaftMapper = TeilhaberschaftMapper.teilhaberschaftMapper();
	}
	
	
	
	

	/*
	 * ########################################################## START Methoden
	 * f�r Kontakt-Objekte
	 * #########################################################
	 */

	/**
	 * Einen Kontakt anlegen
	 * 
	 * @param name der Name des Kontakts
	 * @param erzeugungsdatum das Erzeugungsdatum des Kontakts
	 * @param modifikationsdatum, das Datum an dem Eigenschaften oder Auspraegungen des Kontaktes geändert wurden
	 * @param status der Status ob der Kontakt geteilt wurde oder nicht
	 * @param nutzerID Fremdschlüsselbeziehung zum Ersteller des Kontakes
	 * @return Kontakt-Objekt
	 * @throws IllegalArgumentException
	 */

	public Kontakt insertKontakt(String name, Date erzeugungsdatum, Date modifikationsdatum, int status, int nutzerID)
			throws IllegalArgumentException {

		Kontakt k = new Kontakt();

		k.setName(name);
		k.setErzeugungsdatum(erzeugungsdatum);
		k.setModifikationsdatum(modifikationsdatum);
		k.setStatus(status);
		k.setNutzerID(nutzerID);

		this.konMapper.insertKontakt(k);

		Kontaktliste kl = this.findBasicKontaktliste(k.getNutzerID());

		this.createKontaktinBasicKontakliste(kl, k);

		return k;
	}
	
	
	/**
	 * Einen Kontakt anhand seines Primaerschluessel anzeigen lassen.
	 * 
	 * @param id der Primaerrschluessel des Objekts
	 * @return Kontakt-Objekt mit dem uebergebenen Primaerschluessel
	 * @throws IllegalArgumentException
	 */

	// pr�feb
	@Override
	public Kontakt findKontaktByID(int id) throws IllegalArgumentException {
		return this.konMapper.findKontaktByID(id);

	}

	/**
	 * Alle Kontakte auslesen und in einem Vector ausgeben lassen.
	 * 
	 * @return Vector aller Kontakte
	 * @throws IllegalArgumentException
	 */

	@Override
	public Vector<Kontakt> findAllKontakte() throws IllegalArgumentException {
		return this.konMapper.findAllKontakte();
	}

	/**
	 * Einen Kontakt anhand seines Namens anzeigen lassen.
	 * 
	 * @param name,
	 *            der Name des Kontakts
	 * @return Kontakt-Objekt mit dem �bergebenen Namen
	 * @throws IllegalArgumentException
	 */

	@Override
	public Kontakt findKontaktByName(String name) throws IllegalArgumentException {
		return this.konMapper.findKontaktByName(name);
	}

	/**
	 * Ausgabe eines Vectors mit s�mtlichen geteilten und erstellten Kontakten
	 * eines Nutzers
	 * 
	 * @param nutzerID
	 * @return Vector mit s�mtlichen geteilten und erstellten Kontakten des
	 *         Nutzers
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Kontakt> findAllKontaktFromNutzer(int nutzerID) throws IllegalArgumentException {
		Vector<Kontakt> kontakte = findKontaktByNutzerID(nutzerID);
		// Vector<Teilhaberschaft> teilhabe =
		// getAllTeilhaberschaftenFromUser(nutzerID);
		Vector<Kontakt> empfangeneKontakte = findAllSharedKontakteVonNutzer(nutzerID);
		/*
		 * if(teilhabe!=null){ Vector<Kontakt> kont = new Vector<Kontakt>();
		 * for(Teilhaberschaft t : teilhabe){ if(t.getTeilhaberID() == nutzerID
		 * && t.getKontaktID() != 0){
		 * kont.add(findKontaktByID(t.getTeilhaberID())); } }
		 * kontakte.addAll(kont); }
		 */
		kontakte.addAll(empfangeneKontakte);
		return kontakte;
	}
	
	/**
	 * Auslesen aller eigenen erstellten Kontakte 
	 * 
	 * @param nutzerid
	 * @return vector aller eigenen kontakte
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> findAllEigeneKontaktFromNutzer(int nutzerID) throws IllegalArgumentException {
		Vector<Kontakt> kontakte = findKontaktByNutzerID(nutzerID);
		
		
		return kontakte;
	}

	/**
	 * Ueberschreiben eines <code>Kontakt</code>-Objekts.
	 * 
	 * @param k das zu bearbeitende Kontakt-Objekt
	 * @return das bearbeitete Kontakt-Objekt
	 * @throws IllegalArgumentException
	 */
	@Override
	public Kontakt updateKontakt(Kontakt k) throws IllegalArgumentException {
		k.setModifikationsdatum(new Date());
		return this.konMapper.updateKontakt(k);
	}

	/**
	 * Updaten des Kontakt-Status, wenn keine Teilhaberschaft an diesem vorhanden ist
	 * 
	 * @param k der Kontakt, dessen Status geändert werden soll
	 * @param n der Nutzer, der den Kontakt erstellt hat
	 * 
	 * @return das überschriebene Kontakt-Objekt
	 */
	@Override
	public void updateKontaktStatus(Kontakt k, Nutzer n) throws IllegalArgumentException {
		Kontakt kon = this.findKontaktByID(k.getID());
		Nutzer teilNutzer = this.findNutzerByID(n.getID());
		this.deleteKontaktKontaktlisteByKontaktIDAndByKListID(kon.getID(),
				this.findKontaktliste(teilNutzer.getID(), "Mit mir geteilte Kontakte").getID());

		if (this.findTeilhaberschaftByKontaktID(kon.getID()).size() < 1) {
			kon.setID(0);
			this.konMapper.updateKontakt(kon);
		}

	}

	/**
	 * Einen Kontakt löschen.
	 * Um die referentielle Integrität zu gewährleisten, müssen auch alle anderen Entitäten,
	 * auf die der Kontakt als Fremdschlüssel verweist gelöscht werden.
	 * 
	 * @param k das zu löschende Kontakt-Objekt
	 * @throws IllegalArgumentException
	 */

	@Override
	public void deleteKontakt(Kontakt k) throws IllegalArgumentException {

		List<Eigenschaftauspraegung> eaList = eigenschaftauspraegungMapper.findAuspraegungByKontaktID(k.getID());
		List<KontaktKontaktliste> kontaktKontakliste = kontaktKontaktlisteMapper
				.findKontaktKontaktlisteByKontaktID(k.getID());
		List<Teilhaberschaft> teilhaberschaft = teilhaberschaftMapper.findTeilhaberschaftByKontaktID(k.getID());
		List<Eigenschaftauspraegung> eigauspraegung = eigenschaftauspraegungMapper.findAllSharedAuspraegungenFromKontaktID(k.getID());
		
		
			for (Eigenschaftauspraegung eigenschaftauspraegung : eigauspraegung) {
				teilhaberschaftMapper.deleteTeilhaberschaftByAuspreaegungID(eigenschaftauspraegung.getID());
			}
		 
		

		if (eaList != null) {
			for (Eigenschaftauspraegung ea : eaList) {

				eigenschaftauspraegungMapper.deleteAuspraegung(ea);
			}
		}

		if (kontaktKontakliste != null) {
			for (KontaktKontaktliste kkliste : kontaktKontakliste) {
				kontaktKontaktlisteMapper.deleteKontaktKontaktlisteByKontaktID(kkliste.getKontaktID());
			}
		}

		if (teilhaberschaft != null) {
			for (Teilhaberschaft th : teilhaberschaft) {
				teilhaberschaftMapper.deleteTeilhaberschaftByKontaktID(th.getKontaktID());
			}
		}

		this.konMapper.deleteKontakt(k);
		this.persMapper.deletePerson(this.persMapper.findPersonByID(k.getID()));

	}

	/**
	 * Auslesen von Kontakten anhand der ID des Kontakterstellers.
	 * 
	 * @param nutzerID
	 * @return Vector mit s�mtlichen Kontakten mit der �bergebenen NutzerID
	 * @throws IllegalArgumentException
	 */

	public Vector<Kontakt> findKontaktByNutzerID(int nutzerID) throws IllegalArgumentException {
		return this.konMapper.findKontaktByNutzerID(nutzerID);
	}


	/**
	 * Ausgabe aller Kontake einer Kontaktliste
	 * 
	 * @param kontaktlisteID
	 * @return Vector mit saemtlichen Kontakten einer Kontaktliste
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Kontakt> getAllKontakteFromKontaktliste(int kontaktlisteID) throws IllegalArgumentException {
		Vector<KontaktKontaktliste> kk = getKontaktKontaktlisteFromKontaktliste(kontaktlisteID);
		Vector<Kontakt> kontakte = new Vector<Kontakt>();

		for (KontaktKontaktliste kontaktKontaktliste : kk) {
			kontakte.add(findKontaktByID(kontaktKontaktliste.getKontaktID()));
		}
		return kontakte;
	}

	/**
	 * Ausgabe eines Vectors mit sämtlichen Kontakten, die mit einem
	 * bestimmten Nutzer geteilt wurden.
	 * 
	 * @param nutzerID die ID des Nutzers
	 * @return Vector mit sämtlichen geteilten Kontakten eines Nutzers
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Kontakt> findAllSharedKontakteVonNutzer(int nutzerID) throws IllegalArgumentException {
		Vector<Teilhaberschaft> teilhaben = teilhaberschaftMapper.findTeilhaberschaftByTeilhaberID(nutzerID);
		Vector<Kontakt> kontakte = new Vector<Kontakt>();
		
		for (Teilhaberschaft t : teilhaben) {
			if(t.getKontaktID() != 0){
			kontakte.add(findKontaktByID(t.getKontaktID()));
		}
		}
		return kontakte;
	}
	
	/**
	 * Vector mit allen Kontakten, die zu einer Kontaktliste hinzugefügt werden können.
	 * Dabei sollen nur Kontakte angezeigt werden, die der Nutzer erstellt oder empfangen hat und die noch nicht Teil der Kontaktliste sind
	 * 
	 * @param nutzerID die ID des angemeldeten Nutzers
	 * @param kontaktlisteID die ID der Kontaktliste, zu der Kontakte hinzugefügt werden sollen
	 * @return Vector mit allen Kontakten, die zu einer Kontaktliste hinzugefuegt werden können.
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Kontakt> hinzuzufuegendeKontakte(int nutzerID, int kontaktlisteID) throws IllegalArgumentException {
		Vector<Kontakt> kontakteVonNutzer = this.findAllKontaktFromNutzer(nutzerID);
		Vector<Kontakt> kontakteInListe = this.getAllKontakteFromKontaktliste(kontaktlisteID);
		Vector<Kontakt>	zuEntfernendeKontakte = new Vector<Kontakt>();
		
		for(Kontakt k : kontakteVonNutzer){
			for(Kontakt kon : kontakteInListe){
				if(k.getID() == kon.getID()){
					zuEntfernendeKontakte.add(k);
				}
			}
		}
		kontakteVonNutzer.removeAll(zuEntfernendeKontakte);
		return kontakteVonNutzer;
	}

	/*
	 * ########################################################## ENDE Methoden
	 * fuer Kontakt-Objekte
	 * #########################################################
	 */

	/*
	 * ########################################################## START Methoden
	 * fuer Nutzer-Objekte
	 * #########################################################
	 */
	/**
	 * Einen Nutzer anlegen
	 * 
	 * @param mail die Google-Mail des Nutzers
	 * @return Nutzer-Objekt
	 * @throws IllegalArgumentException
	 */

	@Override
	public Nutzer insertNutzer(String mail) throws IllegalArgumentException {
		Nutzer nutzer = new Nutzer();
		nutzer.setEmail(mail);
		return this.nutzerMapper.insertNutzer(nutzer);

	}

	/**
	 * Löschen eines Nutzers.
	 * Alle Fremdschlüsselbeziehungen des Nutzer-Objekts müssen ebenfalls aufgelöst werden.
	 * 
	 * @param n das zu löschende Nutzer-Objekt
	 * @throws IllegalArgumentException
	 */

	@Override
	public void deleteNutzer(Nutzer n) throws IllegalArgumentException {
		
		Vector<Teilhaberschaft> alleTeilAsTeilhaber = this.getAllTeilhaberschaftenFromUser(n.getID());
		Vector<Teilhaberschaft> alleTeilanKontakten = new Vector<Teilhaberschaft>();
		for (Teilhaberschaft teilhaberschaft : alleTeilAsTeilhaber) {
			alleTeilanKontakten = (this.findTeilhaberschaftByKontaktIDAndNutzerID(teilhaberschaft.getKontaktID(), n.getID()));
			for (Teilhaberschaft t : alleTeilanKontakten ){
				if(this.findTeilhaberschaftByKontaktID(t.getKontaktID()).size() < 1){
					Kontakt k = this.findKontaktByID(t.getKontaktID());
					k.setStatus(0);
					updateKontakt(k);
				}
			}
			this.deleteTeilhaberschaft(teilhaberschaft);
		}
		
		
		Vector<Kontakt> alleKon = this.findAllKontaktFromNutzer(n.getID());
		for (Kontakt kontakt : alleKon) {
			this.deleteKontakt(kontakt);
		}
		
		
		Vector<Kontaktliste> alleList = this.findKontaktlisteByNutzerID(n.getID());
		for (Kontaktliste kontaktliste : alleList) {
			this.deleteKontaktlisteWhenUserDelte(kontaktliste);
			
		}
		
		this.nutzerMapper.deleteNutzer(n);
		this.persMapper.deletePerson(this.persMapper.findPersonByID(n.getID()));
		
		
	}

	/**
	 * Überschreiben eines Nutzer-Objekts.
	 * 
	 * @param n das zu bearbeitende Nutzer-Objekt
	 * @return n das bearbeitete Nutzer Objekt
	 * @throws IllegalArgumentException
	 */

	@Override
	public Nutzer updateNutzer(Nutzer n) throws IllegalArgumentException {
		return this.nutzerMapper.updateNutzer(n);
	}

	/**
	 * Einen Nutzer anhand seiner E-Mail auslesen.
	 * 
	 * @param email die E-Mail des Nutzers
	 * @return Nutzer-Objekt
	 * @throws IllegalArgumentException
	 */

	@Override
	public Nutzer findNutzerByEmail(String email) throws IllegalArgumentException {
		return this.nutzerMapper.findNutzerByEmail(email);
	}

	/**
	 * Einen Nutzer anhand seiner ID auslesen.
	 * 
	 * @param id der Primärschlässel des Nutzer-Objekts
	 * @return Nutzer-Objekt
	 * @throws IllegalArgumentException
	 */

	@Override
	public Nutzer findNutzerByID(int id) throws IllegalArgumentException {
		return this.nutzerMapper.findNutzerByID(id);
	}

	/**
	 * Alle Nutzer auslesen.
	 * 
	 * @return Vector saemtlicher Nutzer
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Nutzer> findAllNutzer() throws IllegalArgumentException {
		return this.nutzerMapper.findAllNutzer();
	}

	/**
	 * Vector mit saemtlichen Nutzern, die eine Teilhaberschaft an einer
	 * Kontaktliste haben
	 * 
	 * @param kontaktlisteID die ID der Kontaktliste an der Teilhaberschaften von Nutzernbestehen
	 * @return Vector mit Nutzern die Teilhaber an einer Kontaktliste sind
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Nutzer> findAllTeilhaberFromKontaktliste(int kontaktlisteID) throws IllegalArgumentException {
		// Vector, in dem die gesuchten Nutzer-Objekte gespeichert werden sollen
		Vector<Nutzer> nutzerVector = new Vector<Nutzer>();
		// Vector mit saemtlichen Teilhaberschaften an einer Kontaktliste
		Vector<Teilhaberschaft> teilhabe = this.findTeilhaberschaftByKontaktlisteID(kontaktlisteID);

		/*
		 * Jedes Teilhaberschaft-Objekt wird ausgelesen und mit Hilfe der
		 * Teilhaber ID der gesuchte Nutzer herausgefunden und zum Nutzer-Vektor
		 * hinzugefuegt
		 */
		for (Teilhaberschaft t : teilhabe) {
			nutzerVector.add(nutzerMapper.findNutzerByID(t.getTeilhaberID()));
		}

		return nutzerVector;
	}

	/**
	 * Auslesen aller Nutzer, mit denen der angemeldete Nutzer eine Kontaktliste
	 * geteilt hat
	 * 
	 * 
	 * @param nutzerID die ID des angemeldeten Nutzers
	 * @param kontaktlisteID die ID der Kontaktliste, deren Teilhaberschaften verwaltet werden sollen
	 * @return Vector mit s�mtlichen Nutzern, mit denen der angemeldete Nutzer die Kontaktliste geteilt hat
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Nutzer> findSharedWithNutzer(int nutzerID, int kontaktlisteID) throws IllegalArgumentException {
		Vector<Teilhaberschaft> t = findTeilhaberschaftByNutzerIDKontaktlisteID(nutzerID, kontaktlisteID);
		Vector<Nutzer> nutzerVector = new Vector<Nutzer>();

		for (Teilhaberschaft teil : t) {
			Nutzer n = findNutzerByID(teil.getTeilhaberID());
			nutzerVector.add(n);
		}

		return nutzerVector;
	}

	/**
	 * Auslesen eines Vectors mit sämtlichen Teilhabern an einer Eigenschaftsausprägung
	 * 
	 * @param auspraegungID die ID der Eigenschaftsausprägung
	 * @return Vector mit Nutzern, die Teilhaber an Ausprägung sind
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Nutzer> getAllTeilhaberfromAuspraegung(int auspraegungID) throws IllegalArgumentException {

		Vector<Teilhaberschaft> t = this.findTeilhaberschaftByAuspraegungID(auspraegungID);
		Vector<Nutzer> n = new Vector<Nutzer>();

		for (Teilhaberschaft teil : t) {
			n.add(this.findNutzerByID(teil.getTeilhaberID()));
		}

		return n;
	}
	
	
	public Vector<Nutzer> getAllTeilhaberfromAuspraegungBerechtigung(int auspraegung, int teilhaberID) throws IllegalArgumentException {
		
		Vector<Teilhaberschaft> t = this.findTeilhaberschaftByAuspraegungID(auspraegung);
		Vector<Nutzer> n = new Vector<Nutzer>();
		
		for (Teilhaberschaft teil : t) {
			if(teil.getTeilhaberID() == teilhaberID || teil.getNutzerID() == teilhaberID){
				n.add(this.findNutzerByID(teil.getTeilhaberID()));
			}
		}
		
		return n;
		
	}
	
	/**
	 * Auslesen aller Teilhaber an einem Kontakt
	 * 
	 * @param kontaktID die ID des Kontaktes
	 * @return Vector mit sämtlichen Nutzer, die Teilhaber an einem Kontakt sind
	 * @throws IllegalArgumentException
	 */
	public Vector<Nutzer> getAllTeilhaberFromKontakt (int kontaktID) throws IllegalArgumentException{
		
		Vector<Teilhaberschaft> t = this.findTeilhaberschaftByKontaktID(kontaktID);
		Vector<Nutzer> n = new Vector<Nutzer>();
		
		for (Teilhaberschaft teil : t) {
			n.add(this.findNutzerByID(teil.getTeilhaberID()));
		}
		
		return n;
		
	}
	
	
	/**
	 * Auslesen eines Vectors mit allen Nutzern, mit denen eine Kontaktliste noch nicht geteilt wurde.
	 * 
	 * @param kontaktlisteID die ID der Kontaktliste, die geteilt werden soll
	 * @param nutzerID, die ID des angemeldeten Nutzers
	 * @return Vector mit Nutzer-Objekten, die noch keine Teilhaberschaft an Kontaktliste besitzen.
	 * @throws IllegalArgumentException
	 */	

	@Override
	public Vector<Nutzer> findNutzerToShareListWith(int kontaktlisteID, int nutzerID) throws IllegalArgumentException {
		Vector<Nutzer> alleNutzer = findAllNutzer();
		Vector<Nutzer> alleTeilhaber = findAllTeilhaberFromKontaktliste(kontaktlisteID);
		Vector<Nutzer> zuEntfernendeNutzer = new Vector<Nutzer>();
		Nutzer angemeldeterNutzer = findNutzerByID(nutzerID);
		
		//Alle Nutzer-Objekte, die sowohl zum Vektor mit allen Nutzern, als auch zum Vektor mit allen Teilhabern gehören, werden asugelesen
		//und dem Vector <code>zuEntfernendeNutzer</code> hinzugefügt. Die darin enthaltenen Nutzer-Objekte werden anschließend vom Vector
		//<code>alleNutzer</code> entfernt.
		for(Nutzer user : alleNutzer){
			for(Nutzer nutzer : alleTeilhaber){
				if(user.getID() == nutzer.getID()){
					zuEntfernendeNutzer.add(user);
				}else if(user.getID() == nutzerID){
					zuEntfernendeNutzer.add(user);
				}
			}
		}
		zuEntfernendeNutzer.add(angemeldeterNutzer);
		alleNutzer.removeAll(zuEntfernendeNutzer);
		return alleNutzer;
	}


	/*
	 * ##########################################################
	 * ENDE Methoden für Nutzer-Objekte
	 * #########################################################
	 */

	/*
	 * ########################################################## 
	 * START Methoden für Person-Objekte
	 * #########################################################
	 */

	/**
	 * Eine Person anhand der ID auslesen.
	 * 
	 * @param ID, der Primärschlässel
	 * @throws IllegalArgumentException
	 */
	public Person findPersonByID(int ID) throws IllegalArgumentException {
		return this.findPersonByID(ID);
	}

	/**
	 * Loeschen einer Person.
	 * 
	 * @param p das zu löschende Personen-Objekt
	 * @throws IllegalArgumentException
	 */
	public void deletePerson(Person p) throws IllegalArgumentException {

		this.persMapper.deletePerson(p);

	}

	/*
	 * ########################################################## 
	 * ENDE Methoden für Person-Objekte
	 * #########################################################
	 */

	/*
	 * ########################################################## 
	 * START Methoden für Eigenschaft-Objekte
	 * #########################################################
	 */

	/**
	 * Eine Eigenschaft anlegen.
	 * 
	 * @param bez die Bezeichnung der Eigenschaft
	 * @param status Status der anzeigt, ob die Eigenschaft geteilt wurde oder nicht
	 * @return Eigenschaft Objekt
	 * @throws IllegalArgumentException
	 */
	@Override
	public Eigenschaft insertEigenschaft(String bez, int status) throws IllegalArgumentException {
		Eigenschaft e = new Eigenschaft();

		Eigenschaft eigenschaft = findEigByBezeichnung(bez);

		if (eigenschaft == null) {

			e.setBezeichnung(bez);
			e.setStatus(status);

			return this.eigMapper.insertEigenschaft(e);
		}

		return null;
	}



	/**
	 * Alle Objekte vom Typ Eigenschaft ausgeben
	 * 
	 * @return
	 * @throws IllegalArgumentException
	 */

	@Override
	public Vector<Eigenschaft> findAllEigenschaft() throws IllegalArgumentException {
		return this.eigMapper.findAllEigenschaft();

	}



	/**
	 * Eine Eigenschaft anhand der ID auslesen.
	 * 
	 * @param id,
	 *            Primaerrschluessel der Eigenschaft
	 * @return Eigenschaft-Objekt
	 * @throws IllegalArgumentException
	 */

	@Override
	public Eigenschaft getEigenschaftByID(int id) throws IllegalArgumentException {
	
		return this.eigMapper.getEigenchaftByID(id);
	}

	/**
	 * Eine Eigenschaft �berschreiben.
	 * 
	 * @param e
	 *            das Eigenschaft-Objekt
	 * @return
	 * @throws IllegalArgumentException
	 */

	@Override
	public Eigenschaft updateEigenschaft(Eigenschaft e) throws IllegalArgumentException {

		return this.eigMapper.updateEigenschaft(e);
	}

	/**
	 * Eine Eigenschaft l�schen.
	 * 
	 * @param e
	 *            das zu l�schende Eigenschaft-Objekt
	 * @throws IllegalArgumentException
	 */

	@Override
	public void deleteEigenschaft(Eigenschaft e) throws IllegalArgumentException {
		this.eigMapper.deleteEigenschaft(e);

	}

	/**
	 * Eine Eigenschaft zur entsprechenden Auspraegung wird ausgelesen
	 * 
	 * @param ID
	 *            der Eigenschaftauspraegung
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Eigenschaft> getEigenschaftbyKontaktID(int id) throws IllegalArgumentException {

		Vector<Eigenschaftauspraegung> aus = this.findEigenschaftauspraegungByKontaktID(id);
		Vector<Eigenschaft> eig = new Vector<Eigenschaft>();

		for (Eigenschaftauspraegung ea : aus) {
			eig.add(this.eigMapper.getEigenchaftByID(ea.getEigenschaftsID()));
		}
		return eig;
	}

	/*
	 * ########################################################## 
	 *  ENDE Methoden für Eigenschaft-Objekte
	 * #########################################################
	 */

	/*
	 * ########################################################## 
	 * START Methoden für Eigenschaftauspragung-Objekte
	 * #########################################################
	 */

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

	@Override
	public Eigenschaftauspraegung insertAuspraegung(String wert, int status, int eigenschaftsID, int kontaktID)
			throws IllegalArgumentException {
		Eigenschaftauspraegung e = new Eigenschaftauspraegung();

		e.setWert(wert);
		e.setStatus(status);
		e.setKontaktID(kontaktID);
		e.setEigenschaftsID(eigenschaftsID);

		return this.eigenschaftauspraegungMapper.insertAuspraegung(e);
	}

	/**
	 * Eine Eigenschaftsauspraegung �berschreiben.
	 * 
	 * @param ea
	 * @return
	 * @throws IllegalArgumentException
	 */

	@Override
	public Eigenschaftauspraegung updateAuspraegung(Eigenschaftauspraegung ea) throws IllegalArgumentException {
		Kontakt k = findKontaktByID(ea.getKontaktID());
		k.setModifikationsdatum(new Date());
		updateKontakt(k);
		return this.eigenschaftauspraegungMapper.updateAuspraegung(ea);
	}

	/**
	 * Eine Eigenschaftsauspraegung l�schen.
	 * 
	 * @param ea
	 *            das Eigenschaftsauspraegung-Objekt
	 * @throws IllegalArgumentException
	 */

	@Override
	public void deleteAuspraegung(Eigenschaftauspraegung ea) throws IllegalArgumentException {
		List<Teilhaberschaft> teilhaberschaft = teilhaberschaftMapper.findTeilhaberschaftByAuspraegungID(ea.getID());
		if (teilhaberschaft != null) {
			for (Teilhaberschaft th : teilhaberschaft) {
				teilhaberschaftMapper.deleteTeilhaberschaftByAuspreaegungID(ea.getID());
			}
		}

	 
		this.eigenschaftauspraegungMapper.deleteAuspraegung(ea);

	}

	
	
	/**
	 * Eine Eigenschaftsauspraegung anhand der ID auslesen.
	 * 
	 * @param id der Primärschlüssel der Auspraegung
	 * @return Eigenschaftsauspraegung-Objekt
	 * @throws IllegalArgumentException
	 */
	@Override
	public Eigenschaftauspraegung getAuspraegungByID(int id) throws IllegalArgumentException {
		return this.eigenschaftauspraegungMapper.getAuspraegungByID(id);

	}
	
	/**
	 * Alle Eigenschaften die zu einem bestimmten Kontakt gehören auslesen
	 * 
	 * @param id die ID des Kontaktes, dessen Eigenschaften ausgelesen werden sollen
	 * @return Vector mit sämtlichen Eigenschaften eines Kontaktes
	 */
	public Vector<Eigenschaftauspraegung> findAllEigenschaftauspraegung() throws IllegalArgumentException {
		return this.eigenschaftauspraegungMapper.findAllAuspraegungn();

	}

	/**
	 * Eine Teilhaberschaft an einer Eigenschaftsauspraegung löschen.
	 * 
	 * @param t das zu löschende Teilhaberschaftobjekt
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteEigenschaftsauspraegungFromTeilhaberschaft(Eigenschaftauspraegung ea, Nutzer n)
			throws IllegalArgumentException {
		this.teilhaberschaftMapper.deleteEigenschaftsauspraegungFromTeilhaberschaft(ea, n);

	}
	/**
	 * Auslesen aller geteilten Eigenschaftsausprägungen eines bestimmten Kontakts
	 * 
	 * @param kontaktID die ID des Kontaktes
	 * @return Vector mit sämtlichen geteilten Eigenschaftsausprägungen des Kontaktes
	 */
	@Override
	public Vector<Eigenschaftauspraegung> findAllSharedAuspraegungenFromKontaktID(int kontaktID)
			throws IllegalArgumentException {

		return this.eigenschaftauspraegungMapper.findAllSharedAuspraegungenFromKontaktID(kontaktID);
	}

	/**
	 * Alle Eigenschaftsauspraegungen eines Kontaktes auslesen
	 * 
	 * @param kontaktID
	 * @return Vector mit sämtlichen Eigenschaftsauspraegungen mit der uebergebenen KontaktID
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Eigenschaftauspraegung> findEigenschaftauspraegungByKontaktID(int kontaktID)
			throws IllegalArgumentException {

		return this.eigenschaftauspraegungMapper.findAuspraegungByKontaktID(kontaktID);
	}
	
	/**
	 * Eine Eigenschaftsausprägung anhand der Fremdschlüssel eigenschaftID und kontaktID auslesen
	 * 
	 * @param eigID Fremdschlüssel eigenschaftID
	 * @param kontaktID	Fremdschlüssel kontaktID
	 * @return Eigenschaftausprägung Objekt mit übergebenen Parametern als Fremdschlüssel
	 * @throws IllegalArgumentException
	 */
	@Override
	public Eigenschaftauspraegung getAuspraegungByEigID(int eigID, int kontaktID) throws IllegalArgumentException {
		return this.eigenschaftauspraegungMapper.getAuspraegungByEigID(eigID, kontaktID);
	}
	
	/**
	 * Erstellung der Eigenschaftauspraegungen, die standardmäßig bei Kontakterstellung angelegt werden
	 * 
	 * @param wert der Wert der Ausprägung
	 * @param status Status, der anzeigt ob die Ausprägung geteilt wurde
	 * @param kontaktID die ID des Kontakts zu der die Ausprägung gehört
	 * @return das erstellte Eigenschaftausprägung-Objekt
	 * @throws IllegalArgumentException
	 */
	public Vector<Eigenschaftauspraegung> insertBasicAuspraegung(String wert, int status, int kontaktID)
			throws IllegalArgumentException {

		Vector<Eigenschaftauspraegung> vector = new Vector<Eigenschaftauspraegung>();

		Eigenschaftauspraegung ea1 = insertAuspraegung("", 0, 1, kontaktID);
		Eigenschaftauspraegung ea2 = insertAuspraegung("", 0, 2, kontaktID);
		Eigenschaftauspraegung ea3 = insertAuspraegung("", 0, 3, kontaktID);
		Eigenschaftauspraegung ea4 = insertAuspraegung("", 0, 4, kontaktID);

		vector.add(ea1);
		vector.add(ea2);
		vector.add(ea3);
		vector.add(ea4);

		return vector;

	}


	/*
	 * ########################################################## 
	 * ENDE Methoden für Eigenschaftauspragung-Objekte
	 * #########################################################
	 */

	/*
	 * ########################################################## 
	 * START Methoden für Kontaktliste-Objekte
	 * #########################################################
	 */

	/**
	 * 
	 * @param bez die Bezeichnung der Kontaktliste
	 * @param status der Status, der anzeigt ob die Kontaktliste geteilt wurde oder nicht
	 * @param nutzerID der Fremdschlüssel stellt die Beziehung zum Ersteller dar
	 * @return Kontaktlisten-Objekt
	 * @throws IllegalArgumentException
	 */

	@Override
	public Kontaktliste insertKontaktliste(String bez, int status, int nutzerID) throws IllegalArgumentException {

		Kontaktliste k = findKontaktliste(nutzerID, bez);

		Kontaktliste kontaktliste = new Kontaktliste();

		if (k == null) {
			kontaktliste.setBez(bez);
			kontaktliste.setStatus(status);
			kontaktliste.setNutzerID(nutzerID);

			return this.konlistMapper.insertKontaktliste(kontaktliste);
		}

		return null;

	}

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
	@Override
	public Kontaktliste insertMeineKontakte(String bez, int status, int nutzerID) throws IllegalArgumentException {
		Kontaktliste meineKontakte = new Kontaktliste();

		meineKontakte.setBez("Meine Kontakte");
		meineKontakte.setStatus(status);
		meineKontakte.setNutzerID(nutzerID);

		return this.konlistMapper.insertKontaktliste(meineKontakte);
	}

	public Vector<Kontaktliste> getAllKontaktlisten() {

		return null;
	}

	/**
	 * Eine Kontaktliste anhand der ID auslesen.
	 * 
	 * @param id
	 *            der Prim�rschl�ssel des Kontaktlisten-Objekts
	 * @return Kontaktlisten-Objekt
	 * @throws IllegalArgumentException
	 */

	@Override
	public Kontaktliste findKontaktlisteByID(int id) throws IllegalArgumentException {
		return this.konlistMapper.findKontaktlistebyID(id);
	}

	/**
	 * Eine Kontaktliste anhand der Bezeichnung auslesen.
	 * 
	 * @param bezeichnung,
	 *            die Bezeichnung der Kontaktliste
	 * @return Kontaktlisten-Objekt
	 * @throws IllegalArgumentException
	 */

	@Override
	public Kontaktliste findKontaktlisteByBezeichnung(String bezeichnung) throws IllegalArgumentException {
		return this.konlistMapper.findKontaktlistebyBezeichnung(bezeichnung);
	}
	
	/**
	 * Auslesen eines Vectors mit allen Kontaktlisten, zu denen ein Kontakt hinzugefügt werden kann.
	 * 
	 * @param kontaktID die ID des Kontaktes der zu einer Kontaktliste hinzugefügt werden soll
	 * @param nutzerID die ID des angemeldeten Nutzers
	 * @return	Vector mit allen Kontaktlisten-Objekten, zu denen der Kontakt noch nicht hinzugefügt wurde
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Kontaktliste> findKontaktlistenToAddKontakt(int kontaktID, int nutzerID)
			throws IllegalArgumentException {
		Vector<Kontaktliste> listenVonNutzer = this.getAllKontaktlistenFromUser(nutzerID);
		Vector<KontaktKontaktliste> KontaktKontaktliste = this.findKontaktKontaktlisteByKontaktID(kontaktID);
		//In diesem Vector sollen die Kontaktlisten gespeichert werden, zu denen der Kontakt bereits gehört und gleichzeitig dem Nutzer gehören.
		Vector<Kontaktliste> zuEntfernendeKontaktlisten = new Vector<Kontaktliste>();
		
		//In diesem Vector sollen die Kontaktlisten gespeichert werden, zu denen der Kontakt bereits gehört
		Vector <Kontaktliste> listenVonKontakt = new Vector<Kontaktliste>();
		//Speichern aller Kontaktliste-Objekte im Vector
		for (KontaktKontaktliste kontaktKontaktliste2 : KontaktKontaktliste) {
			listenVonKontakt.add(this.findKontaktlisteByID(kontaktKontaktliste2.getKontaktlisteID()));
		}
		
		//Wenn eine Kontaktliste in beiden Vectoren vorkommt, wird diese dem Vector mit den zu entfernenden Kontaktlisten hinzugefügt
		for(Kontaktliste liste : listenVonNutzer){
			//Die Standardkontaklisten sollen ebenfalls nicht angezeigt werden, da die Kontakte hier automatisch hinzugefügt werden
			if(liste.getBez().equals("Meine Kontakte") || liste.getBez().equals("Mit mir geteilte Kontakte")){
				zuEntfernendeKontaktlisten.add(liste);
			}
			for(Kontaktliste liste2 : listenVonKontakt){
				if(liste.getID() == liste2.getID()){
					zuEntfernendeKontaktlisten.add(liste);
				}
			}

			
		}
		//Entfernen der Kontaktlisten aus dem Vector mit allen Listen des Nutzers.
		listenVonNutzer.removeAll(zuEntfernendeKontaktlisten);
		return listenVonNutzer;
		
	}

	/**
	 * Überschreiben eines Kontaktliste-Objekts in der Datenbank
	 * 
	 * @param k das zu bearbeitende Kontaktlisten-Objekt
	 * @return das überschriebene Kontaktlisten-Objekt
	 * @throws IllegalArgumentException
	 */

	@Override
	public Kontaktliste updateKontaktliste(Kontaktliste k) throws IllegalArgumentException {
		Kontaktliste konliste = this.konlistMapper.findKontaktlistebyID(k.getID());

		if (konliste.getBez().equals("Meine Kontakte") || konliste.getBez().equals("Mit mir geteilte Kontakte")) {

			return null;

		} else if ((k.getBez().equals("Meine Kontakte") || k.getBez().equals("Mit mir geteilte Kontakte"))) {

			return null;
		}
		{

			return this.konlistMapper.updateKontaktliste(k);
		}

	}

	/**
	 * Eine Kontaktliste löschen.
	 * 
	 * @param k das zu löschende Kontaktlisten-Objekt
	 * @throws IllegalArgumentException
	 */

	

	@Override
	public void deleteKontaktliste(Kontaktliste kliste) throws IllegalArgumentException {

		List<KontaktKontaktliste> kkliste = kontaktKontaktlisteMapper
				.findKontaktKontaktlisteByKontaktlisteID(kliste.getID());
		List<Teilhaberschaft> teilhaberschaft = teilhaberschaftMapper
				.findTeilhaberschaftByKontaktlisteID(kliste.getID());
		Kontaktliste konliste = this.konlistMapper.findKontaktlistebyID(kliste.getID());

		if (konliste.getBez().equals("Meine Kontakte") || konliste.getBez().equals("Mit mir geteilte Kontakte")) {

		} else {

			if (kkliste != null) {
				for (KontaktKontaktliste kkl : kkliste) {

					kontaktKontaktlisteMapper.deleteKontaktKontaktliste(kkl);
				}
			}

			if (teilhaberschaft != null) {
				for (Teilhaberschaft th : teilhaberschaft) {
					teilhaberschaftMapper.deleteTeilhaberschaftByKontaktlisteID(th.getKontaktListeID());
				}
			}

			this.konlistMapper.deleteKontaktliste(kliste);
		}

	}
	
	
	public void deleteKontaktlisteWhenUserDelte(Kontaktliste kliste) throws IllegalArgumentException {
		// this.konlistMapper.deleteKontaktliste(k);

		List<KontaktKontaktliste> kkliste = kontaktKontaktlisteMapper
				.findKontaktKontaktlisteByKontaktlisteID(kliste.getID());
		List<Teilhaberschaft> teilhaberschaft = teilhaberschaftMapper
				.findTeilhaberschaftByKontaktlisteID(kliste.getID());
		Kontaktliste konliste = this.konlistMapper.findKontaktlistebyID(kliste.getID());

		

			if (kkliste != null) {
				for (KontaktKontaktliste kkl : kkliste) {

					kontaktKontaktlisteMapper.deleteKontaktKontaktliste(kkl);
				}
			}

			if (teilhaberschaft != null) {
				for (Teilhaberschaft th : teilhaberschaft) {
					teilhaberschaftMapper.deleteTeilhaberschaftByKontaktlisteID(th.getKontaktListeID());
				}
			}

			this.konlistMapper.deleteKontaktliste(kliste);

	}
	
	/**
	 * Alle Kontaktlisten auslesen.
	 * 
	 * @return Vector mit sämtlichen Kontaktlisten
	 * @throws IllegalArgumentException
	 */

	@Override
	public Vector<Kontaktliste> findKontaktlisteAll() throws IllegalArgumentException {
		return this.konlistMapper.findKontaktlisteAll();
	}

	/**
	 * Aufhebung der Beziehung zwischen Kontakt und Kontaktliste.
	 * 
	 * @param k das zu löschende KontaktKontaktliste-Objekt
	 * @throws IllegalArgumentException
	 */

	@Override
	public void deleteKontaktKontaktliste(KontaktKontaktliste k) throws IllegalArgumentException {
		this.kontaktKontaktlisteMapper.deleteKontaktKontaktliste(k);

	}

	/**
	 * Auslesen der Kontaktlisten anhand der ID des Kontaktlistenerstellers
	 * 
	 * @param nutzerID
	 * @return Vector mit sämtlichen Kontaktlisten mit der übergebenen
	 *         NutzerID
	 * @throws IllegalArgumentException
	 */

	@Override
	public Vector<Kontaktliste> findKontaktlisteByNutzerID(int nutzerID) throws IllegalArgumentException {
		return this.konlistMapper.findKontaktlisteByNutzerID(nutzerID);
	}

	/**
	 * Auslesen aller erstellten und geteilten Kontaktlisten eines Nutzers
	 * 
	 * @param nutzerID die ID des angemeldeten Nutzers
	 * @return Vector mit sämtlichen erstellten und geteilten Kontaktlisten
	 *         eines Users
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Kontaktliste> getAllKontaktlistenFromUser(int nutzerID) throws IllegalArgumentException {
		// Instantiieren der benoetigten Vectoren um Kontaktlisten und
		// Teilhaberschaft Objekte abspeichern zu k�nnen
		Vector<Kontaktliste> kontlisten = findKontaktlisteByNutzerID(nutzerID);
		Vector<Teilhaberschaft> teilhabe = getAllTeilhaberschaftenFromUser(nutzerID);

		// Fuer jede Teilhaberschaft an einer Kontaktliste wird das
		// entsprechende Kontaktlisten-Objekt in einem neuen Vector gespeichert
		if (teilhabe != null) {
			Vector<Kontaktliste> kontlist = new Vector<Kontaktliste>();
			for (Teilhaberschaft teilhaberschaft : teilhabe) {
				if (teilhaberschaft.getTeilhaberID() == nutzerID && teilhaberschaft.getKontaktListeID() != 0) {
					kontlist.add(findKontaktlisteByID(teilhaberschaft.getKontaktListeID()));
				}
			}
			// Hinzufuegen der Kontaktlisten an denen eine Teilhaberschaft
			// besteht zum Vector mit den eigens erstellten Kontaktlisten
			kontlisten.addAll(kontlist);
		}

		// Rueckgabe des Vectors mit den erstellten und geteilten Kontaktlisten
		return kontlisten;

	}
	
	/**
	 * Auslesen der Standardkontaktliste "Mit mir geteilte Kontakte"
	 * 
	 * @param kList die Bezeichnung der Kontaktliste
	 * @param nutzerID die ID des Kontaktlisten-Inhabers
	 *@return Kontaktliste "Mit mir geteilte Kontakte" des Nutzers
	 */
	@Override
	public Kontaktliste findKontaktlisteMeineGeteiltenKontakte(String kList, int nutzerID)
			throws IllegalArgumentException {

		return this.konlistMapper.findKontaktlisteMeineGeteiltenKontakte(kList, nutzerID);
	}

	/**
	 * Gibt es keine Teilhaberschaft an einer Kontaktliste, so wird deren Status
	 * auf 0 (= nicht geteilt) gesetzt werden.
	 *
	 * @param kontaktlisteID
	 * @return Kontaktliste-Objekt mit geändertem Status
	 * @throws IllegalArgumentException
	 */
	@Override
	public Kontaktliste updateKontaktlisteStatus(int kontaktlisteID) throws IllegalArgumentException {
		Vector<Teilhaberschaft> teilhaberschaften = findTeilhaberschaftByKontaktlisteID(kontaktlisteID);
		Kontaktliste kontaktliste = findKontaktlisteByID(kontaktlisteID);
		if (teilhaberschaften.isEmpty()) {
			kontaktliste.setStatus(0);
			return this.konlistMapper.updateKontaktliste(kontaktliste);
		} else
			return null;

	}

	/*
	 * ########################################################## 
	 * ENDE Methoden für Kontaktliste-Objekte
	 * #########################################################
	 */

	/*
	 * ########################################################## 
	 * START Methoden für KontaktKontaktliste-Objekte
	 * #########################################################
	 */

	/**
	 * Anlegen eines Objekts der Klasse KontaktKontaktliste um eine Beziehung
	 * zwischen Kontakt und Kontaktliste in der Datenbank herzustellen.
	 * 
	 * @param kontaktID die ID des Kontaktes
	 * @param kontaktlisteID die ID der Kontaktliste
	 * @return KontaktKontaktliste-Objekt
	 */

	@Override
	public KontaktKontaktliste insertKontaktKontaktliste(int kontaktID, int kontaktlisteID) throws IllegalArgumentException {

		KontaktKontaktliste kk = new KontaktKontaktliste();
		kk.setKontaktID(kontaktID);
		kk.setKontaktlisteID(kontaktlisteID);


		return this.kontaktKontaktlisteMapper.insertKontaktKontaktliste(kk);		


	}
	
	/**
	 * Auslesen eines Vectors mit KontaktKontaktliste-Objekten, die die übergebene kontaktID als Fremdschlüssel besitzen
	 * 
	 * @param kontaktID die ID eines Kontaktes, dessen Zugehörigkeiten an Kontaktlisten ausgelesen werden sollen
	 * @return Vector mit KontaktKontaktliste-Objekten, die die übergebene kontaktID als Fremdschlüssel besitzen
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<KontaktKontaktliste> findKontaktKontaktlisteByKontaktID(int kontaktID)
			throws IllegalArgumentException {
		return this.kontaktKontaktlisteMapper.findKontaktKontaktlisteByKontaktID(kontaktID);
	}
	

	/**
	 * Löschen eines Kontaktes aus einer Kontaktliste
	 * 
	 * @param kontaktID die ID des zu l�schenden Kontaktes
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteKontaktKontaktlisteByKontaktID(int kontaktID) throws IllegalArgumentException {
		this.kontaktKontaktlisteMapper.deleteKontaktKontaktlisteByKontaktID(kontaktID);

	}

	/**
	 * Auslesen eines Vectors mit sämtlichen KontaktKontaktliste-Objekte,
	 * die die übergebene kontaktlisteID als Fremschlüssel halten
	 * 
	 * @param kontaktlisteID
	 * @return Vector mit KontaktKontaktlisten-Objekten die übergebene KontaktlisteID als Fremdschlüssel besitzen
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<KontaktKontaktliste> getKontaktKontaktlisteFromKontaktliste(int kontaktlisteID)
			throws IllegalArgumentException {
		return this.kontaktKontaktlisteMapper.getKontaktKontaktlisteByKontaktlisteID(kontaktlisteID);
	}

	
	/**
	 * Hinzufügen eines oder mehrerer Kontakte zu einer Kontaktliste.
	 * Es werden KontaktKontaktliste-Objekte erstellt die die Zugehörigkeit von Kontakten zu einer Kontaktlsite
	 * repräsentieren
	 * 
	 * @param kontakt Vector der die ausgewählten Kontakt-Objekte enthält
	 * @param kontaktlisteID die ID der Kontaktliste, zu der die Kontakte hinzugefügt werden sollen
	 * @return KontaktKontaktliste-Objekt
	 * @throws IllegalArgumentException
	 */
	@Override
	public KontaktKontaktliste addKontakteToKontaktliste(Vector<Kontakt> kontakt, int kontaktlisteID)
			throws IllegalArgumentException {
		for(Kontakt k : kontakt){
			this.insertKontaktKontaktliste(k.getID(), kontaktlisteID);
		}
		return null;
	}
	
	/**
	 * Löschen eines KontaktKontaktliste-Objekts anhand der übergebenen IDs
	 * 
	 * @param kontaktID 
	 * @param kontaktlisteID
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteKontaktKontaktlisteByKontaktIDAndByKListID(int kontaktID, int kontaktlisteID)
			throws IllegalArgumentException {
		this.kontaktKontaktlisteMapper.deleteKontaktKontaktlisteByKontaktIDAndByKListID(kontaktID, kontaktlisteID);

	}

	/**
	 * Auslesen eines KontaktKontaktliste-Objekts anhand der Fremdschlüssel kontaktID und kontaktlisteID
	 * 
	 * @param kontaktID der Fremdschlüssel kontaktID
	 * @param kListID der Fremdschlüssel kontaktlisteID
	 * @return KontaktKontaktliste-Objekt
	 * @throws IllegalArgumentException
	 */
	@Override
	public KontaktKontaktliste findKontaktKontaktlisteByKontaktIDAndKlisteID(int kontaktID, int kListID)
			throws IllegalArgumentException {
		return this.kontaktKontaktlisteMapper.findKontaktKontaktlisteByKontaktIDAndKlisteID(kontaktID, kListID);
	}

	/*
	 * ########################################################## 
	 * ENDE Methoden für KontaktKontaktliste-Objekte
	 * #########################################################
	 */

	/*
	 * ########################################################## 
	 * START Methoden fuer Teilhaberschaft-Objekte
	 * #########################################################
	 */

	/**
	 * Eine Teilhaberschaft anlegen
	 * 
	 * @param kontaktID Fremdschlüsselbeziehung zum Kontakt
	 * @param kontaktListeID Fremdschlüsselbeziehung zur Kontaktliste
	 * @param eigenschaftsauspraegungID Fremdschlüsselbeziehung zur Eingenschaftsauspraegung
	 * @param teilhaberID Fremdschlüsselbeziehung zum Teilhaber
	 * @return Teilhaberschaft-Objekt
	 * @throws IllegalArgumentException
	 */

	@Override
	public Teilhaberschaft insertTeilhaberschaft(int kontaktListeID, int kontaktID, int eigenschaftsauspraegungID,
			int teilhaberID, int nutzerID) throws IllegalArgumentException {
		Teilhaberschaft t = new Teilhaberschaft();

		t.setKontaktListeID(kontaktListeID);
		t.setKontaktID(kontaktID);
		t.setEigenschaftsauspraegungID(eigenschaftsauspraegungID);
		t.setTeilhaberID(teilhaberID);
		t.setNutzerID(nutzerID);

		return this.teilhaberschaftMapper.insertTeilhaberschaft(t);
	}

	/**
	 * Eine Teilhaberschaft für ein geteilten Kontakt anlegen
	 * 
	 * @param kontaktID
	 * @param eigenschaftsauspraegungID
	 * @param teilhaberID
	 * @return
	 */
	@Override

	public Teilhaberschaft insertTeilhaberschaftKontakt(int kontaktID, int teilhaberID, int nutzerID) {
		Teilhaberschaft t = new Teilhaberschaft();

		t.setKontaktID(kontaktID);
		t.setTeilhaberID(teilhaberID);
		t.setNutzerID(nutzerID);

		return this.teilhaberschaftMapper.insertTeilhaberschaftKontakt(t);
	}

	/**
	 * Erstellen einer Teilhaberschaft zu einer Kontaktliste
	 * 
	 * @param kontaktlisteID die ID der Kontaktliste die geteilt werden soll
	 * @param email die email des Nutzers, mit dem die Kontaktliste geteilt wird
	 * @param nutzerID
	 *            die ID des Nutzers, der die Kontaktliste teilt
	 * @return Teilhaberschaft-Objekt
	 * @throws IllegalArgumentException
	 */
	@Override
	public Teilhaberschaft insertTeilhaberschaftKontaktliste(int kontaktlisteID, String email, int nutzerID)
			throws IllegalArgumentException {
		//anhand der E-Mail wird der entsprechende Nutzer ausgelesen
		Nutzer n = this.findNutzerByEmail(email);
		Kontaktliste kl = this.konlistMapper.findKontaktlistebyID(kontaktlisteID);
		
		//Ist der Status der zu teilenden Kontaktliste auf 0(= nicht geteilt) wird der Status auf 1 (=geteilt) gesetzt
		if(kl.getStatus() == 0){
			kl.setStatus(1);
			this.updateKontaktliste(kl);
		}

		if (kl.getBez().equals("Meine Kontakte") || kl.getBez().equals("Mit mir geteilte Kontakte")) {
			return null;
		}
		Teilhaberschaft t = new Teilhaberschaft();
		t.setKontaktListeID(kl.getID());
		t.setTeilhaberID(n.getID());
		t.setNutzerID(nutzerID);
		return this.teilhaberschaftMapper.insertTeilhaberschaftKontaktliste(t);
		
		
	}

	/**
	 * 
	 */
	@Override
	public int insertTeilhaberschaftAuspraegungenKontakt(Kontakt kon, String selectedValue, int id)
			throws IllegalArgumentException {

		Nutzer teilnutzer = this.findNutzerByEmail(selectedValue);
		Kontakt k = this.findKontaktByID(kon.getID());
		Vector<Eigenschaftauspraegung> allAus = this.getAuspraegungByKontaktID(kon.getID());
		Vector<Kontaktliste> allListsFromTeilNutzer = this.findKontaktlisteByNutzerID(teilnutzer.getID());
		Vector<Teilhaberschaft> t = this.findTeilhaberschaftByKontaktIDAndTeilhaberID(k.getID(), teilnutzer.getID());
		Integer i = null;

		for (Eigenschaftauspraegung e : allAus) {

			if (this.findTeilhaberschaftByAuspraegungIDAndTeilhaberID(e.getID(), teilnutzer.getID()).size() == 0) {
				this.insertTeilhaberschaftAuspraegung(e.getID(), teilnutzer.getID(), id);
				e.setStatus(1);
				this.updateAuspraegung(e);
				i = 1;
			} else {
				i = 0;
				return i;
			}

		}

		for (Kontaktliste kl : allListsFromTeilNutzer) {

			if (kl.getBez().equals("Mit mir geteilte Kontakte")) {
				this.insertKontaktKontaktliste(k.getID(), kl.getID());
				k.setStatus(1);
				this.updateKontakt(k);

			} else {

			}

		}

		if (t.size() == 0) {
			this.insertTeilhaberschaftKontakt(k.getID(), teilnutzer.getID(), id);
		} else {

		}

		return i;
	}

	/**
	 * 
	 */
	@Override
	public int insertTeilhaberschaftAusgewaehlteAuspraegungenKontakt(Kontakt kon,
			Vector<EigenschaftAuspraegungWrapper> eaw, String selectedValue, int id) throws IllegalArgumentException {

		Nutzer teilnutzer = this.findNutzerByEmail(selectedValue);
		Kontakt k = this.findKontaktByID(kon.getID());
		Vector<Kontaktliste> allListsFromTeilNutzer = this.findKontaktlisteByNutzerID(teilnutzer.getID());
		Vector<Teilhaberschaft> t = this.findTeilhaberschaftByKontaktIDAndTeilhaberID(k.getID(), teilnutzer.getID());
		Integer i = null;

		for (EigenschaftAuspraegungWrapper ea : eaw) {
			Eigenschaftauspraegung eAus = this.getAuspraegungByID(ea.getAuspraegungID());
			if (this.findTeilhaberschaftByAuspraegungIDAndTeilhaberID(eAus.getID(), teilnutzer.getID()).size() < 1) {
				this.insertTeilhaberschaftAuspraegung(ea.getAuspraegungID(), teilnutzer.getID(), id);
				ea.getAuspraegung().setStatus(1);
				this.updateAuspraegung(ea.getAuspraegung());
				i = 1;
			} else {
				i = 0;
				return i;
			}
		}

		for (Kontaktliste kl : allListsFromTeilNutzer) {

			if (kl.getBez().equals("Mit mir geteilte Kontakte")) {

				if (this.findKontaktKontaktlisteByKontaktIDAndKlisteID(k.getID(), kl.getID()).getID() < 1) {
					this.insertKontaktKontaktliste(k.getID(), kl.getID());
					k.setStatus(1);
					this.updateKontakt(k);

				} else {

				}
			} else {

			}
		}

		if (t.size() < 1) {
			this.insertTeilhaberschaftKontakt(k.getID(), teilnutzer.getID(), id);
		} else {

		}

		return i;
	}

	/**
	 * 
	 */
	@Override
	public Teilhaberschaft insertTeilhaberschaftAuspraegung(int eigenschaftauspraegungID, int teilhaberID,
			int nutzerID) {
		Teilhaberschaft tAuspraegung = new Teilhaberschaft();
		tAuspraegung.setEigenschaftsauspraegungID(eigenschaftauspraegungID);
		tAuspraegung.setTeilhaberID(teilhaberID);
		tAuspraegung.setNutzerID(nutzerID);
		return this.teilhaberschaftMapper.insertTeilhaberschaftAuspraegung(tAuspraegung);
	}

	/**
	 * Eine Teilhaberschaft anhand der ID auslesen.
	 * 
	 * @param id der Primärschl�ssel der Teilhaberschaft
	 * @return Teilhaberschaft-Objekt
	 * @throws IllegalArgumentException
	 */

	@Override
	public Teilhaberschaft findByID(int id) throws IllegalArgumentException {
	
		return this.teilhaberschaftMapper.findByID(id);
	}
	
	/**
	 * 
	 */
	@Override
	public Teilhaberschaft findByTeilhaberschaftByKontaktlistIDAndTeilhaberID(int kontaktlisteID, int teilhaberID)
			throws IllegalArgumentException {
		return this.teilhaberschaftMapper.findByTeilhaberschaftByKontaktlistIDAndTeilhaberID(kontaktlisteID, teilhaberID);
	}


	/**
	 * Ausgabe saemtlicher Teilhaberschaften an einer Kontaktliste
	 * 
	 * @param kontaktlisteID
	 *            die ID der Kontaktliste deren Teilhaberschaften gesucht werden
	 * @return Vector mit saemtlichen Teilhaberschaften an einer Kontaktliste
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Teilhaberschaft> findTeilhaberschaftByKontaktlisteID(int kontaktlisteID)
			throws IllegalArgumentException {
		return this.teilhaberschaftMapper.findTeilhaberschaftByKontaktlisteID(kontaktlisteID);
	}

	/**
	 * 
	 */
	@Override
	public Vector<Teilhaberschaft> findTeilhaberschaftByTeilhaberIDAndNutzerID(int teilhaberID, int nutzerID)
			throws IllegalArgumentException {
		return this.teilhaberschaftMapper.findTeilhaberschaftByTeilhaberIDAndNutzerID(teilhaberID, nutzerID);
	}

	/**
	 * 
	 */
	@Override
	public Vector<Teilhaberschaft> findTeilhaberschaftByAuspraegungIDAndTeilhaberID(int auspraegungID, int teilhaberID)
			throws IllegalArgumentException {
		return this.teilhaberschaftMapper.findTeilhaberschaftByAuspraegungIDAndTeilhaberID(auspraegungID, teilhaberID);
	}

	/**
	 * Ausgabe aller Teilhaberschaften eines Nutzers
	 * 
	 * @param nutzerID
	 * @return Vector mit allen Teilhaberschaften eines Nutzers
	 */
	@Override
	public Vector<Teilhaberschaft> getAllTeilhaberschaftenFromUser(int nutzerID) {
		return this.teilhaberschaftMapper.findTeilhaberschaftByTeilhaberID(nutzerID);
	}

	/**
	 * Eine Teilhaberschaft loeschen.
	 * 
	 * @param t
	 *            das zu l�schende Teilhaberschaft-Objekt
	 * @throws IllegalArgumentException
	 */

	@Override
	public void deleteTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException {
		this.teilhaberschaftMapper.deleteTeilhaberschaft(t);

	}

	/**
	 * Eine Teilhaberschaft an einer Kontaktliste l�schen
	 * 
	 * @param kontaktlisteID
	 *            die ID der Kontaktliste, an der die Teilhaberchaft aufgel�st
	 *            werden soll
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteTeilhaberschaftByKontaktlisteID(int kontaktlisteID) throws IllegalArgumentException {
		this.teilhaberschaftMapper.deleteTeilhaberschaftByKontaktlisteID(kontaktlisteID);

	}

	/**
	 * L�schen einer Teilhaberschaft anhand der TeilhaberID
	 * 
	 * @param teilhaberID
	 *            die ID des Teilhabers dessen ID gel�scht werden soll
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteTeilhaberschaftByTeilhaberID(int teilhaberID) throws IllegalArgumentException {

		this.teilhaberschaftMapper.deleteTeilhaberschaftByTeilhaberID(teilhaberID);

	}

	/**
	 * Eine Teilhaberschaft an einem Kontakt loeschen.
	 * 
	 * @param t
	 *            das zu loeschende Teilhaber-Objekt
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteKontaktFromTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException {
		this.teilhaberschaftMapper.deleteKontaktFromTeilhaberschaft(t);

	}

	/**
	 * 
	 */
	@Override
	public void deleteUpdateTeilhaberschaft(Eigenschaftauspraegung ea, Nutzer teilhaber, Nutzer n, Kontakt k)
			throws IllegalArgumentException {
		this.deleteEigenschaftsauspraegungFromTeilhaberschaft(ea, teilhaber);
		Nutzer teilNutzer = this.findNutzerByID(teilhaber.getID());
		Nutzer nutzer = this.findNutzerByID(n.getID());
		Kontakt kon = this.findKontaktByID(k.getID());

		Vector<Teilhaberschaft> t = this.findTeilhaberschaftByAuspraegungID(ea.getID());


		if (t.isEmpty()) {
			ea.setStatus(0);
			this.updateAuspraegung(ea);

		} else {

		}
		if (this.findTeilhaberschaftByTeilhaberIDAndNutzerID(teilNutzer.getID(), nutzer.getID()).size() == 1) {
			this.deleteTeilhaberschaftByKontaktIDAndNutzerID(kon.getID(), teilNutzer.getID());
			this.updateKontaktStatus(kon, teilNutzer);

		} else {

		}
		if (this.findTeilhaberschaftByKontaktID(kon.getID()).size() < 1) {
			kon.setStatus(0);
			this.updateKontakt(kon);
		}

	}

	/**
	 * 
	 */
	@Override
	public void deleteTeilhaberschaftByKontaktIDAndNutzerID(int kontaktID, int teilNutzerID)
			throws IllegalArgumentException {
		this.teilhaberschaftMapper.deleteTeilhaberschaftByKontaktIDAndNutzerID(kontaktID, teilNutzerID);

	}
	
	/**
	 * 
	 */
	@Override
	public void deleteTeilhaberschaftAllByKontaktIDAndTeilhaberID(int kontaktID, int teilhaberID)
			throws IllegalArgumentException {
		
		Kontakt k = this.findKontaktByID(kontaktID);
		Nutzer teilhaber = this.findNutzerByID(teilhaberID);
		Kontaktliste kList = this.konlistMapper.findKontaktliste(teilhaber.getID(), "Mit mir geteilte Kontakte");
		this.teilhaberschaftMapper.deleteTeilhaberschaftByKontaktIDAndNutzerID(k.getID(), teilhaber.getID());
		
		this.deleteKontaktKontaktlisteByKontaktIDAndByKListID(k.getID(), kList.getID());
	
		Vector<EigenschaftAuspraegungWrapper> ea = this.findSharedAuspraegung(k.getID(), teilhaber.getID());
		
		for(EigenschaftAuspraegungWrapper eaw : ea){
			
			Eigenschaftauspraegung e = eaw.getAuspraegung();
			
			this.teilhaberschaftMapper.deleteTeilhaberschaftByAuspraegungIDAndTeilhaberID(e.getID(), teilhaber.getID());
			
			if(this.findTeilhaberschaftByAuspraegungID(e.getID()).size() < 1){
				
				e.setStatus(0);
				this.eigenschaftauspraegungMapper.updateAuspraegung(e);
			}
		}
		
		if(this.findTeilhaberschaftByKontaktID(k.getID()).size() < 1){
			
			k.setStatus(0);
			this.updateKontakt(k);
		}
		
		
		
	}
	
	/**
	 *  Es werden alle Teilhaberschaften an einem Kontakt gelöscht. 
	 *  Der Kontakt ist daraufhin mti keinem Nutzer mehr geteilt. 
	 *  Jegliche Teilhaberschaften an dem Kontakt und den dazugehörigen Ausprägungen wurden entfernt.
	 * @param k
	 * @param n
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteAllTeilhaberschaftenKontakt(Kontakt k, Nutzer n) throws IllegalArgumentException {
		
		Kontakt kon = this.findKontaktByID(k.getID());
		Vector<Teilhaberschaft> t = this.findTeilhaberschaftByKontaktID(k.getID());
		this.teilhaberschaftMapper.deleteTeilhaberschaftByKontaktID(k.getID());
		
		Vector<Eigenschaftauspraegung> a = this.findAllSharedAuspraegungenFromKontaktID(kon.getID());
		for (Eigenschaftauspraegung ae : a){
			this.teilhaberschaftMapper.deleteTeilhaberschaftByAuspraegungID(ae.getID());
			ae.setStatus(0);
			this.updateAuspraegung(ae);
		}
		for (Teilhaberschaft th: t){
			Kontaktliste kList = this.konlistMapper.findKontaktliste(th.getTeilhaberID(), "Mit mir geteilte Kontakte");
			this.deleteKontaktKontaktlisteByKontaktIDAndByKListID(kon.getID(), kList.getID());
		}
		kon.setStatus(0);
		this.updateKontakt(kon);
		
	}
	
	

	/**
	 * Eine Teilhaberschaft an einer Kontaktliste loeschen.
	 * 
	 * @param t
	 *            das zu loe�schende Teilhaberschaft-Objekt
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteKontaktlisteFromTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException {
		this.teilhaberschaftMapper.deleteKontaktlisteFromTeilhaberschaft(t);

	}

	/**
	 * Auslesen aller Teilhaberschaften, mit der übergebenen nutzerID und
	 * kontaktlisteID
	 * 
	 * @param nutzerID
	 *            die ID des angemeldeten Nutzers
	 * @param kontaktlisteID
	 *            die ID der gew�hlten kontaktlisteID
	 * @return Vector mit s�mtlichen Teilhaberschaften, mit den uebergebenen
	 *         Parametern
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Teilhaberschaft> findTeilhaberschaftByNutzerIDKontaktlisteID(int nutzerID, int kontaktlisteID)
			throws IllegalArgumentException {
		return this.teilhaberschaftMapper.findTeilhaberschaftenByKontaktlisteIDNutzerID(nutzerID, kontaktlisteID);

	}
	
	/**
	 *  Objekte vom Typ Teilhaberschaft anahnd des Kontaktes und des Teilhabers finden.
	 *  Es werden Objekte gesucht welche die Teilhaberschaft eines bestimmten Kontaktes mit einem bestimmten Nutzer darstellen.
	 *  
	 * @param kontaktID
	 * @param nutzerID
	 * @return Vektor mit Teilhaberschaftsobjekten
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Teilhaberschaft> findTeilhaberschaftByKontaktIDAndNutzerID(int kontaktID, int nutzerID)
			throws IllegalArgumentException {
		return this.teilhaberschaftMapper.findTeilhaberschaftByKontaktIDAndNutzerID(kontaktID, nutzerID);
	}

	/**
	 *  Objekte vom Typ Teilhaberschaft anhand des Kontaktes und des Teilhabers finden.
	 * @param auspraegungID
	 * @param teilhaberID
	 * @return Das Objekt vom Typ Teilhaberschaft
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Teilhaberschaft> findTeilhaberschaftByKontaktIDAndTeilhaberID(int kontaktID, int teilhaberID)
			throws IllegalArgumentException {
		return this.teilhaberschaftMapper.findTeilhaberschaftByKontaktIDAndTeilhaberID(kontaktID, teilhaberID);
	}

	/**
	 *  Alle Objekte vom Typ Teilhaberschaft anhand der auspraegungID finden 
	 * @param auspraegungID
	 * @return Vector mit allen Teilhaberschaften zu dieser Ausprägung
	 */
	@Override
	public Vector<Teilhaberschaft> findTeilhaberschaftByAuspraegungID(int auspraegungID)
			throws IllegalArgumentException {

		return this.teilhaberschaftMapper.findTeilhaberschaftByAuspraegungID(auspraegungID);
	}
	
	/**
	 * Löschen einer Teilhaberschaft an einer Kontaktliste.
	 * Gleichzeitig wird überprüft, ob noch Teilhaberschaften an der Kontaktliste bestehen, ansonsten wird der Status der Kontaktliste
	 * auf 0 (= nicht geteilt) gesetzt und die Kontaktliste geupdated.
	 * 
	 * @param teilhaberID, die ID des Teilhabers, dessen Teilhaberschaft aufgelöst werden muss
	 * @param kontaktlisteID, die ID der Liste, an der die Teilhaberschaft gelöscht werden soll
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteTeilhaberschaftAnKontaktliste(int teilhaberID, int kontaktlisteID)
			throws IllegalArgumentException {
		Nutzer teilhaber = this.findNutzerByID(teilhaberID);
		Kontaktliste kla = this.findKontaktlisteByID(kontaktlisteID);
		this.teilhaberschaftMapper.deleteTeilhaberschaftByKontaktlisteIDAndTeilhaberID(kla.getID(), teilhaber.getID());
		
		Vector<Teilhaberschaft> teilhaben = findTeilhaberschaftByKontaktlisteID(kontaktlisteID);
		if (teilhaben.isEmpty()) {
			Kontaktliste kl = findKontaktlisteByID(kontaktlisteID);
			kl.setStatus(0);
			this.updateKontaktliste(kl);
		}
		
		
	}

	/**
	 *  Objekte vom Typ Teilhaberschaft anhand der Auspägung und des Teilhabers finden.
	 * @param auspraegungID
	 * @param teilhaberID
	 * @return Das Objekt vom Typ Teilhaberschaft
	 * @throws IllegalArgumentException
	 */
	@Override
	public Teilhaberschaft findTeilhaberschaftByAuspraegungIdAndTeilhaberId(int auspraegungID, int nutzerID)
			throws IllegalArgumentException {
		
		return this.teilhaberschaftMapper.findTeilhaberschaftByAuspraegungIdAndTeilhaberId(auspraegungID, nutzerID);
	}



	/*
	 * ########################################################## 
	 * ENDE Methoden fuer Teilhaberschaft-Objekte
	 * ##########################################################
	 */
	
	/*
	 * ########################################################## 
	 * START Methoden fuer Kontakt-Objekte
	 * #########################################################
	 */

	@Override
	public Vector<Eigenschaftauspraegung> getAuspraegungByKontaktID(int id) throws IllegalArgumentException {

		return this.eigenschaftauspraegungMapper.getAuspraegungByKontaktID(id);
	}

	public void deleteEigenschaftUndAuspraegung(EigenschaftAuspraegungWrapper ea) throws IllegalArgumentException {
		Eigenschaftauspraegung eaa = new Eigenschaftauspraegung();
		eaa.setID(ea.getAuspraegungID());
		List<Teilhaberschaft> teilhaberschaft = teilhaberschaftMapper.findTeilhaberschaftByAuspraegungID(eaa.getID());
		if (teilhaberschaft != null) {
			for (Teilhaberschaft th : teilhaberschaft) {
				teilhaberschaftMapper.deleteTeilhaberschaftByAuspreaegungID(eaa.getID());
			}
		}

		

		this.eigenschaftauspraegungMapper.deleteAuspraegung(eaa);

	}
	

	@Override

	public Vector<EigenschaftAuspraegungWrapper> findHybrid(Person pers) throws IllegalArgumentException {

		Vector<Eigenschaftauspraegung> eigaus = getAuspraegungByKontaktID(pers.getID());

		Vector<Eigenschaft> eig = new Vector<Eigenschaft>();

		for (Eigenschaftauspraegung eigenschaftauspraegung : eigaus) {
			eig.add(getEigenschaftByID(eigenschaftauspraegung.getEigenschaftsID()));
		}

		Vector<EigenschaftAuspraegungWrapper> hybrid = new Vector<EigenschaftAuspraegungWrapper>();

		for (int i = 0; i < eigaus.size(); i++) {

			for (int z = 0; z < eigaus.size(); z++) {
				if (eigaus.elementAt(i).getEigenschaftsID() == eig.elementAt(z).getID()) {

					hybrid.add(new EigenschaftAuspraegungWrapper(eig.elementAt(z), eigaus.elementAt(i)));

					break;
				}

			}
		}
		return hybrid;
	}	

	
	/**
	 *  Alle Objekte vom Typ Ausprägungen anhand der Kontakt und der Nutzer ID selektieren.
	 *  Es werden die Ausprägungen gefunden welche mit diesem Nutzer von diesem Kontatk geteitl wurden.
	 * @param kontaktID
	 * @param nutzerID
	 * @return Vector mit allen Ausprägungen zu dem bestimmten Kontakt und Nutzer.
	 * @throws IllegalArgumentException
	 */

	@Override
	public Vector<EigenschaftAuspraegungWrapper> findSharedAuspraegung(int kontaktID, int nutzerID)
			throws IllegalArgumentException {
		
		Nutzer teilhaber = this.findNutzerByID(nutzerID);
		Vector<Eigenschaftauspraegung> eigausShared = this.findAllSharedAuspraegungenFromKontaktID(kontaktID);
		
		Vector<Eigenschaftauspraegung> eigausSharedFromTeilhaber = new Vector<Eigenschaftauspraegung>();
		
		for(Eigenschaftauspraegung e : eigausShared){
			if(this.findTeilhaberschaftByAuspraegungIdAndTeilhaberId(e.getID(), teilhaber.getID()) == null){
				
			}else{
				eigausSharedFromTeilhaber.add(e);
			}
		}
		
		Vector<Eigenschaft> eig = new Vector<Eigenschaft>();
		
		for (Eigenschaftauspraegung eigenschaftauspraegung : eigausSharedFromTeilhaber) {
			eig.add(getEigenschaftByID(eigenschaftauspraegung.getEigenschaftsID()));
		}
		
		Vector<EigenschaftAuspraegungWrapper> hybrid = new Vector<EigenschaftAuspraegungWrapper>();
		
		for (int i = 0; i < eigausSharedFromTeilhaber.size(); i++) {

			for (int z = 0; z < eigausSharedFromTeilhaber.size(); z++) {
				if (eigausSharedFromTeilhaber.elementAt(i).getEigenschaftsID() == eig.elementAt(z).getID()) {

					hybrid.add(new EigenschaftAuspraegungWrapper(eig.elementAt(z), eigausSharedFromTeilhaber.elementAt(i)));

					break;
				}

			}
		}
		return hybrid;

	
	}

	/**
	 *  Eine Eigenschaft anhand der Bezeichnung auslesen.
	 * @param bez Bezeichnung der Eigenschaft
	 * @return Eigenschaft-Objekt
	 * @throws IllegalArgumentException
	 */

	@Override
	public Vector<Eigenschaft> getEigenschaftByBezeichnung(String bez) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.eigMapper.getEigenschaftByBezeichnung(bez);
	}

	/**
	 * Die Basis-Kontaktliste "Meine Kontakte" anhand der NutzerID finden.
	 * @param nutzerID
	 * @return Objekt vom Typ Kontaktliste
	 * @throws IllegalArgumentException
	 */
	
	@Override
	public Kontaktliste findBasicKontaktliste(int nutzerID) throws IllegalArgumentException {
		return this.konlistMapper.findBasicKontaktliste(nutzerID);

	}

	
	public KontaktKontaktliste createKontaktinBasicKontakliste(Kontaktliste kl, Kontakt k)
			throws IllegalArgumentException {

		KontaktKontaktliste kkl = new KontaktKontaktliste();

		kkl.setKontaktlisteID(kl.getID());
		kkl.setKontaktID(k.getID());
		return kontaktKontaktlisteMapper.insertKontaktKontaktliste(kkl);

	}

	/**
	 * Eine Kontaktliste anhand der ID auslesen.
	 * 
	 * @param id
	 *            der Primärschl�ssel des Kontaktlisten-Objekts
	 * @return Kontaktlisten-Objekt
	 * @throws IllegalArgumentException
	 */
	
	@Override
	public Kontaktliste findKontaktliste(int nutzerID, String bez) throws IllegalArgumentException {
		return this.konlistMapper.findKontaktliste(nutzerID, bez);
	}

	/**
	 * Auslesen einer Teilhaberschaft anhand einer TeilhaberID
	 * 
	 * @param teilhaberID
	 *            die ID des Teilhabers
	 * @return gesuchte Teilhaberschaft
	 * @throws IllegalArgumentException
	 */
	@Override
	public Teilhaberschaft findTeilhaberschaftByTeilhaberID(int teilhaberID, int kontaktlisteID)
			throws IllegalArgumentException {
		return this.teilhaberschaftMapper.findByTeilhaberIDKontaktlisteID(teilhaberID, kontaktlisteID);
	}

	/**
	 *   Objekte vom Typ Kontaktliste mit Ausnamhe der Basis Listen "Meine Kontakte" und
	 *    "Meine geteilten Kontakte" anahnd eines Nutzer finden.
	 * @param nutzerID
	 * @return  Objekte vom Typ Kontaktliste
	 * @throws IllegalArgumentException
	 */
	
	public Vector<Kontaktliste> findKontaktlisteByNutzerIDexceptBasicList(int nutzerID)
			throws IllegalArgumentException {

		Vector<Kontaktliste> alleListen = this.getAllKontaktlistenFromUser(nutzerID);

		for (Kontaktliste kontaktliste : alleListen) {

			if (kontaktliste.getBez().equals("Meine Kontakte")
					|| kontaktliste.getBez().equals("Mit mir geteilte Kontakte")) {

				alleListen.remove(kontaktliste);

			}
		}

		

		return alleListen;

	}
	

	@Override
	public Vector<Kontakt> findKontaktByNameAndNutzerID(Kontakt kontakt) throws IllegalArgumentException {
		Vector<Kontakt> result = new Vector<Kontakt>();
		this.konMapper.findKontaktByNameUndNutzerID(kontakt);
		
		
		return result;

	}
	

	/**
	 * Auslesen aller eigenen und geteilten Kontakte durch name und nutzerid
	 * 
	 * @param kontaktobjekt
	 * @return vector aller eigenen und geteilten kontakten mit dem gesuchten name
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> findGesuchteKontakte(Kontakt kontakt) throws IllegalArgumentException{
		Vector<Kontakt> ergebnis = this.konMapper.findKontaktByNameUndNutzerID(kontakt);
		Vector<Kontakt> empfangeneKontakte = findAllSharedKontakteVonNutzer(kontakt.getNutzerID());
		Vector<Kontakt> eigeneKontakte = findAllEigeneKontaktFromNutzer(kontakt.getNutzerID());
		Vector<Kontakt> kontaktErgebnis = new Vector<Kontakt>();
		
		for (Kontakt alle : ergebnis) {
			for (Kontakt empfangene : empfangeneKontakte) {
				if(alle.getID() == empfangene.getID()){
					kontaktErgebnis.add(empfangene);
				}
			}
		}
		for (Kontakt alle : ergebnis) {
			for (Kontakt eigene : eigeneKontakte) {
				if(alle.getID() == eigene.getID()){
					kontaktErgebnis.add(eigene);
				}
			}
		}
	
		return kontaktErgebnis;
	}


	
	/**
	 * Einen Kontakt anhand seiner KontaktID in der Auspraegung anzeigen lassen.
	 * 
	 * @param id,
	 *            
	 * @return Kontakt-Objekt mit der übergebenen kontaktID
	 * @throws IllegalArgumentException
	 * */

	public Kontakt findKontaktByAuspraegungID(int id) throws IllegalArgumentException{
		
		
		Eigenschaftauspraegung eigaus = this.getAuspraegungByID(id);
		Kontakt k = this.findKontaktByID(eigaus.getKontaktID());
		
		return k;
	}
	
	
	/**
	 * Einen Kontaktnamen anhand seiner KontaktID in der Auspraegung anzeigen lassen.
	 * 
	 * @param id,
	 *            
	 * @return Kontakt-Objekt mit der übergebenen kontaktID
	 * @throws IllegalArgumentException
	 * */
	public String findEinenKontaktByAuspraegungID(int id) throws IllegalArgumentException{
		
		
		Eigenschaftauspraegung eigaus = this.getAuspraegungByID(id);
		Kontakt k = this.findKontaktByID(eigaus.getKontaktID());
		
		return k.getName();
	}
	
	/**
	 *  Auslesen eines Objektes vom Typ Eigenschaft anhand der Bezeichnung 
	 * @param bez
	 * @return ein Objekt vom Typ Eigenschaft
	 * @throws IllegalArgumentException
	 */
	
	@Override
	public Eigenschaft findEigByBezeichnung(String bez) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.eigMapper.findEigByBezeichnung(bez);
	}

	/**
	 * Eine Eigenschaftsauspraegung anhand des Wertes auslesen.
	 * 
	 * @param wert,
	 *            der die Auspraegung beschreibt
	 * @return Eigenschafts-Objekt mit gesuchtem Wert
	 * @throws IllegalArgumentException
	 */
	
	@Override
	public Vector<EigenschaftAuspraegungWrapper> getAuspraegungByWert(String wert) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		Vector<Eigenschaftauspraegung> liste = eigenschaftauspraegungMapper.getAuspraegungByWert(wert);
		Vector<EigenschaftAuspraegungWrapper> wrapper = new Vector<EigenschaftAuspraegungWrapper>();
		
		for (Eigenschaftauspraegung eigenschaftauspraegung : liste) {
			wrapper.add(new EigenschaftAuspraegungWrapper(getEigenschaftByID(eigenschaftauspraegung.getEigenschaftsID()), eigenschaftauspraegung, findKontaktByID(eigenschaftauspraegung.getKontaktID())));
		}

		
		
		return wrapper;
	}

	/**
	 *  Objekte vom Typ Teilhaberschaft anahnd des Kontaktes und des Teilhabers finden.
	 *  Es werden Objekte gesucht welche die Teilhaberschaft eines bestimmten Kontaktes mit einem bestimmten Nutzer darstellen.
	 *  
	 * @param kontaktID
	 * @param nutzerID
	 * @return Vektor mit Teilhaberschaftsobjekten
	 * @throws IllegalArgumentException
	 */
	
	@Override
	public Vector<Teilhaberschaft> findTeilhaberschaftByKontaktID(int kontaktID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.teilhaberschaftMapper.findTeilhaberschaftByKontaktID(kontaktID);
	}


	/**
	 *   Objekte vom Typ Kontakt anhand des Eigentümers und des Teilhabers selektieren. 
	 * @param nutzerID
	 * @param teilhaberID
	 * @return Ein Vektor mit den Kontakt-Objekten
	 * @throws IllegalArgumentException
	 */
	
	public Vector<Kontakt> findGeteilteKontakteFromNutzerAndTeilhaber(int nutzerID, int teilhaberID)
			throws IllegalArgumentException {

		Vector<Teilhaberschaft> teilVec = teilhaberschaftMapper.findGeteilteKontakteFromNutzerAndTeilhaber(teilhaberID,
				nutzerID);
		Vector<Kontakt> teilKon = new Vector<Kontakt>();

		for (Teilhaberschaft teilhaberschaft : teilVec) {
			teilKon.add(findKontaktByID(teilhaberschaft.getKontaktID()));

		}

		return teilKon;

	}

	
	/**
	 *   Objekte vom Typ Kontakt anhand des Eigentümers, der Eigenschaft und der Ausprägung finden. 
	 * @param nutzerID
	 * @param teilhaberID
	 * @return Ein Vektor mit den Kontakt-Objekten
	 * @throws IllegalArgumentException
	 */
	
	public Vector<Kontakt> findKontakteByEigAus(int NutzerID, String bez, String wert) throws IllegalArgumentException {
		Vector<Kontakt> gepruefteKontakte = new Vector<Kontakt>();
		Vector<Kontakt> kontakte = findAllKontaktFromNutzer(NutzerID);

		for (Kontakt kontakt : kontakte) {

			Vector<Eigenschaft> eigenschaften = this.getEigenschaftbyKontaktID(kontakt.getID());

			for (Eigenschaft eigenschaft : eigenschaften) {
				if (eigenschaft.getBezeichnung().equals(bez)) {

					Vector<Eigenschaftauspraegung> auspraegungen = this.getAuspraegungByKontaktID(kontakt.getID());
					for (Eigenschaftauspraegung eigenschaftauspraegung : auspraegungen) {
						if (eigenschaftauspraegung.getWert().equals(wert)) {
							gepruefteKontakte.add(kontakt);
						}
					}
				}
			}
		}

		return gepruefteKontakte;
	}


	
	/**
	 *   Objekte vom Typ Kontakt anhand des Eigentümers und der Eigenschaft finden. 
	 * @param nutzerID
	 * @param teilhaberID
	 * @return Ein Vektor mit den Kontakt-Objekten
	 * @throws IllegalArgumentException
	 */

	public Vector<Kontakt> findKontakeByEig(int NutzerID, String bez) throws IllegalArgumentException {
		Vector<Kontakt> gepruefteKontakte = new Vector<Kontakt>();
		Vector<Kontakt> kontakte = findAllKontaktFromNutzer(NutzerID);

		for (Kontakt kontakt : kontakte) {

			Vector<Eigenschaft> eigenschaften = this.getEigenschaftbyKontaktID(kontakt.getID());

			for (Eigenschaft eigenschaft : eigenschaften) {
				if (eigenschaft.getBezeichnung().equals(bez)) {

					gepruefteKontakte.add(kontakt);
				}
			}
		}

		return gepruefteKontakte;
	}
	
	
	/**
	 *   Objekte vom Typ Kontakt anhand des Eigentümers und der Ausprägung finden. 
	 * @param nutzerID
	 * @param teilhaberID
	 * @return Ein Vektor mit den Kontakt-Objekten
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> findKontakteByAus(int NutzerID, String wert) throws IllegalArgumentException {
		Vector<Kontakt> gepruefteKontakte = new Vector<Kontakt>();
		Vector<Kontakt> kontakte = findAllKontaktFromNutzer(NutzerID);

		for (Kontakt kontakt : kontakte) {


					Vector<Eigenschaftauspraegung> auspraegungen = this.getAuspraegungByKontaktID(kontakt.getID());
					for (Eigenschaftauspraegung eigenschaftauspraegung : auspraegungen) {
						if (eigenschaftauspraegung.getWert().equals(wert)) {
							gepruefteKontakte.add(kontakt);
						}
					}
				}
	
		return gepruefteKontakte;
	}
	
	

	







}
