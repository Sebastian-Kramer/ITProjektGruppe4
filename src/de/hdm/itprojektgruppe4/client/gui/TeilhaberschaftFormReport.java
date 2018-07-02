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

/**
 * Klasse f�r die Erstellung des Reports,
 * welche s�mtliche Kontakte des Nutzes die mit bestimmten Nutzern 
 * geteilt worden sind ausgibt
 * @author Georg
 *
 */
public class TeilhaberschaftFormReport extends VerticalPanel {

	private static ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportVerwaltung();
	/*
	 * Widgets werden angelegt
	 */
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

	/*
	 * Aufruf des Callbacks, welcher den Nutzer ausliest
	 */
	class NutzerlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			reportverwaltung.findNutzerByEmail(nutzerBox.getValue(), new FindNutzerCallBack());

		}

	}

	/**
	 * Aufruf des Callback für die SuggestBox, welcher
	 * Nutzer wiedergibt
	 * @author Georg
	 *
	 */
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

	/**
	 *  Aufruf des Report Callback
	 *  hierbei wird die ID und Email des Nutzer gesetzt 
	 *  dannach wird der Report aufgerufen
	 *  und anschlie�en erfolgt die Procces methode der Klasse HTMLReportWrtitter
	 *  und S�mtliche Kontakte die mit bestimmten Nutzern geteilt worden sind,
	 *  werden in Form eines Reports ausgebgeben 
	 * @author Georg
	 *
	 */

	/**
	 * Aufruf des Callback, welcher Kontakte 
	 * mit bestimmter Teilhaberschaft
	 * in Form eines Reports
	 * wiedergibt
	 * @author Georg
	 *
	 */
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
