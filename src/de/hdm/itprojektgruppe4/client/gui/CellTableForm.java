package de.hdm.itprojektgruppe4.client.gui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;

import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.Cookies;

import com.google.gwt.user.client.Element;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.dom.client.KeyDownHandler;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.client.EigenschaftAuspraegungWrapper;
import de.hdm.itprojektgruppe4.client.gui.UpdateKontaktForm.AuspraegungBearbeitenCallback;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;

public class CellTableForm extends CellTable<EigenschaftAuspraegungWrapper> {


	private Kontakt kontakt = new Kontakt();

	private ImageCell imageCell = new ImageCell();
	
//	final UpdateKontaktForm.AuspraegungBearbeitenCallback innerClass1 = kontaktForm.new AuspraegungBearbeitenCallback();
//	final UpdateKontaktForm.KontaktModifikationsdatumCallback innerClass2 = kontaktForm.new KontaktModifikationsdatumCallback();
//	final UpdateKontaktForm.AuspraegungHybridLoeschenCallback innerClass3 = kontaktForm.new AuspraegungHybridLoeschenCallback();
//	final TeilhaberschaftForm.a

	
	private List<EigenschaftAuspraegungWrapper> eList = new ArrayList<>();
	KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();

	private LinkedList<EigenschaftAuspraegungWrapper> getUserList() {
		LinkedList<EigenschaftAuspraegungWrapper> list = new LinkedList<EigenschaftAuspraegungWrapper>();
		return list;
	}

	final ListDataProvider<EigenschaftAuspraegungWrapper> model = new ListDataProvider<EigenschaftAuspraegungWrapper>(
			getUserList());

	private SingleSelectionModel<EigenschaftAuspraegungWrapper> sm = new SingleSelectionModel<EigenschaftAuspraegungWrapper>();
	final MultiSelectionModel<EigenschaftAuspraegungWrapper> selectionModelWrapper = new MultiSelectionModel<EigenschaftAuspraegungWrapper>();


	public SingleSelectionModel<EigenschaftAuspraegungWrapper> getSm() {
		return sm;
	}
	
//	Column<EigenschaftAuspraegungWrapper, String> wertAuspraegung1 = new Column<EigenschaftAuspraegungWrapper, String>(
//			new EditTextCell()) {
//
//		@Override
//		public String getValue(EigenschaftAuspraegungWrapper object) {
//
//			
//			return object.getAuspraegungValue();
//		}
//		public void onBrowserEvent(Context context, Element elem, EigenschaftAuspraegungWrapper object,
//				NativeEvent event) {
//			
//			super.onBrowserEvent(context, elem, object, event);
//			setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
//			if (event.getKeyCode() == KeyCodes.KEY_ENTER){
//				
//				
//				setFieldUpdater(new FieldUpdater<EigenschaftAuspraegungWrapper, String>() {
//					
//					@Override
//					public void update(int index, EigenschaftAuspraegungWrapper object, String value) {
//
//					
//						object.setEigenschaftValue(value);
//						sm.getSelectedObject().setAuspraegungValue(value);
//						sm.getSelectedObject().setAuspraegungID(object.getAuspraegungID());
//						eigaus.setWert(object.getAuspraegungValue());
//						eigaus.setID(object.getAuspraegungID());
//						eigaus.setEigenschaftsID(object.getEigenschaftID());
//						eigaus.setKontaktID(k.getID());
//						eigaus.setStatus(0);
//						
//						eigaus.setWert(sm.getSelectedObject().getAuspraegungValue());
//						verwaltung.updateAuspraegung(eigaus, innerClass1);
//						verwaltung.updateKontakt(k, innerClass2);
//					}	
//				});
//			}
//		};
//	};
//	
	Column<EigenschaftAuspraegungWrapper, String> bezEigenschaft = new Column<EigenschaftAuspraegungWrapper, String>(
			new ClickableTextCell()) {

		@Override
		public String getValue(EigenschaftAuspraegungWrapper object) {
			return object.getEigenschaftValue();
		}
	};

	Column<EigenschaftAuspraegungWrapper, String> wertAuspraegung = new Column<EigenschaftAuspraegungWrapper, String>(
			new ClickableTextCell()) {

		@Override
		public String getValue(EigenschaftAuspraegungWrapper object) {
			return object.getAuspraegungValue();
		}
	};

	Column<EigenschaftAuspraegungWrapper, String> status = new Column<EigenschaftAuspraegungWrapper, String>(
			imageCell) {

		@Override
		public String getValue(EigenschaftAuspraegungWrapper object) {

			if (object.getAuspraegungStatus() == 0) {

				return object.getImageUrlContact(object);
			} else {
				return object.getImageUrl2Contacts(object);
			}

		}

	};

	Column<EigenschaftAuspraegungWrapper, Boolean> checkBox = new Column<EigenschaftAuspraegungWrapper, Boolean>(
			new CheckboxCell(true, false)) {

		@Override
		public Boolean getValue(EigenschaftAuspraegungWrapper object) {

			return selectionModelWrapper.isSelected(object);
		}
	};

