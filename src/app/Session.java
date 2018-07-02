package app;

import database.*;

import java.util.Scanner;

public class Session {
    private static Session session;
    private static final String fileName = "src\\database\\db_log.txt";
    private static Database info;
    private Customer customer;

    private Session() {
        info = new Database(3, fileName);
    }

    public static Session getInstance () {
        if (session == null)
            session = new Session();
        return session;
    }

    public Customer getCustomer() {
        return customer;
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

    public void initSession() {
        info.loadDatabase();
        readCustomer();
    }

    public void computeAmountToPay() {
        Customer currCustomer = getCustomer();
        double amountWanted = currCustomer.getAmount();
        double amountToPay = 0;

        for (int i = 0; i < info.getNoOfStakeholders() && amountWanted > 0; i++) {
            double rest;
            double amountOwned = info.getDatabase().get(i).getAmount();

            rest = amountOwned < amountWanted ? amountOwned : amountWanted;
            amountToPay += rest + rest * info.getDatabase().get(i).getIntRate() * currCustomer.getPeriod() / 12;
            amountWanted -= rest;
        }
        currCustomer.setAmountToPay(amountToPay);
    }

    public void computeMonthlyInstallment() {
        customer.setMonthlyInstallment(customer.getAmountToPay() / 12);
    }

    public void computeDAE() {
        double dae = (customer.getAmountToPay() - customer.getAmount()) * 12
                            / (customer.getAmount() * customer.getPeriod());
        customer.setDae(dae);

    }

    public void computeLoan() {
        computeAmountToPay();
        computeMonthlyInstallment();
        computeDAE();
    }
}
