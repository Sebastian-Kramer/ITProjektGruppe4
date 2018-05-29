package de.hdm.itprojektgruppe4.client.gui;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
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
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;

public class NewKontaktForm extends VerticalPanel {

KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	private HorizontalPanel hpanel = new HorizontalPanel();
	private VerticalPanel vpanel = new VerticalPanel();
	private HorizontalPanel hpanelButtonBar = new HorizontalPanel();
	
	private Label name = new Label("Name: ");
	private TextBox tbName = new TextBox();
	private Button save1 = new Button("Speichern");
	
	private Button save2 = new Button("Kontakt TEST speichern");
	private Button save3 = new Button("Nutzer TEST speichern");
	private Button save4 = new Button("Person TEST speichern");
	private Button save5 = new Button("Kontaktliste TEST speichern");
	private Button save6 = new Button("Eigenschaft TEST speichern");
	private Button save7 = new Button("Ausprägung TEST speichern");
	
	
	private Button cancel = new Button("Cancel");
	private HTML html1 = new HTML("Bitte geben Sie hier die <b> Namen </b> zu ihrem neuen "
			+ " <b>Kontakt</b>  an."		
	        + "<span style='font-family:fixed'></span>", true);
	
	private HTML html2 = new HTML("Bitte geben Sie hier die <b> Eigenschaften </b> und die dazugehörigen"
			+ " <b>Auspärgungen</b>  zu Ihrem Kontakt an."		
	        + "<span style='font-family:fixed'></span>", true);
	
	private Button addRow = new Button("Eigenschaft hinzufügen");
	
	private TextBox txt_Eigenschaft = new TextBox();
	private TextBox txt_Auspraegung = new TextBox();
	
	private CellTableForm ctf = null;
	
	private Eigenschaft eig1 = new Eigenschaft();
	
	public void onLoad(){
		
		super.onLoad();
		
	
		html2.setVisible(true);
		final Kontakt kon = new Kontakt();
		 
		
		 
		hpanelButtonBar.add(save1);
		hpanelButtonBar.add(save2);
		hpanelButtonBar.add(save3);
		hpanelButtonBar.add(save4);
		hpanelButtonBar.add(save5);
		hpanelButtonBar.add(save6);
		hpanelButtonBar.add(save7);
		
		hpanelButtonBar.add(cancel);
		
		RootPanel.get("Buttonbar").clear();
		RootPanel.get("Buttonbar").add(hpanelButtonBar);
		
		
		
		hpanel.add(name);
		hpanel.add(tbName);
		
		vpanel.add(html1);
		vpanel.add(hpanel);
		vpanel.add(html2);
		vpanel.add(txt_Eigenschaft);
		vpanel.add(txt_Auspraegung);
		vpanel.add(addRow);
		this.add(vpanel);
		

		
		
		KeyDownHandler returnKeyHandler = new KeyDownHandler() {
			
			 
	        @Override
	        public void onKeyDown(KeyDownEvent event) {
	            if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
	                Window.alert("Enter key pressed!!");
	             
	                
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
//									final CellTableForm ctf = new CellTableForm(kon);
									ctf = new CellTableForm(kon);
									add(ctf);
									
									
									
								}
							});
						
					    	
						}
					
						
					}); 
	                
	                
	            }
	        }
	    };

	   
	   

	  
	    tbName.addKeyDownHandler(returnKeyHandler);
		
		
		//Nutzer ID muss Hier noch vom Login übergeben werden 
		
		
		
