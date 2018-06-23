package de.hdm.itprojektgruppe4.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.user.client.ui.FlowPanel;

import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.*;
import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.client.NavigationTree;


/**
 * Die Klasse dient zur Darstellung und Verwaltung von Kontaktlisten.
 * 
 * @author Raphael
 *
 */
public class KontaktlisteForm extends VerticalPanel {

	private VerticalPanel vpanel = new VerticalPanel();
	private HorizontalPanel hpanel = new HorizontalPanel();

	private ScrollPanel scrollPanel = new ScrollPanel();
	private FlowPanel fpanel = new FlowPanel();
	private FlowPanel fpanelEdit = new FlowPanel();
	
	private Label lbl_kontaktliste = new Label("Kontaktlistenname:");
	private TextBox txt_kontaktliste = new TextBox();
	private KeyDownHandler changeListNameHandler = new ChangeListNameHandler(); 

	private KontaktCell kontaktcell = new KontaktCell();
	private CellList<Kontakt> kontaktCellList = new CellList<Kontakt>(kontaktcell);
	private SingleSelectionModel<Kontakt> selectionModel = new SingleSelectionModel<Kontakt>();
	private ListDataProvider<Kontakt> dataProvider = new ListDataProvider<Kontakt>();

	private Button kontaktAnzeigen = new Button("Kontakt anzeigen");
	private Button kontaktlisteTeilen = new Button("Kontaktliste teilen");
	private Button teilhaberschaften = new Button("Teilhaberschaften verwalten");
	private Button kontaktlisteBearbeiten = new Button("Kontaktliste bearbeiten");
	private Button zurStartseite = new Button("Zurueck zur Startseite");
	private Button kontaktHinzufuegen = new Button("Kontakt hinzufügen");
	private Button kontaktlisteLoeschen = new Button("Kontaktliste löschen");
	private Button kontaktEntfernen = new Button("Kontakt entfernen");
	private Button zurueck = new Button("Bearbeitung beenden");
	private Image listShared = new Image();
	private boolean deleteListAlert;

	private KontaktAdministrationAsync kontaktVerwaltung = ClientsideSettings.getKontaktverwaltung();

	private Kontaktliste kl = null;
	private Nutzer nutzer = new Nutzer();

