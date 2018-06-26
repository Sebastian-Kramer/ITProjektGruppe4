package de.hdm.itprojektgruppe4.shared;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektgruppe4.client.EigenschaftAuspraegungWrapper;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.KontaktKontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.bo.Person;
import de.hdm.itprojektgruppe4.shared.bo.Teilhaberschaft;

/**
 * Das asynchrone Gegenstück des Interface. Es wird teilautomatisiert durch das
 * Google-Plugin erstellt.
 *
 */

public interface KontaktAdministrationAsync {

	void init(AsyncCallback<Void> callback);

	void insertKontakt(String name, Date erzeugungsdatum, Date modifikationsdatum, int status, int nutzerID,
			AsyncCallback<Kontakt> callback);

	void insertNutzer(String emailAddress, AsyncCallback<Nutzer> callback);

	void findKontaktByID(int id, AsyncCallback<Kontakt> callback);

	void findKontaktByName(String name, AsyncCallback<Kontakt> callback);

	void findAllKontakte(AsyncCallback<Vector<Kontakt>> callback);

	void findAllKontaktNames(AsyncCallback<List<Kontakt>> callback);

	void updateKontakt(Kontakt k, AsyncCallback<Kontakt> callback);

	void updateKontaktStatus(Kontakt k, Nutzer n, AsyncCallback<Void> callback);

	void deleteKontakt(Kontakt k, AsyncCallback<Void> callback);

	void deletePerson(Person p, AsyncCallback<Void> callback);

	void findPersonByID(int ID, AsyncCallback<Person> callback);

	void deleteNutzer(Nutzer n, AsyncCallback<Void> callback);

	void updateNutzer(Nutzer n, AsyncCallback<Nutzer> callback);

	void findNutzerByEmail(String email, AsyncCallback<Nutzer> callback);

	void findNutzerByID(String string, AsyncCallback<Nutzer> callback);

	void findAllNutzer(AsyncCallback<Vector<Nutzer>> callback);

	void findKontaktlisteByID(int id, AsyncCallback<Kontaktliste> callback);

	void findKontaktlisteByBezeichnung(String bezeichnung, AsyncCallback<Kontaktliste> callback);

	void insertKontaktliste(String bez, int status, int nutzerID, AsyncCallback<Kontaktliste> callback);

	void updateKontaktliste(Kontaktliste k, AsyncCallback<Kontaktliste> callback);

	void deleteKontaktliste(Kontaktliste k, AsyncCallback<Void> callback);

	void findKontaktlisteAll(AsyncCallback<Vector<Kontaktliste>> callback);

	void getEigenschaftByID(int id, AsyncCallback<Eigenschaft> callback);

	void updateEigenschaft(Eigenschaft e, AsyncCallback<Eigenschaft> callback);

	void deleteEigenschaft(Eigenschaft e, AsyncCallback<Void> callback);

	void insertEigenschaft(String bez, int status, AsyncCallback<Eigenschaft> callback);

	void findAllEigenschaft(AsyncCallback<Vector<Eigenschaft>> callback);

	void insertAuspraegung(String wert, int status, int eigenschaftsID, int kontaktID,
			AsyncCallback<Eigenschaftauspraegung> callback);

	void updateAuspraegung(Eigenschaftauspraegung ea, AsyncCallback<Eigenschaftauspraegung> callback);

	void deleteAuspraegung(Eigenschaftauspraegung ea, AsyncCallback<Void> callback);

	void getAuspraegungByWert(String wert, AsyncCallback<Vector<EigenschaftAuspraegungWrapper>> callback);

	void getAuspraegungByEigID(int eigID, int kontaktID, AsyncCallback<Eigenschaftauspraegung> callback);

	void getAuspraegungByID(int id, AsyncCallback<Eigenschaftauspraegung> callback);

	void findByID(int id, AsyncCallback<Teilhaberschaft> callback);

	void insertTeilhaberschaft(int kontaktListeID, int kontaktID, int eigenschaftsauspraegungID, int teilhaberID,
			int nutzerID, AsyncCallback<Teilhaberschaft> callback);

	void deleteTeilhaberschaft(Teilhaberschaft t, AsyncCallback<Void> callback);

	void deleteKontaktFromTeilhaberschaft(Teilhaberschaft t, AsyncCallback<Void> callback);

	void deleteKontaktlisteFromTeilhaberschaft(Teilhaberschaft t, AsyncCallback<Void> callback);

	void deleteEigenschaftsauspraegungFromTeilhaberschaft(Eigenschaftauspraegung ea, Nutzer n,
			AsyncCallback<Void> callback);

	void insertKontaktKontaktliste(int kontaktID, int kontaktlisteID, AsyncCallback<KontaktKontaktliste> callback);

