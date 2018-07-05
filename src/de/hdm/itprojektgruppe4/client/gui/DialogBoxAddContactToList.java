package de.hdm.itprojektgruppe4.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.client.NavigationTree;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.KontaktKontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;

public class DialogBoxAddContactToList extends DialogBox {
	/**
	 * In der Klasse DialogBoxAddContactToList kann man einen Kontakt 
	 * einer Kontaktliste hinzufügen. 
	 * Es wird eine Dialogbox geöffnet die durch einen Klick auf einen Button ausgelöst wird.
	 * Daraufhin wird eine Drop-Liste mit Kontaktlisten erzeugt, aus der man eine auswählen kann.
	 * Durch einen Klick auf den Button "Hinzufügen" wird der Kontakt der Liste hinzugefügt.
	 * 
	 * @author Clirim
	 *
	 */
	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();

	private VerticalPanel vpanel = new VerticalPanel();
	private HorizontalPanel hpanel = new HorizontalPanel();

	private Label pickList = new Label("Wählen Sie die gewünschte Liste aus");
	private Button addKontakt = new Button("Hinzufügen");
	private Button cancel = new Button("Abbrechen");
	private Nutzer nutzer = new Nutzer();
	private Kontaktliste kliste = new Kontaktliste();

	private ListBox dropBoxKontaktlisten = new ListBox();
	private Kontakt kon = new Kontakt();

	public DialogBoxAddContactToList(Kontakt k) {
		this.kon = k;
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));
		verwaltung.findKontaktlistenToAddKontakt(kon.getID(), nutzer.getID(), new AllKontaktlisteByNutzerCallback());
	}
	
	/**
	 * Die onLoad()-Methode wird beim Starten der DialogBoxAddContactToList geladen. 
	 * Es werden den Buttons die zugehörigen Clickhandler hinzugefügt und die verschiedenen Widgets den
	 * Panel hinzugefügt.
	 */
	public void onLoad() {
		super.onLoad();

		hpanel.add(addKontakt);
		hpanel.add(cancel);

		vpanel.add(pickList);
		vpanel.add(dropBoxKontaktlisten);
		vpanel.add(hpanel);

		this.add(vpanel);
		this.setStyleName("DialogboxBackground");

		addKontakt.addClickHandler(new addKontaktClick());

		cancel.addClickHandler(new CancelClick());

	}

	/**
	 * Der ClickHandler addKontaktClick wird durch den Button "addKontakt" ausgelöst.
	 * Dieser führt einen Callback aus, welcher den jeweiligen Kontakt in eine Liste hinzufügt.
	 * 
	 * @author Clirim
	 *
	 */
	class addKontaktClick implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			
			verwaltung.insertKontaktKontaktliste(kon.getID(), Integer.parseInt(dropBoxKontaktlisten.getSelectedValue()),
					new InsertKontaktKontaktlisteBeziehung());
		}

	}
	/**
	 * Der ClickHandler CancelClick wird durch den Button "CancelClick" ausgelöst.
	 * Dieser schließt die DialogBox. 
	 * 
	 * @author Clirim
	 *
	 */
	class CancelClick implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			hide();
		}

	}
	/**
	 * Der AsyncCallback AllKontaktlisteByNutzerCallback findet Kontaktlisten aus dem System,
	 * und gibt diese in einem Vector zurück. Diese werden einer dropdownListe hinzugefügt.
	 * 
	 * @author Clirim
	 *
	 */
	class AllKontaktlisteByNutzerCallback implements AsyncCallback<Vector<Kontaktliste>> {

		@Override
		public void onFailure(Throwable caught) {


		}

		@Override
		public void onSuccess(Vector<Kontaktliste> result) {

			if(result == null){
				
			}else{
			
			for (Kontaktliste kl : result) {

				dropBoxKontaktlisten.addItem(kl.getBez(), Integer.toString(kl.getID()));

				kliste.setID(kl.getID());
			}}

		}
	}
	
	/**
	 * Der AsyncCallback InsertKontaktKontaktlisteBeziehung fügt den jeweiligen Kontakt 
	 * der ausgewählten Kontaktliste hinzu.
	 * 
	 * @author Clirim
	 *
	 */
	class InsertKontaktKontaktlisteBeziehung implements AsyncCallback<KontaktKontaktliste> {

		@Override
		public void onFailure(Throwable caught) {

			Window.alert("Der Kontakt wurde nicht der Liste hinzugefügt");
		}

		@Override
		public void onSuccess(KontaktKontaktliste result) {

				Window.alert("Der Kontakt wurde erfolgreich der Liste hinzugefügt");
				NavigationTree navigationTree = new NavigationTree();
				RootPanel.get("Navigator").clear();
				RootPanel.get("Navigator").add(navigationTree);
				hide();		

		}

	}

}
