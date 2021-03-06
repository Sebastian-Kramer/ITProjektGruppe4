package de.hdm.itprojektgruppe4.client;

import java.io.Serializable;

import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
/**
 * Die Klasse EigenschaftAuspraegungWrapper ist eine Wrapperklasse zwischen Eigenschaft, 
 * Eigenschaftausprägung und Kontakt. 
 * Durch die Klasse können EigenschaftAuspraegungWrapper-Objekte erzeugt werden, die meistens 
 * für das anzeigen von CellTables genutzt werden.
 * 
 * @author Clirim
 *
 */
public class EigenschaftAuspraegungWrapper implements Serializable {

	private static final long serialVersionUID = 1L;

	private Eigenschaft eigenschaft = new Eigenschaft();

	private Eigenschaftauspraegung auspraegung = new Eigenschaftauspraegung();

	private Kontakt kontakt = new Kontakt();

	public EigenschaftAuspraegungWrapper(Eigenschaft eigenschaft, Eigenschaftauspraegung auspraegung) {

		this.auspraegung = auspraegung;
		this.eigenschaft = eigenschaft;

	}

	public EigenschaftAuspraegungWrapper(Eigenschaft eigenschaft, Eigenschaftauspraegung auspraegung, Kontakt kontakt) {
		this.auspraegung = auspraegung;
		this.eigenschaft = eigenschaft;
		this.kontakt = kontakt;
	}
	
	/**
	 * Get- und Set Methoden für EigenschaftAuspraegungWrapper-Objekte
	 * 
	 * @author Clirim
	 *
	 */
	
	public Kontakt getKontakt() {
		return kontakt;
	}

	public void setKontakt(Kontakt kontakt) {
		this.kontakt = kontakt;
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

	public EigenschaftAuspraegungWrapper() {

	}

	public void setEigenschaftValue(String bez) {
		this.eigenschaft.setBezeichnung(bez);
	}

	public void setAuspraegungValue(String wert) {
		this.auspraegung.setWert(wert);
	}

	public void setAuspraegungID(int id) {
		this.auspraegung.setID(id);
	}

	public void setEigenschaftID(int id) {
		this.eigenschaft.setID(id);
	}

	public void setAuspraegungStatus(int id) {
		this.auspraegung.setStatus(id);
	}

	public String getEigenschaftValue() {
		return this.eigenschaft.getBezeichnung();
	}

	public int getEigenschaftStatus() {
		return this.eigenschaft.getStatus();
	}

	public int getEigenschaftID() {
		return this.eigenschaft.getID();
	}

	public String getAuspraegungValue() {
		return this.auspraegung.getWert();
	}

	public int getAuspraegungStatus() {
		return this.auspraegung.getStatus();
	}

	public int getAuspraegungID() {
		return this.auspraegung.getID();
	}

	public int getAuspraegungEigenschaftID() {
		return this.auspraegung.getEigenschaftsID();
	}

	public int getAuspraegungKontaktID() {
		return this.auspraegung.getKontaktID();
	}
	/**
	 * Die folgenden Methoden werden dazu verwendet, Icons in die Celltable´s
	 * zu laden.
	 * 
	 * @author Clirim
	 *
	 */
	public String getImageUrl2Contacts(EigenschaftAuspraegungWrapper object) {
		return "Image/contactShared.png";
	}

	public String getDelete(EigenschaftAuspraegungWrapper object) {
		return "Image/DeleteSmall.png";
	}
	/**
	 * To-String Methode für die Klasse.
	 * 
	 * @author Clirim
	 *
	 */
	public String toString() {
		return "<table id =" + "'tableReport'" + "><tr><td id = 'tdEigenschaftReport'>"
				+ this.eigenschaft.getBezeichnung() + ": " + "</td><td id = 'tdAuspraegungReport'>"
				+ this.auspraegung.getWert() + "</td></tr></table>";

	}

}