	void deleteKontaktKontaktliste(KontaktKontaktliste k, AsyncCallback<Void> callback);

	void deleteKontaktKontaktlisteByKontaktIDAndByKListID(int kontaktID, int kontaktlisteID,
			AsyncCallback<Void> callback);

	void findKontaktlisteByNutzerID(int nutzerID, AsyncCallback<Vector<Kontaktliste>> callback);

	void findAllKontakteFromKontaktliste(int i, AsyncCallback<Vector<Integer>> callback);

	void findKontaktByNutzerID(int nutzerID, AsyncCallback<List<Kontakt>> callback);

	void findKontaktByAuspraegungID(int id, AsyncCallback<Kontakt> callback);
	
	void findEinenKontaktByAuspraegungID(int id, AsyncCallback<String> callback);

	void findEigenschaftauspraegungByKontaktID(int kontaktID, AsyncCallback<Vector<Eigenschaftauspraegung>> callback);

	void getAuspraegungByKontaktID(int id, AsyncCallback<Vector<Eigenschaftauspraegung>> callback);

	void findHybrid(Person pers, AsyncCallback<Vector<EigenschaftAuspraegungWrapper>> callback);

	void getAllKontakteFromKontaktliste(int kontaktlisteID, AsyncCallback<Vector<Kontakt>> callback);

	void deleteEigenschaftUndAuspraegung(EigenschaftAuspraegungWrapper ea, AsyncCallback<Void> callback);

	void insertBasicAuspraegung(String wert, int status, int kontaktID,
			AsyncCallback<Vector<Eigenschaftauspraegung>> callback);

	void findNutzerByID(int id, AsyncCallback<Nutzer> callback);

	void getAllKontaktlistenFromUser(int NutzerID, AsyncCallback<Vector<Kontaktliste>> callback);

	void getAllTeilhaberschaftenFromUser(int nutzerID, AsyncCallback<Vector<Teilhaberschaft>> callback);

	void getKontaktKontaktlisteFromKontaktliste(int kontaktlisteID,
			AsyncCallback<Vector<KontaktKontaktliste>> callback);

	void getEigenschaftByBezeichnung(String bez, AsyncCallback<Vector<Eigenschaft>> callback);

	void getEigenschaftbyKontaktID(int id, AsyncCallback<Vector<Eigenschaft>> callback);

	void insertTeilhaberschaftKontakt(int kontaktID, int teilhaberID, int nutzerID,
			AsyncCallback<Teilhaberschaft> callback);

	void insertMeineKontakte(String bez, int status, int nutzerID, AsyncCallback<Kontaktliste> callback);

	void findAllKontaktFromNutzer(int nutzerID, AsyncCallback<Vector<Kontakt>> callback);

	void findBasicKontaktliste(int nutzerID, AsyncCallback<Kontaktliste> callback);

	void findKontaktliste(int nutzerID, String bez, AsyncCallback<Kontaktliste> callback);

	void findKontaktKontaktlisteByKontaktIDAndKlisteID(int kontaktID, int kListID,
			AsyncCallback<KontaktKontaktliste> callback);

	void findKontaktlisteMeineGeteiltenKontakte(String kList, int nutzerID, AsyncCallback<Kontaktliste> callback);

	void deleteTeilhaberschaftByKontaktlisteID(int kontaktlisteID, AsyncCallback<Void> callback);

	void findAllSharedKontakteVonNutzer(int nutzerID, AsyncCallback<Vector<Kontakt>> callback);

	void insertTeilhaberschaftKontaktliste(int kontaktlisteID, String email, int nutzerID,
			AsyncCallback<Teilhaberschaft> callback);

	void insertTeilhaberschaftAuspraegung(int eigenschaftauspraegungID, int teilhaberID, int nutzerID,
			AsyncCallback<Teilhaberschaft> callback);

	void findTeilhaberschaftByKontaktlisteID(int kontaktlisteID, AsyncCallback<Vector<Teilhaberschaft>> callback);

	void findAllTeilhaberFromKontaktliste(int kontaktlisteID, AsyncCallback<Vector<Nutzer>> callback);

	void deleteKontaktKontaktlisteByKontaktID(int kontaktID, AsyncCallback<Void> callback);

	void deleteTeilhaberschaftByTeilhaberID(int teilhaberID, AsyncCallback<Void> callback);

	void deleteTeilhaberschaftByKontaktIDAndNutzerID(int kontaktID, int teilNutzerID, AsyncCallback<Void> callback);

	void deleteUpdateTeilhaberschaft(Eigenschaftauspraegung ea, Nutzer teilhaber, Nutzer n, Kontakt k,
			AsyncCallback<Void> callback);

	void findKontaktlisteByNutzerIDexceptBasicList(int nutzerID, AsyncCallback<Vector<Kontaktliste>> callback);

