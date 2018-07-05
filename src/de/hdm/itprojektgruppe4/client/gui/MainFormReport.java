package de.hdm.itprojektgruppe4.client.gui;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Begrüßungsklasse für den Report
 * @author Georg
 *
 */
public class MainFormReport extends VerticalPanel {

	private HTML reportHTML = new HTML("<h1> Herzlich Willkommen auf dem Report der Kontaktverwaltung Gruppe 4</h1>");
	private VerticalPanel vpanel = new VerticalPanel();

	public MainFormReport() {
	}

	public void onLoad() {

		super.onLoad();

		vpanel.add(reportHTML);

		this.add(reportHTML);

	}
}
