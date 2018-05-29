package de.hdm.itprojektgruppe4.client;


import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.itprojektgruppe4.client.gui.KontaktlisteKontaktTreeViewModel;


public class NavigationTree extends VerticalPanel{
	
	public void onLoad(){
		
		KontaktlisteKontaktTreeViewModel kontaktNavigationTree = new KontaktlisteKontaktTreeViewModel();
		
		CellTree navigationTree = new CellTree(kontaktNavigationTree, null);
		
		this.add(navigationTree);
	}
	
	

}
