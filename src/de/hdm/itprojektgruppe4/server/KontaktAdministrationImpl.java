package de.hdm.itprojektgruppe4.server;

import java.util.Date;
import java.util.List;
import java.util.Vector;
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
     * Referenz auf de NutzerMapper, der Nutzer-Objekte mit der Datenbank abgleicht.
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
    }

    
    
    /*##########################################################
     * START Methoden f�r Kontakt-Objekte
     #########################################################*/

    
    
    public Kontakt insertKontakt(String name, Date erzeugungsdatum, Date modifikationsdatum, int status, int nutzerID)  	
    		throws IllegalArgumentException {
        // TODO implement here
 
    	Kontakt k = new Kontakt();
    	
    	k.setID(1);
    	k.setName(name);
    	k.setErzeugungsdatum(erzeugungsdatum);
    	k.setModifikationsdatum(modifikationsdatum);
    	k.setStatus(status);
    	k.setNutzerID(nutzerID);
    	
    	
    	
        return this.konMapper.insertKontakt(k);
    }
    
    //pr�fen
    public Kontakt findKontaktById(int id) {
        return this.konMapper.findKontaktByID(id);
    }
    
    public Vector<Kontakt> getAllKontakte() {
        return this.konMapper.findAllKontakte();
    }

    //pr�feb
	@Override
	public Kontakt findKontaktByID(int id) throws IllegalArgumentException {
		return this.konMapper.findKontaktByID(id);
		
		
	}
    
	
	//pr�fen
    public Kontakt findNutzerById(int id) {
        // TODO implement here
        return null;
    }
    //pr�fen    
    public Vector<Kontakt> getAllKontakteVonKontaktliste(Kontaktliste k) {
        
        return null;
    }
    
	@Override
	public Vector<Kontakt> findAllKontakte() throws IllegalArgumentException {
		return this.konMapper.findAllKontakte();
	}
	
	@Override
	public Kontakt findKontaktByName(String name) throws IllegalArgumentException {
		return this.konMapper.findKontaktByName(name);
	}
	
	@Override
	public Kontakt updateKontakt(Kontakt k) throws IllegalArgumentException {
		
		return this.konMapper.updateKontakt(k);
	}

	
	@Override
	public void deleteKontakt(Kontakt k) throws IllegalArgumentException {
		this.konMapper.deleteKontakt(k);
		this.persMapper.deletePerson(this.persMapper.findPersonByID(k.getID()));
		
		
	}
	
	public Vector<Kontakt> findKontaktByNutzerID(int nutzerID) throws IllegalArgumentException {
		return this.konMapper.findKontaktByNutzerID(nutzerID);
	}
	

	@Override
	public List<Kontakt> findAllKontaktNames() throws IllegalArgumentException {
		return this.konMapper.findAllKontakte();
	}

	@Override
	public Vector<Integer> findAllKontakteFromKontaktliste(int i) throws IllegalArgumentException {
		return konMapper.findAllKontakteFromKontaktliste(i);
	}

	
    /*##########################################################
     * ENDE Methoden f�r Kontakt-Objekte
     #########################################################*/

    /*##########################################################
     * START Methoden f�r Nutzer-Objekte
     #########################################################*/
	
	@Override
	public Nutzer insertNutzer(String mail) throws IllegalArgumentException {
		Nutzer nutzer = new Nutzer();
		nutzer.setEmail(mail);
		return this.nutzerMapper.insertNutzer(nutzer);
	}
	
	@Override
	public void deleteNutzer(Nutzer n) throws IllegalArgumentException {
		this.nutzerMapper.deleteNutzer(n);
		this.persMapper.deletePerson(this.persMapper.findPersonByID(n.getID()));
	}

	@Override
	public Nutzer updateNutzer(Nutzer n) throws IllegalArgumentException {
		return this.nutzerMapper.updateNutzer(n);
	}

	@Override
	public Nutzer findNutzerByEmail(String email) throws IllegalArgumentException {
		return this.nutzerMapper.findNutzerByEmail(email);
	}

	@Override
	public Nutzer findNutzerByID(int id) throws IllegalArgumentException {
		return this.nutzerMapper.findNutzerByID(id);
	}

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
	
	@Override
	public Person findPersonByID(int ID) throws IllegalArgumentException {
		return this.findPersonByID(ID);
	}
	
	@Override
	public void deletePerson(Person p) throws IllegalArgumentException {
		
		 this.persMapper.deletePerson(p);
		
	}
	
    /*##########################################################
     * ENDE Methoden f�r Person-Objekte
     #########################################################*/
	
    /*##########################################################
     * START Methoden f�r Eigenschaft-Objekte
     #########################################################*/
	
	@Override
	public Eigenschaft insertEigenschaft(String bez, int status) throws IllegalArgumentException {
		Eigenschaft e = new Eigenschaft();
		
		e.setBezeichnung(bez);
		e.setStatus(status);
		
		return this.eigMapper.insertEigenschaft(e);
	}
    
    public Eigenschaft findEigenschaftById(int id) {
        return this.eigMapper.getEigenchaftByID(id);
    }
    
    //pr�fen
    public Vector<Eigenschaft> getAllEigenschaften() {
        // TODO implement here
        return null;
    }

    //pr�fen
    public Vector<Eigenschaft> getAllEigenschaftVonKontakt(Kontakt k) {
        // TODO implement here
        return null;
    }
    
	@Override
	public Eigenschaft getEigenschaftByID(int id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.eigMapper.getEigenchaftByID(id);
	}

	@Override
	public Eigenschaft updateEigenschaft(Eigenschaft e) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.eigMapper.updateEigenschaft(e);
	}

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
	
	@Override
	public Eigenschaftauspraegung insertAuspraegung(String wert, int status, int eigenschaftsID, int kontaktID)
			throws IllegalArgumentException {
		Eigenschaftauspraegung e = new Eigenschaftauspraegung();
		
		e.setWert(wert);
		e.setStatus(status);
		e.setKontaktID(kontaktID);
		e.setEigenschaftsID(eigenschaftsID);
		
		return null;
	}
	
	//pr�fen
	public Eigenschaftauspraegung findEigenschaftsauspraegungById(int id) {
	        
	        return this.eigenschaftauspraegungMapper.getAuspraegungByID(id);
	    }
	 
	@Override
	public Eigenschaftauspraegung updateAuspraegung(Eigenschaftauspraegung ea) throws IllegalArgumentException {
			return this.eigenschaftauspraegungMapper.updateAuspraegung(ea);
		}

	@Override
	public void deleteAuspraegung(Eigenschaftauspraegung ea) throws IllegalArgumentException {
			this.eigenschaftauspraegungMapper.deleteAuspraegung(ea);
		
		}
	
	//pr�fen
	@Override
	public Eigenschaftauspraegung getAuspraegungByID(int id) throws IllegalArgumentException {
			return this.eigenschaftauspraegungMapper.getAuspraegungByID(id);
		
		}
		
	@Override
	public void deleteEigenschaftsauspraegungFromTeilhaberschaft(Teilhaberschaft t)
				throws IllegalArgumentException {
			this.teilhaberschaftMapper.deleteEigenschaftsauspraegungFromTeilhaberschaft(t);
			
		}
	
	//pr�fen
	@Override
	public Eigenschaftauspraegung getAuspraegungByWert(String wert) throws IllegalArgumentException {
			
			return null;
		}
		
		/*##########################################################
	     * ENDE Methoden f�r Eigenschaftauspragung-Objekte
	     #########################################################*/

		/*##########################################################
	     * START Methoden f�r Kontaktliste-Objekte
	     #########################################################*/
		
	@Override
	public Kontaktliste insertKontaktliste(String bez, int status, int nutzerID) throws IllegalArgumentException {
			Kontaktliste k = new Kontaktliste();
			
			k.setBez(bez);
			k.setStatus(status);
			k.setNutzerID(nutzerID);
			
			return this.konlistMapper.insertKontaktliste(k);
		}
	
	public Kontaktliste findKontaktlisteById(int id) {
        
        return this.konlistMapper.findKontaktlistebyID(id);
		}
