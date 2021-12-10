package turfcuttinggui;

import java.sql.*;


public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection() {
        String databaseName     = "TurfCuttingDB";
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
    public void quickQuery(String query){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

}
