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
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.ReportGeneratorAsync;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.report.HTMLReportWriter;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenAuspraegungen;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaften;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaftsAuspraegungen;

public class EigenschaftAuspraegungFormReport extends VerticalPanel {

	private static ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportVerwaltung();

	private HorizontalPanel hPanel = new HorizontalPanel();

	private Label eigenschaftLabel = new Label("Eigenschaft");

	private Label auspraegungLabel = new Label("Auspraegung");

	private TextBox eigenschafBox = new TextBox();

	private TextBox auspraegungBox = new TextBox();

	private Button sendButton = new Button("Absenden");

	Nutzer nutzer = new Nutzer();

	public void onLoad() {
		super.onLoad();

		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));

		RootPanel.get("Buttonbar").clear();
		RootPanel.get("Details").clear();
		hPanel.add(eigenschaftLabel);
		hPanel.add(eigenschafBox);
		hPanel.add(auspraegungLabel);
		hPanel.add(auspraegungBox);
		hPanel.add(sendButton);
		RootPanel.get("Buttonbar").add(hPanel);

		sendButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (auspraegungBox.getText() != null && eigenschafBox.getText().isEmpty()) {
					
					reportverwaltung.kontakteMitBestimmtenAuspraegungen(nutzer.getID(), auspraegungBox.getText(), new AsyncCallback<KontakteMitBestimmtenAuspraegungen>() {
				

								@Override
								public void onSuccess(KontakteMitBestimmtenAuspraegungen result) {
									Window.alert("Alle Kontakte mit der eingegebenen Ausprägung wurden geladen");

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
									Window.alert("Fehler Auspraegung");
								}

		
							});

				} else if (eigenschafBox.getValue() != null && auspraegungBox.getText().isEmpty()) {

					
					reportverwaltung.kontakteMitBestimmtenEigenschaften(nutzer.getID(), eigenschafBox.getText() , new AsyncCallback<KontakteMitBestimmtenEigenschaften>() {

								@Override
								public void onFailure(Throwable caught) {
									RootPanel.get("Details").clear();
									Window.alert("Fehler Eigenschaft");
								}
  
								@Override
								public void onSuccess(KontakteMitBestimmtenEigenschaften result) {
									Window.alert("Alle Kontakte mit der eingegebenen Eigenschaft wurden geladen");
									if (result != null) {

										HTMLReportWriter writer = new HTMLReportWriter();
										writer.process(result);
										RootPanel.get("Details").clear();
										RootPanel.get("Details").add(new HTML(writer.getReportText()));
									}   
								}
							});
				} else if (eigenschafBox.getValue() != null && auspraegungBox != null) {

					reportverwaltung.kontakteMitBestimmtenEigenschaftsAuspraegungen(nutzer.getID(),
							eigenschafBox.getValue(), auspraegungBox.getValue(),
							new AsyncCallback<KontakteMitBestimmtenEigenschaftsAuspraegungen>() {
								@Override
								public void onFailure(Throwable caught) {
									RootPanel.get("Details").clear();
									Window.alert("Fehler");
								}

								@Override
								public void onSuccess(KontakteMitBestimmtenEigenschaftsAuspraegungen result) {

									Window.alert("Alle Kontakte mit der eingegebenen Eigenschaft und Ausprägung wurden geladen");

									if (result != null) {

										HTMLReportWriter writer = new HTMLReportWriter();
										writer.process(result);
										RootPanel.get("Details").clear();
										RootPanel.get("Details").add(new HTML(writer.getReportText()));
									}

								}

							});

				}else if (auspraegungBox.getText().isEmpty() && eigenschafBox.getText().isEmpty()){
					Window.alert("Bitte Daten eingeben");
				}

			}

		});

	}

}
