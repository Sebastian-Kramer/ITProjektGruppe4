package de.hdm.itprojektgruppe4.server.report;

import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektgruppe4.server.KontaktAdministrationImpl;
import de.hdm.itprojektgruppe4.server.db.KontaktMapper;
import de.hdm.itprojektgruppe4.server.db.NutzerMapper;
import de.hdm.itprojektgruppe4.server.db.TeilhaberschaftMapper;
import de.hdm.itprojektgruppe4.shared.KontaktAdministration;
import de.hdm.itprojektgruppe4.shared.ReportGenerator;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.bo.Teilhaberschaft;
import de.hdm.itprojektgruppe4.shared.report.AllEigeneKontakteReport;
import de.hdm.itprojektgruppe4.shared.report.AllNutzerReport;
import de.hdm.itprojektgruppe4.shared.report.Column;
import de.hdm.itprojektgruppe4.shared.report.CompositeParagraph;
import de.hdm.itprojektgruppe4.shared.report.FindAllEigenschaftReport;
import de.hdm.itprojektgruppe4.shared.report.FindAllEigenschaftsAuspraegungn;
import de.hdm.itprojektgruppe4.shared.report.FindEigenschaftByBezeichnung;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenAuspraegungen;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaften;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaftsAuspraegungen;
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

	public Kontakt findKontaktByID(int id) throws IllegalArgumentException {
		return this.kontaktMapper.findKontaktByID(id);

	}

	/*
	 * 
	 * Erster Report
	 * 
	 */

	public AllEigeneKontakteReport AllEigeneKontakteReport(int nutzerID) throws IllegalArgumentException {
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
		headline.addColumn(new Column("Eigenschaft"));
		headline.addColumn(new Column("Auspraegung"));

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

		// return this.nutzerMapper.findNutzerByEmail(email);
	}

	public KontakteMitBestimmterTeilhaberschaftReport kontakteMitBestimmterTeilhaberschaftReport(int nutzerID,
			int teilhaberID) throws IllegalArgumentException {
		if (this.getAdministration() == null)
			// TODO Auto-generated method stub
			return null;

		// Leerer Report
		KontakteMitBestimmterTeilhaberschaftReport result = new KontakteMitBestimmterTeilhaberschaftReport();

		Vector<Kontakt> teilhaben = this.verwaltung.findGeteilteKontakteFromNutzerAndTeilhaber(nutzerID, teilhaberID);

		result.setTitle("Geteilte Kontakte");

		Row headline = new Row();

		headline.addColumn(new Column("Kontakt"));
		headline.addColumn(new Column("Erzeugungsdatum"));
		headline.addColumn(new Column("Modifikationsdatum"));
		headline.addColumn(new Column("Eigenschaft"));
		headline.addColumn(new Column("Auspraegung"));

		result.addRow(headline);

		for (Kontakt t : teilhaben) {

			// eine Leere zeile
			Row kontaktRow = new Row();

			kontaktRow.addColumn(new Column(String.valueOf(t.getName())));

			kontaktRow.addColumn(new Column(String.valueOf(t.getErzeugungsdatum())));

			kontaktRow.addColumn(new Column(String.valueOf(t.getModifikationsdatum())));
			
			
			

			result.addRow(kontaktRow);
		}

		System.out.println(teilhaben);
		return result;

	}

	public KontakteMitBestimmtenEigenschaftsAuspraegungen kontakteMitBestimmtenEigenschaftsAuspraegungen(int NutzerID,
			String bez, String wert) throws IllegalArgumentException {
		if (this.getAdministration() == null)
			// TODO Auto-generated method stub
			return null;

		// Leerer Report
		KontakteMitBestimmtenEigenschaftsAuspraegungen result = new KontakteMitBestimmtenEigenschaftsAuspraegungen();

		Vector<Kontakt> kontakt = this.verwaltung.findKontakteByEigAus(NutzerID, bez, wert);

		result.setTitle("Kontakte mit bestimmten Eigenschaftsauspraegungen");

		Row headline = new Row();

		headline.addColumn(new Column("Kontakt"));
		headline.addColumn(new Column("Erzeugungsdatum"));
		headline.addColumn(new Column("Modifikationsdatum"));
		headline.addColumn(new Column("Eigenschaft"));
		headline.addColumn(new Column("Auspraegung"));
		result.addRow(headline);

		for (Kontakt t : kontakt) {

			// eine Leere zeile
			Row kontaktRow = new Row();

			kontaktRow.addColumn(new Column(String.valueOf(t.getName())));

			result.addRow(kontaktRow);
		}

		return result;

	}

	@Override
	public Nutzer insertNutzer(String mail) throws IllegalArgumentException {
		Nutzer nutzer = new Nutzer();
		nutzer.setEmail(mail);
		return this.nutzerMapper.insertNutzer(nutzer);

	}

	public Vector<Eigenschaft> findAllEigenschaft() throws IllegalArgumentException {
		if (this.getAdministration() == null)
			return null;

		// Leerer Report
		FindAllEigenschaftReport result = new FindAllEigenschaftReport();

		Vector<Eigenschaft> eigenschaft = this.verwaltung.findAllEigenschaft();

		return eigenschaft;

	}

	public Vector<Eigenschaft> findEigenschaftByBezeichnung(String bez) throws IllegalArgumentException {
		if (this.getAdministration() == null)
			return null;

		FindEigenschaftByBezeichnung findEigenschaftByBezeichnung = new FindEigenschaftByBezeichnung();

		Vector<Eigenschaft> eigenschaft = this.verwaltung.getEigenschaftByBezeichnung(bez);

		return eigenschaft;

	}

	public Vector<Eigenschaftauspraegung> findAllEigenschaftsAuspraegungn() throws IllegalArgumentException {
		if (this.getAdministration() == null)
			return null;

		FindAllEigenschaftsAuspraegungn findAllEigenschaftsAuspraegungn = new FindAllEigenschaftsAuspraegungn();

		Vector<Eigenschaftauspraegung> eigenschaftauspraegung = this.verwaltung.findAllEigenschaftauspraegung();

		return eigenschaftauspraegung;

	}

	@Override
	public de.hdm.itprojektgruppe4.shared.report.AllEigeneKontakteReport AlleKontakteByNutzer(int nutzerID,
			int teilhaberID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	public KontakteMitBestimmtenEigenschaften kontakteMitBestimmtenEigenschaften(int NutzerID, String bez)
			throws IllegalArgumentException {
		if (this.getAdministration() == null)

			return null;

		// Leerer Report
		KontakteMitBestimmtenEigenschaften result = new KontakteMitBestimmtenEigenschaften();

		Vector<Kontakt> kontakt = this.verwaltung.findKontakeByEig(NutzerID, bez);

		Vector<Eigenschaft> eig = this.verwaltung.findAllEigenschaft();

		result.setTitle("Kontakte mit bestimmten Eigenschaftsauspraegungen");

		Row headline = new Row();

		headline.addColumn(new Column("Kontakt"));
		headline.addColumn(new Column("Erzeugungsdatum"));
		headline.addColumn(new Column("Modifikationsdatum"));
		headline.addColumn(new Column("Eigenschaft"));
		headline.addColumn(new Column("Auspraegung"));

		result.addRow(headline);

		for (Kontakt t : kontakt) {

				Vector<Eigenschaft> vecEig = this.verwaltung.getEigenschaftbyKontaktID(t.getID());
				
				for (Eigenschaft e : vecEig) {

					if (e.getBezeichnung().equals(bez)) {
						
						Eigenschaftauspraegung ea = this.verwaltung.getAuspraegungByEigID(e.getID());
					
						
					// eine Leere zeile
					Row kontaktRow = new Row();

					kontaktRow.addColumn(new Column(String.valueOf(t.getName())));
					kontaktRow.addColumn(new Column(String.valueOf(t.getErzeugungsdatum())));
					kontaktRow.addColumn(new Column(String.valueOf(t.getModifikationsdatum())));
					kontaktRow.addColumn(new Column(String.valueOf(e.getBezeichnung())));
					kontaktRow.addColumn(new Column(String.valueOf(ea.getWert())));

					result.addRow(kontaktRow);
					}  
				}

	

		}

		return result;
	}

	
	
	public KontakteMitBestimmtenAuspraegungen kontakteMitBestimmtenAuspraegungen(int NutzerID, String wert)
			throws IllegalArgumentException {
		if (this.getAdministration() == null)
			// TODO Auto-generated method stub
			return null;

		// Leerer Report
		KontakteMitBestimmtenAuspraegungen result = new KontakteMitBestimmtenAuspraegungen();

		Vector<Kontakt> kontakt = this.verwaltung.findKontakteByAus(NutzerID, wert);

		result.setTitle("Kontakte mit bestimmten Eigenschaftsauspraegungen");

		Row headline = new Row();

		headline.addColumn(new Column("Kontakt"));
		headline.addColumn(new Column("Erzeugungsdatum"));
		headline.addColumn(new Column("Modifikationsdatum"));
		headline.addColumn(new Column("Eigenschaft"));
		headline.addColumn(new Column("Auspraegung"));
		result.addRow(headline);

		for (Kontakt t : kontakt) {

			// eine Leere zeile
			Row kontaktRow = new Row();

			kontaktRow.addColumn(new Column(String.valueOf(t.getName())));
			kontaktRow.addColumn(new Column(String.valueOf(t.getErzeugungsdatum())));
			kontaktRow.addColumn(new Column(String.valueOf(t.getModifikationsdatum())));

			result.addRow(kontaktRow);
		}

		return result;

	}

}
