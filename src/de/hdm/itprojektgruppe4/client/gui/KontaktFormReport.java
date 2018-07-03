
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

/**

 * Klasse für den Report welche die Eigenen Kontakte in Form eines Reports
 * ausgibt
 * 
 * @author Georg
 *
 */
public class KontaktFormReport extends VerticalPanel {

	private static ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportVerwaltung();

	private HorizontalPanel hPanel = new HorizontalPanel();

	Nutzer nutzer = new Nutzer();

	/*
	 * Methode zu Anzeigen des Reports
	 */
	public void onLoad() {

		super.onLoad();
		/*
		 * Eingeloggter Nutzer wird anhand Cookies identifiziert
		 */
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));

		RootPanel.get("Details").clear();
		RootPanel.get("Buttonbar").clear();
		RootPanel.get("Buttonbar").add(hPanel);

		/**
		 * Report wird aufgerufen, welcher die eigenen Kontakte wiedergibt
		 * 
		 */
		reportverwaltung.AllEigeneKontakteReport(nutzer.getID(), new AllEigeneKontakteReportCallback());
				
				

	}

	/**
	 * Aufruf des Callback, welche die Eigenen Kontakte
	 * in Form eines Reports ausgibt
	 * @author Georg
	 *
	 */
	class AllEigeneKontakteReportCallback implements AsyncCallback<AllEigeneKontakteReport> {


		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			RootPanel.get("Details").clear();
			Window.alert("Fehler");
		}


		@Override
		public void onSuccess(AllEigeneKontakteReport result) {
			// TODO Auto-generated method stub
			if (result != null) {
				if (result.getRows().size() == 1 ) {
					Window.alert("Kein Report für diese Abfrage vorhanden.");
				}else{
				HTMLReportWriter writer = new HTMLReportWriter();
				writer.process(result);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(new HTML(writer.getReportText()));
			}}

		}
		}
		
	}

