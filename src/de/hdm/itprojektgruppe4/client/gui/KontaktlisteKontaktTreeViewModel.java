package de.hdm.itprojektgruppe4.client.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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

public class KontaktlisteKontaktTreeViewModel implements TreeViewModel {

	private KontaktForm kontaktForm;
	private MainForm mainForm;

	private Kontakt selectedKontakt = null;
	private Kontaktliste selectedKontaktliste = null;

	private KontaktAdministrationAsync kontaktVerwaltung = null;

	private ListDataProvider<Kontaktliste> kontaktlisteDataProvider = null;

	private Map<Kontaktliste, ListDataProvider<Kontakt>> kontaktDataProvider = null;

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

	private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler {

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			BusinessObject selection = selectionModel.getSelectedObject();
			if (selection instanceof Kontaktliste) {
				setSelectedKontaktliste((Kontaktliste) selection);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(mainForm);
			} else if (selection instanceof Kontakt) {
				setSelectedKontakt((Kontakt) selection);
			}

		}

	}

	public KontaktlisteKontaktTreeViewModel() {
		kontaktVerwaltung = ClientsideSettings.getKontaktVerwaltung();
		boKeyProvider = new BusinessObjectKeyProvider();
		selectionModel = new SingleSelectionModel<BusinessObject>(boKeyProvider);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		kontaktDataProvider = new HashMap<Kontaktliste, ListDataProvider<Kontakt>>();
	}

	void setMainForm(MainForm mf) {
		mainForm = mf;

	}

	void setKontaktForm(KontaktForm kf) {
		kontaktForm = kf;
	}

	Kontaktliste getSelectedKontaktliste() {
		return selectedKontaktliste;
	}

	void setSelectedKontaktliste(Kontaktliste kl) {
		selectedKontaktliste = kl;
		mainForm.setSelected(kl);
		selectedKontakt = null;
		kontaktForm.setSelected(null);

	}

	Kontakt getSelectedKontakt() {
		return selectedKontakt;
	}

	void setSelectedKontakt(Kontakt k) {
		selectedKontakt = k;
		kontaktForm.setSelected(k);

		if (k != null) {
			kontaktVerwaltung.findKontaktlisteByID(k.getKontaktlisteID(), new AsyncCallback<Kontaktliste>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onSuccess(Kontaktliste result) {
					selectedKontaktliste = result;
					mainForm.setSelected(result);

				}

			});
		}
	}
	
	void addKontaktliste(Kontaktliste kontaktliste){
		kontaktlisteDataProvider.getList().add(kontaktliste);
		selectionModel.setSelected(kontaktliste, true);
	}
	
	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {
		// TODO Auto-generated method stub
		if (value == null) {
			kontaktlisteDataProvider = new ListDataProvider<Kontaktliste>();
			kontaktVerwaltung.getAllKontaktlistenFromUser(1, new AsyncCallback<Vector<Kontaktliste>>(){

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(Vector<Kontaktliste> result) {
					for(Kontaktliste kl: result){
						kontaktlisteDataProvider.getList().add(kl);
					}
					
				}
				
			});
			/**
			kontaktVerwaltung.findKontaktlisteByNutzerID(1, new AsyncCallback<Vector<Kontaktliste>>() {

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
			 */
			return new DefaultNodeInfo<Kontaktliste>(kontaktlisteDataProvider, new KontaktlisteCell(), selectionModel,
					null);
		}

		if (value instanceof Kontaktliste) {

			final ListDataProvider<Kontakt> kontaktProvider = new ListDataProvider<Kontakt>();
			kontaktDataProvider.put((Kontaktliste) value, kontaktProvider);
			
			
			kontaktVerwaltung.findAllKontakte(new AsyncCallback<Vector<Kontakt>>(){

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(Vector<Kontakt> result) {
					for(Kontakt k:result){
						kontaktProvider.getList().add(k);
					}
					
				}
				
			});
			/**
			kontaktVerwaltung.getAllKontakteFromKontaktliste((Kontaktliste) value,
					new AsyncCallback<Vector<Kontakt>>() {

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
*/
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
