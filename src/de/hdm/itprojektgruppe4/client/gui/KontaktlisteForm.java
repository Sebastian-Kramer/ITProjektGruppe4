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

/**
 * Die Klasse dient zur Darstellung und Verwaltung von Kontaktlisten.
 * @author Raphael
 *
 */
public class KontaktlisteForm extends VerticalPanel {
	
	private VerticalPanel vpanel = new VerticalPanel();
	private HorizontalPanel hpanelButtonbar = new HorizontalPanel();
	
	private ScrollPanel scrollPanel = new ScrollPanel();
	private FlowPanel fpanel = new FlowPanel();
	
	private KontaktCell kontaktcell = new KontaktCell();
	private CellList<Kontakt> kontaktCellList = new CellList<Kontakt>(kontaktcell);
	private SingleSelectionModel<Kontakt> selectionModel = new SingleSelectionModel<Kontakt>();
	
	 
	private Button kontaktHinzufuegen = new Button("Kontakt hinzufuegen");
	private Button kontaktlisteLoeschen = new Button("Kontaktliste loeschen");
	private Button kontaktAnzeigen = new Button("Kontakt anzeigen");
	private Button kontaktlisteTeilen = new Button("Kontaktliste teilen");
	private Button teilhaberschaften = new Button("Teilhaberschaften verwalten");
	private Button kontaktEntfernen = new Button("Kontakt entfernen");

	private KontaktAdministrationAsync kontaktVerwaltung = ClientsideSettings.getKontaktverwaltung();
	KontaktlisteKontaktTreeViewModel kktvm = new KontaktlisteKontaktTreeViewModel();
	
