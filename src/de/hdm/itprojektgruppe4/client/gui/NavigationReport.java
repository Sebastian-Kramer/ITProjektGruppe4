package de.hdm.itprojektgruppe4.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.ReportGeneratorAsync;
import de.hdm.itprojektgruppe4.shared.report.AllEigeneKontakteReport;
import de.hdm.itprojektgruppe4.shared.report.HTMLReportWriter;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmterTeilhaberschaftReport;

public class NavigationReport extends VerticalPanel {

	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();
	private static ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportVerwaltung();

	private VerticalPanel vPanel = new VerticalPanel();

	private Button allKontakte = new Button("Alle Kontakte");
	private Button teilhaberschaft = new Button("Kontakte die mit bestimmten Nutzer geteilt worden sind");
	private Button eigenschaftsauspraegung = new Button("Kontakte mit bestimmten Eigenschaftsauspreagungen");

	public void onLoad() {
		super.onLoad();

		allKontakte.setPixelSize(245, 40);

		RootPanel.get("Navigator").clear();
		vPanel.add(allKontakte);
		vPanel.add(eigenschaftsauspraegung);
		vPanel.add(teilhaberschaft);

		RootPanel.get("Navigator").add(vPanel);

		allKontakte.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get("Details").clear();
				KontaktFormReport kontaktFormReport = new KontaktFormReport();
				RootPanel.get("Details").add(kontaktFormReport);

			}
		});

		teilhaberschaft.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get("Details").clear();
				TeilhaberschaftFormReport kontaktFormReport = new TeilhaberschaftFormReport();
				RootPanel.get("Details").add(kontaktFormReport);

			}
		});

		eigenschaftsauspraegung.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get("Details").clear();
				EigenschaftAuspraegungForm eigenschaftAuspraegungForm = new EigenschaftAuspraegungForm();
				RootPanel.get("Details").add(eigenschaftAuspraegungForm);

			}

		});

	}
	
}
