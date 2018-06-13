package de.hdm.itprojektgruppe4.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.ReportGeneratorAsync;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.report.HTMLReportWriter;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmterTeilhaberschaftReport;

public class TeilhaberschaftFormReport extends VerticalPanel{

	private static ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportVerwaltung();

	private HorizontalPanel hPanel = new HorizontalPanel();

	private ListBox nutzerListe = new ListBox();
	
	private Nutzer nutzer1 = new Nutzer();

	public void onLoad() {
		super.onLoad();

		RootPanel.get("Details").clear();
		RootPanel.get("Buttonbar").clear();

		hPanel.add(nutzerListe);
		RootPanel.get("Buttonbar").add(hPanel);
		
		reportverwaltung.allNutzerReport(new AsyncCallback<Vector<Nutzer>>() {


			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Vector<Nutzer> result) {
				// TODO Auto-generated method stub
			
					for (Nutzer nutzer : result) {
						nutzerListe.addItem(nutzer.getEmail());
					}
					
			}
		});
		
		
		reportverwaltung.findNutzerByEmail(nutzerListe.getSelectedValue(), new AsyncCallback<Nutzer>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Nutzer result) {
				// TODO Auto-generated method stub
				nutzer1.setID(result.getID());
			}
			
			
			
			
		});
		
		nutzerListe.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				reportverwaltung.findNutzerByEmail(nutzerListe.getSelectedValue(), new AsyncCallback<Nutzer>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(Nutzer result) {
						// TODO Auto-generated method stub
//						reportverwaltung.AlleKontakteByNutzer(result.getID(), new AsyncCallback<AllEigeneKontakteReport>() {
					
						reportverwaltung.kontakteMitBestimmterTeilhaberschaftReport(1, new AsyncCallback<KontakteMitBestimmterTeilhaberschaftReport>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void onSuccess(KontakteMitBestimmterTeilhaberschaftReport result) {
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
		});
		

	}

}
