package Tabellen;

import javafx.beans.property.*;

public class Kompetenz {
	
	private IntegerProperty kid;
	private StringProperty name;
	
	public Kompetenz(int kid, String name){
		this.setkid(kid);
		this.setname(name);
 
	}
	

	
	public int getkid() {
		return kid.get();
	}
	
	public IntegerProperty getkidProperty() {
		return kid;
	}
	public void setkid(int kid) {
		this.kid = new SimpleIntegerProperty(kid);
	}


	
	public String getname() {
		return name.get();
	}
	
	public StringProperty getnameProperty() {
		return name;
	}
	public void setname(String name) {
		this.name = new SimpleStringProperty(name);
	}
	
}
