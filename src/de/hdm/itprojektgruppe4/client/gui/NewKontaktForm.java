package de.hdm.itprojektgruppe4.client.gui;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Cookies;
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
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.client.EigenschaftAuspraegungWrapper;
import de.hdm.itprojektgruppe4.client.gui.UpdateKontaktForm.AuspraegungBearbeitenCallback;
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
	private HorizontalPanel hpanel2 = new HorizontalPanel();
	
	
	
	private Label name = new Label("Name: ");
	private TextBox tbName = new TextBox();
	private Button save1 = new Button("Speichern");
	private Label eigenschaftName = new Label("Eigenschaft");
	private Label auspraegungName = new Label("Ausprägung");
	
	
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
	
	private Kontakt kontakt1 = new Kontakt();
	
	private Nutzer nutzer = new Nutzer();
	
	KontaktlisteKontaktTreeViewModel kktvw = null;
	
	private Eigenschaftauspraegung eigaus = new Eigenschaftauspraegung();
	
	private SingleSelectionModel<EigenschaftAuspraegungWrapper> sm = new SingleSelectionModel<EigenschaftAuspraegungWrapper>();
	
	public void onLoad(){
		
		super.onLoad();
		
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));
		
		html2.setVisible(false);
		txt_Eigenschaft.setVisible(false);
		txt_Auspraegung.setVisible(false);
		eigenschaftName.setVisible(false);
		auspraegungName.setVisible(false);
		addRow.setVisible(false);
		
		hpanelButtonBar.add(save1);
		
		
		hpanelButtonBar.add(cancel);
		
		RootPanel.get("Buttonbar").clear();
		RootPanel.get("Buttonbar").add(hpanelButtonBar);
		
		hpanel2.add(eigenschaftName);
		hpanel2.add(txt_Eigenschaft);
		hpanel2.add(auspraegungName);
		hpanel2.add(txt_Auspraegung);
		hpanel2.add(addRow);
		
		hpanel.add(name);
		hpanel.add(tbName);
		

		
		vpanel.add(html1);
		vpanel.add(hpanel);
		vpanel.add(html2);
	
		
		this.add(vpanel);
		

		
		
		cancel.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						
		
						MainForm getBack = new MainForm();
						RootPanel.get("Buttonbar").clear();						
						RootPanel.get("Details").clear();						
						RootPanel.get("Details").add(getBack);
//					RootPanel.get("Buttonbar").add(getBack);
					}
				});
		
		KeyDownHandler returnKeyHandler = new KeyDownHandler() {
			
			 
	        @Override
	        public void onKeyDown(KeyDownEvent event) {
	            if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
	               
	             
	                verwaltung.insertKontakt(tbName.getValue(), new Date(), new Date(), 0, nutzer.getID(), new KontaktErstellenCallback());
	                html2.setVisible(true);
	                
	            }
	        }
	    };

	   
	    tbName.addKeyDownHandler(returnKeyHandler);
		
	
		addRow.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				ctf.addRow(txt_Eigenschaft.getValue(), txt_Auspraegung.getValue());
				
				verwaltung.insertEigenschaft(txt_Eigenschaft.getText(), 0, new EigenschaftHinzufuegenCallback() );
				
				
			}
			
		});
		
	
		}
	
	class KontaktErstellenCallback implements AsyncCallback<Kontakt> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Kontakt wurde nicht erstellt");
		}

		@Override
		public void onSuccess(Kontakt result) {
			// TODO Auto-generated method stub
			kontakt1.setID(result.getID());
			Window.alert("Kontakt " +result.getName() + " wurde  erstellt");
			verwaltung.insertBasicAuspraegung("", 0, result.getID(), new BasicAuspraegungenCallback());
			
			
		}
		
	}
	
	class BasicAuspraegungenCallback implements  AsyncCallback<Vector<Eigenschaftauspraegung>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("die leeren ausprägungen wurden NICHT erstellt ");
		}

		@Override
		public void onSuccess(Vector<Eigenschaftauspraegung> result) {
			// TODO Auto-generated method stub
			Window.alert("die leeren ausprägungen wurden erstellt ");
			html2.setVisible(true);
			eigenschaftName.setVisible(true);
			auspraegungName.setVisible(true);
			txt_Eigenschaft.setVisible(true);
			txt_Auspraegung.setVisible(true);
			addRow.setVisible(true);
			
			
			
			ctf = new CellTableForm(kontakt1);
			add(ctf);			
			add(hpanel2);
			
			ctf.setSelectionModel(sm);
			
//			ctf.getWertAuspraegung().setFieldUpdater(new FieldUpdater<EigenschaftAuspraegungHybrid, String>() {
//
//				@Override
//				public void update(int index, EigenschaftAuspraegungHybrid object, String value) {
//					sm.getSelectedObject().setAuspraegung(value);
//					sm.getSelectedObject().setAuspraegungID(object.getAuspraegungID());
//				
//					
//					eigaus.setID(sm.getSelectedObject().getAuspraegungID());
//					eigaus.setWert(sm.getSelectedObject().getAuspraegung());
//					eigaus.setStatus(0);
//					eigaus.setKontaktID(kontakt1.getID());
//					eigaus.setEigenschaftsID(sm.getSelectedObject().getEigenschaftID());	
//					
//					
//				}	
//					
//			});
			
			
			KeyDownHandler kdh = new KeyDownHandler(){


				@Override
				public void onKeyDown(KeyDownEvent event) {
					if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {

						verwaltung.updateAuspraegung(eigaus, new AuspraegungBearbeitenCallback());
						
					}
					
				}
				
			};	
			
			ctf.addKeyDownHandler(kdh);	
			
		}

	}
	
	class EigenschaftHinzufuegenCallback implements  AsyncCallback<Eigenschaft>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Die Neue Eigenschaft wurde nicht hinzugefügt");
		}

		@Override
		public void onSuccess(Eigenschaft result) {
			// TODO Auto-generated method stub
			Window.alert("Die Neue Eigenschaft: " + result.getBezeichnung() + " wurde  hinzugefügt");
			verwaltung.insertAuspraegung(txt_Auspraegung.getText(), 0, result.getID(), kontakt1.getID(), new AuspraegungHinzufuegenCallback());
		}

	}
	
	class AuspraegungHinzufuegenCallback implements AsyncCallback<Eigenschaftauspraegung>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Die Neue Ausprägung wurde  nicht hinzugefügt");
		}

		@Override
		public void onSuccess(Eigenschaftauspraegung result) {
			// TODO Auto-generated method stub
			Window.alert("Die Neue Ausprägung wurde  hinzugefügt");
		}
		
	}
	
	
	class AuspraegungBearbeitenCallback implements AsyncCallback<Eigenschaftauspraegung>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert(" Hat nicht funktioniert ");
		}

		@Override
		public void onSuccess(Eigenschaftauspraegung result) {
			// TODO Auto-generated method stub
			eigaus.setWert(result.getWert());
			Window.alert("Sie haben die Auspr�gung " + result.getWert() + " angepasst");
		}
		
	}
	
	
}
