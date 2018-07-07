package de.hdm.itprojektgruppe4.client.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.BusinessObject;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.bo.Teilhaberschaft;

/**
 * Die Klasse <code>KontaktlisteKontaktTreeViewModel</code> dient zur Verwaltung
 * der Baumstruktur. Bei der Implementierung wurde sich an Professor Rathkes
 * Implementierung einer Baumstruktur aus dem Bankprojekt orientiert.
 * 
 * @author Raphael
 *
 */
public class KontaktlisteKontaktTreeViewModel implements TreeViewModel {

	private Kontakt selectedKontakt = null;
	private Kontaktliste selectedKontaktliste = null;
	private Nutzer nutzer = new Nutzer();
	private static KontaktAdministrationAsync kontaktVerwaltung = ClientsideSettings.getKontaktVerwaltung();

	private ListDataProvider<Kontaktliste> kontaktlisteDataProvider = null;

	private Map<Kontaktliste, ListDataProvider<Kontakt>> kontaktDataProvider = null;

	/**
	 * Diese Nested Class soll den BusinessObjects im Baum eindeutige Schlüssel
	 * zuweisen. Hierdurch könnnen Kontaktlisten-Objekte von Kontakt-Objekten
	 * unterschieden werden. der Schlüssel für Kontaktliste-Objekte ist ein
	 * positiver, der von Kontakt-Objekten ein negativer. (siehe Prof. Rathke,
	 * BankProjekt, 2018)
	 */
	private class BusinessObjectKeyProvider implements ProvidesKey<BusinessObject> {

		@Override
		public Object getKey(BusinessObject bo) {
			if (bo == null) {
				return null;
			}
			if (bo instanceof Kontaktliste) {
				return new Integer(bo.getID());
			} else {
				return new Integer(-bo.getID());
			}
		}

	};

	private BusinessObjectKeyProvider boKeyProvider = null;
	private SingleSelectionModel<BusinessObject> selectionModel = null;

	/**
	 * Nested Class fuer das Setzen von Selektionsereignissen. Ist das
	 * ausgewählte Objekt in der Baumstruktur ein Objekt vom Typ Kontaktliste,
	 * wird die <code>KontaktlisteForm</code> geöffnet, die die Verwaltung und
	 * Bearbeitung der Kontaktliste ermöglicht. Ist das selektierte Objekt vom
	 * Typ Kontakt, wird die <code>KontaktForm</code> geöffnet, die die
	 * Verwaltung und Bearbeitung eines Kontakte ermöglicht. Bei der Auswahl
	 * eines Kontaktes muss differenziert werden, ob der angemeldete Nutzer der
	 * Ersteller des Kontaktes ist (Vollzugriff), ob er Teilhaber ist
	 * (Vollzugriff auf geteilte Elemente) oder ob ihm der Kontakt nur im Rahmen
	 * einer Kontaktlistenteilung geteilt wurde (Read only).
	 * 
	 * @author Raphael
	 *
	 */
	private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler {

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {

			BusinessObject selection = selectionModel.getSelectedObject();
			if (selection instanceof Kontaktliste) {
				setSelectedKontaktliste((Kontaktliste) selection);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(new KontaktlisteForm(getSelectedKontaktliste()));

			} else if (selection instanceof Kontakt) {

				if (((Kontakt) selection).getNutzerID() == nutzer.getID()) {
					KontaktForm kf = new KontaktForm(((Kontakt) selection));
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(kf);

				} else {
					selectedKontakt = ((Kontakt) selection);
					// Prüfe, ob am gewählten Kontakt eine Teilhaberschaft
					// besteht. Weitere Vorgehensweise wird in der
					// Callback-Klasse
					kontaktVerwaltung.findTeilhaberschaftByKontaktIDAndTeilhaberID(((Kontakt) selection).getID(),
							nutzer.getID(), new TeilhaberschaftKontaktCallback());
				}

			}

		}

	}

