package de.hdm.itprojektgruppe4.shared.bo;

public class Teilhaberschaft extends BusinessObject{

	private static final long serialVersionUID = 1L;

	
	int NutzerID; 
	
	int KontaktID;
	
	int KontaktListeID;
	
	int EigenschaftsID;
	
	int TeilhaberID;
	
	public int getTeilhaberID() {
		return TeilhaberID;
	}

	public void setTeilhaberID(int teilhaberID) {
		TeilhaberID = teilhaberID;
	}

	public int getEigenschaftsID() {
		return EigenschaftsID;
	}

	public void setEigenschaftsID(int eigenschaftsID) {
		EigenschaftsID = eigenschaftsID;
	}

	int EigenschaftsAuspraegungID;
	

	public int getNutzerID() {
		return NutzerID;
	}

	public void setNutzerID(int nutzerID) {
		NutzerID = nutzerID;
	}

	public int getKontaktID() {
		return KontaktID;
	}

	public void setKontaktID(int kontaktID) {
		KontaktID = kontaktID;
	}

	public int getKontaktListeID() {
		return KontaktListeID;
	}

	public void setKontaktListeID(int kontaktListeID) {
		KontaktListeID = kontaktListeID;
	}

	public int getEigenschaftsAuspraegungID() {
		return EigenschaftsAuspraegungID;
	}

	public void setEigenschaftsAuspraegungID(int eigenschaftsAuspraegungID) {
		EigenschaftsAuspraegungID = eigenschaftsAuspraegungID;
	}
	
}
