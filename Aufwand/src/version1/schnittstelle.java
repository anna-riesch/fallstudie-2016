package version1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class schnittstelle {

	Connection con = null;
	Statement stmt = null;
	ResultSet rs;

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
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/personal", "root", "");
			System.out.println("Connection zur Datenbank aufgebaut\n--------------------------------");
		} // try
		catch (SQLException e) {
			System.out.println("Connection zur Datenbank NICHT aufgebaut\n--------------------------------");
		} // catch

		try // Statement erzeugen
		{
			stmt = con.createStatement();
			System.out.println("Statement erzeugt\n--------------------------------");
		} // try
		catch (SQLException e) {
			System.out.println("Statement NICHT erzeugt\n--------------------------------");
		} // catch
	}

	public ResultSet projekte_laden() {

		try {
			// List l1 = new LinkedList();
			rs = stmt.executeQuery("select * from projekte order by asc");
		} catch (SQLException e) {
			System.out.println("Fehler: " + e);
		}
		return rs;

	}

}
