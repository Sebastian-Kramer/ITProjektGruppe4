package de.hdm.itprojektgruppe4.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektgruppe4.client.gui.MainForm;

/**
 * Diese Klasse dient zur Anzeige des Impressums 
 *
 */
public class ImpressumSeite extends VerticalPanel {

	private VerticalPanel vpanel = new VerticalPanel();
	private FlowPanel fpanel = new FlowPanel();
	private Button zurueck = new Button("Zurück zur Startseite");
	private Image startseite = new Image();

	private HTML html1 = new HTML(

			"<div class='Impressum'>" + "Hochschule der Medien" + "</br>"
					+ "<b>Wirtschaftsinformatik und Digitale Medien</b></br>" + "Nobelstrasse 10</br>"
					+ "70569 Stuttgart</br></br>" + "Kontakt</br>Telefon: +49 711 8923 10</br>"
					+ "<br><br>Der Studiengang Wirtschaftsinformatik und digitale "
					+ "Medien<br>wird rechtlich vertreten durch die Hochschule der Med"
					+ "ien Stuttgart. <br> <br><A HREF=\"https://www.hdm-stuttgart.de/"
					+ "impressum\"TARGET=\"_blank\">Impressum der Hochschule</A>" + "</div>");

	public void onLoad() {
		
		//Stylen der Widgets
		startseite.setUrl("/Image/Startseite.png");
		startseite.setStyleName("ButtonICON");
		zurueck.getElement().appendChild(startseite.getElement());
		
		//Hinzufügen der Widgets und Buttons
		vpanel.add(html1);
		fpanel.add(zurueck);
		RootPanel.get("Buttonbar").add(zurueck);

		this.add(vpanel);
		
		zurueck.addClickHandler(new GetBackButton());

	}
	
	/**
	 * ClickHandler um bei Betätigen des Buttons auf die Startseite zurückzugelangen
	 *
	 */
	class GetBackButton implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			MainForm mf = new MainForm();
			RootPanel.get("Buttonbar").clear();
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(mf);
		}

	}
}
