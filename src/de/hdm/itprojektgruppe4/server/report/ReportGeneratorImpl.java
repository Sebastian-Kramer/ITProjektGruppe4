package de.hdm.itprojektgruppe4.server.report;

import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektgruppe4.client.EigenschaftAuspraegungWrapper;
import de.hdm.itprojektgruppe4.server.KontaktAdministrationImpl;
import de.hdm.itprojektgruppe4.server.db.KontaktMapper;
import de.hdm.itprojektgruppe4.server.db.NutzerMapper;
import de.hdm.itprojektgruppe4.shared.KontaktAdministration;
import de.hdm.itprojektgruppe4.shared.ReportGenerator;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.report.AllEigeneKontakteReport;
import de.hdm.itprojektgruppe4.shared.report.Column;
import de.hdm.itprojektgruppe4.shared.report.CompositeParagraph;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenAuspraegungen;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaften;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaftsAuspraegungen;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmterTeilhaberschaftReport;
import de.hdm.itprojektgruppe4.shared.report.Report;
import de.hdm.itprojektgruppe4.shared.report.Row;
import de.hdm.itprojektgruppe4.shared.report.SimpleParagraph;

public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator {

	private static final long serialVersionUID = 1L;

	private KontaktAdministration verwaltung = null;
	private NutzerMapper nutzerMapper = null;
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

		// Das Hinzuf�gen des Impressum zum Report
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

		AllEigeneKontakteReport result = new AllEigeneKontakteReport();

		Vector<Kontakt> kontakt = this.verwaltung.findAllKontaktFromNutzer(nutzerID);

		Vector<EigenschaftAuspraegungWrapper> auspraegung = new Vector<EigenschaftAuspraegungWrapper>();

		result.setTitle("Meine Kontakte und mir geteilte Kontakte");

		Row headline = new Row();

		headline.addColumn(new Column("Kontakt"));
		headline.addColumn(new Column("Status"));
		headline.addColumn(new Column("Erstellt von"));
		headline.addColumn(new Column("Eigenschaft: Auspraegung"));

		result.addRow(headline);

		for (Kontakt k : kontakt) {

			Row kontaktRow = new Row();

			auspraegung = this.verwaltung.findHybrid(k);

			kontaktRow.addColumn(new Column(String.valueOf(k.getName())));

			if (k.getStatus() == 0) {
				kontaktRow.addColumn(new Column(String.valueOf("Nicht Geteilt")));
			} else {
				kontaktRow.addColumn(new Column(String.valueOf("geteilt")));
			}

			kontaktRow.addColumn(new Column(String.valueOf(this.verwaltung.findNutzerByID(k.getNutzerID()).getEmail())));

			kontaktRow.addColumn(new Column(auspraegung.toString().replace("[", "").replace("]", "").replace(",", "")));

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

		Vector<Nutzer> nutzer = this.verwaltung.findAllNutzer();

		return nutzer;

	}

	@Override
	public Nutzer findNutzerByEmail(String email) throws IllegalArgumentException {

		return this.verwaltung.findNutzerByEmail(email);


	}

	public KontakteMitBestimmterTeilhaberschaftReport kontakteMitBestimmterTeilhaberschaftReport(int nutzerID,
			int teilhaberID) throws IllegalArgumentException {
		if (this.getAdministration() == null)

			return null;

		// Leerer Report
		KontakteMitBestimmterTeilhaberschaftReport result = new KontakteMitBestimmterTeilhaberschaftReport();

		Vector<Kontakt> teilhaben = this.verwaltung.findGeteilteKontakteFromNutzerAndTeilhaber(nutzerID, teilhaberID);
		
		Vector<EigenschaftAuspraegungWrapper> auspraegung = new Vector<EigenschaftAuspraegungWrapper>();

		result.setTitle("Geteilte Kontakte");

		Row headline = new Row();

		headline.addColumn(new Column("Kontakt"));
		headline.addColumn(new Column("Status"));
		headline.addColumn(new Column("Erstellt von"));
		headline.addColumn(new Column("Eigenschaft: Auspraegung"));

		result.addRow(headline);

		for (Kontakt k : teilhaben) {

			Row kontaktRow = new Row();

			auspraegung = this.verwaltung.findSharedAuspraegung(k.getID(), teilhaberID);

			kontaktRow.addColumn(new Column(String.valueOf(k.getName())));

			if (k.getStatus() == 0) {
				kontaktRow.addColumn(new Column(String.valueOf("Nicht Geteilt")));
			} else {
				kontaktRow.addColumn(new Column(String.valueOf("geteilt")));
			}

			kontaktRow.addColumn(new Column(String.valueOf(this.verwaltung.findNutzerByID(k.getNutzerID()).getEmail())));

			kontaktRow.addColumn(new Column(auspraegung.toString().replace("[", "").replace("]", "").replace(",", "")));

			result.addRow(kontaktRow);
		}

		return result;

	}

	public KontakteMitBestimmtenEigenschaftsAuspraegungen kontakteMitBestimmtenEigenschaftsAuspraegungen(int NutzerID,
			String bez, String wert) throws IllegalArgumentException {
		if (this.getAdministration() == null)

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

			Vector<Eigenschaft> vecEig = this.verwaltung.getEigenschaftbyKontaktID(t.getID());

			for (Eigenschaft e : vecEig) {
				
				Eigenschaftauspraegung ea = this.verwaltung.getAuspraegungByEigID(e.getID(), t.getID());

				if (e.getBezeichnung().equals(bez) && ea.getWert().equals(wert)) {

					// eine Leere zeile
					Row kontaktRow = new Row();

					kontaktRow.addColumn(new Column(String.valueOf(t.getName())));
					if (t.getStatus() == 0) {
						kontaktRow.addColumn(new Column(String.valueOf("Nicht Geteilt")));
					} else {
						kontaktRow.addColumn(new Column(String.valueOf("geteilt")));
					}
					kontaktRow.addColumn(new Column(String.valueOf(this.verwaltung.findNutzerByID(t.getNutzerID()).getEmail())));
					
					kontaktRow.addColumn(new Column(String.valueOf(e.getBezeichnung())));
					
					kontaktRow.addColumn(new Column(String.valueOf(ea.getWert())));

					result.addRow(kontaktRow);
				}
			}

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

		Vector<Eigenschaft> eigenschaft = this.verwaltung.findAllEigenschaft();

		return eigenschaft;

	}

	public Vector<Eigenschaft> findEigenschaftByBezeichnung(String bez) throws IllegalArgumentException {
		if (this.getAdministration() == null)
			return null;

		Vector<Eigenschaft> eigenschaft = this.verwaltung.getEigenschaftByBezeichnung(bez);

		return eigenschaft;

	}

	public Vector<Eigenschaftauspraegung> findAllEigenschaftsAuspraegungn() throws IllegalArgumentException {
		if (this.getAdministration() == null)
			return null;

		Vector<Eigenschaftauspraegung> eigenschaftauspraegung = this.verwaltung.findAllEigenschaftauspraegung();

		return eigenschaftauspraegung;

	}

	@Override
	public AllEigeneKontakteReport AlleKontakteByNutzer(int nutzerID, int teilhaberID) throws IllegalArgumentException {

		return null;
	}

	public KontakteMitBestimmtenEigenschaften kontakteMitBestimmtenEigenschaften(int NutzerID, String bez)
			throws IllegalArgumentException {
		if (this.getAdministration() == null)

			return null;

		// Leerer Report
		KontakteMitBestimmtenEigenschaften result = new KontakteMitBestimmtenEigenschaften();

		Vector<Kontakt> kontakt = this.verwaltung.findKontakeByEig(NutzerID, bez);

		result.setTitle("Kontakte mit bestimmten Eigenschaftsauspraegungen");

		Row headline = new Row();

		headline.addColumn(new Column("Kontakt"));
		headline.addColumn(new Column("Status"));
		headline.addColumn(new Column("Erstellt von"));
		headline.addColumn(new Column("Eigenschaft"));
		headline.addColumn(new Column("Auspraegung"));

		result.addRow(headline);

		for (Kontakt t : kontakt) {

			Vector<Eigenschaft> vecEig = this.verwaltung.getEigenschaftbyKontaktID(t.getID());

			for (Eigenschaft e : vecEig) {

				if (e.getBezeichnung().equals(bez)) {

					Eigenschaftauspraegung ea = this.verwaltung.getAuspraegungByEigID(e.getID(), t.getID());

					// eine Leere zeile
					Row kontaktRow = new Row();

					kontaktRow.addColumn(new Column(String.valueOf(t.getName())));
					if (t.getStatus() == 0) {
						kontaktRow.addColumn(new Column(String.valueOf("Nicht Geteilt")));
					} else {
						kontaktRow.addColumn(new Column(String.valueOf("geteilt")));
					}
					kontaktRow.addColumn(new Column(String.valueOf(this.verwaltung.findNutzerByID(t.getNutzerID()).getEmail())));
					
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

			Vector<Eigenschaftauspraegung> vecAus = this.verwaltung.getAuspraegungByKontaktID(t.getID());

			for (Eigenschaftauspraegung ea : vecAus) {

				if (ea.getWert().equals(wert)) {
					
					Eigenschaft e = this.verwaltung.getEigenschaftByID(ea.getEigenschaftsID());

					// eine Leere zeile
					Row kontaktRow = new Row();

					kontaktRow.addColumn(new Column(String.valueOf(t.getName())));
					if (t.getStatus() == 0) {
						kontaktRow.addColumn(new Column(String.valueOf("Nicht Geteilt")));
					} else {
						kontaktRow.addColumn(new Column(String.valueOf("geteilt")));
					}
					kontaktRow.addColumn(new Column(String.valueOf(this.verwaltung.findNutzerByID(t.getNutzerID()).getEmail())));
					
					kontaktRow.addColumn(new Column(String.valueOf(e.getBezeichnung())));
					
					kontaktRow.addColumn(new Column(String.valueOf(ea.getWert())));

					result.addRow(kontaktRow);
				}
			}
		}

		return result;

	}

}
