package de.hdm.itprojektgruppe4.client;

import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektgruppe4.client.gui.KontaktlisteKontaktTreeViewModel;

public class NavigationTree extends VerticalPanel {

	public void onLoad() {

		KontaktlisteKontaktTreeViewModel kontaktNavigationTree = new KontaktlisteKontaktTreeViewModel();

		CellTree navigationTree = new CellTree(kontaktNavigationTree, null);

		HTML html = new HTML("<h2>Meine Kontaktlisten</h2>");

		this.add(html);
		this.add(navigationTree);
	}

}
