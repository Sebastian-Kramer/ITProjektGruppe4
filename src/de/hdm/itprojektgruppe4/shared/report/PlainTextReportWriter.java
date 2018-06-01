package de.hdm.itprojektgruppe4.shared.report;


public abstract class PlainTextReportWriter extends ReportWriter {

	
	  private String reportText = "";

	  
	  public void resetReportText() {
	    this.reportText = "";
	  }

	 
	  public String getHeader() {
	    // hier noch Ausarbeiten
	    return "";
	  }

	 
	  public String getTrailer() {
	    
	    return "___________________________________________";
	  }

	@Override
	public void process(KontakteMitBestimmtenEigenschaftenReport c) {
		this.resetReportText();
		
		StringBuffer result = new StringBuffer();
		
	}

	@Override
	public void process(AllKontakteReport c) {
		this.resetReportText();
		
		StringBuffer result = new StringBuffer();
		
	}

	@Override
	public void process(KontakteMitBestimmterTeilhaberschaft c) {
		this.resetReportText();
		
		StringBuffer result = new StringBuffer();
		
	}

	
		
	
}
