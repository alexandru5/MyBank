package database;

public class Stakeholder extends Person implements Comparable<Stakeholder> {
    private double intRate;

    public Stakeholder(String firstName, String lastName, String phoneNo, String email,
                            double amount, double intRate) {
        super(firstName, lastName, phoneNo, email, amount);
        this.intRate = intRate;
    }

    @Override
    public int compareTo(Stakeholder o) {
        if (this.intRate < o.intRate) return -1;
        if (this.intRate == o.intRate) return 0;
        if (this.intRate > o.intRate) return 1;
        return 1;
    }

    public double getIntRate() {
        return this.intRate;
    }
}
