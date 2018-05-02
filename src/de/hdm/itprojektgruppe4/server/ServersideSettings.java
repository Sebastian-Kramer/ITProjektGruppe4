package de.hdm.itprojektgruppe4.server;

import java.util.logging.Logger;

import de.hdm.itprojektgruppe4.shared.CommonSettings;

public class ServersideSettings extends CommonSettings{

	private static final String LOGGER_NAME = "Projektmarktplatz Server";
	private static final Logger log = Logger.getLogger(LOGGER_NAME);
	
	/**
	 * 
	 * @return Server-Seitige Logger-Insanz 
	 */
	public static Logger getLogger(){
		return log;
	}
}
