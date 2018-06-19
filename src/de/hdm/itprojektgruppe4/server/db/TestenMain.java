package de.hdm.itprojektgruppe4.server.db;
import java.sql.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.google.gwt.core.client.impl.Impl;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektgruppe4.server.KontaktAdministrationImpl;
import de.hdm.itprojektgruppe4.server.db.KontaktMapper;
import de.hdm.itprojektgruppe4.shared.KontaktAdministrationAsync;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft_Auspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Kontaktliste;
import de.hdm.itprojektgruppe4.shared.KontaktAdministration;
import de.hdm.itprojektgruppe4.server.db.TeilhaberschaftMapper;
import de.hdm.itprojektgruppe4.server.db.*;
import de.hdm.itprojektgruppe4.shared.bo.*;

public class TestenMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//KontaktMapper.kontaktMapper().findKontaktByID(1);
		final KontaktAdministrationAsync kontaktVerwaltung = null;
		
		
		
		Date date = new Date();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

		System.out.println("Datum: " + simpleDateFormat.format(date));

		
		
	//	System.out.println(KontaktMapper.kontaktMapper().findKontaktByID(3));
	
//		Kontaktliste k1 = new Kontaktliste();
//		Kontakt k = new Kontakt();
//		Eigenschaft e = new Eigenschaft();
//		Eigenschaftauspraegung ea = new Eigenschaftauspraegung();
//		Eigenschaft_Auspraegung aa = new Eigenschaft_Auspraegung();
//		
//		k1.setID(1);
//		
//		System.out.println(KontaktlisteMapper.kontaktlisteMapper().findbyid(k1.getID()));
//		
//		k.setID(1);
//		
//		System.out.println(KontaktMapper.kontaktMapper().findKontaktByID(k.getID()));
//		
//		e.setID(11);
//		
//		aa.setID(1);
//		
//		Eigenschaft_Auspraegung aaa = Eigenschaft_AuspraegungMapper.eigenschaft_auspraegungMapper().getEigenchaftAuspraegungByID(1);
//		
//		System.out.println(Eigenschaft_AuspraegungMapper.eigenschaft_auspraegungMapper().getEigenchaftAuspraegungByID(aa.getID()));
//		
//		System.out.println(EigenschaftMapper.eigenschaftMapper().getEigenchaftByID(aaa.getEigenschaft_id()).getID());
//
//		
		
		
		
		
		/*
		k.setId(1);
		k.setNutzer_id(1);
		k.setEigenschaft_id(1);
		k.setName("Person1");
		k.setErzeugungsdatum(date);
		k.setModifikationsdatum(date);
		k.setStatus(0);
		*/
		
		
		
	//	 KontaktMapper.kontaktMapper().insertKontakt(k);
		 
//		 Kontakt abc = new Kontakt();
//		 
//		 abc.setID(1);
//		 abc.setName("IMPLBOI");
//		abc.setErzeugungsdatum(date);
//		 abc.setModifikationsdatum(date);
//		 abc.setStatus(1);
//		 abc.setNutzerID(1);
		 
		// KontaktMapper.kontaktMapper().insertKontakt(abc);
		 
		 
//	Kontakt implBOI = new Kontakt();
//		 
	     KontaktAdministrationImpl kcre = new KontaktAdministrationImpl();
//		
		kcre.init();
		
		
		
		/**
		Kontakt i = new  Kontakt();
		
		i.setID(2);
		
		System.out.println(EigenschaftauspraegungMapper.eigenschaftauspraegungMapper().findAuspraegungByKontaktID(2));
		
		System.out.println(kcre.findEigenschaftauspraegungByKontaktID(2));
		*/
		
		System.out.println(KontaktKontaktlisteMapper.kontaktkontaktlistemapper().getKontaktKontaktlisteByKontaktlisteID(1));

		System.out.println(kcre.findTeilhaberschaftByAuspraegungIDAndTeilhaberID(1,31));
		
		//System.out.println(kcre.findKontaktByID(5));
//		
		//
		
		//System.out.println(kcre.findKontaktByName("'IMPLBOI'"));
		
		//kcre.insertKontakt("IMPLBOI", date, date, 1, 1);
		
		//KontaktMapper.kontaktMapper().findKontaktByName(abc.getName());
		 
		//System.out.println(KontaktMapper.kontaktMapper().findKontaktByID(5));
		
		System.out.println(kcre.findTeilhaberschaftByKontaktID(166));
		
	}
	

}
