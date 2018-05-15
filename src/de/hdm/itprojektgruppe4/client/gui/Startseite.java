package de.hdm.itprojektgruppe4.client.gui;

import java.util.Vector;

import org.datanucleus.state.CallbackHandler;

import com.google.appengine.api.files.FileServicePb.ShuffleRequest.Callback;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;

public class Startseite extends VerticalPanel{
	
	private VerticalPanel vpanel = new VerticalPanel();
	private FlexTable ft1 = new FlexTable();
	private CellTable<Kontakt> ct = new CellTable<Kontakt>();
	private Button b1 = new Button("Test");
	private HTML html1 = new HTML("<h2>Kontaktverwaltung</h2>");
	
	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	public Startseite(){
	
		
			TextColumn<Kontakt> nameColumn = new TextColumn<Kontakt>() {
				@Override
				public String getValue(Kontakt k) {
					
					return "SEBI";
					 
				}
			};
	    
		ct.addColumn(nameColumn, "Name");

		    
		ft1.setWidget(0, 1, html1);
		
		vpanel.add(b1);
		vpanel.add(ct);
		vpanel.add(ct);
		vpanel.add(ft1);
		
		this.add(vpanel);
		
	}

}