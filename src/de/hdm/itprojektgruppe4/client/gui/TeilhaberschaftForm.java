package de.hdm.itprojektgruppe4.client.gui;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.client.EigenschaftAuspraegungWrapper;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.bo.Teilhaberschaft;

/**
 * Mit der Klasse TeilhaberschaftForm lassen sich die Teilhaberschaften aller
 * Ausprägungnen eines Kontakt verwalten. Dazu gehört das Anlegen und Löschen
 * von Teilhaberschaften Die Ausprägungen eines Kontakts können komplett oder
 * nur teilweise an einen anderen, im System angemeldeten Nutzer geteilt werden
 * Außerdem kann der Eigentümer der Teilhaberschaft oder der teilhabende Nutzer
 * die Teilhabschaft entfernen
 * 
 * @author Sebi_0107
 *
 */
public class TeilhaberschaftForm extends VerticalPanel {

	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();

	private Kontakt kon = new Kontakt();
	private Nutzer nutzer = new Nutzer();

	private VerticalPanel vpanel = new VerticalPanel();
	private HorizontalPanel hpanel = new HorizontalPanel();

	private Button zurueck = new Button("Zurück");
	private Button allTeilen = new Button("Alle Eigenschaftsausprägung teilen");
	private Button einzTeilen = new Button("Ausgewählte Eigenschaftsausprägungen teilen");

	private ListBox dropBoxNutzer = new ListBox();

	private CellTableForm ctf = null;
	
	private ScrollPanel scrollPanel = new ScrollPanel();

	private Date date = new Date();

	DateTimeFormat fmt = DateTimeFormat.getFormat("dd.MM.yyyy");

	private HTML html2 = new HTML("Sie können auch nur bestimmte <b> Ausprägungen </b> an einen anderen <b> Nutzer </b> teilen, "
			+ " </br> wählen Sie dafür die entsprechenden <b> Ausprägungen </b>  mit den Checkboxen aus."
			+ "<span style='font-family:fixed'></span>", true);

	private HTML html3 = new HTML("Bitte wählen Sie hier den <b> Nutzer </b> aus dem der "
			+ " <b>Kontakt</b>  geteilt werden soll." + "<span style='font-family:fixed'></span>", true);

	final SingleSelectionModel<Nutzer> selectionModel = new SingleSelectionModel<Nutzer>();
	final MultiSelectionModel<EigenschaftAuspraegungWrapper> selectionModelWrapper = new MultiSelectionModel<EigenschaftAuspraegungWrapper>();

	public TeilhaberschaftForm(Kontakt k) {

		this.kon = k;

		fmt.format(date);

	}

	public void onLoad() {

		super.onLoad();

		HTML html1 = new HTML("<h2>" + kon.getName() + "</h2>");
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));

		verwaltung.findAllNutzer(new AlleNutzer());

		ctf = new CellTableForm(kon);
		ctf.setSelectionModel(selectionModelWrapper,
				DefaultSelectionEventManager.<EigenschaftAuspraegungWrapper>createCheckboxManager());

		selectionModelWrapper.addSelectionChangeHandler(new Handler());

		zurueck.addClickHandler(new ZurueckClickHandler());
		allTeilen.addClickHandler(new AllAuspraegungenTeilenClickHandler());
		einzTeilen.addClickHandler(new AusgewaehlteAuspraegungenTeilenClickHandler());

		ctf.addColumn(ctf.getWertAuspraegung(), "Eigenschaft");
		ctf.addColumn(ctf.getBezEigenschaft(), "Wert");
		ctf.addColumn(ctf.getStatus(), "Status");
		ctf.addColumn(ctf.getCheckBox());
		ctf.addColumn(ctf.getDeleteBtn(), "Teilhaberschaft löschen");
		ctf.setStyleName("CellTableAuspraegung");
		ctf.setWidth("700px");
		ctf.getDeleteBtn().setFieldUpdater(new DeleteFieldUpdater());
		scrollPanel.add(ctf);
		scrollPanel.setHeight("300px");

		dropBoxNutzer.setStyleName("DropDown");
		html1.setStyleName("HtmlText1");
		html2.setStyleName("HtmlText");
		html3.setStyleName("HtmlText");
		vpanel.setStyleName("TeilhaberschaftVPanel");

		hpanel.add(scrollPanel);
		vpanel.add(html1);
		vpanel.add(html2);
		vpanel.add(hpanel);
		vpanel.add(html3);
		vpanel.add(dropBoxNutzer);

		RootPanel.get("Buttonbar").clear();
		RootPanel.get("Buttonbar").add(zurueck);
		RootPanel.get("Buttonbar").add(allTeilen);
		RootPanel.get("Buttonbar").add(einzTeilen);
		this.add(vpanel);

	}

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

	class AlleNutzer implements AsyncCallback<Vector<Nutzer>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Die Nutzer konnten leider nicht geladen werden");

		}

		@Override
		public void onSuccess(Vector<Nutzer> result) {

			for (Nutzer n : result) {
				if (n.getID() != nutzer.getID()) {
					dropBoxNutzer.addItem(n.getEmail());
				} else {

				}
			}

		}

	}

	class ZurueckClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			KontaktForm kf = new KontaktForm(kon);
			RootPanel.get("Details").clear();
			RootPanel.get("Buttonbar").clear();
			RootPanel.get("Details").add(kf);

		}

	}

	class AllAuspraegungenTeilenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			verwaltung.insertTeilhaberschaftAuspraegungenKontakt(kon, dropBoxNutzer.getSelectedValue(), nutzer.getID(),
					new TeilhaberschaftCallback());

		}

	}

	class TeilhaberschaftCallback implements AsyncCallback<Teilhaberschaft> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Die Teilhaberschaft konnte nicht angelegt werden");

		}

		@Override
		public void onSuccess(Teilhaberschaft result) {
			Window.alert("Alle Eigenschaften wurden erfolgreich an den Nutzer " + dropBoxNutzer.getSelectedValue()
					+ " geteilt");
			TeilhaberschaftForm tf = new TeilhaberschaftForm(kon);
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(tf);
		}

	}

	class AusgewaehlteAuspraegungenTeilenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			TeilhaberschaftForm tf = new TeilhaberschaftForm(kon);
			Vector<EigenschaftAuspraegungWrapper> ea = new Vector<EigenschaftAuspraegungWrapper>();
			for (EigenschaftAuspraegungWrapper eaw : selectionModelWrapper.getSelectedSet()) {
				ea.add(eaw);
			}
			verwaltung.insertTeilhaberschaftAusgewaehlteAuspraegungenKontakt(kon, ea, dropBoxNutzer.getSelectedValue(),
					nutzer.getID(), new TeilhaberschaftCallback());
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(tf);
		}

	}

}
