package de.hdm.itprojektgruppe4.client.gui;


import java.util.Date;
import java.util.Vector;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.i18n.client.DateTimeFormat;

import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
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
	private Label lbl_NewAuspraegung = new Label("Auspraegung: ");
	private TextBox txt_Auspraegung = new TextBox();
	private Date date = new Date();
	
	private KeyDownHandler changeNameHandler = new ChangeNameHandler(); 
	
	private CellTableForm ctf = null;
	private ButtonCell deletebtn = new ButtonCell();
	private ClickableTextCell bezeigenschaft = new ClickableTextCell();
	private EditTextCell wertauspraegung = new EditTextCell();
	private CellTableForm.ColumnEigenschaft bezEigenschaft = ctf.new ColumnEigenschaft(bezeigenschaft);
	private CellTableForm.ColumnAuspraegung wertAuspraegung = ctf.new ColumnAuspraegung(wertauspraegung){
		
		public void onBrowserEvent(Context context, Element elem, EigenschaftAuspraegungWrapper object, NativeEvent event) {
			
			super.onBrowserEvent(context, elem, object, event);
			ctf.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
			if (event.getKeyCode() == KeyCodes.KEY_ENTER){
				
				
				setFieldUpdater(new FieldUpdater<EigenschaftAuspraegungWrapper, String>() {
					
					@Override
					public void update(int index, EigenschaftAuspraegungWrapper object, String value) {

					
						object.setEigenschaftValue(value);
						selectionModel.getSelectedObject().setAuspraegungValue(value);
						selectionModel.getSelectedObject().setAuspraegungID(object.getAuspraegungID());
						eigaus.setWert(object.getAuspraegungValue());
						eigaus.setID(object.getAuspraegungID());
						eigaus.setEigenschaftsID(object.getEigenschaftID());
						eigaus.setKontaktID(kon.getID());
						eigaus.setStatus(0);
						
						eigaus.setWert(selectionModel.getSelectedObject().getAuspraegungValue());
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
	
	private SingleSelectionModel<EigenschaftAuspraegungWrapper> selectionModel = new SingleSelectionModel<EigenschaftAuspraegungWrapper>();
	private CellTableForm.ColumnDeleteBtn deleteBtn = ctf.new ColumnDeleteBtn(deletebtn);
	
	public UpdateKontaktForm(Kontakt kon) {

		this.kon = kon;
	}
	
	public void onLoad() {
	
		deleteBtn.setFieldUpdater(new DeleteFieldUpdater());
		
		super.onLoad();
		
		fmt.format(date);
		RootPanel.get("Buttonbar").clear();
		ctf = new CellTableForm(kon);
		txt_KontaktName.setText(kon.getName());
	
		hpanelDetails.setHeight("35px");
		hpanelDetails.add(lbl_KontaktName);
		hpanelDetails.add(txt_KontaktName);

		vpanelDetails.add(hpanelDetails);
		vpanelDetails.add(ctf);
		vpanelDetails.add(hpanelAdd);
		hpanelAdd.add(lbl_NewEigenschaft);
		hpanelAdd.add(eigenschaftSugBox);
		hpanelAdd.add(lbl_NewAuspraegung);
		hpanelAdd.add(txt_Auspraegung);
		hpanelAdd.add(addRow);
		wertAuspraegung.setSortable(true);
		ctf.setSelectionModel(selectionModel);
		ctf.addColumn(bezEigenschaft, "Eigenschaft: ");
		ctf.addColumn(wertAuspraegung, "Wert: ");
		ctf.addColumn(deleteBtn, "Löschen");
	
		RootPanel.get("Buttonbar").add(cancelBtn);
		
		this.add(vpanelDetails);
		
		verwaltung.findAllEigenschaft(new AlleEigenschaftCallback());
		
		cancelBtn.addClickHandler(new CancelClick()); 

		addRow.addClickHandler(new ClickAddRowHandler());
		
		txt_KontaktName.addKeyDownHandler(changeNameHandler);

	}
	
	class DeleteFieldUpdater implements FieldUpdater<EigenschaftAuspraegungWrapper, String>{
		@Override
		public void update(int index, EigenschaftAuspraegungWrapper object, String value) { 
			ea.setAuspraegung(object.getAuspraegung());
			ea.setEigenschaft(object.getEigenschaft());
//			kon.setModifikationsdatum(date);
			verwaltung.deleteEigenschaftUndAuspraegung(ea, new AuspraegungHybridLoeschenCallback());
			verwaltung.updateKontakt(kon, new KontaktModifikationsdatumCallback());
			
		}
	}
		
	class CancelClick implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			verwaltung.findKontaktByID(kon.getID(), new FindKontaktCallback());
			
		}		
	}
		
	class FindKontaktCallback implements AsyncCallback<Kontakt>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Kontakt result) {
			KontaktForm kf = new KontaktForm(result);
			
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(kf);
		}
		
	}
	
	class ClickAddRowHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
//			kon.setModifikationsdatum(date);
			
			ctf.addRow(eigenschaftSugBox.getValue(), txt_Auspraegung.getValue());
			verwaltung.insertEigenschaft(eigenschaftSugBox.getText(), 0, new EigenschaftEinfuegenCallback());
			verwaltung.updateKontakt(kon, new KontaktModifikationsdatumCallback());
			
		}
		}
	
	
	
	class ChangeNameHandler implements KeyDownHandler{

		@Override
		public void onKeyDown(KeyDownEvent event) {
			// TODO Auto-generated method stub
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				kon.setName(txt_KontaktName.getValue());
				verwaltung.updateKontakt(kon, new KontaktModifikationsdatumCallback());
			}
		}
		
		
	}
	
	
	
	class KontaktModifikationsdatumCallback implements AsyncCallback<Kontakt>{

		@Override
		public void onFailure(Throwable caught) {

			
		}

		@Override
		public void onSuccess(Kontakt result) {
			verwaltung.findHybrid(kon, new ReloadCallback());
		}
		
	}
	
	class EigenschaftEinfuegenCallback implements AsyncCallback<Eigenschaft>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Eigenschaft result) {
			
			if (result == null) {
				
				
				verwaltung.findEigByBezeichnung(eigenschaftSugBox.getText(), new FindEigenschaftCallBack());
				
				
			}else{
			
			eig1.setID(result.getID());
			verwaltung.insertAuspraegung(txt_Auspraegung.getText(), 0, eig1.getID(), kon.getID(), new AuspraegungEinfuegenCallback());
			
			}
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
			eigenschaftSugBox.setText("");
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
			
//			Window.alert("wurde aktualisiert");
			eigaus.setWert(result.getWert());
			selectionModel.setSelected(null, true);
			verwaltung.findHybrid(kon, new ReloadCallback());
//			eigaus.setWert(result.getWert());
		}

	}
	
	class AuspraegungHybridLoeschenCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Hat nicht funktioniert");
			
		}

		@Override
		public void onSuccess(Void result) {
			ctf.deleteRow(ea);
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
//			Window.alert("TEST");
			ctf.setRowData(0, result);
			ctf.setRowCount(result.size(), true);
		}
		
	}
	
	class AlleEigenschaftCallback implements AsyncCallback<Vector<Eigenschaft>> {

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
	
	class FindEigenschaftCallBack implements AsyncCallback<Eigenschaft>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Eigenschaft result) {
			// TODO Auto-generated method stub
			
			verwaltung.insertAuspraegung(txt_Auspraegung.getText(), 0, result.getID(), kon.getID(),  new AuspraegungEinfuegenCallback());
		}
		
	}
}

