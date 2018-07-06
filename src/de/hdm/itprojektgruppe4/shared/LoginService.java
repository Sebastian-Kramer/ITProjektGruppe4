package de.hdm.itprojektgruppe4.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojektgruppe4.client.LoginInfo;

/**
 *  Das synchrone Interface für den Login. 
 *  Angabe des RemoteServiceRelativePath's, muss mit der Angabe in der web.xml übereinstimmten.
 * @author Nino
 *
 */

@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {
	
	public LoginInfo login(String requestUri);
}