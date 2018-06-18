package de.hdm.itprojektgruppe4.shared;

import java.util.List;
import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


import de.hdm.itprojektgruppe4.shared.report.*;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.report.AllEigeneKontakteReport;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaftsAuspraegungenReport;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmterTeilhaberschaftReport;
@RemoteServiceRelativePath("reportgenerator")


public interface ReportGenerator extends RemoteService {

	public void init() throws IllegalArgumentException;
	
	
	 public Nutzer findNutzerByEmail(String email) throws IllegalArgumentException;
	 
	 
	
	 AllEigeneKontakteReport AllEigeneKontakteReport(int nutzerID);

public KontakteMitBestimmterTeilhaberschaftReport kontakteMitBestimmterTeilhaberschaftReport(int nutzerID, int teilhaberID);

	 Vector<Nutzer> allNutzerReport() ;

	 public AllEigeneKontakteReport AlleKontakteByNutzer(int nutzerID, int teilhaberID) throws IllegalArgumentException;

	 Vector<Eigenschaft> findAllEigenschaft() throws IllegalArgumentException;

		public Vector<Eigenschaft> findEigenschaftByBezeichnung(String bez) throws IllegalArgumentException ;


    public Nutzer insertNutzer(String mail) throws IllegalArgumentException;

	public Vector<Eigenschaftauspraegung> findAllEigenschaftsAuspraegungn () throws IllegalArgumentException;


}
