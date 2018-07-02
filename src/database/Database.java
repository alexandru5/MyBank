package database;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Scanner;

public class Database {
    private final int noOfStakeholders;
    private final File dbFile;
    private ArrayList<Stakeholder> database;

    public Database(int noOfStakeholders, String fileName) {
        this.noOfStakeholders = noOfStakeholders;
        this.database = new ArrayList<Stakeholder>();
        dbFile = new File(fileName);

    }

    public void readDatabase() {
        Scanner scanner;

        try {
            scanner = new Scanner(this.dbFile);
            scanner.useDelimiter(",");

            for (int i = 0; i < this.noOfStakeholders; i++) {
                String fName = scanner.next();
                String lName = scanner.next();
                String phoneNo = scanner.next();
                String email = scanner.next();
                String amount = scanner.next();
                String intRate = scanner.next();
                this.database.add(new Stakeholder(fName, lName, phoneNo, email,
                                    Double.parseDouble(amount), Double.parseDouble(intRate)));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sortDatabase() {
        Collections.sort(this.database);
    }

    public void loadDatabase() {
        this.readDatabase();
        this.sortDatabase();
    }

    public void printInfos() {
        for (int i = 0; i < noOfStakeholders; i++)
            System.out.println(database.get(i).getFirstName() + " " + database.get(i).getLastName() + " | " + database.get(i).getIntRate());
    }
}
