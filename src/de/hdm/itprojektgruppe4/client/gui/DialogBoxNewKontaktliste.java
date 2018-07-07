package de.hdm.itprojektgruppe4.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.client.NavigationTree;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;

public class DialogBoxNewKontaktliste extends DialogBox {

	/*
	 * Erstellen der benötigten Variablen für diese Klasse
	 * 
	 */
	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();
	KontaktlisteKontaktTreeViewModel kktvm = null;

	private VerticalPanel vpanel = new VerticalPanel();

	private Button anlegen = new Button("Kontaktliste anlegen");
	private Button cancel = new Button("Abbrechen");

	private HTML labelListe = new HTML("<h3>Neue Kontaktliste anlegen</h3>");
	private Label infolabel = new Label(
			"Bitte beachten Sie, dass Sie keine Kontaktliste mit dem gleichen Namen einer anderen Liste anlegen können");
	private Image list = new Image("Image/Neue_Liste_2.png");
	private TextBox boxBez = new TextBox();
	private FlexTable kList = new FlexTable();
	private Nutzer nutzer = new Nutzer();
	private Kontaktliste kl = new Kontaktliste();

	/*
	 * Erstellen eines leeren Konstruktors um in anderen Klassen die DialogBox
	 * aufzurufen
	 */

	public DialogBoxNewKontaktliste() {

	}

	public void onLoad() {

		super.onLoad();

		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));

		/*
		 * Die FlexTable mit den richtigen Widgets befüllen
		 */

		kList.setWidget(0, 0, labelListe);
		kList.setWidget(0, 1, list);
		kList.setWidget(2, 0, boxBez);

		kList.setWidget(4, 0, anlegen);
		kList.setWidget(4, 1, cancel);
		boxBez.getElement().setAttribute("placeholder", "Bezeichnung");

		vpanel.add(kList);
		vpanel.add(infolabel);

		this.setStyleName("DialogboxBackground");
		this.add(vpanel);

		/*
		 * Hinzufügen der Clickhandler zu den Buttons
		 */

		anlegen.addClickHandler(new KontaktListeAnlegen());
		cancel.addClickHandler(new CancelButton());

	}
	/*
	 * Erstellen der ClickHandler Klassen und Asynccallback Klassen
	 * 
	 */

	private class CancelButton implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			hide();
		}

	}

	private class KontaktListeAnlegen implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (boxBez.getValue().equals("")) {
				Window.alert("Bitte geben Sie ein Bezeichnung ein");
			} else {
				verwaltung.insertKontaktliste(boxBez.getValue(), 0, nutzer.getID(), new Liste());
				hide();
			}

		}

	}

	private class Liste implements AsyncCallback<Kontaktliste> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten: " + caught.getMessage());

		}

		@Override
		public void onSuccess(Kontaktliste result) {
			kl = result;
			if (result == null) {
				Window.alert("Kontaktliste konnte nicht angelegt werden, da dieser Name bereits vorhanden ist");

			} else {
				Window.alert("Die Kontaktliste wurde erfolgreich angelegt");

				KontaktlisteForm kf = new KontaktlisteForm(kl);
				NavigationTree navigation = new NavigationTree();
				RootPanel.get("Details").clear();
				RootPanel.get("Navigator").clear();
				RootPanel.get("Buttonbar").clear();
				RootPanel.get("Navigator").add(navigation);
				RootPanel.get("Details").add(kf);
			}
		}

	}

}
