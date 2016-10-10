package Tabellen;

import javafx.beans.property.*;


public class Mitarbeiterkompetenz {
	
	
	private IntegerProperty mkid;
	private StringProperty kompetenz;
	private StringProperty mitarbeitername;
	private IntegerProperty kosten;
	
	public Mitarbeiterkompetenz(int mkid, String kompetenz, String mitarbeitername, int kosten){
		this.setkompetenz(kompetenz);
		this.setkosten(kosten);
		this.setmitarbeitername(mitarbeitername);
		this.setMkid(mkid);
	}
	
	
	
	public int getMkid() {
		return mkid.get();
	}
	
	public IntegerProperty getMkidProperty() {
		return mkid;
	}
	public void setMkid(int mkid) {
		this.mkid = new SimpleIntegerProperty(mkid);
	}


	
	public String getkompetenz() {
		return kompetenz.get();
	}
	
	public StringProperty getkompetenzProperty() {
		return kompetenz;
	}
	public void setkompetenz(String kompetenz) {
		this.kompetenz = new SimpleStringProperty(kompetenz);
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
