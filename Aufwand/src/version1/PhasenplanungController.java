package version1;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import Tabellen.Phasen;
import Tabellen.Projektphasen;
import Tabellen.Wert;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

public class PhasenplanungController {
	@FXML
	private Label label_projekt;
	@FXML
	private TableView<Projektphasen> tbl_phasenTabelle;
	@FXML
	private TableColumn<Projektphasen, Integer> tblCell_phasenID;
	@FXML
	private TableColumn<Projektphasen, String> tblCell_phasenname;
	@FXML
	private TableColumn<Projektphasen, String> tblCell_startdatum;
	@FXML
	private TableColumn<Projektphasen, String> tblCell_enddatum;
	@FXML
	private ComboBox<String> choice_phasenname;
	@FXML
	private TextField text_pt;
	@FXML
	private TextField text_rz;
	@FXML
	private TextField text_phasenname;
	@FXML
	private Button button_phase_anlegen;
	@FXML
	private Button button_phase_loeschen;
	@FXML
	private DatePicker datum_start;
	@FXML
	private DatePicker datum_ende;
	@FXML
	private Button button_pt_berechnen;
	@FXML
	private TableView<Wert> tbl_kompetenzTabelle;
	@FXML
	private TableColumn<Wert, Integer> tblCell_kompID;
	@FXML
	private TableColumn<Wert, String> tblCell_kompname;
	@FXML
	private TableColumn<Wert, String> tblCell_mitarbeitername;
	@FXML
	private TableColumn<Wert, String> tblCell_zugehoerigkeit;
	@FXML
	private TableColumn<Wert, Integer> tblCell_komprisiko;
	@FXML
	private TableColumn<Wert, Integer> tblCell_komppt;
	@FXML
	private TableColumn<Wert, Integer> tblCell_puffer;

	@FXML
	private TableColumn<Wert, Double> tblCell_kompk;

	private ObservableList<Projektphasen> ppData;
	private ObservableList<Phasen> pData;
	private ObservableList<String> pDataString;
	private ObservableList<Wert> wData;

	private schnittstelle db;
	public static int projektid;
	public static boolean geklickt = true;

