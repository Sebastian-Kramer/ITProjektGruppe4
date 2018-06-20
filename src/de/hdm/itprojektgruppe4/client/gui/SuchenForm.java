package de.hdm.itprojektgruppe4.client.gui;

import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.client.EigenschaftAuspraegungWrapper;
import de.hdm.itprojektgruppe4.client.gui.CellTableForm.ColumnAuspraegung;
import de.hdm.itprojektgruppe4.client.gui.CellTableForm.ColumnEigenschaft;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;

public class SuchenForm extends VerticalPanel {

	
	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	private Label beschreibungKontakt = new Label("Bitte Geben sie den Kontatknamen ein");
	private Label beschreibungAuspraegung = new Label("Bitte Geben sie eine Ausprägung ein");
	
	private TextBox tboxKontaktname = new TextBox();
	
	private TextBox tboxAuspraegung = new TextBox();
	
	private Button KontaktSuchen = new Button("Kontakt suchen");
	
	private Button AuspraegungSuchen = new Button("Auspraegung suchen"); 
	
	private Button KontaktKontaktAnzeigenButton = new Button("Kontakt anzeigen");
	
	private Button AuspraegungKontaktAnzeigenButton = new Button("Kontakt anzeigen");
	
	private VerticalPanel vpanel = new VerticalPanel();
	
	private FlexTable flextableKontakt = new FlexTable();
	
	private FlexTable flextableAuspraegung = new FlexTable();
	
	private DecoratorPanel KontaktSuchenPanel = new DecoratorPanel();
	
	private DecoratorPanel AuspraegungSuchenPanel = new DecoratorPanel();
	
	private VerticalPanel vpanel1 = new VerticalPanel();
	
	private VerticalPanel vpanel2 = new VerticalPanel();
	
	private VerticalPanel vpanel3 = new VerticalPanel();
	
	private MultiWordSuggestOracle  KontaktOracle = new MultiWordSuggestOracle();
	
	private SuggestBox suggestionKontaktBox = new SuggestBox(KontaktOracle);
	
	private MultiWordSuggestOracle  AuspraegungOracle = new MultiWordSuggestOracle();
	final SingleSelectionModel<Kontakt> sm = new SingleSelectionModel<Kontakt>();
	final SingleSelectionModel<EigenschaftAuspraegungWrapper> ssm = new SingleSelectionModel<EigenschaftAuspraegungWrapper>();
	private CellTable<Kontakt> ctkontakt = new CellTable<Kontakt>();
	
	private CellTableForm ctAus = null;
	private ClickableTextCell bezeigenschaft = new ClickableTextCell();
	private ClickableTextCell wertauspraegung = new ClickableTextCell();
	private CellTableForm.ColumnEigenschaft bezEigenschaft = ctAus.new ColumnEigenschaft(bezeigenschaft);
	private CellTableForm.ColumnAuspraegung wertAuspraegung = ctAus.new ColumnAuspraegung(wertauspraegung);
			 
	public SuchenForm(){
	}
	
	public void onLoad(){
		
		super.onLoad();
		
		Nutzer nutzer = new Nutzer();
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("mail"));
		
		Column<Kontakt, String> kontaktname = new Column<Kontakt, String>(
				new ClickableTextCell()) {

			@Override
			public String getValue(Kontakt object) {
				// TODO Auto-generated method stub
				return object.getName();
			}
		};
		
		
		ctAus = new CellTableForm();
		ctkontakt.addColumn(kontaktname, "Gefundene Kontakte");
		ctkontakt.setSelectionModel(sm);
		ctkontakt.setVisible(false);
		ctAus.setVisible(false);
		KontaktKontaktAnzeigenButton.setVisible(false);
		AuspraegungKontaktAnzeigenButton.setVisible(false);
		
		flextableKontakt.setWidget(0, 0, beschreibungKontakt);
		flextableKontakt.setWidget(0, 1, suggestionKontaktBox);
		flextableKontakt.setWidget(0, 2, KontaktSuchen);
		KontaktSuchenPanel.add(flextableKontakt);
		
		flextableAuspraegung.setWidget(0, 0, beschreibungAuspraegung);
		flextableAuspraegung.setWidget(0, 1, tboxAuspraegung);
		flextableAuspraegung.setWidget(0, 2, AuspraegungSuchen);
		AuspraegungSuchenPanel.add(flextableAuspraegung);
		
		
		KontaktSuchenPanel.setStyleName("DialogboxBackground");
		suggestionKontaktBox.setStyleName("DialogboxBackground");
		AuspraegungSuchenPanel.setStyleName("DialogboxBackground");
		tboxAuspraegung.setStyleName("DialogboxBackground");

		
//		Problem ist:
//		CellTableForm kann mann nicht erstellen ohne einen Kontakt?
//		CellTable kann ich nicht Column Eigenschaft hinzufügen
		
