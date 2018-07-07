package de.hdm.itprojektgruppe4.shared.report;

import java.io.Serializable;

/**
 * code in Anlehnung an Bankprojekt von Prof Thies
 */

public class Column implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Der Wert eines Spalten-Objektes entspricht einemZelleneintragder Tabelle.
	 * 
	 */

	private String value = "";

	public Column() {
	}

	/**
	 * Konstruktor, erzwingt die Angabe eines Spalteneintrages (Wertes)
	 * 
	 * @param s
	 *            ist der Wert,welcher durch Objekt vom Typ Column dargestellt
	 */

	public Column(String s) {
		this.value = s;
	}

	/**
	 * Methode zur Auslese des Spaltenwertes
	 * 
	 * @return Spaltenwert als String
	 */

	public String getValue() {
		return value;
	}

	/**
	 * Der Wert Spalte wird �berschrieben
	 * 
	 * 
	 * @param value
	 *            der neue Spaltenwert
	 */

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Das Column Objekt wird in ein String umgewandelt.
	 */

	public String toString() {
		return this.value;
	}

}
