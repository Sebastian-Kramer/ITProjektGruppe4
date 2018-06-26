package de.hdm.itprojektgruppe4.client.gui;

import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.RowHoverEvent;
import com.google.gwt.user.cellview.client.RowHoverEvent.Handler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.PopupPanel;
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
	private HTML HTMLForm = new HTML(
			"Sie können auf dieser Seite nach Kontakten anhand von ihrem Namen suchen, "
			+ "<br>oder indem Sie eine Eigenschaftsausprägung eingeben, welche der gesuchte Kontakt beinhaltet</br>");
	private Label teilInfo = new Label("Die Ausprägung gehört zu folgendem Kontakt: ");
	private Label teilInfo2 = new Label("");
	
	private TextBox tboxKontaktname = new TextBox();
	
	private TextBox tboxAuspraegung = new TextBox();
	
	private Button KontaktSuchen = new Button("Kontakt suchen");
	
	private Button AuspraegungSuchen = new Button("Auspraegung suchen"); 
	
	private Button KontaktKontaktAnzeigenButton = new Button("Kontakt anzeigen");
	
	private Button AuspraegungKontaktAnzeigenButton = new Button("Zugehörigen Kontakt anzeigen");
	
	private VerticalPanel vpanel = new VerticalPanel();
	
	private FlexTable flextableKontakt = new FlexTable();
	
	private FlexTable flextableAuspraegung = new FlexTable();
	
	private DecoratorPanel KontaktSuchenPanel = new DecoratorPanel();
	
	private DecoratorPanel AuspraegungSuchenPanel = new DecoratorPanel();
	
	private VerticalPanel vpanel1 = new VerticalPanel();
	
	private VerticalPanel vpanel2 = new VerticalPanel();
	
	private VerticalPanel vpanel3 = new VerticalPanel();
	
	private VerticalPanel vpanelPopUp = new VerticalPanel();
	
	private MultiWordSuggestOracle  KontaktOracle = new MultiWordSuggestOracle();
	
	private SuggestBox suggestionKontaktBox = new SuggestBox(KontaktOracle);
	
	
	
	private MultiWordSuggestOracle  AuspraegungOracle = new MultiWordSuggestOracle();
	final SingleSelectionModel<Kontakt> sm = new SingleSelectionModel<Kontakt>();
	final SingleSelectionModel<EigenschaftAuspraegungWrapper> ssm = new SingleSelectionModel<EigenschaftAuspraegungWrapper>();
	private CellTable<Kontakt> ctkontakt = new CellTable<Kontakt>();
	
	private CellTableForm ctAus = null;
	private ClickableTextCell bezeigenschaft = new ClickableTextCell();
	private ClickableTextCell wertauspraegung = new ClickableTextCell();
	private ClickableTextCell AusKontaktname = new ClickableTextCell();
	private CellTableForm.ColumnEigenschaft bezEigenschaft = ctAus.new ColumnEigenschaft(bezeigenschaft);
	private CellTableForm.ColumnAuspraegung wertAuspraegung = ctAus.new ColumnAuspraegung(wertauspraegung);
	private CellTableForm.ColumnKontaktName kontaktname = ctAus.new ColumnKontaktName(AusKontaktname);
			 
	
	
	private Kontakt test = new Kontakt();
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
		
		Column<EigenschaftAuspraegungWrapper, String> kontaktnameAuspraegung = new Column<EigenschaftAuspraegungWrapper, String>(
				new ClickableTextCell()){
		
					@Override
					public String getValue(EigenschaftAuspraegungWrapper object) {
						// TODO Auto-generated method stub
//						verwaltung.findEinenKontaktByAuspraegungID(object.getAuspraegungID(), new KontaktNameCallback());
						final Kontakt kk = new Kontakt();
						verwaltung.findKontaktByID(object.getAuspraegungKontaktID(),new AsyncCallback<Kontakt>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								Window.alert("fail");
							}

							@Override
							public void onSuccess(Kontakt result) {
								// TODO Auto-generated method stub
								Window.alert(""+result.getName());
								kk.setName(result.getName());
							}
						});
						return kk.getName();
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

		ctAus.setSelectionModel(ssm);
		
		ctAus.addColumn(bezEigenschaft, "Eig");
		ctAus.addColumn(wertAuspraegung, "Aus");
		ctAus.addColumn(kontaktnameAuspraegung, "Zugehöriger Kontakt");
		vpanel.add(HTMLForm);
		vpanel2.add(ctAus);
		vpanel1.add(ctkontakt);
		vpanel3.add(KontaktKontaktAnzeigenButton);
		vpanel3.add(AuspraegungKontaktAnzeigenButton);
		this.add(vpanel);
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
		
		
		
		ctAus.addRowHoverHandler(new Handler() {
			
			@Override
			public void onRowHover(RowHoverEvent event) {
				// TODO Auto-generated method stub
				int id;
				id = ssm.getSelectedObject().getAuspraegungKontaktID();
				verwaltung.findKontaktByID(id,new KontaktHoverCallback());
			}
		});
	}
	
	
	
	private class PopUpInfo extends PopupPanel {
		
		public PopUpInfo(){
			
			super(true);
			
			addDomHandler(new MouseOutHandler() {
				
				@Override
				public void onMouseOut(MouseOutEvent event) {
					// TODO Auto-generated method stub
					hide();
				}
			}, MouseOutEvent.getType());
			
			setPopupPosition(ctAus.getAbsoluteLeft(), ctAus.getAbsoluteTop());
			
		}
		
	}
	
	class KontaktHoverCallback implements AsyncCallback<Kontakt>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Kontakt result) {
			// TODO Auto-generated method stub
			teilInfo2.setText(result.getName());
			vpanelPopUp.add(teilInfo);
			vpanelPopUp.add(teilInfo2);
			PopUpInfo pop = new PopUpInfo();
			pop.setWidget(vpanelPopUp);
			pop.show();
		}
		
	}
	
	
	class KontaktNameCallback implements AsyncCallback<Kontakt>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Kontakt result) {
			// TODO Auto-generated method stub
			Window.alert(""+result);
//			kk.setName(result.getName());
			
		}
		
	}
	
	
	class AuspraegungKontaktAnzeigenHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
			AuspraegungKontaktAnzeigenButton.setText("");
			KontaktKontaktAnzeigenButton.setVisible(false);
			EigenschaftAuspraegungWrapper eigaus = new EigenschaftAuspraegungWrapper();
	

			eigaus.setAuspraegungID(ssm.getSelectedObject().getAuspraegungID());

		

			verwaltung.findKontaktByAuspraegungID(eigaus.getAuspraegungID(), new AuspraegungKontaktAnzeigenCallback());
		}
		
	}
	
	class KontaktKontaktAnzeigenHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			KontaktKontaktAnzeigenButton.setText("");
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
	
	


