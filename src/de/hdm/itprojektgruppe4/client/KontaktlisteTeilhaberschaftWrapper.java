package de.hdm.itprojektgruppe4.client;

import java.io.Serializable;

import de.hdm.itprojektgruppe4.shared.bo.Teilhaberschaft;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;


public class KontaktlisteTeilhaberschaftWrapper implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Teilhaberschaft teilhaberschaft = new Teilhaberschaft();
	private Kontaktliste kontaktliste = new Kontaktliste();
	
	public KontaktlisteTeilhaberschaftWrapper(Teilhaberschaft teilhaberschaft, Kontaktliste kontaktliste){
		this.teilhaberschaft = teilhaberschaft;
		this.kontaktliste = kontaktliste;
	}
	
	

}
