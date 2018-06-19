package de.hdm.itprojektgruppe4.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.client.ImpressumSeite;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;

/**
 * 
 * @author Sebi_
 *
 */

public class MainForm extends VerticalPanel {

	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();

	Kontakt kon = new Kontakt();
	Kontaktliste konList = new Kontaktliste();
	Kontaktliste selectedKontaktlist = null;
	Nutzer nutzer = new Nutzer();

	private VerticalPanel vpanelDetails = new VerticalPanel();
	private HorizontalPanel hpanelDetails = new HorizontalPanel();

	private FlexTable anordnung = new FlexTable();

	private Button newKontakt = new Button("Neuer Kontakt anlegen");
	private Button newKontaktliste = new Button("Neue Kontaktliste anlegen");
	private Button suchen = new Button("Suchen");
	private HTML greetHTML1 = new HTML("<h2>MyContacs<h2>");
	private HTML greetHTML2 = new HTML("Herzlich Willkommen auf MyContacts, " + "<br>Ihrer Kontaktverwaltung. "
			+ "<br> Hier können Sie neue Kontakte anlegen, " + "<br> diese in verschiedene Listen organisieren"
			+ "<br> und sowohl die einzelnen Kontakte und Kontaktlisten mit anderen Nutzern teilen.");

	private HTML html1 = new HTML("<h2>Alle Kontakte</h2>");

	private KontaktCell kontaktCell = new KontaktCell();
	private CellList<Kontakt> cellList = new CellList<Kontakt>(kontaktCell);
	final SingleSelectionModel<Kontakt> selectionModel = new SingleSelectionModel<Kontakt>();
	private ScrollPanel scrollPanel = new ScrollPanel();

	public MainForm() {
	
	}

	public void onLoad() {

		super.onLoad();
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));

		verwaltung.findAllKontaktFromNutzer(nutzer.getID(), new KontakteVonNutzerCallback());

		cellList.setSelectionModel(selectionModel);
		cellList.setPageSize(100);

		selectionModel.addSelectionChangeHandler(new SelectionChangeHandler()); 

		scrollPanel.setSize("450px", "250px");
		scrollPanel.setStyleName("scrollPanel");
		cellList.setStyleName("cellListKontakte");
		scrollPanel.add(cellList);

		RootPanel.get("Buttonbar").add(newKontaktliste);
		RootPanel.get("Buttonbar").add(newKontakt);
		RootPanel.get("Buttonbar").add(suchen);

		anordnung.setWidget(0, 0, greetHTML1);
		anordnung.setWidget(1, 0, greetHTML2);
		anordnung.setWidget(0, 1, html1);
		anordnung.setWidget(1, 1, scrollPanel);

		vpanelDetails.add(hpanelDetails);
		vpanelDetails.add(anordnung);
		this.add(vpanelDetails);

		newKontaktliste.addClickHandler(new NewListClickHandler());
		newKontakt.addClickHandler(new NewKontaktClickHandler());
		suchen.addClickHandler(new SuchenClickHandler());

	}
	
	class SelectionChangeHandler implements SelectionChangeEvent.Handler {

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			Kontakt selected = selectionModel.getSelectedObject();
			Window.alert("Sie haben folgenden Kontakt ausgewählt: " + selected.getName());
			KontaktForm kf = new KontaktForm(selected);
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(kf);
			
		}
		
	}

	class ImpressumButton implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			ImpressumSeite imp = new ImpressumSeite();
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(imp);
		}

	}

	class NewListClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			DialogBoxNewKontaktliste dbk = new DialogBoxNewKontaktliste();
			dbk.center();
		}

	}

	class NewKontaktClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			NewKontaktForm nkf = new NewKontaktForm();
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(nkf);
		}

	}

	class SuchenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			SuchenForm sf = new SuchenForm();
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(sf);

		}
	}
	
	class KontakteVonNutzerCallback implements AsyncCallback<Vector<Kontakt>>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Vector<Kontakt> result) {
			cellList.setRowCount(result.size(), true);
			cellList.setRowData(result);
			
		}
		
	}
	

	class KontaktlisteKontaktCallBack implements AsyncCallback<Kontaktliste> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Beim Laden der Kontakte ist ein Fehler aufgetreten");

		}

		@Override
		public void onSuccess(Kontaktliste result) {

			konList = result;
			Window.alert(
					" Bezeichnung der Liste: " + konList.getBez() + "\n" + " und ID der Liste: " + konList.getID());

			AllKontakteForm allKontakts = new AllKontakteForm(konList);
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(allKontakts);

		}

	}

	class KontakteVonKontaktlisteAnzeigenCallBack implements AsyncCallback<Kontakt> {

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Kontakt result) {

		}

	}

}
