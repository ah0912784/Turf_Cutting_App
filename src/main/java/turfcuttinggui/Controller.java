package turfcuttinggui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;




//Create events to control button inputs
public class Controller {
    public ArrayList<String> queryList = new ArrayList<String>();

    //CheckBox FXML IDs
    @FXML
    private CheckBox ID;
    @FXML
    private CheckBox FULL_NAME;
    @FXML
    private CheckBox ADDRESS;
    @FXML
    private CheckBox CITY;
    @FXML
    private CheckBox STATE;
    @FXML
    private CheckBox ZIP_CODE;
    @FXML
    private CheckBox CELL_PHONE;
    @FXML
    private CheckBox HOME_PHONE;
    @FXML
    private CheckBox EMAIL;
    @FXML
    private CheckBox WORK_EMAIL;
    @FXML
    private CheckBox HIRE_DATE;
    @FXML
    private CheckBox DEPT_NAME;
    @FXML
    private CheckBox LOCATION_DESCRIPTION;
    @FXML
    private CheckBox ANNIVERSARY_DATE;
    @FXML
    private CheckBox SENIOR_DATE;

    @FXML
    protected void onSubmitButtonClick() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String connectQuery = "SELECT FULL_NAME FROM Persons";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            System.out.println(queryOutput);

            while(queryOutput.next()){

                //preview_sp.setContent(new TextArea(queryOutput.getString("FULL_NAME")));
                System.out.println(queryOutput.getString("FULL_NAME"));
            }

            statement.close();
            //connectQuery.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
//Might need for later
//    public void db_connect() {
//        DatabaseConnection connectNow = new DatabaseConnection();
//        Connection connectDB = connectNow.getConnection();
//        String connectQuery = "SELECT FULL_NAME FROM Persons";
//
//        try{
//            Statement statement = connectDB.createStatement();
//            ResultSet queryOutput = statement.executeQuery(connectQuery);
//
//            while(queryOutput.next()){
//                //preview_sp.setText(queryOutput.getString("ID"));
//            }
//
//
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    @FXML
    public void setText(){

    }
    @FXML
    public void cbBuildQuery(CheckBox id, String fieldName){

        if(id.isSelected()){
            queryList.add(fieldName);
        }else{
            try{
                for (int i = 0; i < queryList.size(); i++){
                    if (queryList.get(i) == fieldName) {
                        queryList.remove(i);
                    }
                }
            }catch (ArrayIndexOutOfBoundsException ee) {

            }
        }
}
    @FXML
    public void getAddress(ActionEvent e) {

        cbBuildQuery(ADDRESS, "ADDRESS");
    }
    @FXML
    public void getName(ActionEvent e){

        cbBuildQuery(FULL_NAME,"FULL_NAME");
    }
    @FXML
    public void getZip(ActionEvent e){
        cbBuildQuery(ZIP_CODE,"ZIP_CODE");
    }
    @FXML
    public void getIDNum(ActionEvent e) {
        cbBuildQuery(ID,"ID");
    }
    @FXML
    public void getCellPhone(ActionEvent e){
        cbBuildQuery(CELL_PHONE, "CELLPHONE");
    }
    @FXML
    public void getHomePhone(ActionEvent e){
        cbBuildQuery(HOME_PHONE, "HOME_PHONE");
    }
    @FXML
    public void getEmail(ActionEvent e){
        cbBuildQuery(EMAIL, "EMAIL");
    }
    @FXML
    public void getWorkEmail(ActionEvent e){
        cbBuildQuery(WORK_EMAIL,"WORK_EMAIL");
    }
    @FXML
    public void getCity(ActionEvent e){
        cbBuildQuery(CITY, "CITY");
    }
    @FXML
    public void getHireDate(ActionEvent e){
        cbBuildQuery(HIRE_DATE, "HIRE_DATE");
    }
    @FXML
    public void getDepartmentName(ActionEvent e){
        cbBuildQuery(DEPT_NAME, "DEPT_NAME");
    }
    @FXML
    public void getLocationDescription(ActionEvent e){
        cbBuildQuery(LOCATION_DESCRIPTION, "LOCATION_DESCRIPTION");
    }
    @FXML
    public void getSeniorDate(ActionEvent e){
        cbBuildQuery(SENIOR_DATE,"SENIOR_DATE");
    }
    @FXML
    public void getAnniversaryDate(ActionEvent e){
        cbBuildQuery(ANNIVERSARY_DATE, "ANNIVERSARY_DATE");
    }
    @FXML
    public void getState(ActionEvent e){
        cbBuildQuery(STATE,"STATE");
    }

}//end of class
