package de.hdm.itprojektgruppe4.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;

public class AllKontakteForm extends VerticalPanel{
	
	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	private Kontaktliste k = new Kontaktliste();
	
	/**
	 * Implementierung der Widgets für die AllKontaktForm - Seite
	 */
	
	private VerticalPanel vpanelDetails = new VerticalPanel();	
	
	private List<String> kList2 = new ArrayList<>();
	
	private TextCell tCell = new TextCell();
	
	private CellList<String> cellList = new CellList<String>(tCell);
	
	/**
	 * Überschirft für die From festlegen
	 */
	
	public AllKontakteForm(Kontaktliste kl){
		
		this.k = kl;		
		
		HTML html1 = new HTML("<h2>Meine Kontakte " + k.getBez() + "</h2>");
		
		Window.alert(k.getBez());
		
		vpanelDetails.add(html1);
		vpanelDetails.add(cellList);
		this.add(vpanelDetails);
		
	
		verwaltung.findAllKontakteFromKontaktliste(k.getID(), new AllKontakteFromListCallBack());

	}
	
	class AllKontakteFromListCallBack implements AsyncCallback<Vector<Integer>>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Beim Laden der Kontakte ist ein Fehler aufgetreten");
			
		}

		@Override
		public void onSuccess(Vector<Integer> result) {
			for (int i : result){
				verwaltung.findKontaktByID(i, new AsyncCallback<Kontakt>(){

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(Kontakt result) {
						Window.alert(result.getName());
						kList2.add(result.getName());	
						cellList.setRowCount(kList2.size(), true);
						cellList.setRowData(0, kList2);
						
					}
					
				});

			}
		
		}
		
	}

}