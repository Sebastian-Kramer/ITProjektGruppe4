package de.hdm.itprojektgruppe4.client.gui;

import java.util.ArrayList;
import java.util.List;
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
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.*;
import de.hdm.itprojektgruppe4.client.ClientsideSettings;

public class KontaktlisteForm extends VerticalPanel {
	
	private VerticalPanel vpanel = new VerticalPanel();
	private HorizontalPanel hpanelDetails = new HorizontalPanel();
	private HorizontalPanel hpanelButtonbar = new HorizontalPanel();
	
	private KontaktCell kontaktcell = new KontaktCell();
	private CellList<Kontakt> kontaktCellList = new CellList<Kontakt>(kontaktcell);
	private ListDataProvider<Kontakt> kontakteDataProvider = null;
	
	private Button kontaktlisteHinzufuegen = new Button();
	private Button kontaktlisteLoeschen = new Button("Kontaktliste loeschen");
	
	
	private KontaktAdministrationAsync kontaktVerwaltung = null;
	
	

	Kontaktliste kl = new Kontaktliste();
	
	
	public KontaktlisteForm(Kontaktliste kontaktliste){
		this.kl = kontaktliste;
	}
	
	public void onLoad(){
		
		RootPanel.get("Buttonbar").clear();
		hpanelButtonbar.add(kontaktlisteHinzufuegen);
		hpanelButtonbar.add(kontaktlisteLoeschen);
		RootPanel.get("Buttonbar").add(hpanelButtonbar);
		
		kontaktVerwaltung.getAllKontakteFromKontaktliste(kl.getID(), new KontaktlisteMitKontaktenAnzeigenCallBack());

		vpanel.add(hpanelDetails);
		this.add(vpanel);
		RootPanel.get("Details").add(vpanel);
	}

	
	
	void setSelected(Kontaktliste kl){
		this.kl = kl;
		
	}
	
	
	class KontaktlisteMitKontaktenAnzeigenCallBack implements AsyncCallback<Vector<Kontakt>>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Die Kontaktliste konnte nicht geladen werden");
			
		}

		@Override
		public void onSuccess(Vector<Kontakt> result) {
			kontakteDataProvider = new ListDataProvider<Kontakt>();
			for(Kontakt kon : result){
				kontakteDataProvider.getList().add(kon);
			}
			
			kontakteDataProvider.addDataDisplay(kontaktCellList);
			kontaktCellList.setRowCount(result.size(), true);
			hpanelDetails.add(kontaktCellList);
			
		}
		
	}
	
	
	

}
