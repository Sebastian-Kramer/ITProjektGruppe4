package de.hdm.itprojektgruppe4.shared.bo;

public class Eigenschaftauspraegung extends BusinessObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String wert;
	
	private int status; 
	
	private int eigenschaftsID; 
	
	private int kontaktID;

	public String getWert() {
		return wert;
	}

	public void setWert(String wert) {
		this.wert = wert;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getEigenschaftsID() {
		return eigenschaftsID;
	}

	public void setEigenschaftsID(int eigenschaftsID) {
		this.eigenschaftsID = eigenschaftsID;
	}

	public int getKontaktID() {
		return kontaktID;
	}

	public void setKontaktID(int kontaktID) {
		this.kontaktID = kontaktID;
	}

	@Override
	public String toString() {
		return "Eigenschaftauspraegung [wert=" + wert + ", status=" + status + ", eigenschaftsID=" + eigenschaftsID
				+ ", kontaktID=" + kontaktID + "]";
	} 
	
	
	


	
	
}
