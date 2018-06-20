package de.hdm.itprojektgruppe4.client.gui;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
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
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;

public class NewKontaktForm extends VerticalPanel {

	//Instanziieren und Deklarieren der benötigten Variablen
	
	private KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();

	private HorizontalPanel hpanel = new HorizontalPanel();
	private VerticalPanel vpanel = new VerticalPanel();
	private HorizontalPanel hpanelButtonBar = new HorizontalPanel();
	private HorizontalPanel hpanel2 = new HorizontalPanel();
	private VerticalPanel vpanel2 = new VerticalPanel();

	private Label name = new Label("Name: ");
	private TextBox tbName = new TextBox();
	private Label eigenschaftName = new Label("Eigenschaft");
	private Label auspraegungName = new Label("Ausprägung");

	private Button cancel = new Button("Cancel");
	private Button getBack = new Button("Abschliessen und zurück");
	private HTML html1 = new HTML(
			"Bitte geben Sie hier die <b> Namen </b> zu ihrem neuen "
					+ " <b>Kontakt</b>  an und bestätigen Sie mit Enter." + "<span style='font-family:fixed'></span>",true);

	private HTML html2 = new HTML("Bitte geben Sie hier die <b> Eigenschaften </b> und die dazugehörigen"
			+ " <b>Auspärgungen</b>  zu Ihrem Kontakt an." + "<span style='font-family:fixed'></span>", true);

	private Button addRow = new Button("Eigenschaft hinzufügen");

	private Button addToList = new Button("Kontakt zu einer Liste hinzufügen");

	private TextBox txt_Auspraegung = new TextBox();

	private MultiWordSuggestOracle eigenschaftOracle = new MultiWordSuggestOracle();
	private SuggestBox eigenschaftSugBox = new SuggestBox(eigenschaftOracle);

	private Kontakt kontakt1 = new Kontakt();
	private Eigenschaftauspraegung eigaus = new Eigenschaftauspraegung();
	private Nutzer nutzer = new Nutzer();
	private Eigenschaft eig1 = new Eigenschaft();
	private EigenschaftAuspraegungWrapper ea = new EigenschaftAuspraegungWrapper();
	
	private CellTableForm ctf = null;
	private ButtonCell deleteBtn = new ButtonCell();
	private CellTableForm.ColumnDeleteBtn deleteButtonCol = ctf.new ColumnDeleteBtn(deleteBtn);
	
	
	private ClickableTextCell bezeigenschaft = new ClickableTextCell();
	private EditTextCell wertauspraegung = new EditTextCell();

	private CellTableForm.ColumnEigenschaft bezEigenschaft = ctf.new ColumnEigenschaft(bezeigenschaft);
	private CellTableForm.ColumnAuspraegung wertAuspraegung = ctf.new ColumnAuspraegung(wertauspraegung){

		public void onBrowserEvent(Context context, Element elem, EigenschaftAuspraegungWrapper object, NativeEvent event) {
			
			super.onBrowserEvent(context, elem, object, event);
			ctf.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
			if (event.getKeyCode() == KeyCodes.KEY_ENTER) {
				
				
				setFieldUpdater(new FieldUpdater<EigenschaftAuspraegungWrapper, String>() {

					@Override
					public void update(int index, EigenschaftAuspraegungWrapper object, String value) {
						
						object.setAuspraegungValue(value);
						
						
						ctf.getSm().getSelectedObject().setAuspraegungValue(value);
						ctf.getSm().getSelectedObject().setAuspraegungID(object.getAuspraegungID());
						
						eigaus.setWert(object.getAuspraegungValue());
						eigaus.setID(object.getAuspraegungID());
						eigaus.setEigenschaftsID(object.getEigenschaftID());
						eigaus.setKontaktID(kontakt1.getID());
						eigaus.setStatus(0);
						
						eigaus.setWert(ctf.getSm().getSelectedObject().getAuspraegungValue());
						
						verwaltung.updateAuspraegung(eigaus, new AuspraegungBearbeitenCallback());
					
						verwaltung.findHybrid(kontakt1, new ReloadCallback());
						
					}

				});
			}

		};

	};

