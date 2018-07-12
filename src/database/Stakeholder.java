package database;

/**
 * Class that represents a stakeholder that extends a person
 */
public class Stakeholder extends Person implements Comparable<Stakeholder> {
    private double intRate;

    /**
     * stakeholder constructor
     * @param firstName first name
     * @param lastName last name
     * @param phoneNo phone number
     * @param email email
     * @param amount amount
     * @param intRate interest of a stakeholder
     */
    public Stakeholder(String firstName, String lastName, String phoneNo, String email,
                            double amount, double intRate) {
        super(firstName, lastName, phoneNo, email, amount);
        this.intRate = intRate;
    }

    /**
     *
     * @param o the second stakeholder to be compared
     * @return value of comparison
     */
    @Override
    public int compareTo(Stakeholder o) {
        if (this.intRate < o.intRate) return -1;
        if (this.intRate == o.intRate) return 0;
        if (this.intRate > o.intRate) return 1;
        return 1;
    }

    /**
     * getter for the interest of a stakeholder
     * @return the interest of a stakeholder
     */
    public double getIntRate() {
        return this.intRate;
    }

    @Override
    public String toString() {
        return "('" + getFirstName() + "', '" + getLastName() + "', '" + getPhoneNo() + "', '"
                + getEmail() + "', " + getAmount() + ", " + getIntRate() + ")";
    }
}
