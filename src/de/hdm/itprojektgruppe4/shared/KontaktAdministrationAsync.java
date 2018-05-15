package de.hdm.itprojektgruppe4.shared;

import java.util.Date;
import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
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


	void findAll(AsyncCallback<Vector<Kontakt>> callback);


	void updateKontakt(Kontakt k, AsyncCallback<Kontakt> callback);
	

	void deleteKontakt(Kontakt k, AsyncCallback<Kontakt> callback);


	void deletePerson(Person p, AsyncCallback<Person> callback);


	void findPersonByID(int ID, AsyncCallback<Person> callback);
	
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
