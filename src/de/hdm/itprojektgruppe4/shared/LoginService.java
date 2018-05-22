package de.hdm.itprojektgruppe4.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojektgruppe4.client.LoginInfo;

@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {
	
	public LoginInfo login(String requestUri);
}