package de.hdm.itprojektgruppe4.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;

/**
 * Die Klasse <code>KontaktView</code> dient zur Darstellung des selektierten Kontaktes
 * @author Raphael
 *
 */

public class KontaktForm extends VerticalPanel {
	KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	private HorizontalPanel hpanel = new HorizontalPanel();
	private VerticalPanel vpanel = new VerticalPanel();
	
	private Label eigenschaft = new Label("Eigenschaft");
	private Label auspraegung = new Label("");
	
	Button bearbeitenButton = new Button("Kontakt bearbeiten");
	
	public void onLoad(){
		
		super.onLoad();
		
		Grid kontaktGrid = new Grid(5,2);
		this.add(kontaktGrid);
		
		kontaktGrid.setWidget(0, 1, eigenschaft);
		
		bearbeitenButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				//bearbeiten Seite öffnen
				
			}
			
		});	
			
		}
	
	
	
		
	
	
	
	
}
