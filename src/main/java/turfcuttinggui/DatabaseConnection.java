package turfcuttinggui;

import java.sql.*;


public class DatabaseConnection {
    public Connection databaseLink;
    public String dbName = "TurfCuttingDB";
    public String personsTable = "Persons";

    public Connection getConnection() {
        String databaseName     = dbName;
        String databaseUser     = "root";
        String databasePassword = "password";
        String url              = "jdbc:mysql://localhost:1433/" + databaseName;



        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser,databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return databaseLink;
    }
    public ResultSet getResultSet(String query){
        ResultSet resultSet = null;
        try {
            DatabaseConnection dbConnect = new DatabaseConnection();
            Connection connection = dbConnect.getConnection();
            String connectQuery = query;
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(connectQuery);

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Coming from getResultSet Method");

        }
        return resultSet;
    }
    public void DMLQuery(String query, String message){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);
            statement.close();
            System.out.println(message);
        } catch (SQLException e){
            System.out.println(query+" failed");
            e.printStackTrace();
        }
    }
    public void createPersonsTable() throws SQLException {
        String createTablequery = "create table Persons ( " +
                "ID int NOT NULL, " +
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
                "POSITION_DESCRIPTION varchar(255), " +
                "RESULTS varchar(255), " +
                "primary key(ID));";
        DMLQuery(createTablequery, "create table success");
    }
    public void generateDB() throws SQLException{
        final String DB_URL = "jdbc:mysql://localhost:1433/";
        final String USER = "root";
        final String PASS = "password";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS) ;
            Statement stmt = conn.createStatement()
        ) {
            System.out.println("We got to generate db method");
            String sql = "CREATE DATABASE "+dbName;
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully...");

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean checkForDB(){
        //AND table_name = '"+personsTable+
        System.out.println("looking for a database");
        boolean dbExists = false;
        String   query = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = '"+dbName+"'";
        Connection connection = getConnection();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            if(rs.getInt("COUNT(*)") > 0){
                System.out.println("well is it true");
                dbExists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dbExists;
    }

    boolean tableExistsSQL(String tableName) throws SQLException {
        boolean tableExists = false;
        try {

            ResultSet resultSet = getResultSet("SELECT * FROM Persons");
            resultSet.next();
            if (resultSet.getInt(1) != 0) {
                tableExists = true;
            }
        }catch (SQLException e){
            //e.printStackTrace();
            System.out.println("Error from tableExistsSQL");
            createPersonsTable();
        }
        return tableExists;
    }
}
