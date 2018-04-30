package de.hdm.itprojektgruppe4.server.db;
import java.sql.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import de.hdm.itprojektgruppe4.server.db.KontaktMapper;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;

public class TestenMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//KontaktMapper.kontaktMapper().findKontaktByID(1);
		
		
		
		
		Date date = new Date();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

		System.out.println("Datum: " + simpleDateFormat.format(date));

		
		
		//System.out.println(KontaktMapper.kontaktMapper().findKontaktByID(3));
	
		Kontakt k = new Kontakt();
		
		
		
		
		
		
		k.setID(10);
		k.setNutzerID(1);
		k.setKontaktID(1);
		k.setEigenschaftID(1);
		k.setName("DAWWG");
		k.setErzeugungsdatum(date);
		k.setModifikationsdatum(date);
		k.setStatus(true);
		
		
		 KontaktMapper.kontaktMapper().insertKontakt(k);
	}
	

}
