package de.hdm.itprojektgruppe4.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektgruppe4.client.LoginInfo;

public interface LoginServiceAsync {

	public void login(String requestUri, AsyncCallback<LoginInfo> async);

}
