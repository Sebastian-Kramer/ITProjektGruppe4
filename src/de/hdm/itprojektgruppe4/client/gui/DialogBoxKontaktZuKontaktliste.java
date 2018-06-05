package de.hdm.itprojektgruppe4.client.gui;

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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.DefaultSelectionEventManager;
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

/**
 * Die Klasse <code>DialogBoxKontaktZuKontaktliste</code> ermöglicht das Hinzufuegen eines Kontaktes oder mehrerer Kontakte
 * @author Raphael
 *
 */
public class DialogBoxKontaktZuKontaktliste extends DialogBox {
	
	private static KontaktAdministrationAsync kontaktVerwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	private Nutzer nutzer = new Nutzer();
	private Kontaktliste kl = null;
	
	private VerticalPanel vpanel = new VerticalPanel();
	private KontaktCell kontaktCell = new KontaktCell();
	
	
	private MultiSelectionModel<Kontakt> kontaktSelection = new MultiSelectionModel<Kontakt>();
	
	private CellTable<Kontakt> kontaktTable = new CellTable<Kontakt>();
	private CellList<Kontakt> kontaktList = new CellList<Kontakt>(kontaktCell);
	
	private Button abbrechen = new Button("Abbrechen");
	private Button kontakteHinzufuegen = new Button("Hinzufuegen");
	private Vector<Kontakt> kontaktVector = new Vector<Kontakt>();
	
	/*
	 * Konstruktor der beim Aufrufen der DialogBox zum Einsatz kommt
	 */
	DialogBoxKontaktZuKontaktliste(Kontaktliste kl){
		this.kl = kl;
	}
	
	public void onLoad(){
		super.onLoad();
		
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));
		
		kontaktVerwaltung.findAllKontaktFromNutzer(nutzer.getID(), new AlleKontakteVonNutzer());
		kontaktTable.setSelectionModel(kontaktSelection, DefaultSelectionEventManager.<Kontakt>createCheckboxManager());
		
		/*
		 * Erstellen einer Checkbox um Kontakte in der CellTable auswählen zu können
		 */
		Column<Kontakt, Boolean> checkBox = new Column<Kontakt, Boolean>(new CheckboxCell(true, false)){

			@Override
			public Boolean getValue(Kontakt object) {
				
				return kontaktSelection.isSelected(object);
			}
			
		};
		
		Column<Kontakt, String> kontakt = new Column<Kontakt, String>(new ClickableTextCell()){

			@Override
			public String getValue(Kontakt object) {
				return object.getName();
			}
			
		};
		
		kontakteHinzufuegen.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				
					for(Kontakt k : kontaktSelection.getSelectedSet()){
					kontaktVerwaltung.insertKontaktKontaktliste(k.getID(), kl.getID(), new KontaktHinzufuegen());
					}
					DialogBoxKontaktZuKontaktliste.this.hide();
					KontaktlisteForm kontaktlisteForm = new KontaktlisteForm(kl);
					NavigationTree updatedNavigation = new NavigationTree();
					RootPanel.get("Details").clear();
					RootPanel.get("Navigator").clear();
					RootPanel.get("Details").add(kontaktlisteForm);;
					RootPanel.get("Navigator").add(updatedNavigation);
				
				
			}
			
		});
		
		abbrechen.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				DialogBoxKontaktZuKontaktliste.this.hide();
				
			}
			
		});
		
		kontaktTable.addColumn(kontakt);
		kontaktTable.addColumn(checkBox);
		vpanel.add(kontaktTable);
		vpanel.add(kontakteHinzufuegen);
		vpanel.add(abbrechen);
		this.add(vpanel);
	}
	
	/**
	 * Callback-Klasse um alle Kontakte eines Nutzers mithilfe eines Callbacks zu erhalten.
	 * Die im <code>Vector<Kontakt> result</code> gespeicherten Kontakt-Objekte werden der CellTable hinzugefügt.
	 */
	class AlleKontakteVonNutzer implements AsyncCallback<Vector<Kontakt>>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Vector<Kontakt> result) {
			kontaktTable.setRowCount(result.size());
			kontaktTable.setRowData(0, result);
			kontaktVector = result;
			
		}
		
	}
	
	/**
	 * Callback-Klasse um beim betätigen des Buttons <code>kontakteHinzufuegen</code> die entsprechenden
	 * Kontakt-Objekte zu speichern. 
	 */
	class KontaktHinzufuegen implements AsyncCallback<KontaktKontaktliste>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(KontaktKontaktliste result) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
