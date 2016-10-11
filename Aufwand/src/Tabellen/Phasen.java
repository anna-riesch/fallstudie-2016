package Tabellen;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Phasen {

	private StringProperty phasenname;

	public Phasen(String phasenname) {


		this.setphasenname(phasenname);
		
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
