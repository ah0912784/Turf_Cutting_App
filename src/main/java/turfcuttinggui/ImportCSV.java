package turfcuttinggui;

import java.io.FileReader;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

import com.opencsv.*;

import static java.lang.Integer.parseInt;

//handle the manipulation of data from java query and the export of java query to csv
// file path for importing data
//  C:\Users\%USERPROFILE%\OneDrive\Documents\TurfCuttingDB\turfCuttingCSV
//ID	FULL-NAME	new or old	Address	City	State	zip code	Home phone	cell phone	email 	Work email	ADJ-HIRE-DATE	ANNIVERS-DATE	SENIOR-DATE	DEPARTMENT.NAME	LOCATION.DESCRIPTION	POSITION.DESCRIPTION	Results
/* RECORD POSITIONS
[0]  = ID
[1]  = FULL_NAME
[2]  = ADDRESS
[3]  = CITY
[4]  = STATE
[5]  = ZIP_CODE
[6]  = HOME_PHONE
[7]  = CELL_PHONE
[8]  = EMAIL
[9]  = WORK_EMAIL
[10] = ADJ_HIRE_DATE
[11] = ANNIVERSARY_DATE
[12] = SENIOR_DATE
[13] = DEPT_NAME
[14] = LOCATION_DESCRIPTION
[15] = POSITION_DESCRIPTION
[16] = RESULTS

 */
public class ImportCSV {
    private String usrName = System.getProperty("user.name");
    public Path filePath = Path.of("C:\\Users\\"+usrName+"\\OneDrive\\Documents\\TurfCuttingDB\\turfcutting.csv");
    private ArrayList<Integer> aryCSVIDs      = new ArrayList();
    private ArrayList<Integer> aryDatabaseIDs = new ArrayList<>();
    private ArrayList<Record>  aryCSV_Records = new ArrayList<>();
    private ArrayList<Record> aryDB_Records   = new ArrayList<>();
    private String[] aryColumnNames = {"ID","FULL_NAME","NEW_OR_OLD","ADDRESS","CITY","STATE","ZIP_CODE","HOME_PHONE","CELL_PHONE",
            "EMAIL","WORK_EMAIL", "ADJ_HIRE_DATE","ANNIVERSARY_DATE","SENIOR_DATE","DEPT_NAME","LOCATION_DESCRIPTION",
            "POSITION_DESCRIPTION","RESULTS",};

    //build csv import
    //will be called in main function to execute all of these tasks
    public void masterCall(){
        readCSV();
        readDataBase();
        manageDB(aryDB_Records,aryCSV_Records);
    }
    public String createColumnString(int start, int end){
        String columnString = "";
        for (int i = start; i < end; i++)
            if (i < end-1) {
                columnString += aryColumnNames[i] + ",";
            } else {
                columnString += aryColumnNames[i];
            }
        return columnString;
    }

