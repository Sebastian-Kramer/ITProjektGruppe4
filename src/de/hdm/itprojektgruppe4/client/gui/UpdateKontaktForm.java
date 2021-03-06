package de.hdm.itprojektgruppe4.client.gui;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.cell.client.Cell.Context;
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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.client.EigenschaftAuspraegungWrapper;
import de.hdm.itprojektgruppe4.client.NavigationTree;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.bo.Teilhaberschaft;

/**
 * Mit dieser Klasse UpdateKontaktForm lassen sich Kontakte bearbeiten. Zum
 * einen lässt sich der Kontaktname und die jeweiligen Auspraegungen des
 * Kontaktes ändern. Es können neue Ausprägungen hinzugefügt werden oder
 * gelöscht werden. Bei jeder Bearbeitung wird das Modifikationsdatum des
 * Kontaktes aktualisiert.
 * 
 * @author Nino
 *
 */

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
	private Button deleteAuspraegungButton = new Button("Ausprägungen löschen");

	private TextBox txt_Auspraegung = new TextBox();
	private Date date = new Date();
	private boolean deleteAuspraegung;
	private KeyDownHandler changeNameHandler = new ChangeNameHandler();

	private ScrollPanel scrollPanel = new ScrollPanel();
	private Image kontaktbild = new Image("Image/Visitenkarte_2.png");
	private CellTableForm ctf = null;
	private EditTextCell wertauspraegung = new EditTextCell();

	/**
	 * In der importierten Column ColumnAuspraegung wird die bearbeitungsfunktion 
	 * direkt deklariert. Diese wird durch den onBrowserEvent "ENTER" ausgelöst. 
	 * Es folgt ein FieldUpdater der die bearbeitung durchführt.
	 * 
	 * @author Nino
	 *
	 */
	

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
	private Button cancelBtn = new Button("Bearbeitung beenden");
	private Image zurueckZuHomePic = new Image("Image/Back.png");
	private Image deletePic = new Image("Image/Loeschen.png");
	private EigenschaftAuspraegungWrapper ea = new EigenschaftAuspraegungWrapper();

	private Eigenschaft eig1 = new Eigenschaft();
	private Eigenschaftauspraegung eigaus = new Eigenschaftauspraegung();
	final MultiSelectionModel<EigenschaftAuspraegungWrapper> selectionModelWrapper = new MultiSelectionModel<EigenschaftAuspraegungWrapper>();

	/**
	 * Konstruktor: Beim Laden der UpdateKontaktForm wirde ein Kontakt-Objekt
	 * übergeben. Wenn der Nutzer der Eigentümer des Kontaktes ist, werden alle
	 * Ausprägungen des Kontaktes angezeigt. Der CellTable werden die
	 * entsprechenden Spalten hinzugefügt.
	 * 
	 * @param kon
	 */

	public UpdateKontaktForm(Kontakt k) {

		this.kon = k;
		ctf = new CellTableForm(kon);

	}

	/**
	 * Konstruktor: In diesem Fall is der Nutzer jediglich der Teilhaber des
	 * Kontaktes, es wird ein Kontakt-Objekt und ein String teilhaberschaft
	 * übergeben. Der CellTable werden die entsprechenden Spalten hinzugefügt.
	 * 
	 * @param kon
	 * @param teilhaberschaft
	 */
	public UpdateKontaktForm(Kontakt k, String teilhaberschaft) {

		this.kon = k;
		ctf = new CellTableForm(kon, teilhaberschaft);

	}

	/**
	 * Die onLoad()-Methode wird beim Starten der UpdateKontaktForm geladen. Es
	 * wird eine neue CellTableForm mit dem übergebenen Kontakt erstellt, die
	 * alle benötigten Spalten beinhaltet. Des weiteren werden den Buttons die
	 * zugehörigen Clickhandler hinzugefügt und die verschiedenen Widgets den
	 * Panel hinzugefügt.
	 */

	public void onLoad() {

		super.onLoad();
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));

		deleteAuspraegungButton.setVisible(false);
		selectionModelWrapper.addSelectionChangeHandler(new SelectionHandler());

		fmt.format(date);
		RootPanel.get("Buttonbar").clear();
		ctf.addColumn(ctf.getBezEigenschaft(), "Kontakteigenschaften: ");
		ctf.addColumn(wertAuspraegung);
		ctf.addColumn(ctf.getDeleteBtn());
		ctf.getDeleteBtn().setFieldUpdater(new DeleteFieldUpdater());
		ctf.setStyleName("CellTableAuspraegung");
		kontaktbild.setStyleName("Kontaktbild");
		txt_KontaktName.setText(kon.getName());
		txt_KontaktName.setStyleName("klistBox");
		lbl_KontaktName.setStyleName("KlistBezeichnung");
		hpanelDetails.setStyleName("HpanelTop");
		hpanelDetails.setWidth("700px");
		hpanelDetails.add(kontaktbild);
		hpanelDetails.add(lbl_KontaktName);
		hpanelDetails.add(txt_KontaktName);
		eigenschaftSugBox.getElement().setAttribute("placeholder", "Eigenschaft");
		txt_Auspraegung.getElement().setAttribute("placeholder", "Ausprägung");
		scrollPanel.add(ctf);
		vpanelDetails.add(hpanelDetails);
		vpanelDetails.add(scrollPanel);
		vpanelDetails.add(hpanelAdd);
		hpanelAdd.setStyleName("hpanelRechts");
		hpanelAdd.add(eigenschaftSugBox);
		hpanelAdd.add(txt_Auspraegung);
		hpanelAdd.add(addRow);
		hpanelAdd.add(deleteAuspraegungButton);
		wertAuspraegung.setSortable(true);
		ctf.setSelectionModel(ctf.getSm());
		ctf.setStyleName("CellTableKontakt");
		zurueckZuHomePic.setStyleName("ButtonICON");
		deletePic.setStyleName("ButtonICON");

		RootPanel.get("Buttonbar").add(cancelBtn);

		this.add(vpanelDetails);

		verwaltung.findAllEigenschaft(new AlleEigenschaftCallback());

		cancelBtn.getElement().appendChild(zurueckZuHomePic.getElement());
		cancelBtn.addClickHandler(new CancelClick());
		addRow.addClickHandler(new ClickAddRowHandler());
		txt_KontaktName.addKeyDownHandler(changeNameHandler);

	}

	/*
	 * Nasted AsyncCallback - Classes, Click/Selection - Handler und
	 * FieldUpdater - Classes.
	 */

	/**
	 * 
	 * @author Nino
	 *
	 */

	/**
	 * In diesem FieldUpdater wird die Eigenschaftausprägung aus dem System
	 * entfernt. Es wird überprüft ob die Ausprägung geteilt ist. Falls ja, muss
	 * das löschen über einen Window confirm bestätigt werden.
	 * 
	 * @author Clirim
	 *
	 */
	private class DeleteFieldUpdater implements FieldUpdater<EigenschaftAuspraegungWrapper, String> {

		@Override
		public void update(int index, EigenschaftAuspraegungWrapper object, String value) {

			ea.setAuspraegung(object.getAuspraegung());
			ea.setEigenschaft(object.getEigenschaft());
			if (object.getAuspraegungStatus() == 0) {
				verwaltung.deleteEigenschaftUndAuspraegung(ea, new AuspraegungHybridLoeschenCallback());
				verwaltung.updateKontakt(kon, new KontaktModifikationsdatumCallback());
			} else {

				deleteAuspraegung = Window.confirm(
						"Diese Ausprägung ist mit jemand geteilt, möchten Sie diese Ausprägung dennoch löschen ?");
				if (deleteAuspraegung == true) {
					verwaltung.deleteEigenschaftUndAuspraegung(ea, new AuspraegungHybridLoeschenCallback());
					verwaltung.updateKontakt(kon, new KontaktModifikationsdatumCallback());
				}
			}
		}
	}

	/**
	 * Diese Clickhandler Klasse bricht die Bearbeitung des Kontaktes ab.
	 * 
	 * @author Nino
	 *
	 */
	private class CancelClick implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (kon.getNutzerID() == nutzer.getID()) {
				NavigationTree updatedNavigation = new NavigationTree();
				KontaktForm kf = new KontaktForm(kon);
				RootPanel.get("Navigator").clear();
				RootPanel.get("Details").clear();
				RootPanel.get("Navigator").add(updatedNavigation);
				RootPanel.get("Details").add(kf);
			} else {
				NavigationTree updatedNavigation = new NavigationTree();
				KontaktForm kf = new KontaktForm(kon, "Teilhaberschaft");
				RootPanel.get("Navigator").clear();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(kf);
				RootPanel.get("Navigator").add(updatedNavigation);
			}

		}
	}

	/**
	 * Diese CLickhandler Klasse fügt eine neue Asprägung dem Kontakt hinzu.
	 * 
	 * @author Nino
	 *
	 */

	private class ClickAddRowHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {

			if (eigenschaftSugBox.getValue().equals("") && txt_Auspraegung.getValue().equals("")) {
				Window.alert("Sie haben keine Werte für Eigenschaft und Ausprägung angegeben");
			}else if(eigenschaftSugBox.getValue().equals("")){
				Window.alert("Sie haben keinen Wert für Eigenschaft angegeben");
			} 
			else {
				ctf.addRow(eigenschaftSugBox.getValue(), txt_Auspraegung.getValue());
				verwaltung.insertEigenschaft(eigenschaftSugBox.getText(), 0, new EigenschaftEinfuegenCallback());
				verwaltung.updateKontakt(kon, new KontaktModifikationsdatumCallback());
			}
		}
	}

	/**
	 * Diese KeyDownHandler Klasse erlaut dem Nutzer den Kontaktnamen zu ändern
	 * und per Bestätigung der Enter Taste zu speichern.
	 * 
	 * @author Nino
	 *
	 */

	private class ChangeNameHandler implements KeyDownHandler {

		@Override
		public void onKeyDown(KeyDownEvent event) {

			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				kon.setName(txt_KontaktName.getValue());
				verwaltung.updateKontakt(kon, new KontaktModifikationsdatumCallback());
				Window.alert("Sie haben den Namen des Kontaktes in " + kon.getName() + " geändert!");
			}
		}

	}

	/**
	 * Diese Callback Klasse aktualisiert das Modifikationsdatum des Kontaktes.
	 * 
	 * @author Nino
	 *
	 */

	private class KontaktModifikationsdatumCallback implements AsyncCallback<Kontakt> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Kontakt result) {
			kon = result;
			kon.setModifikationsdatum(new Date());
			if (kon.getNutzerID() == nutzer.getID()) {
				verwaltung.findHybrid(kon, new ReloadCallback());
			} else {
				verwaltung.findSharedAuspraegung(kon.getID(), nutzer.getID(), new ReloadCallback());
			}
		}

	}

	/**
	 * Diese Callback Klasse erstellt und wählt die Eigenschaft aus zu der eine
	 * neue Ausprägung erstellt wird. Sofern diese Eigenschaft bereits vorhanden
	 * ist, wird diese verwendet. Wenn diese Eigenschaft im System noch nicht
	 * vorhanden ist wird diese neu angelegt.
	 * 
	 * @author Nino
	 *
	 */

	private class EigenschaftEinfuegenCallback implements AsyncCallback<Eigenschaft> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten: " + caught.getMessage());
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

	/**
	 * Diese Callback Klasse erstellt eine neue Ausprägung zu diesem Kontakt,
	 * die Eigenschaft auf diese sich die Ausprägung bezieht wurde zuvor
	 * ausgewählt.
	 * 
	 * @author Nino
	 *
	 */

	private class AuspraegungEinfuegenCallback implements AsyncCallback<Eigenschaftauspraegung> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Eigenschaftauspraegung result) {
			if (kon.getNutzerID() == nutzer.getID()) {
				verwaltung.findHybrid(kon, new ReloadCallback());
			} else {
				verwaltung.insertTeilhaberschaftAuspraegung(result.getID(), nutzer.getID(), kon.getNutzerID(),
						new TeilhaberschaftKontaktCallback());

			}
			eigenschaftSugBox.setText("");
			txt_Auspraegung.setText("");
		}

	}

	/**
	 * Diese Callback Klasse wird ausgeführt wenn der Teilhaber eine neue
	 * Ausprägung erstellt. Es wird direkt ein Objekt vom Typ Teilhaberschaft zu
	 * dieser Ausprägung erstellt.
	 * 
	 * @author Nino
	 *
	 */

	private class TeilhaberschaftKontaktCallback implements AsyncCallback<Teilhaberschaft> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Teilhaberschaft result) {
			Window.alert(
					"Die Ausprägung wurde erfolgreich angelgt. Da der angemeldete Nutzer nicht der Eigentümer ist, wurde eine Teilhaberschaft erstellt");
			verwaltung.findSharedAuspraegung(kon.getID(), nutzer.getID(), new ReloadCallback());
		}

	}

	/**
	 * Diese Callback Klasse wird ausgeführt wenn eine bestehende Ausprägung
	 * bearbeitet wird. Je nach dem ob der Nutzer der Eigentümer oder nur
	 * Teilhaber ist werden alle oder nur getielte Ausprägungen angezeigt.
	 * 
	 * @author Nino
	 *
	 */

	private class AuspraegungBearbeitenCallback implements AsyncCallback<Eigenschaftauspraegung> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten: " + caught.getMessage());

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

	/**
	 * Diese Callback Klasse wird ausgeführt wenn eine Ausprägung gelöscht wird.
	 * Je nach dem ob der Nutzer der Eigentümer oder nur Teilhaber ist werden
	 * alle oder nur getielte Ausprägungen angezeigt.
	 * 
	 * @author Nino
	 *
	 */

	private class AuspraegungHybridLoeschenCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten: " + caught.getMessage());

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

	/**
	 * Diese Callback Klasse wird immer aufgerufen, wenn sich die CellTable
	 * geändert hat und ein Refresh notendig ist.
	 * 
	 * @author Nino
	 *
	 */
	private class ReloadCallback implements AsyncCallback<Vector<EigenschaftAuspraegungWrapper>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<EigenschaftAuspraegungWrapper> result) {

			ctf.setRowData(0, result);
			ctf.setRowCount(result.size(), true);
		}

	}

	/**
	 * Diese Callback Klasse wird ausgeführt um die Suggestbox mit bereits
	 * vorhandenen Eigenschaften zu befüllen.
	 * 
	 * @author Nino
	 *
	 */

	private class AlleEigenschaftCallback implements AsyncCallback<Vector<Eigenschaft>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<Eigenschaft> result) {

			for (Eigenschaft eigenschaft : result) {
				eigenschaftOracle.add(eigenschaft.getBezeichnung());

			}

		}

	}

	/**
	 * Diese Callback Klasse wird aufgerufen um eine Eigenschaft anhand ihrer
	 * Bezeichnung zu identifizieren.
	 * 
	 * @author Nino
	 *
	 */

	private class FindEigenschaftCallBack implements AsyncCallback<Eigenschaft> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten: " + caught.getMessage());
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

	class SelectionHandler implements SelectionChangeEvent.Handler {

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			if (selectionModelWrapper.getSelectedSet().isEmpty()) {
				deleteAuspraegungButton.setVisible(false);
			} else {
				deleteAuspraegungButton.setVisible(true);
			}
		}

	}
}
