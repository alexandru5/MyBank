package app;

import database.*;
import java.util.Scanner;
import java.sql.*;

/**
 * class that represents a user session
 */
public class Session {
    private static Session session;
    private static final String FILE_NAME = "src\\database\\db_log.txt";
    private static final String INVALID_INPUT_ERROR = "!!!!Invalid input!!!! \nPlease try again!";
    public static final String VALID_NAME_REGEX = "[a-zA-Z]+";
    public static final String VALID_PHONE_NO_REGEX = "\\+?[0-9]{10,16}";
    public static final String VALID_EMAIL_ADDRESS_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
                                                            "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\" +
                                                            "[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*" +
                                                            "[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4]" +
                                                            "[0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|" +
                                                            "[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]" +
                                                            "|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    public static final String VALID_AMOUNT_REGEX = "[0-9]+[.[0-9]+]?";
    public static final String VALID_PERIOD_REGEX = "[1-9]*[0-9]*";

    private static Database info;
    private Customer customer;

    /**
     * session constructor
     */
    private Session() {
        info = new Database(3, FILE_NAME);
    }

    /**
     * getter for singleton instance
     * @return session's instance
     */
    public static Session getInstance () {
        if (session == null)
            session = new Session();
        return session;
    }

    /**
     * check if valid name
     * @param name name to be checked
     * @return true if valid name else false
     */
    public boolean validName(String name) {
        return name.matches(VALID_NAME_REGEX);
    }

    /**
     * check if valid phone number
     * @param phoneNo phone number to be checked
     * @return true if valid phone number else false
     */
    public boolean validPhoneNo(String phoneNo) {
        return phoneNo.matches(VALID_PHONE_NO_REGEX);
    }

    /**
     * check if valid email
     * @param email email to be checked
     * @return true if valid email else false
     */
    public boolean validEmail(String email) {
        return email.matches(VALID_EMAIL_ADDRESS_REGEX);
    }

    /**
     * check if valid amount
     * @param amount amount to be checked
     * @return true if valid amount else false
     */
    public boolean validAmount(String amount) {
       return amount.matches(VALID_AMOUNT_REGEX);
    }

    /**
     * check if valid period
     * @param period period to be checked
     * @return true if valid period else false
     */
    public boolean validPeriod(String period) {
       return period.matches(VALID_PERIOD_REGEX);
    }

    /**
     * read customer's data from stdin
     * @return true if valid customer else false
     */
    public boolean readCustomer() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("First name: ");
        String fName = scanner.next();
        if (!validName(fName)) return false;

        System.out.print("Last name: ");
        String lName = scanner.next();
        if (!validName(lName)) return false;

        System.out.print("Phone No: ");
        String phoneNo = scanner.next();
        if (!validPhoneNo(phoneNo)) return false;

        System.out.print("Email: ");
        String email = scanner.next();
        if (!validEmail(email)) return false;

        System.out.print("Amount wanted: ");
        String amount = scanner.next();
        if (!validAmount(amount)) return false;
        double amountDouble = Double.parseDouble(amount);

        System.out.print("Period: ");
        String period = scanner.next();
        if (!validPeriod(period)) return false;
        int periodInt = Integer.parseInt(period);
        
        customer = new Customer(fName, lName, phoneNo, email, amountDouble, periodInt);
        return true;
    }

    /**
     * init session with database and customer
     */
    public void initSession() throws SQLException {
        info.loadDatabase();
        while (!readCustomer())
            System.out.println(INVALID_INPUT_ERROR);

        info.insertIntoDatabase(customer);
    }

    /**
     * compute the amount paid at end of loan and set it to customer
     * @return true if enough money else false
     */
    public boolean computeAmountToPay() {
        double amountWanted = customer.getAmount();
        double amountToPay = 0;

        for (int i = 0; i < info.getNoOfStakeholders() && amountWanted > 0; i++) {
            double rest;
            double amountOwned = info.getDatabase().get(i).getAmount();

            rest = amountOwned < amountWanted ? amountOwned : amountWanted;
            amountToPay += rest + rest * info.getDatabase().get(i).getIntRate() * customer.getPeriod() / 12;
            amountWanted -= rest;
        }

        if (amountWanted != 0) return false;

        customer.setAmountToPay(amountToPay);
        return true;
    }

    /**
     * compute monthly installment form total amount to be paid
     */
    public void computeMonthlyInstallment() {
        customer.setMonthlyInstallment(customer.getAmountToPay() / 12);
    }

    /**
     * compute DAE value
     */
    public void computeDAE() {
        double dae = (customer.getAmountToPay() - customer.getAmount()) * 12
                            / (customer.getAmount() * customer.getPeriod());
        customer.setDae(dae);
    }

    /**
     * compute all details of a loan
     * @return true if valid loan else false
     */
    public boolean computeLoan() {
        if (!computeAmountToPay()) return false;
        computeMonthlyInstallment();
        computeDAE();
        return true;
    }

    /**
     * print load details
     */
    public void printLoan() {
        System.out.format("You ( %s %s ) will actually pay : %.3f\nMonthly Installment = %.3f\nDAE = %.3f%%  \n",
                            customer.getFirstName(), customer.getLastName(), customer.getAmountToPay(),
                            customer.getMonthlyInstallment(), customer.getDae() * 100);
    }
}
