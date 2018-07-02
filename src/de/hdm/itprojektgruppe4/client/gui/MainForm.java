package de.hdm.itprojektgruppe4.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;

/**
 * Diese Klasse stellt die Startseite der Kontaktverwaltung dar
 * @author Sebi_
 *
 */

public class MainForm extends VerticalPanel {

	Kontakt kon = new Kontakt();
	Kontaktliste konList = new Kontaktliste();
	Kontaktliste selectedKontaktlist = null;
	Nutzer nutzer = new Nutzer();

	private VerticalPanel vpanelDetails = new VerticalPanel();
	private HorizontalPanel hpanelDetails = new HorizontalPanel();
	private FlowPanel fpanel = new FlowPanel();

	private FlexTable anordnung = new FlexTable();
	private Image logo = new Image("Image/Logo.png");
	private Button newKontakt = new Button("Neuen Kontakt anlegen");
	private Button newKontaktliste = new Button("Neue Kontaktliste anlegen");
	private Button suchen = new Button("Suchen");
	private HTML greetHTML2 = new HTML("Herzlich Willkommen auf <b>WYNWYN</b>, Ihrer <b>Kontaktverwaltung</b>. "
			+ "<br> Hier können Sie neue Kontakte anlegen, " + "<br> diese in verschiedene Listen organisieren und "
			+ "<br> sowohl Kontakte als auch Kontaktlisten mit anderen Nutzern teilen.");
	private Image startImage = new Image();
	private Image newKonPic = new Image();
	private Image newKonlistPic = new Image();
	private Image suchenPic = new Image();

	public MainForm() {

		// Stylen der Widgets
		greetHTML2.setStyleName("BegrueßungsText");
		hpanelDetails.setStyleName("HPanel");
		newKonPic.setStyleName("ButtonICON");
		newKonlistPic.setStyleName("ButtonICON");
		suchenPic.setStyleName("ButtonICON");
		newKonPic.setUrl("Image/Neuer_Kontakt.png");
		newKonlistPic.setUrl("Image/Neue_Liste_2.png");
		suchenPic.setUrl("Image/Suchen.png");
	}

	public void onLoad() {

		super.onLoad();
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));

		// Hinzufügen der Widgets und Buttons
		fpanel.add(newKontaktliste);
		fpanel.add(newKontakt);
		fpanel.add(suchen);
		RootPanel.get("Buttonbar").add(fpanel);
		anordnung.setWidget(0, 0, greetHTML2);
		hpanelDetails.add(startImage);
		logo.setStyleName("LogoStartseite");
		vpanelDetails.add(hpanelDetails);
		vpanelDetails.add(anordnung);
		this.add(logo);
		this.add(vpanelDetails);

		// Hinzufügen der Icons und ClickHandler zu den Buttons
		newKontakt.getElement().appendChild(newKonPic.getElement());
		newKontaktliste.getElement().appendChild(newKonlistPic.getElement());
		suchen.getElement().appendChild(suchenPic.getElement());
		newKontaktliste.addClickHandler(new NewListClickHandler());
		newKontakt.addClickHandler(new NewKontaktClickHandler());
		suchen.addClickHandler(new SuchenClickHandler());

	}

	/**
	 * ClickHandler, der die DialogBox <code>DialogBoxNewKontaktliste</code>
	 * öffnet, die das Erstellen einer neuen Kontaktliste ermöglicht
	 * 
	 * @author Raphael
	 *
	 */
	class NewListClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			DialogBoxNewKontaktliste dbk = new DialogBoxNewKontaktliste();
			dbk.center();
		}

	}

	/**
	 * ClickHandler, der das Anlegen eines neuen Kontakts ermöglicht, indem die
	 * Klasse <code>NewKontaktForm</code> aufgerufen wird
	 *
	 */
	class NewKontaktClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			NewKontaktForm nkf = new NewKontaktForm();
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(nkf);
		}

	}

	/**
	 * ClickHandler, der die Suchfunktion ermöglicht
	 * 
	 */
	class SuchenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			SuchenForm sf = new SuchenForm();
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(sf);

		}
	}

}
