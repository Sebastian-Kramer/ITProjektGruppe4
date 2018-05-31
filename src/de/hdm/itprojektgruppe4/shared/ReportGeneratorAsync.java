package de.hdm.itprojektgruppe4.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektgruppe4.shared.report.AllEigeneKontakteReport;
import de.hdm.itprojektgruppe4.shared.report.AllKontakteVonAnderemNutzerReport;
import de.hdm.itprojektgruppe4.shared.report.AllNutzerReport;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenAuspraegungenReport;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaftenReport;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaftsAuspraegungenReport;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmterTeilhaberschaftReport;

public interface ReportGeneratorAsync {

	void init(AsyncCallback<Void> callback);

	void createAllNutzerReport(AsyncCallback<AllNutzerReport> callback);

	void createAllKontakteVonAnderemNutzerReport(AsyncCallback<AllKontakteVonAnderemNutzerReport> callback);

	void createKontakteMitBestimmtenEigenschaftenReport(
			AsyncCallback<KontakteMitBestimmtenEigenschaftenReport> callback);

	void createKontakteMitBestimmtenAuspraegungenReport(
			AsyncCallback<KontakteMitBestimmtenAuspraegungenReport> callback);

	void createKontakteMitBestimmtenEigenschaftsAuspraegungenReport(
			AsyncCallback<KontakteMitBestimmtenEigenschaftsAuspraegungenReport> callback);

	void createAllEigeneKontakteReport(AsyncCallback<AllEigeneKontakteReport> callback);

	void createKontakteMitBestimmterTeilhaberschaftReport(
			AsyncCallback<KontakteMitBestimmterTeilhaberschaftReport> callback);

}
