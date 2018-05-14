package de.hdm.itprojektgruppe4.shared.bo;

public class Teilhaberschaft extends BusinessObject{

	private static final long serialVersionUID = 1L;

	
	private int kontaktID;
	
	private int kontaktListeID;
	
	private int eigenschaftsauspraegungID;
	
	private int teilhaberID;
	

	public int getKontaktID() {
		return kontaktID;
	}

	public void setKontaktID(int kontaktID) {
		this.kontaktID = kontaktID;
	}

	public int getKontaktListeID() {
		return kontaktListeID;
	}

	public void setKontaktListeID(int kontaktListeID) {
		this.kontaktListeID = kontaktListeID;
	}

	public int getEigenschaftsauspraegungID() {
		return eigenschaftsauspraegungID;
	}

	public void setEigenschaftsauspraegungID(int eigenschaftsauspraegungID) {
		this.eigenschaftsauspraegungID = eigenschaftsauspraegungID;
	}

	public int getTeilhaberID() {
		return teilhaberID;
	}

	public void setTeilhaberID(int teilhaberID) {
		this.teilhaberID = teilhaberID;
	}

	@Override
	public String toString() {
		return "Teilhaberschaft [kontaktID=" + kontaktID + ", kontaktListeID=" + kontaktListeID
				+ ", eigenschaftsauspraegungID=" + eigenschaftsauspraegungID + ", teilhaberID=" + teilhaberID + "]";
	} 
	
	
	
	
}
	
