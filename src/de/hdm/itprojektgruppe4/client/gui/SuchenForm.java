package de.hdm.itprojektgruppe4.client.gui;

import java.util.List;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;

public class SuchenForm extends VerticalPanel {

	
	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	private Label beschreibung = new Label("Bitte Geben sie den Kontatknamen ein");
	private TextBox tboxKontaktname = new TextBox();
	private Button suchen = new Button("Suchen");
	
	private VerticalPanel vpanel = new VerticalPanel();
	
	private FlexTable flextable = new FlexTable();
	
	private DecoratorPanel suchenPanel = new DecoratorPanel();
	
	private MultiWordSuggestOracle  nameoracle = new MultiWordSuggestOracle();
	
	private SuggestBox suggestionBox = new SuggestBox(nameoracle);
	
	public SuchenForm(){
	}
	
	public void onLoad(){
		
		super.onLoad();
		
		Nutzer nutzer = new Nutzer();
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("mail"));
		
		flextable.setWidget(0, 0, beschreibung);
		flextable.setWidget(0, 1, suggestionBox);
		flextable.setWidget(0, 2, suchen);
		suchenPanel.add(flextable);
		suchenPanel.setStyleName("DialogboxBackground");
		suggestionBox.setStyleName("DialogboxBackground");
		
		this.add(suchenPanel);
	
		suchen.addClickHandler(new SuchenButton());
		verwaltung.findAllKontaktFromNutzer(nutzer.getID(), new  AllKontakteCallBack());
	
	
		
		
	}
	
	class SuchenButton implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			Nutzer nutzer = new Nutzer();
			nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
			nutzer.setEmail(Cookies.getCookie("mail"));
			Kontakt k = new Kontakt();
			
			k.setName(tboxKontaktname.getValue());
			k.setNutzerID(nutzer.getID());
			
			
			
		}
		
	}

	class AllKontakteCallBack implements AsyncCallback<Vector<Kontakt>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("SUCCES");
		}

		@Override
		public void onSuccess(Vector<Kontakt> result) {
			// TODO Auto-generated method stub
			Window.alert("SUCCES");
			Kontakt k = new Kontakt();
			
			for (Kontakt kontakt : result) {
				
				nameoracle.add(kontakt.getName());
			}
			
			
			}
		}

		
	}
	
	


