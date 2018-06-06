package de.hdm.itprojektgruppe4.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.report.AllEigeneKontakteReport;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaftsAuspraegungenReport;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmterTeilhaberschaftReport;

public interface ReportGeneratorAsync {

	void init(AsyncCallback<Void> callback);
	
	
	

	void createKontakteMitBestimmtenEigenschaftsAuspraegungenReport(
			AsyncCallback<KontakteMitBestimmtenEigenschaftsAuspraegungenReport> callback);

	void createAllEigeneKontakteReport(AsyncCallback<AllEigeneKontakteReport> callback);

	void createKontakteMitBestimmterTeilhaberschaftReport(
			AsyncCallback<KontakteMitBestimmterTeilhaberschaftReport> callback);




	void findNutzerByEmail(String email, AsyncCallback<Nutzer> callback);





	
	

}
