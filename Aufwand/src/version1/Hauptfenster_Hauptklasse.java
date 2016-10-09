package version1;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Hauptfenster_Hauptklasse extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(Hauptfenster_Hauptklasse.class, (java.lang.String[])null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
        	FXMLLoader fxmlLoader = new FXMLLoader();
        	AnchorPane page = (AnchorPane) FXMLLoader.load(Hauptfenster_Hauptklasse.class.getResource("Hauptfenster.fxml"));
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Projektverwaltungssoftware");
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Hauptfenster_Hauptklasse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}