package de.hdm.itprojektgruppe4.shared;

import com.google.gwt.user.client.rpc.RemoteService;

import de.hdm.itprojektgruppe4.shared.report.AllEigeneKontakteReport;
import de.hdm.itprojektgruppe4.shared.report.AllKontakteVonAnderemNutzerReport;
import de.hdm.itprojektgruppe4.shared.report.AllNutzerReport;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenAuspraegungenReport;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaftenReport;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaftsAuspraegungenReport;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmterTeilhaberschaftReport;

public interface ReportGenerator extends RemoteService {

	public void init() throws IllegalArgumentException;

	AllEigeneKontakteReport createAllEigeneKontakteReport() throws IllegalArgumentException;

	AllNutzerReport createAllNutzerReport() throws IllegalArgumentException;

	AllKontakteVonAnderemNutzerReport createAllKontakteVonAnderemNutzerReport() throws IllegalArgumentException;

	KontakteMitBestimmtenEigenschaftenReport createKontakteMitBestimmtenEigenschaftenReport()
			throws IllegalArgumentException;

	KontakteMitBestimmtenAuspraegungenReport createKontakteMitBestimmtenAuspraegungenReport()
			throws IllegalArgumentException;

	KontakteMitBestimmtenEigenschaftsAuspraegungenReport createKontakteMitBestimmtenEigenschaftsAuspraegungenReport()
			throws IllegalArgumentException;

	KontakteMitBestimmterTeilhaberschaftReport createKontakteMitBestimmterTeilhaberschaftReport()
			throws IllegalArgumentException;

}
