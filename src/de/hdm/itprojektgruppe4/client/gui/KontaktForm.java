package de.hdm.itprojektgruppe4.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
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
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;

/**
 * * Die Klasse <code>KontaktFrom</code> dient zur Darstellung des selektierten
 * Kontaktes. In der Buttonbar werden die Buttons Kontakt bearbeiten, Kontakt löschen, 
 * Teilhaberschaft verwalten und Kontakt einer Liste hinzufügen aufgeführt. Mit diesen Buttons
 * werden die entsprechenden Aktion ausgeführt.
 * 
 * @author Sebastian Kramer
 *
 */
public class KontaktForm extends VerticalPanel {

	private KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();

	private Kontakt k = new Kontakt();
	private Nutzer nutzer = new Nutzer();

	private HorizontalPanel hpanel = new HorizontalPanel();
	private HorizontalPanel hpanel1 = new HorizontalPanel();
	private VerticalPanel vpanel = new VerticalPanel();
	private VerticalPanel vpanelDetails1 = new VerticalPanel();
	private VerticalPanel vpanelBearbeitung = new VerticalPanel();
	private DateTimeFormat fmt = DateTimeFormat.getFormat("dd.MM.yyyy");

	private SingleSelectionModel<EigenschaftAuspraegungWrapper> sm = new SingleSelectionModel<EigenschaftAuspraegungWrapper>();
	private ScrollPanel scrollPanel = new ScrollPanel();
	private Button bearbeitenButton = new Button("Kontakt bearbeiten");
	private Button loeschenButton = new Button("Kontakt löschen");
	private Button kontaktListehinzufuegen = new Button("Kontakt einer Liste hinzufügen");
	private Button kontaktTeilen = new Button("Teilhaberschaft verwalten");
	private boolean deleteKonAlert;

	private NutzerCell nutzerCell = new NutzerCell();
	private CellList<Nutzer> teilhaberCL = new CellList<Nutzer>(nutzerCell);
	private VerticalPanel vpanelPopUp = new VerticalPanel();
	private Label teilInfo = new Label("Mit folgenden Nutzern geteilt: ");

	private CellTableForm ctf = null;
	private ClickableTextCell bezeigenschaft = new ClickableTextCell();
	private ClickableTextCell wertauspraegung = new ClickableTextCell();
	private CellTableForm.ColumnEigenschaft bezEigenschaft = ctf.new ColumnEigenschaft(bezeigenschaft);
	private CellTableForm.ColumnAuspraegung wertAuspraegung = ctf.new ColumnAuspraegung(wertauspraegung);
	
	private Image sharedPic = new Image("Image/contactShared.png");
	private Image loeschenPic = new Image("Image/Loeschen.png");
	private Image bearbeitenPic = new Image("Image/Bearbeiten.png");
	private Image konZuListPic = new Image("Image/KontaktZuListe.png");
	private Image zurueckZuHome = new Image("Image/Startseite.png");
	private Image teilVerwaltenPic = new Image("Image/Teilen.png");

	/**
	 * Einfacher Konstruktor in dem nur ein Kontaktobjekt übergeben wird.
	 * 
	 * @param k
	 */
	public KontaktForm(Kontakt k) {
		this.k = k;
		ctf = new CellTableForm(k);
		if (k.getStatus() == 0) {

		} else if (k.getStatus() == 1) {
			sharedPic.setVisible(true);

		}

	}

	/**
	 * Konstruktor für geteilte Kontakte
	 * 
	 * @param k
	 * @param teilhaberschaft
	 */
	public KontaktForm(Kontakt k, String teilhaberschaft) {

		this.k = k;
		ctf = new CellTableForm(k, teilhaberschaft);
		if (k.getStatus() == 0) {

		} else if (k.getStatus() == 1) {
			sharedPic.setVisible(true);

		}

	}

	/**
	 * Konstruktor für Kontakte die in einer geteilten Liste sind -> Read ONLY
	 * 
	 * @param k
	 * @param shared
	 */
	public KontaktForm(Kontakt k, int shared) {
		this.k = k;
		ctf = new CellTableForm(k);
		bearbeitenButton.setVisible(false);
		loeschenButton.setVisible(false);
		kontaktTeilen.setVisible(false);
		kontaktListehinzufuegen.setVisible(false);
		sharedPic.setVisible(false);
		
	}

