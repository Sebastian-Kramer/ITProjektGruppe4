package de.hdm.itprojektgruppe4.client.gui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;

public class KontaktlisteCell extends AbstractCell<Kontaktliste> {

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, Kontaktliste value, SafeHtmlBuilder sb) {
		if(value == null) {
			return;
		}
		
		sb.appendHtmlConstant("<div>");
		sb.appendEscaped(value.getBez());
		sb.appendHtmlConstant("</div>");
	}

}
