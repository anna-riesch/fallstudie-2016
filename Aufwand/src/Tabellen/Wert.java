package Tabellen;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Wert {

	private IntegerProperty wertid;
	private StringProperty kompetenzname;
	private IntegerProperty risikozuschlag;
	private IntegerProperty personentage;
	private DoubleProperty wert;

	public Wert(int wertid, String kompetenzname, int risikozuschlag, int personentage, double wert) {

		this.setwertid(wertid);
		this.setkompetenzname(kompetenzname);
		this.setrisikozuschlag(risikozuschlag);
		this.setpersonentage(personentage);
		this.setwert(wert);

	}

	public int getwertid() {
		return wertid.get();
	}

	public IntegerProperty getwertidProperty() {
		return wertid;
	}

	public void setwertid(int wertid) {
		this.wertid = new SimpleIntegerProperty(wertid);
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

	public int getrisikozuschlag() {
		return risikozuschlag.get();
	}

	public IntegerProperty getrisikozuschlagProperty() {
		return risikozuschlag;
	}

	public void setrisikozuschlag(int risikozuschlag) {
		this.risikozuschlag = new SimpleIntegerProperty(risikozuschlag);
	}

	public int getpersonentage() {
		return personentage.get();
	}

	public IntegerProperty getpersonentageProperty() {
		return personentage;
	}

	public void setpersonentage(int personentage) {
		this.personentage = new SimpleIntegerProperty(personentage);
	}

	public double getwert() {
		return wert.get();
	}

	public DoubleProperty getwertProperty() {
		return wert;
	}

	public void setwert(double wert) {
		this.wert = new SimpleDoubleProperty(wert);
	}

}
