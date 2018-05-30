package de.hdm.itprojektgruppe4.shared;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.EigenschaftAuspraegungHybrid;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft_Auspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.KontaktKontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.bo.Person;
import de.hdm.itprojektgruppe4.shared.bo.Teilhaberschaft;

public interface KontaktAdministrationAsync {

	void init(AsyncCallback<Void> callback);
	

	void insertKontakt(String name, Date erzeugungsdatum, Date modifikationsdatum, int status, int nutzerID,
			AsyncCallback<Kontakt> callback);

	void insertNutzer(String mail, AsyncCallback<Nutzer> callback);



	void findKontaktByID(int id, AsyncCallback<Kontakt> callback);


	void findKontaktByName(String name, AsyncCallback<Kontakt> callback);


	void findAllKontakte(AsyncCallback<Vector<Kontakt>> callback);
	
	
	void findAllKontaktNames(AsyncCallback<List<Kontakt>> callback);


	void updateKontakt(Kontakt k, AsyncCallback<Kontakt> callback);
	

	void deleteKontakt(Kontakt k, AsyncCallback<Void> callback);


	void deletePerson(Person p, AsyncCallback<Void> callback);
 

	void findPersonByID(int ID, AsyncCallback<Person> callback);


	void deleteNutzer(Nutzer n, AsyncCallback<Void> callback);


	void updateNutzer(Nutzer n, AsyncCallback<Nutzer> callback);


	void findNutzerByEmail(String email, AsyncCallback<Nutzer> callback);


	void findNutzerByID(int id, AsyncCallback<Nutzer> callback);


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


	void insertAuspraegung(String wert, int status, int eigenschaftsID, int kontaktID,
			AsyncCallback<Eigenschaftauspraegung> callback);


	void updateAuspraegung(Eigenschaftauspraegung ea, AsyncCallback<Eigenschaftauspraegung> callback);


	void deleteAuspraegung(Eigenschaftauspraegung ea, AsyncCallback<Void> callback);


	void getAuspraegungByWert(String wert, AsyncCallback<Eigenschaftauspraegung> callback);


	void getAuspraegungByID(int id, AsyncCallback<Eigenschaftauspraegung> callback);


	void findByID(int id, AsyncCallback<Teilhaberschaft> callback);


	void insertTeilhaberschaft(int kontaktID, int kontaktListeID, int eigenschaftsauspraegungID, int teilhaberID,
			AsyncCallback<Teilhaberschaft> callback);


	void deleteTeilhaberschaft(Teilhaberschaft t, AsyncCallback<Void> callback);


	void deleteKontaktFromTeilhaberschaft(Teilhaberschaft t, AsyncCallback<Void> callback);


	void deleteKontaktlisteFromTeilhaberschaft(Teilhaberschaft t, AsyncCallback<Void> callback);


	void deleteEigenschaftsauspraegungFromTeilhaberschaft(Teilhaberschaft t, AsyncCallback<Void> callback);



	void insertKontaktKontaktliste(int kontaktID, int kontaktlisteID, AsyncCallback<KontaktKontaktliste> callback);


	void deleteKontaktKontaktliste(KontaktKontaktliste k, AsyncCallback<Void> callback);

	
	void findKontaktByNutzerID(int nutzerID,AsyncCallback<Vector<Kontakt>> callback);


	void findKontaktlisteByNutzerID(int nutzerID, AsyncCallback<Vector<Kontaktliste>> callback);


	void insertEigenschaft(String bez, int status, AsyncCallback<Eigenschaft> callback);
	
	
	void findAllKontakteFromKontaktliste(int i, AsyncCallback<Vector<Integer>> callback);


	void findAllEigenschaft(AsyncCallback<Vector<Eigenschaft>> callback);



	void findEigenschaftauspraegungByKontaktID(int kontaktID, AsyncCallback<Vector<Eigenschaftauspraegung>> callback);
	
	void getAuspraegungByKontaktID(int id, AsyncCallback<Vector<Eigenschaftauspraegung>> callback);


