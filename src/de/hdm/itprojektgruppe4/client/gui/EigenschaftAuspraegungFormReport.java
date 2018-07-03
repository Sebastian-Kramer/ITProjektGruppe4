package de.hdm.itprojektgruppe4.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.ReportGeneratorAsync;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.report.HTMLReportWriter;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenAuspraegungen;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaften;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaftsAuspraegungen;

public class EigenschaftAuspraegungFormReport extends VerticalPanel {

	/*
	 * Widgets, werden als Attribute angelegt
	 */

	private static ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportVerwaltung();

	private HorizontalPanel hPanel = new HorizontalPanel();

	private Label eigenschaftLabel = new Label("Eigenschaft");

	private Label auspraegungLabel = new Label("Auspraegung");
	private MultiWordSuggestOracle eigenschaftOracle = new MultiWordSuggestOracle();
	private MultiWordSuggestOracle auspraegungOracle = new MultiWordSuggestOracle();
	private SuggestBox eigenschafBox = new SuggestBox(eigenschaftOracle);
	private Vector<Eigenschaftauspraegung> vecAlleAuspr = new Vector<Eigenschaftauspraegung>();

	private SuggestBox auspraegungBox = new SuggestBox(auspraegungOracle);

	private Button sendButton = new Button("Absenden");

	Nutzer nutzer = new Nutzer();

