package turfcuttinggui;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableListValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;




//Create events to control button inputs
public class Controller {
    //queryList is to get appropriate field names for mySql query
    private ArrayList<String> queryList = new ArrayList<String>();
    //get user input for the WHERE clause in sql method, used in buildConstraint
    private String usrZip;
    private String usrCity;
    //used in building rows for dynamic tableview display
    private ObservableList<ObservableList> data;
    //Data export variables will be used to save mySql query when submit button is clicked
    //and then used in export data methods.
    private String mySqlQuery;

    //TableView where output columns are displayed
    @FXML
    private TableView tableView;
    //TextField FXML IDs will be used for getting user constraints
    @FXML
    private TextField zipTextField;
    @FXML
    private TextField cityTextField;

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
    private CheckBox JOB_DESCRIPTION;

    @FXML
    protected void onSubmitButtonClick() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String connectQuery = "SELECT "+buildQueryString();
        System.out.println(connectQuery);
        mySqlQuery = connectQuery;
        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            //get data to ExportCSV class
            ExportCSV exportCSV = new ExportCSV();
            exportCSV.rs = queryOutput;
            exportCSV.statementExp = statement;
            System.out.println(queryOutput);

            for (int i = 0; i < queryOutput.getMetaData().getColumnCount();i++){
                //build the dynamic table
                final int j = i;
                TableColumn col = new TableColumn(queryOutput.getMetaData().getColumnName(i+1));
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>)
                        param -> new SimpleStringProperty(param.getValue().get(j).toString()));
                tableView.getColumns().addAll(col);
                System.out.println("Column ["+i+"] ");

            }
            while (queryOutput.next()){
                int columnsNumber = queryOutput.getMetaData().getColumnCount();
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = queryOutput.getString(i);
                    System.out.print(columnValue + " " + queryOutput.getMetaData().getColumnName(i));
                }
                System.out.println("");
            }
            System.out.println(queryOutput.getMetaData().getColumnCount()+" this is the queryOutput MD column size");
            while (queryOutput.next()){
            //Iterate Row
            ObservableList<String> row = FXCollections.observableArrayList();
            for(int i=1 ; i<= queryOutput.getMetaData().getColumnCount(); i++){
                //Iterate Column

                System.out.println(i+" This is i in the Iterate Column Section");
                row.add(queryOutput.getString(i));
            }
            System.out.println("Row [1] added "+row );
            data.add(row);

        }
            tableView.setItems(data);


            statement.close();
            //connectQuery.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }



public String buildQueryString(){
        String query = "";
        String constraint = buildConstraint();
        System.out.println(constraint);
        if (queryList.size() == 1) {
            query = queryList.get(0)+" FROM Persons "+constraint;
        }
        if (queryList.size() > 1){
            query = queryList.get(0);
            for(int i = 1; i < queryList.size(); i++) {
                if (i != queryList.size()-1){
                    query += ", "+queryList.get(i);
                }if (queryList.size()-1 == i){
                    query += ", " + queryList.get(i) +" FROM Persons "+constraint;
                }
            }//end of for
        }//end of else

        return query;
}//end of buildQueryString

    @FXML
    public void setText(){

    }
    //Methods for building queries

    //Used for putting Selected Columns together
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
                System.out.println(id+" does not exist in the array");
            }
        }
}
    //the boolean is to decide which is zip and which is city name


    //Will be used for putting WHERE constraints
    public String buildConstraint(){
        String constraint = ";";

        if (usrCity != null && usrZip != null) {
            constraint = "WHERE CITY = " + usrCity + " AND ZIP_CODE = " + usrZip + ";";
        }
        if (usrCity != null && usrZip ==null){
            constraint = "WHERE CITY = "+usrCity+";";
        }
        if (usrCity == null && usrZip !=null){
            constraint = "WHERE ZIP_CODE = "+usrZip +";";
        }
        return constraint;
    }
//Methods for getting text values for zip codes and cities from FXMl

    @FXML
    public void getZipInput(){
        //Zip code is true for getCondition Method
        if (zipTextField.getText().isEmpty()) {
           usrZip = null;
        } else{
            usrZip = zipTextField.getText();
        }

    }
    @FXML
    public void getCityInput(){
        //city is false
        if (cityTextField.getText().isEmpty()){
            usrCity = null;
        } else{
            usrCity = cityTextField.getText();
        }

    }
    //exportBtn method
    @FXML
    public void exportData(ActionEvent e){
        ExportCSV exportCSV = new ExportCSV();
        exportCSV.export("Persons");
    }

//Methods for getting check box values
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
    @FXML
    public void getJobTitle(){
        cbBuildQuery(JOB_DESCRIPTION,"JOB_DESCRIPTION");
    }

}//end of class
