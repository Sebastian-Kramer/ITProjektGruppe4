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

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;

/**
 * Die Klasse <code>KontaktView</code> dient zur Darstellung des selektierten Kontaktes
 * @author Raphael
 *
 */

public class KontaktForm extends VerticalPanel {
	
	KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	private HorizontalPanel hpanel = new HorizontalPanel();
	private VerticalPanel vpanel = new VerticalPanel();
	
	
	private Label name = new Label("Name: ");
	private TextBox tbName = new TextBox();
	private Button save = new Button("Speichern");
	private Button cancel = new Button("Cancel");
	
	
	
	private Label msg = new Label("Ein neuen Kontakt anlegen");
	private Label auspraegung = new Label("");
	private Label pfusch = new Label();
	
	
	private Button bearbeitenButton = new Button("Kontakt bearbeiten");
	
	public void onLoad(){
		
		super.onLoad();
		
		
		 final Grid kontaktGrid = new Grid(5,2);
		this.add(kontaktGrid);
		
		kontaktGrid.setWidget(0, 0, msg);
		kontaktGrid.setWidget(0, 1, save);
		kontaktGrid.setWidget(1, 0, name);
		kontaktGrid.setWidget(1, 1, tbName);
		
		//Nutzer ID muss Hier noch vom Login übergeben werden 
		
		verwaltung.findAllEigenschaft(new AsyncCallback<Vector<Eigenschaft>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Vector<Eigenschaft> result) {
				// TODO Auto-generated method stub
				Window.alert("Alle Eigenschaften müssten gedfudnen sein");
				
				for(Eigenschaft eig: result){
					
				}
			}
		});
		
		save.addClickHandler(new ClickHandler() {
			
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
						
						Window.alert("Funktioniert");
					}
				});
			}
		});
		
	
		}
	
	
	
		
	
	
	
	
}
