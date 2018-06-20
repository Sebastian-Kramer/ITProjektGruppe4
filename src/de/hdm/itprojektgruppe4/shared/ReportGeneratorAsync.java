package de.hdm.itprojektgruppe4.shared;

import java.util.List;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.report.AllEigeneKontakteReport;
import de.hdm.itprojektgruppe4.shared.report.AllNutzerReport;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenAuspraegungen;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaften;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaftsAuspraegungen;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaftsAuspraegungenReport;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmterTeilhaberschaftReport;

public interface ReportGeneratorAsync {

	void init(AsyncCallback<Void> callback);
	
	
	
	void insertNutzer(String mail, AsyncCallback<Nutzer> callback);



	void findNutzerByEmail(String email, AsyncCallback<Nutzer> callback);






	void AllEigeneKontakteReport(int nutzerID, AsyncCallback<AllEigeneKontakteReport> asyncCallback);










	void allNutzerReport(AsyncCallback<Vector<Nutzer>> callback);






	void AlleKontakteByNutzer(int nutzerID, int teilhaberID, AsyncCallback<AllEigeneKontakteReport> callback);






	void kontakteMitBestimmterTeilhaberschaftReport(int nutzerID, int teilhaberID,
			AsyncCallback<KontakteMitBestimmterTeilhaberschaftReport> callback);



	void findAllEigenschaft(AsyncCallback<Vector<Eigenschaft>> callback);



	void findEigenschaftByBezeichnung(String bez, AsyncCallback<Vector<Eigenschaft>> callback);



	void findAllEigenschaftsAuspraegungn(AsyncCallback<Vector<Eigenschaftauspraegung>> callback);



	void kontakteMitBestimmtenEigenschaftsAuspraegungen(int NutzerID, String bez, String wert,
			AsyncCallback<KontakteMitBestimmtenEigenschaftsAuspraegungen> asyncCallback);



	void kontakteMitBestimmtenAuspraegungen(int NutzerID, String wert,
			AsyncCallback<KontakteMitBestimmtenAuspraegungen> callback);



	void kontakteMitBestimmtenEigenschaften(int NutzerID, String bez,
			AsyncCallback<KontakteMitBestimmtenEigenschaften> callback);



















//	void KontakteMitBestimmterTeilhaberschaftReport(AsyncCallback<AllEigeneKontakteReport> asyncCallback);







	



	



	



	
	

}
