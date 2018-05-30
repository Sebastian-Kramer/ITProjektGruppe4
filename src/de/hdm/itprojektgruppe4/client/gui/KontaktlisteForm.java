package de.hdm.itprojektgruppe4.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.*;

public class KontaktlisteForm extends VerticalPanel {
	
	private VerticalPanel vpanel = new VerticalPanel();
	private HorizontalPanel hpanelDetails = new HorizontalPanel();
	private HorizontalPanel hpanelButtonbar = new HorizontalPanel();
	
	private KontaktCell kontaktcell = new KontaktCell();
	private CellList<Kontakt> klist = new CellList<Kontakt>(kontaktcell);
	
	private KontaktAdministrationAsync kontaktVerwaltung = null;

	Kontaktliste kl = null;
	
	
	public KontaktlisteForm(Kontaktliste kontaktliste){
		this.kl = kontaktliste;
		
		kontaktVerwaltung.getAllKontakteFromKontaktliste(kontaktliste.getID(), new KontaktlisteMitKontaktenAnzeigenCallBack());
		
		this.add(hpanelDetails);
	}
	
	void setSelected(Kontaktliste kl){
		this.kl = kl;
	}
	
	class KontaktlisteMitKontaktenAnzeigenCallBack implements AsyncCallback<Vector<Kontakt>>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Vector<Kontakt> result) {
			klist.setRowCount(result.size(), true);
			klist.setRowData(0, result);
			
			hpanelDetails.add(klist);
			
			
			
		}
		
	}
}
