package database;

/**
 * Class that represents a customer that extends a person
 */
public class Customer extends Person {
    private int period;
    private double amountToPay;
    private double monthlyInstallment;
    private double dae;

    /**
     * customer constructor
     * @param firstName first name
     * @param lastName last name
     * @param phoneNo phone number
     * @param email email
     * @param amount amount
     * @param period period
     */
    public Customer(String firstName, String lastName, String phoneNo, String email,
                    double amount, int period) {
        super(firstName, lastName, phoneNo, email, amount);
        this.period = period;
    }

    /**
     * setter for amount to be payed at end of period
     * @param amountToPay amount to be payed
     */
    public void setAmountToPay(double amountToPay) {
        this.amountToPay = amountToPay;
    }

    /**
     * getter for amount to be payed at end of period
     * @return amount to be payed
     */
    public double getAmountToPay() {
        return amountToPay;
    }

    /**
     * getter for monthly installment of the loan
     * @return monthly installment
     */
    public double getMonthlyInstallment() {
        return monthlyInstallment;
    }

    /**
     * getter for DAE
     * @return DAE
     */
    public double getDae() {
        return dae;
    }

    /**
     * setter for monthly installment
     * @param monthlyInstallment the monthly installment value
     */
    public void setMonthlyInstallment(double monthlyInstallment) {
        this.monthlyInstallment = monthlyInstallment;
    }

    /**
     * setter for DAE
     * @param dae the value of DAE
     */
    public void setDae(double dae) {
        this.dae = dae;
    }

    /**
     * getter for the period of loan
     * @return the period of loan
     */
    public int getPeriod() {
        return this.period;
    }
}
