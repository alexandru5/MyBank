public class Person {
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
        this.intRate = intRate;
    }

}
