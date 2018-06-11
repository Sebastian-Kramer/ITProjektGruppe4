package de.hdm.itprojektgruppe4.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.user.client.ui.FlowPanel;

import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.*;
import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.client.NavigationTree;




public class KontaktlisteBearbeitenForm extends VerticalPanel {
	
	private KontaktAdministrationAsync kontaktVerwaltung = ClientsideSettings.getKontaktverwaltung();
	
	private FlowPanel fpanel = new FlowPanel();
	private HorizontalPanel hpanel = new HorizontalPanel();
	private VerticalPanel vpanel = new VerticalPanel();
	
	private Nutzer nutzer = new Nutzer();
	private Kontaktliste kl = null;
	
	private Button kontaktHinzufuegen = new Button("Kontakt hinzufuegen");
	private Button kontaktlisteLoeschen = new Button("Kontaktliste loeschen");
	private Button kontaktEntfernen = new Button("Kontakt entfernen");
	private Button zurueck	= new Button("Bearbeitung beenden");
	
	private Label lbl_kontaktliste = new Label("Kontaktlistenname:");
	private TextBox txt_kontaktliste = new TextBox();
	
	private KontaktCell kontaktcell = new KontaktCell();
	private CellList<Kontakt> kontaktCellList = new CellList<Kontakt>(kontaktcell);
	private SingleSelectionModel<Kontakt> selectionModel = new SingleSelectionModel<Kontakt>();
	
	
	public KontaktlisteBearbeitenForm (Kontaktliste kl){
		this.kl = kl;
	}
	
	public void onLoad(){
		super.onLoad();
		
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));
		
		//Instantiieren des DataProviders, der die Daten fuer die Liste haelt
				KontakteDataProvider dataProvider = new KontakteDataProvider();
				dataProvider.addDataDisplay(kontaktCellList);
				kontaktCellList.setSelectionModel(selectionModel);
		
		/*
		 * Hinzuf�gen der Buttons zur Buttonbar
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
	
	}
	
	
	/**
	 * Clickhandler, der das L�schen von Kontaktlisten bzw. die Aufl�sung einer Teilhaberschaft bei Klick erm�glicht
	 * Ist der L�schende der Inhaber der Liste, wird die Kontaktliste komplett aus der Datenbank entfernt.
	 * Ist der L�schende Teilhaber der Liste, wird lediglich die Teilhaberschaft an der Liste aufgel�st.

	 */
	private class KontaktlisteloeschenClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			 //Wenn die ausgew�hlte Kontaktliste vom Nutzer erstellt wurde, wird diese gel�scht
		if(kl.getNutzerID() == nutzer.getID()){
			kontaktVerwaltung.deleteKontaktliste(kl, new KontaktlisteloeschenCallback());
			}
			//Wenn nur eine Teilhaberschaft an der Kontaktliste besteht, wird nur diese aufgel�st	
			else{
				kontaktVerwaltung.deleteTeilhaberschaftByKontaktlisteID(kl.getID(), new KontaktlisteloeschenCallback());
			}
		
			
		}
	}
	
	/**
	 * Nested Class um den Button zum Hinzufuegen von Kontakten zur Kontaktliste bedienen zu k�nnen
	 * Bei Click auf den Button wird eine DialogBox ge�ffnet, die erm�glich, Kontakt zu �ffnen.
	 */
	private class KontaktHinzufuegenClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			DialogBoxKontaktZuKontaktliste dialogKontakt = new DialogBoxKontaktZuKontaktliste(kl);
			dialogKontakt.center();
			
			
		}
		
	}
	
	private class KontaktEntfernenClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			if(selectionModel.getSelectedObject() == null){
				Window.alert("Sie muessen einen Kontakt auswaehlen");
			}else{
				kontaktVerwaltung.deleteKontaktKontaktlisteByKontaktID(selectionModel.getSelectedObject().getID(), new KontaktEntfernenCallback());
			}
			
		}
		
	}
	
	private class BearbeitenBeendenClickhandler implements ClickHandler{

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
	 * Callback-Klasse fuer die Loeschung der Kontaktliste
	 * @author Raphael
	 *
	 */
	private class KontaktlisteloeschenCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Kontaktliste wurde nicht geloescht");
		}

		@Override
		public void onSuccess(Void result) {
			
			if (kl.getBez().equals("Meine Kontakte")) {
				Window.alert("Sie können die Basis Liste Meine Kontakte nicht löschen");
				
			}else{
			
			
			Window.alert("Kontaktliste wurde erfolgreich geloescht");
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
	
	private class KontaktEntfernenCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Void result) {
		
				
		
		}
		
	}
	
	/**
	 * Hier wird der DataProvider mit den entsprechenden Daten fuer die CellList erstellt.
	 */
	private class KontakteDataProvider extends AsyncDataProvider<Kontakt>{

		@Override
		protected void onRangeChanged(HasData<Kontakt> display) {
			final Range range = display.getVisibleRange();
			
			
			kontaktVerwaltung.getAllKontakteFromKontaktliste(kl.getID(), new AsyncCallback<Vector<Kontakt>>(){
				int start = range.getStart();
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(Vector<Kontakt> result) {
				
					List<Kontakt> list = new ArrayList<Kontakt>();
					for(Kontakt k : result){
						list.add(k);
					}
					updateRowData(start, list);
					
				}
				
			});
			
		}
		
	}
}
