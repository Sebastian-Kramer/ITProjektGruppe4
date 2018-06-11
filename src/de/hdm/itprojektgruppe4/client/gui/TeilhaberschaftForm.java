package de.hdm.itprojektgruppe4.client.gui;

import java.net.URL;
import java.util.Vector;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
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
import com.google.gwt.view.client.SingleSelectionModel;
import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.client.EigenschaftAuspraegungWrapper;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.KontaktKontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.bo.Teilhaberschaft;

public class TeilhaberschaftForm extends VerticalPanel {

	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();

	Kontakt kon = new Kontakt();
	Kontaktliste konList = new Kontaktliste();
	Kontaktliste selectedKontaktlist = null;
	Nutzer nutzer = new Nutzer();
	Nutzer teilNutzer = new Nutzer();

	private VerticalPanel vpanel = new VerticalPanel();
	private HorizontalPanel hpanel = new HorizontalPanel();

	private Button zurueck = new Button("Zurück");
	private Button allTeilen = new Button("Alle Eigenschaftsausprägung teilen");
	private Button einzTeilen = new Button("Ausgewählte Eigenschaftsausprägungen teilen");

	private ListBox dropBoxNutzer = new ListBox();

	private CellTableForm ctf = null;

	private ImageCell imageCell = new ImageCell();

	private HTML html2 = new HTML("Sie können auch nur bestimmte Ausprägungen an einen anderen <b> Nutzer </b> teilen, "
			+ " </br> wählen Sie dafür die entsprechenden <b> Ausprägungen </b>  mit den Checkboxen aus."
			+ "<span style='font-family:fixed'></span>", true);

