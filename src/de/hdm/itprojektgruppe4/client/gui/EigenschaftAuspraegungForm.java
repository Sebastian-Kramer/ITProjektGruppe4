package de.hdm.itprojektgruppe4.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.ReportGeneratorAsync;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;

public class EigenschaftAuspraegungForm extends VerticalPanel {

	private static ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportVerwaltung();

	private HorizontalPanel hPanel = new HorizontalPanel();

	private ListBox eigenschaftListe = new ListBox();

	private ListBox auspraegungListe = new ListBox();

	public void onLoad() {
		super.onLoad();

		RootPanel.get("Details").clear();
		hPanel.add(eigenschaftListe);
		hPanel.add(auspraegungListe);
		RootPanel.get("Buttonbar").add(hPanel);

		reportverwaltung.findAllEigenschaft(new AsyncCallback<Vector<Eigenschaft>>() {

			@Override
			public void onSuccess(Vector<Eigenschaft> result) {
				// TODO Auto-generated method stub
				for (Eigenschaft eigenschaft : result) {
					eigenschaftListe.addItem(eigenschaft.getBezeichnung());
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});

		eigenschaftListe.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				reportverwaltung.findEigenschaftByBezeichnung(eigenschaftListe.getSelectedValue(),
						new AsyncCallback<Vector<Eigenschaft>>() {

							@Override
							public void onFailure(Throwable caught) {

								Window.alert("Fehler amk");
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(Vector<Eigenschaft> result) {
								Window.alert(result + "");
								

							}
						});

			}
		});
		
		/*
		 * Jetzt folgt Ausprägung
		 */

		//reportverwaltung.findAllEigenschaft(new AsyncCallback<Vector<Eigenschaft>>() {
		
		reportverwaltung.findAllEigenschaftsAuspraegungn(new AsyncCallback<Vector<Eigenschaftauspraegung>>() {

			public void onSuccess(Vector<Eigenschaftauspraegung> result) {
				// TODO Auto-generated method stub
				for (Eigenschaftauspraegung eigenschaftauspraegung : result) {
					auspraegungListe.addItem(eigenschaftauspraegung.getWert());
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});

	}

}
