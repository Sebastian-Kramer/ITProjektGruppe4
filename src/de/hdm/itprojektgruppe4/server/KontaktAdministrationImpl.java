package de.hdm.itprojektgruppe4.server;

import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Collections;
import java.text.SimpleDateFormat;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektgruppe4.server.db.*;
import de.hdm.itprojektgruppe4.server.db.KontaktlisteMapper;
import de.hdm.itprojektgruppe4.shared.KontaktAdministration;
import de.hdm.itprojektgruppe4.shared.bo.*;

@SuppressWarnings("serial")
public class KontaktAdministrationImpl extends RemoteServiceServlet 
	implements KontaktAdministration{

	/**
     * Default constructor
     */ 
    public KontaktAdministrationImpl() {
    }
    /**
     * Referenz auf den NutzerMapper, der Nutzer-Objekte mit der Datenbank abgleicht.
     */
    private NutzerMapper nutzerMapper = null;
    
    /**
     * Referenz auf den KontaktMapper, der Kontakt-Objekte mit der Datenbank abgleicht.
     */
    private KontaktMapper konMapper = null;
    
    /**
     * Referenz auf den PersonMapper, der Person-Objekte mit der Datenbank abgleicht.
     */
    private PersonMapper persMapper = null;

    /**
     * Referenz auf den KontaktlisteMapper, der Kontakt-Objekte mit der Datenbank abgleicht.
     */
    private KontaktlisteMapper konlistMapper = null;
    
    /**
     * Referenz auf den EigenschaftMapper, der Eigenschaft-Objekte mit der Datenbank abgleicht.
     */
    private EigenschaftMapper eigMapper = null;
    
    /**
     * Referenz auf den EigenschaftauspragungMapper, der Eigenschaftauspraegung-Objekte mit der Datenbank abgleicht.
     */
    private EigenschaftauspraegungMapper eigenschaftauspraegungMapper = null;
  
    /**
     * Referenz auf den TeilhaberschaftMapper, der Teilhaberschaft-Objekte mit der Datenbank abgleicht.
     */
    private TeilhaberschaftMapper teilhaberschaftMapper = null;
    
    /**
     * Referenz auf den KontaktKontaktlisteMapper, der KontaktKontaktliste-Objekte mit der Datenbank abgleicht.
     */
    private KontaktKontaktlisteMapper kontaktKontaktlisteMapper = null;
    
    /*
	   * ***************************************************************************
	   * ABSCHNITT, Beginn: Initialisierung
	   * ***************************************************************************
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

    
    
    /*##########################################################
     * START Methoden f�r Kontakt-Objekte
     #########################################################*/

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
    
    public Kontakt  insertKontakt(String name, Date erzeugungsdatum, Date modifikationsdatum, int status, int nutzerID)  	
    		throws IllegalArgumentException {
        // TODO implement here
 
    	Kontakt k = new Kontakt();
    	
    	//k.setID(1);
    	k.setName(name);
    	k.setErzeugungsdatum(erzeugungsdatum);
    	k.setModifikationsdatum(modifikationsdatum);
    	k.setStatus(status);
    	k.setNutzerID(nutzerID);
    	
    	
//    	for(int i = 1;  i<5; i++) {
//    	
//    		
//    		
//    	Eigenschaftauspraegung ea = new Eigenschaftauspraegung();
//    	ea.setID(k.getID());
//    	ea.setWert("");
//    	ea.setStatus(ea.getStatus());
//    	ea.setKontaktID(k.getID());
//    	ea.setEigenschaftsID(i);
//    	
//    	eigenschaftauspraegungMapper.insertAuspraegung(ea);
//    	
//    	}
    	
    	
        return this.konMapper.insertKontakt(k);
        
        
        
    }
    
    
   
    
    
    
    public Vector<Eigenschaftauspraegung> insertBasicAuspraegung(String wert, int status, int kontaktID)
    		throws IllegalArgumentException{
    	
    	
    	Vector<Eigenschaftauspraegung> vector = new Vector<Eigenschaftauspraegung>();
    	
    	Eigenschaftauspraegung ea1 = insertAuspraegung("Vorname", 0, 1, kontaktID);
    	Eigenschaftauspraegung ea2 = insertAuspraegung("Nachname", 0, 2, kontaktID);
    	Eigenschaftauspraegung ea3 = insertAuspraegung("Email", 0, 3, kontaktID);
    	Eigenschaftauspraegung ea4 = insertAuspraegung("Geburtsdatum", 0, 4, kontaktID);
    	
    	vector.add(ea1);
    	vector.add(ea2);
    	vector.add(ea3);
    	vector.add(ea4);
    	
		
		return vector;
    	
    	
    } 
   
    
    /**
     * Einen Kontakt anhand seines Primaerschluessel anzeigen lassen.
     * 
     * @param id der Primaerrschluessel des Objekts
     * @return Kontakt-Objekt mit dem uebergebenen Primaerschluessel
     * @throws IllegalArgumentException
     */

    //pr�feb
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
     * @param name, der Name des Kontakts
     * @return Kontakt-Objekt mit dem �bergebenen Namen
     * @throws IllegalArgumentException
     */

	@Override
	public Kontakt findKontaktByName(String name) throws IllegalArgumentException {
		return this.konMapper.findKontaktByName(name);
	}
	
	
	/**
     * �berschreiben eines <code>Kontakt</code>-Objekts.
     * 
     * @param k das zu bearbeitende Kontakt-Objekt
     * @return das bearbeitete Kontakt-Objekt
     * @throws IllegalArgumentException
     */
	@Override
	public Kontakt updateKontakt(Kontakt k) throws IllegalArgumentException {
		
		return this.konMapper.updateKontakt(k);
	}

	/**
     * Einen Kontakt l�schen
     * 
     * @param k das zu l�schende Kontakt-Objekt
     * @throws IllegalArgumentException
     */
	
	@Override
	public void deleteKontakt(Kontakt k) throws IllegalArgumentException {

		List<Eigenschaftauspraegung> eaList = eigenschaftauspraegungMapper.findAuspraegungByKontaktID(k.getID());
		List<KontaktKontaktliste> kontaktKontakliste = kontaktKontaktlisteMapper.findKontaktKontaktlisteByKontaktID(k.getID());
		List<Teilhaberschaft> teilhaberschaft = teilhaberschaftMapper.findTeilhaberschaftByKontaktID(k.getID());
		
		
		if (eaList != null){
			for (Eigenschaftauspraegung ea : eaList){
				
				eigenschaftauspraegungMapper.deleteAuspraegung(ea);
			}
		}
		
		if (kontaktKontakliste != null){ 
			for (KontaktKontaktliste kkliste : kontaktKontakliste){
				kontaktKontaktlisteMapper.deleteKontaktKontaktlisteByKontaktID(kkliste.getKontaktID());
			}
		}
		
		if (teilhaberschaft != null){ 
			for (Teilhaberschaft th : teilhaberschaft){
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
     * 
     * 
     * @return
     * @throws IllegalArgumentException
     */
	
	@Override
	public List<Kontakt> findAllKontaktNames() throws IllegalArgumentException {
		return this.konMapper.findAllKontakte();
	}

	/**
	 * 
	 * @param i
	 * @return Vector mit saemtlichen Kontakten einer ausgewaehlten Kontaktliste
	 * @throws IllegalArgumentException
	 */
	
	@Override
	public Vector<Integer> findAllKontakteFromKontaktliste(int i) throws IllegalArgumentException {
		return konMapper.findAllKontakteFromKontaktliste(i);
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


	
    /*##########################################################
     * ENDE Methoden f�r Kontakt-Objekte
     #########################################################*/

    /*##########################################################
     * START Methoden f�r Nutzer-Objekte
     #########################################################*/
	/**
     * Einen Nutzer anlegen
     * 
     * @param mail
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
     * L�schen eines Nutzers.
     * 
     * @param n das zu l�schende Nutzer-Objekt
     * @throws IllegalArgumentException
     */
	
	@Override
	public void deleteNutzer(Nutzer n) throws IllegalArgumentException {
		this.nutzerMapper.deleteNutzer(n);
		this.persMapper.deletePerson(this.persMapper.findPersonByID(n.getID()));
	}

	/**
     * �berschreiben eines Nutzer-Objekts.
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
     * @param email
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
     * @param id der Prim�rschl�ssel des Nutzer-Objekts
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
     * @return Vector s�mtlicher Nutzer
     * @throws IllegalArgumentException
     */

//	@Override
//	public Nutzer findNutzerByID(int id) throws IllegalArgumentException {
//		return this.nutzerMapper.findNutzerByID(id);
//	}


	@Override
	public Vector<Nutzer> findAllNutzer() throws IllegalArgumentException {
		return this.nutzerMapper.findAllNutzer();
	}
	
    /*##########################################################
     * ENDE Methoden f�r Nutzer-Objekte
     #########################################################*/
	
    /*##########################################################
     * START Methoden f�r Person-Objekte
     #########################################################*/
	
	
	/**
     * Eine Person anhand der ID auslesen.
     * 
     * @param ID, der Prim�rschl�ssel
     * @throws IllegalArgumentException
     */
	
	@Override
	public Person findPersonByID(int ID) throws IllegalArgumentException {
		return this.findPersonByID(ID);
	}
	
	
	 /**
     * Loeschen einer Person.
     * 
     * @param p das zu l�schende Personen-Objekt
     * @throws IllegalArgumentException
     */
	
	@Override
	public void deletePerson(Person p) throws IllegalArgumentException {
		
		 this.persMapper.deletePerson(p);
		
	}
	
    /*##########################################################
     * ENDE Methoden f�r Person-Objekte
     #########################################################*/
	
    /*##########################################################
     * START Methoden fuer Eigenschaft-Objekte
     #########################################################*/
	
	  /**
     * Eine Eigenschaft anlegen.
     * 
     * @param bez, die Bezeichnung der Eigenschaft
     * @param status, Status der anzeigt, ob die Eigenschaft geteilt wurde oder nicht
     * @return Eigenschaft Objekt
     * @throws IllegalArgumentException
     */
	
	@Override
	public Eigenschaft insertEigenschaft(String bez, int status) throws IllegalArgumentException {
		Eigenschaft e = new Eigenschaft();
		
		e.setBezeichnung(bez);
		e.setStatus(status);
		
		return this.eigMapper.insertEigenschaft(e);
	}
    
	/**
     *  Alle Objekte vom Typ Eigenschaft ausgeben
     * @return
     * @throws IllegalArgumentException
     */
    
	@Override
	public Vector<Eigenschaft> findAllEigenschaft() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.eigMapper.findAllEigenschaft();
	
    }

    //pr�fen
    public Vector<Eigenschaft> getAllEigenschaftVonKontakt(Kontakt k) {
        // TODO implement here
        return null;
    }
    /**
     * Eine Eigenschaft anhand der ID auslesen.
     * 
     * @param id, Primaerrschluessel der Eigenschaft
     * @return Eigenschaft-Objekt
     * @throws IllegalArgumentException
     */
    
	@Override
	public Eigenschaft getEigenschaftByID(int id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.eigMapper.getEigenchaftByID(id);
	}
	   /**
     * Eine Eigenschaft �berschreiben.
     * 
     * @param e das Eigenschaft-Objekt
     * @return
     * @throws IllegalArgumentException
     */
	
	@Override
	public Eigenschaft updateEigenschaft(Eigenschaft e) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.eigMapper.updateEigenschaft(e);
	}
	 /**
     * Eine Eigenschaft l�schen.
     * 
     * @param e das zu l�schende Eigenschaft-Objekt
     * @throws IllegalArgumentException
     */

	@Override
	public void deleteEigenschaft(Eigenschaft e) throws IllegalArgumentException {
		this.eigMapper.deleteEigenschaft(e);
		
	}
	
    /*##########################################################
     * ENDE Methoden f�r Eigenschaft-Objekte
     #########################################################*/
	
    /*##########################################################
     * START Methoden f�r Eigenschaftauspragung-Objekte
     #########################################################*/
	
	/**
	 * 
	 * @param wert, die Auspraegung der Eigenschaft
	 * @param status, zeigt an ob die Auspraegung geteilt wurde oder nicht
	 * @param eigenschaftsID, Fremschl�sselbeziehung zur Eigenschaft
	 * @param kontaktID, Fremdschl�sselbeziehung zum Kontakt auf den sich die Eigenschaft bezieht
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
			return this.eigenschaftauspraegungMapper.updateAuspraegung(ea);
		}

	/**
     * Eine Eigenschaftsauspraegung l�schen.
     * 
     * @param ea das Eigenschaftsauspraegung-Objekt
     * @throws IllegalArgumentException
     */
	
	@Override
	public void deleteAuspraegung(Eigenschaftauspraegung ea) throws IllegalArgumentException {
			this.eigenschaftauspraegungMapper.deleteAuspraegung(ea);
		
		}
	
	  /**
     * Eine Eigenschaftsauspraegung anhand der ID auslesen.
     * 
     * @param id der Prim�rschl�ssel der Auspraegung
     * @return Eigenschaftsauspraegung-Objekt
     * @throws IllegalArgumentException
     */
	
	//pr�fen
	@Override
	public Eigenschaftauspraegung getAuspraegungByID(int id) throws IllegalArgumentException {
			return this.eigenschaftauspraegungMapper.getAuspraegungByID(id);
		
		}
	
	/**
     * Eine Teilhaberschaft an einer Eigenschaftsauspraegung l�schen.
     * 
     * @param t das zu l�schende Teilhaberschaftobjekt
     * @throws IllegalArgumentException
     */
		
	@Override
	public void deleteEigenschaftsauspraegungFromTeilhaberschaft(Teilhaberschaft t)
				throws IllegalArgumentException {
			this.teilhaberschaftMapper.deleteEigenschaftsauspraegungFromTeilhaberschaft(t);
			
		}
	
	/**
     * Eine Eigenschaftsauspraegung anhand des Wertes auslesen.
     * 
     * @param wert, der die Auspraegung beschreibt
     * @return Eigenschafts-Objekt mit gesuchtem Wert
     * @throws IllegalArgumentException
     */
	
	//pr�fen
	@Override
	public Eigenschaftauspraegung getAuspraegungByWert(String wert) throws IllegalArgumentException {
			
			return null;
		}
	

	@Override
	public Vector<Eigenschaftauspraegung> findEigenschaftauspraegungByKontaktID(int kontaktID)
			throws IllegalArgumentException {
		
		return this.eigenschaftauspraegungMapper.findAuspraegungByKontaktID(kontaktID);
	}
		
		/*##########################################################
	     * ENDE Methoden f�r Eigenschaftauspragung-Objekte
	     #########################################################*/

		/*##########################################################
	     * START Methoden f�r Kontaktliste-Objekte
	     #########################################################*/
	
	   /**
	    * 
	    * @param bez, die Bezeichnung der Kontaktliste
	    * @param status, der Status, der anzeigt ob die Kontaktliste geteilt wurde oder nicht
	    * @param nutzerID, der Fremdschl�ssel stellt die Beziehung zum Ersteller dar
	    * @return Kontaktlisten-Objekt
	    * @throws IllegalArgumentException
	    */
	
	@Override
	public Kontaktliste insertKontaktliste(String bez, int status, int nutzerID) throws IllegalArgumentException {
			Kontaktliste k = new Kontaktliste();
			
			k.setBez(bez);
			k.setStatus(status);
			k.setNutzerID(nutzerID);
			
			return this.konlistMapper.insertKontaktliste(k);
		}
	

	
	
	public Vector<Kontaktliste> getAllKontaktlisten() {
        
        return null;
		}
	
	/**
     * Eine Kontaktliste anhand der ID auslesen.
     * 
     * @param id der Prim�rschl�ssel des Kontaktlisten-Objekts
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
     * @param bezeichnung, die Bezeichnung der Kontaktliste
     * @return Kontaktlisten-Objekt
     * @throws IllegalArgumentException
     */
	
	@Override
	public Kontaktliste findKontaktlisteByBezeichnung(String bezeichnung) throws IllegalArgumentException {
		return this.konlistMapper.findKontaktlistebyBezeichnung(bezeichnung);
		}

	 /**
     * Eine Kontaktliste �berschreiben.
     * 
     * @param k das zu bearbeitende Kontaktlisten-Objekt
     * @return 
     * @throws IllegalArgumentException
     */

	@Override
	public Kontaktliste updateKontaktliste(Kontaktliste k) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.konlistMapper.updateKontaktliste(k);
		}

	/**
     * Eine Kontaktliste  l�schen.
     * 
     * @param k das zu l�schende Kontaktlisten-Objekt
     * @throws IllegalArgumentException
     */
	
	@Override
	public void deleteKontaktliste(Kontaktliste k) throws IllegalArgumentException {
		this.konlistMapper.deleteKontaktliste(k);
	
	}
	
	/**
     * Alle Kontaktlisten auslesen.
     * 
     * @return Vector s�mtlicher Kontaktlisten
     * @throws IllegalArgumentException
     */

	@Override
	public Vector<Kontaktliste> findKontaktlisteAll() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.konlistMapper.findKontaktlisteAll();
	}
	
	 /**
     * Aufhebung der Beziehung zwischen Kontakt und Kontaktliste.
     * 
     * @param k das zu l�schende KontaktKontaktliste-Objekt
     * @throws IllegalArgumentException
     */
	
	@Override
	public void deleteKontaktKontaktliste(KontaktKontaktliste k) 
			throws IllegalArgumentException {
		this.kontaktKontaktlisteMapper.deleteKontaktKontaktliste(k);
		
	}
		

	 /**
     * Auslesen der Kontaktlisten anhand der ID des Kontaktlistenerstellers
     * 
     * @param nutzerID
     * @return Vector mit s�mtlichen Kontaktlisten mit der �bergebenen NutzerID
     * @throws IllegalArgumentException
     */
	
	@Override
	public Vector<Kontaktliste> findKontaktlisteByNutzerID(int nutzerID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.konlistMapper.findKontaktlisteByNutzerID(nutzerID);
	}

	

	 /**
     * Auslesen aller erstellten und geteilten Kontaktlisten eines Nutzers
     * 
     * @param nutzerID die ID des Nutzers
     * @return Vector mit s�mtlichen erstellten und geteilten Kontaktlisten eines Users
     * @throws IllegalArgumentException
     */
	@Override
	public Vector<Kontaktliste> getAllKontaktlistenFromUser(int nutzerID) throws IllegalArgumentException {
	 //Instantiieren der ben�tigten Vectoren um Kontaktlisten und Teilhaberschaft Objekte abspeichern zu k�nnen 
	Vector<Kontaktliste> kontlisten = findKontaktlisteByNutzerID(nutzerID);
	  Vector<Teilhaberschaft> teilhabe = getAllTeilhaberschaftenFromUser(nutzerID);
	  
	  //F�r jede Teilhaberschaft an einer Kontaktliste wird das entsprechende Kontaktlisten-Objekt in einem neuen Vector gespeichert
	 if (teilhabe != null){
		 Vector<Kontaktliste> kontlist = new Vector<Kontaktliste>();
		 for (Teilhaberschaft teilhaberschaft : teilhabe) {
	 	kontlist.add(findKontaktlisteByID(teilhaberschaft.getKontaktListeID()));
	}
		//Hinzuf�gen der Kontaktlisten an denen eine Teilhaberschaft besteht zum Vector mit den eigens erstellten Kontaktlisten
		 kontlisten.addAll(kontlist);
	 }
	  
	 //R�ckgabe des Vectors mit den erstellten und geteilten Kontaktlisten
	  return kontlisten;
		
	}
	

	/*##########################################################
     * ENDE Methoden fuer Kontaktliste-Objekte
     #########################################################*/
	
	/*##########################################################
     * START Methoden fuer KontaktKontaktliste-Objekte
     #########################################################*/
	
	/**
	 * Anlegen eines Objekts der Klasse KontaktKontaktliste um eine Beziehung zwischen Kontakt und Kontaktliste in der Datenbank herzustellen.
	 * 
	 * @param k
	 * @return KontaktKontaktliste-Objekt
	 */
	
	@Override
	public KontaktKontaktliste insertKontaktKontaktliste(int kontaktID, int kontaktlisteID)
			throws IllegalArgumentException {
		KontaktKontaktliste kk = new KontaktKontaktliste();
		
		kk.setKontaktID(kontaktID);
		kk.setKontaktlisteID(kontaktlisteID);
		
		return this.kontaktKontaktlisteMapper.insertKontaktKontaktliste(kk);
	}
	
	/**
	 * 
	 * @param kontaktlisteID
	 * @return Vector mit KontaktKontaktlisten-Objekten die �bergebene KontaktlisteID als Fremdschl�ssel besitzen
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<KontaktKontaktliste> getKontaktKontaktlisteFromKontaktliste(int kontaktlisteID)
			throws IllegalArgumentException {
		return this.kontaktKontaktlisteMapper.getKontaktKontaktlisteByKontaktlisteID(kontaktlisteID);
	}

	
	/*##########################################################
     * ENDE Methoden f�r KontaktKontaktliste-Objekte
     #########################################################*/
	
	/*##########################################################
     * START Methoden f�r Teilhaberschaft-Objekte
     #########################################################*/

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
	
	@Override
	public Teilhaberschaft insertTeilhaberschaft(int kontaktID, int kontaktListeID, int eigenschaftsauspraegungID,
			int teilhaberID) throws IllegalArgumentException {
		Teilhaberschaft t = new Teilhaberschaft();
		
		t.setKontaktID(kontaktID);
		t.setKontaktListeID(kontaktListeID);
		t.setEigenschaftsauspraegungID(eigenschaftsauspraegungID);
		t.setTeilhaberID(teilhaberID);
		
		return this.teilhaberschaftMapper.insertTeilhaberschaft(t);
    }

    /**
     * Eine Teilhaberschaft anhand der ID auslesen.
     * 
     * @param id der Prim�rschl�ssel der Teilhaberschaft
     * @return Teilhaberschaft-Objekt
     * @throws IllegalArgumentException
     */
    
	@Override
	public Teilhaberschaft findByID(int id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.teilhaberschaftMapper.findByID(id);
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
     * @param t das zu l�schende Teilhaberschaft-Objekt
     * @throws IllegalArgumentException
     */
	
	@Override
	public void deleteTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException {
		this.teilhaberschaftMapper.deleteTeilhaberschaft(t);
		
	}

	/**
     * Eine Teilhaberschaft an einem Kontakt loeschen.
     * 
     * @param t das zur l�schende Teilhaber-Objekt
     * @throws IllegalArgumentException
     */
	
	@Override
	public void deleteKontaktFromTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException {
		this.teilhaberschaftMapper.deleteKontaktFromTeilhaberschaft(t);
		
	}

	/**
     * Eine Teilhaberschaft an einer Kontaktliste loeschen.
     * 
     * @param t das zu l�schende Teilhaberschaft-Objekt
     * @throws IllegalArgumentException
     */
	
	@Override
	public void deleteKontaktlisteFromTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException {
		this.teilhaberschaftMapper.deleteKontaktlisteFromTeilhaberschaft(t);
	
	}



	@Override
	public Vector<Eigenschaftauspraegung> getAuspraegungByKontaktID(int id) throws IllegalArgumentException {
		
		return this.eigenschaftauspraegungMapper.getAuspraegungByKontaktID(id);
	}
	
	public void deleteEigenschaftUndAuspraegung(EigenschaftAuspraegungHybrid ea) throws IllegalArgumentException{
		Eigenschaftauspraegung eaa = new Eigenschaftauspraegung();
		Eigenschaft ee = new Eigenschaft();
		eaa.setID(ea.getAuspraegungID());
		ee.setID(ea.getEigenschaftID());
		this.eigenschaftauspraegungMapper.deleteAuspraegung(eaa);
		this.eigMapper.deleteEigenschaft(ee);
	}



	@Override

	public Vector<EigenschaftAuspraegungHybrid> findHybrid(Person pers) throws IllegalArgumentException {
		
		Vector<Eigenschaftauspraegung> eigaus = getAuspraegungByKontaktID(pers.getID());
		
		Vector<Eigenschaft> eig = new Vector<Eigenschaft>();
		
		for(Eigenschaftauspraegung eigenschaftauspraegung : eigaus){
			eig.add(getEigenschaftByID(eigenschaftauspraegung.getEigenschaftsID()));
		}
		
		Vector<EigenschaftAuspraegungHybrid> hybrid = new Vector<EigenschaftAuspraegungHybrid>();
		
		for (int i = 0; i < eigaus.size(); i++){
			
			for (int z = 0; z < eigaus.size(); z++){
				if (eigaus.elementAt(i).getEigenschaftsID() == eig.elementAt(z).getID()){
					
					hybrid.add(new EigenschaftAuspraegungHybrid(eig.elementAt(z), eigaus.elementAt(i)));	
				}
				
		}
		}
		return hybrid;
	}






	public Nutzer findNutzerByID(String string) {
		// TODO Auto-generated method stub
		return null;
	}






	











	









	


	

    
    
}
