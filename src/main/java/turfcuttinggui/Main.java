package turfcuttinggui;

import javafx.application.Application;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        ImportCSV importcsv = new ImportCSV();
        importcsv.masterCall();
        Application.launch(turfCuttingMain.class, args);


    }
}
