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

	/**
	 * Die onLoad() Methode wird bei Aufruf von CellTableForm ausgeführt.
	 * Cookies werden überprüft und die Größe der Tabelle gesetzt.
	 * @author Clirim
	 *
	 */
	public void onLoad() {

		super.onLoad();

		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));

		this.setPageSize(100);
		this.setPageSize(100);
		model.addDataDisplay(this);

	}

	/**
	 * Es wird eine unabhängige Spalte für eine CellTable erstellt, 
	 * die die Bezeichnung einer Eigenschaft zurückgibt.
	 * Die Spalte kann in CellTables anderer Klsen hinzugefügt werden.
	 * @author Clirim
	 *
	 */
	Column<EigenschaftAuspraegungWrapper, String> bezEigenschaft = new Column<EigenschaftAuspraegungWrapper, String>(

			new ClickableTextCell()) {

		@Override
		public String getValue(EigenschaftAuspraegungWrapper object) {
			return object.getEigenschaftValue();
		}
	};
	/**
	 * Es wird eine unabhängige Spalte für eine CellTable erstellt, 
	 * die den Wert einer Ausprägung zurückgibt.
	 * Die Spalte kann in CellTables anderer Klsen hinzugefügt werden.
	 * @author Clirim
	 *
	 */
	Column<EigenschaftAuspraegungWrapper, String> wertAuspraegung = new Column<EigenschaftAuspraegungWrapper, String>(

			new ClickableTextCell()) {

		@Override
		public String getValue(EigenschaftAuspraegungWrapper object) {
			return object.getAuspraegungValue();
		}
	};
	/**
	 * Es wird eine unabhängige Spalte für eine CellTable erstellt, 
	 * die den Status einer Ausprägung zurückgibt.
	 * Die Spalte kann in CellTables anderer Klsen hinzugefügt werden.
	 * @author Clirim
	 *
	 */
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
	/**
	 * Es wird eine unabhängige Spalte für eine CellTable erstellt, 
	 * die eine Lösch-Zelle zurückgibt.
	 * Die Spalte kann in CellTables anderer Klsen hinzugefügt werden.
	 * @author Clirim
	 *
	 */
	Column<EigenschaftAuspraegungWrapper, String> deleteCell = new Column<EigenschaftAuspraegungWrapper, String>(
			imageCellDelete) {

		@Override
		public String getValue(EigenschaftAuspraegungWrapper object) {

			return object.getDelete(object);
		}

	};
	/**
	 * Es wird eine unabhängige Spalte für eine CellTable erstellt, 
	 * die eine CheckBox zurückgibt.
	 * Die Spalte kann in CellTables anderer Klsen hinzugefügt werden.
	 * @author Clirim
	 *
	 */
	Column<EigenschaftAuspraegungWrapper, Boolean> checkBox = new Column<EigenschaftAuspraegungWrapper, Boolean>(
			new CheckboxCell(true, false)) {

		@Override
		public Boolean getValue(EigenschaftAuspraegungWrapper object) {

			return selectionModelWrapper.isSelected(object);
		}
	};
	/**
	 * Es wird eine unabhängige Spalte für eine CellTable erstellt, 
	 * die einen Lösch-Button zurückgibt.
	 * Die Spalte kann in CellTables anderer Klsen hinzugefügt werden.
	 * @author Clirim
	 *
	 */
	Column<EigenschaftAuspraegungWrapper, String> deleteBtn = new Column<EigenschaftAuspraegungWrapper, String>(
			new ButtonCell()) {

		@Override
		public String getValue(EigenschaftAuspraegungWrapper x) {

			return "x";

		}
	};
	/**
	 * Get-Methode für die Spalte WertAusprägung
	 * @author Clirim
	 *
	 */
	public Column<EigenschaftAuspraegungWrapper, String> getWertAuspraegung() {
		return wertAuspraegung;
	}
	/**
	 * Set-Methode für die Spalte WertAusprägung
	 * @author Clirim
	 *
	 */
	public void setWertAuspraegung(Column<EigenschaftAuspraegungWrapper, String> wertAuspraegung) {
		this.wertAuspraegung = wertAuspraegung;
	}
	/**
	 * Get-Methode für die Spalte BezEigenschaft
	 * @author Clirim
	 *
	 */
	public Column<EigenschaftAuspraegungWrapper, String> getBezEigenschaft() {
		return bezEigenschaft;
	}
	/**
	 * Set-Methode für die Spalte BezEigenschaft
	 * @author Clirim
	 *
	 */
	public void setBezEigenschaft(Column<EigenschaftAuspraegungWrapper, String> bezEigenschaft) {
		this.bezEigenschaft = bezEigenschaft;
	}
	/**
	 * Get-Methode für die Spalte Status
	 * @author Clirim
	 *
	 */
	public Column<EigenschaftAuspraegungWrapper, String> getStatus() {
		return status;
	}
	/**
	 * Set-Methode für die Spalte Status
	 * @author Clirim
	 *
	 */
	public void setStatus(Column<EigenschaftAuspraegungWrapper, String> status) {
		this.status = status;
	}
	/**
	 * Get-Methode für die Spalte DeleteCell
	 * @author Clirim
	 *
	 */
	public Column<EigenschaftAuspraegungWrapper, String> getDeleteCell() {
		return deleteCell;
	}
	/**
	 * Set-Methode für die Spalte DeleteCell
	 * @author Clirim
	 *
	 */
	public void getDeleteCell(Column<EigenschaftAuspraegungWrapper, String> deleteCell) {
		this.deleteCell = deleteCell;
	}
	/**
	 * Get-Methode für die Spalte CheckBox
	 * @author Clirim
	 *
	 */
	public Column<EigenschaftAuspraegungWrapper, Boolean> getCheckBox() {
		return checkBox;
	}
	/**
	 * Set-Methode für die Spalte CheckBox
	 * @author Clirim
	 *
	 */
	public void setCheckBox(Column<EigenschaftAuspraegungWrapper, Boolean> checkBox) {
		this.checkBox = checkBox;
	}
	/**
	 * Get-Methode für die Spalte DeleteBtn
	 * @author Clirim
	 *
	 */
	public Column<EigenschaftAuspraegungWrapper, String> getDeleteBtn() {
		return deleteBtn;
	}
	/**
	 * Set-Methode für die Spalte DeleteBtn
	 * @author Clirim
	 *
	 */
	public void setDeleteBtn(Column<EigenschaftAuspraegungWrapper, String> deleteBtn) {
		this.deleteBtn = deleteBtn;
	}
	/**
	 * Methode die eine Tabelle um eine neue Zeile ergänzt
	 * @author Clirim
	 *
	 */
	public void addRow(String a, String b) {
		EigenschaftAuspraegungWrapper wrapper = new EigenschaftAuspraegungWrapper();

		wrapper.setEigenschaftValue(a);
		wrapper.setAuspraegungValue(b);
		eList.add(wrapper);
		this.setRowData(0, eList);
		this.setRowCount(eList.size(), true);
		this.redraw();
	}
	/**
	 * Methode die die letzte Zeile einer Tabelle entfernt
	 * @author Clirim
	 *
	 */
	public void deleteRow(EigenschaftAuspraegungWrapper object) {
		model.getList().remove(object);
		eList.remove(object);
		model.refresh();
		this.redraw();
	}
	/**
	 * Es wird eine unabhängige Spalte für eine CellTable erstellt, 
	 * die den Wert einer Ausprägung zurückgibt.
	 * Die Spalte kann in CellTables anderer Klsen hinzugefügt werden.
	 * @author Clirim
	 *
	 */
	class ColumnAuspraegung extends Column<EigenschaftAuspraegungWrapper, String> {

		public ColumnAuspraegung(Cell<String> cell) {
			super(cell);
		}

		@Override
		public String getValue(EigenschaftAuspraegungWrapper object) {
			return object.getAuspraegungValue();
		}

	}
	/**
	 * Es wird eine unabhängige Spalte für eine CellTable erstellt, 
	 * die eine CheckBox zurückgibt.
	 * Die Spalte kann in CellTables anderer Klsen hinzugefügt werden.
	 * @author Clirim
	 *
	 */
	class ColumnCeckBox extends Column<EigenschaftAuspraegungWrapper, Boolean> {

		public ColumnCeckBox(Cell<Boolean> cell) {
			super(cell);

		}

		@Override
		public Boolean getValue(EigenschaftAuspraegungWrapper object) {

			return selectionModelWrapper.isSelected(object);
		}

	}
	/**
	 * Es wird eine unabhängige Spalte für eine CellTable erstellt, 
	 * die einen Lösch-Button zurückgibt.
	 * Die Spalte kann in CellTables anderer Klsen hinzugefügt werden.
	 * @author Clirim
	 *
	 */
	class ColumnDeleteBtn extends Column<EigenschaftAuspraegungWrapper, String> {

		public ColumnDeleteBtn(Cell<String> cell) {
			super(cell);

		}

		@Override
		public String getValue(EigenschaftAuspraegungWrapper object) {

			return "x";
		}

	}
	/**
	 * Es wird eine unabhängige Spalte für eine CellTable erstellt, 
	 * die die Bezeichnung einer Eigenschaft zurückgibt.
	 * Die Spalte kann in CellTables anderer Klsen hinzugefügt werden.
	 * @author Clirim
	 *
	 */
	class ColumnEigenschaft extends Column<EigenschaftAuspraegungWrapper, String> {

		public ColumnEigenschaft(Cell<String> cell) {
			super(cell);

		}

		@Override
		public String getValue(EigenschaftAuspraegungWrapper object) {

			return object.getEigenschaftValue();
		}

	}
	/**
	 * Es wird eine unabhängige Spalte für eine CellTable erstellt, 
	 * die den Kontaktnamen eines Kontaktes angibt welcher zu der jeweiligen
	 * Ausprägung gehört.
	 * Die Spalte kann in CellTables anderer Klsen hinzugefügt werden.
	 * @author Clirim
	 *
	 */
	class ColumnKontaktName extends Column<EigenschaftAuspraegungWrapper, String> {

		public ColumnKontaktName(Cell<String> cell) {
			super(cell);

		}

		@Override
		public String getValue(EigenschaftAuspraegungWrapper object) {

			return object.getKontakt().getName();
		}

	}
	/**
	 * Es wird eine unabhängige Spalte für eine CellTable erstellt, 
	 * die den Status einer Ausprägung zurückgibt.
	 * Die Spalte kann in CellTables anderer Klsen hinzugefügt werden.
	 * @author Clirim
	 *
	 */
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
	/**
	 * Callback welcher alle geteilten Ausprägungen zurückgibt.
	 * @author Clirim
	 *
	 */
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
	/**
	 * Callback welcher alle geteilten Ausprägungen und Eigenschaften zurückgibt,
	 * und die Tabelle neu lädt.
	 * @author Clirim
	 *
	 */
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