package turfcuttinggui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import com.opencsv.*;

import static java.lang.Integer.parseInt;

//handle the manipulation of data from java query and the export of java query to csv
// file path for importing data
//  C:\Users\%USERPROFILE%\OneDrive\Documents\TurfCuttingDB\turfCuttingCSV
public class ImportCSV {
    private Path filePath = Path.of("C:\\Users\\%USERPROFILE%\\OneDrive\\Documents\\TurfCuttingDB\\turfCuttingCSV");
    private ArrayList<Integer> aryCSVIDs      = new ArrayList();
    private ArrayList<Integer> aryDatabaseIDs = new ArrayList<>();
    private ArrayList<Record>  aryCSV_Records = new ArrayList<>();
    private ArrayList<Record> aryDB_Records   = new ArrayList<>();


    //build csv import
    //will be called in main function to execute all of these tasks
    private void masterCall(){
        readCSV();
        readDataBase();
    }
    private void readCSV(){
        Record buildRecord;
        try (CSVReader reader = new CSVReader(new FileReader("turfCuttingCSV.csv")))
        {
            reader.skip(1);


            while((reader.readNext()) != null) {
                String[] rowData = reader.readNext();
                int id = parseInt(rowData[0]);
                buildRecord = new Record(id,rowData[1],rowData[2],rowData[3],rowData[4],rowData[5],rowData[6],rowData[7],
                        rowData[8],rowData[9],rowData[10],rowData[11],rowData[12],rowData[13],rowData[14],rowData[15],rowData[16]);
                aryCSV_Records.add(buildRecord);
                aryCSVIDs.add(id);

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void readDataBase(){
        Record buildRecord;
        if (checkForDB() == true) {
            try {
                DatabaseConnection dbConnect = new DatabaseConnection();
                Connection connection = dbConnect.getConnection();
                String connectQuery = "SELECT * FROM Persons";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(connectQuery);

                while(resultSet.next()) {
                    ArrayList<String> rowData = new ArrayList;
                    for(int i=1 ; i<= resultSet.getMetaData().getColumnCount(); i++){
                        //Iterate Column
                        rowData.add(resultSet.getString(i));
                    }
                    buildRecord = new Record(id,rowData[1],rowData[2],rowData[3],rowData[4],rowData[5],rowData[6],rowData[7],
                            rowData[8],rowData[9],rowData[10],rowData[11],rowData[12],rowData[13],rowData[14],rowData[15],rowData[16])
                    if (row == null) {
                        System.out.println("row was null");
                    } else {
                        System.out.println("Row [1] added " + row);

                    }
                }
            } catch (SQLException e){
                e.printStackTrace();
            }

        } else{
            generateDB();
        }
    }
    public void writeToDB(){

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
