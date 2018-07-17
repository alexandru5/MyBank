package database;

import javax.xml.validation.Schema;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Class that load the stakeholders contacts and details
 */
public class Database {
    private final int noOfStakeholders;
    private final String fileName;
    private List<Stakeholder> database;
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final int NO_OF_LABELS = 6;
    private final String DB_URL = "jdbc:mysql://localhost:3306/sakila?verifyServerCertificate=false&useSSL=true";
    private final String INSERT_STAKEHOLDER_STATEMENT_SQL = "INSERT INTO stakeholders(FIRST_NAME, LAST_NAME, PHONE_NO, " +
            "                                                   EMAIL, AMOUNT, INTEREST) VALUES(?, ?, ?, ?, ?, ?)";
    private final String INSERT_CLIENT_STATEMENT_SQL = "INSERT INTO clients(FIRST_NAME, LAST_NAME, PHONE_NO, " +
            "                                                   EMAIL, AMOUNT, PERIOD) VALUES(?, ?, ?, ?, ?, ?)";
    private final String SELECT_STATEMENT_SQL = "SELECT FIRST_NAME, LAST_NAME, PHONE_NO, EMAIL, AMOUNT, INTEREST"
                                                + " FROM stakeholders ORDER BY INTEREST";

    static final String USER = "root";
    static final String PASS = "weed";
    Connection connection = null;
    PreparedStatement statement = null;

    /**
     * database constructor
     * @param noOfStakeholders number of stakeholders for database
     * @param fileName name of the stakeholders' log file
     */
    public Database(int noOfStakeholders, String fileName) {
        this.noOfStakeholders = noOfStakeholders;
        this.fileName = fileName;
        this.database = new ArrayList<>();

    }

    /**
     * getter for number of stakeholders
     * @return number of stakeholders
     */
    public int getNoOfStakeholders() {
        return this.noOfStakeholders;
    }

    /**
     * geter for the database that contains stakeholders' details
     * @return an array of stakeholders
     */
    public List<Stakeholder> getDatabase() {
        return this.database;
    }

    /**
     * creates a new Stakeholder from an array of strings
     * @param parts fields that describe a stakeholder
     * @return new stakeholder described by parts
     */
    public Stakeholder createStakeholder(String[] parts) {
        return new Stakeholder(parts[0], parts[1], parts[2], parts[3],
                                Double.parseDouble(parts[4]),
                                Double.parseDouble(parts[5]));
    }

    /**
     * get connection to database
     * @return the connection to database
     */
    private Connection getDBConnection() {
        Connection dbConnection = null;

        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            dbConnection = DriverManager.getConnection(DB_URL, USER, PASS);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return dbConnection;
    }

    /**
     * insert into Database new client
     * @param newClient to be inserted
     * @throws SQLException
     */
    public void insertIntoDatabase(Person newClient) throws SQLException {
        String insertStatement = newClient instanceof Stakeholder ? INSERT_STAKEHOLDER_STATEMENT_SQL : INSERT_CLIENT_STATEMENT_SQL;

        try{
            connection = getDBConnection();
            statement = connection.prepareStatement(insertStatement);
            statement.setString(1, newClient.getFirstName());
            statement.setString(2, newClient.getLastName());
            statement.setString(3, newClient.getPhoneNo());
            statement.setString(4, newClient.getEmail());
            statement.setDouble(5, newClient.getAmount());

            if (newClient instanceof Stakeholder)
                statement.setDouble(6, newClient.getIntRate());
            else
                statement.setInt(6, newClient.getPeriod());

            statement.executeUpdate();
        }catch(SQLException se){
            se.printStackTrace();
        }finally{
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    /**
     * read stakeholders from file and load them into database
     */
    public void readStakeholders() {
        try (Stream<String> readStream = Files.lines(Paths.get(fileName))){

            readStream.map(element -> element.split(","))
                      .forEach(parts -> {
                          try {
                              insertIntoDatabase(createStakeholder(parts));
                          } catch (SQLException e) {
                              e.printStackTrace();
                          }
                      });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getPartsFromSet(ResultSet rs) throws SQLException {
        String[] parts = new String[NO_OF_LABELS];

        for (int i = 1; i <= NO_OF_LABELS; i++)
            parts[i - 1] = rs.getString(i);

        return parts;
    }

    public void readStakeholdersFromDB() throws SQLException {
        try{
            connection = getDBConnection();
            statement = connection.prepareStatement(SELECT_STATEMENT_SQL);

            ResultSet result = statement.executeQuery();

            while (result.next())
                this.database.add(createStakeholder(getPartsFromSet(result)));

        }catch(SQLException se){
            se.printStackTrace();
        }finally{
            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
    }
    /**
     * sorts the stakeholders' array by interest rate
     */
    public void sortStakeholders() {
        Collections.sort(this.database);
    }

    /**
     * read and sort database
     */
    public void loadDatabase () throws SQLException {
        this.readStakeholdersFromDB();
        //for (Stakeholder s : database)
        //    System.out.println(s.toString());
        //this.sortStakeholders();
    }
}
