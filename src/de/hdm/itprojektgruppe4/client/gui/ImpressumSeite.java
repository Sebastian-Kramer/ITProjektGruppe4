package de.hdm.itprojektgruppe4.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ImpressumSeite extends VerticalPanel {

	private VerticalPanel vpanel = new VerticalPanel();
	private Button zurueck = new Button("Zurück");
	
	
	private HTML html1 = new HTML(
			
			"<div class='Impressum'>"
			+ "Hochschule der Medien"+ "</br>"
			+ "<b>Wirtschaftsinformatik und Digitale Medien</b></br>"
			+ "Nobelstrasse 10</br>"
			+ "70569 Stuttgart</br></br>"
			+ "Kontakt</br>Telefon: +49 711 8923 10</br>"
			+ "<br><br>Der Studiengang Wirtschaftsinformatik und digitale "
			+ "Medien<br>wird rechtlich vertreten durch die Hochschule der Med"
			+ "ien Stuttgart. <br> <br><A HREF=\"https://www.hdm-stuttgart.de/"
			+ "impressum\"TARGET=\"_blank\">Impressum der Hochschule</A>"
			+ "</div>");
	
	
	public void onLoad(){ 
		
		
		vpanel.add(html1);
		vpanel.add(zurueck);
		
		this.add(vpanel);
		
		zurueck.addClickHandler(new GetBackButton());
		
	}
	
	class GetBackButton implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			MainForm mf = new MainForm();
			RootPanel.get("Buttonbar").clear();
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(mf);
		}
		
	}
}
