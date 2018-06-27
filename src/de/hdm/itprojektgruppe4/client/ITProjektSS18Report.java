package de.hdm.itprojektgruppe4.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektgruppe4.client.gui.MainFormReport;
import de.hdm.itprojektgruppe4.client.gui.NavigationReport;
import de.hdm.itprojektgruppe4.shared.LoginService;
import de.hdm.itprojektgruppe4.shared.LoginServiceAsync;
import de.hdm.itprojektgruppe4.shared.ReportGeneratorAsync;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;

public class ITProjektSS18Report implements EntryPoint {

	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Bitte melde dich mit deinem Google-Konto an");
	private Anchor signInLink = new Anchor("Anmelden");
	private Anchor signOutLink = new Anchor("Logout");

	LoginServiceAsync loginService = GWT.create(LoginService.class);

	/**
	 * Reportgenerator wird instanziiert um dessen Methoden zu verwenden
	 */
	private static ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportVerwaltung();

	private static String editorHtmlName = "ITProjektSS18Report.html";

	public void onModuleLoad() {

		signOutLink.setStyleName("Logout");
		/**
		 * Loginstatus wird anhand des LoginService überprüft
		 */
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL() + editorHtmlName, new AsyncCallback<LoginInfo>() {

			public void onFailure(Throwable error) {

			}

			/**
			 * Aktueller Nutzer wird gesetzt Anhand der If Else bedinung wir
			 * entschieden ob die CheckNewNutzer Methode oder die LoadLogin
			 * Methode aufgerufen wird
			 */
			public void onSuccess(LoginInfo result) {
				ClientsideSettings.setCurrentUser(result);

				loginInfo = result;
				if (loginInfo.isLoggedIn()) {
					checkNewNutzer(loginInfo);

				} else {
					loadLogin();
				}
			}
		});
	}

	/**
	 * Die CheckNewNutzer Methode hat den Zweck, zu Prüfen: Den Aktuellen Nutzer
	 * aus der Datenbank zu finden Sollte der Nutzer in der Datenbank vorhanden
	 * sein, so wird die LoadStartseite Methode aufgerufen
	 * 
	 * @param result
	 * @return
	 */
	private Nutzer checkNewNutzer(LoginInfo result) {
		final LoginInfo finalLog = result;

		Nutzer nutzer = null;
		reportverwaltung.findNutzerByEmail(result.getEmailAddress(), new AsyncCallback<Nutzer>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Nutzer konnte nicht aus der Datenbank gelsesen werden." + " Daher wird "
						+ finalLog.getEmailAddress() + "angelegt");

			}

			@Override
			public void onSuccess(Nutzer result) {
				if (!result.getEmail().equals(null)) {
					Window.alert(
							"Hallo " + result.getEmail() + " wir konnten dich erfolgreich aus der Datenbank lesen.");
					ClientsideSettings.setAktuellerNutzer(result);
					Cookies.setCookie("email", result.getEmail());
					Cookies.setCookie("id", result.getID() + "");
					loadStartseite();

				}
			}
		});
		return nutzer;
	}

	/**
	 * Die Loadlogin Methode ruft die von Google vordefinierten Login auf
	 */
	private void loadLogin() {

		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get("Details").add(loginPanel);

	}

	/**
	 * Die Methode loadStartseite verweist auf die Klasse MainForm und
	 * navigationTree Zudem beinhaltet die Methode diverse Widget für das GUI
	 */
	private void loadStartseite() {
		NavigationReport navigationReport = new NavigationReport();
		MainFormReport mfReport = new MainFormReport();
		signOutLink.setHref(loginInfo.getLogoutUrl());
		RootPanel.get("Header").add(signOutLink);
		RootPanel.get("Details").add(mfReport);
		RootPanel.get("Navigator").add(navigationReport);
	}

}
