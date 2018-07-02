package de.hdm.itprojektgruppe4.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.client.EigenschaftAuspraegungWrapper;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;

/**
 * In der Klasse SuchenForm kann nach Kontakten oder Ausprägungen gesucht
 * werden. Die Suche nach Kontakten wird duch eine Suggestbox unterstützt. Es
 * werden Kontakte während der Eingabe vorgeschlagen. Die Suche ergibt eine
 * CellTable mit den gefundenen Kontakten. Wenn der jeweilige Kontakt angeklickt
 * wurde kann durch einen Button die Kontaktform des Kontaktes geöffnet werden.
 * Die Suche nach Ausprägungen enthält keine Suggestbox. Bei Klick auf die
 * Ausprägung kann durch einen Button der jeweilige Kontakt in seiner
 * Kontaktform aufgerufen werden.
 * 
 * @author Clirim
 *
 */

public class SuchenForm extends VerticalPanel {

	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();
	private Nutzer nutzer = new Nutzer();
	private Label beschreibungKontakt = new Label("Bitte Geben sie den Kontatknamen ein");
	private Label beschreibungAuspraegung = new Label("Bitte Geben sie eine Ausprägung ein");
	private HTML HTMLForm = new HTML("Sie können auf dieser Seite nach Kontakten anhand von ihrem Namen suchen, "
			+ "<br>oder indem Sie eine Eigenschaftsausprägung eingeben, welche der gesuchte Kontakt beinhaltet</br>");
	private HTML ueberschrift = new HTML("<h2>Kontakte Suchen und finden</h2>");
	private Image suchenIcon = new Image("Image/Suchen.png");
	private TextBox tboxKontaktname = new TextBox();
	private TextBox tboxAuspraegung = new TextBox();
	private Button kontaktSuchen = new Button("Kontakt suchen");
	private Button auspraegungSuchen = new Button("Auspraegung suchen");
	private Button kontaktKontaktAnzeigenButton = new Button("Kontakt anzeigen");
	private Button auspraegungKontaktAnzeigenButton = new Button("Zugehörigen Kontakt anzeigen");
	private VerticalPanel vpanel = new VerticalPanel();
	private VerticalPanel vpanelTop = new VerticalPanel(); 
	private HorizontalPanel hpanelRechts = new HorizontalPanel();
	private HorizontalPanel hpanelLinks = new HorizontalPanel();
	private HorizontalPanel hpanel = new HorizontalPanel();
	private FlexTable flextableKontakt = new FlexTable();
	private FlexTable flextableAuspraegung = new FlexTable();
	private DecoratorPanel KontaktSuchenPanel = new DecoratorPanel();
	private DecoratorPanel AuspraegungSuchenPanel = new DecoratorPanel();
	private VerticalPanel vpanel1 = new VerticalPanel();
	private VerticalPanel vpanel2 = new VerticalPanel();
	private VerticalPanel vpanel3 = new VerticalPanel();
	private MultiWordSuggestOracle KontaktOracle = new MultiWordSuggestOracle();
	private SuggestBox suggestionKontaktBox = new SuggestBox(KontaktOracle);
	private Image suchenKonPic = new Image("Image/Suchen.png");
	private Image suchenAusPic = new Image("Image/Suchen.png");
	
	final SingleSelectionModel<Kontakt> sm = new SingleSelectionModel<Kontakt>();
	final SingleSelectionModel<EigenschaftAuspraegungWrapper> ssm = new SingleSelectionModel<EigenschaftAuspraegungWrapper>();
	private CellTable<Kontakt> ctkontakt = new CellTable<Kontakt>();

	private CellTableForm ctAus = null;
	private ClickableTextCell bezeigenschaft = new ClickableTextCell();
	private ClickableTextCell wertauspraegung = new ClickableTextCell();
	private ClickableTextCell AusKontaktname = new ClickableTextCell();
	private CellTableForm.ColumnEigenschaft bezEigenschaft = ctAus.new ColumnEigenschaft(bezeigenschaft);
	private CellTableForm.ColumnAuspraegung wertAuspraegung = ctAus.new ColumnAuspraegung(wertauspraegung);
	private CellTableForm.ColumnKontaktName kontaktName = ctAus.new ColumnKontaktName(AusKontaktname);

	public SuchenForm() {
		hpanel.setStyleName("SuchenHpanelTop");
		hpanelRechts.setStyleName("hpanelRechts");
		hpanelLinks.setStyleName("hpanelLinks");
		vpanelTop.setStyleName("SuchenVpanelTop");
		ueberschrift.setStyleName("SuchenUeberschrift");
		HTMLForm.setStyleName("SuchenText");
		beschreibungKontakt.setStyleName("SuchenBeschreibenKontakt");
		beschreibungAuspraegung.setStyleName("SuchenBeschreibenAuspraegung");
		tboxKontaktname.setStyleName("SuchenBoxKontakt");
		tboxAuspraegung.setStyleName("SuchenBoxAuspraegung");
		kontaktSuchen.setHeight("60px");
		
	}

