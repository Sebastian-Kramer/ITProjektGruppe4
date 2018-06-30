package de.hdm.itprojektgruppe4.client.gui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Cookies;

import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;

/**
 * Die Klasse dient zur Aufbereitung von Kontaktliste-Objekten zur Ansicht in der GUI.
 * Die Objekte werden dabei nutzerabhängig gerendert.
 */
public class KontaktlisteCell extends AbstractCell<Kontaktliste> {

	private Nutzer nutzer = new Nutzer();
	
	/*
	 * Ist der angemeldete Nutzer Teilhaber einer Kontaktliste, wird diese kursiv dargestellt.
	 */
	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, Kontaktliste value, SafeHtmlBuilder sb) {
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
	
		if(value == null) {
			return;
		}else if(value.getNutzerID() != nutzer.getID()){
		sb.appendHtmlConstant("<div><i>");
		sb.appendEscaped(value.getBez());
		sb.appendHtmlConstant("</i></div>");
		}else if(value.getNutzerID() == nutzer.getID()){
			sb.appendHtmlConstant("<div>");
			sb.appendEscaped(value.getBez());
			sb.appendHtmlConstant("</div>");
		}
	
	}

}