    public void readCSV(){

        try (CSVReader reader = new CSVReader(new FileReader(String.valueOf(filePath))))
        {
            reader.skip(1);
            while((reader.readNext()) != null) {
                String[] rowData = reader.readNext();
                aryCSV_Records.add(recordBuilder(rowData));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void readDataBase(){
        if (checkForDB() == true) {
            try {
                DatabaseConnection dbConnect = new DatabaseConnection();
                Connection connection = dbConnect.getConnection();
                String connectQuery = "SELECT * FROM Persons";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(connectQuery);

                while(resultSet.next()) {
                    String[] rowData = new String[17];
                    for(int i=1 ; i<= resultSet.getMetaData().getColumnCount(); i++){
                        //Iterate Column
                        rowData[i] = resultSet.getString(i);
                    }
                   aryDB_Records.add(recordBuilder(rowData));
                }
                resultSet.close();
                connection.close();
                Collections.sort(aryDatabaseIDs);
            } catch (SQLException e){
                e.printStackTrace();
            }

        } else{
            generateDB();
        }
    }
    //FULL_NAME,ADDRESS,CITY,STATE,ZIP_CODE,HOME_PHONE,CELL_PHONE,EMAIL,WORK_EMAIL,ADJ_HIRE_DATE,ANNIVERSARY_DATE,SENIOR_DATE,DEPT_NAME,LOCATION_DESCRIPTION,POSITION_DESCRIPTION,RESULTS
    public void insertDB(Record record){
        DatabaseConnection mySql = new DatabaseConnection();
        String sqlQuery = "INSERT INTO Persons ("+ createColumnString(0,aryColumnNames.length)+") "+
                "VALUES ("+record.getFULL_NAME()+","+record.getADDRESS()
                +","+record.getCITY()+""+record.getSTATE()+","+record.getZIP_CODE()+","+record.getHOME_PHONE()+","+record.getCELL_PHONE()+","+
                record.getEMAIL()+","+record.getWORK_EMAIL()+","+record.getHIRE_DATE()+","+record.getANNIVERSARY_DATE()+","+
                record.getSenior_Date()+","+record.getDEPT_NAME()+","+record.getLOCATION_DESCRIPTION()+","+
                record.getJOB_DESCRIPTION()+","+record.getRESULT()+")";
        System.out.println(createColumnString(1,aryColumnNames.length));
        mySql.quickQuery(sqlQuery);
    }
    public boolean checkForDB(){
        String dbName = "TurfCuttingDB";
        ResultSet resultSet = null;
        Connection connectDB = null;
        boolean dbExists = false;
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            connectDB = connectNow.getConnection();

            if (connectDB != null) {
                resultSet = connectDB.getMetaData().getCatalogs();

                while (resultSet.next()){
                    String catalogs = resultSet.getString(1);

                    if(dbName.equals(catalogs)){
                        System.out.println("the database "+dbName+" exists");
                        dbExists = true;
                    } else {
                        System.out.println("unable to create database connection");
                        dbExists = false;
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } 	finally{
                if( resultSet != null){
                    try{
                        resultSet.close();
                    } catch(SQLException ex){
                        ex.printStackTrace();
                    }
                }
                if( connectDB != null){
                    try{
                        connectDB.close();
                    } catch(SQLException ex){
                    ex.printStackTrace();
                    }
            }
        }//end of finally

    return dbExists;
    }
    public void generateDB(){
        final String DB_URL = "jdbc:mysql://localhost:1433/";
        final String USER = "root";
        final String PASS = "password";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS) ;
            Statement stmt = conn.createStatement();
        ) {
            String sql = "CREATE DATABASE TurfCuttingDB";
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully...");
            sql = "create table Persons ( ID int NOT NULL, " +
                    "FULL_NAME varchar(255) NOT NULL, " +
                    "NEW_OR_OLD varchar(255), "+
                    "ADDRESS varchar(255) NOT NULL, " +
                    "CITY varchar(255) NOT NULL, " +
                    "STATE varchar(255) NOT NULL, " +
                    "ZIP_CODE varchar(255) NOT NULL, " +
                    "HOME_PHONE varchar(50), " +
                    "CELL_PHONE varchar(50), " +
                    "EMAIL varchar(255), " +
                    "WORK_EMAIL varchar(255), " +
                    "ADJ_HIRE_DATE varchar(255), " +
                    "ANNIVERSARY_DATE varchar(255), " +
                    "SENIOR_DATE VARCHAR(255), " +
                    "DEPT_NAME varchar(255), " +
                    "LOCATION_DESCRIPTION varchar(255), " +
                    "JOB_DESCRIPTION varchar(255), " +
                    "RESULTS varchar(255), " +
                    "primary key(ID))";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void manageDB(ArrayList<Record> dbRecords, ArrayList<Record> csvRecord){
        for (int i = 0; i < dbRecords.size(); i++){
            for (int ii = 0; i < csvRecord.size(); ii++ ){
                if (dbRecords.get(i).getID() == csvRecord.get(ii).getID()){
                    updateDB(csvRecord.get(ii));
                    csvRecord.remove(ii);
                }
            }
        }
        for (int i = 0; i < csvRecord.size();i++){
            insertDB(csvRecord.get(i));
        }
    }
    public Record getRecord(int ID, ArrayList<Record> recordList){
        int size = recordList.size();
        int counter = 0;
        Record record;
        while (counter < size){
            int recordID = recordList.get(counter).getID();

            if(ID == recordID){

                break;
            } else {
                counter++;
            }
        }
        record = recordList.get(counter);

        return record;
    }
    public void updateDB(Record record){
        DatabaseConnection mySql = new DatabaseConnection();
        String updateQuery = "UPDATE Persons " +
                "SET "+
                "FULL_NAME = "+record.getFULL_NAME() +","+
                "ADDRESS = " +record.getADDRESS()+","+
                "CITY = " +record.getCITY()+","+
                "STATE = " +record.getSTATE()+","+
                "ZIP_CODE = " +record.getZIP_CODE()+","+
                "HOME_PHONE = " +record.getHOME_PHONE()+","+
                "CELL_PHONE = "+record.getCELL_PHONE() +","+
                "EMAIL = " +record.getEMAIL()+","+
                "WORK_EMAIL = "+record.getWORK_EMAIL() +","+
                "ADJ_HIRE_DATE = " +record.getHIRE_DATE()+","+
                "ANNIVERSARY_DATE = " +record.getANNIVERSARY_DATE()+","+
                "SENIOR_DATE = " +record.getSenior_Date()+","+
                "DEPT_NAME = " +record.getDEPT_NAME()+","+
                "LOCATION_DESCRIPTION = "+record.getLOCATION_DESCRIPTION() +","+
                "POSITION_DESCRIPTION = "+record.getJOB_DESCRIPTION() +","+
                "RESULTS = "+record.getRESULT()+
                "WHERE ID = " +record.getID();
        mySql.quickQuery(updateQuery);
    }

    public Record recordBuilder(String[] rowData){
        Record record;
        int id = parseInt(rowData[0]);
        record = new Record(id,rowData[1],rowData[2],rowData[3],rowData[4],rowData[5],rowData[6],rowData[7],
                rowData[8],rowData[9],rowData[10],rowData[11],rowData[12],rowData[13],rowData[14],rowData[15],rowData[16],rowData[17]);
        return record;
    }

}
