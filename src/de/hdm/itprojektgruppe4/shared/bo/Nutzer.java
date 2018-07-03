package de.hdm.itprojektgruppe4.shared.bo;

/**
 * Realisierung eines exemplarischen Nutzers
 *
 */
public class Nutzer extends Person {

	private static final long serialVersionUID = 1L;

	/**
	 * Emailadresse des Nutzers
	 */

	private String email;

	/**
	 * Auslesen der Emailadresse
	 * 
	 * @return email
	 */

	public String getEmail() {
		return email;
	}

	/**
	 * Setzen der Emailadresse
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Es wird eine einfache textuelle Darstellung des Nutzers erzeugt.
	 */

	@Override
	public String toString() {
		return this.getEmail();
	}

}
