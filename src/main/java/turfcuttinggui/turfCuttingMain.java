package turfcuttinggui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


//<Stage title="Turf Cutter" xmlns="http://javafx.com/javafx/11.0.2" xmlns:fx="http://javafx.com/fxml/1" fx:controller="turfcuttinggui.Controller">
public class turfCuttingMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(turfCuttingMain.class.getResource("turfcutting_gui.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 586);
        stage.setTitle("Turf Cutter");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) throws SQLException {
        ImportCSV importcsv = new ImportCSV();
        new turfCuttingMain();
        importcsv.masterCall();
        turfCuttingMain.launch();
    }


}