package de.hdm.itprojektgruppe4.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.thirdparty.javascript.jscomp.parsing.parser.trees.GetAccessorTree;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.EigenschaftAuspraegungHybrid;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;

public class CellTableForm extends CellTable<EigenschaftAuspraegungHybrid> {

	KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	
	
	
	public CellTableForm(final Kontakt k){
		
		
		Column<EigenschaftAuspraegungHybrid, String> bezEigenschaft = new Column<EigenschaftAuspraegungHybrid, String>(
				new ClickableTextCell()){
		
	

		@Override
		public String getValue(EigenschaftAuspraegungHybrid object) {
			// TODO Auto-generated method stub
			return object.getEigenschaft();
		}
		
		
	};
	
	
	Column<EigenschaftAuspraegungHybrid, String> wertAuspraegung = new Column<EigenschaftAuspraegungHybrid, String>(
			new ClickableTextCell()){

				@Override
				public String getValue(EigenschaftAuspraegungHybrid object) {
					// TODO Auto-generated method stub
					return object.getAuspraegung();
				}
};

	verwaltung.findHybrid(k, new AllAuspraegungToEigenschaftCallback());

	this.addColumn(bezEigenschaft, "");
	this.addColumn(wertAuspraegung, "");
	
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
