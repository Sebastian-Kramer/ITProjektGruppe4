package de.hdm.itprojektgruppe4.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.client.NavigationTree;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.KontaktKontaktliste;

/**
 * Die Klasse <code>DialogBoxKontaktZuKontaktliste</code> ermöglicht das
 * Hinzufuegen eines oder mehrerer Kontakte zu einer Kontaktliste
 * 
 * @author Raphael
 *
 */
public class DialogBoxKontaktZuKontaktliste extends DialogBox {

	private static KontaktAdministrationAsync kontaktVerwaltung = ClientsideSettings.getKontaktVerwaltung();

	private Nutzer nutzer = new Nutzer();
	private Kontaktliste kl = null;

	private VerticalPanel vpanel = new VerticalPanel();
	private HorizontalPanel hpanel = new HorizontalPanel();
	private MultiSelectionModel<Kontakt> kontaktSelection = new MultiSelectionModel<Kontakt>();

	private CellTable<Kontakt> kontaktTable = new CellTable<Kontakt>();

	private Button abbrechen = new Button("Abbrechen");
	private Button kontakteHinzufuegen = new Button("Hinzufügen");
	private ScrollPanel scrollPanel = new ScrollPanel();
	private HTML txt = new HTML("Folgende Kontakte können Sie der Kontaktliste hinzufügen:");
	private HTML ueberschrift = new HTML("<h3>Kontakte zur Kontaktliste hinzufügen");

	/*
	 * Konstruktor der beim Aufrufen der DialogBox zum Einsatz kommt
	 */
	DialogBoxKontaktZuKontaktliste(Kontaktliste kl) {
		this.kl = kl;
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));
		kontaktVerwaltung.hinzuzufuegendeKontakte(nutzer.getID(), kl.getID(), new AlleKontakteVonNutzer());
	}

	public void onLoad() {
		super.onLoad();

		kontaktTable.setSelectionModel(kontaktSelection, DefaultSelectionEventManager.<Kontakt>createCheckboxManager());

		/*
		 * Erstellen einer Checkbox um Kontakte in der CellTable auswählen zu
		 * können
		 */
		Column<Kontakt, Boolean> checkBox = new Column<Kontakt, Boolean>(new CheckboxCell(true, false)) {

			@Override
			public Boolean getValue(Kontakt object) {

				return kontaktSelection.isSelected(object);

			}

		};

		Column<Kontakt, String> kontakt = new Column<Kontakt, String>(new ClickableTextCell()) {

			@Override
			public String getValue(Kontakt object) {
				return object.getName();
			}

		};

		/*
		 * Stylen der Widgets
		 */
		kontaktTable.setPageSize(100);
		scrollPanel.setHeight("250px");
		scrollPanel.setWidth("250px");
		scrollPanel.setStyleName("scrollPanel");
		scrollPanel.add(kontaktTable);
		this.setStyleName("DialogboxBackground");

		kontakteHinzufuegen.addClickHandler(new kontaktHinzufuegenClickhandler());
		abbrechen.addClickHandler(new AbbrechenClickhandler());

		/*
		 * Hinzufuegen der Columns zu den Kontaktlisten
		 */
		kontaktTable.addColumn(kontakt);
		kontaktTable.addColumn(checkBox);

		/*
		 * Widgets dem Panel hinzufuegen
		 */
		vpanel.add(ueberschrift);
		vpanel.add(txt);
		vpanel.add(scrollPanel);
		hpanel.add(kontakteHinzufuegen);
		hpanel.add(abbrechen);
		vpanel.add(hpanel);
		this.add(vpanel);
	}

	/*
	 * Methode, um ein KontaktKontaktliste-Objekt zu erstellen, welches die
	 * Zugehörigkeit eines Kontaktes zu einer Kontaktliste darstellt. Bei
	 * Methodenaufruf wird ein asynchroner Callback aufgerufen, der es
	 * ermöglicht, ein KontaktKontaktliste-Objekt der Datenbank hinzuzufuegen.
	 */
	private void kontakteHinzufuegen(Kontaktliste kl) {
		Vector<Kontakt> kontakte = new Vector<Kontakt>();
		kontakte.addAll(kontaktSelection.getSelectedSet());
		kontaktVerwaltung.addKontakteToKontaktliste(kontakte, kl.getID(), new KontaktHinzufuegen());
	}

	/**
	 * ClickHandler der das Hinzufügen eines oder mehrerer Kontakte ermöglicht.
	 * Ist kein Kontakt ausgewählt, wird dies mithilfe einer Fehlermeldung dem
	 * User mitgeteilt. Die Methode <code>kontakteHinzufuegen</code> wird
	 * ausgeführt und die DialogBox geschlossen.
	 * 
	 */
	private class kontaktHinzufuegenClickhandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// Wenn kein Kontakt ausgewählt ist, wird ein Window-Alert
			// ausgegeben.
			if (kontaktSelection.getSelectedSet().isEmpty()) {
				Window.alert("Sie müssen mindestens einen Kontakt auswählen");
			} else {
				kontakteHinzufuegen(kl);

			}
		}
	}

	/**
	 * Ermöglicht das Schließen der Dialogbox
	 *
	 */
	private class AbbrechenClickhandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			DialogBoxKontaktZuKontaktliste.this.hide();

		}

	}

	/**
	 * Callback-Klasse um alle Kontakte eines Nutzers mithilfe eines Callbacks
	 * zu erhalten. Die im <code>Vector<Kontakt> result</code> gespeicherten
	 * Kontakt-Objekte werden der CellTable hinzugef�gt.
	 */
	class AlleKontakteVonNutzer implements AsyncCallback<Vector<Kontakt>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<Kontakt> result) {
			kontaktTable.setRowCount(result.size());
			kontaktTable.setRowData(0, result);

		}

	}

	/**
	 * Callback-Klasse um beim betätigen des Buttons
	 * <code>kontakteHinzufuegen</code> die entsprechenden Kontakt-Objekte zu
	 * speichern. Sowohl die <code>KontaktlisteBearbeitenForm</code> als auch
	 * die Baumstruktur werden anschließend neu zum RootPanel hinzugefügt, um
	 * die hinzugefügten Kontakt-Objekte korrekt anzuzeigen. Um den
	 * Bearbeitungsmodus weiterhin aufrechtzuerhalten wird die Methode
	 * <code>setEditable</code> augeführt.
	 */
	class KontaktHinzufuegen implements AsyncCallback<KontaktKontaktliste> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten: " + caught.getMessage());
		}

		@Override
		public void onSuccess(KontaktKontaktliste result) {
			Window.alert(
					"Der Kontakt/die Kontakte wurden erfolgreich zur Kontaktliste " + kl.getBez() + " hinzugefügt");
			DialogBoxKontaktZuKontaktliste.this.hide();
			KontaktlisteForm kf = new KontaktlisteForm(kl);
			NavigationTree updatedNavigation = new NavigationTree();
			RootPanel.get("Details").clear();
			RootPanel.get("Navigator").clear();
			RootPanel.get("Details").add(kf);
			RootPanel.get("Navigator").add(updatedNavigation);
			kf.setEditable();
		}

	}

}
