package database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;

/**
 * Class that load the stakeholders contacts and details
 */
public class Database {
    private final int noOfStakeholders;
    private final String fileName;
    private ArrayList<Stakeholder> database;

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
    public ArrayList<Stakeholder> getDatabase() {
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
     * read stakeholders from file and load the into database
     */
    public void readStakeholders() {
        try (Stream<String> readStream = Files.lines(Paths.get(fileName))){

            readStream.map(element -> element.split(","))
                      .forEach(parts -> this.database.add(createStakeholder(parts)));
        } catch (IOException e) {
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
