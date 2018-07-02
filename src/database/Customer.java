package database;

public class Customer extends Person {
    private int period;
    private double amountToPay;
    private double monthlyInstallment;
    private double dae;

    public void setAmountToPay(double amountToPay) {
        this.amountToPay = amountToPay;
    }

    public double getAmountToPay() {
        return amountToPay;
    }

    public double getMonthlyInstallment() {
        return monthlyInstallment;
    }

    public double getDae() {
        return dae;
    }

    public void setMonthlyInstallment(double monthlyInstallment) {
        this.monthlyInstallment = monthlyInstallment;
    }

    public void setDae(double dae) {
        this.dae = dae;
    }

    public Customer(String firstName, String lastName, String phoneNo, String email, double amount, int period) {
        super(firstName, lastName, phoneNo, email, amount);
        this.period = period;
    }

    public int getPeriod() {
        return this.period;
    }
}
