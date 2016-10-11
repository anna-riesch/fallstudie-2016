package Tabellen;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Projektphasen {

	private IntegerProperty projektphasenID;
	private StringProperty phasenname;

	public Projektphasen(int projektphasenID, String phasenname) {

		this.setprojektphasenID(projektphasenID);
		this.setphasenname(phasenname);

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

}
