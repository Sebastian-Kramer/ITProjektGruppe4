package de.hdm.itprojektgruppe4.shared.report;

import java.io.Serializable;
import java.util.Date;





public abstract class Report implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	  private Paragraph imprint = null;
	 
	  private String title = "Report";
	  
	  private Paragraph headerData = null;
	  
	  private Date created = new Date();
	  
	  public Paragraph getImprint() {
		    return this.imprint;
		  }
	  
	  public void setImprint(Paragraph imprint) {
		    this.imprint = imprint;
		  }
	  
	  public Paragraph getHeaderData() {
		    return this.headerData;
	  }
	  
	  public void setHeaderData(Paragraph headerData) {
		    this.headerData = headerData;
		  }
	  
	  public String getTitle() {
		    return this.title;
		  }
	  
	  public void setTitle(String title) {
		    this.title = title;
		  }
	  
	  public Date getCreated() {
		    return this.created;
		  }
	  
	  public void setCreated(Date created) {
		    this.created = created;
		  }
	  
	  
}
