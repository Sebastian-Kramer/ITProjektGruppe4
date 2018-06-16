package de.hdm.itprojektgruppe4.client.gui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Cookies;

import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;

public class KontaktlisteCell extends AbstractCell<Kontaktliste> {

	private Nutzer nutzer = new Nutzer();
	
	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, Kontaktliste value, SafeHtmlBuilder sb) {
		nutzer.setID(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setEmail(Cookies.getCookie("email"));
	
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
		/*
		if(value.getStatus() == 1){
			sb.appendHtmlConstant("<img src=\"images/contacts.png\">");
		}
		*/
	}

}
