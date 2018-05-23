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
	
	
	private Label name = new Label("Name: ");
	private TextBox tbName = new TextBox();
	private Button save1 = new Button("Kontakt speichern");
	private Button save2 = new Button("Eigenschaft speichern");
	private Button cancel = new Button("Cancel");
	private HTML html1 = new HTML("Bitte geben Sie hier die <b> Namen </b> zu ihrem neuen "
			+ " <b>Kontakt</b>  an."		
	        + "<span style='font-family:fixed'></span>", true);
	
	private HTML html2 = new HTML("Bitte geben Sie hier die <b> Eigenschaften </b> und die dazugehörigen"
			+ " <b>Auspärgungen</b>  zu Ihrem Kontakt an."		
	        + "<span style='font-family:fixed'></span>", true);
	
	
	private Label msg = new Label("Ein neuen Kontakt anlegen");
	private Label auspraegung = new Label("");

	
	private TextBox eigenschaftEingeben = new TextBox();
	private TextBox auspraegungEingeben = new TextBox();
	private HorizontalPanel hpanel2 = new HorizontalPanel();
	
	public void onLoad(){
		
		super.onLoad();
		
		
			html2.setVisible(true);
		 final Kontakt k = new Kontakt();
		 //k.setID(2);
		 
		 final Eigenschaft e = new Eigenschaft();
		 
		 final CellTableForm ctf = new CellTableForm(k);
		 
		
		 	hpanel.add(name);
			hpanel.add(tbName);
			hpanel.add(save1);
			hpanel.add(cancel);
		
			hpanel2.add(eigenschaftEingeben);
			hpanel2.add(auspraegungEingeben);
			hpanel2.add(save2);
			
		this.add(html1);
		this.add(hpanel);
		this.add(html2);
		this.add(ctf);
		this.add(hpanel2);
		
		
		eigenschaftEingeben.setVisible(true);
		auspraegungEingeben.setVisible(true);
		
		
		
		
		
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
						
						Window.alert("Funktioniert" + result.getID() + result.getName());
						html2.setVisible(true);
						eigenschaftEingeben.setVisible(true);
						auspraegungEingeben.setVisible(true);
						Kontakt kontaktNeu = new Kontakt();
						kontaktNeu.setID(result.getID());
						k.setID(kontaktNeu.getID());
						
						
						
						verwaltung.insertEigenschaft(eigenschaftEingeben.getValue(), 0, new AsyncCallback<Eigenschaft>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								Window.alert("Funktioniert nicht");
							}

							@Override
							public void onSuccess(Eigenschaft result) {
								// TODO Auto-generated method stub
								Window.alert("Funktioniert ");
								Eigenschaft eig = new Eigenschaft();
								eig.setID(result.getID());
								e.setID(eig.getID());
								
								
								verwaltung.insertAuspraegung(auspraegungEingeben.getValue(), 0, e.getID(), k.getID(),
										new AsyncCallback<Eigenschaftauspraegung>() {

									@Override
									public void onFailure(Throwable caught) {
										// TODO Auto-generated method stub
										Window.alert("Funktioniert nicht Ausprägung");
									}

									@Override
									public void onSuccess(Eigenschaftauspraegung result) {
										// TODO Auto-generated method stub
										Window.alert("Funktioniert  Ausprägung" + result.getID() + result.getEigenschaftsID() + 
												result.getKontaktID() + result.getWert());
									}
								});
							}
						});
						
						
					}
				});
			}
		});
		
		
	// Im Falle mit 2 Buttons ...	
//		save2.addClickHandler(new ClickHandler() {
//			
//			@Override
//			public void onClick(ClickEvent event) {
//				// TODO Auto-generated method stub
//				
//				
//				
//				verwaltung.insertEigenschaft(eigenschaftEingeben.getValue(), 0, new AsyncCallback<Eigenschaft>() {
//
//					@Override
//					public void onFailure(Throwable caught) {
//						// TODO Auto-generated method stub
//						Window.alert("Funktioniert nicht");
//					}
//
//					@Override
//					public void onSuccess(Eigenschaft result) {
//						// TODO Auto-generated method stub
//						Window.alert("Funktioniert ");
//						Eigenschaft eig = new Eigenschaft();
//						eig.setID(result.getID());
//						e.setID(eig.getID());
//						
//						
//						verwaltung.insertAuspraegung(auspraegungEingeben.getValue(), 0, e.getID(), k.getID(),
//								new AsyncCallback<Eigenschaftauspraegung>() {
//
//							@Override
//							public void onFailure(Throwable caught) {
//								// TODO Auto-generated method stub
//								Window.alert("Funktioniert nicht Ausprägung");
//							}
//
//							@Override
//							public void onSuccess(Eigenschaftauspraegung result) {
//								// TODO Auto-generated method stub
//								Window.alert("Funktioniert  Ausprägung" + result.getID() + result.getEigenschaftsID() + 
//										result.getKontaktID() + result.getWert());
//							}
//						});
//					}
//				});
//				
//				
//				
//			}
//		});
			
		
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
