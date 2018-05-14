package de.hdm.itprojektgruppe4.shared.bo;
import java.util.Date;
public class Kontakt extends Person{

	

	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private Date erzeugungsdatum;
	
	private Date modifikationsdatum;
	
	private int status;
	
	private int kontaktlisteID;
	
	private int nutzerID;

	

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Date getErzeugungsdatum() {
		return erzeugungsdatum;
	}



	public void setErzeugungsdatum(Date erzeugungsdatum) {
		this.erzeugungsdatum = erzeugungsdatum;
	}



	public Date getModifikationsdatum() {
		return modifikationsdatum;
	}



	public void setModifikationsdatum(Date modifikationsdatum) {
		this.modifikationsdatum = modifikationsdatum;
	}



	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}



	public int getKontaktlisteID() {
		return kontaktlisteID;
	}



	public void setKontaktlisteID(int kontaktlisteID) {
		this.kontaktlisteID = kontaktlisteID;
	}



	public int getNutzerID() {
		return nutzerID;
	}



	public void setNutzerID(int nutzerID) {
		this.nutzerID = nutzerID;
	}



	public String toString(){
		return  " Kontakt name = " + name + " "+ super.getID() + " " + status + " "  + "\n" ;
	}
}

