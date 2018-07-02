package de.hdm.itprojektgruppe4.shared.bo;

/**
 * Realisierung einer exemplarischen Eigenschaftsauspraegung
 *
 */
public class Eigenschaft_Auspraegung extends BusinessObject{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Eindeutige Id der Eigenschaft
	 */
	
	private int eigenschaft_id;
	
	/**
	 * Eindeutige Id der Eigenschaftsausprägung
	 */
	
	private int eigenschaftauspraegung_id;
	
	/**
	 * Eindeutige Id der Kontakt Id
	 */
	
	private int kontakt_id;

	public int getEigenschaft_id() {
		return eigenschaft_id;
	}
	
	/**
	 * Setzen der Eigenschafts Id
	 * @param eigenschaft_id
	 */

	public void setEigenschaft_id(int eigenschaft_id) {
		this.eigenschaft_id = eigenschaft_id;
	}
	
	/**
	 * Auslesen der Eigenschaftsauspraegungs Id
	 * @return eigenschatftsauspraegung_id
	 */

	public int getEigenschaftauspraegung_id() {
		return eigenschaftauspraegung_id;
	}
	
	/**
	 * Setzen der Eigenschaftsauspraegungs Id
	 * @param eigenschaftauspraegung_id
	 */

	public void setEigenschaftauspraegung_id(int eigenschaftauspraegung_id) {
		this.eigenschaftauspraegung_id = eigenschaftauspraegung_id;
	}

	/**
	 * Auslesen der Kontakt Id
	 * @return kontakt_id
	 */
	public int getKontakt_id() {
		return kontakt_id;
	}

	/**
	 * Setzen der Kontakt Id
	 * @param kontakt_id
	 */
	public void setKontakt_id(int kontakt_id) {
		this.kontakt_id = kontakt_id;
	}
	
	
	
	

}
