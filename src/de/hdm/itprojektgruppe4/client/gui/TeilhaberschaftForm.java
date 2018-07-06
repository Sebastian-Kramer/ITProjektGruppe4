package de.hdm.itprojektgruppe4.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.client.EigenschaftAuspraegungWrapper;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;

/**
 * Mit der Klasse TeilhaberschaftForm lassen sich die Teilhaberschaften aller
 * Ausprägungnen eines Kontakt verwalten. Dazu gehört das Anlegen und Löschen
 * von Teilhaberschaften. Die Ausprägungen eines Kontakts können komplett oder
 * nur teilweise an einen anderen, im System angemeldeten, Nutzer geteilt
 * werden. Außerdem kann der Eigentümer der Teilhaberschaft oder der teilhabende
 * Nutzer die Teilhabschaft entfernen
 * 
 * @author Sebi_0107
 *
 */
public class TeilhaberschaftForm extends VerticalPanel {

	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();

	private Kontakt kon = new Kontakt();
	private Nutzer nutzer = new Nutzer();

	private VerticalPanel vpanel = new VerticalPanel();
	private HorizontalPanel hpanelTop = new HorizontalPanel();
	private HorizontalPanel nutzerSuchenPanel = new HorizontalPanel();

	private Button zurueck = new Button("Zurück");
	private Button allTeilen = new Button("Alle Eigenschaftsausprägungen teilen");
	private Button einzTeilen = new Button("Ausgewählte Eigenschaftsausprägungen teilen");
	private Button allLoeschen = new Button("Alle Teilhaberschaften löschen");

	private MultiWordSuggestOracle nutzerOracle = new MultiWordSuggestOracle();
	private SuggestBox nutzerSugBox = new SuggestBox(nutzerOracle);

	private CellTableForm ctf = null;
	private Image kontaktbild = new Image("Image/Visitenkarte_2.png");
	private Image teilen = new Image("Image/Teilen2.png");
	private Image teilen2 = new Image("Image/Teilen1.png");
	private Image teilen3 = new Image("Image/Teilen1.png");

	private Image getBackPic = new Image("Image/Back.png");
	private Image deletePic = new Image("Image/Loeschen.png");

	private ScrollPanel scrollPanel = new ScrollPanel();
	private HTML html1 = null;
	private HTML html2 = new HTML(
			"Sie können auch nur bestimmte <b> Ausprägungen </b> an einen anderen <b> Nutzer </b> teilen, "
					+ " </br> wählen Sie dafür die entsprechenden <b> Ausprägungen </b>  mit den Checkboxen aus."
					+ "<span style='font-family:fixed'></span>",
			true);

	private HTML html3 = new HTML(
			"Bitte wählen Sie hier den <b> Nutzer </b> aus dem der <b>Kontakt</b>  geteilt werden soll."
					+ "<span style='font-family:fixed'></span>",
			true);

	final MultiSelectionModel<EigenschaftAuspraegungWrapper> selectionModelWrapper = new MultiSelectionModel<EigenschaftAuspraegungWrapper>();

	/**
	 * Konstruktur: Beim Laden der TeilhaberschaftForm wird ein Kontakt-Objekt
	 * übergeben, damit alle entsprechenden Ausprägungen zu diesem Kontakt
	 * angezeigt werden können. Ist der angemeldete Nutzer der Eigentümer des
	 * Kontakts, werden alle Details des Kontakts (z.B. Ausprägungen und
	 * Teilhaberschaften) angezeigt.
	 * 
	 * @param k
	 */
	public TeilhaberschaftForm(Kontakt k) {

		this.kon = k;
		ctf = new CellTableForm(kon);
		einzTeilen.setVisible(false);

		RootPanel.get("Buttonbar").clear();
		RootPanel.get("Buttonbar").add(zurueck);
		RootPanel.get("Buttonbar").add(allLoeschen);
		RootPanel.get("Buttonbar").add(allTeilen);
		RootPanel.get("Buttonbar").add(einzTeilen);

	}

