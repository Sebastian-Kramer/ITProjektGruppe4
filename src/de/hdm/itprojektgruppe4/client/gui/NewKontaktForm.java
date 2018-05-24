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
	
	
	
	private HorizontalPanel hpanelEig1 = new HorizontalPanel();
	private TextBox tboxEig1 = new TextBox();
	private TextBox eigenschaft1 = new TextBox();
	
	private HorizontalPanel hpanelEig2 = new HorizontalPanel();
	private TextBox tboxEig2 = new TextBox();
	private TextBox eigenschaft2 = new TextBox();
	
	private HorizontalPanel hpanelEig3 = new HorizontalPanel();
	private TextBox tboxEig3 = new TextBox();
	private TextBox eigenschaft3 = new TextBox();
	
	private HorizontalPanel hpanelEig4 = new HorizontalPanel();
	private TextBox tboxEig4 = new TextBox();
	private TextBox eigenschaft4 = new TextBox();
	
	
	private TextBox eigenschaftEingeben = new TextBox();
	private TextBox auspraegungEingeben = new TextBox();
	private HorizontalPanel hpanel2 = new HorizontalPanel();
	
	private Eigenschaft eig1 = new Eigenschaft();
	
	
	
	public void onLoad(){
		
		super.onLoad();
		
		
		html2.setVisible(true);
		final Kontakt k = new Kontakt();
	
		 
		final Eigenschaft e = new Eigenschaft();
		 
		final CellTableForm ctf = new CellTableForm(k);
		 
		hpanelButtonBar.add(save1);
		hpanelButtonBar.add(cancel);
		
		RootPanel.get("Buttonbar").clear();
		RootPanel.get("Buttonbar").add(hpanelButtonBar);
		
		hpanel.add(name);
		hpanel.add(tbName);
		
		
		eigenschaft1.setValue("Vorname");
		eigenschaft1.setReadOnly(true);
		
		eigenschaft2.setValue("Nachname");
		eigenschaft2.setReadOnly(true);
		
		eigenschaft3.setValue("Geburtsdatum");
		eigenschaft3.setReadOnly(true);
		
		eigenschaft4.setValue("Adresse");
		eigenschaft4.setReadOnly(true);
		
		// Konzept in Bearbeitung 
		
		hpanelEig1.add(eigenschaft1);
		hpanelEig1.add(tboxEig1);
		
		hpanelEig2.add(eigenschaft2);
		hpanelEig2.add(tboxEig2);
		
		hpanelEig3.add(eigenschaft3);
		hpanelEig3.add(tboxEig3);
		
		hpanelEig4.add(eigenschaft4);
		hpanelEig4.add(tboxEig4);
		
		hpanel2.add(eigenschaftEingeben);
		hpanel2.add(auspraegungEingeben);
	
		
		//hpanel2.add(save2);
			
		this.add(html1);
		this.add(hpanel);
		this.add(html2);
		this.add(hpanelEig1);
		this.add(hpanelEig2);
		this.add(hpanelEig3);
		this.add(hpanelEig4);
		// CellTable auskommentiert (ausgeblendet)
		
		
		this.add(hpanel2);
		this.add(ctf);
		
		eigenschaftEingeben.setVisible(true);
		auspraegungEingeben.setVisible(true);
		
		verwaltung.getEigenschaftByID(4, new AsyncCallback<Eigenschaft>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Eigenschaft result) {
				// TODO Auto-generated method stub
				eig1.setID(result.getID());
				eig1.setBezeichnung(result.getBezeichnung());
				Window.alert("OLA" + result.getBezeichnung());
			}
		});
		
		// Konzept
		
		
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
