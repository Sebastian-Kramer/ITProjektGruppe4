package de.hdm.itprojektgruppe4.server.db;
import java.sql.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import de.hdm.itprojektgruppe4.server.db.KontaktMapper;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;

public class TestenMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//KontaktMapper.kontaktMapper().findKontaktByID(1);
		
		
		
		
		Date date = new Date();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

		System.out.println("Datum: " + simpleDateFormat.format(date));

		
		
		//System.out.println(KontaktMapper.kontaktMapper().findKontaktByID(3));
	
		Kontaktliste k1 = new Kontaktliste();
		Kontakt k = new Kontakt();
		
		k1.setID(1);
		
		System.out.println(
		KontaktlisteMapper.kontaktlisteMapper().findbyid(k1.getID())
				);
		/*
		k.setId(1);
		k.setNutzer_id(1);
		k.setEigenschaft_id(1);
		k.setName("Person1");
		k.setErzeugungsdatum(date);
		k.setModifikationsdatum(date);
		k.setStatus(0);
		*/
		
		
		
		 //KontaktMapper.kontaktMapper().insertKontakt(k);
		 
		 
	}
	

}
