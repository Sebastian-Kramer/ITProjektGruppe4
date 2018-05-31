package de.hdm.itprojektgruppe4.client.gui;

import java.util.ArrayList;
import java.util.Vector;

import org.datanucleus.state.CallbackHandler;
 

import com.google.appengine.api.files.FileServicePb.ShuffleRequest.Callback;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.ClickableTextCell;
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
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.event.dom.client.KeyPressHandler;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.EigenschaftAuspraegungHybrid;
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
	
	private Button cancel = new Button("Cancel");
	

	private EigenschaftAuspraegungHybrid ea = new EigenschaftAuspraegungHybrid();

	private Eigenschaft eig1 = new Eigenschaft();
	private Eigenschaftauspraegung eigaus = new Eigenschaftauspraegung();


	private SingleSelectionModel<EigenschaftAuspraegungHybrid> sm = new SingleSelectionModel<EigenschaftAuspraegungHybrid>();
	private CellTableForm ctf = null;

	public UpdateKontaktForm(Kontakt kon) {

		this.kon = kon;
	}

	public void onLoad() {

		super.onLoad();

		Window.alert("die id ist: " + kon.getID() + "name: " + kon.getName());
		verwaltung.findAllEigenschaft(new AllEigenschaftCallback());
		
		ctf = new CellTableForm(kon);

		txt_KontaktName.setText(kon.getName());
	
		hpanelDetails.setHeight("35px");
		hpanelDetails.add(lbl_KontaktName);
		hpanelDetails.add(txt_KontaktName);
		hpanelDetails.add(cancel);

		vpanelDetails.add(hpanelDetails);
		vpanelDetails.add(ctf);
		vpanelDetails.add(hpanelAdd);
		hpanelAdd.add(lbl_NewEigenschaft);
		hpanelAdd.add(txt_Eigenschaft);
		hpanelAdd.add(lbl_NewAuspraegung);
		hpanelAdd.add(txt_Auspraegung);
		hpanelAdd.add(addRow);
		
		
		this.add(vpanelDetails);

		ea.setAuspraegung(txt_Auspraegung.getText());
		ea.setEigenschaft(txt_Eigenschaft.getText());

		ctf.setSelectionModel(sm);
		
		ctf.getWertAuspraegung().setFieldUpdater(new FieldUpdater<EigenschaftAuspraegungHybrid, String>() {

			@Override
			public void update(int index, EigenschaftAuspraegungHybrid object, String value) {
				sm.getSelectedObject().setAuspraegung(value);
				sm.getSelectedObject().setAuspraegungID(object.getAuspraegungID());
			
				
				eigaus.setID(sm.getSelectedObject().getAuspraegungID());
				eigaus.setWert(sm.getSelectedObject().getAuspraegung());
				eigaus.setStatus(0);
				eigaus.setKontaktID(kon.getID());
				eigaus.setEigenschaftsID(sm.getSelectedObject().getEigenschaftID());	
				
				
			}	
				
		});
		
		KeyDownHandler kdh = new KeyDownHandler(){


				@Override
				public void onKeyDown(KeyDownEvent event) {
					if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {

						verwaltung.updateAuspraegung(eigaus, new AuspraegungBearbeitenCallback());
						
					}
					
				}
				
			};	
			
		ctf.addKeyDownHandler(kdh);	
	
		Column<EigenschaftAuspraegungHybrid, String> deleteBtn = new Column<EigenschaftAuspraegungHybrid, String>(
				new ButtonCell()) {
			

			@Override
			public String getValue(EigenschaftAuspraegungHybrid x) {
				// TODO Auto-generated method stub
				return "x";
			}
			};
				
		ctf.addColumn(deleteBtn, "");
	
		deleteBtn.setFieldUpdater(new FieldUpdater<EigenschaftAuspraegungHybrid, String>() {
					
				@Override
				public void update(int index, EigenschaftAuspraegungHybrid object, String value) {
					
					ea.setAuspraegungID(object.getAuspraegungID());
					ea.setEigenschaftID(object.getEigenschaftID());
					
					verwaltung.deleteEigenschaftUndAuspraegung(ea, new AuspraegungHybridLoeschenCallback());
					
				}
			});
		
		addRow.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ctf.addRow(txt_Eigenschaft.getValue(), txt_Auspraegung.getValue());

				verwaltung.insertEigenschaft(txt_Eigenschaft.getText(), 0, new EigenschaftEinfuegenCallback());
		}

		});}
	
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
			Window.alert("Sie haben die Ausprägung " + result.getWert() + " angepasst");
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
	
	class ReloadCallback implements AsyncCallback<Vector<EigenschaftAuspraegungHybrid>>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Vector<EigenschaftAuspraegungHybrid> result) {
			ctf.setRowData(0, result);
			ctf.setRowCount(result.size(), true);
		}
		
	}

}