	/**
	 * Die onLoad()-Methode wird beim Starten der SuchenForm geladen. Es werden
	 * zwei CellTables erstellt. "ctAus" wird mit den Eigenschaften und
	 * Ausprägungen befüllt, während "ctKontakt" mit Kontaktobjekten befüllt
	 * wird. Des weiteren werden den Buttons die zugehörigen Clickhandler
	 * hinzugefügt und die verschiedenen Widgets den Panel hinzugefügt.
	 */

	public void onLoad() {

		super.onLoad();

	
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("mail"));

		/**
		 * Die Column (Spalte) "kontaktname" wird bei der Suche nach Kontakten
		 * mit Kontaktobjekten befüllt, welche durch das Selectionmodel
		 * anwählbar sind.
		 * 
		 * @author Clirim
		 *
		 */
		Column<Kontakt, String> kontaktname = new Column<Kontakt, String>(new ClickableTextCell()) {

			@Override
			public String getValue(Kontakt object) {
		
				return object.getName();
			}

		};

		ctAus = new CellTableForm();
		ctkontakt.addColumn(kontaktname, "Gefundene Kontakte");
		ctkontakt.setSelectionModel(sm);
		ctkontakt.setStyleName("TableKontakte"); 
		ctkontakt.setVisible(false);
		ctAus.setVisible(false);
		kontaktKontaktAnzeigenButton.setVisible(false);
		auspraegungKontaktAnzeigenButton.setVisible(false);

		flextableKontakt.setWidget(0, 0, beschreibungKontakt);
		flextableKontakt.setWidget(0, 1, suggestionKontaktBox);
		flextableKontakt.setWidget(1, 0, kontaktSuchen);
		KontaktSuchenPanel.add(flextableKontakt);

		flextableAuspraegung.setWidget(0, 0, beschreibungAuspraegung);
		flextableAuspraegung.setWidget(0, 1, tboxAuspraegung);
		flextableAuspraegung.setWidget(1, 0, auspraegungSuchen);
		AuspraegungSuchenPanel.add(flextableAuspraegung);

		KontaktSuchenPanel.setStyleName("DialogboxBackground");
		suggestionKontaktBox.setStyleName("DialogboxBackground");
		AuspraegungSuchenPanel.setStyleName("DialogboxBackground");
		tboxAuspraegung.setStyleName("DialogboxBackground");

		ctAus.setSelectionModel(ssm);
		ctAus.addColumn(bezEigenschaft, "Eig");
		ctAus.addColumn(wertAuspraegung, "Aus");
		ctAus.addColumn(kontaktName, "Zugehöriger Kontakt");
		ctAus.setStyleName("TableAuspraegung");
		suchenKonPic.setStyleName("ButtonICON");
		suchenAusPic.setStyleName("ButtonICON");
		
		hpanel.add(suchenIcon);
		hpanel.add(ueberschrift);
		vpanelTop.add(hpanel);	
		hpanelLinks.add(KontaktSuchenPanel);
		hpanelLinks.add(AuspraegungSuchenPanel);
		vpanel2.add(ctAus);
		vpanel1.add(ctkontakt);
		hpanelRechts.add(ctAus);

		hpanelRechts.add(auspraegungKontaktAnzeigenButton);
		vpanel3.add(kontaktKontaktAnzeigenButton);

		hpanelRechts.add(auspraegungKontaktAnzeigenButton);
		vpanel3.add(kontaktKontaktAnzeigenButton);

		this.add(vpanelTop);
		this.add(HTMLForm);
		this.add(hpanelLinks);
		this.add(vpanel);

		suggestionKontaktBox.getElement().setAttribute("placeholder", "Kontaktname");
		tboxAuspraegung.getElement().setAttribute("placeholder", "Ausprägung");
		kontaktSuchen.addClickHandler(new KontaktSuchenButton());
		kontaktSuchen.getElement().appendChild(suchenKonPic.getElement());
		auspraegungSuchen.addClickHandler(new AuspraegungSuchenButton());
		auspraegungSuchen.getElement().appendChild(suchenAusPic.getElement());

		this.add(vpanel1);
		this.add(vpanel2);
		this.add(vpanel3);
		this.add(hpanelRechts);

