package de.hdm.itprojektgruppe4.shared.report;

public abstract class ReportWriter {
	
	public abstract void process(KontakteMitBestimmtenEigenschaftsAuspraegungenReport c);
	
	public abstract void process(AllEigeneKontakteReport c); 
	
	public abstract void process(KontakteMitBestimmterTeilhaberschaftReport c);
	
	
	
	
}