	private HTML html3 = new HTML("Bitte wählen Sie hier den <b> Nutzer </b> aus dem der "
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

		Column<EigenschaftAuspraegungWrapper, String> status = new Column<EigenschaftAuspraegungWrapper, String>(
				imageCell) {

			@Override
			public String getValue(EigenschaftAuspraegungWrapper object) {

				// Window.alert(" " + object.getAuspraegungStatus());
				// if (object.getAuspraegungStatus() == 0){
				// return " ";
				// }else{
				// return
				// "/itprojektgruppe4/src/de/hdm/itprojektgruppe4/client/gui/contacts.png";
				// }
				return object.getImageUrl(object);

			}

		};

		Column<EigenschaftAuspraegungWrapper, Boolean> checkBox = new Column<EigenschaftAuspraegungWrapper, Boolean>(
				new CheckboxCell(true, false)) {

			@Override
			public Boolean getValue(EigenschaftAuspraegungWrapper object) {

				if (selectionModelWrapper.getSelectedSet().isEmpty()) {
					einzTeilen.setVisible(false);
				} else {
					einzTeilen.setVisible(true);
				}
				return selectionModelWrapper.isSelected(object);

			}
		};

		// imageCell.getClass().getResource("/war/itprojektss18/contacts.png");
		dropBoxNutzer.setStyleName("DropDown");
		html1.setStyleName("HtmlText1");
		html2.setStyleName("HtmlText");
		html3.setStyleName("HtmlText");
		vpanel.setStyleName("TeilhaberschaftVPanel");
		status.setCellStyleNames("ImageCell");

		zurueck.addClickHandler(new ZurueckClickHandler());
		allTeilen.addClickHandler(new AllAuspraegungenTeilenClickHandler());
		einzTeilen.addClickHandler(new MancheAuspraegungenTeilenClickHandler());

		ctf.setStyleName("CellTableAuspraegung");
		ctf.setWidth("500px");
		ctf.addColumn(bezEigenschaft, "Eigenschaft");
		ctf.addColumn(wertAuspraegung, "Wert");
		ctf.addColumn(status, "Status");
		ctf.addColumn(checkBox);

		hpanel.add(ctf);
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

	class MancheAuspraegungenTeilenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			findNutzerByEmail(dropBoxNutzer.getSelectedValue());

		}

	}

	private void findNutzerByEmail(String email) {

		verwaltung.findNutzerByEmail(email, new GetNutzerFromEmail());
	}

	class GetNutzerFromEmail implements AsyncCallback<Nutzer> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Der Nutzer konnte leider nicht gefunden werden");

		}

		@Override
		public void onSuccess(Nutzer result) {
			teilNutzer.setID(result.getID());
			teilNutzer.setEmail(result.getEmail());
			TeilhaberschaftForm tf = new TeilhaberschaftForm(kon);
			EigenschaftAuspraegungWrapper ea = new EigenschaftAuspraegungWrapper();

			if (selectionModelWrapper.getSelectedSet().isEmpty()) {
				Window.alert("Sie müssen mindestens eine Ausprägung auswählen");
			} else {

				for (EigenschaftAuspraegungWrapper eaw : selectionModelWrapper.getSelectedSet()) {
					ea = eaw;
					Window.alert("Die Eigenschaftsausprägung " + ea.getAuspraegungValue() + " wurde an den Nutzer "
							+ teilNutzer.getEmail() + " geteilt");
					auspraegungenTeilen(eaw);

				}
				Window.alert("Die Teilhaberschaft wurde angelegt");
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(tf);
			}

		}

	}

	private void auspraegungenTeilen(EigenschaftAuspraegungWrapper ea) {
		verwaltung.insertTeilhaberschaftKontakt(kon.getID(), ea.getAuspraegungID(), teilNutzer.getID(), nutzer.getID(),
				new TeilhaberschaftAuspraegung());
	}

	class TeilhaberschaftAuspraegung implements AsyncCallback<Teilhaberschaft> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Die Teilhaberschaft konnte nicht angelegt werden");

		}

		@Override
		public void onSuccess(Teilhaberschaft result) {
			verwaltung.findKontaktlisteByNutzerID(teilNutzer.getID(), new KontaktListe());
		}

	}

	class AllAuspraegungenTeilenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			verwaltung.findNutzerByEmail(dropBoxNutzer.getSelectedValue(), new TeilhaberschaftAllerAuspraegungen());

		}

	}

	class TeilhaberschaftAllerAuspraegungen implements AsyncCallback<Nutzer> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Der Nutzer konnte leider nicht gefunden werden");

		}

		@Override
		public void onSuccess(Nutzer result) {
			teilNutzer.setID(result.getID());
			teilNutzer.setEmail(result.getEmail());

			verwaltung.getAuspraegungByKontaktID(kon.getID(), new AllAuspraegungenFromKontakt());

		}

	}

	class AllAuspraegungenFromKontakt implements AsyncCallback<Vector<Eigenschaftauspraegung>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Die Ausprägungen konnten leider nicht gefunden werden");

		}

		@Override
		public void onSuccess(Vector<Eigenschaftauspraegung> result) {
			for (Eigenschaftauspraegung e : result) {
				Window.alert(e.getWert() + " wurde geteilt");
				verwaltung.insertTeilhaberschaftKontakt(kon.getID(), e.getID(), teilNutzer.getID(), nutzer.getID(),
						new TeilhaberschaftAuspraegungAll());
			}
			verwaltung.findKontaktlisteByNutzerID(teilNutzer.getID(), new KontaktListe());
		}

	}

	class TeilhaberschaftAuspraegungAll implements AsyncCallback<Teilhaberschaft> {

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Teilhaberschaft result) {

		}

	}

	class KontaktListe implements AsyncCallback<Vector<Kontaktliste>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Die Kontaktliste konnte nicht geladen werden");

		}

		@Override
		public void onSuccess(Vector<Kontaktliste> result) {
			for (Kontaktliste k : result) {

				if (k.getBez() == "Meine geteilten Kontakte") {
					verwaltung.insertKontaktKontaktliste(teilNutzer.getID(), k.getID(), new ListEintrag());
				} else {
				}
			}
			kon.setStatus(1);
			verwaltung.updateKontakt(kon, new KontaktStatus());

		}

	}

	class KontaktStatus implements AsyncCallback<Kontakt> {

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Kontakt result) {

		}

	}

	class ListEintrag implements AsyncCallback<KontaktKontaktliste> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Der Kontakt konnte nicht der Kontaktliste hinzugefügt werden");

		}

		@Override
		public void onSuccess(KontaktKontaktliste result) {
			Window.alert("Der geteilte Kontakt " + kon.getName() + " wurde beim Nutzer " + teilNutzer.getEmail()
					+ " in die Kontaktliste 'Meine geteilten Kontakte' hinzugefügt");

		}

	}

}
