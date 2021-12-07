package turfcuttinggui;

import java.io.*;
import java.sql.*;
import java.text.*;

import java.util.Date;
//"C:\Users\rocke\Downloads
public class ExportCSV {

    public Statement statementExp;
    private BufferedWriter fileWriter;


    public void export(String table,String mySqlQuery) {
        String usrName = System.getProperty("user.name");
        String dir = "C:\\Users\\"+usrName+"\\Downloads";
        File file = new File(dir);

        String csvFileName = getFileName(table.concat("_Export"));
        try{

            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();

            Statement statement = connectDB.createStatement();
            System.out.println(mySqlQuery);


            ResultSet rs = statement.executeQuery(mySqlQuery);
            File actualFile = new File(file, csvFileName);
            fileWriter = new BufferedWriter(new FileWriter(actualFile));

            int columnCount = writeHeaderLine(rs);

            while (rs.next()) {
                String line = "";

                for (int i = 1; i <= columnCount; i++) {
                    Object valueObject = rs.getObject(i);
                    String valueString = "";

                    if (valueObject != null) valueString = valueObject.toString();

                    if (valueObject instanceof String) {
                        valueString = "\"" + escapeDoubleQuotes(valueString) + "\"";
                    }

                    line = line.concat(valueString);

                    if (i != columnCount) {
                        line = line.concat(",");
                    }
                }

                fileWriter.newLine();
                fileWriter.write(line);
            }

            //statementExp.close();
            fileWriter.close();

        } catch (SQLException e) {
            System.out.println("Datababse error:");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File IO error:");
            e.printStackTrace();
        }

    }

    private String getFileName(String baseName) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String dateTimeInfo = dateFormat.format(new Date());
        return baseName.concat(String.format("_%s.csv", dateTimeInfo));
    }

    private int writeHeaderLine(ResultSet result) throws SQLException, IOException {
        // write header line containing column names
        ResultSetMetaData metaData = result.getMetaData();
        int numberOfColumns = metaData.getColumnCount();
        String headerLine = "";

        // exclude the first column which is the ID field
        for (int i =1; i <= numberOfColumns; i++) {
            System.out.println("In the for loop "+ i);
            String columnName = metaData.getColumnName(i);
            headerLine = headerLine.concat(columnName).concat(",");
        }

        fileWriter.write(headerLine.substring(0, headerLine.length()-1));

        return numberOfColumns;
    }

    private String escapeDoubleQuotes(String value) {
        return value.replaceAll("\"", "\"\"");
    }

//        exporter.export("review");
//        exporter.export("product");
//    }


}
