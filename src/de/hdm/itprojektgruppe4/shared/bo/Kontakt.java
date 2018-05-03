package de.hdm.itprojektgruppe4.shared.bo;
import java.util.Date;
public class Kontakt extends BusinessObject{

	

	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private Date erzeugungsdatum;
	
	private Date modifikationsdatum;
	
	private int status;
	
	private String googleMail;
	
	private int kontaktliste_id;
	
	private int nutzer_id;

	public int getNutzer_id() {
		return nutzer_id;
	}



	public void setNutzer_id(int nutzer_id) {
		this.nutzer_id = nutzer_id;
	}


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
	
	
	public String getGoogleMail() {
		return googleMail;
	}



	public void setGoogleMail(String googleMail) {
		this.googleMail = googleMail;
	}



	public int getKontaktliste_id() {
		return kontaktliste_id;
	}



	public void setKontaktliste_id(int kontaktliste_id) {
		this.kontaktliste_id = kontaktliste_id;
	}



	public String toString(){
		return  "Kontakt [name=" + name + " "+ super.getID()  + "]";
	}
}

