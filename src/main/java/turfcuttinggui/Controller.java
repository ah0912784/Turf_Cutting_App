package turfcuttinggui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.util.ArrayList;


//Create events to control button inputs
public class Controller {
    public TextField id_textfield;
    //test variable for counting loops
    //queryList is to get appropriate field names for mySql query
    private final ArrayList<String> queryList = new ArrayList<String>();
    //used in building rows for dynamic tableview display
    public ObservableList<ObservableList> data = FXCollections.observableArrayList();
    //Data export variables will be used to save mySql query when submit button is clicked
    //and then used in export data methods.
    public String mySqlString;

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
    private CheckBox RESULTS;
    @FXML
    private Button delete_button;

    @FXML
    private CheckBox NEW_OR_OLD;
    @FXML
    protected void onSubmitButtonClick() {

        //connector class information
        String connectQuery = "SELECT "+buildQueryString();
        setMySqlString(connectQuery);
        DatabaseConnection mySql = new DatabaseConnection();
        try{

            ResultSet queryOutput = mySql.getResultSet(connectQuery);
            //get data to ExportCSV class

            System.out.println(queryOutput.getMetaData().getColumnCount());

            for (int i = 0; i < queryOutput.getMetaData().getColumnCount();i++) {
                //build the dynamic table
                int j = i;
                System.out.println(j+" this is j");
                TableColumn col = new TableColumn(queryOutput.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>)
                        param -> new SimpleStringProperty(param.getValue().get(j).toString()));
                tableView.getColumns().addAll(col);
                System.out.println("Column [" + i + "] ");

            }


            System.out.println(queryOutput.getMetaData().getColumnCount()+" this is the queryOutput MD column size");

            while (queryOutput.next()){
            //Iterate Row
            ObservableList<String> row = FXCollections.observableArrayList();
            for(int i=1 ; i<= queryOutput.getMetaData().getColumnCount(); i++){
                //Iterate Column
                //System.out.println(i+" This is i in the Iterate Column Section "+queryOutput.getString(i));
                row.add(queryOutput.getString(i));
            }
                if (row == null) {
                 System.out.println("row was null");
                } else {
                    System.out.println("Row [1] added " + row);
                    data.addAll(row);
                }
        }
            //System.out.println(data+", the size is "+data.size());
            tableView.setItems(data);

            connectQuery = "";
            queryOutput.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }


//builds the string for the mySql query
public String buildQueryString(){
        String query = "";
        String constraint = buildConstraint();
        System.out.println(constraint);
        if (queryList.size() == 0){
            query = "* FROM Persons "+constraint;
        }
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

    //Will be used for putting WHERE constraints
    public String buildConstraint(){
        String constraint = ";";
        String usrCity = getCityInput();
        String usrZip = getZipInput();
        if (usrCity != null && usrZip != null) {
            constraint = "WHERE CITY = '" + usrCity + "' AND ZIP_CODE = '"+ usrZip +"';";
        }
        if (usrCity != null && usrZip ==null){
            constraint = "WHERE CITY = '"+usrCity+"';";
        }
        if (usrCity == null && usrZip !=null){
            constraint = "WHERE ZIP_CODE = '"+usrZip +"';";
        }
        return constraint;
    }

    public String getInput_ID(){
        String id = id_textfield.getText();
        return id;
    }
    //Clear method to erase entry info and columns from file
    @FXML
    public void clearData(ActionEvent e){
        tableView.getColumns().clear();
        cityTextField.clear();
        zipTextField.clear();
        id_textfield.clear();
    }
//Methods for getting text values for zip codes and cities from FXMl

    @FXML
    public String getZipInput(){
        //Zip code is true for getCondition Method
        String usrZip = "";
        if (zipTextField.getText().isEmpty()) {
           usrZip = null;
        } else{
            usrZip = zipTextField.getText();
        }
    return usrZip;
    }
    @FXML
    public String getCityInput(){
        String usrCity = "";
        //city is false
        if (cityTextField.getText().isEmpty()){
            usrCity = null;
        } else{
            usrCity = cityTextField.getText();
        }
        return usrCity;
    }

    public void setMySqlString(String mySqlString){
        this.mySqlString = mySqlString;
    }
    public String getMySqlString(){
        return mySqlString;
    }
    //exportBtn method
    @FXML
    public void exportData(ActionEvent e){
        ExportCSV exportCSV = new ExportCSV();
        exportCSV.export("Persons",getMySqlString());
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
    public void deleteRecords(){
        String id = getInput_ID();
        String query = "DELETE FROM Persons WHERE ID = "+id;
        DatabaseConnection mySql = new DatabaseConnection();
        if(id != null){
            mySql.DMLQuery(query, "Record Deleted");
     }
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
        cbBuildQuery(CELL_PHONE, "CELL_PHONE");
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
        cbBuildQuery(HIRE_DATE, "ADJ_HIRE_DATE");
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
    public void getJobTitle(ActionEvent e){
        cbBuildQuery(JOB_DESCRIPTION,"POSITION_DESCRIPTION");
    }
    @FXML
    public void getResults(ActionEvent e) { cbBuildQuery(RESULTS, "RESULTS"); }
    @FXML
    public void getNewOrOld(ActionEvent e){cbBuildQuery(NEW_OR_OLD, "NEW_OR_OLD");}



}//end of class
