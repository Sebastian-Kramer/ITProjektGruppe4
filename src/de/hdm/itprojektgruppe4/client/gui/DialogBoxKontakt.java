package de.hdm.itprojektgruppe4.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;

public class DialogBoxKontakt extends DialogBox{

	KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	private VerticalPanel vpanel = new VerticalPanel();
	private HorizontalPanel hpanel = new HorizontalPanel();
	private Button ok = new Button("Ok");
	private Button abbrechen = new Button("Abbrechen");
	private Label safedelete = new Label("Wollen sie den Kontakt wirklich löschen");
	
	
	public DialogBoxKontakt(final Kontakt selectedKontakt){
		
		
	
		this.setStyleName("DialogboxBackground");
	
	
		
		hpanel.add(ok);
		hpanel.add(abbrechen);
		vpanel.add(safedelete);
		vpanel.add(hpanel);
		
		
		this.add(vpanel);
	
	   abbrechen.addClickHandler(new ClickHandler() {
			
				@Override
				public void onClick(ClickEvent event) {
					hide();
				}
			});
	   
	   
	   ok.addClickHandler(new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
			verwaltung.deleteKontakt(selectedKontakt, new DeleteKontaktCallback());
			hide();
		}
	});
	   
	}  
	  class DeleteKontaktCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Fehler beim Löschen des Kontaktes.");
		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
						Window.alert("Sie haben den Kontakt erfolgreich gelöscht." );
			MainForm mf = new MainForm();
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(mf);
		}}
	   
	}

