package de.hdm.itprojektgruppe4.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;

public class NavigationReport extends VerticalPanel {

	static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();

	private VerticalPanel vPanel = new VerticalPanel();

	/*
	 * Widgets werden angelegt
	 */
	private Button allKontakte = new Button("Alle Kontakte");
	private Button teilhaberschaft = new Button("Kontakte die mit bestimmten Nutzer geteilt worden sind");
	private Button eigenschaftsauspraegung = new Button("Kontakte mit bestimmten Eigenschaftsauspreagungen");

	public void onLoad() {
		super.onLoad();

		allKontakte.setPixelSize(245, 40);

		/*
		 * Widgets werden den Panel hinzugefügt
		 */
		RootPanel.get("Navigator").clear();
		vPanel.add(allKontakte);
		vPanel.add(eigenschaftsauspraegung);
		vPanel.add(teilhaberschaft);

		RootPanel.get("Navigator").add(vPanel);

		/**
		 * Bei aufruf dieses Clickhandlers, wird auf die Klasse
		 * KontaktformReport verwiesen/ aufgerufen
		 */
		allKontakte.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get("Details").clear();
				KontaktFormReport kontaktFormReport = new KontaktFormReport();
				RootPanel.get("Details").add(kontaktFormReport);

			}
		});

		/**
		 * Bei aufruf dieses Clickhandlers, wird auf die Klasse
		 * TeilhaberschaftFormReport verwiesen/ aufgerufen
		 */
		teilhaberschaft.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get("Details").clear();
				TeilhaberschaftFormReport teilhaberschaftReport = new TeilhaberschaftFormReport();
				RootPanel.get("Details").add(teilhaberschaftReport);

			}
		});

		/**
		 * Bei aufruf dieses Clickhandlers, wird auf die Klasse
		 * EigenschaftAuspraegungFormReport verwiesen/ aufgerufen
		 */
		eigenschaftsauspraegung.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				RootPanel.get("Details").clear();
				EigenschaftAuspraegungFormReport eigenschaftAuspraegungForm = new EigenschaftAuspraegungFormReport();
				RootPanel.get("Details").add(eigenschaftAuspraegungForm);

			}

		});

	}

}
