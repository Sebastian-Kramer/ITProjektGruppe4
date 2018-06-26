package de.hdm.itprojektgruppe4.server.report;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektgruppe4.client.EigenschaftAuspraegungWrapper;
import de.hdm.itprojektgruppe4.server.KontaktAdministrationImpl;
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

		CompositeParagraph imprint = new CompositeParagraph();

		// 1. Zeile
		imprint.addSubParagraph(new SimpleParagraph("Kontaktverwaltung"));

		// Das Hinzuf�gen des Impressum zum Report
		r.setImprint(imprint);

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
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");
		result.setCreated(simpleDateFormat.format(new Date()));
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

			kontaktRow
					.addColumn(new Column(String.valueOf(this.verwaltung.findNutzerByID(k.getNutzerID()).getEmail())));

			kontaktRow.addColumn(new Column(auspraegung.toString().replace("[", "").replace("]", "").replace(",", "")));

			result.addRow(kontaktRow);
		}

		return result;

	}




	
	/*
	 * REPORT 
	 */

	public KontakteMitBestimmterTeilhaberschaftReport kontakteMitBestimmterTeilhaberschaftReport(int nutzerID,
			int teilhaberID) throws IllegalArgumentException {
		if (this.getAdministration() == null)

			return null;

		// Leerer Report
		KontakteMitBestimmterTeilhaberschaftReport result = new KontakteMitBestimmterTeilhaberschaftReport();

		Vector<Kontakt> teilhaben = this.verwaltung.findGeteilteKontakteFromNutzerAndTeilhaber(nutzerID, teilhaberID);

		Vector<EigenschaftAuspraegungWrapper> auspraegung = new Vector<EigenschaftAuspraegungWrapper>();

		Nutzer teilhaber = this.verwaltung.findNutzerByID(teilhaberID);

		result.setTitle("Geteilte Kontakte");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");
		result.setCreated(simpleDateFormat.format(new Date()));

		Row headline = new Row();

		headline.addColumn(new Column("Kontakt"));
		headline.addColumn(new Column("Status"));
		headline.addColumn(new Column("Erstellt von"));
		headline.addColumn(new Column("Teilhaber"));
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

			kontaktRow
					.addColumn(new Column(String.valueOf(this.verwaltung.findNutzerByID(k.getNutzerID()).getEmail())));

			kontaktRow.addColumn(new Column(String.valueOf(teilhaber.getEmail())));

			kontaktRow.addColumn(new Column(auspraegung.toString().replace("[", "").replace("]", "").replace(",", "")));

			result.addRow(kontaktRow);
		}

		return result;

	}
	
	
	/*
	 * 
	 * 
	 * 
	 * REPORT
	 */

	public KontakteMitBestimmtenEigenschaftsAuspraegungen kontakteMitBestimmtenEigenschaftsAuspraegungen(int NutzerID,
			String bez, String wert) throws IllegalArgumentException {
		if (this.getAdministration() == null)

			return null;

		// Leerer Report
		KontakteMitBestimmtenEigenschaftsAuspraegungen result = new KontakteMitBestimmtenEigenschaftsAuspraegungen();

		Vector<Kontakt> kontakt = this.verwaltung.findKontakteByEigAus(NutzerID, bez, wert);

		result.setTitle("Kontakte mit bestimmten Eigenschaftsauspraegungen");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");
		result.setCreated(simpleDateFormat.format(new Date()));

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
					kontaktRow.addColumn(
							new Column(String.valueOf(this.verwaltung.findNutzerByID(t.getNutzerID()).getEmail())));

					kontaktRow.addColumn(new Column(String.valueOf(e.getBezeichnung())));

					kontaktRow.addColumn(new Column(String.valueOf(ea.getWert())));

					result.addRow(kontaktRow);
				}
			}

		}

		return result;

	}


	public KontakteMitBestimmtenEigenschaften kontakteMitBestimmtenEigenschaften(int NutzerID, String bez)
			throws IllegalArgumentException {
		if (this.getAdministration() == null)

			return null;

		// Leerer Report
		KontakteMitBestimmtenEigenschaften result = new KontakteMitBestimmtenEigenschaften();

		Vector<Kontakt> kontakt = this.verwaltung.findKontakeByEig(NutzerID, bez);

		result.setTitle("Kontakte mit bestimmten Eigenschaftsauspraegungen");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");
		result.setCreated(simpleDateFormat.format(new Date()));

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
					kontaktRow.addColumn(
							new Column(String.valueOf(this.verwaltung.findNutzerByID(t.getNutzerID()).getEmail())));

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
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");
		result.setCreated(simpleDateFormat.format(new Date()));

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
					kontaktRow.addColumn(
							new Column(String.valueOf(this.verwaltung.findNutzerByID(t.getNutzerID()).getEmail())));

					kontaktRow.addColumn(new Column(String.valueOf(e.getBezeichnung())));

					kontaktRow.addColumn(new Column(String.valueOf(ea.getWert())));

					result.addRow(kontaktRow);
				}
			}
		}

		return result;

	}
	
	public Vector<Nutzer> allNutzerReport() throws IllegalArgumentException {
		if (this.getAdministration() == null)
			return null;


		Vector<Nutzer> nutzer = this.verwaltung.findAllNutzer();

		return nutzer;

	}
	
	
	public Nutzer findNutzerByEmail(String email) throws IllegalArgumentException {

		return this.verwaltung.findNutzerByEmail(email);

	}
	
}
