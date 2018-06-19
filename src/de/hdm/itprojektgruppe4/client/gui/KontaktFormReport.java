package de.hdm.itprojektgruppe4.client.gui;


import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.ReportGeneratorAsync;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.report.AllEigeneKontakteReport;
import de.hdm.itprojektgruppe4.shared.report.HTMLReportWriter;

public class KontaktFormReport extends VerticalPanel {
	
	private static ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportVerwaltung();

	
	private HorizontalPanel hPanel = new HorizontalPanel();
	
	
	Nutzer nutzer = new Nutzer (); 


	
	public void onLoad() {
		super.onLoad();
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));

		RootPanel.get("Details").clear();
		RootPanel.get("Buttonbar").clear();
		RootPanel.get("Buttonbar").add(hPanel);
		
		


				// TODO Auto-generated method stub
				reportverwaltung.AllEigeneKontakteReport(nutzer.getID(),new AsyncCallback<AllEigeneKontakteReport>() {
					@Override
					public void onFailure(Throwable caught) {
						RootPanel.get("Details").clear();
						Window.alert("Fehler");

					}

					@Override
					public void onSuccess(AllEigeneKontakteReport result) {

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
