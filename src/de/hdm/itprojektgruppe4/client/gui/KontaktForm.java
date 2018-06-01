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
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.client.gui.UpdateKontaktForm.AllEigenschaftCallback;
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
	

	private SingleSelectionModel<EigenschaftAuspraegungHybrid> sm = new SingleSelectionModel<EigenschaftAuspraegungHybrid>();
	private CellTableForm ctf = null;
	


	private Button bearbeitenButton = new Button("Kontakt bearbeiten");
	private Button loeschenButton = new Button("Kontakt löschen");
	
	
	
	public KontaktForm(Kontakt k){
		this.k = k;
	}
	
	public void onLoad(){
		
		
		final Image kontaktbild = new Image();
		kontaktbild.setUrl("https://ssl.gstatic.com/s2/contacts/images/NoPicture.gif");
		RootPanel.get("Buttonbar").clear();
		
		HTML html1 = new HTML("<h2>" +  k.getName()   + "</h2>"); 

		super.onLoad();
		verwaltung.findAllEigenschaft(new AllEigenschaftCallback());
		ctf = new CellTableForm(k);
		
		

		hpanel.add(vpanelDetails1);
		hpanel.add(ctf);
		vpanelDetails1.add(kontaktbild);
		vpanelDetails1.add(html1);
		vpanelDetails.add(hpanel);
		
		ctf.setSelectionModel(sm);
		vpanel.add(vpanelDetails1);
		vpanel.add(vpanelDetails);
		
		RootPanel.get("Buttonbar").add(bearbeitenButton);
		RootPanel.get("Buttonbar").add(loeschenButton);

		this.add(vpanel);
		
		loeschenButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				DialogBox deleteBox = new DialogBoxKontakt(k);
				deleteBox.center();
			
			}
		});
		
		
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
	
//	class EigenschaftFromKontakt implements AsyncCallback<Vector<Eigenschaftauspraegung>>{
//
//		@Override
//		public void onFailure(Throwable caught) {
//			Window.alert("Die Eigenschaftsausprägungen konnten nicht geladen werden");
//			
//		}
//
//		@Override
//		public void onSuccess(Vector<Eigenschaftauspraegung> result) {
//				
//				table.setRowData(0, result);
//				table.setRowCount(result.size(), true); 	
//				
//				for (Eigenschaftauspraegung ea : result){
//					verwaltung.getEigenschaftByID(ea.getEigenschaftsID(), new AsyncCallback<Eigenschaft>(){
//
//						@Override
//						public void onFailure(Throwable caught) {
//							Window.alert("Die Eigenschaften konnten nicht geladen werden");
//							
//						}
//
//						@Override
//						public void onSuccess(Eigenschaft result) {
//							
//						}
//						
//					});
//
//				}
//				Window.alert(" " + eList.size());
//				eTable.setRowData(0, eList);
//				eTable.setRowCount(eList.size(), true);
//
//		}
//		
//	}
	
	class AllEigenschaftCallback implements AsyncCallback<Vector<Eigenschaft>>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Vector<Eigenschaft> result) {
			
			for (Eigenschaft eig : result)
				;

			for (Eigenschaft eig : result) {

			}
		}
		
	}
	
	
	void setSelected(Kontakt k){
		kon = k;
	}

		
}