	Column<EigenschaftAuspraegungWrapper, String> deleteBtn = new Column<EigenschaftAuspraegungWrapper, String>(
			new ButtonCell()) {

		@Override
		public String getValue(EigenschaftAuspraegungWrapper x) {

			return "x";

		}
	};

	public Column<EigenschaftAuspraegungWrapper, String> getWertAuspraegung() {
		return wertAuspraegung;
	}

	public void setWertAuspraegung(Column<EigenschaftAuspraegungWrapper, String> wertAuspraegung) {
		this.wertAuspraegung = wertAuspraegung;
	}

	public Column<EigenschaftAuspraegungWrapper, String> getBezEigenschaft() {
		return bezEigenschaft;
	}

	public void setBezEigenschaft(Column<EigenschaftAuspraegungWrapper, String> bezEigenschaft) {
		this.bezEigenschaft = bezEigenschaft;
	}

	public Column<EigenschaftAuspraegungWrapper, String> getStatus() {
		return status;
	}

	public void setStatus(Column<EigenschaftAuspraegungWrapper, String> status) {
		this.status = status;
	}

	public Column<EigenschaftAuspraegungWrapper, Boolean> getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(Column<EigenschaftAuspraegungWrapper, Boolean> checkBox) {
		this.checkBox = checkBox;
	}

	public Column<EigenschaftAuspraegungWrapper, String> getDeleteBtn() {
		return deleteBtn;
	}

	public void setDeleteBtn(Column<EigenschaftAuspraegungWrapper, String> deleteBtn) {
		this.deleteBtn = deleteBtn;
	}

	public CellTableForm(final Kontakt k) {

//
//		this.setSelectionModel(sm);
//		
//		this.setStyleName("CellTableHyprid");


		this.setSelectionModel(sm);



//		Column<EigenschaftAuspraegungHybrid, String> bezEigenschaft = new Column<EigenschaftAuspraegungHybrid, String>(
//				new ClickableTextCell()) {
//
//			@Override
//			public String getValue(EigenschaftAuspraegungHybrid object) {
//				// TODO Auto-generated method stub
//				return object.getEigenschaft();
//			}
//		};
//		bezEigenschaft.setCellStyleNames("bezEigenschaft");
//		this.addColumn(bezEigenschaft, "Eigenschaft");
//
//		wertAuspraegung = new Column<EigenschaftAuspraegungHybrid, String>(new EditTextCell()) {
//
//			@Override
//			public String getValue(EigenschaftAuspraegungHybrid object) {
//				// TODO Auto-generated method stub
//				return object.getAuspraegung();
//			}
//
//		};
//
//		this.addColumn(wertAuspraegung, "Auspraegung");
//
//		// ListDataProvider<EigenschaftAuspraegungHybrid> model = new
//		// ListDataProvider<EigenschaftAuspraegungHybrid>();
//		// model.addDataDisplay(this);
//		bezEigenschaft.setCellStyleNames("add-scrollbar");
//		bezEigenschaft.setSortable(true);
//		this.setHeight("300px");
//		this.setRowCount(getUserList().size());
//		model.addDataDisplay(this);

		this.setStyleName("CellTableHyprid");
		
		status.setCellStyleNames("ImageCell");

		model.addDataDisplay(this);


		verwaltung.findHybrid(k, new AllAuspraegungToEigenschaftCallback());

		this.kontakt = k;
		run();
	}
	
	public CellTableForm(Kontakt k, String teilhaberschaft){
		this.kontakt = k;
		Nutzer nutzer = new Nutzer();
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		verwaltung.findSharedAuspraegung(nutzer.getID(), k.getID(), new AsyncCallback<Vector<EigenschaftAuspraegungWrapper>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Vector<EigenschaftAuspraegungWrapper> result) {
				// TODO Auto-generated method stub
				eList.addAll(result);
				setRowData(0, eList);
				setRowCount(eList.size(), true);
			}
		});
		run();
	}
	public void run(){

		this.setSelectionModel(sm);
		
		this.setStyleName("CellTableHyprid");
		model.addDataDisplay(this);

		

	}

	public void addRow(String a, String b) {
		EigenschaftAuspraegungWrapper wrapper = new EigenschaftAuspraegungWrapper();

		wrapper.setEigenschaftValue(a);
		wrapper.setAuspraegungValue(b);
		eList.add(wrapper);
		this.setRowData(0, eList);
		this.setRowCount(eList.size(), true);
		this.redraw();
	}

	public void deleteRow(EigenschaftAuspraegungWrapper object) {
		model.getList().remove(object);
		eList.remove(object);
		model.refresh();
		this.redraw();
	}

	class AllAuspraegungToEigenschaftCallback implements AsyncCallback<Vector<EigenschaftAuspraegungWrapper>> {

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Vector<EigenschaftAuspraegungWrapper> result) {
			eList.addAll(result);
			setRowData(0, eList);
			setRowCount(eList.size(), true);
		}

	}

	public HandlerRegistration addKeyDownHandler(KeyDownHandler keyDownHandler) {
		return addDomHandler(keyDownHandler, KeyDownEvent.getType());

	}

}