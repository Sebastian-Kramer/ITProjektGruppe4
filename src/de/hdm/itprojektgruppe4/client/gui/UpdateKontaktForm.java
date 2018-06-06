package de.hdm.itprojektgruppe4.client.gui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import org.datanucleus.state.CallbackHandler;
 

import com.google.appengine.api.files.FileServicePb.ShuffleRequest.Callback;
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
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.event.dom.client.KeyPressHandler;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.client.EigenschaftAuspraegungWrapper;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;

public class UpdateKontaktForm extends VerticalPanel {

	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();

	FlexTable ft_KontaktBearbeiten = new FlexTable();

	private VerticalPanel vpanelDetails = new VerticalPanel();
	private HorizontalPanel hpanelDetails = new HorizontalPanel();
	private HorizontalPanel hpanelAdd = new HorizontalPanel();

	private Kontakt kon = new Kontakt();


	private Label lbl_KontaktName = new Label("Kontaktname: ");
	private TextBox txt_KontaktName = new TextBox();
	private Button addRow = new Button("Hinzufügen");
	private Label lbl_NewEigenschaft = new Label("Eigenschaft: ");
	private TextBox txt_Eigenschaft = new TextBox();
	private Label lbl_NewAuspraegung = new Label("Auspraegung: ");
	private TextBox txt_Auspraegung = new TextBox();
	private Date date = new Date();
//	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
	DateTimeFormat fmt = DateTimeFormat.getFormat("dd.MM.yyyy");
	private Button cancelBtn = new Button("Cancel");
	

	private EigenschaftAuspraegungWrapper ea = new EigenschaftAuspraegungWrapper();

	private Eigenschaft eig1 = new Eigenschaft();
	private Eigenschaftauspraegung eigaus = new Eigenschaftauspraegung();


	private SingleSelectionModel<EigenschaftAuspraegungWrapper> sm = new SingleSelectionModel<EigenschaftAuspraegungWrapper>();
	private CellTableForm ctf = null;
	
	
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
			// TODO Auto-generated method stub
			return object.getAuspraegungValue();
		}
		
		public void onBrowserEvent(Context context, Element elem, EigenschaftAuspraegungWrapper object,
				NativeEvent event) {
			super.onBrowserEvent(context, elem, object, event);
			
			if (event.getKeyCode() == KeyCodes.KEY_ENTER){
				kon.setModifikationsdatum(date);
				verwaltung.updateAuspraegung(eigaus, new AuspraegungBearbeitenCallback());
				verwaltung.updateKontakt(kon, new KontaktModifikationsdatumCallback());
			}
			
		};
		
	};
