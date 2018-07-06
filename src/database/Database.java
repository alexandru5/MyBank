package database;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Scanner;

/**
 * Class that load the stakeholders contacts and details
 */
public class Database {
    private final int noOfStakeholders;
    private final File dbFile;
    private ArrayList<Stakeholder> database;

    /**
     * database constructor
     * @param noOfStakeholders number of stakeholders for database
     * @param fileName name of the stakeholders' log file
     */
    public Database(int noOfStakeholders, String fileName) {
        this.noOfStakeholders = noOfStakeholders;
        this.database = new ArrayList<>();
        dbFile = new File(fileName);

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
    public ArrayList<Stakeholder> getDatabase() {
        return this.database;
    }

    /**
     * read stakeholders from file and load the into database
     */
    public void readStakeholders() {
        Scanner scanner;

        try {
            scanner = new Scanner(this.dbFile);
            scanner.useDelimiter(",");

            for (int i = 0; i < this.noOfStakeholders; i++) {
                String fName = scanner.next();
                String lName = scanner.next();
                String phoneNo = scanner.next();
                String email = scanner.next();
                double amount = Double.parseDouble(scanner.next());
                double intRate = Double.parseDouble(scanner.next());
                this.database.add(new Stakeholder(fName, lName, phoneNo, email, amount, intRate));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
    public void loadDatabase() {
        this.readStakeholders();
        this.sortStakeholders();
    }
}
