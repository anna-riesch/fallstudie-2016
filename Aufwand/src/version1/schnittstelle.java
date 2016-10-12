package version1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Tabellen.Kompetenz;
import Tabellen.Mitarbeiterkompetenz;
import Tabellen.Projekt;
import Tabellen.Projektkompetenz;
import Tabellen.Projektphasen;
import Tabellen.Wert;

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

			stmt.executeUpdate("insert into Projektphase (ProjektID, PhasenID, Startdatum, Enddatum) values (" + pid
					+ ", 1, '2016-01-01', '2016-01-01')");
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
				int aktuellepid = rs.getInt("ProjektkompetenzID");
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

	public int projekt_loeschen(int id) throws SQLException {

		int anzahl = 0;
		try {

			anzahl = stmt.executeUpdate("delete from Projekt where ProjektID = " + id);

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

	public List<Mitarbeiterkompetenz> mitarbeiterkompetenzen_laden(int kid) throws SQLException {

		List<Mitarbeiterkompetenz> mitarbeiterkompetenzen = new ArrayList<Mitarbeiterkompetenz>();

		try {
			rs = stmt.executeQuery(
					"select Mitarbeiter.MitarbeiterID, Mitarbeiter.Name, Mitarbeiter.Kosten_pro_PT, Mitarbeiter.MAK, Mitarbeiter.Zugehoerigkeit, Mitarbeiterkompetenz.MitarbeiterkompetenzID from Mitarbeiterkompetenz inner join Mitarbeiter on Mitarbeiterkompetenz.MitarbeiterID = Mitarbeiter.MitarbeiterID where Mitarbeiterkompetenz.KompetenzID = "
							+ kid);

			while (rs.next()) {
				mitarbeiterkompetenzen.add(new Mitarbeiterkompetenz(rs.getInt("MitarbeiterID"), rs.getString("Name"),
						rs.getInt("Kosten_pro_PT"), rs.getInt("MAK"), rs.getString("Zugehoerigkeit"),
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
		try {

			anzahl = stmt.executeUpdate(
					"insert into Projektkompetenz (MitarbeiterkompetenzID, ProjektID, KompetenzID) values (" + mkid
							+ ", " + pid + ", " + kid + ")");

		} catch (SQLException e) {
			System.out.println("Fehler: " + e);
		}
		return anzahl;
	}

	public int projektkompetenz_loeschen(int pkid) throws SQLException {

		int anzahl = 0;
		try {

			anzahl = stmt.executeUpdate("delete from Projektkompetenz where ProjektkompetenzID = " + pkid);

		} catch (SQLException e) {
			System.out.println("Fehler: " + e);
		}
		return anzahl;
	}

	public List<Projektphasen> projektphasen_laden(int pid) throws SQLException {

		List<Projektphasen> projektphasen = new ArrayList<Projektphasen>();

		try {
			rs = stmt.executeQuery(
					"select Projektphase.ProjektphasenID, Phase.Name, Projektphase.Startdatum, Projektphase.Enddatum from Projektphase inner join Phase on Phase.PhasenID = Projektphase.PhasenID where Projektphase.ProjektID = "
							+ pid + " ORDER BY Projektphase.ProjektphasenID ASC");
			while (rs.next()) {
				projektphasen.add(new Projektphasen(rs.getInt("ProjektphasenID"), rs.getString("Name"),
						rs.getString("Startdatum"), rs.getString("Enddatum")));
			}
		} catch (SQLException e) {
			System.out.println("Fehler: " + e);
		}
		return projektphasen;

	}

	public List<String> phasen_laden() throws SQLException {

		List<String> phasen = new ArrayList<String>();

		try {
			rs = stmt.executeQuery("select * from Phase");
			while (rs.next()) {
				phasen.add(new String(rs.getString("Name")));
			}
		} catch (SQLException e) {
			System.out.println("Fehler: " + e);
		}
		return phasen;

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
					"select Wert.WertID, Kompetenz.Name, Wert.Risikozuschlag, Wert.Personentage, Wert.Wert from Wert inner join (Projektkompetenz inner join Kompetenz on Kompetenz.KompetenzID = Projektkompetenz.KompetenzID) on Wert.ProjektkompetenzID = Projektkompetenz.ProjektkompetenzID where Wert.ProjektphasenID = "
							+ phid);
			while (rs.next()) {
				werte.add(new Wert(rs.getInt("WertID"), rs.getString("Name"), rs.getInt("Risikozuschlag"),
						rs.getInt("Personentage"), rs.getDouble("Wert")));
			}
		} catch (SQLException e) {
			System.out.println("Fehler: " + e);
		}
		return werte;

	}

}