		ctAus.setSelectionModel(ssm);
		
		ctAus.addColumn(bezEigenschaft, "Eig");
		ctAus.addColumn(wertAuspraegung, "Aus");
		vpanel2.add(ctAus);
		vpanel1.add(ctkontakt);
		vpanel3.add(KontaktKontaktAnzeigenButton);
		vpanel3.add(AuspraegungKontaktAnzeigenButton);
		this.add(KontaktSuchenPanel);
		this.add(AuspraegungSuchenPanel);
		
		KontaktSuchen.addClickHandler(new KontaktSuchenButton());
		AuspraegungSuchen.addClickHandler(new AuspraegungSuchenButton());
		verwaltung.findAllKontaktFromNutzer(nutzer.getID(), new  AllKontakteCallBack());
	
		this.add(vpanel1);
		this.add(vpanel2);
		this.add(vpanel3);
		
		
		KontaktKontaktAnzeigenButton.addClickHandler(new KontaktKontaktAnzeigenHandler());
		AuspraegungKontaktAnzeigenButton.addClickHandler(new AuspraegungKontaktAnzeigenHandler());
		
	}
	
	class AuspraegungKontaktAnzeigenHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
			EigenschaftAuspraegungWrapper eigaus = new EigenschaftAuspraegungWrapper();
	

			eigaus.setAuspraegungID(ctAus.getSm().getSelectedObject().getAuspraegungID());

		

			verwaltung.findKontaktByAuspraegungID(eigaus.getAuspraegungID(), new AuspraegungKontaktAnzeigenCallback());
		}
		
	}
	
	class KontaktKontaktAnzeigenHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			Kontakt selected = sm.getSelectedObject();
			KontaktForm kf = new KontaktForm(selected);
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(kf);
		}
		
	}
	
	
	class KontaktSuchenButton implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			Nutzer nutzer = new Nutzer();
			nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
			nutzer.setEmail(Cookies.getCookie("mail"));

			Kontakt k = new Kontakt();
			
			k.setName(tboxKontaktname.getValue());
			k.setNutzerID(nutzer.getID());
			
			k.setName(suggestionKontaktBox.getValue());
			k.setNutzerID(nutzer.getID());
			
			AuspraegungKontaktAnzeigenButton.setVisible(false);
			KontaktKontaktAnzeigenButton.setVisible(true);
			
		
			
			verwaltung.findKontaktByNameAndNutzerID(k, new AsyncCallback<Vector<Kontakt>>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(Vector<Kontakt> result) {
					// TODO Auto-generated method stub
					ctkontakt.setRowData(0, result);
					ctkontakt.setRowCount(result.size(), true);
					ctAus.setVisible(false);
					ctkontakt.setVisible(true);
//					gefundene = result;
					
					
				
				}
			});
		}
		}
	
	class AuspraegungSuchenButton implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			Nutzer nutzer = new Nutzer();
			nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
			nutzer.setEmail(Cookies.getCookie("mail"));
			
			EigenschaftAuspraegungWrapper eigaus = new EigenschaftAuspraegungWrapper();
			
			eigaus.setAuspraegungValue(tboxAuspraegung.getValue());
			Window.alert(tboxAuspraegung.getValue());
			KontaktKontaktAnzeigenButton.setVisible(false);
			AuspraegungKontaktAnzeigenButton.setVisible(true);
			verwaltung.getAuspraegungByWert(tboxAuspraegung.getValue(), new FindAuspraegungCallback());
			
		}
		
	}
	
	
	
	class AuspraegungKontaktAnzeigenCallback implements AsyncCallback<Kontakt>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Kontakt result) {
			// TODO Auto-generated method stub
			Window.alert("1");
			KontaktForm kf = new KontaktForm(result);
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(kf);
		}
		
	}
	
	class FindAuspraegungCallback implements AsyncCallback<Vector<EigenschaftAuspraegungWrapper>>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}
			
		@Override
		public void onSuccess(Vector<EigenschaftAuspraegungWrapper> result) {
			// TODO Auto-generated method stub
			for(EigenschaftAuspraegungWrapper eaw : result){
				Window.alert(eaw.getAuspraegungValue());
			}
			ctAus.setRowData(0, result);
			ctAus.setRowCount(result.size(), true);
			ctkontakt.setVisible(false);
			ctAus.setVisible(true);
			
			
			
		}
		
	}
	

	class AllKontakteCallBack implements AsyncCallback<Vector<Kontakt>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Vector<Kontakt> result) {
			// TODO Auto-generated method stub

			Kontakt k = new Kontakt();
			
			for (Kontakt kontakt : result) {
				
				KontaktOracle.add(kontakt.getName());
			}
			
			
			}
		}

		
	}
	
	


