package de.hdm.itprojektgruppe4.client;

import com.google.gwt.core.client.GWT;

import de.hdm.itprojektgruppe4.shared.CommonSettings;
import de.hdm.itprojektgruppe4.shared.KontaktAdministration;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.LoginService;
import de.hdm.itprojektgruppe4.shared.LoginServiceAsync;

public class ClientsideSettings extends CommonSettings{
	
	private static KontaktAdministrationAsync kontaktVerwaltung = null;
	
	private static LoginServiceAsync loginService = null;	
	
	public static KontaktAdministrationAsync getKontaktVerwaltung(){
		if (kontaktVerwaltung == null){
			kontaktVerwaltung = GWT.create(KontaktAdministration.class);
		}
		return kontaktVerwaltung;
	}

	public static LoginServiceAsync getLoginService() {
		if (loginService == null) {
			loginService = GWT.create(LoginService.class);
		}
		return loginService;
	}
	
}
