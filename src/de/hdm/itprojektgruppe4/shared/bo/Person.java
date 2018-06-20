package de.hdm.itprojektgruppe4.shared.bo;

/**
 * Realisierung einer Exemplarischen Person
 * @author
 *
 */

public class Person  extends BusinessObject{
	
	private static final long serialVersionUID = 1L;
	
	  /**
	   * Erzeugen einer einfachen textuellen Repräsentation der jeweiligen
	   * Kontoinstanz.
	   */

	@Override
	public String toString() {
		return "Person [getID()=" + getID() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	
	
	
}
