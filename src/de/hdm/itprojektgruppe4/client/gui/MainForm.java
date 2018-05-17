package de.hdm.itprojektgruppe4.client.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.thirdparty.javascript.jscomp.Result;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;

public class MainForm extends VerticalPanel{
	
	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	Kontakt kon = new Kontakt();

	List<Kontakt> list2 = new ArrayList<>();
	
	private VerticalPanel vpanel = new VerticalPanel();
	private VerticalPanel vpanelb = new VerticalPanel();
	private HorizontalPanel hpanel = new HorizontalPanel();

	private Button z = new Button("Zurück");
	private HTML html1 = new HTML("<h2>Alle meine Kontakte</h2>");
	
	private TextCell tCell = new TextCell();
	
	private CellList<String> cellList = new CellList<String>(tCell);
	
	private List<String> kList = new ArrayList<>();

	public MainForm(){				

		
		verwaltung.findAllKontaktNames(new KontaktCallBack());

		
		final SingleSelectionModel<Kontakt> selectionModel = new SingleSelectionModel<Kontakt>();
//		cellList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
		public void onSelectionChange(SelectionChangeEvent event) {
			Kontakt selected = selectionModel.getSelectedObject();
	        if (selected != null) {
	        	Window.alert(selected.getName());
	        }
	      }
	    });	
		
		hpanel.add(z);
		vpanel.add(html1);
		vpanel.add(cellList);
		vpanel.add(hpanel);
		this.add(vpanel);
		
	}
	
	class KontaktCallBack implements AsyncCallback<List<Kontakt>>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Beim Laden der Kontakte ist ein Fehler aufgetreten");
			
		}

		@Override
		public void onSuccess(List<Kontakt> result) {
			
			Window.alert("Geht" + result.size());
			
			for (Kontakt kon : result){
				kList.add(kon.getName());
			}	
			
			cellList.setRowCount(kList.size(), true);
			cellList.setRowData(0, kList);
		}
			
	}

}
