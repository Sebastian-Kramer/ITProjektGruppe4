package de.hdm.itprojektgruppe4.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.ReportGeneratorAsync;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.report.HTMLReportWriter;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmterTeilhaberschaftReport;

public class TeilhaberschaftFormReport extends VerticalPanel {

	private static ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportVerwaltung();
	private MultiWordSuggestOracle nutzerOracle = new MultiWordSuggestOracle();
	private SuggestBox nutzerBox = new SuggestBox(nutzerOracle);

	private Button reportAnzeigen = new Button("Kontakte anzeigen");

	Nutzer nutzer = new Nutzer();

	private Nutzer nutzer1 = new Nutzer();

	public void onLoad() {
		super.onLoad();

		nutzerBox.setPixelSize(245, 35);

		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));

		RootPanel.get("Details").clear();
		RootPanel.get("Buttonbar").clear();

		RootPanel.get("Buttonbar").add(nutzerBox);
		RootPanel.get("Buttonbar").add(reportAnzeigen);

		reportverwaltung.allNutzerReport(new AllNutzerCallback());
		reportAnzeigen.addClickHandler(new NutzerlisteClickHandler());

	}

	class NutzerlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			reportverwaltung.findNutzerByEmail(nutzerBox.getValue(), new FindNutzerCallBack());

		}

	}

	class AllNutzerCallback implements AsyncCallback<Vector<Nutzer>> {

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Vector<Nutzer> result) {

			for (Nutzer n : result) {

				if (n.getID() != nutzer.getID()) {
					nutzerOracle.add(n.getEmail());
				} else {

				}

			}

		}

	}

	// in Bearbeitung

	class FindNutzerCallBack implements AsyncCallback<Nutzer> {

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Nutzer result) {

			nutzer1.setID(result.getID());
			nutzer1.setEmail(result.getEmail());

			reportverwaltung.kontakteMitBestimmterTeilhaberschaftReport(nutzer.getID(), nutzer1.getID(),
					new AsyncCallback<KontakteMitBestimmterTeilhaberschaftReport>() {

						@Override
						public void onFailure(Throwable caught) {

						}

						@Override
						public void onSuccess(KontakteMitBestimmterTeilhaberschaftReport result) {

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
