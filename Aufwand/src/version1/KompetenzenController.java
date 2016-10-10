package version1;

import java.sql.SQLException;

import Tabellen.Kompetenz;
import Tabellen.Mitarbeiterkompetenz;
import Tabellen.Projekt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.Label;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;


public class KompetenzenController {
	@FXML
	private TableView<Mitarbeiterkompetenz> tbl_mitarbeiterkompetenzTabelle;
	@FXML
	private TableColumn tblCell_MKID;
	@FXML
	private TableColumn tblCell_MKKompetenz;
	@FXML
	private TableColumn tblCell_MKMitarbeitername;
	@FXML
	private TableColumn tblCell_MKKosten;
	@FXML
	private TableView tbl_mitarbeiterTabelle;
	@FXML
	private TableColumn tblCell_MitarbeiterID;
	@FXML
	private TableColumn tblCell_MitarbeiterName;
	@FXML
	private TableColumn tblCell_MitarbeiterKosten;
	@FXML
	private TableColumn tblCell_MitarbeiterMAK;
	@FXML
	private TableColumn tblCell_MitarbeiterZugehoerigkeit;
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
	
	
	private schnittstelle db;
	
	@FXML
	private void initialize() throws SQLException {
		
		
		System.out.println("Fenster startet");
		
		label_projekt.setText(OpenKompetenzStage.pname);
		
    	db = new schnittstelle();
    	
    	kData = FXCollections.observableArrayList(db.kompetenzen_laden());

    	tblCell_KompetenzID.setCellValueFactory(cellData -> cellData.getValue().getkidProperty().asObject());
    	tblCell_KompetenzName.setCellValueFactory(cellData -> cellData.getValue().getnameProperty());
    	
    	tbl_kompetenzTabelle.setItems(kData);  
    	
    	tbl_kompetenzTabelle.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> kompetenz_geklickt(tbl_kompetenzTabelle.getSelectionModel().getSelectedItem()));
	

	
	}
	
	public void kompetenz_geklickt(Kompetenz p) {
		
		//text_projektname.setText(p.getProjektname());						
		
	
	}
	
}
