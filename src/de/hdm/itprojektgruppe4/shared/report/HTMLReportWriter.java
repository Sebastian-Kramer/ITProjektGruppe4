package de.hdm.itprojektgruppe4.shared.report;
import java.util.Vector;

public class HTMLReportWriter extends ReportWriter {

	  private String reportText = "";

	
	  public void resetReportText() {
	    this.reportText = "";
	  }

	 
	  public String paragraph2HTML(Paragraph p) {
	    if (p instanceof CompositeParagraph) {
	      return this.paragraph2HTML((CompositeParagraph) p);
	    }
	    else {
	      return this.paragraph2HTML((SimpleParagraph) p);
	    }
	  }

	
	  public String paragraph2HTML(CompositeParagraph p) {
	    StringBuffer result = new StringBuffer();

	    for (int i = 0; i < p.getNumParagraphs(); i++) {
	      result.append("<p>" + p.getParagraphAt(i) + "</p>");
	    }

	    return result.toString();
	  }

	 
	  public String paragraph2HTML(SimpleParagraph p) {
	    return "<p>" + p.toString() + "</p>";
	  }

	 
	  public String getHeader() {
	    StringBuffer result = new StringBuffer();

	    result.append("<html><head><title></title></head><body>");
	    return result.toString();
	  }

	
	  public String getTrailer() {
	    return "</body></html>";
	  }

	@Override
	public void process(AllKontakteVonAnderemNutzerReport c) {
	    this.resetReportText();
	    
	    StringBuffer result = new StringBuffer();
		
	}

	@Override
	public void process(AllEigeneKontakteReport c) {
	    this.resetReportText();
	    
	    StringBuffer result = new StringBuffer();
		
	}

	@Override
	public void process(AllNutzerReport c) {
	    this.resetReportText();
		
	    StringBuffer result = new StringBuffer();
	}

	@Override
	public void process(KontakteMitBestimmtenEigenschaftenReport c) {
	    this.resetReportText();
	    
	    StringBuffer result = new StringBuffer();
	}

	@Override
	public void process(KontakteMitBestimmtenEigenschaftsAuspraegungenReport c) {
	    this.resetReportText();
		
	    StringBuffer result = new StringBuffer();
	}

	@Override
	public void process(KontakteMitBestimmtenAuspraegungenReport c) {
	    this.resetReportText();
	    
	    StringBuffer result = new StringBuffer();
		
	}

	@Override
	public void process(KontakteMitBestimmterTeilhaberschaftReport c) {
	    this.resetReportText();
	    
	    StringBuffer result = new StringBuffer();
		
	}

	
}
