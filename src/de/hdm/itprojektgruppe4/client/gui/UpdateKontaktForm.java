package de.hdm.itprojektgruppe4.client.gui;

import java.util.ArrayList;
import java.util.Vector;

import org.datanucleus.state.CallbackHandler;

import com.google.appengine.api.files.FileServicePb.ShuffleRequest.Callback;
import com.google.gwt.cell.client.Cell.Context;
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

	private Kontakt kon = new Kontakt();
	private CellTableForm ctf1 = new CellTableForm(kon);

	private Label lbl_KontaktName = new Label("Kontaktname: ");
	private TextBox txt_KontaktName = new TextBox();
	private TextBox txt_Eigenschaft = new TextBox();
	private TextBox txt_Auspraegung = new TextBox();
	private Button save = new Button("Speichern");
	private Button cancel = new Button("Cancel");
	private Button addRow = new Button("Add");
	private Button saveRow = new Button("Save Changes");

	private EigenschaftAuspraegungHybrid ea = new EigenschaftAuspraegungHybrid();

	private Eigenschaft eig1 = new Eigenschaft();
	private Eigenschaftauspraegung eigaus = new Eigenschaftauspraegung();

	private SingleSelectionModel<EigenschaftAuspraegungHybrid> sm = new SingleSelectionModel<EigenschaftAuspraegungHybrid>();

	public UpdateKontaktForm(Kontakt kon) {

		this.kon = kon;
	}

	public void onLoad() {

		super.onLoad();

		// Window.alert("die id ist: " + kon.getID() + "name: " +
		// kon.getName());
		verwaltung.findKontaktByID(kon.getID(), new AsyncCallback<Kontakt>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Kontakt result) {
				txt_KontaktName.setText(result.getName());

			}

		});

		final CellTableForm ctf = new CellTableForm(kon);

		txt_KontaktName.setText(kon.getName());
		// txt_Eigenschaft.setText(kon.ge);

		// hpanelDetails.add(lbl_Eigenschaft);
		// hpanelDetails.add(txt_Eigenschaft);

		hpanelDetails.add(lbl_KontaktName);
		hpanelDetails.add(txt_KontaktName);
		hpanelDetails.add(save);
		hpanelDetails.add(cancel);

		vpanelDetails.add(hpanelDetails);
		vpanelDetails.add(ctf);
		vpanelDetails.add(addRow);
		vpanelDetails.add(saveRow);
		vpanelDetails.add(txt_Auspraegung);
		vpanelDetails.add(txt_Eigenschaft);

		this.add(vpanelDetails);

		ea.setAuspraegung(txt_Auspraegung.getText());
		ea.setEigenschaft(txt_Eigenschaft.getText());

		// System.out.println(ctf.sm.getLastSelectedObject().getAuspraegung());
		// Window.alert(""+ ctf.getSm().getSelectedObject().getAuspraegung());

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
		



		// sm.addSelectionChangeHandler(new SelectionChangeEvent.Handler(){
		//
		// @Override
		// public void onSelectionChange(SelectionChangeEvent event) {
		// EigenschaftAuspraegungHybrid selected = sm.getSelectedObject();
		// selected.setAuspraegung(ctf.getWertAuspraegung().getValue(selected));
		//
		// eigaus.setWert(selected.getAuspraegung());
		// eigaus.setEigenschaftsID(selected.getEigenschaftID());
		// Window.alert(" " + eigaus.getWert());
		//// eigaus.setEigenschaftsID(event.getSelectedItem().getEigenschaftID());
		//// eigaus.setWert(event.getSm().getSelectedObject().getAuspraegung());
		//
		// }
		//
		// });

		saveRow.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Window.alert("die neue auspraegung lautet " + sm.getSelectedObject().getAuspraegung());
			}
		});

		addRow.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ctf.addRow(txt_Eigenschaft.getValue(), txt_Auspraegung.getValue());

				verwaltung.insertEigenschaft(txt_Eigenschaft.getText(), 0, new AsyncCallback<Eigenschaft>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Eigenschaft result) {
						// TODO Auto-generated method stub

						eig1.setID(result.getID());

						verwaltung.insertAuspraegung(txt_Auspraegung.getText(), 0, eig1.getID(), kon.getID(),
								new AsyncCallback<Eigenschaftauspraegung>() {

									@Override
									public void onFailure(Throwable caught) {
										// TODO Auto-generated method stub

									}

									@Override
									public void onSuccess(Eigenschaftauspraegung result) {
										// TODO Auto-generated method stub
									}
								});
					}
				});

			}

		});

		verwaltung.findAllEigenschaft(new AsyncCallback<Vector<Eigenschaft>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Vector<Eigenschaft> result) {
				Window.alert("alle Eigenschaften m�ssten gefunden sein");

				for (Eigenschaft eig : result)
					;

			}

		});

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

	// class AuspraegungBearbeiten implements SelectionHandler{
	//
	// @Override
	// public void onSelection(SelectionEvent event) {
	// // TODO Auto-generated method stub
	//
	//
	// }
	//
	// }

}
