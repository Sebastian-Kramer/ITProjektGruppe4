package de.hdm.itprojektgruppe4.client.gui;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;

import java.util.Vector;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.client.NavigationTree;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.bo.Teilhaberschaft;

public class DialogBoxKontaktlisteTeilen extends DialogBox {

	private static KontaktAdministrationAsync kontaktVerwaltung = ClientsideSettings.getKontaktVerwaltung();

	private Nutzer nutzer = new Nutzer();
	private Kontaktliste kl = null;
	private NutzerCell nutzerCell = new NutzerCell();

	private VerticalPanel vpanel = new VerticalPanel();
	private HTML html = null;
	private Button teilen = new Button("Kontaktliste teilen");
	private Button abbrechen = new Button("abbrechen");

	private CellList<Nutzer> nutzerList = new CellList<Nutzer>(nutzerCell);
	private SingleSelectionModel<Nutzer> nutzerSelection = new SingleSelectionModel<Nutzer>();
	private FlexTable ft = new FlexTable();
	private ListDataProvider<Nutzer> dataProvider = new ListDataProvider<Nutzer>();
	
	private MultiWordSuggestOracle nutzerOracle = new MultiWordSuggestOracle();
	private SuggestBox nutzerSugBox = new SuggestBox(nutzerOracle);
	
	/*
	 * Konstruktur, der beim Aufruf der Klasse zum Einsatz kommt.
	 */
	DialogBoxKontaktlisteTeilen(Kontaktliste kl) {
		this.kl = kl;
		kontaktVerwaltung.findNutzerToShareListWith(kl.getID(), nutzer.getID(), new NutzerToSugBox());
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));
		dataProvider.addDataDisplay(nutzerList);
		nutzerList.setSelectionModel(nutzerSelection);
		html = new HTML("Geben Sie die Mail-Adresse des Nutzers ein, mit dem Sie die Kontaktliste teilen möchten:");
	}

	public void onLoad() {
		super.onLoad();
		
		teilen.addClickHandler(new TeilenClickhandler());
		abbrechen.addClickHandler(new AbbrechenClickhandler());

		ft.setWidget(2, 0, teilen);
		ft.setWidget(2, 1, abbrechen);
		ft.setStylePrimaryName("Flextable");

		vpanel.add(html);
		vpanel.add(nutzerSugBox);
		vpanel.add(ft);
		this.add(vpanel);
		this.setStylePrimaryName("DialogboxBackground1");
	}

	/**
	 * Clickhandler, der das Teilen einer Kontaktliste mit einem Nutzer
	 * ermoeglicht. Hierfuer wird das in der Celllist ausgewaehlte Objekt einem
	 * neuen Nutzer-Objekt zugewiesen. Die Teilhaberschaft wird dann anhand der
	 * Kontaktliste und dem Nutzer mithilfe eines Callbacks in der Datenbank
	 * angelegt.
	 *
	 */
	private class TeilenClickhandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// ist kein Nutzer ausgewählt, wird dies mithilfe eines
			// Window-Alerts dem User kenntlich gemacht.
			if (nutzerSugBox.getValue().isEmpty() == true) {
				Window.alert("Bitte suchen Sie nach einem Nutzer, mit dem Sie die Kontaktliste teilen wollen.");
			} else {
				kontaktVerwaltung.insertTeilhaberschaftKontaktliste(kl.getID(), nutzerSugBox.getValue(), nutzer.getID(),
						new TeilhaberschaftErstellenCallback());
			
			}
		}
	}

	/**
	 * Implementierung eines Clickhandlers, um beim Klick des Abbrechen-Buttons
	 * die DialogBox zu schliessen.
	 */
	private class AbbrechenClickhandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			hide();

		}

	}

	/**
	 * Callback-Klasse um eine Teilhaberschaft zu erstellen. Wird eine
	 * Teilhaberschaft erstellt, wird auch gleichzeitig der Status der
	 * Kontaktliste auf 1 (= geteilt) gesetzt.
	 */
	private class TeilhaberschaftErstellenCallback implements AsyncCallback<Teilhaberschaft> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Teilhaberschaft result) {
			if (result == null) {
				Window.alert("Sie koennen diese Liste nicht teilen");
			} else {
				Window.alert("Die Liste wurde erfolgreich geteilt");
				DialogBoxKontaktlisteTeilen.this.hide();
				KontaktlisteForm kontaktlisteForm = new KontaktlisteForm(kl);
				NavigationTree updatedTree = new NavigationTree();
				RootPanel.get("Navigator").clear();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(kontaktlisteForm);
				RootPanel.get("Navigator").add(updatedTree);
			}

		}

	}


	
	/**
	 * Callback-Klasse um alle Nutzer, mit denen die Kontaktliste geteilt werden kann zu erhalten.
	 * Alle Objekte des Ergebnisvektors werden dem MultiSuggestOracle der SuggestBox hinzugefügt, damit diese
	 * als Vorschläge bei der Eingabe erscheinen.
	 */
	private class NutzerToSugBox implements AsyncCallback<Vector<Nutzer>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Vector<Nutzer> result) {
			// TODO Auto-generated method stub
			for (Nutzer n : result) {
				if (n.getID() != nutzer.getID() ) {
					nutzerOracle.add(n.getEmail());
				} else {

				}
			}
		}
		
	}
	


}
