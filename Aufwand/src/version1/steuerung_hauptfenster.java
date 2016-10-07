package version1;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.LinkedList;

public class steuerung_hauptfenster {

	schnittstelle ss = new schnittstelle();
	boolean projekte_geladen = projekte_laden();

	public boolean projekte_laden() {

		ResultSet rs = ss.projekte_laden();
		List ll = new LinkedList();
		ResultSetMetaData metadata = rs.getMetaData();
		int spalten = metadata.getColumnCount();
		while (rs.next()) {

			dataClass zeile = new dataClass();

			ll.add(zeile);

		}

		return true;

	}

}
