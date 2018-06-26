package de.hdm.itprojektgruppe4.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.report.AllEigeneKontakteReport;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenAuspraegungen;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaften;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaftsAuspraegungen;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmterTeilhaberschaftReport;

@RemoteServiceRelativePath("reportgenerator")

public interface ReportGenerator extends RemoteService {

	/**
	 * Objekt wird initialisiert.
	 * 
	 * @throws IllegalArgumentException
	 */
	public AllEigeneKontakteReport AllEigeneKontakteReport(int nutzerID) throws IllegalArgumentException;

	public void init() throws IllegalArgumentException;

	public Nutzer findNutzerByEmail(String email) throws IllegalArgumentException;

	public KontakteMitBestimmterTeilhaberschaftReport kontakteMitBestimmterTeilhaberschaftReport(int nutzerID,
			int teilhaberID);


	KontakteMitBestimmtenEigenschaftsAuspraegungen kontakteMitBestimmtenEigenschaftsAuspraegungen(int NutzerID,
			String bez, String wert) throws IllegalArgumentException;

	public KontakteMitBestimmtenAuspraegungen kontakteMitBestimmtenAuspraegungen(int NutzerID, String wert)
			throws IllegalArgumentException;

	public KontakteMitBestimmtenEigenschaften kontakteMitBestimmtenEigenschaften(int NutzerID, String bez)
			throws IllegalArgumentException;

	Vector<Nutzer> allNutzerReport();

}
