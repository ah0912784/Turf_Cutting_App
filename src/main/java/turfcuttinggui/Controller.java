package turfcuttinggui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import java.sql.Connection;
import java.util.ArrayList;

//Create events to control button inputs
public class Controller {
    public ArrayList<String> queryList = new ArrayList<String>();
    @FXML
    private Label submit_b;
    private Label clear_b;
    private Label export_b;
    private Label name_rb;
    private Label address_rb;
    private Label cellphone_rb;
    private Label city_rb;
    private Label email_rb;
    private Label id_rb;
    private Label zipcode_rb;

    protected void onSubmitButtonClick() {
        db_connect();

    }

    public void db_connect() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

    }

    public void toggleButtonReturn(String fieldName, ActionEvent event) {
        if (queryList.size() == 0) {
        queryList.add(fieldName);

        }else {
            for(int i = 0; i < queryList.size(); i++) {

            }
        }
    }
}