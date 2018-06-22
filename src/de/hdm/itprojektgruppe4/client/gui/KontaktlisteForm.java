package de.hdm.itprojektgruppe4.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
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
	private HorizontalPanel hpanel = new HorizontalPanel();
	
	private ScrollPanel scrollPanel = new ScrollPanel();
	private FlowPanel fpanel = new FlowPanel();
	
	private KontaktCell kontaktcell = new KontaktCell();
	private CellList<Kontakt> kontaktCellList = new CellList<Kontakt>(kontaktcell);
	private SingleSelectionModel<Kontakt> selectionModel = new SingleSelectionModel<Kontakt>();
	
	private Button kontaktAnzeigen = new Button("Kontakt anzeigen");
	private Button kontaktlisteTeilen = new Button("Kontaktliste teilen");
	private Button teilhaberschaften = new Button("Teilhaberschaften verwalten");
	private Button kontaktlisteBearbeiten = new Button("Kontaktliste bearbeiten");
	private Button zurStartseite = new Button("Zurueck zur Startseite");
	private Image listNotShared = new Image();
	private Image listShared = new Image();

	private KontaktAdministrationAsync kontaktVerwaltung = ClientsideSettings.getKontaktverwaltung();
	
	private Kontaktliste kl = null;
	private Nutzer nutzer = new Nutzer();
	
	/**
	 * Konstruktor, der beim Auswaehlen einer Kontaktliste im Baum eingesetzt wird.
	 * @param kontaktliste
	 */
	public KontaktlisteForm(Kontaktliste kontaktliste){
		this.kl = kontaktliste;	
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));
		
		
	}
	
	public void onLoad(){
		super.onLoad();
			
		//Instantiieren des DataProviders, der die Daten fuer die Liste haelt
		KontakteDataProvider dataProvider = new KontakteDataProvider();
		dataProvider.addDataDisplay(kontaktCellList);
	
		kontaktCellList.setSelectionModel(selectionModel);
		scrollPanel.setStyleName("scrollPanel");
		kontaktCellList.setStyleName("cellListKontakte");
		scrollPanel.add(kontaktCellList);
		HTML html1 = new HTML("<h2> Liste: " +  kl.getBez()   + "</h2>");
	
		/*
		 * Hinzufuegen der Buttons zur Buttonbar
		 */
		RootPanel.get("Buttonbar").clear();
		
		//Der Button <code>kontaktlisteBearbeiten</code> wird nur zum Panel hinzugefuegt, wenn der angemeldete Nutzer Eigentuemer der Kontaktliste ist
		if(kl.getNutzerID() == nutzer.getID()){
			fpanel.add(kontaktlisteBearbeiten);
		}
		
		fpanel.add(kontaktAnzeigen);
		fpanel.add(kontaktlisteTeilen);
		fpanel.add(teilhaberschaften);
		fpanel.add(zurStartseite);
		RootPanel.get("Buttonbar").add(fpanel);
		
		/*
		 * Hinzufuegen der Ueberschrift und der CellList zum Vertical Panel
		 */
		hpanel.add(html1);
		vpanel.add(hpanel);
		vpanel.add(scrollPanel);
		RootPanel.get("Details").add(vpanel);
		
		/*
		 * Hinzufuegen der Clickhandler zu den Buttons
		 */
		kontaktAnzeigen.addClickHandler(new KontaktAnzeigenClickhandler());
		kontaktlisteTeilen.addClickHandler(new KontaktlisteTeilenClickhandler());
		teilhaberschaften.addClickHandler(new TeilhaberschaftenVerwaltenClickhandler());
		kontaktlisteBearbeiten.addClickHandler(new KontaktlisteBearbeitenClickhandler());
		zurStartseite.addClickHandler(new ZurueckZurStartseiteClickhandler());
		
		/*
		 * Bedienungen überprüft den Status der Liste und macht den Status "geteilt" sichtbar.
		 */
		
		if (kl.getStatus() == 1) {
			hpanel.add(listShared);
			
		}
		
	}
	
	public void checkStatus(){
		kontaktVerwaltung.updateKontaktlisteStatus(kl.getID(), new UpdateKontaktlisteStatus());
	}
	
	/*
	 * Clickhandler ermöglicht das Anzeigen eines ausgewaehlten Kontaktes.
	 */
	private class KontaktAnzeigenClickhandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (selectionModel.getSelectedObject() == null) {
				Window.alert("Sie muessen einen Kontakt ausw�hlen");
			} else if (kl.getID() == nutzer.getID()) {
				KontaktForm kf = new KontaktForm(selectionModel.getSelectedObject());
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(kf);
			} else {
				KontaktForm kf = new KontaktForm(selectionModel.getSelectedObject(), "Teilhaberschaft");
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(kf);
			}
		}
	}
	
	/**
	 * Clickhandler, der das Teilen einer Kontaktliste ermöglicht.
	 * Wird der Button "Kontaktliste teilen" angewählt, öffnet sich die DialogBox <code>DialogBoxKontaktlisteTeilen</code>
	 */
	private class KontaktlisteTeilenClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			DialogBoxKontaktlisteTeilen dbteilen = new DialogBoxKontaktlisteTeilen(kl);
			dbteilen.center();
			
		}
		
	}
	
	private class UpdateKontaktlisteStatus implements AsyncCallback<Kontaktliste>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Kontaktliste result) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/*
	 * Clickhandler, der das Anwählen des Buttons "Teilhaberschaft verwalten" ermöglicht.
	 * Bei Aktivierung des Clickhandlers wird eine DialogBox mit den Teilhaberschaften an einer Kontaktliste geöffnet
	 */
	private class TeilhaberschaftenVerwaltenClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			DialogBoxTeilhaberschaftVerwalten dbteilhaber = new DialogBoxTeilhaberschaftVerwalten(kl);
			dbteilhaber.center();
		}
		
	}
	
	private class KontaktlisteBearbeitenClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			KontaktlisteBearbeitenForm kbf = new KontaktlisteBearbeitenForm(kl);
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(kbf);
			
		}
		
	}
		
	private class ZurueckZurStartseiteClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			MainForm mf = new MainForm();
			RootPanel.get("Buttonbar").clear();
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(mf);
			
			
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
