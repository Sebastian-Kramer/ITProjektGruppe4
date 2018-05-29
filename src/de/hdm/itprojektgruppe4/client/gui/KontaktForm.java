package de.hdm.itprojektgruppe4.client.gui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.*;

/**
 * Die Klasse <code>KontaktFrom</code> dient zur Darstellung des selektierten Kontaktes
 * @author Raphael
 *
 */

public class KontaktForm extends VerticalPanel {
	
	KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	Kontakt k = new Kontakt();
	Kontakt kon = null;
	
	
	private HorizontalPanel hpanel = new HorizontalPanel();
	
	private VerticalPanel vpanel = new VerticalPanel();
	private VerticalPanel vpanelDetails = new VerticalPanel();
	private VerticalPanel vpanelDetails1 = new VerticalPanel();
	
	List<Eigenschaft> eList = new ArrayList<>();
	
	CellTable<Eigenschaftauspraegung> table = new CellTable<Eigenschaftauspraegung>();
	CellTable<Eigenschaft> eTable = new CellTable<Eigenschaft>();
	
	TextArea ta = new TextArea();
	
	private Label hinweis = new Label("Hinweis zum Kontakt");
	private Label auspraegung = new Label("Auspraegung");

	private Button bearbeitenButton = new Button("Kontakt bearbeiten");
	private Button zurueckButton = new Button("Zurück");
	
	public KontaktForm(Kontakt k){
		this.k = k;
	}
	
	public void onLoad(){
		
		RootPanel.get("Buttonbar").clear();
		
		HTML html1 = new HTML("<h2>Meine Kontakt " +  k.getName()   + "</h2>"); 
//		HTML html2 = new HTML("<div id = 'Linie'>_________________________________________________________________________________________________________________________________________________________________________________________________________</div>");
		
		super.onLoad();
		
		verwaltung.findEigenschaftauspraegungByKontaktID(k.getID(), new EigenschaftFromKontakt());
		
		Column<Eigenschaftauspraegung, String> bez = 
			    new Column<Eigenschaftauspraegung, String>(new ClickableTextCell())  {
			    
					@Override
					public String getValue(Eigenschaftauspraegung object) {
						return object.getWert();
					}
					    
		};
		
		Column<Eigenschaft, String> name = 
			    new Column<Eigenschaft, String>(new ClickableTextCell())  {
			    
					@Override
					public String getValue(Eigenschaft object) {
						return object.getBezeichnung();
					}
					    
		};
		
//		Window.alert(" " + eList.size());
//		eTable.setRowData(0, eList);
//		eTable.setRowCount(eList.size(), true);
		table.addColumn(bez, "Wert");
		eTable.addColumn(name, "Bezeichnung");
		
	    ta.setCharacterWidth(30);
	    ta.setVisibleLines(20);
		
	    vpanelDetails1.add(hinweis);
	    vpanelDetails1.add(ta);
		
	    hpanel.add(eTable);
		hpanel.add(vpanelDetails1);
		
		vpanelDetails.add(html1);
		vpanelDetails.add(hpanel);
		
		
		
		vpanel.add(vpanelDetails);
		
		RootPanel.get("Buttonbar").add(bearbeitenButton);

		this.add(vpanel);
		
		bearbeitenButton.addClickHandler(new ClickHandler(){

				@Override
				
					public void onClick(ClickEvent event) {
//						Kontakt testk = new Kontakt();
//						testk.setID(2);
						UpdateKontaktForm ukf = new UpdateKontaktForm(k);
						RootPanel.get("Details").clear();
						RootPanel.get("Details").add(ukf);
						bearbeitenButton.setVisible(false);
				}
		    	
		    });
		
		
		
//		verwaltung.findEigenschaftauspraegungByKontaktID(2, new AsyncCallback<Vector<Eigenschaftauspraegung>>(){
//
//			@Override
//			public void onFailure(Throwable caught) {
//				Window.alert("Auspraegungen laden hat nicht geklappt");
//				
//			}
//
//			@Override
//			public void onSuccess(Vector<Eigenschaftauspraegung> result) {
//				Window.alert("L�UFT");
//				kontaktGrid.resizeRows(result.size());
//				for (int row = 1; row < result.size(); ++row){
//						for(Eigenschaftauspraegung e : result){
//							kontaktGrid.setWidget(row, 0, tbEig);
//							kontaktGrid.setWidget(row, 1, tbAuspraegung);
//							tbAuspraegung.setValue(e.getWert());
//						
//						}
//						
//					
//				}
//				}
//				
//			
//			
//		});
//				
	
		}
	
	class EigenschaftFromKontakt implements AsyncCallback<Vector<Eigenschaftauspraegung>>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Die Eigenschaftsausprägungen konnten nicht geladen werden");
			
		}

		@Override
		public void onSuccess(Vector<Eigenschaftauspraegung> result) {
				
				table.setRowData(0, result);
				table.setRowCount(result.size(), true); 	
				
				for (Eigenschaftauspraegung ea : result){
					verwaltung.getEigenschaftByID(ea.getEigenschaftsID(), new AsyncCallback<Eigenschaft>(){

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Die Eigenschaften konnten nicht geladen werden");
							
						}

						@Override
						public void onSuccess(Eigenschaft result) {
							
						}
						
					});

				}
//				Window.alert(" " + eList.size());
//				eTable.setRowData(0, eList);
//				eTable.setRowCount(eList.size(), true);

		}
		
	}
	void setSelected(Kontakt k){
		kon = k;
	}

		
}
