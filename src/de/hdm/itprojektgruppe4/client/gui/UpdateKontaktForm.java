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
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;


public class UpdateKontaktForm extends VerticalPanel{

	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();
	
FlexTable ft_KontaktBearbeiten = new FlexTable();
	
private VerticalPanel vpanelDetails = new VerticalPanel();	
private HorizontalPanel hpanelDetails = new HorizontalPanel();

	private Kontakt kon = new Kontakt();
	
	private Label lbl_KontaktName = new Label("Kontaktname: ");
	private TextBox txt_KontaktName = new TextBox();
	private Button save = new Button("Speichern");
	private Button cancel = new Button("Cancel");
	private Button addRow = new Button("Add");
	private Button deleteRow = new Button("Delete last row");
	
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
				Window.alert("funktioniert");
				txt_KontaktName.setText(result.getName());
			
				
			}
 			
 		});
 		
 		CellTableForm ctf = new CellTableForm(kon);
 		
 		

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
 		

		this.add(vpanelDetails);
 		
 		
		addRow.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
//				ctf
				
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