	/**
	 * Konstruktur: Beim Laden der TeilhaberschaftForm wird ein Kontakt-Objekt
	 * und eine Teilhaberschaft übergeben, damit alle entsprechenden
	 * Ausprägungen zu diesem Kontakt angezeigt werden können. Dieser
	 * Kontstruktur wird verwendet, wenn der angemeldete Nutzer Teilhaber an
	 * diesem Kontakt ist. Er kann nur die Ausprägungen, die ihm geteilt wurden
	 * bearbeiten oder weiterteilen. Außerdem kann er nur eine Teilhaberschaft
	 * löschen, wenn er eine Ausprägung geteilt hat.
	 * 
	 * @param k
	 * @param teilhaberschaft
	 */
	public TeilhaberschaftForm(Kontakt k, String teilhaberschaft) {

		this.kon = k;
		ctf = new CellTableForm(kon, teilhaberschaft);
		einzTeilen.setVisible(false);

		RootPanel.get("Buttonbar").clear();
		RootPanel.get("Buttonbar").add(zurueck);
		RootPanel.get("Buttonbar").add(allTeilen);
		RootPanel.get("Buttonbar").add(einzTeilen);

	}

	/**
	 * Die onLoad()-Methode wird beim Starten der TeilhaberschaftForm geladen.
	 * Es wird eine neue CellTableForm mit dem übergebenen Kontakt erstellt, die
	 * alle benötigten Spalten beinhaltet. Diese CellTable wird ein ScrollPanel
	 * hinzugefügt. Außerdem werden drei Buttons mit den dazugehörigen
	 * ClickHandler Klassen angelegt und der Buttonbar hinzugefügt. Die
	 * verschiedenen HTML-Texte, sowie die CellTable werden am Ende dem Panel
	 * hinzugefügt.
	 */
	public void onLoad() {

		super.onLoad();
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));

		verwaltung.findAllNutzer(new AlleNutzer());
		nutzerSugBox.setStyleName("teilBox");
		teilen.setStyleName("teilLogo");
		selectionModelWrapper.addSelectionChangeHandler(new Handler());
		html1 = new HTML("<h2>Kontakt " + kon.getName() + " teilen</h2>");
		zurueck.addClickHandler(new ZurueckClickHandler());
		zurueck.getElement().appendChild(getBackPic.getElement());
		allTeilen.addClickHandler(new AllAuspraegungenTeilenClickHandler());
		allTeilen.getElement().appendChild(teilen2.getElement());
		einzTeilen.addClickHandler(new AusgewaehlteAuspraegungenTeilenClickHandler());
		einzTeilen.getElement().appendChild(teilen3.getElement());
		allLoeschen.addClickHandler(new AllAuspraegungenLoeschenClickHandler());
		allLoeschen.getElement().appendChild(deletePic.getElement());

		/*
		 * Hier wird der CellTable eine Selection Model für die Auswahl von
		 * mehreren Ausprägungen angelegt. Außerdem werden der Tabelle die
		 * benötigten Spalten über die Methoden der Klasse CellTableForm
		 * hinzugefügt. Am Ende wird die Tabelle einem scrollbaren-Panel
		 * hinzugefügt.
		 */
		ctf.setSelectionModel(selectionModelWrapper,
				DefaultSelectionEventManager.<EigenschaftAuspraegungWrapper>createCheckboxManager());
		ctf.addColumn(ctf.getCheckBox());
		ctf.addColumn(ctf.getBezEigenschaft(), "Kontakteigenschaften: ");
		ctf.addColumn(ctf.getWertAuspraegung());
		ctf.addColumn(ctf.getStatus());
		ctf.addColumn(ctf.getDeleteBtn(), "Teilhaberschaft löschen");
		ctf.setStyleName("CellTableAuspraegung");
		ctf.setStyleName("CellTableTeilhaberschaft");
		ctf.getDeleteBtn().setFieldUpdater(new DeleteFieldUpdater());
		scrollPanel.add(ctf);
		nutzerSugBox.getElement().setAttribute("placeholder", "Nutzer");
		nutzerSugBox.setStyleName("DropDown");
		nutzerSugBox.setHeight("20px");
		kontaktbild.setStyleName("Kontaktbild");
		teilen2.setStyleName("ButtonICON");
		teilen3.setStyleName("ButtonICON");
		getBackPic.setStyleName("ButtonICON");
		deletePic.setStyleName("ButtonICON");
		html1.setStyleName("HtmlText1");
		html2.setStyleName("HtmlText2");
		html3.setStyleName("HtmlText");

		/*
		 * Hier werden die zuvor angelegten Widgets dem VerticalPanel und dem
		 * HorizontelPanel hinzugefügt
		 */
		hpanelTop.setStyleName("HpanelTop");
		hpanelTop.add(kontaktbild);
		hpanelTop.add(html1);
		hpanelTop.add(teilen);

		vpanel.add(hpanelTop);
		vpanel.add(html2);
		nutzerSuchenPanel.setStyleName("nutzerSuchenPanel");
		nutzerSuchenPanel.add(html3);
		nutzerSuchenPanel.add(nutzerSugBox);
		vpanel.add(nutzerSuchenPanel);
		vpanel.add(scrollPanel);

		this.add(vpanel);

	}

	/*
	 * Nasted AsyncCallback - Classes, Click/Selection - Handler und
	 * FieldUpdater - Classes.
	 */

	/**
	 * 
	 * Dieses SelectionChangeEvent wird ausgeführt, wenn der Nutzer mind. eine
	 * Ausprägung, die er teilen möchte ausgewählt. Der Button 'Ausgewählte
	 * Eigenschaftsausprägungen teilen' wird in diesem Fall sichtbar gemacht.
	 *
	 */
	class Handler implements SelectionChangeEvent.Handler {

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			if (selectionModelWrapper.getSelectedSet().isEmpty()) {
				einzTeilen.setVisible(false);
			} else {
				einzTeilen.setVisible(true);
			}

		}

	}

	/**
	 * 
	 * Die Klasse DeleteFieldUpdater wird ausgeführt, wenn der Nutzer auf den
	 * deleteButton 'X' drückt. Dementsprechend wird die Dialogbox
	 * TeilhaberschaftVerwalten aktiviert.
	 *
	 */
	class DeleteFieldUpdater implements FieldUpdater<EigenschaftAuspraegungWrapper, String> {

		@Override
		public void update(int index, EigenschaftAuspraegungWrapper object, String value) {

			if (object.getAuspraegungStatus() == 0) {
				Window.alert("Es ist momentan keine Teilhaberschaft vorhanden");
			} else {
				DialogBoxTeilhaberschaftVerwalten dtl = new DialogBoxTeilhaberschaftVerwalten(object, kon);
				dtl.center();
			}

		}

	}

	/**
	 * 
	 * ClickHandler Klasse mit der alle Teilhaberschaften von allen Ausprägungen
	 * eines Kontakts entfernt werden. Dafür werden das Kontakt-Objekt und das
	 * Nutzer-Objekt übergeben.
	 *
	 */
	class AllAuspraegungenLoeschenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			boolean sureDeleteAllTeil = Window
					.confirm("Möchten Sie alle Teilhaberschaften an " + kon.getName() + " auflösen ?");

			if (sureDeleteAllTeil) {
				verwaltung.deleteAllTeilhaberschaftenKontakt(kon, nutzer, new AlleTeilhaberschaftenEntferntCallback());
			}

		}

	}

	/**
	 * Callback - Klasse
	 * 
	 * @author Sebi_0107
	 *
	 */
	class AlleTeilhaberschaftenEntferntCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Die Teilhaberschaften an diesem Kontakt konnten nicht gelöscht werden!");

		}

		@Override
		public void onSuccess(Void result) {
			Window.alert("Sie haben alle Teilhaberschaften an diesem Kontakt gelöscht.");
			kon.setStatus(0);
			TeilhaberschaftForm tf = new TeilhaberschaftForm(kon);
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(tf);

		}

	}

	/**
	 * 
	 * Diese Klasse wird beim Laden der onLoad()-Methode ausgeführt, um alle
	 * Nutzer, die auf dem System angemeldet sind, zu suchen. Alle Nutzer die
	 * auf dem System sind werden dann dem NutzerOracle hinzugefügt, damit sie
	 * über die Eingabe in die Suggest gefunden werden können.
	 *
	 */
	class AlleNutzer implements AsyncCallback<Vector<Nutzer>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Die Nutzer konnten leider nicht geladen werden");

		}

		@Override
		public void onSuccess(Vector<Nutzer> result) {

			for (Nutzer n : result) {
				if (n.getID() != nutzer.getID()) {
					nutzerOracle.add(n.getEmail());
				} else {

				}
			}

		}

	}

	/**
	 * 
	 * Diese ClickHandler-Klasse wird ausgeführt wenn der Nutzer auf den
	 * 'zurück' - Button klickt. Dadurch kommt der angemeldete Nutzer auf die
	 * KontaktForm zurück. Es wird geprüft, ob der angemeldete Nutzer der
	 * Eigentümer oder Teilhaber des Kontakts ist, dementsprechend werden die
	 * Werte an den Konstruktur weitergegeben.
	 *
	 */
	class ZurueckClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (kon.getNutzerID() == nutzer.getID()) {
				KontaktForm kf = new KontaktForm(kon);
				RootPanel.get("Details").clear();
				RootPanel.get("Buttonbar").clear();
				RootPanel.get("Details").add(kf);
			} else {
				KontaktForm kf = new KontaktForm(kon, "Teilhaberschaft");
				RootPanel.get("Details").clear();
				RootPanel.get("Buttonbar").clear();
				RootPanel.get("Details").add(kf);
			}

		}

	}

	/**
	 * 
	 * Diese ClickHandler-Klasse wird ausgeführt wenn der Nutzer auf den 'Alle
	 * Eigenschaftsausprägung teilen' - Button klickt. Hier findet der Aufruf
	 * der Methode insertTeilhaberschaftAuspraegungenKontakt statt. Der Methode
	 * werden der Kontakt, der ausgewählte Nutzer und die eigene NutzerID
	 * übergeben
	 *
	 */
	class AllAuspraegungenTeilenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (nutzerSugBox.getText().isEmpty()) {
				Window.alert("Sie haben keinen Nutzer ausgewählt");
			} else {
				verwaltung.insertTeilhaberschaftAuspraegungenKontakt(kon, nutzerSugBox.getValue(), nutzer.getID(),
						new TeilhaberschaftCallback());
			}
		}

	}

	/**
	 * 
	 * Diese ClickHandler-Klasse wird ausgeführt wenn der Nutzer auf den
	 * 'Ausgewählte Eigenschaftsausprägungen teilen' - Button klickt. Hier
	 * findet der Aufruf der Methode
	 * insertTeilhaberschaftAusgewaehlteAuspraegungenKontakt statt. Der Methode
	 * werden der Kontakt, ein Vector mit allen EigenschaftAuspraegung, der
	 * ausgewählte Nutzer und die eigene NutzerID übergeben.
	 *
	 */
	class AusgewaehlteAuspraegungenTeilenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			Vector<EigenschaftAuspraegungWrapper> ea = new Vector<EigenschaftAuspraegungWrapper>();
			for (EigenschaftAuspraegungWrapper eaw : selectionModelWrapper.getSelectedSet()) {
				ea.add(eaw);
			}

			if (nutzerSugBox.getText().isEmpty()) {
				Window.alert("Sie haben keinen Nutzer ausgewählt");
			} else {
				verwaltung.insertTeilhaberschaftAusgewaehlteAuspraegungenKontakt(kon, ea, nutzerSugBox.getValue(),
						nutzer.getID(), new TeilhaberschaftCallback());
			}

		}

	}

	/**
	 * 
	 * Die AsyncCallback-Klasse wird aufgerufen, sobald einer der beiden
	 * Methoden insertTeilhaberschaftAusgewaehlteAuspraegungenKontakt/
	 * insertTeilhaberschaftAuspraegungenKontakt erfolgreich ausgeführt wurden.
	 * Es wird geprüft, ob bereits eine Teilhaberschaft besteht oder nicht,
	 * dementsprechend werden die Window.alert - Ausgaben ausgeführt die
	 * TeilhaberschaftForm wird neu geladen
	 *
	 */
	class TeilhaberschaftCallback implements AsyncCallback<Integer> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Die Teilhaberschaft konnte nicht angelegt werden");

		}

		@Override
		public void onSuccess(Integer result) {

			if (result == 1) {
				Window.alert("Die Teilhaberschaft wurde erfolgreich bei dem Nutzer: " + nutzerSugBox.getValue()
						+ " angelegt");
				if (kon.getNutzerID() == nutzer.getID()) {
					kon.setStatus(1);
					TeilhaberschaftForm tf = new TeilhaberschaftForm(kon);
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(tf);
				} else {
					TeilhaberschaftForm tf = new TeilhaberschaftForm(kon, "Teilhaberschaft");
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(tf);
				}
			} else {
				Window.alert("Mit dem Nutzer " + nutzerSugBox.getValue()
						+ " besteht bereits eine Teilhaberschaft für den Kontakt " + kon.getName() + " .");
				if (kon.getNutzerID() == nutzer.getID()) {
					TeilhaberschaftForm tf = new TeilhaberschaftForm(kon);
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(tf);
				} else {
					TeilhaberschaftForm tf = new TeilhaberschaftForm(kon, "Teilhaberschaft");
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(tf);
				}
			}

		}

	}

}