	KontaktlisteKontaktTreeViewModel kktvw = null;

	
	public void onLoad() {

		super.onLoad();

		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));

		html2.setVisible(false);
		eigenschaftSugBox.setVisible(false);
		txt_Auspraegung.setVisible(false);
		eigenschaftName.setVisible(false);
		auspraegungName.setVisible(false);
		addRow.setVisible(false);
		ctf = new CellTableForm(kontakt1);
		ctf.addColumn(bezEigenschaft, "Eigenschaft");
		ctf.addColumn(wertAuspraegung, "Ausprägung");
		ctf.addColumn(deleteButtonCol);
		
		ctf.setSelectionModel(ctf.getSm());

		hpanelButtonBar.add(cancel);	

		hpanel2.add(eigenschaftName);
		hpanel2.add(eigenschaftSugBox);
		hpanel2.add(auspraegungName);
		hpanel2.add(txt_Auspraegung);
		hpanel2.add(addRow);

		vpanel2.add(hpanel2);
		vpanel2.add(addToList);
		vpanel2.add(getBack);

		hpanel.add(name);
		hpanel.add(tbName);
		vpanel.add(html1);
		vpanel.add(hpanel);
		vpanel.add(html2);
		
		RootPanel.get("Buttonbar").clear();
		RootPanel.get("Buttonbar").add(hpanelButtonBar);
		
		this.add(vpanel);

		verwaltung.findAllEigenschaft(new AlleEigenschaftCallback());

		// Clickhandler den Buttons hinzufügen

		cancel.addClickHandler(new CancelClick());
		getBack.addClickHandler(new GetBackClick());
		addToList.addClickHandler(new AddToListClick());
		tbName.addKeyDownHandler(new InsertReturn());
		addRow.addClickHandler(new ClickAddRowHandler());
		deleteButtonCol.setFieldUpdater(new DeleteFieldUpdater());

	}
	// Implementierung der Clickhandler- und Asynccallback - Klassen
	

	/**
	 * Diese KeyDownHandler Klasse überprüft ob die Enter-Taste gedrückt wurde und 
	 * stößt den Callback, um einen neuen Kontakt zu erstellen, an.
	 * @author Nino
	 *
	 */
	
	class InsertReturn implements KeyDownHandler {

		@Override
		public void onKeyDown(KeyDownEvent event) {
			// TODO Auto-generated method stub
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {

				verwaltung.insertKontakt(tbName.getValue(), new Date(), new Date(), 0, nutzer.getID(),
						new KontaktErstellenCallback());

				html2.setVisible(true);
				cancel.setVisible(false);

			}
		}
		
	}

	/**
	 * ClickHandler Klasse für den Cancel Button, welcher die Kontakterstellung
	 * abbricht und den Nutzer zurück auf die Startseite bringt
	 * 
	 * @author nino
	 *
	 */

	class CancelClick implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			MainForm getBack = new MainForm();
			RootPanel.get("Buttonbar").clear();
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(getBack);
		}

	}

	/**
	 * ClickHandler Klasse für den Cancel Button, welcher die Kontakterstellung
	 * beendet und den Nutzer zurück auf die Startseite bringt
	 * 
	 * @author nino
	 *
	 */

	class GetBackClick implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			MainForm getBack = new MainForm();
			RootPanel.get("Buttonbar").clear();
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(getBack);
		}

	}

	/**
	 * Diese Clickhandler Klasse ermöglicht das Erscheinen 
	 * der DialogBox um den Kontakt direkt beim Erstellen einer Liste hinzuzufügen
	 * @author Nino
	 *
	 */
	
	class AddToListClick implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			DialogBoxAddContactToList dbkl = new DialogBoxAddContactToList(kontakt1);
			dbkl.center();
			verwaltung.findHybrid(kontakt1, new ReloadCallback());
		}

	}

	/**
	 * Diese Clickhandler stößt die Callback-aufrufe an um die neue Ausprägung 
	 * und die zugehörige Eigenschaft anzulegen 
	 * @author Nino
	 *
	 */
	private class ClickAddRowHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub

			ctf.addRow(eigenschaftSugBox.getValue(), txt_Auspraegung.getValue());
			verwaltung.insertEigenschaft(eigenschaftSugBox.getText(), 0, new EigenschaftHinzufuegenCallback());

		}

	}

	/**
	 * CallBack Klasse um einen neuen Kontakt zu erstellen, in der
	 * Applikationslogik wurde implemnetiert, dass ein neuer Kontakt zu der
	 * Basisliste "Meine Kontakte " hinzugefügt wird.
	 * 
	 * @author nino
	 *
	 */

	private class KontaktErstellenCallback implements AsyncCallback<Kontakt> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Kontakt wurde nicht erstellt");
		}

		@Override
		public void onSuccess(Kontakt result) {
			kontakt1.setID(result.getID());
			kontakt1.setName(result.getName());
			// Window.alert("Kontakt " + result.getName() + " wurde erstellt");
			verwaltung.insertBasicAuspraegung("", 0, result.getID(), new BasicAuspraegungenCallback());

		}

	}

	/**
	 * Callback Klasse um leere Ausprägungen, für unsere 4 vorgegebenen
	 * Eigenschaften, zu erstellen. Diese Ausprägungen werden im Nachheinen
	 * bearbeitet.
	 * 
	 * @author nino
	 *
	 */

	private class BasicAuspraegungenCallback implements AsyncCallback<Vector<Eigenschaftauspraegung>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("die leeren ausprägungen wurden NICHT erstellt ");
		}

		@Override
		public void onSuccess(Vector<Eigenschaftauspraegung> result) {
			// Window.alert("die leeren ausprägungen wurden erstellt ");
			html2.setVisible(true);
			eigenschaftName.setVisible(true);
			auspraegungName.setVisible(true);
			eigenschaftSugBox.setVisible(true);
			txt_Auspraegung.setVisible(true);
			addRow.setVisible(true);


			add(ctf);
			add(vpanel2);
			verwaltung.findHybrid(kontakt1, new ReloadCallback());
			

		}

	}

	/**
	 * Callback Klasse um die leeren Ausprägungen, zu den Basis-Eigenschaften,
	 * zu bearbeiten.
	 * 
	 * @author nino
	 *
	 */

	private class AuspraegungBearbeitenCallback implements AsyncCallback<Eigenschaftauspraegung> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(" Hat nicht funktioniert ");
		}

		@Override
		public void onSuccess(Eigenschaftauspraegung result) {
			eigaus.setWert(result.getWert());
			ctf.getSm().setSelected(null, true);
			verwaltung.findHybrid(kontakt1, new ReloadCallback());
			
		
		}

	}

	/**
	 * Callback Klasse um alle Eigenschaften aus dem System zu bekommen und
	 * diese in die SuggestionBox zu laden.
	 * 
	 * @author nino
	 *
	 */

	private class AlleEigenschaftCallback implements AsyncCallback<Vector<Eigenschaft>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Vector<Eigenschaft> result) {
			// TODO Auto-generated method stub
			for (Eigenschaft eigenschaft : result) {
				eigenschaftOracle.add(eigenschaft.getBezeichnung());

			}

		}

	}

	/**
	 * CallBack Klasse um anahnd der SuggestionBox eine neue Eigenschaft für den
	 * neuen Kontakt zu erstellen.
	 * 
	 * @author nino
	 *
	 */

	private class EigenschaftHinzufuegenCallback implements AsyncCallback<Eigenschaft> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Die Neue Eigenschaft wurde nicht hinzugefügt");
		}

		@Override
		public void onSuccess(Eigenschaft result) {

			if (result == null) {

				verwaltung.findEigByBezeichnung(eigenschaftSugBox.getText(), new FindEigenschaftCallback());

			} else {

				eig1.setID(result.getID());
				verwaltung.insertAuspraegung(txt_Auspraegung.getText(), 0, eig1.getID(), kontakt1.getID(),
						new AuspraegungHinzufuegenCallback());

			}

		}

	}

	/**
	 * CallBack Klasse um anahnd der TextBox eine neue Ausprägung für den neuen
	 * Kontakt zu erstellen.
	 * 
	 * @author nino
	 *
	 */

	private class AuspraegungHinzufuegenCallback implements AsyncCallback<Eigenschaftauspraegung> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Die Neue Ausprägung wurde  nicht hinzugefügt");
		}

		@Override
		public void onSuccess(Eigenschaftauspraegung result) {

			verwaltung.findHybrid(kontakt1, new ReloadCallback());
			eigenschaftSugBox.setText("");
			txt_Auspraegung.setText("");
		}

	}


	/**
	 * Callback-Klasse um eine neue Ausprägung zu diesem Kontakt hinzuzufügen. 
	 * Der Wert der Ausprägung wird aus der Textbox entnommen.
	 * @author Nino
	 *
	 */
	private class FindEigenschaftCallback implements AsyncCallback<Eigenschaft> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Eigenschaft result) {
			// TODO Auto-generated method stub
			verwaltung.insertAuspraegung(txt_Auspraegung.getText(), 0, result.getID(), kontakt1.getID(),
					new AuspraegungHinzufuegenCallback());
		}

	}
	
	/**
	 * Diese Callback Klasse wird immer aufgerufen, wenn sich die CellTable geändert hat.
	 * @author Nino
	 *
	 */
	
	private class ReloadCallback implements AsyncCallback<Vector<EigenschaftAuspraegungWrapper>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Vector<EigenschaftAuspraegungWrapper> result) {

			ctf.setRowData(0, result);
			ctf.setRowCount(result.size(), true);
		}

	}
	
	
	private class DeleteFieldUpdater implements FieldUpdater<EigenschaftAuspraegungWrapper, String>{

		@Override
		public void update(int index, EigenschaftAuspraegungWrapper object, String value) {
			// TODO Auto-generated method stub
			ea.setAuspraegung(object.getAuspraegung());
			ea.setEigenschaft(object.getEigenschaft());
			verwaltung.deleteEigenschaftUndAuspraegung(ea, new EigAusDeleteCallback());
		}
		
	}
	
	/**
	 *  Diese Callback-Klasse löscht die ausgewählte Ausprägung.
	 * @author Nino
	 *
	 */
	private class EigAusDeleteCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Die Ausprägung konnte nicht gelöscht werden");
		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			ctf.deleteRow(ea);
			verwaltung.findHybrid(kontakt1, new ReloadCallback());
		}
		
	}

}
