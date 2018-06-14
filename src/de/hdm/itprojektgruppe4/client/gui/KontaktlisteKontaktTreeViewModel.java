package de.hdm.itprojektgruppe4.client.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;
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

	private KontaktForm kontaktForm;
	private KontaktlisteForm kontaktlisteForm;

	private Kontakt selectedKontakt = null;
	private Kontaktliste selectedKontaktliste = null;
	private Nutzer nutzer = new Nutzer();
	private KontaktAdministrationAsync kontaktVerwaltung = null;

	private ListDataProvider<Kontaktliste> kontaktlisteDataProvider = null;

	private Map<Kontaktliste, ListDataProvider<Kontakt>> kontaktDataProvider = null;
	private String teilhaberschaft = "";
	
	/**
	 * Diese Nested Class soll den BusinessObjects im Baum eindeutige Schlï¿½ssel zuweisen.
	 * Hierdurch kï¿½nnnen Kontaktlisten-Objekte von Kontakt-Objekten unterschieden werden.
	 * der Schlï¿½ssel fï¿½r Kontaktliste-Objekte ist ein positiver, der von Kontakt-Objekten ein negativer.
	 * (siehe Prof. Rathke, BankProjekt, 2018)
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
	 * Nested Class fï¿½r das Setzen von Selektionsereignissen.
	 * Ist das ausgewï¿½hlte Objekt in der Baumstruktur ein Objekt vom Typ Kontaktliste,
	 * wird die <code>KontaktlisteForm</code> geï¿½ffnet, die die Verwaltung und Bearbeitung der Kontaktliste ermï¿½glicht.
	 * Ist das selektierte Objekt vom Typ Kontakt, wird die <code>KontaktForm</code> geï¿½ffnet, die die Verwaltung und Bearbeitung
	 * eines Kontakte ermï¿½glicht.
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
				if(((Kontaktliste) selection).getBez().equals("Meine geteilten Kontakte")){
					teilhaberschaft = "teilhaberschaft";
				}
			}else if (selection instanceof Kontakt){
				setSelectedKontakt((Kontakt) selection);
				RootPanel.get("Details").clear();
				if(teilhaberschaft.equals("teilhaberschaft")){
					RootPanel.get("Details").add(new KontaktForm((Kontakt) selection, teilhaberschaft));
				} else {
					RootPanel.get("Details").add(new KontaktForm((Kontakt) selection));
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
	 * Setter für das Nutzer-Objekt
	 * Informationen werden aus den Cookie gelesen
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
	
	void updateKontaktliste(Kontaktliste kontaktliste){
		List<Kontaktliste> kontaktlisteList = kontaktlisteDataProvider.getList();
		int i = 0;
		for(Kontaktliste kl : kontaktlisteList){
			if(kl.getID() == kl.getID()){
				kontaktlisteList.set(i, kontaktliste);
				break;
			}else {
				i++;
			}
		}
		kontaktlisteDataProvider.refresh();
	}
	
	void removeKontaktliste(Kontaktliste kontaktliste){
		kontaktlisteDataProvider.getList().remove(kontaktliste);
		kontaktDataProvider.remove(kontaktliste);
	}
	
	void addKontaktToKontaktliste(Kontakt kontakt, Kontaktliste kontaktliste){
		if(!kontaktDataProvider.containsKey(kontaktliste)){
			return;
		}
		ListDataProvider<Kontakt> kontaktProvider = kontaktDataProvider.get(kontaktliste);
		if(!kontaktProvider.getList().contains(kontakt)){
			kontaktProvider.getList().add(kontakt);
		}
		selectionModel.setSelected(kontakt, true);
	}
	
	void removeKontaktFromKontaktliste(Kontakt kontakt, Kontaktliste kontaktliste){
		if(!kontaktDataProvider.containsKey(kontaktliste)){
			return;
		}
		kontaktDataProvider.get(kontaktliste).getList().remove(kontakt);
		selectionModel.setSelected(kontaktliste, true);
	}
	/**
	void updateKontakt(Kontakt k){
		kontaktVerwaltung.findKontaktlisteByID(id, callback);
	}
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
							// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return (value instanceof Kontakt);
	}

}