		kontaktKontaktAnzeigenButton.addClickHandler(new KontaktKontaktAnzeigenHandler());
		auspraegungKontaktAnzeigenButton.addClickHandler(new AuspraegungKontaktAnzeigenHandler());
		verwaltung.findAllKontaktFromNutzer(nutzer.getID(), new AllKontakteCallBack());
	}

	/**
	 * 
	 * @author Clirim
	 *
	 */

	/**
	 * Der ClickHandler bezieht sich auf den AuspraegungKontaktAnzeigenButton.
	 * Ein Wrapper-Objekt wird gesetzt mit der AusprägungID durch einen Klick in
	 * die CellTable mit dem SelectionModel. Mit dem AsyncCallback wird ein
	 * Kontakt in der Datenbank durch die übergebene AusprägungID gefunden.
	 * 
	 * @author Clirim
	 *
	 */

	class AuspraegungKontaktAnzeigenHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			
			auspraegungKontaktAnzeigenButton.setText("");
			kontaktKontaktAnzeigenButton.setVisible(false);
			EigenschaftAuspraegungWrapper eigaus = new EigenschaftAuspraegungWrapper();

			eigaus.setAuspraegungID(ssm.getSelectedObject().getAuspraegungID());

			verwaltung.findKontaktByAuspraegungID(eigaus.getAuspraegungID(), new AuspraegungKontaktAnzeigenCallback());
		}

	}

	/**
	 * In dem KlickHandler der sich auf den Button "Kontakt anzeigen" bezieht
	 * wird ein neues KontaktFormular erstellt mit dem (durch das selectionmodel
	 * ausgewählten Kontakt.
	 * 
	 * @author Clirim
	 *
	 */
	class KontaktKontaktAnzeigenHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			kontaktKontaktAnzeigenButton.setText("");
			Kontakt selected = sm.getSelectedObject();
			KontaktForm kf = new KontaktForm(selected);
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(kf);
		}

	}

	/**
	 * Der ClickHandler befüllt ein Kontaktobjekt mit dem String der in der
	 * Textbox eingegeben wird. Mit diesem Kontaktobjekt wird der AsyncCallback
	 * aufgerufen, welcher die folgende Kontaktform erzeugt.
	 * 
	 * @author Clirim
	 *
	 */
	class KontaktSuchenButton implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			Nutzer nutzer = new Nutzer();
			nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
			nutzer.setEmail(Cookies.getCookie("mail"));

			Kontakt k = new Kontakt();

			k.setName(tboxKontaktname.getValue());
			k.setNutzerID(nutzer.getID());

			k.setName(suggestionKontaktBox.getValue());
			k.setNutzerID(nutzer.getID());

			auspraegungKontaktAnzeigenButton.setVisible(false);
			kontaktKontaktAnzeigenButton.setVisible(true);

			verwaltung.findGesuchteKontakte(k, new FindKontaktCallback());

		}
	}

	/**
	 * In dem ClickHandler wird ein Wrapperobjekt mit der Eingabe der Textbox
	 * befüllt. Mit der Eingabe der Textbox wird der Callback aufgerufen, der
	 * Objekte für die Ausprägunng CellTable findet.
	 * 
	 * @author Clirim
	 */

	class AuspraegungSuchenButton implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			Nutzer nutzer = new Nutzer();
			nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
			nutzer.setEmail(Cookies.getCookie("mail"));

			EigenschaftAuspraegungWrapper eigaus = new EigenschaftAuspraegungWrapper();

			eigaus.setAuspraegungValue(tboxAuspraegung.getValue());

			kontaktKontaktAnzeigenButton.setVisible(false);
			auspraegungKontaktAnzeigenButton.setVisible(true);
			verwaltung.getAuspraegungByWert(tboxAuspraegung.getValue(), new FindAuspraegungCallback());

		}

	}

	/**
	 * Der Callback befüllt die CellTable mit Wrapper-Objekten.
	 * 
	 * @author Clirim
	 *
	 */
	class FindKontaktCallback implements AsyncCallback<Vector<Kontakt>> {

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Vector<Kontakt> result) {
			ctkontakt.setRowData(0, result);
			ctkontakt.setRowCount(result.size(), true);
			ctAus.setVisible(false);
			ctkontakt.setVisible(true);

		}

	}

	/**
	 * Der Callback erzeugt eine neue KontaktForm für den Kontakt, welcher durch
	 * die Ausprägungssuche angezeigt und danach durch das selectionmodel
	 * ausgewählt wurde.
	 * 
	 * @author Clirim
	 *
	 */
	class AuspraegungKontaktAnzeigenCallback implements AsyncCallback<Kontakt> {

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Kontakt result) {
			if(nutzer.getID() == result.getNutzerID()){
				KontaktForm kf = new KontaktForm(result);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(kf);
			}else{
				KontaktForm kf = new KontaktForm(result, "teilhaberschaft");
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(kf);
			}
			
		}

	}

	/**
	 * Der Callback befüllt die CellTable "ctAus" mit Wrapperobjekten (ein
	 * Vector), welche anschließend mit dem selectionmodel ausgewählt werden
	 * können.
	 * 
	 * @author Clirim
	 *
	 */

	class FindAuspraegungCallback implements AsyncCallback<Vector<EigenschaftAuspraegungWrapper>> {

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Vector<EigenschaftAuspraegungWrapper> result) {


			ctAus.setRowData(0, result);
			ctAus.setRowCount(result.size(), true);
			ctkontakt.setVisible(false);
			ctAus.setVisible(true);

		}
	}

	class AllKontakteCallBack implements AsyncCallback<Vector<Kontakt>> {

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(Vector<Kontakt> result) {

			for (Kontakt kontakt : result) {

				KontaktOracle.add(kontakt.getName());
			}

		}
	}

}
