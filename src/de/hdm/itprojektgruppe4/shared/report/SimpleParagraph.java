package de.hdm.itprojektgruppe4.shared.report;

import java.io.Serializable;

/**
 * Diese Klasse stellt einzelne Absätze dar. Der Absatzinhalt wird als String
 * gespeichert. Der Anwender sollte in diesem Strinig keinerlei
 * Formatierungssymbole einfügen, da diese in der Regel zielformatspezifisch
 * sind.
 * 
 * Code in Anlehnung an bankProjekt von Prof. Thies
 */

public class SimpleParagraph extends Paragraph implements Serializable {

	private static final long serialVersionUID = 1L;

	private String text = "";

	public SimpleParagraph() {
	}

	/**
	 * 
	 * @param value
	 *            Inhalt des Absatzes
	 */

	public SimpleParagraph(String value) {
		this.text = value;
	}

	/**
	 * 
	 * @return inhalt als String
	 */

	public String getText() {
		return this.text;
	}

	/**
	 * Überschreibt den Inhalt
	 * 
	 * @param text
	 *            der neue Inhalt
	 */

	public void setText(String text) {
		this.text = text;
	}

	/**
	 * wandelt das Objekt SimpleParagraph in einen String um
	 */

	public String toString() {
		return this.text;
	}

}
