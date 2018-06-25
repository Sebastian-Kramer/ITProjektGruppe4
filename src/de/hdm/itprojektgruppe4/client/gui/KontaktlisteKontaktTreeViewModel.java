package de.hdm.itprojektgruppe4.client.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.*;

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
	 * Diese Nested Class soll den BusinessObjects im Baum eindeutige Schl�ssel
	 * zuweisen. Hierdurch k�nnnen Kontaktlisten-Objekte von Kontakt-Objekten
	 * unterschieden werden. der Schl�ssel f�r Kontaktliste-Objekte ist ein
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
	 * ausgew�hlte Objekt in der Baumstruktur ein Objekt vom Typ Kontaktliste,
	 * wird die <code>KontaktlisteForm</code> ge�ffnet, die die Verwaltung und
	 * Bearbeitung der Kontaktliste erm�glicht. Ist das selektierte Objekt vom
	 * Typ Kontakt, wird die <code>KontaktForm</code> ge�ffnet, die die
	 * Verwaltung und Bearbeitung eines Kontakte erm�glicht.
	 * 
	 * @author Raphael
	 *
	 */
	private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler {

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			BusinessObject selection = selectionModel.getSelectedObject();
			Kontakt k = new Kontakt();
			if (selection instanceof Kontaktliste) {
				setSelectedKontaktliste((Kontaktliste) selection);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(new KontaktlisteForm(getSelectedKontaktliste()));

			} else if (selection instanceof Kontakt) {
				k = ((Kontakt) selection);

				if (((Kontakt) selection).getNutzerID() == nutzer.getID()) {
					KontaktForm kf = new KontaktForm(k);
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(kf);
				} else {
					KontaktForm kf = new KontaktForm(k, "Teilhaberschaft");
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(kf);
				}

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
	 * Setter f�r das Nutzer-Objekt Informationen werden aus den Cookie gelesen
	 * 
	 * @param nutzer
	 */
	void setNutzer(Nutzer nutzer) {
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));
	}

	/*
	 * Getter f�r das Nutzer-Objekt
	 */
	Nutzer getNutzer() {
		return nutzer;
	}

	Kontaktliste getSelectedKontaktliste() {
		return selectedKontaktliste;
	}

	void setSelectedKontaktliste(Kontaktliste kl) {
		selectedKontaktliste = kl;
	}

	Kontakt getSelectedKontakt() {
		return selectedKontakt;
	}

	void setSelectedKontakt(Kontakt k) {
		selectedKontakt = k;
	}

	void addKontaktliste(Kontaktliste kontaktliste) {
		kontaktlisteDataProvider.getList().add(kontaktliste);
		selectionModel.setSelected(kontaktliste, true);
	}

	void updateKontaktliste(Kontaktliste kontaktliste) {
		List<Kontaktliste> kontaktlisteList = kontaktlisteDataProvider.getList();
		int i = 0;
		for (Kontaktliste kl : kontaktlisteList) {
			if (kl.getID() == kl.getID()) {
				kontaktlisteList.set(i, kontaktliste);
				break;
			} else {
				i++;
			}
		}
		kontaktlisteDataProvider.refresh();
	}

	void removeKontaktliste(Kontaktliste kontaktliste) {
		kontaktlisteDataProvider.getList().remove(kontaktliste);
		kontaktDataProvider.remove(kontaktliste);
	}

	void addKontaktToKontaktliste(Kontakt kontakt, Kontaktliste kontaktliste) {
		if (!kontaktDataProvider.containsKey(kontaktliste)) {
			return;
		}
		ListDataProvider<Kontakt> kontaktProvider = kontaktDataProvider.get(kontaktliste);
		if (!kontaktProvider.getList().contains(kontakt)) {
			kontaktProvider.getList().add(kontakt);
		}
		selectionModel.setSelected(kontakt, true);
	}

	void removeKontaktFromKontaktliste(Kontakt kontakt, Kontaktliste kontaktliste) {
		if (!kontaktDataProvider.containsKey(kontaktliste)) {
			return;
		}
		kontaktDataProvider.get(kontaktliste).getList().remove(kontakt);
		selectionModel.setSelected(kontaktliste, true);
	}

	/**
	 * void updateKontakt(Kontakt k){ kontaktVerwaltung.findKontaktlisteByID(id,
	 * callback); }
	 */
	/*
	 * 
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
