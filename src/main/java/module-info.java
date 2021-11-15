module turfcuttinggui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens turfcuttinggui to javafx.fxml;
    exports turfcuttinggui;
}
