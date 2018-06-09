package de.hdm.itprojektgruppe4.client.gui;

import java.util.Set;
import java.util.Vector;
import java.util.logging.Handler;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.PromptHandler;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
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
import de.hdm.itprojektgruppe4.client.EigenschaftAuspraegungWrapper;
import de.hdm.itprojektgruppe4.client.NavigationTree;
import de.hdm.itprojektgruppe4.client.gui.DialogBoxKontaktZuKontaktliste.KontaktHinzufuegen;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.bo.Teilhaberschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;

public class TeilhaberschaftForm extends VerticalPanel {

	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();

	Kontakt kon = new Kontakt();
	Kontaktliste konList = new Kontaktliste();
	Kontaktliste selectedKontaktlist = null;
	Nutzer nutzer = new Nutzer();
	Nutzer teilNutzer = new Nutzer();

	private VerticalPanel vpanel = new VerticalPanel();
	private HorizontalPanel hpanel = new HorizontalPanel();

	private Vector<Eigenschaftauspraegung> vecAus = new Vector<Eigenschaftauspraegung>();
	private Vector<Eigenschaft> vecEig = new Vector<Eigenschaft>();
	private Vector<EigenschaftAuspraegungWrapper> eigAus = new Vector<EigenschaftAuspraegungWrapper>();

	private Button teilen = new Button("teilen");
	private Button allTeilen = new Button("Alle Eigenschaftsausprägung teilen");
	private Button einzTeilen = new Button("Einzelne Eigenschaftsausprägungen auswählen");

	private ListBox dropBoxNutzer = new ListBox();

	private CheckBox cb = new CheckBox();

	private CellTableForm ctf = null;

	private HTML html2 = new HTML("Bitte wählen Sie hier den <b> Nutzer </b> aus dem der "
			+ " <b>Kontakt</b>  geteilt werden soll." + "<span style='font-family:fixed'></span>", true);

	final SingleSelectionModel<Nutzer> selectionModel = new SingleSelectionModel<Nutzer>();
	final MultiSelectionModel<EigenschaftAuspraegungWrapper> selectionModelWrapper = new MultiSelectionModel<EigenschaftAuspraegungWrapper>();

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

		ctf = new CellTableForm(kon);

		HTML html1 = new HTML("<h2>" + kon.getName() + "</h2>");

		ctf.setSelectionModel(selectionModelWrapper,
				DefaultSelectionEventManager.<EigenschaftAuspraegungWrapper>createCheckboxManager());

		Column<EigenschaftAuspraegungWrapper, String> bezEigenschaft = new Column<EigenschaftAuspraegungWrapper, String>(
				new ClickableTextCell()) {

			@Override
			public String getValue(EigenschaftAuspraegungWrapper object) {
				return object.getEigenschaftValue();
			}
		};

		Column<EigenschaftAuspraegungWrapper, String> wertAuspraegung = new Column<EigenschaftAuspraegungWrapper, String>(
				new ClickableTextCell()) {

			@Override
			public String getValue(EigenschaftAuspraegungWrapper object) {
				return object.getAuspraegungValue();
			}
		};

		final Column<EigenschaftAuspraegungWrapper, Boolean> checkBox = new Column<EigenschaftAuspraegungWrapper, Boolean>(
				new CheckboxCell(true, false)) {

			@Override
			public Boolean getValue(EigenschaftAuspraegungWrapper object) {

//				if (selectionModelWrapper.isSelected(object) == false) {
//					Window.alert(object.getAuspraegungValue());
//				}
				return selectionModelWrapper.isSelected(object);

			}
		};

		allTeilen.addClickHandler(new AllAuspraegungenTeilenClickHandler());
		teilen.addClickHandler(new MancheAuspraegungenTeilenClickHandler());

		ctf.setStyleName("CellTableAuspraegung");
		ctf.setWidth("500px");
		ctf.addColumn(bezEigenschaft, "Eigenschaft");
		ctf.addColumn(wertAuspraegung, "Wert");
		ctf.addColumn(checkBox);

