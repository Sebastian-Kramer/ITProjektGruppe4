package de.hdm.itprojektgruppe4.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.client.NavigationTree;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;

public class DialogBoxNewKontaktliste extends DialogBox {

	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();
	KontaktlisteKontaktTreeViewModel kktvm = null;

	private VerticalPanel vpanel = new VerticalPanel();

	private Button anlegen = new Button("Kontaktliste anlegen");

	private HTML labelListe = new HTML("<h3>Neue Kontaktliste anlegen</h3>");

	private Label labelBez = new Label("Bezeichnung: ");
	private TextBox boxBez = new TextBox();

	private FlexTable kList = new FlexTable();

	private Nutzer nutzer = new Nutzer();

	public DialogBoxNewKontaktliste() {

	}

	public void onLoad() {

		super.onLoad();

		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));

		kList.setWidget(0, 0, labelListe);
		kList.setWidget(2, 0, labelBez);
		kList.setWidget(2, 1, boxBez);
		kList.setWidget(4, 1, anlegen);

		vpanel.add(kList);

		this.setStyleName("DialogboxBackground");
		this.add(vpanel);

		anlegen.addClickHandler(new KontaktListeAnlegen());


	}

	class KontaktListeAnlegen implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			hide();
			verwaltung.insertKontaktliste(boxBez.getText(), 0, nutzer.getID(), new Liste());
			MainForm mf = new MainForm();
			NavigationTree navigation = new NavigationTree();
			RootPanel.get("Details").clear();
			RootPanel.get("Navigation").clear();
			RootPanel.get("Buttonbar").clear();
			RootPanel.get("Navigation").add(navigation);
			RootPanel.get("Details").add(mf);						

		}

	}

	class Liste implements AsyncCallback<Kontaktliste> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Die Kontaktliste konnte leider nicht angelegt werden");

		}

		@Override
		public void onSuccess(Kontaktliste result) {
			kktvm.addKontaktliste(result);
			
			Window.alert("Die Kontaktliste wurde erfolgreich angelegt");

		}

	}
	
	void setKktvw(KontaktlisteKontaktTreeViewModel kktvm) {
		this.kktvm = kktvm;
	}
	

}
