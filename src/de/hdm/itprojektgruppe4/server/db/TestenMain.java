package de.hdm.itprojektgruppe4.server.db;

import de.hdm.itprojektgruppe4.server.db.KontaktMapper;

public class TestenMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		//KontaktMapper.kontaktMapper().findKontaktByID(1);
		
		
		System.out.println(KontaktMapper.kontaktMapper().findKontaktByID(3));
	}

}