	/**
	 * Callback-Klasse, die die Teilhaber an einem in der Baumstruktur
	 * ausgewählten Kontakt zurückgibt. Wird zur Differenzierung benötigt,
	 * welchen Zugriff der User auf einen Kontakt bekommt, je nachdem ob er
	 * Teilhaber an einem Kontakt ist oder den Kontakt lediglich im Rahmen einer
	 * Kontaktlistenteilung erhalten hat.
	 *
	 */
	class TeilhaberschaftKontaktCallback implements AsyncCallback<Vector<Teilhaberschaft>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<Teilhaberschaft> result) {
			// Ist der User weder Eigentümer noch Teilhaber am Kontakt, so wird
			// die KontakAnsicht auf ReadOnly gestellt
			if (selectedKontakt.getNutzerID() != nutzer.getID() && result.isEmpty()) {
				KontaktForm kf = new KontaktForm(selectedKontakt, 1);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(kf);
			} else {
				KontaktForm kf = new KontaktForm(selectedKontakt, "Teilhaberschaft");
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(kf);
			}

		}

	}

	/*
	 * Konstruktor fuer die Initialisierung der wichtigsten Variablen des Baums
	 */
	public KontaktlisteKontaktTreeViewModel() {
		kontaktVerwaltung = ClientsideSettings.getKontaktVerwaltung();
		boKeyProvider = new BusinessObjectKeyProvider();
		selectionModel = new SingleSelectionModel<BusinessObject>(boKeyProvider);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		kontaktDataProvider = new HashMap<Kontaktliste, ListDataProvider<Kontakt>>();

	}

	/*
	 * Setter fur das Nutzer-Objekt Informationen werden aus den Cookie gelesen
	 * 
	 * @param nutzer
	 */
	void setNutzer(Nutzer nutzer) {
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));
	}

	/*
	 * Getter für das Nutzer-Objekt
	 */
	Nutzer getNutzer() {
		return nutzer;
	}

	/*
	 * Getter für die ausgewählte Kontaktliste
	 */
	Kontaktliste getSelectedKontaktliste() {
		return selectedKontaktliste;
	}

	/*
	 * Setter der ausgewählten Kontaktliste
	 */
	void setSelectedKontaktliste(Kontaktliste kl) {
		selectedKontaktliste = kl;
	}

	/*
	 * Getter für den ausgewählten Kontakt
	 */
	Kontakt getSelectedKontakt() {
		return selectedKontakt;
	}

	/*
	 * Setter für den ausgewählten Kontakt
	 */
	void setSelectedKontakt(Kontakt k) {
		selectedKontakt = k;
	}

	/*
	 * In der Methode getNodeInfo werden dem Baum sein Inhalt und dessen
	 * Kindknoten mitgeteilt
	 */
	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {
		this.setNutzer(nutzer);
		if (value == null) {

			kontaktlisteDataProvider = new ListDataProvider<Kontaktliste>();
			kontaktVerwaltung.getAllKontaktlistenFromUser(this.getNutzer().getID(),
					new AsyncCallback<Vector<Kontaktliste>>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Es ist ein Fehler aufgetreten: " + caught.getMessage());
						}

						@Override
						public void onSuccess(Vector<Kontaktliste> result) {
							for (Kontaktliste kl : result) {
								kontaktlisteDataProvider.getList().add(kl);
							}

						}

					});

			return new DefaultNodeInfo<Kontaktliste>(kontaktlisteDataProvider, new KontaktlisteCell(), selectionModel,
					null);
		}

		else if (value instanceof Kontaktliste) {

			final ListDataProvider<Kontakt> kontaktProvider = new ListDataProvider<Kontakt>();
			kontaktDataProvider.put((Kontaktliste) value, kontaktProvider);
			int kontaktlisteID = ((Kontaktliste) value).getID();
			kontaktVerwaltung.getAllKontakteFromKontaktliste(kontaktlisteID, new AsyncCallback<Vector<Kontakt>>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Es ist ein Fehler aufgetreten: " + caught.getMessage());
				}

				@Override
				public void onSuccess(Vector<Kontakt> result) {
					for (Kontakt k : result) {
						kontaktProvider.getList().add(k);
					}

				}

			});

			return new DefaultNodeInfo<Kontakt>(kontaktProvider, new KontaktCell(), selectionModel, null);
		}

		return null;

	}

	@Override
	public boolean isLeaf(Object value) {

		return (value instanceof Kontakt);
	}

}