	private Kontaktliste kl = null;
	private Nutzer nutzer = new Nutzer();
	
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
		
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));
		
		//Instantiieren des DataProviders, der die Daten fuer die Liste haelt
		KontakteDataProvider dataProvider = new KontakteDataProvider();
		dataProvider.addDataDisplay(kontaktCellList);
	
		kontaktCellList.setSelectionModel(selectionModel);
		scrollPanel.setStyleName("scrollPanel");
		kontaktCellList.setStyleName("cellListKontakte");
		scrollPanel.add(kontaktCellList);
		HTML html1 = new HTML("<h2>" +  kl.getBez()   + "</h2>");
	
		/*
		 * Hinzufï¿½gen der Buttons zur Buttonbar
		 */
		RootPanel.get("Buttonbar").clear();
		fpanel.add(kontaktAnzeigen);
		fpanel.add(kontaktHinzufuegen);
		fpanel.add(kontaktEntfernen);
		fpanel.add(kontaktlisteTeilen);
		fpanel.add(teilhaberschaften);
		fpanel.add(kontaktlisteLoeschen);
		RootPanel.get("Buttonbar").add(fpanel);
		
		/*
		 * Hinzufuegen der ï¿½berschrift und der CellList zum Vertical Panel
		 */
		vpanel.add(html1);
		vpanel.add(scrollPanel);
		vpanel.add(kontaktCellList);
		RootPanel.get("Details").add(vpanel);
		
		/*
		 * Hinzufuegen der Clickhandler zu den Buttons
		 */
		kontaktlisteLoeschen.addClickHandler(new KontaktlisteloeschenClickhandler());
		kontaktHinzufuegen.addClickHandler(new KontaktHinzufuegenClickhandler());
		kontaktAnzeigen.addClickHandler(new KontaktAnzeigenClickhandler());
		kontaktlisteTeilen.addClickHandler(new KontaktlisteTeilenClickhandler());
		teilhaberschaften.addClickHandler(new TeilhaberschaftenVerwaltenClickhandler());
		kontaktEntfernen.addClickHandler(new KontaktEntfernenClickhandler());

	}
	
	/**
	 * Nested Class, die es ermoeglicht, auf Selektionsereignisse in der CellList zu reagieren.
	 * 
	 */
	private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler{

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			Kontakt selection = selectionModel.getSelectedObject();	
			
		}
		
	}
	
	/**
	 * Clickhandler, der das Löschen von Kontaktlisten bzw. die Auflösung einer Teilhaberschaft bei Klick ermöglicht
	 * Ist der Löschende der Inhaber der Liste, wird die Kontaktliste komplett aus der Datenbank entfernt.
	 * Ist der Löschende Teilhaber der Liste, wird lediglich die Teilhaberschaft an der Liste aufgelöst.

	 */
	private class KontaktlisteloeschenClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			 //Wenn die ausgewï¿½hlte Kontaktliste vom Nutzer erstellt wurde, wird diese gelï¿½scht
		if(kl.getNutzerID() == nutzer.getID()){
			kontaktVerwaltung.deleteKontaktliste(kl, new KontaktlisteloeschenCallback());
			}
			//Wenn nur eine Teilhaberschaft an der Kontaktliste besteht, wird nur diese aufgelï¿½st	
			else{
				kontaktVerwaltung.deleteTeilhaberschaftByKontaktlisteID(kl.getID(), new KontaktlisteloeschenCallback());
			}
		
			
		}
	}
	/**
	 * Nested Class um den Button zum Hinzufuegen von Kontakten zur Kontaktliste bedienen zu können
	 * Bei Click auf den Button wird eine DialogBox geöffnet, die ermöglich, Kontakt zu öffnen.
	 */
	private class KontaktHinzufuegenClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			DialogBoxKontaktZuKontaktliste dialogKontakt = new DialogBoxKontaktZuKontaktliste(kl);
			dialogKontakt.center();
			
			
		}
		
	}
	
	private class KontaktAnzeigenClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			if(selectionModel.getSelectedObject() == null){
				Window.alert("Sie mï¿½ssen einen Kontakt auswï¿½hlen");
			}else{
			KontaktForm kf = new KontaktForm(selectionModel.getSelectedObject());
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(kf);
			kktvm.setSelectedKontakt(selectionModel.getSelectedObject());
			}
		}
		
	}
	
	private class KontaktEntfernenClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			if(selectionModel.getSelectedObject() == null){
				Window.alert("Sie mï¿½ssen einen Kontakt auswï¿½hlen");
			}else{
				kontaktVerwaltung.deleteKontaktKontaktlisteByKontaktID(selectionModel.getSelectedObject().getID(), new KontaktEntfernenCallback());
			}
			
		}
		
	}
	
	private class KontaktlisteTeilenClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			DialogBoxKontaktlisteTeilen dbteilen = new DialogBoxKontaktlisteTeilen(kl);
			dbteilen.center();
			
		}
		
	}
	
	private class TeilhaberschaftenVerwaltenClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			DialogBoxTeilhaberschaftVerwalten dbteilhaber = new DialogBoxTeilhaberschaftVerwalten(kl);
			dbteilhaber.center();
		}
		
	}
		
	
	void setKktvw(KontaktlisteKontaktTreeViewModel kktvm) {
		this.kktvm = kktvm;
	}
	
	void setSelected(Kontaktliste kl){
		this.kl = kl;
		
	}
	
	

	/**
	 * Callback-Klasse fï¿½r die Lï¿½schung der Kontaktliste
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
	
	private class KontaktEntfernenCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Void result) {
			// Bei erfolgreicher Lï¿½schung mï¿½ssen sowohl die KontaktlisteForm als auch der NavigationTree neu geladen werden
			// und dem RootPanel hinzugefï¿½gt werden
			KontaktlisteForm kf = new KontaktlisteForm(kl);
			NavigationTree updatedTree = new NavigationTree();
			RootPanel.get("Navigator").clear();
			RootPanel.get("Details").clear();
			RootPanel.get("Buttonbar").clear();
			RootPanel.get("Details").add(kf);
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