	void findHybrid(Person pers, AsyncCallback<Vector<EigenschaftAuspraegungHybrid>> callback);

	void getAllKontakteFromKontaktliste(int kontaktlisteID, AsyncCallback<Vector<Kontakt>> callback);


	void getAllKontakteFromKontaktliste(Kontaktliste kl, AsyncCallback<Vector<Kontakt>> callback);

	void deleteEigenschaftUndAuspraegung(EigenschaftAuspraegungHybrid ea, AsyncCallback<Void> callback);



	void insertBasicAuspraegung(String wert, int status, int kontaktID,
			AsyncCallback<Vector<Eigenschaftauspraegung>> callback);


	void getAllKontaktlistenFromUser(int NutzerID, AsyncCallback<Vector<Kontaktliste>> callback);


	void getAllTeilhaberschaftenFromUser(int nutzerID, AsyncCallback<Vector<Teilhaberschaft>> callback);


	


	




	
	/*
	void createEigenschaft(String bezeichnung, boolean status, AsyncCallback<Eigenschaft> callback);

	void init(AsyncCallback<Void> callback);

	void createEigenschaftsauspraegung(String bezeichnung, AsyncCallback<Eigenschaftsauspraegung> callback);

	void createKontakt(String name, Date erzeugungsdatum, Date modifikationsdatum, boolean status,
			AsyncCallback<Kontakt> callback);
	
	void createKontaktliste(String bezeichnung, boolean status, AsyncCallback<Kontaktliste> callback);
	
	void createTeilhaberschaftKlist(int KontaktlisteID, int KontaktID, int NutzerID, int Eigenschaftsauspraegung,
			AsyncCallback<Teilhaberschaft> callback);
	
	void createTeilhaberschaftKontakt(int KontaktID, int NutzerID, int Eigenschaftsauspraegung,
			AsyncCallback<Teilhaberschaft> callback);
	
	void deleteEigenschaft(Eigenschaft eigenschaft, AsyncCallback<Void> callback);

	void deleteKontakt(Kontakt kontakt, AsyncCallback<Void> callback);

	void deleteEigenschaftsauspraegung(Eigenschaftsauspraegung eigenschaftsauspraegung, AsyncCallback<Void> callback);

	void deleteKontaktliste(Kontaktliste kontaktliste, AsyncCallback<Void> callback);

	void deleteTeilhaberschaft(Teilhaberschaft teilhaberschaft, AsyncCallback<Void> callback);
	
	void findEigenschaftById(int id, AsyncCallback<Eigenschaft> callback);

	void findEigenschaftsauspraegungById(int id, AsyncCallback<Eigenschaftsauspraegung> callback);

	void findKontaktById(int id, AsyncCallback<Kontakt> callback);

	void findKontaktlisteById(int id, AsyncCallback<Kontaktliste> callback);

	void findNutzerById(int id, AsyncCallback<Kontakt> callback);

	void findTeilhaberschaftById(int id, AsyncCallback<Teilhaberschaft> callback);

	void getAllEigenschaftFromKontakt(Kontakt k, AsyncCallback<Vector<Eigenschaft>> callback);

	void getAllKontakte(AsyncCallback<Vector<Kontakt>> callback);

	void getAllKontaktefromNutzer(Kontakt k, AsyncCallback<Vector<Kontakt>> callback);

	void getAllKontakteVonKontaktliste(Kontaktliste k, AsyncCallback<Vector<Kontakt>> callback);

	void getAllKontaktlisten(AsyncCallback<Vector<Kontaktliste>> callback);
	
	void updateEigenschaft(Eigenschaft eigenschaft, AsyncCallback<Void> callback);

	void updateEigenschaftsauspraegung(Eigenschaftsauspraegung eigenschaftsauspraegung, AsyncCallback<Void> callback);

	void updateKontakt(Kontakt kontakt, AsyncCallback<Void> callback);

	void updateKontaktliste(Kontaktliste kontaktliste, AsyncCallback<Void> callback);

	void updateTeilhaberschaft(Teilhaberschaft teilhaberschaft, AsyncCallback<Void> callback);
	*/
	
}
