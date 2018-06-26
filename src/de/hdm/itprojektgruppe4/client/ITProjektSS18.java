package de.hdm.itprojektgruppe4.client;

import de.hdm.itprojektgruppe4.client.gui.MainForm;

import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.LoginService;
import de.hdm.itprojektgruppe4.shared.LoginServiceAsync;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.client.NavigationTree;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;

public class ITProjektSS18 implements EntryPoint {

	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Bitte melde dich mit deinem Google-Konto an");
	private Label eingeloggt = new Label("Momentan in WYNWYN angemeldet: ");
	private Anchor signInLink = new Anchor("Anmelden");
	private Anchor signOutLink = new Anchor("Logout");
	private Anchor impressum = new Anchor("Impressum");
	private Anchor startseite = new Anchor("Startseite");

	Nutzer n = new Nutzer();

	LoginServiceAsync loginService = GWT.create(LoginService.class);
	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();

	private static String editorHtmlName = "ITProjektSS18.html";

	public void onModuleLoad() {

		impressum.setStyleName("Impressum");
		signOutLink.setStyleName("Logout");
		startseite.setStyleName("Startseite");
		eingeloggt.setStyleName("AktiverUser");
		
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL() + editorHtmlName, new AsyncCallback<LoginInfo>() {

			public void onFailure(Throwable error) {

			}

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

	private Nutzer checkNewNutzer(LoginInfo result) {
		final LoginInfo finalLog = result;

		Nutzer nutzer = null;
		verwaltung.findNutzerByEmail(result.getEmailAddress(), new AsyncCallback<Nutzer>() {

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

				} else {

					verwaltung.insertNutzer(loginInfo.getEmailAddress(), new AsyncCallback<Nutzer>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Fehler");
						}

						@Override
						public void onSuccess(Nutzer result) {
							Window.alert("Nutzer " + finalLog.getEmailAddress() + " wurde erfolgreich angelegt.");
							Cookies.setCookie("email", result.getEmail());
							Cookies.setCookie("id", result.getID() + "");
							verwaltung.insertMeineKontakte("Meine Kontakte", 0, result.getID(),
									new MeineKontakteAnlegen());
							n = result;
						}

					});

				}

			}
		});
		return nutzer;
	}

	private void loadLogin() {

		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get("Details").add(loginPanel);

	}

	private void loadStartseite() {
		MainForm mainForm = new MainForm();
		NavigationTree navigationTree = new NavigationTree();
		signOutLink.setHref(loginInfo.getLogoutUrl());
		RootPanel.get("Header").add(eingeloggt);
		RootPanel.get("Header").add(signOutLink);
		RootPanel.get("Header").add(impressum);
		RootPanel.get("Header").add(startseite);
		impressum.addClickHandler(new ImpressumClickHandler());
		startseite.addClickHandler(new StartseiteClickHandler());
		RootPanel.get("Details").add(mainForm);
		RootPanel.get("Navigator").add(navigationTree);
	}

	/*
<<<<<<< HEAD
	 * Meldet sich ein Nutzer neu auf der Plattform an, sollwn sofort die
	 * Kontaktlisten "Meine Kontakte" und "Meine geteilten Kontakte" erstellt
	 * werden. Hierfür wird diese Callback-Klasse benötigt. Die Kontaktlisten
	 * stellen Standardkontaktliste da.
=======
	 * Meldet sich ein Nutzer neu auf der Plattform an, sollwe sofort die
	 * Kontaktlisten "Meine Kontakte" und "Meine geteilten Kontakte" erstellt werden. Hierfür wird diese
	 * Callback-Klasse benötigt. Die Kontaktlisten stellen Standardkontaktliste dar.
>>>>>>> refs/heads/Raphael
	 */
	private class MeineKontakteAnlegen implements AsyncCallback<Kontaktliste> {

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Kontaktliste result) {
			verwaltung.insertKontaktliste("Meine geteilten Kontakte", 0, n.getID(), new GeteilteKontakteAnlegen());

		}

	}

	class StartseiteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			MainForm mf = new MainForm();
			RootPanel.get("Buttonbar").clear();
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(mf);

		}

	}

	class ImpressumClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			ImpressumSeite imp = new ImpressumSeite();
			RootPanel.get("Buttonbar").clear();
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(imp);

		}

	}

	class GeteilteKontakteAnlegen implements AsyncCallback<Kontaktliste> {

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Kontaktliste result) {
			loadStartseite();

		}

	}
}