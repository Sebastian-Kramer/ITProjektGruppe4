package de.hdm.itprojektgruppe4.client.gui;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;

import de.hdm.itprojektgruppe4.client.EigenschaftAuspraegungWrapper;
import de.hdm.itprojektgruppe4.client.gui.UpdateKontaktForm.AuspraegungBearbeitenCallback;

import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.KontaktKontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;

public class NewKontaktForm extends VerticalPanel {

	KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();

	private HorizontalPanel hpanel = new HorizontalPanel();
	private VerticalPanel vpanel = new VerticalPanel();
	private HorizontalPanel hpanelButtonBar = new HorizontalPanel();
	private HorizontalPanel hpanel2 = new HorizontalPanel();

	private Label name = new Label("Name: ");
	private TextBox tbName = new TextBox();
	private Button save1 = new Button("Speichern");
	private Label eigenschaftName = new Label("Eigenschaft");
	private Label auspraegungName = new Label("Ausprägung");

	private Button cancel = new Button("Cancel");
	private HTML html1 = new HTML("Bitte geben Sie hier die <b> Namen </b> zu ihrem neuen " + " <b>Kontakt</b>  an."
			+ "<span style='font-family:fixed'></span>", true);

	private HTML html2 = new HTML("Bitte geben Sie hier die <b> Eigenschaften </b> und die dazugehörigen"
			+ " <b>Auspärgungen</b>  zu Ihrem Kontakt an." + "<span style='font-family:fixed'></span>", true);

	private Button addRow = new Button("Eigenschaft hinzufügen");

	private TextBox txt_Eigenschaft = new TextBox();
	private TextBox txt_Auspraegung = new TextBox();

	private CellTableForm ctf = null;

	private Kontakt kontakt1 = new Kontakt();

	private Nutzer nutzer = new Nutzer();
	
	private Kontaktliste kList = new Kontaktliste();

	KontaktlisteKontaktTreeViewModel kktvw = null;

	private Eigenschaftauspraegung eigaus = new Eigenschaftauspraegung();

	
	private SingleSelectionModel<EigenschaftAuspraegungWrapper> sm = new SingleSelectionModel<EigenschaftAuspraegungWrapper>();
	
	
	
