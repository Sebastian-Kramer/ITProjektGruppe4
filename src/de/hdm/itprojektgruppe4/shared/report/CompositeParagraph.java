package de.hdm.itprojektgruppe4.shared.report;

import java.io.Serializable;
import java.util.Vector;

public class CompositeParagraph extends Paragraph implements Serializable {

	  
	  private static final long serialVersionUID = 1L;

	  
	  private Vector<SimpleParagraph> subParagraphs = new Vector<SimpleParagraph>();

	 
	  public void addSubParagraph(SimpleParagraph p) {
	    this.subParagraphs.addElement(p);
	  }

	 
	  public void removeSubParagraph(SimpleParagraph p) {
	    this.subParagraphs.removeElement(p);
	  }

	  
	  public Vector<SimpleParagraph> getSubParagraphs() {
	    return this.subParagraphs;
	  }

	 
	  public int getNumParagraphs() {
	    return this.subParagraphs.size();
	  }

	
	  public SimpleParagraph getParagraphAt(int i) {
	    return this.subParagraphs.elementAt(i);
	  }

	
	  
	public String toString() {
	   
	    StringBuffer result = new StringBuffer();


	    for (int i = 0; i < this.subParagraphs.size(); i++) {
	      SimpleParagraph p = this.subParagraphs.elementAt(i);

	     
	      result.append(p.toString() + "\n");
	    }

	   
	    return result.toString();
	  }

}
