package de.hdm.itprojektgruppe4.client;

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

import de.hdm.itprojektgruppe4.client.gui.MainForm;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.LoginService;
import de.hdm.itprojektgruppe4.shared.LoginServiceAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;

/**
 * @author Georg EntryPoint wird definiert
 */
public class ITProjektSS18 implements EntryPoint {

	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Bitte melde dich mit deinem Google-Konto an");
	private Label eingeloggt = new Label("Momentan in WYNWYN angemeldet: ");
	private Anchor signInLink = new Anchor("Anmelden");
	private Anchor signOutLink = new Anchor("Logout");
	private Anchor impressum = new Anchor("Impressum");
	private Anchor startseite = new Anchor("Startseite");
	private Anchor deleteNutzer = new Anchor("Konto löschen");
	private Anchor loggedNutzer;
	private boolean sureDelete;

	Nutzer n1 = new Nutzer();
	Nutzer n = new Nutzer();

	LoginServiceAsync loginService = GWT.create(LoginService.class);
	/**
	 * Administration wird instanziiert um dessen Methoden zu verwenden
	 */

	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();

	private static String editorHtmlName = "ITProjektSS18.html";

	public ITProjektSS18() {

	}

	public void onModuleLoad() {

		impressum.setStyleName("Impressum");
		signOutLink.setStyleName("Logout");
		startseite.setStyleName("Startseite");
		eingeloggt.setStyleName("AktiverUser");
		deleteNutzer.setStyleName("KontoLoeschen");

		/**
		 * Loginstatus wird anhand des LoginService �berpr�ft
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
	 * Die CheckNewNutzer Methode hat den Zweck, zu Pr�fen: Den Aktuellen Nutzer
	 * aus der Datenbank zu finden Sollte der Nutzer nicht in der Datenbank
	 * vorhanden sein, wird dieser angelegt und anschlie�end wird die LoadLogin
	 * Methode aufgerufen Sollte der Nutzer in der Datenbank vorhanden sein, so
	 * wird die LoadStartseite Methode aufgerufen Zudem werden bei einem
	 * Neuangelegten Nutzer Zwei Kontaktlisten Angelegt
	 * 
	 * @param result
	 * @return
	 */
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
					loggedNutzer = new Anchor(result.getEmail());
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
							loggedNutzer = new Anchor(result.getEmail());
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
	 * navigationTree Zudem beinhaltet die Methode diverse Widget f�r das GUI
	 */
	private void loadStartseite() {
		MainForm mainForm = new MainForm();
		NavigationTree navigationTree = new NavigationTree();
		signOutLink.setHref(loginInfo.getLogoutUrl());
		RootPanel.get("Header").add(eingeloggt);
		RootPanel.get("Header").add(loggedNutzer);
		RootPanel.get("Header").add(deleteNutzer);
		RootPanel.get("Header").add(signOutLink);
		RootPanel.get("Header").add(impressum);
		RootPanel.get("Header").add(startseite);
		impressum.addClickHandler(new ImpressumClickHandler());
		startseite.addClickHandler(new StartseiteClickHandler());
		deleteNutzer.addClickHandler(new DeleteNutzerClickHandler());
		RootPanel.get("Details").add(mainForm);
		RootPanel.get("Navigator").add(navigationTree);

	}

	/**
	 * Meldet sich ein Nutzer neu auf der Plattform an, sollen sofort die
	 * Kontaktlisten "Meine Kontakte" und "Mit mir geteilte Kontakte" erstellt
	 * werden. Hierfür wird diese Callback-Klasse benötigt. Die Kontaktlisten
	 * stellen Standardkontaktliste da. Meldet sich ein Nutzer neu auf der
	 * Plattform an, sollwe sofort die Kontaktlisten "Meine Kontakte" und "Mit
	 * mir geteilte Kontakte" erstellt werden. Hierfür wird diese
	 * Callback-Klasse benötigt. Die Kontaktlisten stellen Standardkontaktliste
	 * dar.
	 */
	private class MeineKontakteAnlegen implements AsyncCallback<Kontaktliste> {

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Kontaktliste result) {
			verwaltung.insertKontaktliste("Mit mir geteilte Kontakte", 0, n.getID(), new GeteilteKontakteAnlegen());

		}

	}

	/**
	 * 
	 * Clickhandler Klasse f�r die Startseite
	 *
	 */
	class StartseiteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			MainForm mf = new MainForm();
			NavigationTree nf = new NavigationTree();
			RootPanel.get("Buttonbar").clear();
			RootPanel.get("Details").clear();
			RootPanel.get("Navigator").clear();
			RootPanel.get("Details").add(mf);
			RootPanel.get("Navigator").add(nf);
		}

	}

	/**
	 * 
	 * Clickhandler f�r das Impressum
	 *
	 */
	class ImpressumClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			ImpressumSeite imp = new ImpressumSeite();
			RootPanel.get("Buttonbar").clear();
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(imp);

		}

	}

	class DeleteNutzerClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub

			n1.setID(Integer.parseInt(Cookies.getCookie("id")));
			n1.setEmail(Cookies.getCookie("email"));

			Window.alert(n1 + "   <---- NUTZER LÖSCHEN ");
			sureDelete = Window.confirm("Möchten Sie Ihr Nutzerprofil vollständig löschen?");
			if (sureDelete == true) {

				verwaltung.deleteNutzer(n1, new DeleteNutzerCallBack());
			}
		}

	}

	class DeleteNutzerCallBack implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Beim Löschen Ihrers Profils ist etwas schief gegangen" + caught);
		}

		@Override
		public void onSuccess(Void result) {
			Window.alert("hallo");
			RootPanel.get("Buttonbar").clear();
			RootPanel.get("Navigator").clear();
			RootPanel.get("Details").clear();
			RootPanel.get("Header").clear();

			Cookies.removeCookie("id");
			Cookies.removeCookie("email");

			onModuleLoad();

		}

	}

	/**
	 * 
	 * AsyncCallback f�r das Anlegen geteilter Kontakte Die Methode verweist
	 * zudem auf die loadStartseite() Methode
	 *
	 */
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