	public void onLoad(){
		


		super.onLoad();

		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));

		html2.setVisible(false);
		txt_Eigenschaft.setVisible(false);
		txt_Auspraegung.setVisible(false);
		eigenschaftName.setVisible(false);
		auspraegungName.setVisible(false);
		addRow.setVisible(false);

		hpanelButtonBar.add(save1);

		hpanelButtonBar.add(cancel);

		RootPanel.get("Buttonbar").clear();
		RootPanel.get("Buttonbar").add(hpanelButtonBar);

		hpanel2.add(eigenschaftName);
		hpanel2.add(txt_Eigenschaft);
		hpanel2.add(auspraegungName);
		hpanel2.add(txt_Auspraegung);
		hpanel2.add(addRow);

		hpanel.add(name);
		hpanel.add(tbName);

		vpanel.add(html1);
		vpanel.add(hpanel);
		vpanel.add(html2);

		this.add(vpanel);

		
		
		
		cancel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				MainForm getBack = new MainForm();
				RootPanel.get("Buttonbar").clear();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(getBack);

			}
		});

		KeyDownHandler returnKeyHandler = new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {

					verwaltung.insertKontakt(tbName.getValue(), new Date(), new Date(), 0, nutzer.getID(),
							new KontaktErstellenCallback());
				
					html2.setVisible(true);

				}
			}
		};

		tbName.addKeyDownHandler(returnKeyHandler);

		addRow.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ctf.addRow(txt_Eigenschaft.getValue(), txt_Auspraegung.getValue());

				verwaltung.insertEigenschaft(txt_Eigenschaft.getText(), 0, new EigenschaftHinzufuegenCallback());

			}

		});

	}
	
	public void fillTable(){
		
		
		Column<EigenschaftAuspraegungWrapper, String> bezEigenschaft = new Column<EigenschaftAuspraegungWrapper, String>(
				new ClickableTextCell()) {

			@Override
			public String getValue(EigenschaftAuspraegungWrapper object) {
				// TODO Auto-generated method stub
				return object.getEigenschaftValue();
			}
		};
		
		Column<EigenschaftAuspraegungWrapper, String> wertAuspraegung = new Column<EigenschaftAuspraegungWrapper, String>(
				new EditTextCell()) {

			@Override
			public String getValue(EigenschaftAuspraegungWrapper object) {
//				object.setAuspraegungID(object.getAuspraegungEigenschaftID());
				
				return object.getAuspraegungValue();
			}
			public void onBrowserEvent(Context context, Element elem, EigenschaftAuspraegungWrapper object,
					NativeEvent event) {
				super.onBrowserEvent(context, elem, object, event);
				
				if (event.getKeyCode() == KeyCodes.KEY_ENTER){
					setFieldUpdater(new FieldUpdater<EigenschaftAuspraegungWrapper, String>() {
						
						@Override
						public void update(int index, EigenschaftAuspraegungWrapper object, String value) {
							object.setEigenschaftValue(value);
							sm.getSelectedObject().setAuspraegungValue(value);
							sm.getSelectedObject().setAuspraegungID(object.getAuspraegungID());
							eigaus.setWert(object.getAuspraegungValue());
							eigaus.setID(object.getAuspraegungID());
							eigaus.setEigenschaftsID(object.getEigenschaftID());
							eigaus.setKontaktID(kontakt1.getID());
							eigaus.setStatus(0);
							
							eigaus.setWert(sm.getSelectedObject().getAuspraegungValue());
							verwaltung.updateAuspraegung(eigaus, new AuspraegungBearbeitenCallback());
						}	
						
					});

				}
				
			};
		
		};
		
		ctf = new CellTableForm(kontakt1);
		ctf.addColumn(bezEigenschaft, "Eigenschaft");
		ctf.addColumn(wertAuspraegung, "Wert");
		add(ctf);
		add(hpanel2);

		ctf.setSelectionModel(sm);
		
		
	}
	
	class FindListMeineKontakte implements AsyncCallback<Kontaktliste> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Da hat etwas nicht funktioniert");
			
		}

		@Override
		public void onSuccess(Kontaktliste result) {
			kList = result;
			verwaltung.insertKontaktKontaktliste(kontakt1.getID(), kList.getID(), new InsertIntoMeineKontakte());
			
		}
		
	}
	
	class InsertIntoMeineKontakte implements AsyncCallback<KontaktKontaktliste> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Der Kontakt " + kontakt1.getName() + " konnte nicht der Kontaktliste 'Meine Kontakte' hinzugefügt werden");
			
		}

		@Override
		public void onSuccess(KontaktKontaktliste result) {
			Window.alert("Der Kontakt " + kontakt1.getName() + " wurde der Kontaktliste 'Meine Kontakte' hinzugefügt");
			
		}
		
	}

	class KontaktErstellenCallback implements AsyncCallback<Kontakt> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Kontakt wurde nicht erstellt");
		}

		@Override
		public void onSuccess(Kontakt result) {
			kontakt1.setID(result.getID());
			kontakt1.setName(result.getName());
			Window.alert("Kontakt " + result.getName() + " wurde  erstellt");
			verwaltung.insertBasicAuspraegung("", 0, result.getID(), new BasicAuspraegungenCallback());
			verwaltung.findKontaktlisteByBezeichnung("Meine Kontakte", new FindListMeineKontakte());
			
		}

	}

	class BasicAuspraegungenCallback implements AsyncCallback<Vector<Eigenschaftauspraegung>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("die leeren ausprägungen wurden NICHT erstellt ");
		}

		@Override
		public void onSuccess(Vector<Eigenschaftauspraegung> result) {
			Window.alert("die leeren ausprägungen wurden erstellt ");
			html2.setVisible(true);
			eigenschaftName.setVisible(true);
			auspraegungName.setVisible(true);
			txt_Eigenschaft.setVisible(true);
			txt_Auspraegung.setVisible(true);
			addRow.setVisible(true);

			fillTable();


			KeyDownHandler kdh = new KeyDownHandler() {

				@Override
				public void onKeyDown(KeyDownEvent event) {
					if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {

						verwaltung.updateAuspraegung(eigaus, new AuspraegungBearbeitenCallback());

					}

				}

			};

			ctf.addKeyDownHandler(kdh);

		}

	}

	class EigenschaftHinzufuegenCallback implements AsyncCallback<Eigenschaft> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Die Neue Eigenschaft wurde nicht hinzugefügt");
		}

		@Override
		public void onSuccess(Eigenschaft result) {
			Window.alert("Die Neue Eigenschaft: " + result.getBezeichnung() + " wurde  hinzugefügt");
			verwaltung.insertAuspraegung(txt_Auspraegung.getText(), 0, result.getID(), kontakt1.getID(),
					new AuspraegungHinzufuegenCallback());
		}

	}

	class AuspraegungHinzufuegenCallback implements AsyncCallback<Eigenschaftauspraegung> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Die Neue Ausprägung wurde  nicht hinzugefügt");
		}

		@Override
		public void onSuccess(Eigenschaftauspraegung result) {
			Window.alert("Die Neue Ausprägung wurde  hinzugefügt");
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
			Window.alert("Sie haben die Ausprägung " + result.getWert() + " angepasst");
		}

	}

}
