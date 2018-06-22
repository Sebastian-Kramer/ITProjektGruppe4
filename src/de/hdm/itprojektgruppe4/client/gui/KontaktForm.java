package de.hdm.itprojektgruppe4.client.gui;

import java.util.Vector;

import com.google.appengine.api.xmpp.XMPPServicePb.PresenceResponse.SHOW;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.client.EigenschaftAuspraegungWrapper;
import de.hdm.itprojektgruppe4.client.NavigationTree;
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

	private Kontakt k = new Kontakt();
	private Nutzer nutzer = new Nutzer();

	KontaktlisteKontaktTreeViewModel kktvw = null;

	private HorizontalPanel hpanel = new HorizontalPanel();

	private HorizontalPanel hpanel1 = new HorizontalPanel();

	private VerticalPanel vpanel = new VerticalPanel();
	private VerticalPanel vpanelDetails = new VerticalPanel();
	private VerticalPanel vpanelDetails1 = new VerticalPanel();
	private VerticalPanel vpanelBearbeitung = new VerticalPanel();
	DateTimeFormat fmt = DateTimeFormat.getFormat("dd.MM.yyyy");

	private SingleSelectionModel<EigenschaftAuspraegungWrapper> sm = new SingleSelectionModel<EigenschaftAuspraegungWrapper>();

	private ScrollPanel scrollPanel = new ScrollPanel();

	private Button bearbeitenButton = new Button("Kontakt bearbeiten");
	private Button loeschenButton = new Button("Kontakt löschen");
	private Button zurueckBtn = new Button("Zurück");
	private Button kontaktListehinzufuegen = new Button("Kontakt einer Liste hinzufügen");
	private Button kontaktTeilen = new Button("Kontakt teilen");
	private boolean deleteKonAlert;
	private CellTableForm ctf = null;
	private ClickableTextCell bezeigenschaft = new ClickableTextCell();
	private ClickableTextCell wertauspraegung = new ClickableTextCell();
	private CellTableForm.ColumnEigenschaft bezEigenschaft = ctf.new ColumnEigenschaft(bezeigenschaft);
	private CellTableForm.ColumnAuspraegung wertAuspraegung = ctf.new ColumnAuspraegung(wertauspraegung);
	private Image sharedPic = new Image();

	public KontaktForm(Kontakt k) {
		this.k = k;
		ctf = new CellTableForm(k);

	}

	public KontaktForm(Kontakt k, String teilhaberschaft) {

		this.k = k;
		ctf = new CellTableForm(k, teilhaberschaft);

	}

	public void onLoad() {

		super.onLoad();

		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));
		final Image kontaktbild = new Image();
		kontaktbild.setUrl("https://ssl.gstatic.com/s2/contacts/images/NoPicture.gif");
		sharedPic.setUrl("Image/contactShared.png");

		HTML html1 = new HTML("<h2>" + k.getName() + "</h2>");
		HTML html2 = new HTML("Erstellt am: " + fmt.format(k.getErzeugungsdatum()));
		HTML html3 = new HTML("Zuletzt geändert am: " + fmt.format(k.getModifikationsdatum()));

		ctf.addColumn(bezEigenschaft, "Eigenschaft: ");
		ctf.addColumn(wertAuspraegung, "Wert: ");
		ctf.addColumn(ctf.getStatus(), "Status");
		ctf.setSelectionModel(sm);
		RootPanel.get("Buttonbar").clear();

		scrollPanel.setSize("650px", "300px");
		scrollPanel.add(ctf);

		vpanelBearbeitung.add(html2);
		vpanelBearbeitung.add(html3);
		hpanel1.add(kontaktbild);
		hpanel1.add(html1);
		hpanel1.add(vpanelBearbeitung);

		hpanel.add(scrollPanel);

		vpanelDetails1.add(hpanel1);
		vpanelDetails.add(hpanel1);

		vpanel.add(vpanelDetails1);
		vpanel.add(vpanelDetails);
		vpanel.add(hpanel);
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

		} else if (k.getStatus() == 1) {
			hpanel1.add(sharedPic);

		}
		
//		INbeabreitung VON NINO		
//		sharedPic.addMouseOverHandler(new MouseOverHandler() {
//			
//			@Override
//			public void onMouseOver(MouseOverEvent event) {
//				// TODO Auto-generated method stub
//				verwaltung.getAllTeilhaberFromKontakt(k.getID(), new AsyncCallback<Vector<Nutzer>>() {
//					
//					@Override
//					public void onSuccess(Vector<Nutzer> result) {
//						// TODO Auto-generated method stub
//						PopupPanel pop = new PopupPanel();
//						pop.setWidget(new Label("HALLOPOPUP"));
//						pop.show();
//						
//					}
//					
//					@Override
//					public void onFailure(Throwable caught) {
//						// TODO Auto-generated method stub
//						
//					}
//				});
//			}
//		});

	}

	class ClickLoeschenHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {

			
			
			deleteKonAlert = Window.confirm("Möchten Sie den Kontakt endgültig löschen ?");
			if(deleteKonAlert == true){
				verwaltung.deleteKontakt(k, new DeleteKontaktCallback());
			}
				
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
			Window.alert(k.getName());

			if (k.getNutzerID() == nutzer.getID()) {
				UpdateKontaktForm ukf = new UpdateKontaktForm(k);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(ukf);
				bearbeitenButton.setVisible(false);
				Window.alert("Test 1");
			} else {
				UpdateKontaktForm ukf2 = new UpdateKontaktForm(k, "Teilhaberschaft");
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(ukf2);
				bearbeitenButton.setVisible(false);
				Window.alert("Test 2");
			}
		}
	}

	class ClickTeilenHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (k.getNutzerID() == nutzer.getID()) {
				TeilhaberschaftForm tf = new TeilhaberschaftForm(k);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(tf);
			} else {
				TeilhaberschaftForm tf = new TeilhaberschaftForm(k, "Teilhaberschaft");
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(tf);
			}
		}
	}

	class ClickHinzufuegenHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			DialogBoxAddContactToList dbkl = new DialogBoxAddContactToList(k);
			dbkl.center();
		}

	}
	
	private class DeleteKontaktCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			Window.alert("Sie haben den Kontakt erfolgreich gelöscht." );
			MainForm mf = new MainForm();
			NavigationTree updatedTree = new NavigationTree();
			RootPanel.get("Buttonbar").clear();
			RootPanel.get("Details").clear();
			RootPanel.get("Navigator").clear();
			RootPanel.get("Details").add(mf);
			RootPanel.get("Navigator").add(updatedTree);
			
			
		}
		 
	}
	 void setKktvw(KontaktlisteKontaktTreeViewModel kktvw) {
			this.kktvw = kktvw;
		}
}
