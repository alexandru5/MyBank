package database;

/**
 * class that represents a person
 */
public class Person {
    /**
     * first name of a person
     */
    //private int id;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String email;
    private double amount;

    /**
     * person constructor
     * @param firstName first name
     * @param lastName last name
     * @param phoneNo phone number
     * @param email email
     * @param amount amount invested/wanted
     */
    public Person(String firstName, String lastName, String phoneNo, String email, double amount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.email = email;
        this.amount = amount;
    }

    /**
     * getter for first name
     * @return first name
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * getter for last name
     * @return last name
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * getter for amount wanted/invested (depends on customer/stakeholder)
     * @return the amount wanted/invested
     */
    public double getAmount() {
        return this.amount;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public double getIntRate() {
        return 0;
    }
    public int getPeriod() {
        return 0;
    }
}
