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

public class NavigationReport extends VerticalPanel {

	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();
	private static ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportVerwaltung();

	private VerticalPanel vPanel = new VerticalPanel();

	private Button alleeigenenKontakte = new Button("Alle Eigenen Kontakte");

	public void onLoad() {
		super.onLoad();

		RootPanel.get("Navigator").clear();
		vPanel.add(alleeigenenKontakte);
		RootPanel.get("Navigator").add(vPanel);

		alleeigenenKontakte.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				// TODO Auto-generated method stub
				reportverwaltung.AllEigeneKontakteReport(new AsyncCallback<AllEigeneKontakteReport>() {
					@Override
					public void onFailure(Throwable caught) {
						RootPanel.get("Details").clear();
						Window.alert("Fehler");

					}

					@Override
					public void onSuccess(AllEigeneKontakteReport result) {
						Window.alert("Läuft");

						// TODO Auto-generated method stub
						if (result != null) { 	
							HTMLReportWriter writer = new HTMLReportWriter();
							writer.process(result);
							RootPanel.get("Details").clear();
							RootPanel.get("Details").add(new HTML(writer.getReportText()));
						}

					}
				});

			}
		});

	}

}
