package de.hdm.itprojektgruppe4.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.report.AllEigeneKontakteReport;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaftsAuspraegungenReport;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmterTeilhaberschaftReport;
@RemoteServiceRelativePath("reportgenerator")


public interface ReportGenerator extends RemoteService {

	public void init() throws IllegalArgumentException;
	
	
	 public Nutzer findNutzerByEmail(String email) throws IllegalArgumentException;

	AllEigeneKontakteReport createAllEigeneKontakteReport() throws IllegalArgumentException;

	KontakteMitBestimmtenEigenschaftsAuspraegungenReport createKontakteMitBestimmtenEigenschaftsAuspraegungenReport()
			throws IllegalArgumentException;

	KontakteMitBestimmterTeilhaberschaftReport createKontakteMitBestimmterTeilhaberschaftReport()
			throws IllegalArgumentException;

}
