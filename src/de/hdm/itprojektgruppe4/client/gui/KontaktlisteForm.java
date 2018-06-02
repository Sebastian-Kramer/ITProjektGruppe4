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

public class KontaktlisteForm extends VerticalPanel {
	
	private VerticalPanel vpanel = new VerticalPanel();
	private HorizontalPanel hpanelButtonbar = new HorizontalPanel();
	
	private ScrollPanel scrollPanel = new ScrollPanel();
	
	private KontaktCell kontaktcell = new KontaktCell();
	private KontaktlisteCell kontaktlisteCell = null;
	private CellList<Kontakt> kontaktCellList = new CellList<Kontakt>(kontaktcell);
	private SingleSelectionModel<Kontakt> selectionModel = new SingleSelectionModel<Kontakt>();
	
	private Button kontaktlisteHinzufuegen = new Button();
	private Button kontaktlisteLoeschen = new Button("Kontaktliste loeschen");
	
	
	
	
	private KontaktAdministrationAsync kontaktVerwaltung = ClientsideSettings.getKontaktverwaltung();
	
	private Kontaktliste kl = null;
	
	
	public KontaktlisteForm(Kontaktliste kontaktliste){
		this.kl = kontaktliste;
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		
	
	}
	
	public void onLoad(){
		super.onLoad();
		KontakteDataProvider dataProvider = new KontakteDataProvider();
		dataProvider.addDataDisplay(kontaktCellList);
		kontaktCellList.setSelectionModel(selectionModel);
		
	

		scrollPanel.setSize("450px", "200px");
		scrollPanel.setStyleName("scrollPanel");
		kontaktCellList.setStyleName("cellListKontakte");
		scrollPanel.add(kontaktCellList);
		
		//vpanel.add(kontaktCellList);
		RootPanel.get("Buttonbar").clear();
		hpanelButtonbar.add(kontaktlisteHinzufuegen);
		hpanelButtonbar.add(kontaktlisteLoeschen);
		RootPanel.get("Buttonbar").add(hpanelButtonbar);
		vpanel.add(scrollPanel);
		vpanel.add(kontaktCellList);
		RootPanel.get("Details").add(vpanel);
	
		

	}

	
	private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler{

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			Kontakt selection = selectionModel.getSelectedObject();
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(new KontaktForm(selection));
			
			
		}
		
	}
	
	void setSelected(Kontaktliste kl){
		this.kl = kl;
		
	}
	
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
