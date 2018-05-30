package de.hdm.itprojektgruppe4.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektgruppe4.shared.bo.*;

public class KontaktlisteForm extends VerticalPanel {

	Kontaktliste kl = null;
	
	void setSelected(Kontaktliste kl){
		this.kl = kl;
	}
	
	public KontaktlisteForm(Kontaktliste kontaktliste){
		this.kl = kontaktliste;
	}
}
