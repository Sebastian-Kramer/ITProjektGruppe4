package de.hdm.itprojektgruppe4.client;

import com.google.gwt.core.client.GWT;

import de.hdm.itprojektgruppe4.shared.CommonSettings;
import de.hdm.itprojektgruppe4.shared.KontaktAdministration;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.LoginService;
import de.hdm.itprojektgruppe4.shared.LoginServiceAsync;
import de.hdm.itprojektgruppe4.shared.ReportGenerator;
import de.hdm.itprojektgruppe4.shared.ReportGeneratorAsync;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;

/**
 * Diese Klasse beinhaltet Eigenschaften, welche für alle Clientside-Klassen
 * benötigt werden. KontaktAdministration, ReportGenerator und LoginService
 * 
 * @author Nino
 *
 */

public class ClientsideSettings extends CommonSettings {

	/**
	 * Proxy um Verbindung mit dem Serverseitigen-Dienst aufzunehmen.
	 * <code> KontaktAdministration </code>
	 */

	private static KontaktAdministrationAsync kontaktVerwaltung = null;

	/**
	 * Proxy um Verbindung mit dem Serverseitigen-Dienst aufzunehmen.
	 * <code> ReportgeneratorAdministration </code>
	 */

	private static ReportGeneratorAsync reportVerwaltung = null;

	/**
	 * Proxy um Verbindung mit dem Serverseitigen-Dienst aufzunehmen.
	 * <code> LoginService </code>
	 */

	private static LoginServiceAsync loginService = null;

	private static LoginInfo currentUser = null;

	private static Nutzer aktuellerNutzer = null;

	/**
	 * Der Aufruf der Methode erfolgt im Client z.B. durch
	 * <code> KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung(); </code>
	 * 
	 * @return
	 */

	public static KontaktAdministrationAsync getKontaktVerwaltung() {
		if (kontaktVerwaltung == null) {
			kontaktVerwaltung = GWT.create(KontaktAdministration.class);
		}
		return kontaktVerwaltung;
	}

	/**
	 * Der Aufruf der Methode erfolgt im Client z.B. durch
	 * <code> LoginServiceAsync loginService = GWT.create(LoginService.class); </code>
	 * 
	 * @return
	 */

	public static LoginServiceAsync getLoginService() {
		if (loginService == null) {
			loginService = GWT.create(LoginService.class);
		}
		return loginService;
	}

	public static LoginInfo getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(LoginInfo nutzer) {
		currentUser = nutzer;
	}

	public static KontaktAdministrationAsync getKontaktverwaltung() {

		if (kontaktVerwaltung == null) {
			kontaktVerwaltung = GWT.create(KontaktAdministration.class);
		}

		return kontaktVerwaltung;
	}

	public static Nutzer getAktuellerNutzer() {
		return aktuellerNutzer;
	}

	public static void setAktuellerNutzer(Nutzer aktuellerNutzer) {
		ClientsideSettings.aktuellerNutzer = aktuellerNutzer;
	}

	/**
	 * Der Aufruf der Methode erfolgt im Client z.B. durch
	 * <code> ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportVerwaltung(); </code>
	 * 
	 * @return
	 */

	public static ReportGeneratorAsync getReportVerwaltung() {

		if (reportVerwaltung == null) {
			reportVerwaltung = GWT.create(ReportGenerator.class);
		}
		return reportVerwaltung;

	}

}
