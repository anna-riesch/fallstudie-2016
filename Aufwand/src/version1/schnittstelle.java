package version1;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Tabellen.Kompetenz;
import Tabellen.Mitarbeiterkompetenz;
import Tabellen.Projekt;
import Tabellen.Projektkompetenz;
import Tabellen.Projektphasen;
import Tabellen.Wert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class schnittstelle {

	Connection con = null;
	Statement stmt = null;
	Statement stmt2 = null;
	ResultSet rs;

	// test
	public schnittstelle() {
		try // Treiber für mySQL laden
		{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Treiber gefunden\n--------------------------------");
		} // try
		catch (ClassNotFoundException e) {
			System.out.println("Treiber NICHT gefunden\n--------------------------------");
		} // catch

		try // Connection zur Datenbank aufbauen
		{
			con = DriverManager.getConnection("jdbc:mysql://lolstats.org:3306/fallstudie_2016", "fallstudie_user2",
					"passwort123!");
			System.out.println("Connection zur Datenbank aufgebaut\n--------------------------------");
		} // try
		catch (SQLException e) {
			System.out.println("Connection zur Datenbank NICHT aufgebaut\n--------------------------------");
		} // catch

		try // Statement erzeugen
		{
			stmt = con.createStatement();
			stmt2 = con.createStatement();
			System.out.println("Statement erzeugt\n--------------------------------");
		} // try
		catch (SQLException e) {
			System.out.println("Statement NICHT erzeugt\n--------------------------------");
		} // catch
	}

	public List<Projekt> projekte_laden() throws SQLException {

		List<Projekt> projekte = new ArrayList<Projekt>();

		try {

			rs = stmt.executeQuery("select * from Projekt");

			while (rs.next()) {
				projekte.add(new Projekt(rs.getInt("ProjektID"), rs.getString("Name")));
			}

		} catch (SQLException e) {
			System.out.println("Fehler: " + e);
		}

		return projekte;

	}

	public List<Kompetenz> kompetenzen_laden() throws SQLException {

		List<Kompetenz> kompetenzen = new ArrayList<Kompetenz>();

		try {

			rs = stmt.executeQuery("select * from Kompetenz");

			while (rs.next()) {
				kompetenzen.add(new Kompetenz(rs.getInt("KompetenzID"), rs.getString("Name")));
			}

		} catch (SQLException e) {
			System.out.println("Fehler: " + e);
		}

		return kompetenzen;

	}

	public int projektname_aendern(int id, String neu) throws SQLException {

		int anzahl = 0;
		try {

			anzahl = stmt.executeUpdate("update Projekt set Name = '" + neu + "' where ProjektID = " + id);

		} catch (SQLException e) {
			System.out.println("Fehler: " + e);
		}

		return anzahl;
	}

	public int personentage_aendern(int wid, String neu) throws SQLException {

		int anzahl = 0;
		try {

			anzahl = stmt.executeUpdate("update Wert set Personentage = '" + neu + "' where WertID = " + wid);

		} catch (SQLException e) {
			System.out.println("Fehler: " + e);
		}

		return anzahl;
	}

	public int risikozuschlag_aendern(int wid, String neu) throws SQLException {

		int anzahl = 0;
		try {

			anzahl = stmt.executeUpdate("update Wert set Risikozuschlag = '" + neu + "' where WertID = " + wid);

		} catch (SQLException e) {
			System.out.println("Fehler: " + e);
		}

		return anzahl;
	}

	public int projekt_anlegen() throws SQLException {

		int anzahl = 0;
		try {

			anzahl = stmt.executeUpdate("insert into Projekt (Name) values ('')");

		} catch (SQLException e) {
			System.out.println("Fehler: " + e);
		}
		return anzahl;
	}

	public int projektphase_anlegen(int pid) throws SQLException {

		int geaendert = 0;
		int anzahl = 0;

		try {
			Calendar currenttime = Calendar.getInstance();
			Date sqldate = new Date((currenttime.getTime()).getTime());
			stmt.executeUpdate("insert into Projektphase (ProjektID, Startdatum, Enddatum, Name) values (" + pid + ",'"
					+ sqldate + "', '" + sqldate + "', '')");
			rs = stmt
					.executeQuery("select max(ProjektphasenID) as neueppid from Projektphase where ProjektID = " + pid);
			rs.next();
			int neueppid = rs.getInt("neueppid");

			/*
			 * rs = stmt.
			 * executeQuery("select count(*) as anzahl from Projektkompetenz where ProjektID = "
			 * + pid); rs.next(); int rows = rs.getInt("anzahl"); int[] werte =
			 * new int[rows];
			 */
			rs = stmt.executeQuery("select * from Projektkompetenz where ProjektID = " + pid);

			int wievielekomp = rs.getRow();

			while (rs.next()) {
				wievielekomp = rs.getRow();

				geaendert = stmt2.executeUpdate("insert into Wert (ProjektphasenID, ProjektkompetenzID) values ("
						+ neueppid + ", " + rs.getInt("ProjektkompetenzID") + ")");
				anzahl = anzahl + geaendert;
				// wievielekomp = rs.getRow();
			}

		} catch (SQLException e) {
			System.out.println("Fehler: " + e);
		}
		return anzahl;
	}

	public List<Mitarbeiterkompetenz> mitarbeiterkompetenzen_laden(int kid) throws SQLException {

		List<Mitarbeiterkompetenz> mitarbeiterkompetenzen = new ArrayList<Mitarbeiterkompetenz>();

		try {
			rs = stmt.executeQuery(
					"select Mitarbeiter.MitarbeiterID, Mitarbeiter.Name, Mitarbeiter.Kosten_pro_PT, Mitarbeiter.Zugehoerigkeit, Mitarbeiterkompetenz.MitarbeiterkompetenzID from Mitarbeiterkompetenz inner join Mitarbeiter on Mitarbeiterkompetenz.MitarbeiterID = Mitarbeiter.MitarbeiterID where Mitarbeiterkompetenz.KompetenzID = "
							+ kid);

			while (rs.next()) {
				mitarbeiterkompetenzen.add(new Mitarbeiterkompetenz(rs.getInt("MitarbeiterID"), rs.getString("Name"),
						rs.getInt("Kosten_pro_PT"), rs.getString("Zugehoerigkeit"),
						rs.getInt("MitarbeiterkompetenzID")));
			}
		} catch (SQLException e) {
			System.out.println("Fehler: " + e);
		}
		return mitarbeiterkompetenzen;

	}

	public List<Projektkompetenz> projektkompetenzen_laden(int pid) throws SQLException {

		List<Projektkompetenz> projektkompetenzen = new ArrayList<Projektkompetenz>();

		try {
			rs = stmt.executeQuery(
					"select Projektkompetenz.ProjektkompetenzID, Kompetenz.Name as kname, Mitarbeiter.Name as mname, Mitarbeiter.Kosten_pro_PT from Projektkompetenz inner join (Mitarbeiterkompetenz inner join Mitarbeiter on Mitarbeiter.MitarbeiterID = Mitarbeiterkompetenz.MitarbeiterID) on Projektkompetenz.MitarbeiterkompetenzID = Mitarbeiterkompetenz.MitarbeiterkompetenzID inner join Kompetenz on Kompetenz.KompetenzID = Projektkompetenz.KompetenzID where Projektkompetenz.ProjektID = "
							+ pid);
			while (rs.next()) {
				projektkompetenzen.add(new Projektkompetenz(rs.getInt("ProjektkompetenzID"), rs.getString("kname"),
						rs.getString("mname"), rs.getInt("Kosten_pro_PT")));
			}
		} catch (SQLException e) {
			System.out.println("Fehler: " + e);
		}
		return projektkompetenzen;

	}

	public int projektkompetenz_anlegen(int mkid, int pid, int kid) throws SQLException {

		int anzahl = 0;
		int geaendert = 0;
		try {

			anzahl = stmt.executeUpdate(
					"insert into Projektkompetenz (MitarbeiterkompetenzID, ProjektID, KompetenzID) values (" + mkid
							+ ", " + pid + ", " + kid + ")");

			rs = stmt.executeQuery(
					"select max(ProjektkompetenzID) as neuepkid from Projektkompetenz where MitarbeiterkompetenzID = "
							+ mkid);
			rs.next();
			int neuepkid = rs.getInt("neuepkid");

			rs = stmt.executeQuery("select * from Projektphase where ProjektID = " + pid);

			while (rs.next()) {

				geaendert = stmt2.executeUpdate("insert into Wert (ProjektphasenID, ProjektkompetenzID) values ("
						+ rs.getInt("ProjektphasenID") + ", " + neuepkid + ")");
				anzahl = anzahl + geaendert;
				// wievielekomp = rs.getRow();
			}

		} catch (SQLException e) {
			System.out.println("Fehler: " + e);
		}
		return anzahl;
	}

	public int projekt_loeschen(int id) throws SQLException {

		int anzahl = 0;
		try {

			stmt.executeUpdate(
					"delete from Wert where Wert.ProjektphasenID in (select ProjektphasenID from Projektphase where ProjektID = "
							+ id + ")");
			stmt.executeUpdate("delete from Projektphase where ProjektID = " + id);
			stmt.executeUpdate("delete from Projektkompetenz where ProjektID = " + id);
			anzahl = stmt.executeUpdate("delete from Projekt where ProjektID = " + id);

		} catch (SQLException e) {
			System.out.println("Fehler: " + e);
		}
		return anzahl;
	}

	public int projektkompetenz_loeschen(int pkid) throws SQLException {

		int anzahl = 0;
		try {

			anzahl = stmt.executeUpdate("delete from Wert where ProjektkompetenzID = " + pkid);
			anzahl = stmt.executeUpdate("delete from Projektkompetenz where ProjektkompetenzID = " + pkid);

		} catch (SQLException e) {
			System.out.println("Fehler: " + e);
		}
		return anzahl;
	}

	public int projektphase_loeschen(int phid) throws SQLException {

		int anzahl = 0;
		try {

			stmt.executeUpdate("delete from Wert where ProjektphasenID = " + phid);
			stmt.executeUpdate("delete from Projektphase where ProjektphasenID = " + phid);

		} catch (SQLException e) {
			System.out.println("Fehler: " + e);
		}
		return anzahl;
	}

	public List<Projektphasen> projektphasen_laden(int pid) throws SQLException {

		List<Projektphasen> projektphasen = new ArrayList<Projektphasen>();

		try {
			rs = stmt.executeQuery(
					"select ProjektphasenID, Name, Startdatum, Enddatum from Projektphase where ProjektID = " + pid
							+ " ORDER BY ProjektphasenID ASC");
			while (rs.next()) {
				projektphasen.add(new Projektphasen(rs.getInt("ProjektphasenID"), rs.getString("Name"),
						rs.getString("Startdatum"), rs.getString("Enddatum")));
			}
		} catch (SQLException e) {
			System.out.println("Fehler: " + e);
		}
		return projektphasen;

	}

	public ObservableList<String> phasen_laden() throws SQLException {

		ObservableList<String> phasen = FXCollections.observableArrayList();

		try {
			rs = stmt.executeQuery("select * from Phase order by Name ASC");
			while (rs.next()) {
				phasen.add(rs.getString("Name"));
			}
		} catch (SQLException e) {
			System.out.println("Fehler: " + e);
		}
		return phasen;

	}

	public int phase_anlegen(String neu, int phid) throws SQLException {

		int anzahl = 0;
		try {

			anzahl = stmt.executeUpdate("insert into Phase (Name) values ('" + neu + "')");
			rs = stmt.executeQuery("select max(PhasenID) as neuepid from Phase");
			rs.next();
			int neuepid = rs.getInt("neuepid");
			anzahl = stmt.executeUpdate(
					"UPDATE Projektphase set PhasenID = " + neuepid + " where ProjektphasenID = " + phid);

		} catch (SQLException e) {
			System.out.println("Fehler: " + e);
		}
		return anzahl;

	}

	public int phasenname_aendern(String neu, int phid) throws SQLException {

		int anzahl = 0;
		try {

			anzahl = stmt.executeUpdate("UPDATE Projektphase set Name = '" + neu + "' where ProjektphasenID = " + phid);

		} catch (SQLException e) {
			System.out.println("Fehler: " + e);
		}
		return anzahl;

	}

	public int phasenname_aendern(int phid, String nameneu) throws SQLException {

		int anzahl = 0;
		try {

			anzahl = stmt.executeUpdate("UPDATE Projektphase set PhasenID = (select PhasenID from Phase where Name = '"
					+ nameneu + "') where ProjektphasenID = " + phid);

		} catch (SQLException e) {
			System.out.println("Fehler: " + e);
		}

		return anzahl;
	}

	public int startdatum_aendern(int phid, String datumneu) throws SQLException {

		int anzahl = 0;
		try {

			anzahl = stmt.executeUpdate(
					"UPDATE Projektphase set Startdatum = '" + datumneu + "' where ProjektphasenID = " + phid);

		} catch (SQLException e) {
			System.out.println("Fehler: " + e);
		}

		return anzahl;
	}

	public int enddatum_aendern(int phid, String datumneu) throws SQLException {

		int anzahl = 0;
		try {

			anzahl = stmt.executeUpdate(
					"UPDATE Projektphase set Enddatum = '" + datumneu + "' where ProjektphasenID = " + phid);

		} catch (SQLException e) {
			System.out.println("Fehler: " + e);
		}

		return anzahl;
	}

	public List<Wert> werte_laden(int phid) throws SQLException {

		List<Wert> werte = new ArrayList<Wert>();

		try {
			rs = stmt.executeQuery(
					"select Wert.WertID, Wert.Risikozuschlag, Wert.Personentage, Wert.Wert, Kompetenz.Name, Projektkompetenz.Auslastung, (Mitarbeiter.Kosten_pro_PT*Wert.Personentage*(Projektkompetenz.Auslastung/100)*(1+(Wert.Risikozuschlag/100))) as 'betrag', Mitarbeiter.Name as MName, Mitarbeiter.Zugehoerigkeit from Wert inner join (Projektkompetenz inner join (Mitarbeiterkompetenz inner join Mitarbeiter on Mitarbeiter.MitarbeiterID = Mitarbeiterkompetenz.MitarbeiterID) on Projektkompetenz.MitarbeiterkompetenzID = Mitarbeiterkompetenz.MitarbeiterkompetenzID inner join Kompetenz on Kompetenz.KompetenzID = Projektkompetenz.KompetenzID) on Wert.ProjektkompetenzID = Projektkompetenz.ProjektkompetenzID where Wert.ProjektphasenID = "
							+ phid + " ORDER BY Kompetenz.Name ASC");
			while (rs.next()) {
				werte.add(new Wert(rs.getInt("WertID"), rs.getString("Name"), rs.getInt("Risikozuschlag"),
						rs.getInt("Personentage"), rs.getInt("Auslastung"), rs.getDouble("betrag"),
						rs.getString("MName"), rs.getString("Zugehoerigkeit")));
			}
		} catch (SQLException e) {
			System.out.println("Fehler: " + e);
		}
		return werte;

	}

}
