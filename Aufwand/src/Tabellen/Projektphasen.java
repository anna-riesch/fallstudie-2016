package Tabellen;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Projektphasen {

	private IntegerProperty projektphasenID;
	private StringProperty phasenname;
	private StringProperty startdatum;
	private StringProperty enddatum;

	public Projektphasen(int projektphasenID, String phasenname, String startdatum, String enddatum) {

		this.setprojektphasenID(projektphasenID);
		this.setphasenname(phasenname);
		this.setstartdatum(startdatum);
		this.setenddatum(enddatum);

	}

	public int getprojektphasenID() {
		return projektphasenID.get();
	}

	public IntegerProperty getprojektphasenIDProperty() {
		return projektphasenID;
	}

	public void setprojektphasenID(int projektphasenID) {
		this.projektphasenID = new SimpleIntegerProperty(projektphasenID);
	}

	public String getphasenname() {
		return phasenname.get();
	}

	public StringProperty getphasennameProperty() {
		return phasenname;
	}

	public void setphasenname(String phasenname) {
		this.phasenname = new SimpleStringProperty(phasenname);
	}
	
	
	
	
	
	public String getstartdatum() {
		return startdatum.get();
	}

	public StringProperty getstartdatumProperty() {
		return startdatum;
	}

	public void setstartdatum(String startdatum) {
		this.startdatum = new SimpleStringProperty(startdatum);
	}
	
	
	
	
	
	public String getenddatum() {
		return enddatum.get();
	}

	public StringProperty getenddatumProperty() {
		return enddatum;
	}

	public void setenddatum(String enddatum) {
		this.enddatum = new SimpleStringProperty(enddatum);
	}
	
	

}
