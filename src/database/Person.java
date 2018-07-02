package database;

public class Person implements Comparable<Person>{
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String email;
    private double amount;
    private double intRate;

    public Person(String firstName, String lastName, String phoneNo, String email,
                        double amount, double intRate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.email = email;
        this.amount = amount;
        this.intRate = intRate;
    }

    public Person(String firstName, String lastName, String phoneNo, String email,
                  double amount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.email = email;
        this.amount = amount;
        this.intRate = 0;
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

    public double getIntRate() {
        return this.intRate;
    }

    @Override
    public int compareTo(Person o) {
        if (this.intRate < o.intRate) return -1;
        if (this.intRate == o.intRate) return 0;
        if (this.intRate > o.intRate) return 1;
        return 1;
    }
}
