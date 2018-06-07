package de.hdm.itprojektgruppe4.client.gui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.thirdparty.javascript.jscomp.parsing.parser.trees.GetAccessorTree;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
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

public class CellTableForm extends CellTable<EigenschaftAuspraegungWrapper> {

	private List<EigenschaftAuspraegungWrapper> eList = new ArrayList<>();
	KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();

	private LinkedList<EigenschaftAuspraegungWrapper> getUserList() {
		LinkedList<EigenschaftAuspraegungWrapper> list = new LinkedList<EigenschaftAuspraegungWrapper>();
		return list;
	}

	final ListDataProvider<EigenschaftAuspraegungWrapper> model = new ListDataProvider<EigenschaftAuspraegungWrapper>(
			getUserList());

	private SingleSelectionModel<EigenschaftAuspraegungWrapper> sm = new SingleSelectionModel<EigenschaftAuspraegungWrapper>();

	public SingleSelectionModel<EigenschaftAuspraegungWrapper> getSm() {
		return sm;
	}

	Column<EigenschaftAuspraegungWrapper, String> wertAuspraegung;

	public Column<EigenschaftAuspraegungWrapper, String> getWertAuspraegung() {
		return wertAuspraegung;
	}

	public void setWertAuspraegung(Column<EigenschaftAuspraegungWrapper, String> wertAuspraegung) {
		this.wertAuspraegung = wertAuspraegung;
	}

	public CellTableForm(final Kontakt k) {

		this.setSelectionModel(sm);
		
		this.setStyleName("CellTableHyprid");

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
		model.addDataDisplay(this);

		verwaltung.findHybrid(k, new AllAuspraegungToEigenschaftCallback());

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
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Vector<EigenschaftAuspraegungWrapper> result) {
			// TODO Auto-generated method stub
			eList.addAll(result);
			setRowData(0, eList);
			setRowCount(eList.size(), true);
		}

	}

	public HandlerRegistration addKeyDownHandler(KeyDownHandler keyDownHandler) {
		return addDomHandler(keyDownHandler, KeyDownEvent.getType());

	}

}