	/**
	 * Konstruktor, der beim Auswaehlen einer Kontaktliste im Baum eingesetzt
	 * wird.
	 * 
	 * @param kontaktliste
	 */
	public KontaktlisteForm(Kontaktliste kontaktliste) {
		this.kl = kontaktliste;
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));
		kontaktVerwaltung.getAllKontakteFromKontaktliste(kl.getID(), new KontakteVonKontaktlisteCallback());

	}

	public void onLoad() {
		super.onLoad();

		// Instantiieren des DataProviders, der die Daten fuer die Liste haelt
		kontaktCellList.setSelectionModel(selectionModel);
		dataProvider.addDataDisplay(kontaktCellList);

		kontaktCellList.setSelectionModel(selectionModel);
		scrollPanel.setStyleName("scrollPanel");
		kontaktCellList.setStyleName("cellListKontakte");
		scrollPanel.add(kontaktCellList);
		HTML html1 = new HTML("<h2> Liste: " + kl.getBez() + "</h2>");

		/*
		 * Hinzufuegen der Buttons zur Buttonbar
		 */
		RootPanel.get("Buttonbar").clear();

		// Der Button <code>kontaktlisteBearbeiten</code> wird nur zum Panel
		// hinzugefuegt, wenn der angemeldete Nutzer Eigentuemer der
		// Kontaktliste ist
		if (kl.getNutzerID() == nutzer.getID()) {
			fpanel.add(kontaktlisteBearbeiten);
		}

		fpanel.add(kontaktAnzeigen);
		fpanel.add(kontaktlisteTeilen);
		fpanel.add(teilhaberschaften);
		fpanel.add(zurStartseite);
		RootPanel.get("Buttonbar").add(fpanel);

		/*
		 * Hinzufuegen der Ueberschrift und der CellList zum Vertical Panel
		 */
		hpanel.add(html1);
		vpanel.add(hpanel);
		vpanel.add(scrollPanel);
		RootPanel.get("Details").add(vpanel);

		/*
		 * Hinzufuegen der Clickhandler zu den Buttons, sowie des KeyDownHandlers zur Textbox
		 */
		kontaktAnzeigen.addClickHandler(new KontaktAnzeigenClickhandler());
		kontaktlisteTeilen.addClickHandler(new KontaktlisteTeilenClickhandler());
		teilhaberschaften.addClickHandler(new TeilhaberschaftenVerwaltenClickhandler());
		kontaktlisteBearbeiten.addClickHandler(new KontaktlisteBearbeitenClickhandler());
		zurStartseite.addClickHandler(new ZurueckZurStartseiteClickhandler());
		kontaktHinzufuegen.addClickHandler(new KontaktHinzufuegenClickhandler());
		kontaktEntfernen.addClickHandler(new KontaktEntfernenClickhandler());
		kontaktlisteLoeschen.addClickHandler(new KontaktlisteloeschenClickhandler());
		zurueck.addClickHandler(new BearbeitenBeendenClickhandler());
		txt_kontaktliste.addKeyDownHandler(changeListNameHandler);
		/*
		 * if-clause überprüft den Status der Liste. Ist dieser auf 1
		 * (=geteilt), so wird dieser visuell sichtbar gemacht, indem ein
		 * entsprechendes Symbol angefügt wird.
		 */
		if (kl.getStatus() == 1) {
			hpanel.add(listShared);

		}

	}
	
	/*
	 * Die Methode <code>setEditable</code> ermöglicht den Wechsel in den Bearbeitungsmodus.
	 * Dabei werden die Buttons der Kontaktlistenansicht entfernt und dafür Buttons in das RootPanel <code>Buttonbar</code> geladen,
	 * die das Bearbeiten einer Kontaktliste möglich machen.
	 */
	private void setEditable(){
		RootPanel.get("Buttonbar").clear();
		fpanelEdit.add(kontaktHinzufuegen);
		fpanelEdit.add(kontaktEntfernen);
		fpanelEdit.add(kontaktlisteLoeschen);
		fpanelEdit.add(zurueck);
		RootPanel.get("Buttonbar").add(fpanelEdit);
		
		hpanel.clear();
		hpanel.add(lbl_kontaktliste);
		hpanel.add(txt_kontaktliste);
		txt_kontaktliste.setText(kl.getBez());
	}
	

	/*
	 * Clickhandler ermöglicht das Anzeigen eines ausgewaehlten Kontaktes.
	 */
	private class KontaktAnzeigenClickhandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (selectionModel.getSelectedObject() == null) {
				Window.alert("Sie muessen einen Kontakt ausw�hlen");

			} else {
				if (kl.getID() == nutzer.getID()) {
					KontaktForm kf = new KontaktForm(selectionModel.getSelectedObject());
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(kf);
				} else {
					KontaktForm kf = new KontaktForm(selectionModel.getSelectedObject(), "Teilhaberschaft");
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(kf);
				}
			}
		}

	}

	/**
	 * Clickhandler, der das Teilen einer Kontaktliste ermöglicht. Wird der
	 * Button "Kontaktliste teilen" angewählt, öffnet sich die DialogBox
	 * <code>DialogBoxKontaktlisteTeilen</code>
	 */
	private class KontaktlisteTeilenClickhandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			DialogBoxKontaktlisteTeilen dbteilen = new DialogBoxKontaktlisteTeilen(kl);
			dbteilen.center();

		}

	}

	/*
	 * Clickhandler, der das Anwählen des Buttons "Teilhaberschaft verwalten"
	 * ermöglicht. Bei Aktivierung des Clickhandlers wird eine DialogBox mit den
	 * Teilhaberschaften an einer Kontaktliste geöffnet
	 */
	private class TeilhaberschaftenVerwaltenClickhandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			DialogBoxTeilhaberschaftVerwalten dbteilhaber = new DialogBoxTeilhaberschaftVerwalten(kl);
			dbteilhaber.center();
		}

	}

	/*
	 * ClickHandler um in den Bearbeitungsmodus einer Kontaktliste zu gelangen.
	 * Hier wird lediglich die Methode setEditable ausgeführt, welche die Buttons zur Bearbeitung zur Verfügung stellt.
	 */
	private class KontaktlisteBearbeitenClickhandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			setEditable();
		}

	}

	/*
	 * ClickHandler um auf die Startseite zurückzugelangen.
	 */
	private class ZurueckZurStartseiteClickhandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			MainForm mf = new MainForm();
			RootPanel.get("Buttonbar").clear();
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(mf);

		}

	}
	
	/**
	 * Clickhandler, der das Loeschen von Kontaktlisten bzw. die Aufloesung
	 * einer Teilhaberschaft bei Klick ermoeglicht Ist der Loeschende der
	 * Inhaber der Liste, wird die Kontaktliste komplett aus der Datenbank
	 * entfernt. Ist der Loeschende Teilhaber der Liste, wird lediglich die
	 * Teilhaberschaft an der Liste aufgeloest.
	 */
	private class KontaktlisteloeschenClickhandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			
			deleteListAlert = Window.confirm("Möchten Sie die Kontaktliste endgültig löschen ?");
			// Wenn die ausgewaehlte Kontaktliste vom Nutzer erstellt wurde,
			// wird diese geloescht
			if (deleteListAlert == true) {
				if (kl.getNutzerID() == nutzer.getID()) {
					kontaktVerwaltung.deleteKontaktliste(kl, new KontaktlisteloeschenCallback());
				}
				// Wenn nur eine Teilhaberschaft an der Kontaktliste besteht, wird
				// nur diese aufgelöst
				else {
					kontaktVerwaltung.deleteTeilhaberschaftByKontaktlisteID(kl.getID(), new KontaktlisteloeschenCallback());
				
				}

			} else if(deleteListAlert == false) {
				
			}

		}
	}

	/**
	 * Nested Class um den Button zum Hinzufuegen von Kontakten zur Kontaktliste
	 * bedienen zu koennen Bei Click auf den Button wird eine DialogBox
	 * geoeffnet, die ermoeglicht, Kontakt zu oeffnen.
	 */
	private class KontaktHinzufuegenClickhandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			DialogBoxKontaktZuKontaktliste dialogKontakt = new DialogBoxKontaktZuKontaktliste(kl);
			dialogKontakt.center();
		}

	}

	/**
	 * Clickhandler um das Entfernen eines Kontaktes aus der Kontaktliste zu
	 * ermoeglichen. Ist ein Kontakt ausgewaehlt, wird das gewaehlte
	 * Kontakt-Objekt sowohl aus der Datenbank, als auch aus der Celllist
	 * entfernt. Zum Entfernen des Objekts aus der Liste dient die Methode
	 * <code>remove()</code>.
	 */
	private class KontaktEntfernenClickhandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (selectionModel.getSelectedObject() == null) {
				Window.alert("Sie muessen einen Kontakt auswaehlen");
			} else {
				dataProvider.getList().remove(selectionModel.getSelectedObject());
				kontaktVerwaltung.deleteKontaktKontaktlisteByKontaktIDAndByKListID(
						selectionModel.getSelectedObject().getID(), kl.getID(), new KontaktEntfernenCallback());
			}

		}

	}

	/**
	 * Clickhandler der das Beenden der Bearbeitung einer Kontaktliste
	 * ermoeglicht. Da bei der Bearbeitung der Name und auch die Kontakte der
	 * Kontaktliste geaendert werden koennen, wird sowohl die KontaklisteForm
	 * als auch der Navigations-Baum neu geladen.
	 * 
	 * @author Raphael
	 *
	 */
	private class BearbeitenBeendenClickhandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			KontaktlisteForm kf = new KontaktlisteForm(kl);
			NavigationTree updatedNavTree = new NavigationTree();
			RootPanel.get("Details").clear();
			RootPanel.get("Navigator").clear();
			RootPanel.get("Details").add(kf);
			RootPanel.get("Navigator").add(updatedNavTree);

		}

	}
	
	/**
	 * Callback-Klasse für das Updaten einer Kontaktliste.
	 */
	private class UpdateKontaktlisteCallback implements AsyncCallback<Kontaktliste> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Kontaktliste result) {
			if (result == null) {
				Window.alert("Sie dürfen den Namen einer Standardkontaktliste nicht ändern.");
				KontaktlisteForm kf = new KontaktlisteForm(kl);
				NavigationTree updatedNavTree = new NavigationTree();
				RootPanel.get("Details").clear();
				RootPanel.get("Navigator").clear();
				RootPanel.get("Details").add(kf);
				RootPanel.get("Navigator").add(updatedNavTree);
			}
			else{
				NavigationTree updatedNavTree = new NavigationTree();
				RootPanel.get("Navigator").clear();
				RootPanel.get("Navigator").add(updatedNavTree);
			Window.alert("Der Name der Liste wurde erfolgreich zu " + result.getBez() + " geändert.");
		}
		}

	}
	
	/**
	 * KeyDownHandler um den Kontaktlisten-Namen durch Eingabe in der Textbox
	 * ändern zu können.
	 */
	private class ChangeListNameHandler implements KeyDownHandler {

		@Override
		public void onKeyDown(KeyDownEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				if(txt_kontaktliste.getText().isEmpty()){
					Window.alert("Sie müssen der Kontaktliste einen Namen geben");
				}else{
				kl.setBez(txt_kontaktliste.getValue());
				kontaktVerwaltung.updateKontaktliste(kl, new UpdateKontaktlisteCallback());
				}

			}

		}

	}

	/**
	 * Callback-Klasse, um alle Kontakte der Kontaktliste auszulesen. Jedes
	 * Kontakt Object aus dem Vector Result wird zum ListDataProvider
	 * hinzugefuegt, der die Daten fuer die Celllist haelt.
	 */
	private class KontakteVonKontaktlisteCallback implements AsyncCallback<Vector<Kontakt>> {

		@Override
		public void onFailure(Throwable caught) {
			// 

		}

		@Override
		public void onSuccess(Vector<Kontakt> result) {
			for (Kontakt k : result) {
				dataProvider.getList().add(k);
			}

		}

	}


	/**
	 * Callback-Klasse fuer die Loeschung der Kontaktliste
	 *
	 */
	private class KontaktlisteloeschenCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Die Kontaktliste konnte nicht gelöscht werden");
		}

		@Override
		public void onSuccess(Void result) {
			if (kl.getBez().equals("Meine Kontakte") || kl.getBez().equals("Meine geteilten Kontakte")) {
				Window.alert("Dies ist eine Standardkontaktliste und kann nicht gelöscht werden.");
			} else {
				Window.alert("Die Kontaktliste wurde erfolgreich gelöscht");
				MainForm main = new MainForm();
				NavigationTree updatedTree = new NavigationTree();
				RootPanel.get("Navigator").clear();
				RootPanel.get("Details").clear();
				RootPanel.get("Buttonbar").clear();
				RootPanel.get("Details").add(main);
				RootPanel.get("Navigator").add(updatedTree);
			}
		}

	}

	/**
	 * Diese Nested Class wird als Callback benoetigt um das Entfernen eines
	 * Kontaktes aus einer Kontaktliste zu ermoeglichen
	 */
	private class KontaktEntfernenCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Void result) {
			Window.alert("Der Kontakt wurde erfolgreich entfernt");
			NavigationTree updatedTree = new NavigationTree();
			RootPanel.get("Navigator").clear();
			RootPanel.get("Navigator").add(updatedTree);
		}

	}


	/**
	 * Hier wird der DataProvider mit den entsprechenden Daten fuer die CellList
	 * erstellt. Dabei wird der DataProvider mithilfe eines RPC-Callbacks, der alle Kontakte einer Kontaktliste enthält, befüllt.
	 
	private class KontakteDataProvider extends AsyncDataProvider<Kontakt> {

		@Override
		protected void onRangeChanged(HasData<Kontakt> display) {
			final Range range = display.getVisibleRange();

			kontaktVerwaltung.getAllKontakteFromKontaktliste(kl.getID(), new AsyncCallback<Vector<Kontakt>>() {
				int start = range.getStart();

				@Override
				public void onFailure(Throwable caught) {


				}

				@Override
				public void onSuccess(Vector<Kontakt> result) {

					List<Kontakt> list = new ArrayList<Kontakt>();
					for (Kontakt k : result) {
						list.add(k);
					}
					updateRowData(start, list);

				}

			});

		}

	}
 */
}
