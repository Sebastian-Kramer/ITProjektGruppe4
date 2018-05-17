package de.hdm.itprojektgruppe4.server;

import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.text.SimpleDateFormat;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektgruppe4.server.db.*;
import de.hdm.itprojektgruppe4.server.db.KontaktlisteMapper;
import de.hdm.itprojektgruppe4.shared.KontaktAdministration;
import de.hdm.itprojektgruppe4.shared.bo.*;

public class KontaktAdministrationImpl extends RemoteServiceServlet 
	implements KontaktAdministration{

	/**
     * Default constructor
     */ 
    public KontaktAdministrationImpl() {
    }

    private NutzerMapper nutzerMapper = null;
    
    /**
     * 
     */
    private KontaktMapper konMapper = null;
    
    private PersonMapper persMapper = null;

    /**
     * 
     */
    //private KontaktlisteMapper kontaktlisteMapper;
    private KontaktlisteMapper konlistMapper = null;
    /**
     * 
     */
    //private EigenschaftMapper eigenschaftMapper;
    private EigenschaftMapper eigMapper = null;
    
    private EigenschaftauspraegungMapper eigenschaftauspraegungMapper = null;
    
    /**
     * 
     */
    //private EigenschaftauspraegungMapper eigenschaftsauspraegungMapper;
    
    /**
     * 
     */
    private TeilhaberschaftMapper teilhaberschaftMapper = null;
    
    /**
     * 
     */
    
    private KontaktKontaktlisteMapper kontaktKontaktlisteMapper = null;
    
    /*
	   * ***************************************************************************
	   * ABSCHNITT, Beginn: Initialisierung
	   * ***************************************************************************
	   */

//    /**
//     * No-Argument-Constructor
//     */
//    public void KontaktAdministrationImpl() throws IllegalArgumentException {
//    }
    
    /**
     * @return
     */
    public void init() {
        this.konMapper = KontaktMapper.kontaktMapper();
        this.nutzerMapper = NutzerMapper.nutzerMapper();
        this.persMapper = PersonMapper.personMapper();
        this.konlistMapper = KontaktlisteMapper.kontaktlisteMapper();
    }

    /**
     * @param name 
     * @param erzeugungsdatum 
     * @param modifikationsdatum 
     * @param status 
     * @return
     */
    
    /*##########################################################
     * START Kontakt
     #########################################################*/

    
    
    public Kontakt insertKontakt(String name, Date erzeugungsdatum, Date modifikationsdatum, int status, int nutzerID)  	throws IllegalArgumentException {
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
    public Eigenschaftauspraegung createEigenschaftsauspraegung(String bezeichnung) {
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
    public Kontakt findKontaktById(int id) {
        // TODO implement here
        return null;
    }

    /**
     * @param id 
     * @return
     */
    public Eigenschaft findEigenschaftById(int id) {
        // TODO implement here
        return null;
    }

    /**
     * @param id 
     * @return
     */
    public Kontaktliste findKontaktlisteById(int id) {
        // TODO implement here
        return null;
    }

    /**
     * @param id 
     * @return
     */
    public Eigenschaftauspraegung findEigenschaftsauspraegungById(int id) {
        // TODO implement here
        return null;
    }

    /**
     * @param id 
     * @return
     */
    public Teilhaberschaft findTeilhaberschaftById(int id) {
        // TODO implement here
        return null;
    }

    /**
     * @param id 
     * @return
     */
    public Kontakt findNutzerById(int id) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Vector<Kontaktliste> getAllKontaktlisten() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Vector<Kontakt> getAllKontakte() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Vector<Eigenschaft> getAllEigenschaften() {
        // TODO implement here
        return null;
    }

    /**
     * @param k 
     * @return
     */
    public Vector<Eigenschaft> getAllEigenschaftVonKontakt(Kontakt k) {
        // TODO implement here
        return null;
    }

    /**
     * @param k 
     * @return
     */
    public Vector<Kontakt> getAllKontakteVonKontaktliste(Kontaktliste k) {
        // TODO implement here
        return null;
    }

	@Override
	public Nutzer insertNutzer(String mail) throws IllegalArgumentException {
		Nutzer nutzer = new Nutzer();
		nutzer.setEmail(mail);
		return this.nutzerMapper.insertNutzer(nutzer);
	}

	

	@Override
	public Kontakt findKontaktByID(int id) throws IllegalArgumentException {
		return this.konMapper.findKontaktByID(id);
		
		
	}

	@Override
	public Kontakt findKontaktByName(String name) throws IllegalArgumentException {
		return this.konMapper.findKontaktByName(name);
	}

	@Override
	public Vector<Kontakt> findAllKontakte() throws IllegalArgumentException {
		return this.konMapper.findAllKontakte();
	}

	@Override
	public Kontakt updateKontakt(Kontakt k) throws IllegalArgumentException {
		
		return this.konMapper.updateKontakt(k);
	}

	
	@Override
	public Kontakt deleteKontakt(Kontakt k) throws IllegalArgumentException {
		this.konMapper.deleteKontakt(k);
		this.persMapper.deletePerson(this.persMapper.findPersonByID(k.getID()));
		
		return k;
	}
	@Override
	public Person deletePerson(Person p) throws IllegalArgumentException {
		
		 this.persMapper.deletePerson(p);
		 return p;
	}

	@Override
	public Person findPersonByID(int ID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.findPersonByID(ID);
	}

	@Override
	public Nutzer deleteNutzer(Nutzer n) throws IllegalArgumentException {
		this.nutzerMapper.deleteNutzer(n);
		this.persMapper.deletePerson(this.persMapper.findPersonByID(n.getID()));
		
		return n;
	}

	@Override
	public Nutzer updateNutzer(Nutzer n) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.nutzerMapper.updateNutzer(n);
	}

	@Override
	public Nutzer findNutzerByEmail(String email) throws IllegalArgumentException {
		return this.nutzerMapper.findNutzerByEmail(email);
	}

	@Override
	public Nutzer findNutzerByID(int id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.nutzerMapper.findNutzerByID(id);
	}

	@Override
	public Vector<Nutzer> findAllNutzer() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.nutzerMapper.findAllNutzer();
	}

	@Override
	public Kontaktliste findKontaktlisteByID(int id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.konlistMapper.findKontaktlistebyID(id);
	}

	@Override
	public Kontaktliste findKontaktlisteByBezeichnung(String bezeichnung) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.konlistMapper.findKontaktlistebyBezeichnung(bezeichnung);
	}

	@Override
	public Kontaktliste insertKontaktliste(Kontaktliste kl) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.konlistMapper.insertKontaktliste(kl);
	}

	@Override
	public Kontaktliste updateKontaktliste(Kontaktliste k) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.konlistMapper.updateKontaktliste(k);
	}

	@Override
	public Kontaktliste deleteKontaktliste(Kontaktliste k) throws IllegalArgumentException {
		this.konlistMapper.deleteKontaktliste(k);
		return null;
	}

	@Override
	public Vector<Kontaktliste> findKontaktlisteAll() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.konlistMapper.findKontaktlisteAll();
	}

	@Override
	public Eigenschaft getEigenschaftByID(int id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.eigMapper.getEigenchaftByID(id);
	}

	@Override
	public Eigenschaft insterEigenschaft(Eigenschaft e) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.eigMapper.insertEigenschaft(e);
	}

	@Override
	public Eigenschaft updateEigenschaft(Eigenschaft e) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.eigMapper.updateEigenschaft(e);
	}

	@Override
	public Eigenschaft deleteEigenschaft(Eigenschaft e) throws IllegalArgumentException {
		this.eigMapper.deleteEigenschaft(e);
		return null;
	}

	@Override
	public Eigenschaftauspraegung insertAuspraegung(Eigenschaftauspraegung ea) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.eigenschaftauspraegungMapper.insertAuspraegung(ea);
	}

	@Override
	public Eigenschaftauspraegung updateAuspraegung(Eigenschaftauspraegung ea) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.eigenschaftauspraegungMapper.updateAuspraegung(ea);
	}

	@Override
	public Eigenschaftauspraegung deleteAuspraegung(Eigenschaftauspraegung ea) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Eigenschaftauspraegung getAuspraegungByWert(Eigenschaftauspraegung ea) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.eigenschaftauspraegungMapper.getAuspraegungByWert(ea);
	}

	@Override
	public Eigenschaftauspraegung getAuspraegungByID(int id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.eigenschaftauspraegungMapper.getAuspraegungByID(id);
	
	}

	@Override
	public Teilhaberschaft findByID(int id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.teilhaberschaftMapper.findByID(id);
	}

	@Override
	public Teilhaberschaft insertTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.teilhaberschaftMapper.insertTeilhaberschaft(t);
	}

	@Override
	public Teilhaberschaft deleteTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException {
		this.teilhaberschaftMapper.deleteTeilhaberschaft(t);
		return null;
	}

	@Override
	public Teilhaberschaft deleteKontaktFromTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException {
		this.teilhaberschaftMapper.deleteKontaktFromTeilhaberschaft(t);
		return null;
	}

	@Override
	public Teilhaberschaft deleteKontaktlisteFromTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException {
		this.teilhaberschaftMapper.deleteKontaktlisteFromTeilhaberschaft(t);
		return null;
	}

	@Override
	public Teilhaberschaft deleteEigenschaftsauspraegungFromTeilhaberschaft(Teilhaberschaft t)
			throws IllegalArgumentException {
		this.teilhaberschaftMapper.deleteEigenschaftsauspraegungFromTeilhaberschaft(t);
		return null;
	}

	@Override
	public List<Kontakt> findAllKontaktNames() throws IllegalArgumentException {
		return this.konMapper.findAllKontakte();
	}

	@Override

	public KontaktKontaktliste insertKontaktKontaktliste(KontaktKontaktliste k) 
			throws IllegalArgumentException {
		this.kontaktKontaktlisteMapper.insertKontaktKontaktliste(k);
		return null;
		}

	@Override
	public KontaktKontaktliste deleteKontaktKontaktliste(KontaktKontaktliste k) 
			throws IllegalArgumentException {
		this.kontaktKontaktlisteMapper.deleteKontaktKontaktliste(k);
		return null;
	}
		
	

	public Vector<Kontakt> findKontaktByNutzerID(int nutzerID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.konMapper.findKontaktByNutzerID(nutzerID);
	}

	@Override
	public Vector<Kontaktliste> findKontaktlisteByNutzerID(int nutzerID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.konlistMapper.findKontaktlisteByNutzerID(nutzerID);
	}


	

    
    
}
