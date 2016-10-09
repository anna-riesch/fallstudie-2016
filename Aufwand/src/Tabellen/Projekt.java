package Tabellen;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Projekt {
	
	private IntegerProperty projektid;
	private StringProperty projektname;
	
	public Projekt(int projektid, String projektname){
		this.setProjektid(projektid);
		this.setProjektname(projektname);
	}

	public int getProjektid() {
		return projektid.get();
	}
	
	public IntegerProperty getProjektidProperty() {
		return projektid;
	}

	public void setProjektid(int projektid) {
		this.projektid = new SimpleIntegerProperty(projektid);
	}

	public String getProjektname() {
		return projektname.get();
	}
	
	public StringProperty getProjektnameProperty() {
		return projektname;
	}

	public void setProjektname(String projektname) {
		this.projektname = new SimpleStringProperty(projektname);
	}
		
}

