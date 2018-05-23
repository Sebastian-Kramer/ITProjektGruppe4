package de.hdm.itprojektgruppe4.client.gui;

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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.ListDataProvider;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.EigenschaftAuspraegungHybrid;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;

public class CellTableForm extends CellTable<EigenschaftAuspraegungHybrid> {

	KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	private LinkedList<EigenschaftAuspraegungHybrid> getUserList() {
		 LinkedList<EigenschaftAuspraegungHybrid> list = new LinkedList<EigenschaftAuspraegungHybrid>();
	return list;
		}
	
	
	final ListDataProvider<EigenschaftAuspraegungHybrid> model = new ListDataProvider<EigenschaftAuspraegungHybrid>(
			getUserList());
	
	public CellTableForm(final Kontakt k){
		
	
		
		final CellTable ct = new CellTable();
		
		
		Column<EigenschaftAuspraegungHybrid, String> bezEigenschaft = new Column<EigenschaftAuspraegungHybrid, String>(
				new ClickableTextCell()){
		
	

		@Override
		public String getValue(EigenschaftAuspraegungHybrid object) {
			// TODO Auto-generated method stub
			return object.getEigenschaft();
		}
		
		
	};
	
	
	Column<EigenschaftAuspraegungHybrid, String> wertAuspraegung = new Column<EigenschaftAuspraegungHybrid, String>(
			new EditTextCell()){

				@Override
				public String getValue(EigenschaftAuspraegungHybrid object) {
					// TODO Auto-generated method stub
					return object.getAuspraegung();
				}
};

	bezEigenschaft.setSortable(true);
	
	this.setRowCount(getUserList().size());
	model.addDataDisplay(this);
	
	verwaltung.findHybrid(k, new AllAuspraegungToEigenschaftCallback());

	this.addColumn(bezEigenschaft, "Eigenschaft");
	this.addColumn(wertAuspraegung, "Auspraegung");
	
	
	


		Column<EigenschaftAuspraegungHybrid, String> deleteBtn = new Column<EigenschaftAuspraegungHybrid, String>(
				new ButtonCell()) {
			

			@Override
			public String getValue(EigenschaftAuspraegungHybrid x) {
				// TODO Auto-generated method stub
				return "x";
			}
			};
				
			this.addColumn(deleteBtn, "");
			
			deleteBtn.setFieldUpdater(new FieldUpdater<EigenschaftAuspraegungHybrid, String>() {
				
				@Override
				public void update(int index, EigenschaftAuspraegungHybrid object, String value) {
					model.getList().remove(object);
				    model.refresh();
				    ct.redraw();
					
				}
			});
			
			
	}



class AllAuspraegungToEigenschaftCallback implements AsyncCallback<Vector<EigenschaftAuspraegungHybrid>>{

	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(Vector<EigenschaftAuspraegungHybrid> result) {
		// TODO Auto-generated method stub
		setRowData(0, result);
		setRowCount(result.size(), true);
	}
	
}



}
