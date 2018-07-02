package de.hdm.itprojektgruppe4.shared.bo;

import java.io.Serializable;

public abstract class BusinessObject implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * eindeutige Identifikationsnummer einer Instanz der Klasse
	 */
	
	private int ID = 0;
	
	/**
	 * Auslesen der ID
	 * @return ID
	 */

	public int getID() {
		return ID;
	}
	
	/**
	 * Setzen der ID
	 * @param ID
	 */

	public void setID(int id) {
		this.ID = id;
	}

}
