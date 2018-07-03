package app;

/**
 * Main class that starts a session
 */
public class Main {
    private static Session session;

    /**
     * main function
     * @param args TODO
     */
    public static void main(String[] args) {
        System.out.println("Hello Friend!");
        session = Session.getInstance();
        session.initSession();
        session.computeLoan();
        session.printLoan();
    }
}