	@FXML
	private void initialize() throws SQLException {

		System.out.println("Phasenplanung startet");

		projektid = OpenPhasenStage.getpid();

		System.out.println("Projekt-ID abgespeichert: " + projektid);

		choice_phasenname.setTooltip(new Tooltip("Projektphasenname auswählen"));

		db = new schnittstelle();

		this.phase_aktualisieren();

		tbl_phasenTabelle.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				try {

					System.out.println("tbl_phasenTabelle_action");

					phase_geklickt(tbl_phasenTabelle.getSelectionModel().getSelectedItem());

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		/*
		 * choice_phasenname.getSelectionModel().selectedItemProperty().
		 * addListener((observable, oldValue, newValue) -> { try {
		 * choice_phasenname_auswahl(choice_phasenname.getValue()); } catch
		 * (SQLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } });
		 */

		tbl_kompetenzTabelle.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				try {

					System.out.println("tbl_kompetenzTabelle_action");

					tbl_kompetenzTabelle_geklickt(tbl_kompetenzTabelle.getSelectionModel().getSelectedItem());

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}

	public void phase_aktualisieren() throws SQLException {
		System.out.println("phase_aktualisieren");
		ppData = FXCollections.observableArrayList(db.projektphasen_laden(projektid));
		tblCell_phasenID.setCellValueFactory(cellData -> cellData.getValue().getprojektphasenIDProperty().asObject());
		tblCell_phasenname.setCellValueFactory(cellData -> cellData.getValue().getphasennameProperty());
		tblCell_startdatum.setCellValueFactory(cellData -> cellData.getValue().getstartdatumProperty());
		tblCell_enddatum.setCellValueFactory(cellData -> cellData.getValue().getenddatumProperty());

		tbl_phasenTabelle.setItems(ppData);

	}

	@FXML
	public void phase_geklickt(Projektphasen p) throws SQLException {
		System.out.println("phase_geklickt");

		wData = FXCollections.observableArrayList(
				db.werte_laden(tbl_phasenTabelle.getSelectionModel().getSelectedItem().getprojektphasenID()));

		tblCell_kompID.setCellValueFactory(cellData -> cellData.getValue().getwertidProperty().asObject());
		tblCell_kompname.setCellValueFactory(cellData -> cellData.getValue().getkompetenznameProperty());
		tblCell_mitarbeitername.setCellValueFactory(cellData -> cellData.getValue().getmitarbeiternameProperty());
		tblCell_zugehoerigkeit.setCellValueFactory(cellData -> cellData.getValue().getzugehoerigkeitProperty());
		tblCell_komprisiko.setCellValueFactory(cellData -> cellData.getValue().getrisikozuschlagProperty().asObject());
		tblCell_komppt.setCellValueFactory(cellData -> cellData.getValue().getpersonentageProperty().asObject());
		tblCell_puffer.setCellValueFactory(cellData -> cellData.getValue().getpufferProperty().asObject());

		tblCell_kompk.setCellValueFactory(cellData -> cellData.getValue().getwertProperty().asObject());

		tbl_kompetenzTabelle.setItems(wData);

		text_phasenname.setText(p.getphasenname());

		LocalDate startdt = LocalDate.parse(p.getstartdatum());
		LocalDate enddt = LocalDate.parse(p.getenddatum());

		datum_start.setValue(startdt);
		datum_ende.setValue(enddt);

		// pDataString = db.phasen_laden();
		// choice_phasenname.setItems(pDataString);
		// choice_phasenname.setValue(tbl_phasenTabelle.getSelectionModel().getSelectedItem().getphasenname());

		// this.kompetenzen_aktualisieren(tbl_phasenTabelle.getSelectionModel().getSelectedItem().getprojektphasenID());

		/*
		 * choice_phasenname.setValue(null);
		 * choice_phasenname.getItems().clear();
		 * 
		 * choice_phasenname.setValue(phasenname); pData =
		 * FXCollections.observableArrayList(db.phasen_laden());
		 * 
		 * String array[] = new String[pData.size()]; for (int j = 0; j <
		 * pData.size(); j++) { array[j] = pData.get(j); }
		 * 
		 * for (String k : array) { choice_phasenname.getItems().add(k); }
		 * 
		 * LocalDate sdt =
		 * LocalDate.parse(tbl_phasenTabelle.getSelectionModel().getSelectedItem
		 * ().getstartdatum()); LocalDate edt =
		 * LocalDate.parse(tbl_phasenTabelle.getSelectionModel().getSelectedItem
		 * ().getenddatum());
		 * 
		 * datum_start.setValue(sdt); datum_ende.setValue(edt); int test =
		 * tbl_phasenTabelle.getSelectionModel().getSelectedItem().
		 * getprojektphasenID(); geklickt = false;
		 * 
		 * this.kompetenzen_aktualisieren(test);
		 */

	}

	public void choice_phasenname_auswahl() throws SQLException {
		System.out.println("choice_phasenname_auswahl");

		if (geklickt == true) {
			if (!choice_phasenname.getValue().toString()
					.equals(tbl_phasenTabelle.getSelectionModel().getSelectedItem().getphasenname().toString())
					&& !choice_phasenname.getValue().toString().equals(null)) {
				System.out.println("JETZT ÄNDERN");

			} else {
				System.out.println("JETZT NICHT ÄNDERN");
			}
		}
		geklickt = true;

		// if (phasenname != null
		// && phasenname !=
		// tbl_phasenTabelle.getSelectionModel().getSelectedItem().getphasenname())
		// {
		// System.out.println(choice_phasenname.getValue());
		// System.out.println("ES WIRD EIN PHASENNAME GEÄNDERT");
		// int anzahl = db.phasenname_aendern(
		// tbl_phasenTabelle.getSelectionModel().getSelectedItem().getprojektphasenID(),
		// phasenname);
		// System.out.println(phasenname + anzahl);
		// geklickt = false;
		//
		// }

	}

	@FXML
	public void phasen_aktualisieren(int pos) throws SQLException {
		System.out.println("phasen_aktualisieren");

		ppData = FXCollections.observableArrayList(db.projektphasen_laden(projektid));

		tblCell_phasenID.setCellValueFactory(cellData -> cellData.getValue().getprojektphasenIDProperty().asObject());
		tblCell_phasenname.setCellValueFactory(cellData -> cellData.getValue().getphasennameProperty());
		tblCell_startdatum.setCellValueFactory(cellData -> cellData.getValue().getstartdatumProperty());
		tblCell_enddatum.setCellValueFactory(cellData -> cellData.getValue().getenddatumProperty());

		// datum_start.setValue(ppData.g);

		// Platform.runLater(new Runnable() {
		// @Override
		// public void run() {
		tbl_phasenTabelle.requestFocus();
		tbl_phasenTabelle.getSelectionModel().select(pos);
		// tbl_phasenTabelle.getFocusModel().focus(pos);
		// }
		// });
		// test
		tbl_phasenTabelle.setItems(ppData);

	}

	@FXML
	public void datum_start_geklickt(ActionEvent event) throws SQLException {
		System.out.println("datum_start_geklickt");
		geklickt = false;

		if (datum_start.getValue().toString() != null && !tbl_phasenTabelle.getSelectionModel().getSelectedItem()
				.getstartdatum().toString().equals(datum_start.getValue().toString())) {

			int anzahl = db.startdatum_aendern(
					tbl_phasenTabelle.getSelectionModel().getSelectedItem().getprojektphasenID(),
					datum_start.getValue().toString());
			System.out.println(anzahl + "START - Datensätze geändert.");
			int pos = tbl_phasenTabelle.getSelectionModel().getSelectedIndex();
			this.phase_aktualisieren();
			tbl_phasenTabelle.requestFocus();
			tbl_phasenTabelle.getSelectionModel().select(pos);
		}
	}

	@FXML
	public void datum_ende_geklickt(ActionEvent event) throws SQLException {
		System.out.println("datum_ende_geklickt");
		geklickt = false;

		if (datum_ende.getValue().toString() != null && !tbl_phasenTabelle.getSelectionModel().getSelectedItem()
				.getenddatum().toString().equals(datum_ende.getValue().toString())) {

			int anzahl = db.enddatum_aendern(
					tbl_phasenTabelle.getSelectionModel().getSelectedItem().getprojektphasenID(),
					datum_ende.getValue().toString());
			System.out.println(anzahl + "END - Datensätze geändert.");
			int pos = tbl_phasenTabelle.getSelectionModel().getSelectedIndex();
			this.phase_aktualisieren();
			tbl_phasenTabelle.requestFocus();
			tbl_phasenTabelle.getSelectionModel().select(pos);

		}
	}

	public void kompetenzen_aktualisieren(int phid) throws SQLException {
		System.out.println("kompetenzen_aktualisieren");

		wData = FXCollections.observableArrayList(
				db.werte_laden(tbl_phasenTabelle.getSelectionModel().getSelectedItem().getprojektphasenID()));

		tblCell_kompID.setCellValueFactory(cellData -> cellData.getValue().getwertidProperty().asObject());
		tblCell_kompname.setCellValueFactory(cellData -> cellData.getValue().getkompetenznameProperty());
		tblCell_komprisiko.setCellValueFactory(cellData -> cellData.getValue().getrisikozuschlagProperty().asObject());
		tblCell_komppt.setCellValueFactory(cellData -> cellData.getValue().getpersonentageProperty().asObject());

		tblCell_puffer.setCellValueFactory(cellData -> cellData.getValue().getpufferProperty().asObject());
		tblCell_kompk.setCellValueFactory(cellData -> cellData.getValue().getwertProperty().asObject());
		geklickt = false;
		tbl_kompetenzTabelle.setItems(wData);

	}

	public void tbl_kompetenzTabelle_geklickt(Wert w) throws SQLException {
		System.out.println("tbl_kompetenzTabelle_geklickt");

		text_pt.setText(String.valueOf(w.getpersonentage()));
		text_rz.setText(String.valueOf(w.getrisikozuschlag()));

	}

	@FXML
	public void text_personentage_geklickt(ActionEvent event) throws SQLException {
		System.out.println("text_personentage_geklickt");
	}

	@FXML
	public void text_risikozuschlag_geklickt(ActionEvent event) throws SQLException {
		System.out.println("text_risikozuschlag_geklickt");
	}

	@FXML
	public void werte_uebernehmen(ActionEvent event) throws SQLException, ParseException {
		System.out.println("werte_uebernehmen");
		geklickt = false;
		int pos = tbl_kompetenzTabelle.getSelectionModel().getSelectedIndex();

		// HIER MUSS EXTERNE PUFFER REIN
		int puffer = 0;

		if (tbl_kompetenzTabelle.getSelectionModel().getSelectedItem().getzugehoerigkeit().equals("extern")) {
			int ptgesamt = pt_berechnen();
			int eingabe = Integer.parseInt(text_pt.getText());
			puffer = ptgesamt - eingabe;
		}

		db.personentage_aendern(tbl_kompetenzTabelle.getSelectionModel().getSelectedItem().getwertid(),
				text_pt.getText(), puffer);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				tbl_kompetenzTabelle.requestFocus();
				tbl_kompetenzTabelle.getSelectionModel().select(pos);
				// tbl_phasenTabelle.getFocusModel().focus(pos);
			}
		});