//	public Column<EigenschaftAuspraegungHybrid, String> getWertAuspraegung() {
//		return wertAuspraegung;
//	}
	
	
	
	public UpdateKontaktForm(Kontakt kon) {

		this.kon = kon;
	}
	
	public void onLoad() {
	
		super.onLoad();

//		simpleDateFormat.format(date);
		fmt.format(date);
		verwaltung.findAllEigenschaft(new AllEigenschaftCallback());
		RootPanel.get("Buttonbar").clear();
		ctf = new CellTableForm(kon);

		txt_KontaktName.setText(kon.getName());
	
		hpanelDetails.setHeight("35px");
		hpanelDetails.add(lbl_KontaktName);
		hpanelDetails.add(txt_KontaktName);
//		hpanelDetails.add(cancelBtn);

		vpanelDetails.add(hpanelDetails);
		
		vpanelDetails.add(ctf);
		vpanelDetails.add(hpanelAdd);
		hpanelAdd.add(lbl_NewEigenschaft);
		hpanelAdd.add(txt_Eigenschaft);
		hpanelAdd.add(lbl_NewAuspraegung);
		hpanelAdd.add(txt_Auspraegung);
		hpanelAdd.add(addRow);
		
		ctf.addColumn(bezEigenschaft, "Eigenschaft:");
		ctf.addColumn(wertAuspraegung, "Wert:");
		
		
		RootPanel.get("Buttonbar").add(cancelBtn);
		
		this.add(vpanelDetails);

		ea.setAuspraegungValue(txt_Auspraegung.getText());
		ea.setEigenschaftValue(txt_Eigenschaft.getText());

		ctf.setSelectionModel(sm);
		
//		ctf.getWertAuspraegung().setFieldUpdater(new FieldUpdater<EigenschaftAuspraegungHybrid, String>() {
//
//			@Override
//			public void update(int index, EigenschaftAuspraegungHybrid object, String value) {
//				sm.getSelectedObject().setAuspraegung(value);
//				sm.getSelectedObject().setAuspraegungID(object.getAuspraegungID());
//			
//				
//				eigaus.setID(sm.getSelectedObject().getAuspraegungID());
//				eigaus.setWert(sm.getSelectedObject().getAuspraegung());
//				eigaus.setStatus(0);
//				eigaus.setKontaktID(kon.getID());
//				eigaus.setEigenschaftsID(sm.getSelectedObject().getEigenschaftID());	
//				
//				
//			}	
//				
//		});
	
		
		KeyDownHandler kdh = new KeyDownHandler(){


				@Override
				public void onKeyDown(KeyDownEvent event) {
					if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
						kon.setModifikationsdatum(date);
						verwaltung.updateAuspraegung(eigaus, new AuspraegungBearbeitenCallback());
						verwaltung.updateKontakt(kon, new KontaktModifikationsdatumCallback());
					}
					
				}
				
			};	
			
		ctf.addKeyDownHandler(kdh);	
	
		Column<EigenschaftAuspraegungWrapper, String> deleteBtn = new Column<EigenschaftAuspraegungWrapper, String>(
				new ButtonCell()) {
			

			@Override
			public String getValue(EigenschaftAuspraegungWrapper x) {
				// TODO Auto-generated method stub
				return "x";
			}
			};
				
		ctf.addColumn(deleteBtn, "");
	
		cancelBtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				KontaktForm kf = new KontaktForm(kon);
				
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(kf);
				
			}
		});
		
		
		deleteBtn.setFieldUpdater(new FieldUpdater<EigenschaftAuspraegungWrapper, String>() {
					
				@Override
				public void update(int index, EigenschaftAuspraegungWrapper object, String value) {
					
					ea.setAuspraegungID(object.getAuspraegungID());
					ea.setEigenschaftID(object.getEigenschaftID());
					kon.setModifikationsdatum(date);
					verwaltung.deleteEigenschaftUndAuspraegung(ea, new AuspraegungHybridLoeschenCallback());
					verwaltung.updateKontakt(kon, new KontaktModifikationsdatumCallback());
				}
			});
			
		addRow.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				kon.setModifikationsdatum(date);

				ctf.addRow(txt_Eigenschaft.getValue(), txt_Auspraegung.getValue());

				verwaltung.insertEigenschaft(txt_Eigenschaft.getText(), 0, new EigenschaftEinfuegenCallback());
				verwaltung.updateKontakt(kon, new KontaktModifikationsdatumCallback());
		}

		});}
	
	class KontaktModifikationsdatumCallback implements AsyncCallback<Kontakt>{

		@Override
		public void onFailure(Throwable caught) {

			
		}

		@Override
		public void onSuccess(Kontakt result) {
			verwaltung.findHybrid(kon, new ReloadCallback());
		}
		
	}
	
	class AllEigenschaftCallback implements AsyncCallback<Vector<Eigenschaft>>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Vector<Eigenschaft> result) {
			
			for (Eigenschaft eig : result)
				;

			for (Eigenschaft eig : result) {

			}
		}
		
	}
	
	class EigenschaftEinfuegenCallback implements AsyncCallback<Eigenschaft>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Eigenschaft result) {
			eig1.setID(result.getID());
			verwaltung.insertAuspraegung(txt_Auspraegung.getText(), 0, eig1.getID(), kon.getID(), new AuspraegungEinfuegenCallback());
			
		}
		
	}
	
	class AuspraegungEinfuegenCallback implements AsyncCallback<Eigenschaftauspraegung>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Eigenschaftauspraegung result) {
			verwaltung.findHybrid(kon, new ReloadCallback());
			txt_Eigenschaft.setText("");
			txt_Auspraegung.setText("");
		}
		
	}
	
	class AuspraegungBearbeitenCallback implements AsyncCallback<Eigenschaftauspraegung> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(" Hat nicht funktioniert ");

		}

		@Override
		public void onSuccess(Eigenschaftauspraegung result) {
			Window.alert("Sie haben die Auspr�gung " + result.getWert() + " angepasst");
			eigaus.setWert(result.getWert());
		}

	}
	
	class AuspraegungHybridLoeschenCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("nein gunners nein");
			
		}

		@Override
		public void onSuccess(Void result) {
			verwaltung.findHybrid(kon, new ReloadCallback());
			
//			ctf.redraw();
			
		}
	}
	
	class ReloadCallback implements AsyncCallback<Vector<EigenschaftAuspraegungWrapper>>{

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

}
