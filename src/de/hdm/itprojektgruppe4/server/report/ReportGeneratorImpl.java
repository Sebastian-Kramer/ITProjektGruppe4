package de.hdm.itprojektgruppe4.server.report;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektgruppe4.server.KontaktAdministrationImpl;
import de.hdm.itprojektgruppe4.shared.KontaktAdministration;
import de.hdm.itprojektgruppe4.shared.ReportGenerator;
import de.hdm.itprojektgruppe4.shared.report.AllEigeneKontakteReport;
import de.hdm.itprojektgruppe4.shared.report.AllKontakteVonAnderemNutzerReport;
import de.hdm.itprojektgruppe4.shared.report.AllNutzerReport;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenAuspraegungenReport;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaftenReport;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaftsAuspraegungenReport;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmterTeilhaberschaftReport;

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
	
	/*
	 * 
	 * Erster Report
	 * 
	 */

	public AllEigeneKontakteReport createAllEigeneKontakteReport() throws IllegalArgumentException {
		if (this.getAdministration()== null)
			return null;
		
		AllEigeneKontakteReport report = new AllEigeneKontakteReport();
		
		report.setTitle("Alle eigenen Kontakte");
		
		return report;
		
	}

	public AllNutzerReport createAllNutzerReport() throws IllegalArgumentException {
		return null;
	}

	public AllKontakteVonAnderemNutzerReport createAllKontakteVonAnderemNutzerReport() throws IllegalArgumentException {
		return null;
	}

	public KontakteMitBestimmtenEigenschaftenReport createKontakteMitBestimmtenEigenschaftenReport()
			throws IllegalArgumentException {
		return null;

	}

	public KontakteMitBestimmtenAuspraegungenReport createKontakteMitBestimmtenAuspraegungenReport()
			throws IllegalArgumentException {
		return null;
	}

	public KontakteMitBestimmtenEigenschaftsAuspraegungenReport createKontakteMitBestimmtenEigenschaftsAuspraegungenReport()
			throws IllegalArgumentException {
		return null;
	}

	public KontakteMitBestimmterTeilhaberschaftReport createKontakteMitBestimmterTeilhaberschaftReport()
			throws IllegalArgumentException {
		return null;
	}

}
