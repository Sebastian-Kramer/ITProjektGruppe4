package de.hdm.itprojektgruppe4.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.client.EigenschaftAuspraegungWrapper;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;

public class DialogBoxTeilhaberschaftLoeschen extends DialogBox {

	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();

	private Kontakt kon = new Kontakt();
	private Nutzer nutzer = new Nutzer();
	private EigenschaftAuspraegungWrapper ea = new EigenschaftAuspraegungWrapper();

	private VerticalPanel vpanel = new VerticalPanel();
	private HorizontalPanel hpanel = new HorizontalPanel();

	private CellTable<Nutzer> ct = new CellTable<Nutzer>();

	private HTML html1 = new HTML("Diese Nutzer haben eine Teilhaberschaft an der Ausprägung");

	private Button teilLoeschen = new Button("Teilhaberschaft entfernen");
	private Button abbrechen = new Button("Zurück");
	
	final MultiSelectionModel<Nutzer> selectionModel = new MultiSelectionModel<Nutzer>();


	public DialogBoxTeilhaberschaftLoeschen(EigenschaftAuspraegungWrapper eaw) {
		this.ea = eaw;
	}

	public void onLoad() {

		super.onLoad();

		Window.alert("DialogboxForm");

		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));

		verwaltung.getAllTeilhaberfromAuspraegung(ea.getAuspraegungID(), new AllTeilhaberschaften());

		Column<Nutzer, String> nutzer = new Column<Nutzer, String>(new ClickableTextCell()) {

			@Override
			public String getValue(Nutzer object) {

				return object.getEmail();
			}

		};
		
		Column<Nutzer, Boolean> checkBox = new Column<Nutzer, Boolean>(
				new CheckboxCell(true, false)) {

					@Override
					public Boolean getValue(Nutzer object) {
						
						return selectionModel.isSelected(object);
					}

		};

		abbrechen.addClickHandler(new AbbrechenClickHandler());
		teilLoeschen.addClickHandler(new LoeschenClickHandler());

		ct.addColumn(nutzer, "Teilhaber");
		ct.addColumn(checkBox);
		ct.setSelectionModel(selectionModel);
		
		selectionModel.addSelectionChangeHandler(new ChangeHandler());

		hpanel.add(teilLoeschen);
		hpanel.add(abbrechen);
		vpanel.add(html1);
		vpanel.add(ct);
		vpanel.add(hpanel);

		this.addStyleName("DialogBoxDeleteTeilhaberschaft");
		this.add(vpanel);

	}

	class AbbrechenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			hide();

		}
	}
	
	class LoeschenClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			if(selectionModel.getSelectedSet().isEmpty()){
				Window.alert("Sie müssen zum Löschen einer Teilhaberschaft einen Nutzer auswählen");
			}else{
				Window.alert("Geht");
//				verwaltung.deleteTeilhaberschaft(t, callback);
			}
				
		}
		
	}

	class AllTeilhaberschaften implements AsyncCallback<Vector<Nutzer>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Die Teilhaber der gewählten Ausprägung konnten nicht geladen werden");

		}

		@Override
		public void onSuccess(Vector<Nutzer> result) {
			ct.setRowData(0, result);
			ct.setRowCount(result.size(), true);

		}

	}
	
	class ChangeHandler implements Handler{

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
