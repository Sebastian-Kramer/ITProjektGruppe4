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
import de.hdm.itprojektgruppe4.shared.bo.Teilhaberschaft;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.KontaktKontaktliste;


public class DialogBoxTeilhaberschaftVerwalten extends DialogBox {
	
	private static KontaktAdministrationAsync kontaktVerwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	private Nutzer nutzer = new Nutzer();
	private Kontaktliste kl = null;
	private Teilhaberschaft t = new Teilhaberschaft();
	
	private VerticalPanel vpanel = new VerticalPanel();
	
	private Button teilhaberschaftAufloesen = new Button("Teilhaberschaft entfernen");
	private Button abbrechen = new Button("abbrechen");
	
	private FlexTable flextable = new FlexTable();
	private NutzerCell nutzerCell = new NutzerCell();
	private CellList<Nutzer> nutzerList = new CellList<Nutzer>(nutzerCell);
	private SingleSelectionModel<Nutzer> selectionModel = new SingleSelectionModel<Nutzer>();
	
	private Vector<Teilhaberschaft> teilhaberschaft = new Vector<Teilhaberschaft>();
	
	DialogBoxTeilhaberschaftVerwalten(Kontaktliste kl){
		this.kl = kl;
		run();
	}
	
	public void run(){
//		super.onLoad();
		this.setStylePrimaryName("dialogbox");
		
		//Setzen der Nutzerinformationen
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));
		
		NutzerDataProvider dataProvider = new NutzerDataProvider();
		dataProvider.addDataDisplay(nutzerList);
		
		abbrechen.addClickHandler(new AbbrechenClickhandler());
		teilhaberschaftAufloesen.addClickHandler(new TeilhaberschaftAufloesenClickhandler());
		nutzerList.setSelectionModel(selectionModel);
		
		
		flextable.setWidget(0, 0, nutzerList);
		flextable.setWidget(1, 0, abbrechen);
		flextable.setWidget(1, 1, teilhaberschaftAufloesen);
		this.add(flextable);
	}
	

	private void pruefeErlaubnis(){
		kontaktVerwaltung.findTeilhaberschaftByTeilhaberID(selectionModel.getSelectedObject().getID(), kl.getID(), new TeilhaberschaftAuslesen());
		
		if(nutzer.getID() == kl.getNutzerID()){
			kontaktVerwaltung.deleteTeilhaberschaftByTeilhaberID(selectionModel.getSelectedObject().getID(), new TeilhaberschaftLoeschenCallback() );
		}
		//Ist der Nutzer der Ersteller der Teilhaberschaft zu einer Kontaktliste mit einem anderen Nutzer, so ist er berechtigt, diese Teilhaberschaft
		// aufzulösen
		else if(nutzer.getID() == t.getNutzerID() && selectionModel.getSelectedObject().getID() == t.getTeilhaberID()){
			kontaktVerwaltung.deleteTeilhaberschaftByTeilhaberID(selectionModel.getSelectedObject().getID(), new TeilhaberschaftLoeschenCallback());
		}
		//Trifft keiner dieser Fälle zu ist der Nutzer nicht berechtig, eine Teilhaberschaft aufzulösen
		else if(nutzer.getID() != kl.getNutzerID() || nutzer.getID() != t.getNutzerID() && selectionModel.getSelectedObject().getID() != t.getTeilhaberID()){
			Window.alert("Sie sind nicht berechtigt, diese Teilhaberschaft aufzulösen, da Sie weder der Ersteller dieser Kontaktliste noch dieser Teilhaberschaft sind.");
			
		}
	}
	
	private class AbbrechenClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			hide();
			
		}
		
	}
		
	private class TeilhaberschaftAufloesenClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			//Teilhaberschaftsobjekt anhand der Kontaktliste und der ID des gewählten Nutzers auslesen
			
			//Ist der angemeldete Nutzer der Urheber der Kontaktliste, so kann er jegliche Teilhaberschaften auslösen
			pruefeErlaubnis();
			
				
			
		}
		
	}
	
	private class TeilhaberschaftLoeschenCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Klappt nicht");
			
		}

		@Override
		public void onSuccess(Void result) {
			Window.alert("Die Teilhaberschaft wurde erfolgreich gelöscht");
			hide();
			
		}
		
	}
	
	class TeilhaberschaftAuslesen implements AsyncCallback<Teilhaberschaft>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Teilhaberschaft result) {
			t = result;
			Window.alert(t.getNutzerID() + "");
			
			
		}

	
			
		
		
	}
		
	
	/**
	 * DataProvider, in den alle Teilhaber einer Kontaktliste gespeichert werden.
	 * Mithilfe des DataProviders können Daten im Zuge eines asnychronen Methodenaufrufs dort gespeichert werden.
	 * Anschließen werden diese Daten an die CellList <code>NutzerList</code> übergeben, um die Nutzer entsprechend
	 * in der GUI anzeigen lassen zu können.
	 * @author Raphael
	 *
	 */
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
