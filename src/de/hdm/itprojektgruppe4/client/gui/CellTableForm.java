package de.hdm.itprojektgruppe4.client.gui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.dom.client.KeyDownHandler;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.client.EigenschaftAuspraegungWrapper;

import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;

public class CellTableForm extends CellTable<EigenschaftAuspraegungWrapper> {

	private Kontakt kontakt = new Kontakt();
	private ImageCell imageCell = new ImageCell();

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

	public CellTableForm(Kontakt k, String teilhaberschaft) {

		this.kontakt = k;
		Nutzer nutzer = new Nutzer();
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		verwaltung.findSharedAuspraegung(nutzer.getID(), kontakt.getID(),
				new AsyncCallback<Vector<EigenschaftAuspraegungWrapper>>() {

					@Override
					public void onFailure(Throwable caught) {

					}

					@Override
					public void onSuccess(Vector<EigenschaftAuspraegungWrapper> result) {

						eList.addAll(result);
						setRowData(0, eList);
						setRowCount(eList.size(), true);
					}
				});
		this.setPageSize(100);
		run();
	}

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

	public CellTableForm(Kontakt k) {

		//
		// this.setSelectionModel(sm);
		//
		// this.setStyleName("CellTableHyprid");

		this.setSelectionModel(sm);

		// Column<EigenschaftAuspraegungHybrid, String> bezEigenschaft = new
		// Column<EigenschaftAuspraegungHybrid, String>(
		// new ClickableTextCell()) {
		//
		// @Override
		// public String getValue(EigenschaftAuspraegungHybrid object) {
		//
		// return object.getEigenschaft();
		// }
		// };
		// bezEigenschaft.setCellStyleNames("bezEigenschaft");
		// this.addColumn(bezEigenschaft, "Eigenschaft");
		//
		// wertAuspraegung = new Column<EigenschaftAuspraegungHybrid,
		// String>(new EditTextCell()) {
		//
		// @Override
		// public String getValue(EigenschaftAuspraegungHybrid object) {
		//
		// return object.getAuspraegung();
		// }
		//
		// };
		//
		// this.addColumn(wertAuspraegung, "Auspraegung");
		//
		// // ListDataProvider<EigenschaftAuspraegungHybrid> model = new
		// // ListDataProvider<EigenschaftAuspraegungHybrid>();
		// // model.addDataDisplay(this);
		// bezEigenschaft.setCellStyleNames("add-scrollbar");
		// bezEigenschaft.setSortable(true);
		// this.setHeight("300px");
		// this.setRowCount(getUserList().size());
		// model.addDataDisplay(this);

		this.setStyleName("CellTableHyprid");

		status.setCellStyleNames("ImageCell");

		verwaltung.findHybrid(k, new AllAuspraegungToEigenschaftCallback());

		this.kontakt = k;
		run();
	}

	public void run() {

		this.setSelectionModel(sm);
		this.setStyleName("CellTableHyprid");
		this.setPageSize(100);
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

	class ColumnAuspraegung extends Column<EigenschaftAuspraegungWrapper, String> {

		public ColumnAuspraegung(Cell<String> cell) {
			super(cell);
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getValue(EigenschaftAuspraegungWrapper object) {
			// TODO Auto-generated method stub
			return object.getAuspraegungValue();
		}

	}

	class ColumnCeckBox extends Column<EigenschaftAuspraegungWrapper, Boolean> {

		public ColumnCeckBox(Cell<Boolean> cell) {
			super(cell);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Boolean getValue(EigenschaftAuspraegungWrapper object) {
			// TODO Auto-generated method stub
			return selectionModelWrapper.isSelected(object);
		}

	}

	class ColumnDeleteBtn extends Column<EigenschaftAuspraegungWrapper, String> {

		public ColumnDeleteBtn(Cell<String> cell) {
			super(cell);
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getValue(EigenschaftAuspraegungWrapper object) {
			// TODO Auto-generated method stub
			return "x";
		}

	}

	class ColumnEigenschaft extends Column<EigenschaftAuspraegungWrapper, String> {

		public ColumnEigenschaft(Cell<String> cell) {
			super(cell);
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getValue(EigenschaftAuspraegungWrapper object) {
			// TODO Auto-generated method stub
			return object.getEigenschaftValue();
		}

	}

	class ColumnStatus extends Column<EigenschaftAuspraegungWrapper, String> {

		public ColumnStatus(Cell<String> cell) {
			super(cell);
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getValue(EigenschaftAuspraegungWrapper object) {
			// TODO Auto-generated method stub
			if (object.getAuspraegungStatus() == 0) {

				return object.getImageUrlContact(object);
			} else {
				return object.getImageUrl2Contacts(object);
			}
		}

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