	/**
	 * Die onLoad() Methode wird bei Aufruf von KontaktForm ausgeführt. Cookies
	 * werden überprüft und Widgets und Panels gesetzt.
	 * 
	 * @author Clirim
	 *
	 */
	public void onLoad() {

		super.onLoad();

		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));
		final Image kontaktbild = new Image("Image/Visitenkarte_2.png");
		sharedPic.setStyleName("sharedPic");
		kontaktbild.setStyleName("Kontaktbild");
		loeschenPic.setStyleName("ButtonICON");
		bearbeitenPic.setStyleName("ButtonICON");
		konZuListPic.setStyleName("ButtonICON");
		zurueckZuHome.setStyleName("ButtonICON");
		teilVerwaltenPic.setStyleName("ButtonICON");
		vpanelPopUp.setStyleName("popupPanel");

		HTML html1 = new HTML("<h2>" + k.getName() + "</h2>");
		html1.setStyleName("KontaktUeberschrift");
		HTML html2 = new HTML("Erstellt am: " + fmt.format(k.getErzeugungsdatum()));
		HTML html3 = new HTML("Zuletzt geändert am: " + fmt.format(k.getModifikationsdatum()));

		ctf.addColumn(bezEigenschaft, "Kontakteigenschaften: ");
		ctf.addColumn(wertAuspraegung);
		ctf.addColumn(ctf.getStatus());
		ctf.setSelectionModel(sm);
		ctf.setStyleName("CellTableKontakt");
		RootPanel.get("Buttonbar").clear();

		scrollPanel.add(ctf);

		vpanelBearbeitung.add(html2);
		vpanelBearbeitung.add(html3);
		vpanelBearbeitung.setStyleName("KontaktDaten");
		hpanel1.setStyleName("HpanelTop");
		hpanel1.setWidth("700px");
		hpanel1.add(kontaktbild);
		hpanel1.add(html1);
		hpanel1.add(sharedPic);
		hpanel1.add(vpanelBearbeitung);
		hpanel.add(scrollPanel);
		vpanelDetails1.add(hpanel1);
		vpanel.add(vpanelDetails1);
		vpanel.add(hpanel);
		this.add(vpanel);

		RootPanel.get("Buttonbar").add(bearbeitenButton);
		RootPanel.get("Buttonbar").add(loeschenButton);
		RootPanel.get("Buttonbar").add(kontaktTeilen);
		RootPanel.get("Buttonbar").add(kontaktListehinzufuegen);

		loeschenButton.getElement().appendChild(loeschenPic.getElement());
		bearbeitenButton.getElement().appendChild(bearbeitenPic.getElement());
		kontaktListehinzufuegen.getElement().appendChild(konZuListPic.getElement());
		kontaktTeilen.getElement().appendChild(teilVerwaltenPic.getElement());
		loeschenButton.addClickHandler(new ClickLoeschenHandler());
		bearbeitenButton.addClickHandler(new ClickBearbeitenHandler());
		kontaktTeilen.addClickHandler(new ClickTeilenHandler());
		kontaktListehinzufuegen.addClickHandler(new ClickHinzufuegenHandler());
		/**
		 * Abfrage des Status - wenn der Status nicht 0 ist wird das
		 * Geteilt-Icon gesetzt.
		 * 
		 * @author Clirim
		 *
		 */
		if (k.getStatus() == 0) {
			sharedPic.setVisible(false);
		} else if (k.getStatus() == 1) {
			sharedPic.setVisible(true);

		}
		/**
		 * Methode die mit einem MouseOverHandler dafür sorgt, dass eine
		 * Informative Anzeige erscheint wenn mit die Maus auf dem Geteilt-Icon
		 * liegt.
		 * 
		 * @author Clirim
		 *
		 */
		if (k.getNutzerID() == nutzer.getID()) {

			sharedPic.addMouseOverHandler(new MouseOverHandler() {

				@Override
				public void onMouseOver(MouseOverEvent event) {

					verwaltung.getAllTeilhaberFromKontakt(k.getID(), new TeilhaberFromKontaktCallback());

				}
			});
		}

	}

	/**
	 * In dieser abstrakten Klasse wird die informative Anzeige in Form einer
	 * PopUpInfo erzeugt. Diese Zeigt alle Nutzer an an die ein Objekt geteilt
	 * ist.
	 * 
	 * @author Clirim
	 *
	 */
	private class PopUpInfo extends PopupPanel {

		public PopUpInfo() {

			super(true);

			addDomHandler(new MouseOutHandler() {

				@Override
				public void onMouseOut(MouseOutEvent event) {

					hide();
				}
			}, MouseOutEvent.getType());

			setPopupPosition(sharedPic.getAbsoluteLeft(), sharedPic.getAbsoluteTop());

		}

	}

	/**
	 * In dem ClickHandler ClickLoeschenHandler wird nach einer
	 * Nutzerüberprüfung, entweder der ganze Kontakt gelöscht oder die
	 * Teilhaberschaft entfernt.
	 * 
	 * @author Clirim
	 *
	 */
	private class ClickLoeschenHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {

			if (nutzer.getID() == k.getNutzerID()) {
				deleteKonAlert = Window.confirm("Möchten Sie den Kontakt endgültig löschen?");
				if (deleteKonAlert == true) {
					verwaltung.deleteKontakt(k, new DeleteKontaktCallback());
				}
			} else {
				deleteKonAlert = Window.confirm("Möchten Sie Ihre Teilhaberschaft entfernen?");
				if (deleteKonAlert == true) {
					verwaltung.deleteTeilhaberschaftAllByKontaktIDAndTeilhaberID(k.getID(), nutzer.getID(),
							new DeleteKontaktTeilhaberschaftCallback());
				}
			}
		}
	}

	/**
	 * Der ClickHandler ClickZurueckHandler wird aufgerufen durch den Button
	 * zurueckBtn, welcher eine neue MainForm und einen neuen NavigationTree
	 * öffnet.
	 * 
	 * @author Clirim
	 *
	 */
	class ClickZurueckHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			MainForm mf = new MainForm();
			NavigationTree nf = new NavigationTree();
			RootPanel.get("Navigator").clear();
			RootPanel.get("Buttonbar").clear();
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(mf);
			RootPanel.get("Navigator").add(nf);
			bearbeitenButton.setVisible(false);

		}
	}

	/**
	 * In dem ClickHandler ClickBearbeitenHandler, der sich auf den
	 * bearbeitenButton bezieht, wird nach einer Nutzerüberprüfung eine
	 * UpdateKontaktForm erstellt, in der man einen Kontakt bearbeiten kann.
	 * 
	 * @author Clirim
	 *
	 */
	class ClickBearbeitenHandler implements ClickHandler {
		@Override

		public void onClick(ClickEvent event) {

			if (k.getNutzerID() == nutzer.getID()) {
				UpdateKontaktForm ukf = new UpdateKontaktForm(k);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(ukf);
				bearbeitenButton.setVisible(false);

			} else {
				UpdateKontaktForm ukf2 = new UpdateKontaktForm(k, "Teilhaberschaft");
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(ukf2);
				bearbeitenButton.setVisible(false);

			}
		}
	}

	/**
	 * In dem ClickHandler ClickTeilen, der sich auf den teilenButton bezieht,
	 * wird nach einer Nutzerüberprüfung eine TeilhaberschaftForm erstellt, in
	 * der man einen Kontakt und/oder einzelne Ausprägungen an andere Nutzer
	 * teilen kann.
	 * 
	 * @author Clirim
	 *
	 */
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

	/**
	 * Der ClickHandler ClickHinzufuegenHandler wird aufgerufen durch den Button
	 * kontaktListehinzufuegen. Eine Dialogbox wird angezeigt, in der man einen
	 * Kontakt einer Liste hinzufügen kann.
	 * 
	 * @author Clirim
	 *
	 */
	class ClickHinzufuegenHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			DialogBoxAddContactToList dbkl = new DialogBoxAddContactToList(k);
			dbkl.center();
		}

	}

	/**
	 * Dieser Callback wird verwendet um herauszulesen, an welche Nutzer ein
	 * Objekt geteilt ist. Die Nutzer werden in einer CellList dargestellt.
	 * 
	 * @author Clirim
	 *
	 */
	private class TeilhaberFromKontaktCallback implements AsyncCallback<Vector<Nutzer>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<Nutzer> result) {

			teilhaberCL.setRowCount(result.size(), true);
			teilhaberCL.setRowData(result);
			teilhaberCL.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
			vpanelPopUp.add(teilInfo);
			vpanelPopUp.add(teilhaberCL);
			PopUpInfo pop = new PopUpInfo();
			pop.setWidget(vpanelPopUp);
			pop.show();
		}

	}

	/**
	 * Der Callback wird aufgerufen nachdem ein Kontakt aus dem System gelöscht
	 * wurde. Es wird eine neue MainForm generiert.
	 * 
	 * @author Clirim
	 *
	 */
	private class DeleteKontaktCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Void result) {

			Window.alert("Sie haben den Kontakt erfolgreich gelöscht.");
			MainForm mf = new MainForm();
			NavigationTree updatedTree = new NavigationTree();
			RootPanel.get("Buttonbar").clear();
			RootPanel.get("Details").clear();
			RootPanel.get("Navigator").clear();
			RootPanel.get("Details").add(mf);
			RootPanel.get("Navigator").add(updatedTree);

		}

	}

	/**
	 * Der Callback wird aufgerufen nachdem eine Teilhaberschaft aus dem System
	 * entfernt wurde. Es wird eine neue MainForm generiert.
	 * 
	 * @author Clirim
	 *
	 */
	class DeleteKontaktTeilhaberschaftCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Void result) {
			Window.alert("Sie haben Ihre Teilhaberschaft erfolgreich gelöscht.");
			MainForm mf = new MainForm();
			NavigationTree updatedTree = new NavigationTree();
			RootPanel.get("Buttonbar").clear();
			RootPanel.get("Details").clear();
			RootPanel.get("Navigator").clear();
			RootPanel.get("Details").add(mf);
			RootPanel.get("Navigator").add(updatedTree);

		}

	}

}
