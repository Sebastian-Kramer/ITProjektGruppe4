package de.hdm.itprojektgruppe4.client;

import com.google.gwt.core.client.GWT;

import de.hdm.itprojektgruppe4.shared.CommonSettings;
import de.hdm.itprojektgruppe4.shared.KontaktAdministration;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;

public class ClientsideSettings extends CommonSettings{
	
	private static KontaktAdministrationAsync kontaktVerwaltung = null;
	
	public static KontaktAdministrationAsync getKontaktVerwaltung(){
		if (kontaktVerwaltung == null){
			kontaktVerwaltung = GWT.create(KontaktAdministration.class);
		}
		return kontaktVerwaltung;
	}

}
