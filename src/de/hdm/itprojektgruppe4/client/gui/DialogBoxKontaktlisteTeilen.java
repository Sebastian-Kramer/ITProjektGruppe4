package de.hdm.itprojektgruppe4.client.gui;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;

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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.Range;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.bo.Teilhaberschaft;

public class DialogBoxKontaktlisteTeilen extends DialogBox{
	
	private static KontaktAdministrationAsync kontaktVerwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	private Nutzer nutzer = new Nutzer();
	private Kontaktliste kl = null;
	private NutzerCell nutzerCell = new NutzerCell();
	private Vector<Nutzer> teilhaberListe = new Vector<Nutzer>();
	
	private VerticalPanel vpanel = new VerticalPanel();
	private HTML html = null;
	private Button teilen = new Button("Kontaktliste teilen");
	private Button abbrechen = new Button("abbrechen");
	
	private CellList<Nutzer> nutzerList = new CellList<Nutzer>(nutzerCell);
	private SingleSelectionModel<Nutzer> nutzerSelection = new SingleSelectionModel<Nutzer>();
	private FlexTable ft = new FlexTable();
	private ListDataProvider<Nutzer> dataProvider = new ListDataProvider<Nutzer>();
	
	
	DialogBoxKontaktlisteTeilen(Kontaktliste kl){
		this.kl = kl;
		kontaktVerwaltung.findAllTeilhaberFromKontaktliste(kl.getID(), new TeilhaberVonKontaktliste());
		kontaktVerwaltung.findAllNutzer(new NutzerAnzeigen());
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));
		dataProvider.addDataDisplay(nutzerList);
		nutzerList.setSelectionModel(nutzerSelection);
		html = new HTML("Wählen Sie einen Nutzer, mit dem Sie die Kontaktliste teilen möchten:");	
	}
	
	public void onLoad(){
		super.onLoad();
		
		teilen.addClickHandler(new TeilenClickhandler());
		abbrechen.addClickHandler(new AbbrechenClickhandler());
		
		ft.setWidget(1, 0, nutzerList);
		ft.setWidget(2, 0, teilen);
		ft.setWidget(2, 1, abbrechen);
		ft.setStylePrimaryName("Flextable");
		
		vpanel.add(html);
		vpanel.add(ft);
		this.add(vpanel);
		this.setStylePrimaryName("DialogboxBackground1");
	}
	
	/**
	 * Clickhandler, der das Teilen einer Kontaktliste mit einem Nutzer ermoeglicht.
	 * Hierfuer wird das in der Celllist ausgewaehlte Objekt einem neuen Nutzer-Objekt zugewiesen.
	 * Die Teilhaberschaft wird dann anhand der Kontaktliste und dem Nutzer mithilfe eines Callbacks in der Datenbank
	 * angelegt.
	 *
	 */
	private class TeilenClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// ist kein Nutzer ausgew�hlt, wird dies mithilfe eines Window-Alerts dem User kenntlich gemacht.
			if(nutzerSelection.getSelectedObject() == null){
				Window.alert("Sie muessen einen Nutzer auswaehlen");
			}else{
			Nutzer n = nutzerSelection.getSelectedObject();
			//Bei erfolgreicher Teilung der Kontaktliste wird der Status der Kontaktliste auf 1 (= geteilt) gesetzt
			kl.setStatus(1);
				kontaktVerwaltung.insertTeilhaberschaftKontaktliste(kl.getID(), n.getID(), nutzer.getID(), new TeilhaberschaftErstellenCallback());
				
			DialogBoxKontaktlisteTeilen.this.hide();
			KontaktlisteForm kontaktlisteForm = new KontaktlisteForm(kl);
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(kontaktlisteForm);
			}
		}
	}
	
	/**
	 * Implementierung eines Clickhandlers, um beim Klick des Abbrechen-Buttons die DialogBox zu schliessen.
	 */
	private class AbbrechenClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			hide();
			
		}
		
	}
	
	/*
	 * Callback-Klasse um eine Teilhaberschaft zu erstellen.
	 * Wird eine Teilhaberschaft erstellt, wird auch gleichzeitig der Status der Kontaktliste auf 1 (= geteilt) gesetzt.
	 */
	private class TeilhaberschaftErstellenCallback implements AsyncCallback<Teilhaberschaft>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Teilhaberschaft result) {
			if(result==null){
				Window.alert("Sie koennen diese Liste nicht teilen");
			}else{
			Window.alert("Die Liste wurde erfolgreich geteilt");
			kontaktVerwaltung.updateKontaktliste(kl, new KontaktlisteUpdatenCallback());
			
			
			}
			
		}
		
	}
	
	private class KontaktlisteUpdatenCallback implements AsyncCallback<Kontaktliste>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Kontaktliste result) {
			
		}
		
	}
	
	/*
	 * Callback-Klasse um alle Teilhaber an einer Kontaktliste zu erhalten.
	 * Der Ergebnisvektor <code>result</code> wird dem Vector <code>teilhaberListe</code> zugewiesen.
	 */
	private class TeilhaberVonKontaktliste implements AsyncCallback<Vector<Nutzer>>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Vector<Nutzer> result) {
			teilhaberListe = result;
			
		}

		
	}
		
	/*
	 * Callback-Klasse um alle Nutzer zu erhalten, mit denen die Kontaktliste geteilt werden kann.
	 * Da eine Kontaktliste nicht mehrmals mit dem gleichen Nutzer geteilt werden soll, werden Nutzer, die bereits Teilhaber
	 * an einer Kontakt sind, werden diese Kontakte aus dem Ergebnis-Vektor <code>result</code> entfernt.
	 * Hierfür wird der Ergebnis-Vektor mit dem Vektor <code>teilhaberListe</code> gegengeprüft, der die Nutzer enthält, die bereits eine Teilhaberschaft 
	 * an der Kontaktliste besitzen.
	 */
	private class NutzerAnzeigen implements AsyncCallback<Vector<Nutzer>>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Vector<Nutzer> result) {
			for(Nutzer n : result){
				for(Nutzer user : teilhaberListe){
					if(n.getID() == user.getID() || n.getID() == nutzer.getID()){
						result.remove(n);
					}
				}
			}
			nutzerList.setRowCount(result.size());
			nutzerList.setRowData(result);
			
		}
		
	}
	

}
