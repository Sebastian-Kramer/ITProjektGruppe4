package de.hdm.itprojektgruppe4.shared.report;

import java.util.Vector;

/**
 * Einfacher Report. Weist Informationen der Superklasse Report auf und eine Tabelle auf, 
 * die auf die beiden Hilfsklassen Row und Column zugreift
 * Code in Anlehnung an Prof. Thies
 *
 */

	public abstract class SimpleReport extends Report {
	
		private static final long serialVersionUID = 1L ;
		
		private Vector<Row> table = new Vector<Row>();
		
		  public void addRow(Row r) {
			    this.table.addElement(r);
			  }
		  
		  
		  public void removeRow(Row r) {
			    this.table.removeElement(r);
			  }
		  
		  public Vector<Row> getRows() {
			    return this.table;
		  	}
	
	}
	
