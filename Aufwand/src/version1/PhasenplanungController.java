package version1;

import java.sql.SQLException;
import java.time.LocalDate;

import Tabellen.Projektphasen;
import Tabellen.Wert;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

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
	private ComboBox<String> choice_phasenname = new ComboBox<String>();
	@FXML
	private TextField text_pt;
	@FXML
	private TextField text_rz;
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
	private TableColumn<Wert, Integer> tblCell_komprisiko;
	@FXML
	private TableColumn<Wert, Integer> tblCell_komppt;
	@FXML
	private TableColumn<Wert, Double> tblCell_kompk;

	private ObservableList<Projektphasen> ppData;
	private ObservableList<String> pData;
	private ObservableList<Wert> wData;

	private schnittstelle db;
	public static int projektid;

	@FXML
	private void initialize() throws SQLException {

		System.out.println("Phasenplanung startet");

		projektid = OpenPhasenStage.getpid();

		System.out.println("Projekt-ID abgespeichert: " + projektid);

		choice_phasenname.setTooltip(new Tooltip("Projektphasenname auswählen"));

		db = new schnittstelle();

		this.phasen_aktualisieren(0);

		tbl_phasenTabelle.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			try {
				phase_geklickt(tbl_phasenTabelle.getSelectionModel().getSelectedItem().getphasenname());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		choice_phasenname.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			try {
				choice_phasenname_auswahl(choice_phasenname.getValue());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		tbl_kompetenzTabelle.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					try {
						tbl_kompetenzTabelle_geklickt(tbl_kompetenzTabelle.getSelectionModel().getSelectedItem());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
	}

	@FXML
	public void phase_geklickt(String phasenname) throws SQLException {

		choice_phasenname.setValue(null);
		choice_phasenname.getItems().clear();

		choice_phasenname.setValue(phasenname);
		pData = FXCollections.observableArrayList(db.phasen_laden());

		String array[] = new String[pData.size()];
		for (int j = 0; j < pData.size(); j++) {
			array[j] = pData.get(j);
		}

		for (String k : array) {
			choice_phasenname.getItems().add(k);
		}

		LocalDate sdt = LocalDate.parse(tbl_phasenTabelle.getSelectionModel().getSelectedItem().getstartdatum());
		LocalDate edt = LocalDate.parse(tbl_phasenTabelle.getSelectionModel().getSelectedItem().getstartdatum());

		datum_start.setValue(sdt);
		datum_ende.setValue(edt);

		this.kompetenzen_aktualisieren(tbl_phasenTabelle.getSelectionModel().getSelectedItem().getprojektphasenID());

	}

	@FXML
	public void choice_phasenname_auswahl(String phasenname) throws SQLException {

		if (phasenname != null
				&& phasenname != tbl_phasenTabelle.getSelectionModel().getSelectedItem().getphasenname()) {
			System.out.println(choice_phasenname.getValue());
			System.out.println("ES WIRD EIN PHASENNAME GEÄNDERT");
			int anzahl = db.phasenname_aendern(
					tbl_phasenTabelle.getSelectionModel().getSelectedItem().getprojektphasenID(), phasenname);
			System.out.println(phasenname + anzahl);
			this.phasen_aktualisieren(tbl_phasenTabelle.getSelectionModel().getSelectedIndex());

		}

	}

	@FXML
	public void phasen_aktualisieren(int pos) throws SQLException {

		System.out.println("Projekt-ID wird jetzt verwendet um die Projektphasen zu laden: " + projektid);
		ppData = FXCollections.observableArrayList(db.projektphasen_laden(projektid));

		tblCell_phasenID.setCellValueFactory(cellData -> cellData.getValue().getprojektphasenIDProperty().asObject());
		tblCell_phasenname.setCellValueFactory(cellData -> cellData.getValue().getphasennameProperty());
		tblCell_startdatum.setCellValueFactory(cellData -> cellData.getValue().getstartdatumProperty());
		tblCell_enddatum.setCellValueFactory(cellData -> cellData.getValue().getenddatumProperty());

		// datum_start.setValue(ppData.g);
		System.out.println(ppData.toString() + " = LOL");

		tbl_phasenTabelle.setItems(ppData);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				tbl_phasenTabelle.requestFocus();
				tbl_phasenTabelle.getSelectionModel().select(pos);
				// tbl_phasenTabelle.getFocusModel().focus(pos);
			}
		});

	}

	@FXML
	public void datum_start_geklickt(ActionEvent event) throws SQLException {

		if (datum_start.getValue().toString() != null && !tbl_phasenTabelle.getSelectionModel().getSelectedItem()
				.getstartdatum().toString().equals(datum_start.getValue().toString())) {

			int anzahl = db.startdatum_aendern(
					tbl_phasenTabelle.getSelectionModel().getSelectedItem().getprojektphasenID(),
					datum_start.getValue().toString());
			System.out.println(anzahl + "START - Datensätze geändert.");
			this.phasen_aktualisieren(tbl_phasenTabelle.getSelectionModel().getSelectedIndex());
		}
	}

	@FXML
	public void datum_ende_geklickt(ActionEvent event) throws SQLException {

		if (datum_ende.getValue().toString() != null && !tbl_phasenTabelle.getSelectionModel().getSelectedItem()
				.getenddatum().toString().equals(datum_ende.getValue().toString())) {

			System.out.println("ENDE GEKLICKT");
			int anzahl = db.enddatum_aendern(
					tbl_phasenTabelle.getSelectionModel().getSelectedItem().getprojektphasenID(),
					datum_ende.getValue().toString());
			System.out.println(anzahl + "END - Datensätze geändert.");
			this.phasen_aktualisieren(tbl_phasenTabelle.getSelectionModel().getSelectedIndex());
		}
	}

	public void kompetenzen_aktualisieren(int phid) throws SQLException {

		wData = FXCollections.observableArrayList(
				db.werte_laden(tbl_phasenTabelle.getSelectionModel().getSelectedItem().getprojektphasenID()));

		tblCell_kompID.setCellValueFactory(cellData -> cellData.getValue().getwertidProperty().asObject());
		tblCell_kompname.setCellValueFactory(cellData -> cellData.getValue().getkompetenznameProperty());
		tblCell_komprisiko.setCellValueFactory(cellData -> cellData.getValue().getrisikozuschlagProperty().asObject());
		tblCell_komppt.setCellValueFactory(cellData -> cellData.getValue().getpersonentageProperty().asObject());
		tblCell_kompk.setCellValueFactory(cellData -> cellData.getValue().getwertProperty().asObject());

		tbl_kompetenzTabelle.setItems(wData);

	}

	public void tbl_kompetenzTabelle_geklickt(Wert w) throws SQLException {

		text_pt.setText(String.valueOf(w.getpersonentage()));
		text_rz.setText(String.valueOf(w.getrisikozuschlag()));

	}

	@FXML
	public void text_personentage_geklickt(ActionEvent event) throws SQLException {

		System.out.println("Personentage werden geändert");
		int anzahl = db.personentage_aendern(tbl_kompetenzTabelle.getSelectionModel().getSelectedItem().getwertid(),
				text_pt.getText());
		System.out.println(anzahl + " Datensätze geändert!");
		wData.get(tbl_kompetenzTabelle.getSelectionModel().getSelectedIndex())
				.setpersonentage(Integer.parseInt(text_pt.getText()));
		tbl_kompetenzTabelle.refresh();

	}

	@FXML
	public void text_risikozuschlag_geklickt(ActionEvent event) throws SQLException {

		System.out.println("Risikozuschlag wird geändert");
		int anzahl = db.risikozuschlag_aendern(tbl_kompetenzTabelle.getSelectionModel().getSelectedItem().getwertid(),
				text_rz.getText());
		System.out.println(anzahl + " Datensätze geändert!");
		wData.get(tbl_kompetenzTabelle.getSelectionModel().getSelectedIndex())
				.setrisikozuschlag(Integer.parseInt(text_rz.getText()));
		tbl_kompetenzTabelle.refresh();

	}

	@FXML
	public void button_phase_anlegen_click(ActionEvent event) throws SQLException {

		int anzahl = db.projektphase_anlegen(projektid);
		System.out.println(anzahl + " Werte angelegt");

		// wData = FXCollections.observableArrayList(db.werte_laden(projektid));
		// tbl_kompetenzTabelle.setItems(wData);
		this.phasen_aktualisieren(0);

	}

	@FXML
	public void button_phase_loeschen_click(ActionEvent event) throws SQLException {

		db.projektphase_loeschen(tbl_phasenTabelle.getSelectionModel().getSelectedItem().getprojektphasenID());
		this.phasen_aktualisieren(0);

	}

}
