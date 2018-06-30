package de.hdm.itprojektgruppe4.client.gui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Cookies;

import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;

/**
 * Die Klasse dient zur Aufbereitung von Kontakt-Objekten zur Ansicht in der GUI
 * Die Objekte werden dabei nutzerabhängig gerendert
 */
public class KontaktCell extends AbstractCell<Kontakt>{
	private Nutzer nutzer = new Nutzer();
	
	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, Kontakt value, SafeHtmlBuilder sb) {
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		
		/*
		 * Ist der angemeldete Nutzer nicht der Eigentümer eines Kontaktes, wird dieser kursiv dargestellt
		 */
		if(value == null) {
			return;
		}else if(value.getNutzerID() != nutzer.getID()){
		sb.appendHtmlConstant("<div><i>");
		sb.appendEscaped(value.getName());
		sb.appendHtmlConstant("</i></div>");
		}else if(value.getNutzerID() == nutzer.getID()){
			sb.appendHtmlConstant("<div>");
			sb.appendEscaped(value.getName());
			sb.appendHtmlConstant("</div>");
		}

}
	
}
