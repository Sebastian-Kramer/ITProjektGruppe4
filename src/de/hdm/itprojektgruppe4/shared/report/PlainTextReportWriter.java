package de.hdm.itprojektgruppe4.shared.report;

/**
 * Ein <code>ReportWriter</code>, der Reports mittels Plain Text formatiert. Das
 * im Zielformat vorliegende Ergebnis wird in der Variable
 * <code>reportText</code> abgelegt und kann nach Aufruf der entsprechenden
 * Prozessierungsmethode mit <code>getReportText()</code> ausgelesen werden.
 * 
 * code in Anlehnung an Bankprojekt von Prof. Thies
 */

public abstract class PlainTextReportWriter extends ReportWriter {

	/**
	 * Diese Variable wird mit dem Ergebnis einer Umwandlung (vgl.
	 * <code>process...</code>-Methoden) belegt. Format: Text
	 */

	private String reportText = "";

	/**
	 * Setzt die Variable reportText zurück
	 */

	public void resetReportText() {
		this.reportText = "";
	}

	/**
	 * Produziert Header
	 * 
	 * @return Text
	 */

	public String getHeader() {
		// hier noch Ausarbeiten
		return "";
	}

	/**
	 * Produziert Trailer
	 * 
	 * @return Text
	 */
	public String getTrailer() {

		return "___________________________________________";
	}

	@Override
	public void process(KontakteMitBestimmtenEigenschaftsAuspraegungen c) {
		this.resetReportText();

		StringBuffer result = new StringBuffer();

	}

	/**
	 * Prozessiert den übergebenen Report und leht ihn im Zielformat ab.
	 * 
	 * @param r
	 *            der zu prozessierende Report
	 */

	@Override
	public void process(AllEigeneKontakteReport c) {
		this.resetReportText();

		StringBuffer result = new StringBuffer();

	}

	/**
	 * Prozessiert den übergebenen Report und leht ihn im Zielformat ab.
	 * 
	 * @param r
	 *            der zu prozessierende Report
	 */
	@Override
	public void process(KontakteMitBestimmterTeilhaberschaftReport c) {
		this.resetReportText();

		StringBuffer result = new StringBuffer();

	}

}
