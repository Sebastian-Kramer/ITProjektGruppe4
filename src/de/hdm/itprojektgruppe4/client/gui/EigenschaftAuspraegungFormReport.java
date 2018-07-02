package de.hdm.itprojektgruppe4.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.ReportGeneratorAsync;
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

	private TextBox eigenschafBox = new TextBox();

	private TextBox auspraegungBox = new TextBox();

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

	}
		/**
		 * Die Funktion des der Klasse namens SendButton die vom ClickHandler implementiert ist:
		 * 
		 * falls nur die Eigenschaftsbox werte aufweist, so werden lediglich die
		 * eigenenKontakte mit bestimmten Eigenschaften in form eines Report
		 * angezeigt.
		 * 
		 * Falls nur die Auspr�gungsbox werte aufweist, so werden lediglich die
		 * Eigenen Kontakte mit bestimmten Auspr�gungen in form eines Report
		 * angezeigt
		 * 
		 * Sollten beide Textboxe werte aufweisen, so werden die eigenen
		 * Kontakte mit bestimmten Eigenschafts- Auspr�gungen in form eines
		 * Report angezeigt
		 * 
		 * Sollten beide Textboxen keine werte beinhalten, so wird eine fehler
		 * meldung folgen
		 * 
		 */
		
		class sendButtonClick implements ClickHandler {
		//sendButton.addClickHandler(new ClickHandler() {

			/*
			 * If abfrage ob die auspr�gungsbox werte beinhaltet sollte dies der
			 * Fall sein, so werden die eigenen KOntakte die die selbem
			 * Auspr�gungen aufweisen in form eines Reports angezeigt
			 */
			@Override
			public void onClick(ClickEvent event) {
				if (auspraegungBox.getText() != null && eigenschafBox.getText().isEmpty()) {

					/*
					 * Aufruf des reports
					 */
					reportverwaltung.kontakteMitBestimmtenAuspraegungen(nutzer.getID(), auspraegungBox.getText(),
							new AsyncCallback<KontakteMitBestimmtenAuspraegungen>() {

								@Override
								public void onSuccess(KontakteMitBestimmtenAuspraegungen result) {

									if (result != null) {

										HTMLReportWriter writer = new HTMLReportWriter();
										writer.process(result);
										RootPanel.get("Details").clear();
										RootPanel.get("Details").add(new HTML(writer.getReportText()));
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
				} else if (eigenschafBox.getValue() != null && auspraegungBox.getText().isEmpty()) {

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

										HTMLReportWriter writer = new HTMLReportWriter();
										writer.process(result);
										RootPanel.get("Details").clear();
										RootPanel.get("Details").add(new HTML(writer.getReportText()));
									}
								}
							});

					/*
					 * If abfrage ob die eigenschaftsbox & die auspr�gungsbox
					 * werte beinhaltet sollte dies der Fall sein, so werden die
					 * eigenen Kontakte die die selbe Eigenschaftsauspr�gungen
					 * aufweisen in form eines Reports angezeigt
					 */
				} else if (eigenschafBox.getValue() != null && auspraegungBox != null) {

					reportverwaltung.kontakteMitBestimmtenEigenschaftsAuspraegungen(nutzer.getID(),
							eigenschafBox.getValue(), auspraegungBox.getValue(),
							new AsyncCallback<KontakteMitBestimmtenEigenschaftsAuspraegungen>() {
								@Override
								public void onFailure(Throwable caught) {
									RootPanel.get("Details").clear();
								}

								@Override
								public void onSuccess(KontakteMitBestimmtenEigenschaftsAuspraegungen result) {


									if (result != null) {

										HTMLReportWriter writer = new HTMLReportWriter();
										writer.process(result);
										RootPanel.get("Details").clear();
										RootPanel.get("Details").add(new HTML(writer.getReportText()));
									}

								}

							});
					/*
					 * Sollten die Textboxen keine Werte beinhalten, so wird
					 * eine Fehlermeldung ausgegeben, sodass der Nutzer Werte in
					 * die Textboxen eingibt
					 */
				} else if (eigenschafBox.equals("") && auspraegungBox.equals("")) {
					Window.alert("Bitte Daten eingeben");
				}

			}

		}

	

}
