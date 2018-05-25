package de.hdm.itprojektgruppe4.client.gui;
import java.util.ArrayList;
import java.util.Vector;

import org.datanucleus.state.CallbackHandler;

import com.google.appengine.api.files.FileServicePb.ShuffleRequest.Callback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.EigenschaftAuspraegungHybrid;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;


public class UpdateKontaktForm extends VerticalPanel{

	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();
	
FlexTable ft_KontaktBearbeiten = new FlexTable();
	
private VerticalPanel vpanelDetails = new VerticalPanel();	
private HorizontalPanel hpanelDetails = new HorizontalPanel();

	private Kontakt kon = new Kontakt();
	
	private Label lbl_KontaktName = new Label("Kontaktname: ");
	private TextBox txt_KontaktName = new TextBox();
	private TextBox txt_Eigenschaft = new TextBox();
	private TextBox txt_Auspraegung = new TextBox();
	private Button save = new Button("Speichern");
	private Button cancel = new Button("Cancel");
	private Button addRow = new Button("Add");
	private Button saveRow = new Button("Save Changes");
		
	private EigenschaftAuspraegungHybrid ea = new EigenschaftAuspraegungHybrid();
		
	private Eigenschaft eig1 = new Eigenschaft();
		
	public UpdateKontaktForm(Kontakt kon) {
		
		
		
		this.kon = kon;
	}	
		
     public void onLoad(){
 		
 		super.onLoad();
 		
 		
 		
     Window.alert("die id ist: " + kon.getID() + "name: " + kon.getName());
 		verwaltung.findKontaktByID(kon.getID(), new AsyncCallback<Kontakt>(){

 			
 			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Kontakt result) {
				txt_KontaktName.setText(result.getName());
			
				
			}
 			
 		});
 		
 		final CellTableForm ctf = new CellTableForm(kon);
 		
 		

 		txt_KontaktName.setText(kon.getName());
// 		txt_Eigenschaft.setText(kon.ge);
 		
// 		hpanelDetails.add(lbl_Eigenschaft);
// 		hpanelDetails.add(txt_Eigenschaft);
 		
 		
 		
 		hpanelDetails.add(lbl_KontaktName);
 		hpanelDetails.add(txt_KontaktName);
 		hpanelDetails.add(save);
 		hpanelDetails.add(cancel);
 		
 		
 		vpanelDetails.add(hpanelDetails);
 		vpanelDetails.add(ctf);
 		vpanelDetails.add(addRow);
 		vpanelDetails.add(saveRow);
 		vpanelDetails.add(txt_Auspraegung);
 		vpanelDetails.add(txt_Eigenschaft);
 		

		this.add(vpanelDetails);
 		
 		ea.setAuspraegung(txt_Auspraegung.getText());
 		ea.setEigenschaft(txt_Eigenschaft.getText());
 		
 		
 		final Eigenschaftauspraegung eigaus = new Eigenschaftauspraegung();
 		eigaus.setEigenschaftsID(ctf.sm.getLastSelectedObject().getEigenschaftID());
 		eigaus.setWert(ctf.sm.getLastSelectedObject().getAuspraegung());
// 		ctf.sm.getLastSelectedObject().getAuspraegung();
 		
 		saveRow.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				verwaltung.updateAuspraegung(eigaus, new AsyncCallback<Eigenschaftauspraegung>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("nix da");
						
					}

					@Override
					public void onSuccess(Eigenschaftauspraegung result) {
					Window.alert("funkt");
						
					}
				});
				
			}
		});
 		
 		
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
		
		
		
 		verwaltung.findAllEigenschaft(new AsyncCallback<Vector<Eigenschaft>>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Vector<Eigenschaft> result) {
				Window.alert("alle Eigenschaften m�ssten gefunden sein");
				
				for (Eigenschaft eig: result);
				
			}
 			
 		});
 		
 		
 		
}

	}

