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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.EigenschaftAuspraegungHybrid;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;

public class CellTableForm extends CellTable<EigenschaftAuspraegungHybrid> {

	
	private List<EigenschaftAuspraegungHybrid> eList = new ArrayList<>();
	KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	private LinkedList<EigenschaftAuspraegungHybrid> getUserList() {
		 LinkedList<EigenschaftAuspraegungHybrid> list = new LinkedList<EigenschaftAuspraegungHybrid>();
	return list;
		}
	
	
	final ListDataProvider<EigenschaftAuspraegungHybrid> model = new ListDataProvider<EigenschaftAuspraegungHybrid>(
			getUserList());
	
	private SingleSelectionModel<EigenschaftAuspraegungHybrid> sm = new SingleSelectionModel<EigenschaftAuspraegungHybrid>();
	
	
	
	public SingleSelectionModel<EigenschaftAuspraegungHybrid> getSm() {
		return sm;
	}

	public CellTableForm(final Kontakt k){
		
		
		this.setSelectionModel(sm);
		
		Column<EigenschaftAuspraegungHybrid, String> bezEigenschaft = new Column<EigenschaftAuspraegungHybrid, String>(
				new ClickableTextCell()){
		
	

		@Override
		public String getValue(EigenschaftAuspraegungHybrid object) {
			// TODO Auto-generated method stub
			return object.getEigenschaft();
		}
	};
	
	this.addColumn(bezEigenschaft, "Eigenschaft");
	
	
	
	Column<EigenschaftAuspraegungHybrid, String> wertAuspraegung = new Column<EigenschaftAuspraegungHybrid, String>(
			new EditTextCell()){

				@Override
				public String getValue(EigenschaftAuspraegungHybrid object) {
					// TODO Auto-generated method stub
					return object.getAuspraegung();
				}
					
};
this.addColumn(wertAuspraegung, "Auspraegung");

//	ListDataProvider<EigenschaftAuspraegungHybrid> model = new ListDataProvider<EigenschaftAuspraegungHybrid>();
//	model.addDataDisplay(this);



	bezEigenschaft.setSortable(true);
	
	this.setRowCount(getUserList().size());
	model.addDataDisplay(this);
	
	verwaltung.findHybrid(k, new AllAuspraegungToEigenschaftCallback());

	}

	public void addRow(String a, String b){
		EigenschaftAuspraegungHybrid eigenschafthybrid = new EigenschaftAuspraegungHybrid();
		eigenschafthybrid.setEigenschaft(a);
		eigenschafthybrid.setAuspraegung(b);
		
		eList.add(eigenschafthybrid);
//		model.getList().add(eigenschafthybrid);
	
//		int row = this.getRowCount();
		this.setRowData(0, eList);
		this.setRowCount(eList.size(), true);
	
		this.redraw();
	}
	
	
//	public void updateRow(String a, String b){
//		EigenschaftAuspraegungHybrid eigenschafthybrid = new EigenschaftAuspraegungHybrid();
//		eigenschafthybrid.setEigenschaft(a);
//		eigenschafthybrid.setAuspraegung(b);
//		
//		eList.add(eigenschafthybrid);
////		model.getList().add(eigenschafthybrid);
//	   
////		int row = this.getRowCount();
//		this.setRowData(0, eList);
//		this.setRowCount(eList.size(), true);
//	   
//		this.redraw();
//	}
	
	
	
	
	
	
	
class AllAuspraegungToEigenschaftCallback implements AsyncCallback<Vector<EigenschaftAuspraegungHybrid>>{

	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(Vector<EigenschaftAuspraegungHybrid> result) {
		// TODO Auto-generated method stub
		eList.addAll(result);
		setRowData(0, eList);
		setRowCount(eList.size(), true);
	}
	
}



}
