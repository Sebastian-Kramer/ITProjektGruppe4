package de.hdm.itprojektgruppe4.shared.bo;
import java.util.Date;
public class Kontakt extends BusinessObject{

	

	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private int nutzer_id;
	
	private int eigenschaft_id;
	
	private String name;
	
	private Date erzeugungsdatum;
	
	private Date modifikationsdatum;
	
	private int status;
	
	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getNutzer_id() {
		return nutzer_id;
	}



	public void setNutzer_id(int nutzer_id) {
		this.nutzer_id = nutzer_id;
	}



	public int getEigenschaft_id() {
		return eigenschaft_id;
	}



	public void setEigenschaft_id(int eigenschaft_id) {
		this.eigenschaft_id = eigenschaft_id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Date getErzeugungsdatum() {
		return erzeugungsdatum;
	}



	public void setErzeugungsdatum(Date erzeugungsdatum) {
		this.erzeugungsdatum = erzeugungsdatum;
	}



	public Date getModifikationsdatum() {
		return modifikationsdatum;
	}



	public void setModifikationsdatum(Date modifikationsdatum) {
		this.modifikationsdatum = modifikationsdatum;
	}


	
	
	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}



	public String toString(){
		return  "Kontakt [name=" + name + " "+ id + " " + eigenschaft_id +  "]";
	}
}

