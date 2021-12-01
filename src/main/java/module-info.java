module turfcuttinggui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.opencsv;


    opens turfcuttinggui to javafx.fxml;
    exports turfcuttinggui;
}
