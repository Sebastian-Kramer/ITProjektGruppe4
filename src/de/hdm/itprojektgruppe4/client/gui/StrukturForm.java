package de.hdm.itprojektgruppe4.client.gui;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class StrukturForm extends VerticalPanel{
	
	public void onLoad(){
		
		super.onLoad();
		
		RootPanel.get("Details").clear();
		
		this.run();
	}
	
	protected abstract void run();

}