//pr�fen
	public Vector<Kontaktliste> getAllKontaktlisten() {
        
        return null;
		}
    
	@Override
	public Kontaktliste findKontaktlisteByID(int id) throws IllegalArgumentException {
		return this.konlistMapper.findKontaktlistebyID(id);
		}
	
	@Override
	public Kontaktliste findKontaktlisteByBezeichnung(String bezeichnung) throws IllegalArgumentException {
		return this.konlistMapper.findKontaktlistebyBezeichnung(bezeichnung);
		}


	@Override
	public Kontaktliste updateKontaktliste(Kontaktliste k) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.konlistMapper.updateKontaktliste(k);
		}

	@Override
	public void deleteKontaktliste(Kontaktliste k) throws IllegalArgumentException {
		this.konlistMapper.deleteKontaktliste(k);
	
	}

	@Override
	public Vector<Kontaktliste> findKontaktlisteAll() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.konlistMapper.findKontaktlisteAll();
	}
	
	@Override
	public void deleteKontaktKontaktliste(KontaktKontaktliste k) 
			throws IllegalArgumentException {
		this.kontaktKontaktlisteMapper.deleteKontaktKontaktliste(k);
		
	}
		
	@Override
	public Vector<Kontaktliste> findKontaktlisteByNutzerID(int nutzerID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.konlistMapper.findKontaktlisteByNutzerID(nutzerID);
	}
	
	/*##########################################################
     * ENDE Methoden f�r Kontaktliste-Objekte
     #########################################################*/
	
	/*##########################################################
     * START Methoden f�r KontaktKontaktliste-Objekte
     #########################################################*/
	
	@Override
	public KontaktKontaktliste insertKontaktKontaktliste(int kontaktID, int kontaktlisteID)
			throws IllegalArgumentException {
		KontaktKontaktliste kk = new KontaktKontaktliste();
		
		kk.setKontaktID(kontaktID);
		kk.setKontaktlisteID(kontaktlisteID);
		
		return this.kontaktKontaktlisteMapper.insertKontaktKontaktliste(kk);
	}
	
	/*##########################################################
     * ENDE Methoden f�r KontaktKontaktliste-Objekte
     #########################################################*/
	
	/*##########################################################
     * START Methoden f�r Teilhaberschaft-Objekte
     #########################################################*/


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

    public Teilhaberschaft findTeilhaberschaftById(int id) {
        // TODO implement here
        return this.teilhaberschaftMapper.findByID(id);
    }

	@Override
	public Teilhaberschaft findByID(int id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.teilhaberschaftMapper.findByID(id);
	}

	@Override
	public void deleteTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException {
		this.teilhaberschaftMapper.deleteTeilhaberschaft(t);
		
	}

	@Override
	public void deleteKontaktFromTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException {
		this.teilhaberschaftMapper.deleteKontaktFromTeilhaberschaft(t);
		
	}

	@Override
	public void deleteKontaktlisteFromTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException {
		this.teilhaberschaftMapper.deleteKontaktlisteFromTeilhaberschaft(t);
	
	}






	@Override
	public Vector<Eigenschaft> findAllEigenschaft() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.eigMapper.findAllEigenschaft();
	}


	

	


	

    
    
}
