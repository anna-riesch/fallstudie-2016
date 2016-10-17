package Tabellen;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Mitarbeiterkompetenz {

	private IntegerProperty mitarbeiterid;
	private StringProperty mitarbeitername;
	private IntegerProperty kosten;
	private IntegerProperty mak;
	private StringProperty zugehoerigkeit;
	private IntegerProperty mkid;

	public Mitarbeiterkompetenz(int mitarbeiterid, String mitarbeitername, int kosten, int mak, String zugehoerigkeit,
			int mkid) {

		this.setkosten(kosten);
		this.setmitarbeitername(mitarbeitername);
		this.setMkid(mkid);
		this.setmitarbeiterid(mitarbeiterid);
		this.setmak(mak);
		this.setzugehoerigkeit(zugehoerigkeit);
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

	public String getmitarbeitername() {
		return mitarbeitername.get();
	}

	public StringProperty getmitarbeiternameProperty() {
		return mitarbeitername;
	}

	public void setmitarbeitername(String mitarbeitername) {
		this.mitarbeitername = new SimpleStringProperty(mitarbeitername);
	}

	public String getzugehoerigkeit() {
		return zugehoerigkeit.get();
	}

	public StringProperty getzugehoerigkeitProperty() {
		return zugehoerigkeit;
	}

	public void setzugehoerigkeit(String zugehoerigkeit) {
		this.zugehoerigkeit = new SimpleStringProperty(zugehoerigkeit);
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

	public int getmitarbeiterid() {
		return mitarbeiterid.get();
	}

	public IntegerProperty getmitarbeiteridProperty() {
		return mitarbeiterid;
	}

	public void setmitarbeiterid(int mitarbeiterid) {
		this.mitarbeiterid = new SimpleIntegerProperty(mitarbeiterid);
	}

	public int getmak() {
		return mak.get();
	}

	public IntegerProperty getmakProperty() {
		return mak;
	}

	public void setmak(int mak) {
		this.mak = new SimpleIntegerProperty(mak);
	}

}
