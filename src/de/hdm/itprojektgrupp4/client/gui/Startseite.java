package de.hdm.itprojektgrupp4.client.gui;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektgruppe4.shared.bo.Kontakt;

public class Startseite extends VerticalPanel{
	
	private VerticalPanel vpanel = new VerticalPanel();
	private FlexTable ft1 = new FlexTable();
	private HTML html1 = new HTML("<article> <h2>Kontaktverwaltung</h2>");
	
	public Startseite(){
		
		ft1.setWidget(0, 1, html1);
		
		vpanel.add(ft1);
		this.add(vpanel);
		
	}

}
	
