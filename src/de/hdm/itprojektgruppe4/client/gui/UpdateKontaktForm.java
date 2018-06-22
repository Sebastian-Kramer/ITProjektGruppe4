package de.hdm.itprojektgruppe4.client.gui;

import java.util.Date;
import java.util.Vector;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.client.EigenschaftAuspraegungWrapper;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.bo.Teilhaberschaft;

public class UpdateKontaktForm extends VerticalPanel {

	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();

	FlexTable ft_KontaktBearbeiten = new FlexTable();

	private VerticalPanel vpanelDetails = new VerticalPanel();
	private HorizontalPanel hpanelDetails = new HorizontalPanel();
	private HorizontalPanel hpanelAdd = new HorizontalPanel();

	private Kontakt kon = new Kontakt();
	private Nutzer nutzer = new Nutzer();

	private Label lbl_KontaktName = new Label("Kontaktname: ");
	private TextBox txt_KontaktName = new TextBox();
	private Button addRow = new Button("Hinzufügen");
	private Label lbl_NewEigenschaft = new Label("Eigenschaft: ");
	private Label lbl_NewAuspraegung = new Label("Auspraegung: ");
	private TextBox txt_Auspraegung = new TextBox();
	private Date date = new Date();

	private KeyDownHandler changeNameHandler = new ChangeNameHandler();

	private ScrollPanel scrollPanel = new ScrollPanel();

	private CellTableForm ctf = null;
	private ButtonCell deletebtn = new ButtonCell();
	private ClickableTextCell bezeigenschaft = new ClickableTextCell();
	private EditTextCell wertauspraegung = new EditTextCell();
	private CellTableForm.ColumnEigenschaft bezEigenschaft = ctf.new ColumnEigenschaft(bezeigenschaft);
	private CellTableForm.ColumnAuspraegung wertAuspraegung = ctf.new ColumnAuspraegung(wertauspraegung) {

		public void onBrowserEvent(Context context, Element elem, EigenschaftAuspraegungWrapper object,
				NativeEvent event) {

			super.onBrowserEvent(context, elem, object, event);
			ctf.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
			if (event.getKeyCode() == KeyCodes.KEY_ENTER) {

				setFieldUpdater(new FieldUpdater<EigenschaftAuspraegungWrapper, String>() {

					@Override
					public void update(int index, EigenschaftAuspraegungWrapper object, String value) {

						object.setEigenschaftValue(value);
						ctf.getSm().getSelectedObject().setAuspraegungValue(value);
						ctf.getSm().getSelectedObject().setAuspraegungID(object.getAuspraegungID());
						eigaus.setWert(object.getAuspraegungValue());
						eigaus.setID(object.getAuspraegungID());
						eigaus.setEigenschaftsID(object.getEigenschaftID());
						eigaus.setKontaktID(kon.getID());
						if (object.getAuspraegungStatus() == 1) {
							eigaus.setStatus(1);
						} else {
							eigaus.setStatus(0);
						}
						eigaus.setWert(ctf.getSm().getSelectedObject().getAuspraegungValue());
						verwaltung.updateAuspraegung(eigaus, new AuspraegungBearbeitenCallback());
						verwaltung.updateKontakt(kon, new KontaktModifikationsdatumCallback());
					}

				});
			}

		};

	};

	private MultiWordSuggestOracle eigenschaftOracle = new MultiWordSuggestOracle();
	private SuggestBox eigenschaftSugBox = new SuggestBox(eigenschaftOracle);

	DateTimeFormat fmt = DateTimeFormat.getFormat("dd.MM.yyyy");
	private Button cancelBtn = new Button("Cancel");

	private EigenschaftAuspraegungWrapper ea = new EigenschaftAuspraegungWrapper();

	private Eigenschaft eig1 = new Eigenschaft();
	private Eigenschaftauspraegung eigaus = new Eigenschaftauspraegung();

	private CellTableForm.ColumnDeleteBtn deleteBtn = ctf.new ColumnDeleteBtn(deletebtn);

