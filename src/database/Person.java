package database;

public class Person {
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String email;
    private double amount;

    public Person(String firstName, String lastName, String phoneNo, String email, double amount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.email = email;
        this.amount = amount;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getPhoneNo() {
        return this.phoneNo;
    }

    public String getEmail() {
        return this.email;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
