package de.hdm.itprojektgruppe4.client.gui;

import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ImageBundle.Resource;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.client.EigenschaftAuspraegungWrapper;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.*;

/**
 * Die Klasse <code>KontaktFrom</code> dient zur Darstellung des selektierten
 * Kontaktes
 * 
 *
 */

public class KontaktForm extends VerticalPanel {

	KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();

	Kontakt k = new Kontakt();
	Kontakt kon = null;
	Kontaktliste kl = new Kontaktliste();

	KontaktlisteKontaktTreeViewModel kktvw = null;

	private HorizontalPanel hpanel = new HorizontalPanel();
	private HorizontalPanel hpanel2 = new HorizontalPanel();

	private VerticalPanel vpanel = new VerticalPanel();
	private VerticalPanel vpanelDetails = new VerticalPanel();
	private VerticalPanel vpanelDetails1 = new VerticalPanel();
	DateTimeFormat fmt = DateTimeFormat.getFormat("dd.MM.yyyy");

	private SingleSelectionModel<EigenschaftAuspraegungWrapper> sm = new SingleSelectionModel<EigenschaftAuspraegungWrapper>();

	private ScrollPanel scrollPanel = new ScrollPanel();

	private Button bearbeitenButton = new Button("Kontakt bearbeiten");
	private Button loeschenButton = new Button("Kontakt löschen");
	private Button zurueckBtn = new Button("Zurück");
	private Button kontaktListehinzufuegen = new Button("Kontakt einer Liste hinzufügen");
	private Button kontaktTeilen = new Button("Teilhaberschaft verwalten");

	private CellTableForm ctf = null;
	private ImageCell image = new ImageCell();
	private ClickableTextCell bezeigenschaft = new ClickableTextCell();
	private ClickableTextCell wertauspraegung = new ClickableTextCell();
	private CellTableForm.ColumnStatus status = ctf.new ColumnStatus(image);
	private CellTableForm.ColumnEigenschaft bezEigenschaft = ctf.new ColumnEigenschaft(bezeigenschaft);
	private CellTableForm.ColumnAuspraegung wertAuspraegung = ctf.new ColumnAuspraegung(wertauspraegung);
	private Image sharedPic = new Image();
	private Image notSharedPic = new Image();
	private FlexTable ifShared = new FlexTable();
	 
	
	public KontaktForm(Kontakt k) {
		this.k = k;
		ctf = new CellTableForm(k);
		
		

	}

	public KontaktForm(Kontakt k, String teilhaberschaft) {
		this.k = k;
		Window.alert(teilhaberschaft);
		ctf = new CellTableForm(k, teilhaberschaft);
	}

	public KontaktForm(Kontaktliste kl, Kontakt k) {
		this.kl = kl;
		this.k = k;
	}

	public void onLoad() {

		final Image kontaktbild = new Image();
		kontaktbild.setUrl("https://ssl.gstatic.com/s2/contacts/images/NoPicture.gif");
		sharedPic.setUrl("Image/contactShared.png");
		notSharedPic.setUrl("Image/contactNotShared.png");
		
		RootPanel.get("Buttonbar").clear();

		HTML html1 = new HTML("<h2>" + k.getName() + "</h2>");
		HTML html2 = new HTML("Erstellt am: " + fmt.format(k.getErzeugungsdatum()));
		HTML html3 = new HTML("Zuletzt geändert am: " + fmt.format(k.getModifikationsdatum()));

		ctf.addColumn(bezEigenschaft, "Eigenschaft: ");
		ctf.addColumn(wertAuspraegung, "Wert: ");
		ctf.addColumn(status, "Status");
		ctf.setSelectionModel(sm);
		
		hpanel2.add(html1);	
		hpanel.add(vpanelDetails1);
		scrollPanel.setSize("650px", "300px");
		scrollPanel.add(ctf);
		hpanel.add(scrollPanel);
		vpanelDetails1.add(kontaktbild);
		vpanelDetails1.add(hpanel2);	
		vpanelDetails1.add(html2);
		vpanelDetails1.add(html3);
		vpanelDetails.add(hpanel);
		vpanel.add(vpanelDetails1);
		vpanel.add(vpanelDetails);
		this.add(vpanel);

		RootPanel.get("Buttonbar").add(bearbeitenButton);
		RootPanel.get("Buttonbar").add(loeschenButton);
		RootPanel.get("Buttonbar").add(kontaktTeilen);
		RootPanel.get("Buttonbar").add(kontaktListehinzufuegen);
		RootPanel.get("Buttonbar").add(zurueckBtn);

		loeschenButton.addClickHandler(new ClickLoeschenHandler());
		zurueckBtn.addClickHandler(new ClickZurueckHandler());
		bearbeitenButton.addClickHandler(new ClickearbeitenHandler());
		kontaktTeilen.addClickHandler(new ClickTeilenHandler());
		kontaktListehinzufuegen.addClickHandler(new ClickHinzufuegenHandler());
		
		if (k.getStatus() == 0) {
			hpanel2.add(notSharedPic);
			
		}else if(k.getStatus() == 1) {
			hpanel2.add(sharedPic);
		
		}

	}

	
	
	
	class ClickLoeschenHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {

			DialogBox deleteBox = new DialogBoxKontakt(k);
			deleteBox.center();

		}
	}

	class ClickZurueckHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			MainForm mf = new MainForm();
			RootPanel.get("Buttonbar").clear();
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(mf);
			bearbeitenButton.setVisible(false);

		}
	}

	class ClickearbeitenHandler implements ClickHandler {
		@Override

		public void onClick(ClickEvent event) {
			// Kontakt testk = new Kontakt();
			// testk.setID(2);
			UpdateKontaktForm ukf = new UpdateKontaktForm(k);
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(ukf);
			bearbeitenButton.setVisible(false);
		}
	}

	class ClickTeilenHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			TeilhaberschaftForm tf = new TeilhaberschaftForm(k);
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(tf);
		}
	}

	class ClickHinzufuegenHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			DialogBoxAddContactToList dbkl = new DialogBoxAddContactToList(k);
			dbkl.center();
		}

	}

	void setSelected(Kontakt k) {
		kon = k;
	}

}