//		save1.addClickHandler(new ClickHandler() {
//			
//			@Override
//			public void onClick(ClickEvent event) {
//				// TODO Auto-generated method stub
//				verwaltung.insertKontakt(tbName.getValue(), new Date(), new Date(), 0, 1, new AsyncCallback<Kontakt>() {
//
//					@Override
//					public void onFailure(Throwable caught) {
//						// TODO Auto-generated method stub
//						Window.alert("Funktioniert nicht");
//					}
//
//					@Override
//					public void onSuccess(Kontakt result) {
//						// TODO Auto-generated method stub
//						Window.alert("Funktioniert 12");
//						
//						kon.setID(result.getID());
//						
//						Window.alert(result.getName() + result.getID());
//						
//					
//						
//						verwaltung.insertBasicAuspraegung("", 0, kon.getID(), new AsyncCallback<Vector<Eigenschaftauspraegung>>() {
//
//							@Override
//							public void onFailure(Throwable caught) {
//								// TODO Auto-generated method stub
//								Window.alert("Funktioniert nicht");
//							}
//
//							@Override
//							public void onSuccess(Vector<Eigenschaftauspraegung> result) {
//								// TODO Auto-generated method stub
//								Window.alert("Funktioniert QQQQ");
//
//								ctf = new CellTableForm(kon);
//								add(ctf);
//								
//								
//								
//							}
//						});
//					
//				    	
//					}
//				
//					
//				});
//				
//			
//		}
//		});
		
		


		
		addRow.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				ctf.addRow(txt_Eigenschaft.getValue(), txt_Auspraegung.getValue());
				
				
				verwaltung.insertEigenschaft(txt_Eigenschaft.getText(), 0, new AsyncCallback<Eigenschaft>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(Eigenschaft result) {
						// TODO Auto-generated method stub
						
						eig1.setID(result.getID());
						
						verwaltung.insertAuspraegung(txt_Auspraegung.getText(), 0, eig1.getID(), kon.getID(), new AsyncCallback<Eigenschaftauspraegung>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void onSuccess(Eigenschaftauspraegung result) {
								// TODO Auto-generated method stub
							}
						});
					}
				});
				
				
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
		
		
		//========================TEST======================================
		
		//Kontakt
		
		save2.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				verwaltung.insertKontakt("TESTDIENSTAG 2", new Date(), new Date(), 1, 72, new AsyncCallback<Kontakt>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Window.alert("DEPLOY KONTAKT NEIN " + caught );
					}

					@Override
					public void onSuccess(Kontakt result) {
						// TODO Auto-generated method stub
					Window.alert("DEPLOY KONTAKT JA " + result.getName() );
					}
				});
			}
		});
		
		
		
		//Test Nutzer
		save3.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						// TODO Auto-generated method stub
						verwaltung.insertNutzer("DIENSTAG@MAIL2", new AsyncCallback<Nutzer>() {
		
							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								
							}
		
							@Override
							public void onSuccess(Nutzer result) {
								// TODO Auto-generated method stub
								Window.alert("DEPLOY Nutzer JA " + result.getEmail() );
							}
						});
					}
				});

			
		
		// TEest Person
		save4.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
			}
		});

		//Test Kontaktliste
		save5.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				verwaltung.insertKontaktliste("DIENSTAGKONTAKTLIsTE 2", 0, 72, new AsyncCallback<Kontaktliste>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(Kontaktliste result) {
						// TODO Auto-generated method stub
						Window.alert("DEPLOY Kontaktliste JA " + result.getBez() );
					}
				});
			}
		});
		
		
		save6.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				verwaltung.insertEigenschaft("DIENSTAGEIGENSCHAFT 2", 0, new AsyncCallback<Eigenschaft>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(Eigenschaft result) {
						// TODO Auto-generated method stub
						Window.alert("DEPLOY Eigenschaft JA " + result.getBezeichnung() );
					}
				});
			}
		});
		
		
		
		save7.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				verwaltung.insertAuspraegung("DIENSTAGAUSPRÄGUNG 2", 0, 1, 70, new AsyncCallback<Eigenschaftauspraegung>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(Eigenschaftauspraegung result) {
						// TODO Auto-generated method stub
						Window.alert("DEPLOY Ausprägung JA " + result.getWert() );
					}
				});
			}
		});
		
		//========================TEST======================================		
		
		
		
		
	
		
		
		}
	
	
	
}
