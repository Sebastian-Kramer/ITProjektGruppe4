package de.hdm.itprojektgruppe4.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.client.EigenschaftAuspraegungWrapper;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;

/**
 * Die DialogboxTeilhaberschaft zeigt die jeweiligen Nutzer an, die eine
 * Teilhaberschaft an einer Kontaktliste, an einem Kontakt oder an einer
 * Ausprägung haben. Hierfür werden zwei verschiedene Konstruktoren benötigt,
 * die die jeweils benötigten Daten aus der Datenbank in eine CellList lädt.
 * Über diese Dialogbox können die vorhanden Teilhaberschaften auch wieder
 * entfernt werden.
 * 
 */

public class DialogBoxTeilhaberschaftVerwalten extends DialogBox {

	private static KontaktAdministrationAsync kontaktVerwaltung = ClientsideSettings.getKontaktVerwaltung();

	private Nutzer nutzer = new Nutzer();
	private EigenschaftAuspraegungWrapper ea = new EigenschaftAuspraegungWrapper();
	private Eigenschaftauspraegung e = new Eigenschaftauspraegung();
	private Kontaktliste k = new Kontaktliste();
	private Kontakt kon = new Kontakt();

	private Button teilhaberschaftAufloesen = new Button("Teilhaberschaft entfernen");
	private Button abbrechen = new Button("abbrechen");

	private HTML html1 = null;

	private FlexTable flextable = new FlexTable();
	private NutzerCell nutzerCell = new NutzerCell();
	private CellList<Nutzer> nutzerList = new CellList<Nutzer>(nutzerCell);
	private SingleSelectionModel<Nutzer> selectionModel = new SingleSelectionModel<Nutzer>();
	private ListDataProvider<Nutzer> dataProvider = new ListDataProvider<Nutzer>();
	private VerticalPanel vpanel = new VerticalPanel();

