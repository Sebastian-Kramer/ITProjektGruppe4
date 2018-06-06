package de.hdm.itprojektgruppe4.client.gui;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;

public class SuchenForm extends VerticalPanel {

	
	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	private Label beschreibung = new Label("Bitte Geben sie den Kontatknamen ein");
	private TextBox tbox = new TextBox();
	
	private VerticalPanel vpanel = new VerticalPanel();
	
	private FlexTable flextable = new FlexTable();
	
	private DecoratorPanel suchenPanel = new DecoratorPanel();
	
	public SuchenForm(){
		
		
		flextable.setWidget(0, 0, beschreibung);
		flextable.setWidget(0, 1, tbox);
		suchenPanel.add(flextable);
		
		
		this.add(suchenPanel);
		
	
	}
	
	
}
