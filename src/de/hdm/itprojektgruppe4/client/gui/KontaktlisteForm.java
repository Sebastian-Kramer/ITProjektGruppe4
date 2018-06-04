package de.hdm.itprojektgruppe4.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.cellview.client.CellList;
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

import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.*;
import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.client.NavigationTree;

/**
 * Die Klasse dient zur Darstellung und Verwaltung von Kontaktlisten.
 * @author Raphael
 *
 */
public class KontaktlisteForm extends VerticalPanel {
	
	private VerticalPanel vpanel = new VerticalPanel();
	private HorizontalPanel hpanelButtonbar = new HorizontalPanel();
	
	private ScrollPanel scrollPanel = new ScrollPanel();
	
	private KontaktCell kontaktcell = new KontaktCell();
	private CellList<Kontakt> kontaktCellList = new CellList<Kontakt>(kontaktcell);
	private SingleSelectionModel<Kontakt> selectionModel = new SingleSelectionModel<Kontakt>();
	
	 
	private Button kontaktHinzufuegen = new Button("Kontakt hinzufuegen");
	private Button kontaktlisteLoeschen = new Button("Kontaktliste loeschen");

	private KontaktAdministrationAsync kontaktVerwaltung = ClientsideSettings.getKontaktverwaltung();
	KontaktlisteKontaktTreeViewModel kktvm = new KontaktlisteKontaktTreeViewModel();
	
	private Kontaktliste kl = null;
	
	/**
	 * Konstruktor, der beim Auswaehlen einer Kontaktliste im Baum eingesetzt wird.
	 * @param kontaktliste
	 */
	public KontaktlisteForm(Kontaktliste kontaktliste){
		this.kl = kontaktliste;
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		
	
	}
	
	public void onLoad(){
		super.onLoad();
		
		//Instantiieren des DataProviders, der die Daten f�r die Liste h�lt
		KontakteDataProvider dataProvider = new KontakteDataProvider();
		dataProvider.addDataDisplay(kontaktCellList);
	
		kontaktCellList.setSelectionModel(selectionModel);
		scrollPanel.setStyleName("scrollPanel");
		kontaktCellList.setStyleName("cellListKontakte");
		scrollPanel.add(kontaktCellList);
		HTML html1 = new HTML("<h2>" +  kl.getBez()   + "</h2>");
	
		/*
		 * Hinzuf�gen der Buttons zur Buttonbar
		 */
		RootPanel.get("Buttonbar").clear();
		hpanelButtonbar.add(kontaktlisteLoeschen);
		hpanelButtonbar.add(kontaktHinzufuegen);
		RootPanel.get("Buttonbar").add(hpanelButtonbar);
		
		/*
		 * Hinzuf�gen der �berschrift und der CellList zum Vertical Panel
		 */
		vpanel.add(html1);
		vpanel.add(scrollPanel);
		vpanel.add(kontaktCellList);
		RootPanel.get("Details").add(vpanel);
	
		kontaktlisteLoeschen.addClickHandler(new KontaktlisteloeschenClickhandler());
		kontaktHinzufuegen.addClickHandler(new KontaktHinzufuegenClickhandler());

	}
	
	
	

	/**
	 * Nested Class, die es erm�glicht, auf Selektionsereignisse in der CellList zu reagieren.
	 * Bei aktivieren der Selektion wird die <code>KontaktForm</code> aufgerufen, um den ausgew�hlten
	 * Kontakt anzuzeigen.
	 */
	private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler{

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			Kontakt selection = selectionModel.getSelectedObject();
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(new KontaktForm(selection));
			
			
		}
		
	}
	
	/**
	 * Nested Class, fuer den Kontaktliste loeschen Button.
	 */
	
	private class KontaktlisteloeschenClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			kontaktVerwaltung.deleteKontaktliste(kl, new KontaktlisteloeschenCallback());
			kktvm.removeKontaktliste(kl);
			
		}
	}
	/**
	 * Nested Class um den Button zum Hinzufuegen von Kontakten zur Kontaktliste bedienen zu k�nnen
	 */
	private class KontaktHinzufuegenClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			DialogBoxKontaktZuKontaktliste dialogKontakt = new DialogBoxKontaktZuKontaktliste(kl);
			dialogKontakt.center();
			
			
		}
		
	}
		
	
	void setKktvw(KontaktlisteKontaktTreeViewModel kktvm) {
		this.kktvm = kktvm;
	}
	
	void setSelected(Kontaktliste kl){
		this.kl = kl;
		
	}
	
	

	
	
	private class KontaktlisteloeschenCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Kontaktliste wurde nicht gelöscht");
		}

		@Override
		public void onSuccess(Void result) {
			Window.alert("Kontaktliste wurde erfolgreich gelöscht");
			MainForm main = new MainForm();
			NavigationTree updatedTree = new NavigationTree();
			RootPanel.get("Navigator").clear();
			RootPanel.get("Details").clear();
			RootPanel.get("Buttonbar").clear();
			RootPanel.get("Details").add(main);
			RootPanel.get("Navigator").add(updatedTree);
			
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
