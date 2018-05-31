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
	  
	  public String getReportText(){
		  return this.getHeader() + this.reportText + this.getTrailer();
	  }

	@Override
	public void process(KontakteMitBestimmtenEigenschaftenReport p) {
	    this.resetReportText();
	    
	    StringBuffer result = new StringBuffer();
	    
	    result.append("<h3>"+ p.getTitle() + "</h3>");
		  result.append("<table style=\"width:400px;border:1px solid silver\"><tr>");
		  result.append("</tr><tr><td></td><td>" + p.getCreated().toString() + "</td></tr></table>");

		  	 Vector<Row> rows = p.getRows();
		     result.append("<table style=\"width:400px\">");
		     for (int i = 0; i < rows.size(); i++) {
		         Row row = rows.elementAt(i);
		         result.append("<tr>");
		         for (int k = 0; k < row.getColumns().size(); k++) {
			           if (i == 0) {
			        	
		             result.append("<td style=\"background:silver;font-weight:bold\">" + row.getColumnAt(k)
		                 + "</td>");	             
		           }
		           else {
		             if (i > 1) {
		               result.append("<td style=\"border-top:1px solid silver\">"
		                   + row.getColumnAt(k) + "</td>");
		             }
		             else {
		               result.append("<td valign=\"top\">" + row.getColumnAt(k) + "</td>");
		             }
		           }
		         }
		         result.append("</tr>");
		       }

		       result.append("</table>");    
		       this.reportText = result.toString();
	}
		
	

	@Override
	public void process(AllKontakteReport p) {
	    this.resetReportText();
	    
	    StringBuffer result = new StringBuffer();
	   
		  
		  result.append("<h3>"+ p.getTitle() + "</h3>");
		  result.append("<table style=\"width:400px;border:1px solid silver\"><tr>");
		  result.append("</tr><tr><td></td><td>" + p.getCreated().toString() + "</td></tr></table>");

		  	 Vector<Row> rows = p.getRows();
		     result.append("<table style=\"width:400px\">");
		     for (int i = 0; i < rows.size(); i++) {
		         Row row = rows.elementAt(i);
		         result.append("<tr>");
		         for (int k = 0; k < row.getColumns().size(); k++) {
		           if (i == 0) {
		        	
		             result.append("<td style=\"background:silver;font-weight:bold\">" + row.getColumnAt(k)
		                 + "</td>");	             
		           }
		           else {
		             if (i > 1) {
		               result.append("<td style=\"border-top:1px solid silver\">"
		                   + row.getColumnAt(k) + "</td>");
		             }
		             else {
		               result.append("<td valign=\"top\">" + row.getColumnAt(k) + "</td>");
		             }
		           }
		         }
		         result.append("</tr>");
		       }

		       result.append("</table>");    
		       this.reportText = result.toString();
		
	}

	@Override
	public void process(KontakteMitBestimmterTeilhaberschaft p) {
	    this.resetReportText();
		
	    StringBuffer result = new StringBuffer();
	    
	    result.append("<h3>"+ p.getTitle() + "</h3>");
		  result.append("<table style=\"width:400px;border:1px solid silver\"><tr>");
		  result.append("</tr><tr><td></td><td>" + p.getCreated().toString() + "</td></tr></table>");

		  	 Vector<Row> rows = p.getRows();
		     result.append("<table style=\"width:400px\">");
		     for (int i = 0; i < rows.size(); i++) {
		         Row row = rows.elementAt(i);
		         result.append("<tr>");
		         for (int k = 0; k < row.getColumns().size(); k++) {
		           if (i == 0) {
		        	
		             result.append("<td style=\"background:silver;font-weight:bold\">" + row.getColumnAt(k)
		                 + "</td>");	             
		           }
		           else {
		             if (i > 1) {
		               result.append("<td style=\"border-top:1px solid silver\">"
		                   + row.getColumnAt(k) + "</td>");
		             }
		             else {
		               result.append("<td valign=\"top\">" + row.getColumnAt(k) + "</td>");
		             }
		           }
		         }
		         result.append("</tr>");
		       }

		       result.append("</table>");    
		       this.reportText = result.toString();
	
	}

	

	
	
}
