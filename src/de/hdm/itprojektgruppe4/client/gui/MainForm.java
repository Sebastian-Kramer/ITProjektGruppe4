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
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
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
	Kontaktliste konList = new Kontaktliste();

	List<Kontakt> list2 = new ArrayList<>();
	
	private VerticalPanel vpanelDetails = new VerticalPanel();	
	private VerticalPanel vpanelNavigator = new VerticalPanel();
	
	private HorizontalPanel hpanel = new HorizontalPanel();

	private Button z = new Button("Zurück");
	private HTML html1 = new HTML("<h2>Meine Kontakte</h2>");
	private HTML html2 = new HTML("<h2>Menü</h2>");
	
	private TextCell tCell = new TextCell();
	
	private CellList<String> cellList = new CellList<String>(tCell);
	
	private List<String> kList = new ArrayList<>();

	private Tree kontaktListTree = new Tree();
	
    private TreeItem kontaktListTreeItem = new TreeItem();
	
	public MainForm(){				

		
		verwaltung.findAllKontaktNames(new KontaktCallBack());
		verwaltung.findKontaktlisteAll(new KontaktlistCallBack());

		
		final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();
		cellList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
		public void onSelectionChange(SelectionChangeEvent event) {
			String selected = selectionModel.getSelectedObject();
	        if (selected != null) {
	        	Window.alert("Sie haben folgenden Kontakt ausgewählt: " + selected);
	        }
	      }
	    });	
				
		// Create a tree with a few items in it.

		kontaktListTreeItem.setText("Meine Kontaktlisten");
//		kontaktListTreeItem.addTextItem("item0");
//		kontaktListTreeItem.addTextItem("item1");
//		kontaktListTreeItem.addTextItem("item2");

	    // Add a CheckBox to the tree
//	    TreeItem item = new TreeItem(new CheckBox("item3"));
//	    kontaktListTreeItem.addItem(item);

		kontaktListTree.addItem(kontaktListTreeItem);
		
		vpanelNavigator.add(html2);
		vpanelNavigator.add(kontaktListTree);
		vpanelNavigator.add(z);
	    RootPanel.get("Navigator").add(vpanelNavigator);
		
		
		vpanelDetails.add(html1);
		vpanelDetails.add(cellList);
		this.add(vpanelDetails);
		

//		vpanelNavigator.add(html2);
//		vpanelNavigator.add(kontaktListTree);
//		vpanelNavigator.add(z);
//		RootPanel.get("Navigator").add(vpanelNavigator);
		
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
	
	class KontaktlistCallBack implements AsyncCallback<Vector<Kontaktliste>>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Beim Laden der Kontaktlisten ist ein Fehler aufgetreten");
			
		}

		@Override
		public void onSuccess(Vector<Kontaktliste> result) {
			
			Window.alert("Alle Kontaktlsiten wurden gefunden");
			
			for (Kontaktliste kList: result){
				kontaktListTreeItem.addTextItem(kList.getBez());
			}
			
		}
		
	}

}
