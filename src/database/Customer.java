package database;

public class Customer extends Person {
    private int period;

    public Customer(String firstName, String lastName, String phoneNo, String email, double amount, int period) {
        super(firstName, lastName, phoneNo, email, amount);
        this.period = period;
    }

    public int getPeriod() {
        return this.period;
    }
}
