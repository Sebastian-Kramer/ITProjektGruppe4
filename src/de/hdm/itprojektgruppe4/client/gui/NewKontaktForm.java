package de.hdm.itprojektgruppe4.client.gui;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;

public class NewKontaktForm extends VerticalPanel {

KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	private HorizontalPanel hpanel = new HorizontalPanel();
	private VerticalPanel vpanel = new VerticalPanel();
	private HorizontalPanel hpanelButtonBar = new HorizontalPanel();
	
	private Label name = new Label("Name: ");
	private TextBox tbName = new TextBox();
	private Button save1 = new Button("Speichern");
	private Button save2 = new Button("Eigenschaft speichern");
	private Button cancel = new Button("Cancel");
	private HTML html1 = new HTML("Bitte geben Sie hier die <b> Namen </b> zu ihrem neuen "
			+ " <b>Kontakt</b>  an."		
	        + "<span style='font-family:fixed'></span>", true);
	
	private HTML html2 = new HTML("Bitte geben Sie hier die <b> Eigenschaften </b> und die dazugehörigen"
			+ " <b>Auspärgungen</b>  zu Ihrem Kontakt an."		
	        + "<span style='font-family:fixed'></span>", true);
	
	
	
	
	public void onLoad(){
		
		super.onLoad();
		
		
		html2.setVisible(true);
		final Kontakt kon = new Kontakt();
		 
		

		 
		hpanelButtonBar.add(save1);
		hpanelButtonBar.add(cancel);
		
		RootPanel.get("Buttonbar").clear();
		RootPanel.get("Buttonbar").add(hpanelButtonBar);
		
		hpanel.add(name);
		hpanel.add(tbName);
		
		

		
		verwaltung.getEigenschaftByID(4, new AsyncCallback<Eigenschaft>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Eigenschaft result) {
				// TODO Auto-generated method stub
				
				Window.alert("OLA" + result.getBezeichnung());
			}
		});
		
		
		
		
		//Nutzer ID muss Hier noch vom Login übergeben werden 
		
		
		
		save1.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				verwaltung.insertKontakt(tbName.getValue(), new Date(), new Date(), 0, 1, new AsyncCallback<Kontakt>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Window.alert("Funktioniert nicht");
					}

					@Override
					public void onSuccess(Kontakt result) {
						// TODO Auto-generated method stub
						Window.alert("Funktioniert 12");
						
						kon.setID(result.getID());
						
						Window.alert(result.getName() + result.getID());
						
						
						verwaltung.insertBasicAuspraegung("", 0, kon.getID(), new AsyncCallback<Vector<Eigenschaftauspraegung>>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								Window.alert("Funktioniert nicht");
							}

							@Override
							public void onSuccess(Vector<Eigenschaftauspraegung> result) {
								// TODO Auto-generated method stub
								Window.alert("Funktioniert QQQQ");
								final CellTableForm ctf = new CellTableForm(kon);
								add(ctf);
								
							
								
								
							}
						});
					
				    	
					}
				
					
				});
				
			
		}
		});
		
		
		save2.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
		
		

			
		
		cancel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				

				MainForm getBack = new MainForm();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(getBack);
				
			}
		});
		
		
		
	
		}
	
	
	
}
