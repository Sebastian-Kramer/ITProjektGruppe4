package de.hdm.itprojektgruppe4.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.report.AllEigeneKontakteReport;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenAuspraegungen;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaften;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaftsAuspraegungen;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmterTeilhaberschaftReport;

public interface ReportGeneratorAsync {

	void init(AsyncCallback<Void> callback);

	void findNutzerByEmail(String email, AsyncCallback<Nutzer> callback);

	void allNutzerReport(AsyncCallback<Vector<Nutzer>> callback);

	void kontakteMitBestimmterTeilhaberschaftReport(int nutzerID, int teilhaberID,
			AsyncCallback<KontakteMitBestimmterTeilhaberschaftReport> callback);

	void kontakteMitBestimmtenEigenschaftsAuspraegungen(int NutzerID, String bez, String wert,
			AsyncCallback<KontakteMitBestimmtenEigenschaftsAuspraegungen> asyncCallback);

	void kontakteMitBestimmtenAuspraegungen(int NutzerID, String wert,
			AsyncCallback<KontakteMitBestimmtenAuspraegungen> callback);

	void kontakteMitBestimmtenEigenschaften(int NutzerID, String bez,
			AsyncCallback<KontakteMitBestimmtenEigenschaften> callback);

	void AllEigeneKontakteReport(int nutzerID, AsyncCallback<AllEigeneKontakteReport> callback);

}
