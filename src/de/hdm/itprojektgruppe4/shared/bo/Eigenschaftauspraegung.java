package de.hdm.itprojektgruppe4.shared.bo;

public class Eigenschaftauspraegung extends BusinessObject{
	
	private String wert;
	
	private int eigentuemer_id;

	public String getWert() {
		return wert;
	}

	public void setWert(String wert) {
		this.wert = wert;
	}

	public int getEigentuemer_id() {
		return eigentuemer_id;
	}

	public void setEigentuemer_id(int eigentuemer_id) {
		this.eigentuemer_id = eigentuemer_id;
	}

	
	
}
