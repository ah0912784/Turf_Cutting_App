package turfcuttinggui;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

//handle the manipulation of data from java query and the export of java query to csv
// file path for importing data
//ID	FULL-NAME	new or old	Address	City	State	zip code	Home phone	cell phone	email 	Work email	ADJ-HIRE-DATE	ANNIVERS-DATE	SENIOR-DATE	DEPARTMENT.NAME	LOCATION.DESCRIPTION	POSITION.DESCRIPTION	Results
/* RECORD POSITIONS
[0]  = ID
[1]  = FULL_NAME
[2]  = NEW_OR_OLD
[3]  = ADDRESS
[4]  = CITY
[5]  = STATE
[6]  = ZIP_CODE
[7]  = HOME_PHONE
[8]  = CELL_PHONE
[9]  = EMAIL
[10]  = WORK_EMAIL
[11] = ADJ_HIRE_DATE
[12] = ANNIVERSARY_DATE
[13] = SENIOR_DATE
[14] = DEPT_NAME
[15] = LOCATION_DESCRIPTION
[16] = POSITION_DESCRIPTION
[17] = RESULTS

 */
public class ImportCSV {
    private final String usrName = System.getProperty("user.name");
    public  String filePath = ("C:\\Users\\"+usrName+"\\OneDrive\\Documents\\TurfCuttingDB\\turfcuttingcsv.csv");
    private final ArrayList<Integer> aryCSVIDs      = new ArrayList();
    private final ArrayList<Integer> aryDatabaseIDs = new ArrayList<>();
    private final ArrayList<Record>  aryCSV_Records = new ArrayList<>();
    private final ArrayList<Record> aryDB_Records   = new ArrayList<>();
    private final String[] aryColumnNames = {"ID","FULL_NAME","NEW_OR_OLD","ADDRESS","CITY","STATE","ZIP_CODE","HOME_PHONE","CELL_PHONE",
            "EMAIL","WORK_EMAIL", "ADJ_HIRE_DATE","ANNIVERSARY_DATE","SENIOR_DATE","DEPT_NAME","LOCATION_DESCRIPTION",
            "POSITION_DESCRIPTION","RESULTS"};

    //build csv import
    //will be called in main function to execute all of these tasks
    public void masterCall() throws SQLException {
        DatabaseConnection conn = new DatabaseConnection();

        try {
            conn.generateDB();
            conn.createPersonsTable();
        } catch (SQLException e){

        } finally {
            readCSV();
            readDataBase();
            manageDB(aryDB_Records,aryCSV_Records);
        }

        //manageDB(aryDB_Records,aryCSV_Records);
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
        CSVReader reader = null;
        String[] rowData;
        try (FileReader fr = new FileReader(filePath)){
            reader = new CSVReader(fr);
            reader.skip(1);
            int i = 0;
            while((rowData = reader.readNext()) != null) {
                System.out.println(rowData[1]);

                aryCSV_Records.add(recordBuilder(rowData));



            }
//            System.out.println(reader.getSkipLines()+" lines skipped");
//            System.out.println(reader.getLinesRead()+" Lines read");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(aryCSV_Records.size()+" final size of the records from the csv");

    }
    public void readDataBase(){
        DatabaseConnection dbConnect = new DatabaseConnection();

            try {
                String connectQuery = "SELECT * FROM Persons";
                ResultSet resultSet = dbConnect.getResultSet(connectQuery);
                while(resultSet.next()) {
                    String[] rowData = new String[resultSet.getMetaData().getColumnCount()];
                    for(int i=1 ; i <= resultSet.getMetaData().getColumnCount(); i++){
                        //Iterate Column
                        rowData[i-1] = resultSet.getString(i);
                    }
                   aryDB_Records.add(recordBuilder(rowData));
                }
                resultSet.close();
                Collections.sort(aryDatabaseIDs);
            } catch (SQLException e){
                e.printStackTrace();
            }
            System.out.println(aryDB_Records.size()+" record(s) have been read");
        }

    //FULL_NAME,ADDRESS,CITY,STATE,ZIP_CODE,HOME_PHONE,CELL_PHONE,EMAIL,WORK_EMAIL,ADJ_HIRE_DATE,ANNIVERSARY_DATE,SENIOR_DATE,DEPT_NAME,LOCATION_DESCRIPTION,POSITION_DESCRIPTION,RESULTS
    public void insertDB(Record record){
        DatabaseConnection mySql = new DatabaseConnection();
        String sqlQuery = "INSERT INTO Persons ("+ createColumnString(0,aryColumnNames.length)+") "+
                "VALUES ("+prepareQueryData(record,0,aryColumnNames.length)+")";
        mySql.DMLQuery(sqlQuery, "Record Inserted");
    }


    public void manageDB(ArrayList<Record> dbRecords, ArrayList<Record> csvRecord){
        int counter = 0;
        System.out.println(csvRecord.size()+" csvRecord size");
        for (int i = 0; i < dbRecords.size()-1; i++){
            for (int ii = 0; ii < csvRecord.size()-1; ii++ ){
                System.out.println(ii+" times in inner loop");
                //System.out.println(csvRecord.get(ii).getID());
                if (dbRecords.get(i).getID() == csvRecord.get(ii).getID()){
                    System.out.println(ii);
                    updateDB(csvRecord.get(ii));

                }
                counter = ii;
            }
            csvRecord.remove(counter);
        }
        for (int i = 0; i < csvRecord.size();i++){
            insertDB(csvRecord.get(i));
        }
    }

    public void updateDB(Record record){
        DatabaseConnection mySql = new DatabaseConnection();
        String updateQuery = "UPDATE Persons SET ";
        String[] recordArray = record.getStringArray();
        ArrayList<String> newRecordList = new ArrayList<>();
        ArrayList<String> newColumnName = new ArrayList<>();
        for (int i = 1; i < recordArray.length; i++){
            if (recordArray[i].isEmpty() == false){
                newRecordList.add(recordArray[i]);
                newColumnName.add(aryColumnNames[i]);
            }
        }
        for (int i = 1; i < newColumnName.size(); i++){
           if (newColumnName.size()-1 == i) {
               updateQuery += newColumnName.get(i) + " =  '" + newColumnName.get(i) + "' WHERE ID = "+record.getID();
           } else {
               updateQuery += newColumnName.get(i) + " =  '" + newColumnName.get(i) + "',";
           }
        }
        mySql.DMLQuery(updateQuery,"Record Updated");
    }

    public Record recordBuilder(String[] rowData){
        Record record;
        //System.out.println(rowData);
        int id = Integer.parseInt(rowData[0]);
        record = new Record(id, rowData[1], rowData[2], rowData[3], rowData[4], rowData[5], rowData[6], rowData[7],
                rowData[8], rowData[9], rowData[10], rowData[11], rowData[12], rowData[13], rowData[14], rowData[15], rowData[16], rowData[17]);
        return record;
    }
    //prepare record for query. used to fill null data types and format the query;
    public String prepareQueryData(Record record,int start,int fin){
        String[] recordArray = record.getStringArray();
        String query = "'"+recordArray[start]+"',";

        for (int i = start+1; i < fin; i++){
            if (recordArray[i].isEmpty() == true){
                recordArray[i] = "NULL";
            }
            if (i == fin-1) {
                query += "'"+recordArray[i]+"'";
            } else{
            query += "'"+recordArray[i]+"',";
        }
        }

        return query;
    }

}
