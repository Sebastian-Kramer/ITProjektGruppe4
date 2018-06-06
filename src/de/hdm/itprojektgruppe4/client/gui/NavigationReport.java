package de.hdm.itprojektgruppe4.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NavigationReport extends VerticalPanel{
	
	private VerticalPanel vPanel = new VerticalPanel();
	
	private Button alleeigenenKontakte = new Button("Alle Eigenen Kontakte");
	
	
	public void onLoad(){
		super.onLoad();
		
		RootPanel.get("Navigator").clear();
		vPanel.add(alleeigenenKontakte);
		RootPanel.get("Navigator").add(vPanel);
		
	}

	
	

}
