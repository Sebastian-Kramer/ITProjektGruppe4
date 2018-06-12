package de.hdm.itprojektgruppe4.server.report;

import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektgruppe4.server.KontaktAdministrationImpl;
import de.hdm.itprojektgruppe4.server.db.KontaktMapper;
import de.hdm.itprojektgruppe4.server.db.NutzerMapper;
import de.hdm.itprojektgruppe4.server.db.TeilhaberschaftMapper;
import de.hdm.itprojektgruppe4.shared.KontaktAdministration;
import de.hdm.itprojektgruppe4.shared.ReportGenerator;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.bo.Teilhaberschaft;
import de.hdm.itprojektgruppe4.shared.report.AllEigeneKontakteReport;
import de.hdm.itprojektgruppe4.shared.report.AllNutzerReport;
import de.hdm.itprojektgruppe4.shared.report.Column;
import de.hdm.itprojektgruppe4.shared.report.CompositeParagraph;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaftsAuspraegungenReport;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmterTeilhaberschaftReport;
import de.hdm.itprojektgruppe4.shared.report.Report;
import de.hdm.itprojektgruppe4.shared.report.Row;
import de.hdm.itprojektgruppe4.shared.report.SimpleParagraph;

public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator {

	private static final long serialVersionUID = 1L;

	private KontaktAdministration verwaltung = null;
	private NutzerMapper nutzerMapper = null;
	private TeilhaberschaftMapper teilhaberschaftMapper = null;
	private KontaktMapper kontaktMapper = null;

	public ReportGeneratorImpl() throws IllegalArgumentException {

	}

	public void init() throws IllegalArgumentException {

		KontaktAdministrationImpl k = new KontaktAdministrationImpl();
		k.init();
		this.verwaltung = k;

	}

	protected KontaktAdministration getAdministration() {
		return this.verwaltung;
	}

	protected void addImprint(Report r) {

		// Das Impressum kann mehrere Seiten haben
		CompositeParagraph imprint = new CompositeParagraph();

		// 1. Zeile
		imprint.addSubParagraph(new SimpleParagraph("Kontaktverwaltung"));

		// Das Hinzufügen des Impressum zum Report
		r.setImprint(imprint);

	}

	/*
	 * 
	 * Erster Report
	 * 
	 */

	public AllEigeneKontakteReport AllEigeneKontakteReport() throws IllegalArgumentException {
		if (this.getAdministration() == null)
			return null;

		// Leerer Report
		AllEigeneKontakteReport result = new AllEigeneKontakteReport();

		//
		Vector<Kontakt> kontakt = this.verwaltung.findAllKontakte();

		result.setTitle("Meine Kontakte");

		Row headline = new Row();

		headline.addColumn(new Column("Kontakt"));

		headline.addColumn(new Column("Erzeugungsdatum"));

		headline.addColumn(new Column("Modifikationsdatum"));

		result.addRow(headline);

		for (Kontakt k : kontakt) {
			// eine Leere zeile
			Row kontaktRow = new Row();

			kontaktRow.addColumn(new Column(String.valueOf(k.getName())));

			kontaktRow.addColumn(new Column(String.valueOf(k.getErzeugungsdatum())));

			kontaktRow.addColumn(new Column(String.valueOf(k.getModifikationsdatum())));

			result.addRow(kontaktRow);
		}

		return result;

	}
	
	
	
	public AllEigeneKontakteReport AlleKontakteByNutzer(int nutzerID) throws IllegalArgumentException {
		if (this.getAdministration() == null)
			return null;

		// Leerer Report
		AllEigeneKontakteReport result = new AllEigeneKontakteReport();

		//
		Vector<Kontakt> kontakt = this.verwaltung.findAllKontaktFromNutzer(nutzerID);

		result.setTitle("Meine Kontakte");

		Row headline = new Row();

		headline.addColumn(new Column("Kontakt"));

		headline.addColumn(new Column("Erzeugungsdatum"));

		headline.addColumn(new Column("Modifikationsdatum"));

		result.addRow(headline);

		for (Kontakt k : kontakt) {
			// eine Leere zeile
			Row kontaktRow = new Row();

			kontaktRow.addColumn(new Column(String.valueOf(k.getName())));

			kontaktRow.addColumn(new Column(String.valueOf(k.getErzeugungsdatum())));

			kontaktRow.addColumn(new Column(String.valueOf(k.getModifikationsdatum())));

			result.addRow(kontaktRow);
		}

		return result;

	}
	
	
	
	

	public Vector<Nutzer> allNutzerReport() throws IllegalArgumentException {
		if (this.getAdministration() == null)
			return null;

		// Leerer Report
		AllNutzerReport result = new AllNutzerReport();

	

		Vector<Nutzer> nutzer = this.verwaltung.findAllNutzer();



		

		return nutzer;

	}

	@Override
	public Nutzer findNutzerByEmail(String email) throws IllegalArgumentException {
		// TODO Auto-generated method stub
	
			return this.verwaltung.findNutzerByEmail(email);
		
	//	return this.nutzerMapper.findNutzerByEmail(email);
	}

	@Override
	public KontakteMitBestimmterTeilhaberschaftReport kontakteMitBestimmterTeilhaberschaftReport(int nutzerID)
			throws IllegalArgumentException {
		if (this.getAdministration() == null)
			// TODO Auto-generated method stub
			return null;

		// Leerer Report
		KontakteMitBestimmterTeilhaberschaftReport result = new KontakteMitBestimmterTeilhaberschaftReport();

		Vector<Kontakt> kontakt = this.verwaltung.findAllSharedKontakteVonNutzer(nutzerID);

		result.setTitle("Geteilte Kontakte");

		Row headline = new Row();

		headline.addColumn(new Column("Kontaktname"));

		headline.addColumn(new Column("Erzeugungsdatum"));

		headline.addColumn(new Column("Modifikationsdatum"));

		headline.addColumn(new Column("Nutzer"));

		result.addRow(headline);

		for (Kontakt k : kontakt) {

			// eine Leere zeile
			Row kontaktRow = new Row();

			kontaktRow.addColumn(new Column(String.valueOf(k.getName())));

			kontaktRow.addColumn(new Column(String.valueOf(k.getErzeugungsdatum())));

			kontaktRow.addColumn(new Column(String.valueOf(k.getModifikationsdatum())));

			kontaktRow.addColumn(new Column(String.valueOf(k.getNutzerID())));

			result.addRow(kontaktRow);
		}

		return result;

	}
	
	
	
	
	

}
