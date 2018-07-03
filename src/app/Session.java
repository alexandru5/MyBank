package app;

import database.*;

import java.util.Scanner;

/**
 * class that represents a user session
 */
public class Session {
    private static Session session;
    private static final String fileName = "src\\database\\db_log.txt";
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
     * getter for customer object (customer's data)
     * @return a customer object
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * read customer's data from stdin
     */
    public void readCustomer() {
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

        customer = new Customer(fName, lName, phoneNo, email, amount, period);
    }

    /**
     * init session with database and customer
     */
    public void initSession() {
        info.loadDatabase();
        readCustomer();
    }

    /**
     * compute the amount paid at end of loan and set it to customer
     */
    public void computeAmountToPay() {
        double amountWanted = customer.getAmount();
        double amountToPay = 0;

        for (int i = 0; i < info.getNoOfStakeholders() && amountWanted > 0; i++) {
            double rest;
            double amountOwned = info.getDatabase().get(i).getAmount();

            rest = amountOwned < amountWanted ? amountOwned : amountWanted;
            amountToPay += rest + rest * info.getDatabase().get(i).getIntRate() * customer.getPeriod() / 12;
            amountWanted -= rest;
        }
        customer.setAmountToPay(amountToPay);
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
     */
    public void computeLoan() {
        computeAmountToPay();
        computeMonthlyInstallment();
        computeDAE();
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
