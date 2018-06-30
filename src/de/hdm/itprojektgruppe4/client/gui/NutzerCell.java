package de.hdm.itprojektgruppe4.client.gui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.itprojektgruppe4.shared.bo.Nutzer;

/**
 * Die Klasse dient zur Aufbereitung von Nutzer-Objekten in der GUI
 * 
 * @author Raphael
 *
 */
public class NutzerCell extends AbstractCell<Nutzer> {

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, Nutzer value, SafeHtmlBuilder sb) {
		if (value == null) {
			return;
		}

		sb.appendHtmlConstant("<div>");
		sb.appendEscaped(value.getEmail());
		sb.appendHtmlConstant("</div>");
	}

}
