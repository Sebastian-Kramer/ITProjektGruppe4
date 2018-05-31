package de.hdm.itprojektgruppe4.client;


import de.hdm.itprojektgruppe4.client.gui.MainForm;
import de.hdm.itprojektgruppe4.client.gui.Startseite;
import de.hdm.itprojektgruppe4.shared.FieldVerifier;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.LoginService;
import de.hdm.itprojektgruppe4.shared.LoginServiceAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.client.gui.KontaktlisteKontaktTreeViewModel;
import de.hdm.itprojektgruppe4.client.NavigationTree;

import java.util.Date;
import java.util.Vector;



import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektgruppe4.client.gui.MainForm;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.LoginService;
import de.hdm.itprojektgruppe4.shared.LoginServiceAsync;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;

public class ITProjektSS18 implements EntryPoint {

	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Bitte melde dich mit deinem Google-Konto an");
	private Anchor signInLink = new Anchor("Anmelden");
	private Anchor signOutLink = new Anchor("Logout");

	LoginServiceAsync loginService = GWT.create(LoginService.class);
	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();

	private static String editorHtmlName = "ITProjektSS18.html";

	public void onModuleLoad() {

		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL() + editorHtmlName, new AsyncCallback<LoginInfo>() {

		public void onFailure(Throwable error) {
			
		}
		public void onSuccess(LoginInfo result) {
		ClientsideSettings.setCurrentUser(result);

		loginInfo = result;
		if(loginInfo.isLoggedIn()) {
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
							Window.alert("Nutzer" + finalLog.getEmailAddress() + " wurde erfolgreich angelegt.");
							Cookies.setCookie("email", result.getEmail());
							Cookies.setCookie("id", result.getID()+"");
							loadStartseite();

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
		RootPanel.get("Details").add(signOutLink);
		RootPanel.get("Details").add(mainForm);
		RootPanel.get("Navigator").add(navigationTree);
	}

}