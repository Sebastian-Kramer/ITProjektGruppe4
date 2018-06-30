package de.hdm.itprojektgruppe4.client.gui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.client.EigenschaftAuspraegungWrapper;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;

/**
 * Die Klasse CellTableForm die alle Eigenschaften und Ausprägungen eines KontaktObjekts enthält. Diese Klasse
 * erbt von der Superklasse CellTable mit dem Datentyp EigenschaftAuspraegungWrapper. 
 * @author Sebi_0107
 *
 */
public class CellTableForm extends CellTable<EigenschaftAuspraegungWrapper> {

	private Kontakt kontakt = new Kontakt();
	private Nutzer nutzer = new Nutzer();
	private ImageCell imageCell = new ImageCell();
	private ImageCell imageCellDelete = new ImageCell();
	private List<EigenschaftAuspraegungWrapper> eList = new ArrayList<>();
	private KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();

	private LinkedList<EigenschaftAuspraegungWrapper> getUserList() {
		LinkedList<EigenschaftAuspraegungWrapper> list = new LinkedList<EigenschaftAuspraegungWrapper>();
		return list;
	}

	final ListDataProvider<EigenschaftAuspraegungWrapper> model = new ListDataProvider<EigenschaftAuspraegungWrapper>(
			getUserList());

	private SingleSelectionModel<EigenschaftAuspraegungWrapper> sm = new SingleSelectionModel<EigenschaftAuspraegungWrapper>();
	final MultiSelectionModel<EigenschaftAuspraegungWrapper> selectionModelWrapper = new MultiSelectionModel<EigenschaftAuspraegungWrapper>();
	final MultiSelectionModel<Kontakt> selectionModelKontakt = new MultiSelectionModel<Kontakt>();

	public SingleSelectionModel<EigenschaftAuspraegungWrapper> getSm() {
		return sm;
	}

	public CellTableForm() {
		super();
	}

	public CellTableForm(Kontakt k) {

		this.kontakt = k;
		verwaltung.findHybrid(k, new AllAuspraegungToEigenschaftCallback());

	}

	public CellTableForm(Kontakt k, String teilhaberschaft) {

		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));
		this.kontakt = k;

		verwaltung.findSharedAuspraegung(kontakt.getID(), nutzer.getID(), new AllSharedAuspraegungen());

	}

	public void onLoad() {

		super.onLoad();

		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));

		this.setPageSize(100);
		this.setPageSize(100);
		model.addDataDisplay(this);

	}

	class AllSharedAuspraegungen implements AsyncCallback<Vector<EigenschaftAuspraegungWrapper>> {

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

				return "";
			} else {
				return object.getImageUrl2Contacts(object);
			}

		}

	};

	Column<EigenschaftAuspraegungWrapper, String> deleteCell = new Column<EigenschaftAuspraegungWrapper, String>(
			imageCellDelete) {

		@Override
		public String getValue(EigenschaftAuspraegungWrapper object) {

			return object.getDelete(object);
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

	public Column<EigenschaftAuspraegungWrapper, String> getDeleteCell() {
		return deleteCell;
	}

	public void getDeleteCell(Column<EigenschaftAuspraegungWrapper, String> deleteCell) {
		this.deleteCell = deleteCell;
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
		}

		@Override
		public String getValue(EigenschaftAuspraegungWrapper object) {
			return object.getAuspraegungValue();
		}

	}

	class ColumnCeckBox extends Column<EigenschaftAuspraegungWrapper, Boolean> {

		public ColumnCeckBox(Cell<Boolean> cell) {
			super(cell);

		}

		@Override
		public Boolean getValue(EigenschaftAuspraegungWrapper object) {

			return selectionModelWrapper.isSelected(object);
		}

	}

	class ColumnDeleteBtn extends Column<EigenschaftAuspraegungWrapper, String> {

		public ColumnDeleteBtn(Cell<String> cell) {
			super(cell);

		}

		@Override
		public String getValue(EigenschaftAuspraegungWrapper object) {

			return "x";
		}

	}

	class ColumnEigenschaft extends Column<EigenschaftAuspraegungWrapper, String> {

		public ColumnEigenschaft(Cell<String> cell) {
			super(cell);

		}

		@Override
		public String getValue(EigenschaftAuspraegungWrapper object) {

			return object.getEigenschaftValue();
		}

	}

	class ColumnKontaktName extends Column<EigenschaftAuspraegungWrapper, String> {

		public ColumnKontaktName(Cell<String> cell) {
			super(cell);

		}

		@Override
		public String getValue(EigenschaftAuspraegungWrapper object) {

			return object.getKontakt().getName();
		}

	}

	class ColumnStatus extends Column<EigenschaftAuspraegungWrapper, String> {

		public ColumnStatus(Cell<String> cell) {
			super(cell);

		}

		@Override
		public String getValue(EigenschaftAuspraegungWrapper object) {

			if (object.getAuspraegungStatus() == 0) {

				return " ";
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