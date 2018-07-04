package app;

import database.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class that represents a user session
 */
public class Session {
    private static Session session;
    private static final String fileName = "src\\database\\db_log.txt";
    private static final String invalidInputError = "!!!!Invalid input!!!! \nPlease try again!";
    public static final Pattern VALID_NAME_REGEX =
            Pattern.compile("[A-Z]+", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_PHONE_NO_REGEX =
            Pattern.compile("\\+?[0-9]{10,16}", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final int phoneNoMaxLength = 16;
    private static final int phoneNoMinLength = 10;
    private static Database info;
    private Customer customer;

    /**
     * session constructor
     */
    private Session() {
        info = new Database(3, fileName);
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
        Matcher matcher = VALID_NAME_REGEX .matcher(name);
        return matcher.find();
    }

    /**
     * check if valid phone number
     * @param phoneNo phone number to be checked
     * @return true if valid phone number else false
     */
    public boolean validPhoneNo(String phoneNo) {
        Matcher matcher = VALID_PHONE_NO_REGEX.matcher(phoneNo);
        return matcher.find();

    }

    /**
     * check if valid email
     * @param email email to be checked
     * @return true if valid email else false
     */
    public boolean validEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        return matcher.find();
    }

    /**
     * read customer's data from stdin
     * @return true if valid customer else false
     */
    public boolean readCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("First name: ");
        String fName = scanner.next();
        System.out.print("Last name: ");
        String lName = scanner.next();
        System.out.print("Phone No: ");
        String phoneNo = scanner.next();
        System.out.print("Email: ");
        String email = scanner.next();
        System.out.print("Amount wanted: ");
        double amount = Double.parseDouble(scanner.next());
        System.out.print("Period: ");
        int period = Integer.parseInt(scanner.next());

        if (!validName(fName) || !validName(lName)  || !validPhoneNo(phoneNo) || !validEmail(email))
            return false;

        customer = new Customer(fName, lName, phoneNo, email, amount, period);
        return true;
    }

    /**
     * init session with database and customer
     */
    public void initSession() {
        info.loadDatabase();
        while (!readCustomer()) {
            System.out.println(invalidInputError);
        }
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
        System.out.println("You ( " + customer.getFirstName() + " "
                                + customer.getLastName() + " ) will actually pay "
                                + customer.getAmountToPay());
        System.out.println("Monthly Installment = " + customer.getMonthlyInstallment());
        System.out.println("DAE = " + customer.getDae() * 100 + "%");
    }
}
