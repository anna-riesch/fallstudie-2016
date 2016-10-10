package Tabellen;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Projektkompetenz {

	private IntegerProperty projektkompetenzID;
	private StringProperty kompetenzname;
	private StringProperty mitarbeitername;
	private IntegerProperty kosten;

	public Projektkompetenz(int projektkompetenzID, String kompetenzname, String mitarbeitername, int kosten) {

		this.setprojektkompetenzID(projektkompetenzID);
		this.setkompetenzname(kompetenzname);
		this.setmitarbeitername(mitarbeitername);
		this.setkosten(kosten);
	}

	public int getprojektkompetenzID() {
		return projektkompetenzID.get();
	}

	public IntegerProperty getprojektkompetenzIDProperty() {
		return projektkompetenzID;
	}

	public void setprojektkompetenzID(int projektkompetenzID) {
		this.projektkompetenzID = new SimpleIntegerProperty(projektkompetenzID);
	}

	public String getkompetenzname() {
		return kompetenzname.get();
	}

	public StringProperty getkompetenznameProperty() {
		return kompetenzname;
	}

	public void setkompetenzname(String kompetenzname) {
		this.kompetenzname = new SimpleStringProperty(kompetenzname);
	}

	public String getmitarbeitername() {
		return mitarbeitername.get();
	}

	public StringProperty getmitarbeiternameProperty() {
		return mitarbeitername;
	}

	public void setmitarbeitername(String mitarbeitername) {
		this.mitarbeitername = new SimpleStringProperty(mitarbeitername);
	}

	public int getkosten() {
		return kosten.get();
	}

	public IntegerProperty getkostenProperty() {
		return kosten;
	}

	public void setkosten(int kosten) {
		this.kosten = new SimpleIntegerProperty(kosten);
	}

}
