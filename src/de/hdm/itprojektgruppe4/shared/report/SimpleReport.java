package de.hdm.itprojektgruppe4.shared.report;

import java.util.Vector;

/**
 * Einfacher Report. Weist Informationen der Superklasse Report auf und eine
 * Tabelle auf, die auf die beiden Hilfsklassen Row und Column zugreift Code in
 * Anlehnung an Prof. Thies
 *
 */

public abstract class SimpleReport extends Report {

	private static final long serialVersionUID = 1L;

	/**
	 * die Tabelle wird zeilenhweise in diesem Vektor abgelegt
	 */
	private Vector<Row> table = new Vector<Row>();

	/**
	 * fügt eine Zeile hinzu
	 * 
	 * @param r
	 *            die hinzuzufügende Zeile
	 */

	public void addRow(Row r) {
		this.table.addElement(r);
	}

	/**
	 * entfernt eine Zeile
	 * 
	 * @param r
	 *            die zu entfernende Zeile
	 */

	public void removeRow(Row r) {
		this.table.removeElement(r);
	}

	/**
	 * Auslesen der Positionsdatenaus der Tabelle
	 * 
	 * @return Tabelle der Positionsdaten
	 */
	public Vector<Row> getRows() {
		return this.table;
	}

}