	void findTeilhaberschaftByTeilhaberID(int teilhaberID, int kontaktlisteID, AsyncCallback<Teilhaberschaft> callback);

	void findTeilhaberschaftByKontaktID(int kontaktID, AsyncCallback<Vector<Teilhaberschaft>> callback);

	void findByTeilhaberschaftByKontaktlistIDAndTeilhaberID(int kontaktlisteID, int teilhaberID,
			AsyncCallback<Teilhaberschaft> callback);

	void findTeilhaberschaftByAuspraegungIDAndTeilhaberID(int auspraegungID, int teilhaberID,
			AsyncCallback<Vector<Teilhaberschaft>> callback);

	void findTeilhaberschaftByKontaktIDAndTeilhaberID(int kontaktID, int teilhaberID,
			AsyncCallback<Vector<Teilhaberschaft>> callback);

	void findTeilhaberschaftByTeilhaberIDAndNutzerID(int teilhaberID, int nutzerID,
			AsyncCallback<Vector<Teilhaberschaft>> callback);

	void findKontaktByNameAndNutzerID(Kontakt kontakt, AsyncCallback<Vector<Kontakt>> callback);

	void findEigByBezeichnung(String bez, AsyncCallback<Eigenschaft> callback);

	void insertKontaktinMeineGeteiltenKontakte(int konID, int kListID, AsyncCallback<KontaktKontaktliste> callback);

	void findSharedWithNutzer(int nutzerID, int kontaktlisteID, AsyncCallback<Vector<Nutzer>> callback);

	void findTeilhaberschaftByNutzerIDKontaktlisteID(int nutzerID, int kontaktlisteID,
			AsyncCallback<Vector<Teilhaberschaft>> callback);

	void insertTeilhaberschaftAuspraegungenKontakt(Kontakt kon, String selectedValue, int id,
			AsyncCallback<Integer> callback);

	void findAllEigenschaftauspraegung(AsyncCallback<Vector<Eigenschaftauspraegung>> callback);

	void insertTeilhaberschaftAusgewaehlteAuspraegungenKontakt(Kontakt kon, Vector<EigenschaftAuspraegungWrapper> eaw,
			String selectedValue, int id, AsyncCallback<Integer> callback);

	void getAllTeilhaberfromAuspraegung(int auspraegungID, AsyncCallback<Vector<Nutzer>> callback);

	void findTeilhaberschaftByAuspraegungID(int auspraegungID, AsyncCallback<Vector<Teilhaberschaft>> callback);

	void findTeilhaberschaftByAuspraegungIdAndTeilhaberId(int auspraegungID, int nutzerID,
			AsyncCallback<Teilhaberschaft> callback);

	void findSharedAuspraegung(int kontaktID, int nutzerID,
			AsyncCallback<Vector<EigenschaftAuspraegungWrapper>> callback);

	void findGeteilteKontakteFromNutzerAndTeilhaber(int nutzerID, int teilhaberID,
			AsyncCallback<Vector<Kontakt>> callback);

	void findKontakteByEigAus(int NutzerID, String bez, String wert, AsyncCallback<Vector<Kontakt>> callback);

	void updateKontaktlisteStatus(int kontaktlisteID, AsyncCallback<Kontaktliste> callback);

	void findKontakeByEig(int NutzerID, String bez, AsyncCallback<Vector<Kontakt>> callback);

	void findKontakteByAus(int NutzerID, String wert, AsyncCallback<Vector<Kontakt>> callback);

	void findNutzerToShareListWith(int kontaktlisteID, int nutzerID, AsyncCallback<Vector<Nutzer>> callback);

	void deleteTeilhaberschaftAnKontaktliste(int teilhaberID, int kontaktlisteID, AsyncCallback<Void> callback);

	void findAllSharedAuspraegungenFromKontaktID(int kontaktID, AsyncCallback<Vector<Eigenschaftauspraegung>> callback);

	void hinzuzufuegendeKontakte(int nutzerID, int kontaktlisteID, AsyncCallback<Vector<Kontakt>> callback);

	void getAllTeilhaberFromKontakt(int kontaktID, AsyncCallback<Vector<Nutzer>> callback);

	void findKontaktKontaktlisteByKontaktID(int kontaktID, AsyncCallback<Vector<KontaktKontaktliste>> callback);

	void findKontaktlistenToAddKontakt(int kontaktID, int nutzerID, AsyncCallback<Vector<Kontaktliste>> callback);
	
	void getAllTeilhaberfromAuspraegungBerechtigung(int auspraegung, int teilhaberID, AsyncCallback<Vector<Nutzer>> callback);
	
	void deleteTeilhaberschaftAllByKontaktIDAndTeilhaberID(int kontaktID, int teilhaberID, AsyncCallback<Void> callback);

}
