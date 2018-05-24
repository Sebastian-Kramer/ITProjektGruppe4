package de.hdm.itprojektgruppe4.client.gui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.cell.client.Cell.Context;

import de.hdm.itprojektgruppe4.shared.bo.Kontakt;

public class KontaktCell extends AbstractCell<Kontakt>{

	@Override
	public void render(Context context, Kontakt value, SafeHtmlBuilder sb) {
		// TODO Auto-generated method stub
		if (value == null){
			return;
		} 
		
		  sb.appendHtmlConstant("<div>");
	      sb.appendEscaped(value.getName());
	      sb.appendHtmlConstant("</div>");
		
	}

}
