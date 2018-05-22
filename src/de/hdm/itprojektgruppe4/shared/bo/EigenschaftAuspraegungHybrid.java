package de.hdm.itprojektgruppe4.shared.bo;

import java.io.Serializable;

public class EigenschaftAuspraegungHybrid implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private String eigenschaft = "";
	
	private String auspraegung = "";
	
	private int eigenschaftID;
	
	private int auspraegungID;
	
	public EigenschaftAuspraegungHybrid(){
		
	}
	
	public EigenschaftAuspraegungHybrid(Eigenschaft eigenschaft, Eigenschaftauspraegung auspraegung){
		
		this.auspraegung = auspraegung.getWert();
		this.eigenschaft = eigenschaft.getBezeichnung();
		this.eigenschaftID = auspraegung.getEigenschaftsID();
		this.auspraegungID = auspraegung.getID();
		
		
	}

	public String getEigenschaft() {
		return eigenschaft;
	}

	public void setEigenschaft(String eigenschaft) {
		this.eigenschaft = eigenschaft;
	}

	public String getAuspraegung() {
		return auspraegung;
	}

	public void setAuspraegung(String auspraegung) {
		this.auspraegung = auspraegung;
	}

	public int getEigenschaftID() {
		return eigenschaftID;
	}

	public void setEigenschaftID(int eigenschaftID) {
		this.eigenschaftID = eigenschaftID;
	}

	public int getAuspraegungID() {
		return auspraegungID;
	}

	public void setAuspraegungID(int auspraegungID) {
		this.auspraegungID = auspraegungID;
	}

	@Override
	public String toString() {
		return "EigenschaftAuspraegungHybrid [eigenschaft=" + eigenschaft + ", auspraegung=" + auspraegung
				+ ", eigenschaftID=" + eigenschaftID + ", auspraegungID=" + auspraegungID + "]";
	}
	
	
}