		db.risikozuschlag_aendern(tbl_kompetenzTabelle.getSelectionModel().getSelectedItem().getwertid(),
				text_rz.getText());

		this.kompetenzen_aktualisieren(tbl_phasenTabelle.getSelectionModel().getSelectedItem().getprojektphasenID());

		// wData.get(tbl_kompetenzTabelle.getSelectionModel().getSelectedIndex())
		// .setrisikozuschlag(Integer.parseInt(text_rz.getText()));
		// wData.get(tbl_kompetenzTabelle.getSelectionModel().getSelectedIndex())
		// .setpersonentage(Integer.parseInt(text_pt.getText()));
		tbl_kompetenzTabelle.refresh();

	}

	@FXML
	public void button_phase_anlegen_click(ActionEvent event) throws SQLException {
		System.out.println("button_phase_anlegen_click");

		int anzahl = db.projektphase_anlegen(projektid);
		System.out.println(anzahl + " Werte angelegt");

		// wData = FXCollections.observableArrayList(db.werte_laden(projektid));
		// tbl_kompetenzTabelle.setItems(wData);
		System.out.println("WIE VIELE GIBTS GRAD?" + tbl_phasenTabelle.getItems().size());
		int pos = tbl_phasenTabelle.getItems().size();
		this.phase_aktualisieren();
		tbl_phasenTabelle.requestFocus();
		tbl_phasenTabelle.getSelectionModel().select(pos);
		// this.kompetenzen_aktualisieren(tbl_phasenTabelle.getSelectionModel().getSelectedItem().getprojektphasenID());
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					phase_geklickt(tbl_phasenTabelle.getSelectionModel().getSelectedItem());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		// wData.clear();
		text_phasenname.setText("");
		geklickt = false;
		// pDataString.clear();

	}

	@FXML
	public void button_phase_loeschen_click(ActionEvent event) throws SQLException {
		System.out.println("button_phase_loeschen_click");
		geklickt = false;
		System.out.println(tbl_phasenTabelle.getSelectionModel().getSelectedItem().getprojektphasenID());
		db.projektphase_loeschen(tbl_phasenTabelle.getSelectionModel().getSelectedItem().getprojektphasenID());
		System.out.println("WIE VIELE GIBTS GRAD?" + tbl_phasenTabelle.getItems().size());
		System.out.println(tbl_phasenTabelle.getItems().size());
		tbl_phasenTabelle.requestFocus();
		geklickt = false;
		this.phase_aktualisieren();
		wData.clear();
		text_phasenname.setText("");
		geklickt = false;
		// pDataString.clear();

		// this.phasen_aktualisieren(tbl_phasenTabelle.getItems().size() - 2);

	}

	@FXML
	public void button_pt_berechnen_click(ActionEvent event) throws SQLException, ParseException {

		System.out.println("button_pt_berechnen_click");

		// Aktuelle Position abrufen
		int pos = tbl_kompetenzTabelle.getSelectionModel().getSelectedIndex();

		// int tageint = toIntExact(taged);
		// int wochen = tageint / 7;

		int tageint = pt_berechnen();

		// in String konvertieren
		String ptneu = String.valueOf(tageint);

		// Neuer Personentag-Wert in Datenbank einfügen
		db.personentage_aendern(tbl_kompetenzTabelle.getSelectionModel().getSelectedItem().getwertid(), ptneu, 0);

		// GUI aktualisieren
		geklickt = false;
		int test = tbl_phasenTabelle.getSelectionModel().getSelectedItem().getprojektphasenID();
		this.kompetenzen_aktualisieren(test);
		text_pt.setText(ptneu);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				tbl_kompetenzTabelle.requestFocus();
				tbl_kompetenzTabelle.getSelectionModel().select(pos);
				// tbl_phasenTabelle.getFocusModel().focus(pos);
			}
		});

	}

	public int pt_berechnen() throws ParseException {

		// Datums-Formater deklarieren
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		System.out.println(tbl_phasenTabelle.getSelectionModel().getSelectedItem().getstartdatum());
		Date a = format.parse(tbl_phasenTabelle.getSelectionModel().getSelectedItem().getstartdatum());
		Date b = format.parse(tbl_phasenTabelle.getSelectionModel().getSelectedItem().getenddatum());
		Date d;

		// Differenz zwischen Start- und Enddatum berechnen und Wochenend-Tage
		// abziehen
		long diff = b.getTime() - a.getTime();
		long tage = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

		long wochen = tage / 7;
		double taged = (double) tage;
		double wochend = (double) wochen;
		taged = wochend * 4.25;
		System.out.println(taged);
		int tageint = (int) taged;

		return tageint;

	}

	@FXML
	public void phasenname_uebernehmen(ActionEvent event) throws SQLException {
		System.out.println("phasenname_uebernehmen");
		int anzahl = db.phasenname_aendern(text_phasenname.getText(),
				tbl_phasenTabelle.getSelectionModel().getSelectedItem().getprojektphasenID());
		int pos = tbl_phasenTabelle.getSelectionModel().getSelectedIndex();
		this.phase_aktualisieren();
		tbl_phasenTabelle.requestFocus();
		tbl_phasenTabelle.getSelectionModel().select(pos);
		// pDataString = db.phasen_laden();
		// choice_phasenname.setItems(pDataString);
		// tbl_phasenTabelle.getSelectionModel().select(tbl_phasenTabelle.getItems().size()
		// - 1);
		// choice_phasenname.setValue(tbl_phasenTabelle.getSelectionModel().getSelectedItem().getphasenname());

	}
}