		hpanel.add(ctf);
		vpanel.add(html1);
		vpanel.add(hpanel);
		vpanel.add(html2);
		vpanel.add(dropBoxNutzer);

		RootPanel.get("Buttonbar").clear();
		RootPanel.get("Buttonbar").add(teilen);
		RootPanel.get("Buttonbar").add(allTeilen);
		RootPanel.get("Buttonbar").add(einzTeilen);
		this.add(vpanel);

	}
	
	private void auspraegungenTeilen(EigenschaftAuspraegungWrapper ea, Nutzer n){
		Window.alert(n.getEmail());
		verwaltung.insertTeilhaberschaftKontakt(kon.getID(), ea.getAuspraegungID(), n.getID(), nutzer.getID(), new TeilhaberschaftAuspraegung());
	}

	class AlleNutzer implements AsyncCallback<Vector<Nutzer>> {

		@Override
		public void onFailure(Throwable caught) {

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

	class AlleEigenschaftFromKontakt implements AsyncCallback<Vector<Eigenschaft>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Die Eigenschaften konnten leider nicht geladen werden");

		}

		@Override
		public void onSuccess(Vector<Eigenschaft> result) {
			vecEig = result;
		}

	}

	class AlleAuspraegungenFromNutzer implements AsyncCallback<Vector<Eigenschaftauspraegung>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Die Ausprägungen konnten leider nicht geladen werden");

		}

		@Override
		public void onSuccess(Vector<Eigenschaftauspraegung> result) {
			vecAus = result;

		}

	}

	class NutzerIDFromEmail implements AsyncCallback<Nutzer> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Der Nutzer konnte leider nicht gefunden werden");

		}

		@Override
		public void onSuccess(Nutzer result) {
			teilNutzer = result;
			Window.alert("nutzerid : " + teilNutzer.getID());

			for (Eigenschaftauspraegung ea : vecAus) {

				verwaltung.insertTeilhaberschaftKontakt(kon.getID(), ea.getID(), teilNutzer.getID(), nutzer.getID(),
						new TeilhaberschaftAll());

			}
		}

	}
	
	class GetNutzerFromEmail implements AsyncCallback<Nutzer> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Der Nutzer konnte leider nicht gefunden werden");
			
		}

		@Override
		public void onSuccess(Nutzer result) {
			Window.alert(result.getEmail());
			teilNutzer = result;
			
		}
		
	}

	class TeilhaberschaftAll implements AsyncCallback<Teilhaberschaft> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Die Teilhaberschaft konnte nicht angelegt werden");

		}

		@Override
		public void onSuccess(Teilhaberschaft result) {
			Window.alert("Die Teilhaberschaft wurde angelegt");

		}

	}

	class AllAuspraegungenTeilenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Window.alert(dropBoxNutzer.getSelectedValue());
			verwaltung.findNutzerByEmail(dropBoxNutzer.getSelectedValue(), new NutzerIDFromEmail());

		}

	}

	class MancheAuspraegungenTeilenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			verwaltung.findNutzerByEmail(dropBoxNutzer.getSelectedValue(), new GetNutzerFromEmail());
			if (selectionModelWrapper.getSelectedSet().isEmpty()) {
				Window.alert("Sie müssen mindestens eine Ausprägung auswählen");
			} else {
				for (EigenschaftAuspraegungWrapper eaw : selectionModelWrapper.getSelectedSet()) {
					Window.alert(" " + eaw.getAuspraegungValue());
					auspraegungenTeilen(eaw, teilNutzer);
					
				}

			}
		}

	}
	
	class TeilhaberschaftAuspraegung implements AsyncCallback<Teilhaberschaft>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Die Teilhaberschaft konnte nicht angelegt werden");
			
		}

		@Override
		public void onSuccess(Teilhaberschaft result) {
			Window.alert("Die Teilhaberschaft wurde angelegt");
			
		}
		
	}

}
