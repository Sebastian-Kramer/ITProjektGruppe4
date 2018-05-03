package de.hdm.itprojektgruppe4.shared.bo;

public class Eigenschaft_Auspraegung extends BusinessObject{
	
	
	private int eigenschaft_id;
	
	private int eigenschaftauspraegung_id;
	
	private int kontakt_id;

	public int getEigenschaft_id() {
		return eigenschaft_id;
	}

	public void setEigenschaft_id(int eigenschaft_id) {
		this.eigenschaft_id = eigenschaft_id;
	}

	public int getEigenschaftauspraegung_id() {
		return eigenschaftauspraegung_id;
	}

	public void setEigenschaftauspraegung_id(int eigenschaftauspraegung_id) {
		this.eigenschaftauspraegung_id = eigenschaftauspraegung_id;
	}

	public int getKontakt_id() {
		return kontakt_id;
	}

	public void setKontakt_id(int kontakt_id) {
		this.kontakt_id = kontakt_id;
	}
	
	
	
	

}
