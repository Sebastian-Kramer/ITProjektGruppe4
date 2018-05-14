package de.hdm.itprojektgruppe4.client;

import de.hdm.itprojektgruppe4.shared.FieldVerifier;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ITProjektSS18 implements EntryPoint {

	VerticalPanel vpanel = new VerticalPanel();
	Button button = new Button("Hallo Nino");
	TextBox tb = new TextBox();
	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	@Override
	public void onModuleLoad() {
		RootPanel.get("Details").clear();
		vpanel.add(button);
		vpanel.add(tb);
		RootPanel.get("Details").add(vpanel);
		
		button.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				verwaltung.insertKontakt(tb.getValue(), new Date(), new Date(), 1, 1, new AsyncCallback<Kontakt>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Funktioniert nicht");
					}

					@Override
					public void onSuccess(Kontakt result) {
						Window.alert("Funktioniert");
					}
				});
			}
			
		});
		
	}
	
}
