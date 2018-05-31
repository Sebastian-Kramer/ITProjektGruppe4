package de.hdm.itprojektgruppe4.shared.report;

public abstract class ReportWriter {
	
	public abstract void process(KontakteMitBestimmtenEigenschaftenReport c);
	
	public abstract void process(AllKontakteReport c); 
	
	public abstract void process(KontakteMitBestimmterTeilhaberschaft c);
	
	
}
