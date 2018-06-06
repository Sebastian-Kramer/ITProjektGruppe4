package de.hdm.itprojektgruppe4.client.gui;

import com.google.gwt.user.client.ui.DialogBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.Range;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;


import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.client.NavigationTree;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.bo.Teilhaberschaft;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.KontaktKontaktliste;

public class DialogBoxKontaktlisteTeilen extends DialogBox{
	
	private static KontaktAdministrationAsync kontaktVerwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	private Nutzer nutzer = new Nutzer();
	private Kontaktliste kl = null;
	private NutzerCell nutzerCell = new NutzerCell();
	
	private VerticalPanel vpanel = new VerticalPanel();
	
	private Button teilen = new Button("teilen");
	private Button abbrechen = new Button("abbrechen");
	
	
	private CellList<Nutzer> nutzerList = new CellList<Nutzer>(nutzerCell);
	
	private MultiSelectionModel<Nutzer> nutzerSelection = new MultiSelectionModel<Nutzer>();
	
	DialogBoxKontaktlisteTeilen(Kontaktliste kl){
		this.kl = kl;
	}
	
	public void onLoad(){
		super.onLoad();
		
		NutzerDataProvider dataProvider = new NutzerDataProvider();
		dataProvider.addDataDisplay(nutzerList);
		
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));
	
		nutzerList.setSelectionModel(nutzerSelection);
		teilen.addClickHandler(new TeilenClickhandler());
		abbrechen.addClickHandler(new AbbrechenClickhandler());
		
		
		
		vpanel.add(nutzerList);
		vpanel.add(teilen);
		vpanel.add(abbrechen);
		this.add(vpanel);
		
		
	}
	
	private class TeilenClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			if(nutzerSelection.getSelectedSet() == null){
				Window.alert("Sie m�ssen einen Nutzer ausw�hlen");
			}else{
			Vector<Nutzer> nutzerVector = new Vector<Nutzer>();
			nutzerVector.addAll(nutzerSelection.getSelectedSet());
			for(Nutzer n : nutzerVector){
				kontaktVerwaltung.insertTeilhaberschaftKontaktliste(kl.getID(), n.getID(), nutzer.getID(), new TeilhaberschaftErstellenCallback());
			}
			DialogBoxKontaktlisteTeilen.this.hide();
			KontaktlisteForm kontaktlisteForm = new KontaktlisteForm(kl);
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(kontaktlisteForm);
			}
		}
	}
	
	private class AbbrechenClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			hide();
			
		}
		
	}
	
	private class TeilhaberschaftErstellenCallback implements AsyncCallback<Teilhaberschaft>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Teilhaberschaft result) {
			Window.alert("Die Liste wurde erfolgreich geteilt");
		
			
		}
		
	}
		
	
	
	private class NutzerDataProvider extends AsyncDataProvider<Nutzer>{

		@Override
		protected void onRangeChanged(HasData<Nutzer> display) {
			final Range range = display.getVisibleRange();
			
			kontaktVerwaltung.findAllNutzer(new AsyncCallback<Vector<Nutzer>>(){
				int start = range.getStart();
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(Vector<Nutzer> result) {
					List<Nutzer> list = new ArrayList<Nutzer>();
						for(Nutzer n : result){
							list.add(n);
						}
						updateRowData(start, list);
					}
					
				
				
			});
			
		}
		
	}

}
