package de.hdm.itprojektgruppe4.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.report.AllEigeneKontakteReport;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenAuspraegungen;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaften;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaftsAuspraegungen;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmterTeilhaberschaftReport;

/**
 * Synchrone Schnittstelle für eine RPC-fähige Klasse zur Erstellung von
 * Reports
 * @author Georg
 *
 */
@RemoteServiceRelativePath("reportgenerator")
public interface ReportGenerator extends RemoteService {

	/**
	 * Objekt wird initialisiert.
	 * 
	 * @throws IllegalArgumentException
	 */
	public void init() throws IllegalArgumentException;

	/**
	 * Erstellen eines <code>AllEigeneKontakteReport</code>-Reports.
     * Dieser Report-Typ stellt sämtliche Kontakte des Nutzers dar.
	 * 
	 * @param nutzerID
	 * @return
	 * @throws IllegalArgumentException
	 */
	public AllEigeneKontakteReport AllEigeneKontakteReport(int nutzerID) throws IllegalArgumentException;

	/**
	 * Erstellen eines <code>findNutzerByEmail</code>-Reports.
     * Dieser Report-Typ liest den Nutzer anhand seiner Email aus.
	 * 
	 * 
	 * @param email
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Nutzer findNutzerByEmail(String email) throws IllegalArgumentException;

	/**
	 * Erstellen eines <code>KontakteMitBestimmterTeilhaberschaftReport</code>-Reports.
     * Dieser Report-Typ liest die alle Kontakte aus die mit bestimmten Nutzer geteilt wirden sind.
	 * 
	 * 
	 * @param nutzerID
	 * @param teilhaberID
	 * @return
	 * @throws IllegalArgumentException
	 */
	public KontakteMitBestimmterTeilhaberschaftReport kontakteMitBestimmterTeilhaberschaftReport(int nutzerID,
			int teilhaberID);

	/**
	 * Erstellen eines <code>KontakteMitBestimmtenEigenschaftsAuspraegungen</code>-Reports.
     * Dieser Report-Typ liest die alle eigenen Kontakte des NUtzers aus 
     * die bestimmte Eigenschaften & Ausprägungen aufweisen
	 * 
	 * 
	 * @param NutzerID
	 * @param bez
	 * @param wert
	 * @return
	 * @throws IllegalArgumentException
	 */
	KontakteMitBestimmtenEigenschaftsAuspraegungen kontakteMitBestimmtenEigenschaftsAuspraegungen(int NutzerID,
			String bez, String wert) throws IllegalArgumentException;
	/**	 
	 * Erstellen eines <code>KontakteMitBestimmtenAuspraegungen</code>-Reports.
     * Dieser Report-Typ liest die alle eigenen Kontakte des Nutzers aus 
     * die bestimmte Ausprägungen aufweisen
	 * 
	 * 
	 * @param NutzerID
	 * @param wert
	 * @return
	 * @throws IllegalArgumentException
	 */
	public KontakteMitBestimmtenAuspraegungen kontakteMitBestimmtenAuspraegungen(int NutzerID, String wert)
			throws IllegalArgumentException;

	/**
	 * Erstellen eines <code>KontakteMitBestimmtenEigenschaften</code>-Reports.
     * Dieser Report-Typ liest die alle eigenen Kontakte des Nutzers aus 
     * die bestimmte Eigenschaften aufweisen
	 * 
	 * @param NutzerID
	 * @param bez
	 * @return
	 * @throws IllegalArgumentException
	 */
	public KontakteMitBestimmtenEigenschaften kontakteMitBestimmtenEigenschaften(int NutzerID, String bez)
			throws IllegalArgumentException;

	/**
	 * Gibt dem Nutzer alle Nutzer des System aus
	 * @return
	 */
	Vector<Nutzer> allNutzerReport();

}
