package version1;

import java.sql.SQLException;

import Tabellen.Projektphasen;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PhasenplanungController {
	@FXML
	private Label label_projekt;
	@FXML
	private TableView tbl_phasenTabelle;
	@FXML
	private TableColumn tblCell_phasenID;
	@FXML
	private TableColumn tblCell_phasenname;
	@FXML
	private ChoiceBox choice_phasenname;
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
	private TableView tbl_kompetenzTabelle;
	@FXML
	private TableColumn tblCell_kompID;
	@FXML
	private TableColumn tblCell_kompname;
	@FXML
	private TableColumn tblCell_komprisiko;
	@FXML
	private TableColumn tblCell_komppt;
	@FXML
	private TableColumn tblCell_kompk;

	private ObservableList<Projektphasen> ppData;
	private schnittstelle db;

	@FXML
	private void initialize() throws SQLException {

		System.out.println("Phasenplanung startet");

		db = new schnittstelle();

	}

}
