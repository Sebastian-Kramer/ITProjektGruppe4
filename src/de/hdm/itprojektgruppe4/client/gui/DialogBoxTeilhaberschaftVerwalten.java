package de.hdm.itprojektgruppe4.client.gui;

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
import com.google.gwt.user.client.ui.DialogBox;
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
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.KontaktKontaktliste;


public class DialogBoxTeilhaberschaftVerwalten extends DialogBox {
	
	private static KontaktAdministrationAsync kontaktVerwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	private Nutzer nutzer = new Nutzer();
	private Kontaktliste kl = null;
	
	private VerticalPanel vpanel = new VerticalPanel();
	
	private NutzerCell nutzerCell = new NutzerCell();
	private CellList<Nutzer> nutzerList = new CellList<Nutzer>(nutzerCell);
	
	DialogBoxTeilhaberschaftVerwalten(Kontaktliste kl){
		this.kl = kl;
	}
	
	public void onLoad(){
		super.onLoad();
		
		NutzerDataProvider dataProvider = new NutzerDataProvider();
		dataProvider.addDataDisplay(nutzerList);
		
		
		vpanel.add(nutzerList);
		this.add(vpanel);
	}
	
	private class NutzerDataProvider extends AsyncDataProvider<Nutzer>{

		@Override
		protected void onRangeChanged(HasData<Nutzer> display) {
			final Range range = display.getVisibleRange();
			
			kontaktVerwaltung.findAllTeilhaberFromKontaktliste(kl.getID(),new AsyncCallback<Vector<Nutzer>>(){
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
