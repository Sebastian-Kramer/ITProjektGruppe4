package de.hdm.itprojektgruppe4.server.report;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektgruppe4.client.EigenschaftAuspraegungWrapper;
import de.hdm.itprojektgruppe4.server.KontaktAdministrationImpl;
import de.hdm.itprojektgruppe4.shared.KontaktAdministration;
import de.hdm.itprojektgruppe4.shared.ReportGenerator;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaft;
import de.hdm.itprojektgruppe4.shared.bo.Eigenschaftauspraegung;
import de.hdm.itprojektgruppe4.shared.bo.Kontakt;
import de.hdm.itprojektgruppe4.shared.bo.Nutzer;
import de.hdm.itprojektgruppe4.shared.bo.Teilhaberschaft;
import de.hdm.itprojektgruppe4.shared.report.AllEigeneKontakteReport;
import de.hdm.itprojektgruppe4.shared.report.Column;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenAuspraegungen;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaften;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmtenEigenschaftsAuspraegungen;
import de.hdm.itprojektgruppe4.shared.report.KontakteMitBestimmterTeilhaberschaftReport;
import de.hdm.itprojektgruppe4.shared.report.Row;

/**
 * Implementierung des ReportGenerator-Interface
 * 
 * @author Georg
 *
 */
public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator {

	private static final long serialVersionUID = 1L;

	/**
	 * Der Report benï¿½tigt den Zugriff auf die Administration
	 */
	private KontaktAdministration verwaltung = null;

	/**
	 * NO-Argument-Konstruktor
	 */

	public ReportGeneratorImpl() throws IllegalArgumentException {

	}

	/**
	 * Initialisierung des Objekts. Diese Methode ist vor dem Hintergrund von
	 * GWT RPC zusï¿½tzlich zum No Argument Constructor notwendig.
	 */
	public void init() throws IllegalArgumentException {

		KontaktAdministrationImpl k = new KontaktAdministrationImpl();
		k.init();
		this.verwaltung = k;

	}

	/**
	 * Auslesen der Administration
	 */
	protected KontaktAdministration getAdministration() {
		return this.verwaltung;
	}

	/**
	 * Erster Report
	 * 
	 * Der Report Namens AllEigeneKontakte gibt dem Nutzer Alle seine eigenen
	 * Kontakte sowie die geteilte Kontakte in Form eines Report aus
	 * 
	 * @param nutzerID
	 * @return der fertige Report
	 */

	public AllEigeneKontakteReport AllEigeneKontakteReport(int nutzerID) throws IllegalArgumentException {
		if (this.getAdministration() == null)
			return null;

		/*
		 * leerer Report wird angelegt
		 */
		AllEigeneKontakteReport result = new AllEigeneKontakteReport();

		/*
		 * Alle Kontakte und alle geteilten Kontakte vom Nutzer werden
		 * ausgelesen
		 */
		Vector<Kontakt> kontakt = this.verwaltung.findAllKontaktFromNutzer(nutzerID);

		Vector<EigenschaftAuspraegungWrapper> auspraegung = new Vector<EigenschaftAuspraegungWrapper>();

		/*
		 * Hinzufügen der Kopfzeile
		 */
		result.setTitle("Meine Kontakte und mir geteilte Kontakte");

		/*
		 * Anpassung an das Datumformat
		 */
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");
		result.setCreated(simpleDateFormat.format(new Date()));

		/*
		 * Leere Zeile wird angelegt
		 */
		Row headline = new Row();

		headline.addColumn(new Column("Kontakt"));
		headline.addColumn(new Column("Geteilt mit"));
		headline.addColumn(new Column("Erstellt von"));
		headline.addColumn(new Column("Eigenschaften"));

		result.addRow(headline);
		/*
		 * for each Schleife wird aufgerufen um die Daten für den Report
		 * auszulesen
		 */
		for (Kontakt k : kontakt) {

			Vector<Teilhaberschaft> t = this.verwaltung.findTeilhaberschaftByKontaktIDAndNutzerID(k.getID(), nutzerID);

			Vector<Nutzer> n = new Vector<Nutzer>();
			/*
			 * for each Schleife wird aufgerufen um die Daten für den Report
			 * auszulesen
			 */
			for (Teilhaberschaft th : t) {
				n.add(this.verwaltung.findNutzerByID(th.getTeilhaberID()));
			}

			Row kontaktRow = new Row();

			auspraegung = this.verwaltung.findHybrid(k);

			kontaktRow.addColumn(new Column(String.valueOf(k.getName())));
			/*
			 * Abfrage des Teilhaberstatus
			 */
			if (k.getStatus() == 0) {
				kontaktRow.addColumn(new Column(String.valueOf("keine Teilhaberschaften vorhanden")));
			} else {
				kontaktRow.addColumn(new Column(n.toString().replace("[", "").replace("]", "").replace(",", "")));
			}
			/*
			 * Emailadresser wird hinzugefügt
			 */
			kontaktRow
					.addColumn(new Column(String.valueOf(this.verwaltung.findNutzerByID(k.getNutzerID()).getEmail())));

			kontaktRow.addColumn(new Column(auspraegung.toString().replace("[", "").replace("]", "").replace(",", "")));

			/*
			 * Zeile wird dem Report hinzugefpgt
			 */
			result.addRow(kontaktRow);
		}
		/*
		 * Rückgabe des fertigen Report
		 */
		return result;

	}

	/**
	 * Zweiter Report
	 * 
	 * Der Report Namens KontakteMitBestimmterTeilhaberschaftReport gibt dem
	 * Nutzer Alle seine ihm geteilten Kontakte in Form eines Report aus
	 * 
	 * @param teilhaberID
	 * @return der fertige Report
	 */
	public KontakteMitBestimmterTeilhaberschaftReport kontakteMitBestimmterTeilhaberschaftReport(int nutzerID,
			int teilhaberID) throws IllegalArgumentException {
		if (this.getAdministration() == null)

			return null;

		/*
		 * Leerer Report wird angelegt
		 */
		KontakteMitBestimmterTeilhaberschaftReport result = new KontakteMitBestimmterTeilhaberschaftReport();
		/*
		 * Alle geteilten Kontakte werden ausgelesen
		 */
		Vector<Kontakt> teilhaben = this.verwaltung.findGeteilteKontakteFromNutzerAndTeilhaber(nutzerID, teilhaberID);

		Vector<EigenschaftAuspraegungWrapper> auspraegung = new Vector<EigenschaftAuspraegungWrapper>();

		Nutzer teilhaber = this.verwaltung.findNutzerByID(teilhaberID);
		/*
		 * Titel des Reports
		 */
		result.setTitle("Geteilte Kontakte");

		/*
		 * Anpassung an das Format des Datum
		 */
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");
		result.setCreated(simpleDateFormat.format(new Date()));

		/*
		 * Kopfzeile wird angelegt
		 */
		Row headline = new Row();

		/*
		 * Überschriften der jeweiligen Spalten in der Kopfzeile
		 */
		headline.addColumn(new Column("Kontakt"));
		headline.addColumn(new Column("Status"));
		headline.addColumn(new Column("Erstellt von"));
		headline.addColumn(new Column("Teilhaber"));
		headline.addColumn(new Column("Eigenschaft"));

		/*
		 * Hinzufügen der Kopfzeile
		 */
		result.addRow(headline);

		/*
		 * Es werden sämtliche Kontakte die dem Nutzer geteilt wurden ausgelsen
		 * und anschließend in die Tabelle eingetragen
		 */
		for (Kontakt k : teilhaben) {

			Row kontaktRow = new Row();

			auspraegung = this.verwaltung.findSharedAuspraegung(k.getID(), teilhaberID);

			kontaktRow.addColumn(new Column(String.valueOf(k.getName())));

			if (k.getStatus() == 0) {
				kontaktRow.addColumn(new Column(String.valueOf("Nicht Geteilt")));
			} else {
				kontaktRow.addColumn(new Column(String.valueOf("geteilt")));
			}

			kontaktRow
					.addColumn(new Column(String.valueOf(this.verwaltung.findNutzerByID(k.getNutzerID()).getEmail())));

			kontaktRow.addColumn(new Column(String.valueOf(teilhaber.getEmail())));

			kontaktRow.addColumn(new Column(auspraegung.toString().replace("[", "").replace("]", "").replace(",", "")));

			result.addRow(kontaktRow);
		}

		/*
		 * Der fertige Report wird zurückgegeben
		 */
		return result;

	}

	/**
	 * Dritter Report
	 * 
	 * Der Report Namens KontakteMitBestimmtenEigenschaftsAuspraegungen gibt dem
	 * Nutzer Alle seine ihm Kontakte anhand der Eigenschaft & Ausprägung in
	 * Form eines Report aus
	 * 
	 * @param String
	 *            bez, String wert
	 * @return der fertige Report
	 */
	public KontakteMitBestimmtenEigenschaftsAuspraegungen kontakteMitBestimmtenEigenschaftsAuspraegungen(int NutzerID,
			String bez, String wert) throws IllegalArgumentException {
		if (this.getAdministration() == null)

			return null;

		/*
		 * Leerer Report wird angelegt
		 */
		KontakteMitBestimmtenEigenschaftsAuspraegungen result = new KontakteMitBestimmtenEigenschaftsAuspraegungen();

		/*
		 * Auslesen der Eigenen Kontakte anhand der NutzerID, bezeichnung der
		 * Eigenschaft und dem Wert der Ausprägung
		 */
		Vector<Kontakt> kontakt = this.verwaltung.findKontakteByEigAus(NutzerID, bez, wert);

		/*
		 * Titel des Reports
		 */
		result.setTitle("Kontakte mit bestimmten Eigenschaftsauspraegungen");

		/*
		 * Anpassung an das Format des Datum
		 */
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");
		result.setCreated(simpleDateFormat.format(new Date()));

		/*
		 * Kopfzeile wird angelegt
		 */
		Row headline = new Row();

		/*
		 * Überschriften der jeweiligen Spalten in der Kopfzeile
		 */
		headline.addColumn(new Column("Kontakt"));
		headline.addColumn(new Column("Erzeugungsdatum"));
		headline.addColumn(new Column("Modifikationsdatum"));
		headline.addColumn(new Column("Eigenschaft"));
		headline.addColumn(new Column("Auspraegung"));
		result.addRow(headline);

		/*
		 * Es werden sämtliche Kontakte des Nutzer mit bestimmten Eigenschaften
		 * & Ausprägungen ausgelesen und anschließend in die Tabelle eingetragen
		 */
		for (Kontakt t : kontakt) {

			Vector<Eigenschaft> vecEig = this.verwaltung.getEigenschaftbyKontaktID(t.getID());

			for (Eigenschaft e : vecEig) {

				Eigenschaftauspraegung ea = this.verwaltung.getAuspraegungByEigID(e.getID(), t.getID());

				if (e.getBezeichnung().equals(bez) && ea.getWert().equals(wert)) {

					// eine Leere zeile
					Row kontaktRow = new Row();

					kontaktRow.addColumn(new Column(String.valueOf(t.getName())));
					if (t.getStatus() == 0) {
						kontaktRow.addColumn(new Column(String.valueOf("Nicht Geteilt")));
					} else {
						kontaktRow.addColumn(new Column(String.valueOf("geteilt")));
					}
					kontaktRow.addColumn(
							new Column(String.valueOf(this.verwaltung.findNutzerByID(t.getNutzerID()).getEmail())));

					kontaktRow.addColumn(new Column(String.valueOf(e.getBezeichnung())));

					kontaktRow.addColumn(new Column(String.valueOf(ea.getWert())));

					result.addRow(kontaktRow);
				}
			}

		}

		/*
		 * Der fertige Report wird zurückgegeben
		 */
		return result;

	}

	/**
	 * Vierter Report
	 * 
	 * Der Report Namens KontakteMitBestimmtenEigenschaft gibt dem Nutzer Alle
	 * seine ihm Kontakte anhand der Eigenschaft in Form eines Report aus
	 * 
	 * @param NutzerID,
	 *            bez
	 * @return der fertige Report
	 */
	public KontakteMitBestimmtenEigenschaften kontakteMitBestimmtenEigenschaften(int NutzerID, String bez)
			throws IllegalArgumentException {
		if (this.getAdministration() == null)

			return null;

		/*
		 * Leerer Report wird angelegt
		 */
		KontakteMitBestimmtenEigenschaften result = new KontakteMitBestimmtenEigenschaften();

		/*
		 * Auslesen der Eigenen Kontakte anhand der NutzerID, bezeichnung der
		 * Eigenschaft
		 */
		Vector<Kontakt> kontakt = this.verwaltung.findKontakeByEig(NutzerID, bez);

		/*
		 * Titel des Reports
		 */
		result.setTitle("Kontakte mit bestimmten Eigenschaftsauspraegungen");

		/*
		 * Anpassung an das Format des Datum
		 */
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");
		result.setCreated(simpleDateFormat.format(new Date()));

		/*
		 * Kopfzeile wird angelegt
		 */
		Row headline = new Row();

		/*
		 * Überschriften der jeweiligen Spalten in der Kopfzeile
		 */
		headline.addColumn(new Column("Kontakt"));
		headline.addColumn(new Column("Status"));
		headline.addColumn(new Column("Erstellt von"));
		headline.addColumn(new Column("Eigenschaft"));
		headline.addColumn(new Column("Auspraegung"));

		result.addRow(headline);

		/*
		 * Es werden sämtliche Kontakte des Nutzer mit bestimmten Eigenschaften
		 * & Ausprägungen ausgelesen und anschließend in die Tabelle eingetragen
		 */
		for (Kontakt t : kontakt) {

			Vector<Eigenschaft> vecEig = this.verwaltung.getEigenschaftbyKontaktID(t.getID());

			for (Eigenschaft e : vecEig) {

				if (e.getBezeichnung().equals(bez)) {

					Eigenschaftauspraegung ea = this.verwaltung.getAuspraegungByEigID(e.getID(), t.getID());

					// eine Leere zeile
					Row kontaktRow = new Row();

					kontaktRow.addColumn(new Column(String.valueOf(t.getName())));
					if (t.getStatus() == 0) {
						kontaktRow.addColumn(new Column(String.valueOf("Nicht Geteilt")));
					} else {
						kontaktRow.addColumn(new Column(String.valueOf("geteilt")));
					}
					kontaktRow.addColumn(
							new Column(String.valueOf(this.verwaltung.findNutzerByID(t.getNutzerID()).getEmail())));

					kontaktRow.addColumn(new Column(String.valueOf(e.getBezeichnung())));

					kontaktRow.addColumn(new Column(String.valueOf(ea.getWert())));

					// Hinzufügen der Kopfzeile
					result.addRow(kontaktRow);
				}
			}

		}

		/*
		 * Der fertige Report wird zurückgegeben
		 */
		return result;
	}

	/**
	 * Fünfter Report
	 * 
	 * Der Report Namens KontakteMitBestimmtenAusprägungen gibt dem Nutzer Alle
	 * seine ihm Kontakte anhand der Ausprägung in Form eines Report aus
	 * 
	 * @param NutzerID,
	 *            wert
	 * @return der fertige Report
	 */
	public KontakteMitBestimmtenAuspraegungen kontakteMitBestimmtenAuspraegungen(int NutzerID, String wert)
			throws IllegalArgumentException {
		if (this.getAdministration() == null)
			return null;

		/*
		 * Leerer Report wird angelegt
		 */
		KontakteMitBestimmtenAuspraegungen result = new KontakteMitBestimmtenAuspraegungen();

		/*
		 * Auslesen aller eigenen Kontakte anhand der Ausprägung
		 */
		Vector<Kontakt> kontakt = this.verwaltung.findKontakteByAus(NutzerID, wert);

		/*
		 * Titel des Reports
		 */
		result.setTitle("Kontakte mit bestimmten Eigenschaftsauspraegungen");

		/*
		 * Anpassung an das Format des Datum
		 */
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");
		result.setCreated(simpleDateFormat.format(new Date()));

		/*
		 * Kopfzeile wird angelegt
		 */
		Row headline = new Row();

		/*
		 * Überschriften der jeweiligen Spalten in der Kopfzeile
		 */
		headline.addColumn(new Column("Kontakt"));
		headline.addColumn(new Column("Erzeugungsdatum"));
		headline.addColumn(new Column("Modifikationsdatum"));
		headline.addColumn(new Column("Eigenschaft"));
		headline.addColumn(new Column("Auspraegung"));
		result.addRow(headline);

		/*
		 * Es werden sämtliche Kontakte des Nutzer mit bestimmten Ausprägungen
		 * ausgelesen und anschließend in die Tabelle eingetragen
		 */
		for (Kontakt t : kontakt) {

			Vector<Eigenschaftauspraegung> vecAus = this.verwaltung.getAuspraegungByKontaktID(t.getID());

			for (Eigenschaftauspraegung ea : vecAus) {

				if (ea.getWert().equals(wert)) {

					Eigenschaft e = this.verwaltung.getEigenschaftByID(ea.getEigenschaftsID());

					// eine Leere zeile
					Row kontaktRow = new Row();

					kontaktRow.addColumn(new Column(String.valueOf(t.getName())));
					if (t.getStatus() == 0) {
						kontaktRow.addColumn(new Column(String.valueOf("Nicht Geteilt")));
					} else {
						kontaktRow.addColumn(new Column(String.valueOf("geteilt")));
					}
					kontaktRow.addColumn(
							new Column(String.valueOf(this.verwaltung.findNutzerByID(t.getNutzerID()).getEmail())));

					kontaktRow.addColumn(new Column(String.valueOf(e.getBezeichnung())));

					kontaktRow.addColumn(new Column(String.valueOf(ea.getWert())));

					result.addRow(kontaktRow);
				}
			}
		}

		/*
		 * Der fertige Report wird zurückgegeben
		 */
		return result;

	}

	/**
	 * Alle Nutzer des Systems werden ausgelesen
	 */
	public Vector<Nutzer> allNutzerReport() throws IllegalArgumentException {
		if (this.getAdministration() == null)
			return null;

		Vector<Nutzer> nutzer = this.verwaltung.findAllNutzer();

		return nutzer;

	}

	/**
	 * Der Nutzer wird in dieser Methode anhand seiner Email ausgelesen
	 * 
	 * @param email
	 */
	public Nutzer findNutzerByEmail(String email) throws IllegalArgumentException {

		return this.verwaltung.findNutzerByEmail(email);

	}

}