	public UpdateKontaktForm(Kontakt kon) {

		this.kon = kon;
		ctf = new CellTableForm(kon);
		ctf.addColumn(bezEigenschaft, "Eigenschaft: ");
		ctf.addColumn(wertAuspraegung, "Wert: ");
		ctf.addColumn(deleteBtn, "Löschen");

	}

	public UpdateKontaktForm(Kontakt kon, String teilhaberschaft) {

		this.kon = kon;
		ctf = new CellTableForm(kon, teilhaberschaft);
		ctf.addColumn(bezEigenschaft, "Eigenschaft: ");
		ctf.addColumn(wertAuspraegung, "Wert: ");
	}

	public void onLoad() {

		super.onLoad();

		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));

		fmt.format(date);
		RootPanel.get("Buttonbar").clear();

		txt_KontaktName.setText(kon.getName());

		hpanelDetails.setHeight("35px");
		hpanelDetails.add(lbl_KontaktName);
		hpanelDetails.add(txt_KontaktName);

		scrollPanel.add(ctf);
		vpanelDetails.add(hpanelDetails);
		vpanelDetails.add(scrollPanel);
		vpanelDetails.add(hpanelAdd);
		hpanelAdd.add(lbl_NewEigenschaft);
		hpanelAdd.add(eigenschaftSugBox);
		hpanelAdd.add(lbl_NewAuspraegung);
		hpanelAdd.add(txt_Auspraegung);
		hpanelAdd.add(addRow);
		wertAuspraegung.setSortable(true);
		ctf.setSelectionModel(ctf.getSm());
		ctf.setStyleName("CellTableBearbeiten");

		scrollPanel.setHeight("400px");

		RootPanel.get("Buttonbar").add(cancelBtn);

		this.add(vpanelDetails);

		verwaltung.findAllEigenschaft(new AlleEigenschaftCallback());

		deleteBtn.setFieldUpdater(new DeleteFieldUpdater());
		cancelBtn.addClickHandler(new CancelClick());
		addRow.addClickHandler(new ClickAddRowHandler());
		txt_KontaktName.addKeyDownHandler(changeNameHandler);

	}

	class DeleteFieldUpdater implements FieldUpdater<EigenschaftAuspraegungWrapper, String> {
		@Override
		public void update(int index, EigenschaftAuspraegungWrapper object, String value) {
			ea.setAuspraegung(object.getAuspraegung());
			ea.setEigenschaft(object.getEigenschaft());
			if(object.getAuspraegungStatus() == 0){
			verwaltung.deleteEigenschaftUndAuspraegung(ea, new AuspraegungHybridLoeschenCallback());
			verwaltung.updateKontakt(kon, new KontaktModifikationsdatumCallback());
			}else{
				Window.alert("Sie müssen als erstes alle Teilhaberschaften an dieser Ausprägung löschen");
			}
		}
	}

	class CancelClick implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (kon.getNutzerID() == nutzer.getID()) {
				KontaktForm kf = new KontaktForm(kon);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(kf);
			} else {
				KontaktForm kf = new KontaktForm(kon, "Teilhaberschaft");
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(kf);
			}

		}
	}

	class ClickAddRowHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {

			ctf.addRow(eigenschaftSugBox.getValue(), txt_Auspraegung.getValue());
			
			verwaltung.insertEigenschaft(eigenschaftSugBox.getText(), 0, new EigenschaftEinfuegenCallback());
			verwaltung.updateKontakt(kon, new KontaktModifikationsdatumCallback());

		}
	}

	class ChangeNameHandler implements KeyDownHandler {

		@Override
		public void onKeyDown(KeyDownEvent event) {

			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				kon.setName(txt_KontaktName.getValue());
				verwaltung.updateKontakt(kon, new KontaktModifikationsdatumCallback());
			}
		}

	}

	class KontaktModifikationsdatumCallback implements AsyncCallback<Kontakt> {

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Kontakt result) {
			if (kon.getNutzerID() == nutzer.getID()) {
				verwaltung.findHybrid(kon, new ReloadCallback());
			} else {
				verwaltung.findSharedAuspraegung(kon.getID(), nutzer.getID(), new ReloadCallback());
			}
		}

	}

	class EigenschaftEinfuegenCallback implements AsyncCallback<Eigenschaft> {

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Eigenschaft result) {

			if (result == null) {

				verwaltung.findEigByBezeichnung(eigenschaftSugBox.getText(), new FindEigenschaftCallBack());

			} else {

				if (kon.getNutzerID() == nutzer.getID()) {

					eig1.setID(result.getID());
					verwaltung.insertAuspraegung(txt_Auspraegung.getText(), 0, eig1.getID(), kon.getID(),
							new AuspraegungEinfuegenCallback());
				} else {

					eig1.setID(result.getID());
					verwaltung.insertAuspraegung(txt_Auspraegung.getText(), 1, eig1.getID(), kon.getID(),
							new AuspraegungEinfuegenCallback());
				}

			}
		}

	}

	class AuspraegungEinfuegenCallback implements AsyncCallback<Eigenschaftauspraegung> {

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Eigenschaftauspraegung result) {
			if (kon.getNutzerID() == nutzer.getID()) {
				verwaltung.findHybrid(kon, new ReloadCallback());
			} else {
				verwaltung.insertTeilhaberschaftAuspraegung(result.getID(), nutzer.getID(), kon.getNutzerID(), new TeilhaberschaftKontaktCallback());
				
			}
			eigenschaftSugBox.setText("");
			txt_Auspraegung.setText("");
		}

	}

	class TeilhaberschaftKontaktCallback implements AsyncCallback<Teilhaberschaft>{

		@Override
		public void onFailure(Throwable caught) {
	
			
		}

		@Override
		public void onSuccess(Teilhaberschaft result) {
			Window.alert("Die Ausprägung wurde erfolgreich angelgt. Da der angemeldete Nutzer nicht der Eigentümer ist, wurde eine Teilhaberschaft erstellt");
			verwaltung.findSharedAuspraegung(kon.getID(), nutzer.getID(), new ReloadCallback());
		}
		
	}
	
	class AuspraegungBearbeitenCallback implements AsyncCallback<Eigenschaftauspraegung> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(" Hat nicht funktioniert ");

		}

		@Override
		public void onSuccess(Eigenschaftauspraegung result) {

			eigaus.setWert(result.getWert());
			ctf.getSm().setSelected(null, true);

			if (kon.getNutzerID() == nutzer.getID()) {
				verwaltung.findHybrid(kon, new ReloadCallback());
			} else {
				verwaltung.findSharedAuspraegung(kon.getID(), nutzer.getID(), new ReloadCallback());
			}

		}

	}

	class AuspraegungHybridLoeschenCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Hat nicht funktioniert");

		}

		@Override
		public void onSuccess(Void result) {
			ctf.deleteRow(ea);

			if (kon.getNutzerID() == nutzer.getID()) {
				verwaltung.findHybrid(kon, new ReloadCallback());
			} else {
				verwaltung.findSharedAuspraegung(kon.getID(), nutzer.getID(), new ReloadCallback());
			}

		}
	}

	class ReloadCallback implements AsyncCallback<Vector<EigenschaftAuspraegungWrapper>> {

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Vector<EigenschaftAuspraegungWrapper> result) {

			ctf.setRowData(0, result);
			ctf.setRowCount(result.size(), true);
		}

	}

	class AlleEigenschaftCallback implements AsyncCallback<Vector<Eigenschaft>> {

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Vector<Eigenschaft> result) {

			for (Eigenschaft eigenschaft : result) {
				eigenschaftOracle.add(eigenschaft.getBezeichnung());

			}

		}

	}

	class FindEigenschaftCallBack implements AsyncCallback<Eigenschaft> {

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Eigenschaft result) {

			if (kon.getNutzerID() == nutzer.getID()) {

				eig1.setID(result.getID());
				verwaltung.insertAuspraegung(txt_Auspraegung.getText(), 0, eig1.getID(), kon.getID(),
						new AuspraegungEinfuegenCallback());
			} else {

				eig1.setID(result.getID());
				verwaltung.insertAuspraegung(txt_Auspraegung.getText(), 1, eig1.getID(), kon.getID(),
						new AuspraegungEinfuegenCallback());
			}
		}

	}
}
