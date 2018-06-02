package de.hdm.itprojektgruppe4.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;

public class TeilhaberschaftForm extends VerticalPanel {

	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();

	Kontakt kon = new Kontakt();
	Kontaktliste konList = new Kontaktliste();
	Kontaktliste selectedKontaktlist = null;
	Nutzer nutzer = new Nutzer();

	private VerticalPanel vpanel = new VerticalPanel();
	private HorizontalPanel hpanel = new HorizontalPanel();

	private Button teilen = new Button("teilen");
	private Button allTeilen = new Button("Alle Eigenschaftsausprägung teilen");
	private Button einzTeilen = new Button("Einzelne Eigenschaftsausprägungen auswählen");

	private ListBox dropBoxNutzer = new ListBox();

	private CellTable<Eigenschaftauspraegung> cellAus = new CellTable<Eigenschaftauspraegung>();
	private CellTable<Eigenschaft> cellEig = new CellTable<Eigenschaft>();

	private HTML html2 = new HTML("Bitte wählen Sie hier den <b> Nutzer </b> aus dem der "
			+ " <b>Kontakt</b>  geteilt werden soll." + "<span style='font-family:fixed'></span>", true);

	final SingleSelectionModel<Nutzer> selectionModel = new SingleSelectionModel<Nutzer>();
	final SelectionModel<Eigenschaftauspraegung> selectionModelAus = new MultiSelectionModel<Eigenschaftauspraegung>();

	public TeilhaberschaftForm(Kontakt k) {
		this.kon = k;
	}

	public void onLoad() {

		super.onLoad();

		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));

		verwaltung.findAllNutzer(new AlleNutzer());
		verwaltung.getAuspraegungByKontaktID(kon.getID(), new AlleAuspraegungenFromNutzer());
		verwaltung.getEigenschaftbyKontaktID(kon.getID(), new AlleEigenschaftFromKontakt());

		HTML html1 = new HTML("<h2>" + kon.getName() + "</h2>");

		cellAus.setSelectionModel(selectionModelAus,
				DefaultSelectionEventManager.<Eigenschaftauspraegung>createCheckboxManager());

		Column<Eigenschaftauspraegung, Boolean> checkBox = new Column<Eigenschaftauspraegung, Boolean>(
				new CheckboxCell(true, false)) {

			@Override
			public Boolean getValue(Eigenschaftauspraegung object) {
				return selectionModelAus.isSelected(object);
			}

		};

		Column<Eigenschaftauspraegung, String> ausp = new Column<Eigenschaftauspraegung, String>(
				new ClickableTextCell()) {

			@Override
			public String getValue(Eigenschaftauspraegung object) {
				return object.getWert();
			}

		};

		Column<Eigenschaft, String> eig = new Column<Eigenschaft, String>(new ClickableTextCell()) {

			@Override
			public String getValue(Eigenschaft object) {
				return object.getBezeichnung();
			}

		};

		cellAus.setStyleName("CellTableAuspraegung");
		cellAus.setWidth("500px");
		cellAus.addColumn(ausp, "Wert");
		cellAus.addColumn(checkBox);

		cellEig.addColumn(eig, "Eigenschaft");

		hpanel.add(cellEig);
		hpanel.add(cellAus);
		vpanel.add(html1);
		vpanel.add(hpanel);
		vpanel.add(html2);
		vpanel.add(dropBoxNutzer);

		teilen.setStyleName("TeilhaberschaftButton");
		allTeilen.setStyleName("TeilhaberschaftButton");
		einzTeilen.setStyleName("TeilhaberschaftButton");

		RootPanel.get("Buttonbar").clear();
		RootPanel.get("Buttonbar").add(teilen);
		RootPanel.get("Buttonbar").add(allTeilen);
		RootPanel.get("Buttonbar").add(einzTeilen);
		this.add(vpanel);

	}

	class AlleNutzer implements AsyncCallback<Vector<Nutzer>> {

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Vector<Nutzer> result) {

			for (Nutzer n : result) {
				dropBoxNutzer.addItem(n.getEmail());
			}

		}

	}

	class AlleEigenschaftFromKontakt implements AsyncCallback<Vector<Eigenschaft>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Die Eigenschaften konnten leider nicht geladen werden");

		}

		@Override
		public void onSuccess(Vector<Eigenschaft> result) {
			cellEig.setRowCount(result.size(), true);
			cellEig.setRowData(0, result);
		}

	}

	class AlleAuspraegungenFromNutzer implements AsyncCallback<Vector<Eigenschaftauspraegung>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Die Ausprägungen konnten leider nicht geladen werden");

		}

		@Override
		public void onSuccess(Vector<Eigenschaftauspraegung> result) {

			cellAus.setRowCount(result.size(), true);
			cellAus.setRowData(0, result);

		}

	}

}