	/*
	 * Beim Anzeigen werden die Widgets erzeugt.
	 */
	public void onLoad() {
		super.onLoad();
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));

		/*
		 * Widgets werden den Panel hinzugef�gt
		 */
		RootPanel.get("Buttonbar").clear();
		RootPanel.get("Details").clear();
		hPanel.add(eigenschaftLabel);
		hPanel.add(eigenschafBox);
		hPanel.add(auspraegungLabel);
		hPanel.add(auspraegungBox);
		hPanel.add(sendButton);
		RootPanel.get("Buttonbar").add(hPanel);

		sendButton.addClickHandler(new sendButtonClick());
		reportverwaltung.findAllEigenschaft(new AlleEigCallback());
		reportverwaltung.findAllAuspraegungen(nutzer.getID(), new AlleAusCallback());

	}

	/**
	 * Die Funktion des der Klasse namens SendButton die vom ClickHandler
	 * implementiert ist:
	 * 
	 * falls nur die Eigenschaftsbox werte aufweist, so werden lediglich die
	 * eigenenKontakte mit bestimmten Eigenschaften in form eines Report
	 * angezeigt.
	 * 
	 * Falls nur die Auspr�gungsbox werte aufweist, so werden lediglich die
	 * Eigenen Kontakte mit bestimmten Auspr�gungen in form eines Report
	 * angezeigt
	 * 
	 * Sollten beide Textboxe werte aufweisen, so werden die eigenen Kontakte
	 * mit bestimmten Eigenschafts- Auspr�gungen in form eines Report angezeigt
	 * 
	 * Sollten beide Textboxen keine werte beinhalten, so wird eine fehler
	 * meldung folgen
	 * 
	 */

	class sendButtonClick implements ClickHandler {
		boolean abfrage = false;
		// sendButton.addClickHandler(new ClickHandler() {

		/*
		 * If abfrage ob die auspr�gungsbox werte beinhaltet sollte dies der
		 * Fall sein, so werden die eigenen KOntakte die die selbem Auspr�gungen
		 * aufweisen in form eines Reports angezeigt
		 */
		@Override
		public void onClick(ClickEvent event) {

			if (eigenschafBox.getText().isEmpty() && auspraegungBox.getText().isEmpty()) {
				Window.alert("Bitte Daten eingeben");
			}

			else if (auspraegungBox.getText() != null && eigenschafBox.getText().isEmpty()) {
				
				for (Eigenschaftauspraegung auspraegung : vecAlleAuspr) {
					if (auspraegung.getWert().equals(auspraegungBox.getText())) {
						abfrage = true;
					}
				}
				if (abfrage == true) {

					/*
					 * Aufruf des reports
					 */

					reportverwaltung.kontakteMitBestimmtenAuspraegungen(nutzer.getID(), auspraegungBox.getText(),
							new AsyncCallback<KontakteMitBestimmtenAuspraegungen>() {

								@Override
								public void onSuccess(KontakteMitBestimmtenAuspraegungen result) {

									if (result != null) {
										if (result.getRows().size() == 1) {
											Window.alert("Kein Report für diese Abfrage vorhanden.");
										} else {
											HTMLReportWriter writer = new HTMLReportWriter();
											writer.process(result);
											RootPanel.get("Details").clear();
											RootPanel.get("Details").add(new HTML(writer.getReportText()));
										}
									}
								}

								@Override
								public void onFailure(Throwable caught) {
									RootPanel.get("Details").clear();
								}

							});

					/*
					 * If abfrage ob die eigenschaftsbox werte beinhaltet sollte
					 * dies der Fall sein, so werden die eigenen Kontakte die
					 * die selbem Eigenschaften aufweisen in form eines Reports
					 * angezeigt
					 */
				}
			} else if (eigenschafBox.getText() != null && auspraegungBox.getText().isEmpty()) {

				/*
				 * Aufruf des reports
				 */

				reportverwaltung.kontakteMitBestimmtenEigenschaften(nutzer.getID(), eigenschafBox.getText(),
						new AsyncCallback<KontakteMitBestimmtenEigenschaften>() {

							@Override
							public void onFailure(Throwable caught) {
								RootPanel.get("Details").clear();
							}

							@Override
							public void onSuccess(KontakteMitBestimmtenEigenschaften result) {
								if (result != null) {
									if (result.getRows().size() == 1) {
										Window.alert("Kein Report für diese Abfrage vorhanden.");
									} else {
										HTMLReportWriter writer = new HTMLReportWriter();
										writer.process(result);
										RootPanel.get("Details").clear();
										RootPanel.get("Details").add(new HTML(writer.getReportText()));
									}
								}
							}
						});

				/*
				 * If abfrage ob die eigenschaftsbox & die auspr�gungsbox werte
				 * beinhaltet sollte dies der Fall sein, so werden die eigenen
				 * Kontakte die die selbe Eigenschaftsauspr�gungen aufweisen in
				 * form eines Reports angezeigt
				 */
			} else if (eigenschafBox.getText() != null && auspraegungBox.getText() != null) {
				for (Eigenschaftauspraegung auspraegung : vecAlleAuspr) {
					if (auspraegung.getWert().equals(auspraegungBox.getText())) {
						abfrage = true;
						
					}
				}
				if (abfrage == true) {
				
				reportverwaltung.kontakteMitBestimmtenEigenschaftsAuspraegungen(nutzer.getID(),
						eigenschafBox.getText(), auspraegungBox.getText(),
						new AsyncCallback<KontakteMitBestimmtenEigenschaftsAuspraegungen>() {
							@Override
							public void onFailure(Throwable caught) {
								RootPanel.get("Details").clear();
							}

							@Override
							public void onSuccess(KontakteMitBestimmtenEigenschaftsAuspraegungen result) {

								if (result != null) {
									if (result.getRows().size() == 1) {
										Window.alert("Kein Report für diese Abfrage vorhanden.");
									} else {
										HTMLReportWriter writer = new HTMLReportWriter();
										writer.process(result);
										RootPanel.get("Details").clear();
										RootPanel.get("Details").add(new HTML(writer.getReportText()));
									}
								}

							}

						});
				/*
				 * Sollten die Textboxen keine Werte beinhalten, so wird eine
				 * Fehlermeldung ausgegeben, sodass der Nutzer Werte in die
				 * Textboxen eingibt
				 */
				}
			}

		}

	}

	/**
	 * Callback Klasse um alle Eigenschaften zu selektieren und in dem
	 * MultiWordSuggestOracle zu speichern.
	 * 
	 * @author Marcus
	 *
	 */

	private class AlleEigCallback implements AsyncCallback<Vector<Eigenschaft>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Vector<Eigenschaft> result) {
			// TODO Auto-generated method stub
			for (Eigenschaft eigenschaft : result) {
				eigenschaftOracle.add(eigenschaft.getBezeichnung());
			}
		}

	}

	/**
	 * Callback Klasse um alle EIgenschaftsausprägungen in einem
	 * MultiWordSuggestOracle zu speichern. Es werden nur Ausprägungen von
	 * eigenen Kontakten oder Ausprägungen zu denen eine Teilhaberschaft besteht
	 * selektiert.
	 * 
	 * @author Marcus
	 *
	 */
	private class AlleAusCallback implements AsyncCallback<Vector<Eigenschaftauspraegung>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Vector<Eigenschaftauspraegung> result) {
			// TODO Auto-generated method stub
			vecAlleAuspr = result;
			for (Eigenschaftauspraegung eigenschaftauspraegung : result) {
				auspraegungOracle.add(eigenschaftauspraegung.getWert());
			}
		}

	}

}
