package de.hdm.itprojektgruppe4.client.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import com.google.appengine.api.datastore.Email;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.thirdparty.javascript.jscomp.Result;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
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
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;

public class MainForm extends Composite{
	
	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	Kontakt kon = new Kontakt();
	Kontaktliste konList = new Kontaktliste();

	List<Kontakt> list2 = new ArrayList<>();
	
	private VerticalPanel vpanelDetails = new VerticalPanel();	
	private VerticalPanel vpanelNavigator = new VerticalPanel();
	private HorizontalPanel hpanelDetails = new HorizontalPanel();
	
	private HorizontalPanel hpanel = new HorizontalPanel();

	private Button z = new Button("Zurück");
	private HTML html1 = new HTML("<h2>Meine Kontakte</h2>");
	private HTML html2 = new HTML("<h2>Menü</h2>");
	private Button newKontakt = new Button ("Neuen Kontakt anlegen");
	private Button updateKontakt = new Button("Kontakt bearbeiten");
	
	private TextCell tCell = new TextCell();
	
	private CellList<String> cellList = new CellList<String>(tCell);
	
	private List<String> kList = new ArrayList<>();

	private Tree kontaktListTree = new Tree();
	
    private TreeItem kontaktListTreeItem = new TreeItem();
	
	public MainForm(){	
		
		initWidget(this.vpanelDetails);
		Nutzer nutzer = new Nutzer ();
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));
		
		
		
		verwaltung.findKontaktByNutzerID(nutzer.getID(), new KontaktCallBack());
		
		

		
		
	//	verwaltung.findKontaktlisteByNutzerID(new KontaktlistCallBack());

		
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
		
		// Navigator Panels & Widgets
		
		vpanelNavigator.add(html2);
		vpanelNavigator.add(kontaktListTree);
		vpanelNavigator.add(z);
	    RootPanel.get("Navigator").add(vpanelNavigator);
		
	    
	    // Details Panels & Widgets
	    
	    newKontakt.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				KontaktForm kf = new KontaktForm();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(kf);
			}
		});
	    
	    updateKontakt.setVisible(false);
		hpanelDetails.add(newKontakt);
		hpanelDetails.add(updateKontakt);
		
		vpanelDetails.add(html1);
		vpanelDetails.add(hpanelDetails);
		vpanelDetails.add(cellList);
	//	this.add(vpanelDetails);
		

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
