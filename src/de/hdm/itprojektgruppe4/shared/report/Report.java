package de.hdm.itprojektgruppe4.shared.report;

import java.io.Serializable;

/**
 * Basisklasse der Reports Reports sind als Serializable deklariert um die vom
 * Server an den Client gesendet werden können. Zugriff auf Reports erfolgt nach
 * deren Bereitstellung lokal auf dem CLient.
 * 
 * Code in Anlehnung an Bankprojekt von Prof. Thies
 */

public abstract class Report implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * "Impressum"
	 */

	private Paragraph imprint = null;

	/**
	 * Individueller Titel
	 */

	private String title = "Report";

	/**
	 * Kopfdaten des Reports
	 */

	private Paragraph headerData = null;

	/**
	 * Erstelldatum des Reports
	 */

	private String created = "";

	/**
	 * Auslesen des Impressums
	 * 
	 * @return Text des Impressums
	 */

	public Paragraph getImprint() {
		return this.imprint;
	}

	/**
	 * Setzen des Impressums
	 * 
	 * @param imprint
	 *            des Textes des Impressums
	 */

	public void setImprint(Paragraph imprint) {
		this.imprint = imprint;
	}

	/**
	 * Auslesen des Headers
	 * 
	 * @return Text der Headerdaten
	 */

	public Paragraph getHeaderData() {
		return this.headerData;
	}

	/**
	 * Setzen der Headerdaten
	 * 
	 * @param headerData
	 */

	public void setHeaderData(Paragraph headerData) {
		this.headerData = headerData;
	}

	/**
	 * Auslesen des Reporttitels
	 * 
	 * @return Titeltext
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Setzen des Reporttitels
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Auslesen des Erstelldatums des Reports
	 * 
	 * @return Erstelldatum des Berichts
	 */

	public String getCreated() {
		return this.created;
	}

	/**
	 * Setzen des Erstelldatums des Reports
	 * 
	 * @param created
	 *            Zeitpunkt des Erstellens
	 */

	public void setCreated(String created) {
		this.created = created;
	}

}
