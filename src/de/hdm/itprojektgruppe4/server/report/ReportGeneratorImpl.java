package de.hdm.itprojektgruppe4.server.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektgruppe4.server.KontaktAdministrationImpl;
import de.hdm.itprojektgruppe4.server.db.KontaktMapper;
import de.hdm.itprojektgruppe4.shared.KontaktAdministration;
import de.hdm.itprojektgruppe4.shared.ReportGenerator;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.report.AllEigeneKontakteReport;
import de.hdm.itprojektgruppe4.shared.report.AllKontakteVonAnderemNutzerReport;
import de.hdm.itprojektgruppe4.shared.report.AllNutzerReport;
import de.hdm.itprojektgruppe4.shared.report.Column;
import de.hdm.itprojektgruppe4.shared.report.CompositeParagraph;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenAuspraegungenReport;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaftenReport;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaftsAuspraegungenReport;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmterTeilhaberschaftReport;
import de.hdm.itprojektgruppe4.shared.report.Report;
import de.hdm.itprojektgruppe4.shared.report.Row;
import de.hdm.itprojektgruppe4.shared.report.SimpleParagraph;

public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator {

	private static final long serialVersionUID = 1L;

	private KontaktAdministration verwaltung = null;

	public ReportGeneratorImpl() throws IllegalArgumentException {

	}

	public void init() throws IllegalArgumentException {

		KontaktAdministrationImpl k = new KontaktAdministrationImpl();
		k.init();
		this.verwaltung = k;

	}

	protected KontaktAdministration getAdministration() {
		return this.verwaltung;
	}
	
	protected void addImprint (Report r){
		
		// Das Impressum kann mehrere Seiten haben
		CompositeParagraph imprint = new CompositeParagraph();
		
		// 1. Zeile
		imprint.addSubParagraph(new SimpleParagraph("Kontaktverwaltung"));
		
		// Das Hinzufügen des Impressum zum Report
		r.setImprint(imprint);

	}
	
	/*
	 * 
	 * Erster Report
	 * 
	 */

	public List<Kontakt> createAllEigeneKontakteReport(int NutzerID) throws IllegalArgumentException {
		if (this.getAdministration()== null)
		      return null;

		List<Kontakt> kontakt = this.verwaltung.findKontaktByNutzerID(NutzerID);

		for (Kontakt k : kontakt){
			// eine Leere zeile
			Row kontaktRow = new Row();
			
			kontaktRow.addColumn(new Column(String.valueOf(k.getID())));
			
			kontaktRow.addColumn(new Column(String.valueOf(this.verwaltung.findKontaktByNutzerID(NutzerID))));
			
		//	result.addRow(kontaktRow);
		}

		return kontakt;
		
	}

	public List<Eigenschaft> createKontakteMitBestimmtenEigenschaftenReport(String bez) throws IllegalArgumentException {
		if (this.getAdministration()== null)
		      return null;

		List<Eigenschaft> eigenschaft = this.verwaltung.getEigenschaftByBezeichnung(bez);


		for (Eigenschaft e : eigenschaft){
			// eine Leere zeile
			Row eigenschaftRow = new Row();
			

			eigenschaftRow.addColumn(new Column(String.valueOf(e.getID())));
			
			eigenschaftRow.addColumn(new Column(String.valueOf(this.verwaltung.getEigenschaftByBezeichnung(bez))));
			
		//	result.addRow(kontaktRow);
		}

		return eigenschaft;
		
	}




//	public List<Eigenschaftauspraegung>  createKontakteMitBestimmtenAuspraegungenReport(String wert)
//			throws IllegalArgumentException {
//		if (this.getAdministration()== null)
//		      return null;
//
//			Eigenschaftauspraegung eigenschaftauspraegung = this.verwaltung.getAuspraegungByWert(wert);
//
//
//			for (Eigenschaftauspraegung ea : eigenschaftauspraegung){
//				// eine Leere zeile
//				Row eigenschaftauspraegungRow = new Row();
//				
//
//				eigenschaftauspraegungRow.addColumn(new Column(String.valueOf(ea.getID())));
//				
//				eigenschaftauspraegungRow.addColumn(new Column(String.valueOf(this.verwaltung.getEigenschaftByBezeichnung(wert))));
//				
//			//	result.addRow(kontaktRow);
//			}
//
//			return eigenschaftauspraegung;
//			
//		}
//	

	public KontakteMitBestimmtenEigenschaftsAuspraegungenReport createKontakteMitBestimmtenEigenschaftsAuspraegungenReport()
			throws IllegalArgumentException {
		return null;
	}

	public KontakteMitBestimmterTeilhaberschaftReport createKontakteMitBestimmterTeilhaberschaftReport()
			throws IllegalArgumentException {
		return null;
	}

	@Override
	public AllEigeneKontakteReport createAllEigeneKontakteReport() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AllNutzerReport createAllNutzerReport() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AllKontakteVonAnderemNutzerReport createAllKontakteVonAnderemNutzerReport() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KontakteMitBestimmtenEigenschaftenReport createKontakteMitBestimmtenEigenschaftenReport()
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KontakteMitBestimmtenAuspraegungenReport createKontakteMitBestimmtenAuspraegungenReport()
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}



}
