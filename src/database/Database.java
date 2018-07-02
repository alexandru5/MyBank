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
    private Customer customer;

    public Database(int noOfStakeholders, String fileName) {
        this.noOfStakeholders = noOfStakeholders;
        this.database = new ArrayList<Stakeholder>();
        dbFile = new File(fileName);

    }

    public int getNoOfStakeholders() {
        return this.noOfStakeholders;
    }

    public ArrayList<Stakeholder> getDatabase() {
        return this.database;
    }

    public Customer getCustomer() {
        return this.customer;
    }

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

    public void sortStakeholders() {
        Collections.sort(this.database);
    }

    public void readCustomer() {
        Scanner scanner = new Scanner(System.in);
        String fName = scanner.next();
        String lName = scanner.next();
        String phoneNo = scanner.next();
        String email = scanner.next();
        double amount = Double.parseDouble(scanner.next());
        int period = Integer.parseInt(scanner.next());

        customer = new Customer(fName, lName, phoneNo, email, amount, period);
    }
    public void loadDatabase() {
        this.readStakeholders();
        this.sortStakeholders();
    }

    public void printInfos() {
        for (int i = 0; i < noOfStakeholders; i++)
            System.out.println(i + " : " + database.get(i).getLastName() + " | " + database.get(i).getIntRate());

        if (customer != null)
            System.out.println("Customer : " + customer.getLastName() + " -> " + customer.getAmount());
    }
}