	/**
	 * Dieser Konstruktor wird verwendet, wenn der angemeldete Nutzer im System
	 * die Teilhaberschaft einer bestimmten Kontaktliste verwalten möchte, dazu
	 * wird das ausgewählte Kontaktliste-Object übergeben.
	 * 
	 * @param kl
	 */
	DialogBoxTeilhaberschaftVerwalten(Kontaktliste kl) {
		this.k = kl;
		// Setzen der Nutzerinformationen
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));

		html1 = new HTML("Diese <b> Nutzer </b> haben eine <b> Teilhaberschaft </b> an der Kontaktliste");

		/*
		 * Ist der angemeldete User Eigentuemer der gew�hlten Kontaktliste,
		 * werden alle Teilhaber der Kontaktliste der Celllist hinzugef�gt. Der
		 * Eigentuemer soll in der Lage sein, jegliche Teilhaberschaften an
		 * einer Kontaktliste aufzul�sen.
		 * 
		 * Ist der angemeldete Nutzer Teilhaber einer Kontaktliste, werden die
		 * Anweiseungen im else-Block erledigt. Lediglich die Nutzer, mit denen
		 * der angemeldete Nutzer die Kontaktliste geteilt hat, werden der
		 * Celllist hizugefuegt. Hierzu dient der Aufruf der Methode
		 * <code>findSharedWithNutzer</code>
		 */
		if (nutzer.getID() == k.getID()) {
			kontaktVerwaltung.findAllTeilhaberFromKontaktliste(k.getID(), new TeilhaberVonKontaktliste());
			dataProvider.addDataDisplay(nutzerList);
		} else {
			kontaktVerwaltung.findSharedWithNutzer(nutzer.getID(), k.getID(), new SharedWithNutzerCallback());
			dataProvider.addDataDisplay(nutzerList);
		}

		// Hinzufuegen der Clickhandler zu den Buttons
		abbrechen.addClickHandler(new AbbrechenClickhandler());
		teilhaberschaftAufloesen.addClickHandler(new TeilhaberschaftAufloesenClickhandler());

		// Anordnen der Buttons und der Celllist mithilfe einer Flextable und
		// anschlie�endes Hinzufuegen der Flextable zum VerticalPanel

	}

	/**
	 * Dieser Konstruktor wird verwendet, wenn der angemeldete Nutzer im System
	 * die Teilhaberschaft einer bestimmten Ausprägung verwalten möchte, dazu
	 * wird das ausgewählte EigenschaftAuspraegungWrapper-Object und das
	 * Kontakt-Object übergeben.
	 * 
	 * @param eaw
	 * @param kon
	 */
	DialogBoxTeilhaberschaftVerwalten(EigenschaftAuspraegungWrapper eaw, Kontakt kon) {
		this.ea = eaw;  
		this.kon = kon;
		this.e = eaw.getAuspraegung();
		
		Window.alert(e.getWert()); 

		html1 = new HTML("Diese <b> Nutzer </b> haben eine <b> Teilhaberschaft </b> an der Ausprägung");

		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));

		kontaktVerwaltung.getAllTeilhaberfromAuspraegung(ea.getAuspraegungID(), new AllTeilhaberschaften());
		dataProvider.addDataDisplay(nutzerList);

		abbrechen.addClickHandler(new AbbrechenClickhandler());
		teilhaberschaftAufloesen.addClickHandler(new LoeschenClickHandler());

	}

	/**
	 * Die onLoad() Methode wird automatisch beim Öffnen der Dialogbox
	 * ausgeführt. Dabei wird differenziert, ob man die Teilhaberschaft einer
	 * Kontaktliste oder die Teilahaberschaft einer Ausprägung verwalten möchte.
	 * Dementsprechend ist die CellList befüllt und die entsprechenden Nutzer
	 * werden angezeigt.
	 */
	public void onLoad() {

		super.onLoad();

		nutzerList.setSelectionModel(selectionModel);

		flextable.setWidget(0, 0, nutzerList);
		flextable.setWidget(1, 0, abbrechen);
		flextable.setWidget(1, 1, teilhaberschaftAufloesen);

		flextable.setStylePrimaryName("Flextable");

		vpanel.add(html1);
		vpanel.add(flextable);

		this.setStylePrimaryName("DialogboxBackground1");
		this.add(vpanel);

	}

	/**
	 * Callback - Klasse um alle Teilhaberschaften an einer Ausprägung in die
	 * Celllist zu speichern.
	 * 
	 * @author Sebi_0107
	 *
	 */
	private class AllTeilhaberschaften implements AsyncCallback<Vector<Nutzer>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Die Teilhaber der gewählten Ausprägung konnten nicht geladen werden");

		}

		@Override
		public void onSuccess(Vector<Nutzer> result) {
			for (Nutzer n : result) {
				Window.alert(n.getEmail());
				dataProvider.getList().add(n);
			}

		}

	}

	/**
	 * Clickhandler um das Verwalten der Teilhaberschaften abzubrechen. Bei
	 * Ausloesen des Clickhandlers wird die DialogBox geschlossen
	 */
	private class AbbrechenClickhandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			hide();

		}

	}

	/**
	 * Clickhandler, um das Loeschen einer Teilhaberschaft zu ermoeglichen. Die
	 * asynchrone Callback-Methode
	 * <code>deleteTeilhaberschaftByTeilhaberID</code> wird aufgerufen, um die
	 * Teilhaberschaft anhand der ID des gew�hlten Nutzer-Objekts zu entfernen.
	 * Aschlie�end wird die DialogBox geschlossen.
	 */
	private class TeilhaberschaftAufloesenClickhandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
		kontaktVerwaltung.deleteTeilhaberschaftAnKontaktliste(selectionModel.getSelectedObject().getID(), k.getID(), new TeilhaberschaftLoeschenCallback() );	
		}

	}

	private class LoeschenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Window.alert(e.getWert());
			kontaktVerwaltung.deleteUpdateTeilhaberschaft(e, selectionModel.getSelectedObject(), nutzer, kon,
					new TeilhaberschaftAuspraegungLoeschenCallback());
		}

	}

	/**
	 * Callback fuer die Loeschung der Teilhaberschaft. Nach erfolgreicher
	 * Loeschung wird die Dialogbox geschlossen.
	 *
	 */
	private class TeilhaberschaftLoeschenCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Die Teilhaberschaft konnte nicht gelöscht werden");

		}

		@Override
		public void onSuccess(Void result) {
			hide();
			if (k.getNutzerID() == nutzer.getID()) {
				Window.alert("Die Teilhaberschaft wurde erfolgreich geloescht");
				KontaktlisteForm kf = new KontaktlisteForm(k);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(kf);
			} else {
				Window.alert("Die Teilhaberschaft wurde erfolgreich geloescht");
				KontaktlisteForm kf = new KontaktlisteForm(k, "Teilhaberschaft");
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(kf);
			}

		}

	}
	
	private class TeilhaberschaftAuspraegungLoeschenCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Die Teilhaberschaft konnte nicht gelöscht werden");
			
		}

		@Override
		public void onSuccess(Void result) {
			Window.alert("Die Teilhaberschaft wurde erfolgreich geloescht");
			hide();
			if (k.getNutzerID() == nutzer.getID()) {
			TeilhaberschaftForm tf = new TeilhaberschaftForm(kon);
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(tf);
			} else {
				TeilhaberschaftForm tf = new TeilhaberschaftForm(kon, "Teilhaberschaft");
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(tf);
			}
			
			
		}
		
	}

	/**
	 * Callback-Klasse. Gibt alle Nutzer als Vector aus, mit denen der
	 * angemeldete Nutzer die Kontaktliste geteilt hat. Jedes Objekt aus dem
	 * Vector <code>result</code> wird dabei der Celllist hinzugefuegt.
	 *
	 */
	private class SharedWithNutzerCallback implements AsyncCallback<Vector<Nutzer>> {

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Vector<Nutzer> result) {
			for (Nutzer n : result) {
				dataProvider.getList().add(n);
				if (nutzer.getID() == n.getID()) {
					dataProvider.getList().add(n);
				}
			}

		}

	}

	/**
	 * Callback-Klasse um alle Teilhaber an einer Kontaktliste anzeigen zu
	 * lassen. Bei erfolgreicher Abfrage werden die Objekte aus dem
	 * Ergebnis-Vector zur Celllist hinzugefuegt.
	 * 
	 * Diese Klasse wird nur ben�tigt, wenn der angemeldete Nutzer auch
	 * gleichzeitig der Eigentuemer der Kontaktliste ist, da der Eigentuemer
	 * Zugriff auf saemtliche Teilhaberschaften haben soll.
	 * 
	 * Jedes Nutzer Objekt wird aus dem Ergebnis-Vektor gelesen und der Celllist
	 * hinzugefuegt.
	 *
	 */
	private class TeilhaberVonKontaktliste implements AsyncCallback<Vector<Nutzer>> {

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Vector<Nutzer> result) {
			for (Nutzer n : result) {
				dataProvider.getList().add(n);
				if (nutzer.getID() == n.getID()) {
					dataProvider.getList().remove(n);
				}

			}

		}

	}

	class ChangeHandler implements Handler {

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {

		}

	}

}
