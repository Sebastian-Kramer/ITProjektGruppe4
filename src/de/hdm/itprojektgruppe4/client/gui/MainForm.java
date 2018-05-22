package de.hdm.itprojektgruppe4.client.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.event.TreeSelectionListener;

import com.gargoylesoftware.htmlunit.javascript.host.EventHandler;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.thirdparty.javascript.jscomp.Result;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.HasSelectionChangedHandlers;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.event.logical.shared.*;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;

public class MainForm extends Composite{
	
	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	Kontakt kon = new Kontakt();
	Kontaktliste konList = new Kontaktliste();

	List<Kontakt> list2 = new ArrayList<>();
	
	private VerticalPanel vpanelDetails = new VerticalPanel();	
	private VerticalPanel vpanelNavigator = new VerticalPanel();
	private HorizontalPanel hpanelDetails = new HorizontalPanel();
	
	private HorizontalPanel hpanel = new HorizontalPanel();

	private Button profil = new Button("Mein Profil");
	private Button newKontakt = new Button("Neuer Kontakt");
	private Button showKontakt = new Button("Kontakt anzeigen");
	private HTML html1 = new HTML("<h2>Meine Kontakte</h2>");
	private HTML html2 = new HTML("<h2>Menü</h2>");
	private Button updateKontakt = new Button("Kontakt bearbeiten");
	
	private TextCell tCell = new TextCell();
	
	private CellList<String> cellList = new CellList<String>(tCell);
	
	private List<String> kList = new ArrayList<>();
	
	private List<String> kList2 = new ArrayList<>();
	
	private Tree kontaktListTree = new Tree();
	
    private TreeItem kontaktListTreeItem = new TreeItem();
    
    final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();
	final SingleSelectionModel<TreeItem> selectionTreeItem = new SingleSelectionModel<TreeItem>();
	
	public MainForm(){	
		
		initWidget(this.vpanelDetails);

		
		
		verwaltung.findAllKontaktNames(new KontaktCallBack());
		verwaltung.findKontaktlisteAll(new KontaktlistCallBack());

		
		
		cellList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			@Override	
			public void onSelectionChange(SelectionChangeEvent event) {
				String selected = selectionModel.getSelectedObject();
				if (selected != null) {
					  updateKontakt.setVisible(true);
					Window.alert("Sie haben folgenden Kontakt ausgewählt: " + selected);
				}
			}
	    });	
		

		kontaktListTree.addSelectionHandler(new SelectionHandler<TreeItem>(){

			@Override
			public void onSelection(SelectionEvent<TreeItem> event) {
				TreeItem it = event.getSelectedItem();
				RootPanel.get("Details").clear();
				verwaltung.findKontaktlisteByBezeichnung(it.getText(), new KontaktlisteKontaktCallBack());
				verwaltung.findAllKontakteFromKontaktliste(konList.getID(), new AllKontakteFromListCallBack());
			}
			
		});
		
		kontaktListTree.addOpenHandler(new OpenHandler<TreeItem>(){

			@Override
			public void onOpen(OpenEvent<TreeItem> event) {
				Window.alert(kontaktListTreeItem.getText() + " wird geladen");
				
			}
			
		});
		
				
		kontaktListTreeItem.setText("Meine Kontaktlisten");

		kontaktListTree.addItem(kontaktListTreeItem);
		
		// Navigator Panels & Widgets
		
		vpanelNavigator.add(html2);
		vpanelNavigator.add(newKontakt);
		vpanelNavigator.add(kontaktListTree);
		vpanelNavigator.add(profil);
		vpanelNavigator.add(showKontakt);
	    RootPanel.get("Navigator").add(vpanelNavigator);
		
	    
	    // Details Panels & Widgets
	    
	    newKontakt.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				NewKontaktForm nkf = new NewKontaktForm();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(nkf);
			}
		});
	    
	    showKontakt.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				KontaktForm kf = new KontaktForm();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(kf);
				
			}
	    	
	    	
	    });
	    
	    
	    
	    updateKontakt.setVisible(false);
		hpanelDetails.add(newKontakt);
		hpanelDetails.add(updateKontakt);
		hpanelDetails.add(showKontakt);
		
		vpanelDetails.add(html1);
		vpanelDetails.add(hpanelDetails);
		vpanelDetails.add(cellList);
	//	this.add(vpanelDetails);
		
		
	}

	
	class KontaktCallBack implements AsyncCallback<List<Kontakt>>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Beim Laden der Kontakte ist ein Fehler aufgetreten");
			
		}

		@Override
		public void onSuccess(List<Kontakt> result) {
			
			Window.alert("Es wurden " + result.size() + " Kontakte geladen");
			
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
	
	class KontaktlisteKontaktCallBack implements AsyncCallback<Kontaktliste>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Beim Laden der Kontakte ist ein Fehler aufgetreten");
			
		}

		@Override
		public void onSuccess(Kontaktliste result) {

			konList = result;
			Window.alert(" Bezeichnung der Liste: " + konList.getBez() + " und ID der Liste: " +  konList.getID());
			
			
			
		}
		
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
						
					}
					
				});

			}
			cellList.setRowCount(kList2.size(), true);
			cellList.setRowData(0, kList2);
			vpanelDetails.add(html1);
			vpanelDetails.add(cellList);
			RootPanel.get("Details").add(vpanelDetails);
			
		}
		
	}
	
	

}
