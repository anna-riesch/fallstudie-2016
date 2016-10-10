package version1;

import java.sql.SQLException;

import Tabellen.Kompetenz;
import Tabellen.Mitarbeiterkompetenz;
import Tabellen.Projektkompetenz;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class KompetenzenController {
	@FXML
	private TableView<Projektkompetenz> tbl_mitarbeiterkompetenzTabelle;
	@FXML
	private TableColumn<Projektkompetenz, Integer> tblCell_MKID;
	@FXML
	private TableColumn<Projektkompetenz, String> tblCell_MKKompetenz;
	@FXML
	private TableColumn<Projektkompetenz, String> tblCell_MKMitarbeitername;
	@FXML
	private TableColumn<Projektkompetenz, Integer> tblCell_MKKosten;
	@FXML
	private TableView<Mitarbeiterkompetenz> tbl_mitarbeiterTabelle;
	@FXML
	private TableColumn<Mitarbeiterkompetenz, Integer> tblCell_MitarbeiterID;
	@FXML
	private TableColumn<Mitarbeiterkompetenz, String> tblCell_MitarbeiterName;
	@FXML
	private TableColumn<Mitarbeiterkompetenz, Integer> tblCell_MitarbeiterKosten;
	@FXML
	private TableColumn<Mitarbeiterkompetenz, Integer> tblCell_MitarbeiterMAK;
	@FXML
	private TableColumn<Mitarbeiterkompetenz, String> tblCell_MitarbeiterZugehoerigkeit;
	@FXML
	private TableColumn<Mitarbeiterkompetenz, Integer> tblCell_MitarbeiterMKID;
	@FXML
	private TableView<Kompetenz> tbl_kompetenzTabelle;
	@FXML
	private TableColumn<Kompetenz, Integer> tblCell_KompetenzID;
	@FXML
	private TableColumn<Kompetenz, String> tblCell_KompetenzName;
	@FXML
	private Label label_projekt;
	@FXML
	private Button button_mk_hinzufuegen;
	@FXML
	private Button button_mk_loeschen;

	private ObservableList<Kompetenz> kData;
	private ObservableList<Mitarbeiterkompetenz> mkData;
	private ObservableList<Projektkompetenz> pkData;

	private schnittstelle db;

	@FXML
	private void initialize() throws SQLException {

		System.out.println("Fenster startet");

		db = new schnittstelle();

		kData = FXCollections.observableArrayList(db.kompetenzen_laden());

		tblCell_KompetenzID.setCellValueFactory(cellData -> cellData.getValue().getkidProperty().asObject());
		tblCell_KompetenzName.setCellValueFactory(cellData -> cellData.getValue().getnameProperty());

		tbl_kompetenzTabelle.setItems(kData);

		tbl_kompetenzTabelle.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					try {
						kompetenz_geklickt(tbl_kompetenzTabelle.getSelectionModel().getSelectedItem());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});

		this.projektkompetenz_aktualisieren();

	}

	public void kompetenz_geklickt(Kompetenz k) throws SQLException {

		mkData = FXCollections.observableArrayList(db.mitarbeiterkompetenzen_laden(k.getkid()));

		tblCell_MitarbeiterID
				.setCellValueFactory(cellData -> cellData.getValue().getmitarbeiteridProperty().asObject());
		tblCell_MitarbeiterName.setCellValueFactory(cellData -> cellData.getValue().getmitarbeiternameProperty());
		tblCell_MitarbeiterKosten.setCellValueFactory(cellData -> cellData.getValue().getkostenProperty().asObject());
		tblCell_MitarbeiterMAK.setCellValueFactory(cellData -> cellData.getValue().getmakProperty().asObject());
		tblCell_MitarbeiterZugehoerigkeit
				.setCellValueFactory(cellData -> cellData.getValue().getzugehoerigkeitProperty());
		tblCell_MitarbeiterMKID.setCellValueFactory(cellData -> cellData.getValue().getMkidProperty().asObject());

		tbl_mitarbeiterTabelle.setItems(mkData);

	}

	@FXML
	public void button_mitarbeiter_hinzufuegen_click(ActionEvent event) throws SQLException {

		int anzahl = db.projektkompetenz_anlegen(tbl_mitarbeiterTabelle.getSelectionModel().getSelectedItem().getMkid(),
				OpenKompetenzStage.getpid(), tbl_kompetenzTabelle.getSelectionModel().getSelectedItem().getkid());
		System.out.println(anzahl + " Projektkompetenz(en) angelegt");
		this.projektkompetenz_aktualisieren();

	}

	@FXML
	public void button_mitarbeiter_loeschen_click(ActionEvent event) throws SQLException {
		System.out
				.println(tbl_mitarbeiterkompetenzTabelle.getSelectionModel().getSelectedItem().getprojektkompetenzID());
		int anzahl = db.projektkompetenz_loeschen(
				tbl_mitarbeiterkompetenzTabelle.getSelectionModel().getSelectedItem().getprojektkompetenzID());
		System.out.println(anzahl + " Projektkompetenz(en) gelöscht");
		this.projektkompetenz_aktualisieren();

	}

	@FXML
	public void projektkompetenz_aktualisieren() throws SQLException {

		pkData = FXCollections.observableArrayList(db.projektkompetenzen_laden(OpenKompetenzStage.getpid()));

		tblCell_MKID.setCellValueFactory(cellData -> cellData.getValue().getprojektkompetenzIDProperty().asObject());
		tblCell_MKKompetenz.setCellValueFactory(cellData -> cellData.getValue().getkompetenznameProperty());
		tblCell_MKMitarbeitername.setCellValueFactory(cellData -> cellData.getValue().getmitarbeiternameProperty());
		tblCell_MKKosten.setCellValueFactory(cellData -> cellData.getValue().getkostenProperty().asObject());

		tbl_mitarbeiterkompetenzTabelle.setItems(pkData);

	}

}
