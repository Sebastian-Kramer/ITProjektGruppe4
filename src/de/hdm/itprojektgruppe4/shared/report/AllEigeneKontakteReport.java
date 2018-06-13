package de.hdm.itprojektgruppe4.shared.report;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Report, der alle eigenen Kontakte eines Nutzers darstellt.
 * Die Klasse tr#gt keine weiteren Attribute- und Methoden-Implementierungen,
 * da alles Notwendige schon in den Superklassen vorliegt. Ihre Existenz ist 
 * dennoch wichtig, um bestimmte Typen von Reports deklarieren und mit ihnen 
 * objektorientiert umgehen zu können.
 * 
 * code in Anlehnung an Bankprojekt von Prof Thies
 * 
 */
public class AllEigeneKontakteReport extends SimpleReport  implements IsSerializable, Serializable{
	
	private static final long serialVersionUID = 1L;

}
