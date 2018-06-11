package de.hdm.itprojektgruppe4.client;

import java.io.Serializable;

import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;

public class EigenschaftAuspraegungWrapper implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Eigenschaft eigenschaft = new Eigenschaft();
	
	private Eigenschaftauspraegung auspraegung = new Eigenschaftauspraegung();
	

	
	public EigenschaftAuspraegungWrapper(Eigenschaft eigenschaft, Eigenschaftauspraegung auspraegung){
		
		this.auspraegung = auspraegung;
		this.eigenschaft = eigenschaft;
		
		
	}

	public Eigenschaft getEigenschaft() {
		return eigenschaft;
	}

	public void setEigenschaft(Eigenschaft eigenschaft) {
		this.eigenschaft = eigenschaft;
	}

	public Eigenschaftauspraegung getAuspraegung() {
		return auspraegung;
	}

	public void setAuspraegung(Eigenschaftauspraegung auspraegung) {
		this.auspraegung = auspraegung;
	}

	public EigenschaftAuspraegungWrapper(){
		
	}
	
	
	public void setEigenschaftValue(String bez){
		this.eigenschaft.setBezeichnung(bez);
	}
	
	public void setAuspraegungValue(String wert){
		this.auspraegung.setWert(wert);
	}
	
	public void setAuspraegungID(int id){
		this.auspraegung.setID(id);
	}
	
	public void setEigenschaftID(int id){
		this.eigenschaft.setID(id);
	}
	
	public String getEigenschaftValue(){
		return this.eigenschaft.getBezeichnung();
	}
	
	public int getEigenschaftStatus(){
		return this.eigenschaft.getStatus();
	}
	
	public int getEigenschaftID(){
		return this.eigenschaft.getID();	
	}
	
	public String getAuspraegungValue(){
		return this.auspraegung.getWert();
	} 
	
	public int getAuspraegungStatus(){
		return this.auspraegung.getStatus();
	}
	
	public int getAuspraegungID(){
		return this.auspraegung.getID();
	}
	
	public int getAuspraegungEigenschaftID(){
		return this.auspraegung.getEigenschaftsID();
	}
	
	public int getAuspraegungKontaktID(){
		return this.auspraegung.getKontaktID();
	}

	public String getImageUrl(EigenschaftAuspraegungWrapper object) {
		return "<img src = https://png.icons8.com/metro/100/000000/contacts.png>";
	}
	

	
}
