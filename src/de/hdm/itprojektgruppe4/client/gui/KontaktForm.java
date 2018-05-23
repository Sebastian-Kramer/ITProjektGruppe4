package de.hdm.itprojektgruppe4.client.gui;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.cellview.client.CellTable;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.*;

/**
 * Die Klasse <code>KontaktView</code> dient zur Darstellung des selektierten Kontaktes
 * @author Raphael
 *
 */

public class KontaktForm extends VerticalPanel {
	
	KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	private HorizontalPanel hpanel = new HorizontalPanel();
	private VerticalPanel vpanel = new VerticalPanel();
	
	private CellTable<Eigenschaftauspraegung> table = new CellTable<Eigenschaftauspraegung>();
	private Label eigenschaft = new Label("Eigenschaft");
	private Label auspraegung = new Label("Auspraegung");

	private Button bearbeitenButton = new Button("Kontakt bearbeiten");
	
	public void onLoad(){
		
		super.onLoad();
		
		this.add(table);
		
		
		
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
//				Window.alert("LÄUFT");
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
	

	
		
	
	
	
	
}
