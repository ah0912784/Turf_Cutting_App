module com.example.turfcuttinggui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.turfcuttinggui to javafx.fxml;
    //exports com.example.turfcuttinggui;
    exports turfcuttinggui;
    opens turfcuttinggui to javafx.fxml;
}