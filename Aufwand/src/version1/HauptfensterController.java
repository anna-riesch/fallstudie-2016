package version1;

import java.sql.SQLException;

import Tabellen.Projekt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class HauptfensterController {
	@FXML
	private AnchorPane fenster_haupt;
	@FXML
	private Button button_projekt_anlegen;
	@FXML
	private TableView<Projekt> tbl_projektTabelle;
	@FXML
	private TableColumn<Projekt, Integer> tblCell_projektID;
	@FXML
	private TableColumn<Projekt, String> tblCell_projektName;
	@FXML
	private Button button_projekt_loeschen;
	@FXML
	private Button button_phasenplanung;
	@FXML
	private Button button_kompetenzen;
	@FXML
	private TextField text_projektname;

	private ObservableList<Projekt> projektData;
	private schnittstelle db;

	@FXML
	private void initialize() throws SQLException {
		System.out.println("Fenster startet");

		db = new schnittstelle();

		projektData = FXCollections.observableArrayList(db.projekte_laden());

		tblCell_projektID.setCellValueFactory(cellData -> cellData.getValue().getProjektidProperty().asObject());
		tblCell_projektName.setCellValueFactory(cellData -> cellData.getValue().getProjektnameProperty());

		tbl_projektTabelle.setItems(projektData);

		tbl_projektTabelle.getSelectionModel().selectedItemProperty().addListener((observable, oldValue,
				newValue) -> tabelle_geklickt(tbl_projektTabelle.getSelectionModel().getSelectedItem()));
	}

	@FXML
	public void tabelle_geklickt(Projekt p) {

		text_projektname.setText(p.getProjektname());

	}

	@FXML
	public void text_projektname_geklickt(ActionEvent event) throws SQLException {

		System.out.println("KlickText");
		int anzahl = db.projektname_aendern(tbl_projektTabelle.getSelectionModel().getSelectedItem().getProjektid(),
				text_projektname.getText());
		System.out.println(anzahl + " Datensätze geändert!");
		projektData.get(tbl_projektTabelle.getSelectionModel().getSelectedIndex())
				.setProjektname(text_projektname.getText());
		tbl_projektTabelle.refresh();

	}

	// Event Listener on Button[#button_projekt_anlegen].onAction
	@FXML
	public void button_projekt_anlegen_click(ActionEvent event) throws SQLException {

		int anzahl = db.projekt_anlegen();
		System.out.println(anzahl + " Projekt(e) angelegt");
		projektData = FXCollections.observableArrayList(db.projekte_laden());
		tbl_projektTabelle.setItems(projektData);

	}

	@FXML
	public void button_projekt_loeschen_click(ActionEvent event) throws SQLException {

		int anzahl = db.projekt_loeschen(tbl_projektTabelle.getSelectionModel().getSelectedItem().getProjektid());
		System.out.println(anzahl + " Projekt(e) gelöscht");
		projektData = FXCollections.observableArrayList(db.projekte_laden());
		tbl_projektTabelle.setItems(projektData);

	}

	public void button_kompetenzen_click(ActionEvent event) throws SQLException {

		if (tbl_projektTabelle.getSelectionModel().getSelectedItem() != null)
			;
		{

			try {
				new OpenKompetenzStage(tbl_projektTabelle.getSelectionModel().getSelectedItem().getProjektid(),
						tbl_projektTabelle.getSelectionModel().getSelectedItem().getProjektname());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
