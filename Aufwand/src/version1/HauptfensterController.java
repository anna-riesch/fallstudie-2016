package version1;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

import Tabellen.Projekt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
	@FXML
	private TextField text_projektbeschreibung;
	
	private ObservableList<Projekt> projektData;
	
	@FXML
	private void initialize() throws SQLException {
		System.out.println("Fenster startet");
		
    	schnittstelle db = new schnittstelle();
    	
    	projektData = FXCollections.observableArrayList(db.projekte_laden());
    	
		tblCell_projektID.setCellValueFactory(cellData -> cellData.getValue().getProjektidProperty().asObject());
		tblCell_projektName.setCellValueFactory(cellData -> cellData.getValue().getProjektnameProperty());
		
		tbl_projektTabelle.setItems(projektData);
    	
    	

    	
	}

	// Event Listener on Button[#button_projekt_anlegen].onAction
	@FXML
	public void button_projekt_anlegen_click(ActionEvent event) {
		System.out.println("Klick");
	}
}
