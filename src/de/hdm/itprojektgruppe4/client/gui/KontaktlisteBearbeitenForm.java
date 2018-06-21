package de.hdm.itprojektgruppe4.client.gui;

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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.FlowPanel;

import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.*;
import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.client.NavigationTree;

/**
 * Diese Klasse dient der Bearbeitung einer Kontaktliste. Hier können Kontakte
 * zur Kontaktliste hinzugefuegt oder entfernt werden, der Name der Kontaktliste
 * geaendert werden, sowie die komplette Kontaktliste geloescht werden.
 *
 */
public class KontaktlisteBearbeitenForm extends VerticalPanel {

	private KontaktAdministrationAsync kontaktVerwaltung = ClientsideSettings.getKontaktverwaltung();

	private FlowPanel fpanel = new FlowPanel();
	private HorizontalPanel hpanel = new HorizontalPanel();
	private VerticalPanel vpanel = new VerticalPanel();

	private Nutzer nutzer = new Nutzer();
	private Kontaktliste kl = null;
	private KontaktlisteKontaktTreeViewModel kktvw = new KontaktlisteKontaktTreeViewModel();

	private Button kontaktHinzufuegen = new Button("Kontakt hinzufuegen");
	private Button kontaktlisteLoeschen = new Button("Kontaktliste loeschen");
	private Button kontaktEntfernen = new Button("Kontakt entfernen");
	private Button zurueck = new Button("Bearbeitung beenden");

	private KeyDownHandler changeListNameHandler = new ChangeListNameHandler(); 
	private Label lbl_kontaktliste = new Label("Kontaktlistenname:");
	private TextBox txt_kontaktliste = new TextBox();

	private KontaktCell kontaktcell = new KontaktCell();
	private CellList<Kontakt> kontaktCellList = new CellList<Kontakt>(kontaktcell);
	private SingleSelectionModel<Kontakt> selectionModel = new SingleSelectionModel<Kontakt>();
	private ListDataProvider<Kontakt> dataProvider = new ListDataProvider<Kontakt>();
	
	
	public KontaktlisteBearbeitenForm(Kontaktliste kl) {
		this.kl = kl;
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));
		kontaktVerwaltung.getAllKontakteFromKontaktliste(kl.getID(), new KontakteVonKontaktlisteCallback());
		setTreeViewModel(kktvw);
	}

	public void onLoad() {
		super.onLoad();
		kontaktCellList.setSelectionModel(selectionModel);
		dataProvider.addDataDisplay(kontaktCellList);

		/*
		 * Hinzufuegen der Buttons zur Buttonbar
		 */
		RootPanel.get("Buttonbar").clear();
		fpanel.add(kontaktHinzufuegen);
		fpanel.add(kontaktEntfernen);
		fpanel.add(kontaktlisteLoeschen);
		fpanel.add(zurueck);
		RootPanel.get("Buttonbar").add(fpanel);

		txt_kontaktliste.setText(kl.getBez());

		hpanel.add(lbl_kontaktliste);
		hpanel.add(txt_kontaktliste);

		vpanel.add(hpanel);
		vpanel.add(kontaktCellList);
		RootPanel.get("Details").add(vpanel);

		kontaktHinzufuegen.addClickHandler(new KontaktHinzufuegenClickhandler());
		kontaktEntfernen.addClickHandler(new KontaktEntfernenClickhandler());
		kontaktlisteLoeschen.addClickHandler(new KontaktlisteloeschenClickhandler());
		zurueck.addClickHandler(new BearbeitenBeendenClickhandler());
		txt_kontaktliste.addKeyDownHandler(changeListNameHandler);

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
			// Wenn die ausgewaehlte Kontaktliste vom Nutzer erstellt wurde,
			// wird diese geloescht
			if (kl.getNutzerID() == nutzer.getID()) {
				kontaktVerwaltung.deleteKontaktliste(kl, new KontaktlisteloeschenCallback());
			}
			// Wenn nur eine Teilhaberschaft an der Kontaktliste besteht, wird
			// nur diese aufgel�st
			else {
				kontaktVerwaltung.deleteTeilhaberschaftByKontaktlisteID(kl.getID(), new KontaktlisteloeschenCallback());
			
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
				kontaktVerwaltung.deleteKontaktKontaktlisteByKontaktID(selectionModel.getSelectedObject().getID(),
						new KontaktEntfernenCallback());
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
	 * KeyDownHandler um den Kontaktlisten-Namen durch Eingabe in der Textbox ändern zu können.
	 */
	private class ChangeListNameHandler implements KeyDownHandler{

		@Override
		public void onKeyDown(KeyDownEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				kl.setBez(txt_kontaktliste.getValue());
				kontaktVerwaltung.updateKontaktliste(kl, new UpdateKontaktlisteCallback());
				NavigationTree updatedNavTree = new NavigationTree();
				RootPanel.get("Navigator").clear();
				RootPanel.get("Navigator").add(updatedNavTree);
	
			}
			
			
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
				Window.alert("Sie dürfen den Namen einer Standardkontaktliste nicht ändern." );
				KontaktlisteForm kf = new KontaktlisteForm(kl);
				NavigationTree updatedNavTree = new NavigationTree();
				RootPanel.get("Details").clear();
				RootPanel.get("Navigator").clear();
				RootPanel.get("Details").add(kf);
				RootPanel.get("Navigator").add(updatedNavTree);	
			}
			Window.alert("Der Name der Liste wurde erfolgreich zu " + result.getBez() +" geändert." );
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
			// TODO Auto-generated method stub

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
			}
			else {
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
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Void result) {
			
		}

	}
	
	private void setTreeViewModel (KontaktlisteKontaktTreeViewModel kktvw){
		this.kktvw = kktvw;
	}

}
