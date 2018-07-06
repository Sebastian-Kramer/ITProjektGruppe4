package de.hdm.itprojektgruppe4.client;

import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektgruppe4.client.gui.KontaktlisteKontaktTreeViewModel;

/**
 * Die Klasse NavigationTree dient zur Darstellung der Navigation.
 * Es wird ein Objkt vom Typ <code>KontaktlisteKontaktTreeViewModel </code> instanziiert 
 * und einem Vertical Pnale hinzugefügt.
 * 
 * Hinzu wird die Überschrift "Meine Kontaktlisten" hinzugefügt.
 * @author Nino
 *
 */

public class NavigationTree extends VerticalPanel {

	public void onLoad() {

		KontaktlisteKontaktTreeViewModel kontaktNavigationTree = new KontaktlisteKontaktTreeViewModel();

		CellTree navigationTree = new CellTree(kontaktNavigationTree, null);

		HTML html = new HTML("<h1>Meine Kontaktlisten</h1>");

		this.add(html);
		this.add(navigationTree);
	}

}
