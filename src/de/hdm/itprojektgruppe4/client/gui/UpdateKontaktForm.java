package de.hdm.itprojektgruppe4.client.gui;
import java.util.Vector;

import org.datanucleus.state.CallbackHandler;

import com.google.appengine.api.files.FileServicePb.ShuffleRequest.Callback;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektgruppe4.client.ClientsideSettings;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;


public class UpdateKontaktForm extends VerticalPanel{

	private static KontaktAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();
	
FlexTable ft_KontaktBearbeiten = new FlexTable();
	
	Button btn_speichern = new Button("Speichern");
	Button btn_abbrechen = new Button("Abbrechen");
	
	Label lbl_KontaktName = new Label("Kontaktname: ");
	TextBox txt_KontaktName = new TextBox();
	Label lbl_Eigenschaft = new Label("Eigenschaft: ");
	TextArea txt_Eigenschaft = new TextArea();
	
}
