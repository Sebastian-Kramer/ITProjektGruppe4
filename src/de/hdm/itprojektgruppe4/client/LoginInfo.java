package de.hdm.itprojektgruppe4.client;

import java.io.Serializable;


/**
 *  Der Login Dienst - Es resultiert daraus ein Objekt von LoginInfo welches alle
 *  benötigten Elemente  eines angemeldeten Nutzers beinhaltet.
 * @author Nino
 *
 */
public class LoginInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Überprüfung ob Nutzer bereits eingeloggt ist.
	 */
	
	private boolean loggedIn = false;
	
	/**
	 *  Speichern der LoginUrl
	 *  
	 */
	private String loginUrl;
	
	/**
	 *  Speichern der LogoutUrl
	 */
	private String logoutUrl;
	
	/**
	 * Speichern der GoogleMail Adresse
	 */
	private String emailAddress;
	
	/**
	 *  Speichern des Nicknames 
	 */
	private String nickname;
	
	
	/**
	 *  Prüfen ob User eingeloggt
	 * @return loggedIn
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	
	}

	public String getLoginUrl() {
		return loginUrl;
	}
	
	/**
	 *  Setzen der LoginUrl
	 * @param loginUrl
	 */

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	/**
	 * Setze der LogoutUrl
	 * @param logoutUrl
	 */
	
	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	/**
	 *  EMail Adresse erhalten
	 * @return emailAddress
	 */
	
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 *  EMail Adresse setzen.
	 * @param emailAddress
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	
	/**
	 *  Methode zum Nickname erhalten
	 * @return nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/** 
	 *  Methode um den Nickname zu setzen
	 * @param nickname
	 */
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
}