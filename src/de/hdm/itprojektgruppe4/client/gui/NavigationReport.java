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
	private Button teilhaberschaft = new Button("Kontakte die mit bestimmten Nutzer geteilt worden sind");
	private Button eigenschaftsauspraegung = new Button("Kontakte mit bestimmten Eigenschaftsauspreagungen");



	public void onLoad() {
		super.onLoad();
		
		alleeigenenKontakte.setPixelSize(245, 40);

		RootPanel.get("Navigator").clear();
		vPanel.add(alleeigenenKontakte);
		vPanel.add(eigenschaftsauspraegung);
		vPanel.add(teilhaberschaft);

		RootPanel.get("Navigator").add(vPanel);

		alleeigenenKontakte.addClickHandler(new AlleEigenenKontakteClickHandler());
		teilhaberschaft.addClickHandler(new TeilhaberschaftClickHandler());	
	
	}
	
class AlleEigenenKontakteClickHandler implements ClickHandler{

	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub
		

							// TODO Auto-generated method stub
							reportverwaltung.AllEigeneKontakteReport(new AsyncCallback<AllEigeneKontakteReport>() {
								@Override
								public void onFailure(Throwable caught) {
									RootPanel.get("Details").clear();
									Window.alert("Fehler");
			
								}
			
								@Override
							public void onSuccess(AllEigeneKontakteReport result) {
								Window.alert("L�uft");
			
									if (result != null) { 	
										HTMLReportWriter writer = new HTMLReportWriter();
										writer.process(result);
										RootPanel.get("Details").clear();
										RootPanel.get("Details").add(new HTML(writer.getReportText()));
									}
			
							}
							});
			
						}
	}
	
	
	class TeilhaberschaftClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			reportverwaltung.KontakteMitBestimmterTeilhaberschaftReport(new AsyncCallback<AllEigeneKontakteReport>() {
				@Override
				public void onFailure(Throwable caught) {
					RootPanel.get("Details").clear();
					Window.alert("Fehler");

				}

				@Override
			public void onSuccess(AllEigeneKontakteReport result) {
				RootPanel.get("Details").clear();
				Window.alert("L�uft" + result);

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
		}
	}
		
	

