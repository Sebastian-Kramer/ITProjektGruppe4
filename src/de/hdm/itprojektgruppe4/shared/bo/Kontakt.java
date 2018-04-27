package de.hdm.itprojektgruppe4.shared.bo;
import java.util.Date;
public class Kontakt {

	

	private static final long serialVersionUID = 1L;
	
	private int ID;
	
	private int nutzerID;
	
	private int kontaktID;
	
	private int eigenschaftID;
	
	private String name;
	
	private Date erzeugungsdatum;
	
	private Date modifikationsdatum;
	
	private boolean status;

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		ID = ID;
	}

	public int getNutzerID() {
		return nutzerID;
	}

	public void setNutzerID(int nutzerID) {
		this.nutzerID = nutzerID;
	}

	public int getKontaktID() {
		return kontaktID;
	}

	public void setKontaktID(int kontaktID) {
		this.kontaktID = kontaktID;
	}

	public int getEigenschaftID() {
		return eigenschaftID;
	}

	public void setEigenschaftID(int eigenschaftID) {
		this.eigenschaftID = eigenschaftID;
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public String toString(){
		return  "Kontakt [name=" + name + " "+ ID + " " + eigenschaftID +  "]";
	}
}

