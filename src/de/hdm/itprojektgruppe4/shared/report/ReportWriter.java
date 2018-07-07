package de.hdm.itprojektgruppe4.shared.report;

/**
 * <p>
 * Diese Klasse wird benötigt, um auf dem Client die ihm vom Server zur
 * Verfügung gestellten <code>Report</code>-Objekte in ein menschenlesbares
 * Format zu überführen.
 * </p>
 * <p>
 * Das Zielformat kann prinzipiell beliebig sein. Methoden zum Auslesen der in
 * das Zielformat überführten Information wird den Subklassen überlassen. In
 * dieser Klasse werden die Signaturen der Methoden deklariert, die für die
 * Prozessierung der Quellinformation zuständig sind.
 * </p>
 * Code in Anlehnung an Bankprojekt von Prof.Thies
 *
 */

public abstract class ReportWriter {

	/**
	 * Übersetzt einen
	 * <code>KontakteMitBestimmtenEIgenschaftsAuspraegungenReport</code> in das
	 * Zielformat.
	 * 
	 * @param c
	 */

	public abstract void process(KontakteMitBestimmtenEigenschaftsAuspraegungen c);

	/**
	 * Übersetzt einen <code>AllEigeneKontakteReport</code> in das Zielformat
	 * 
	 * @param c
	 */

	public abstract void process(AllEigeneKontakteReport c);

	/**
	 * Übersetzt einen <code>KontaktMitBestimmterTeilhaberschaftReport</code> in
	 * das Zielformat
	 * 
	 * @param c
	 */

	public abstract void process(KontakteMitBestimmterTeilhaberschaftReport c);

}
