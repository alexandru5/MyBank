package app;

import java.sql.SQLException;

/**
 * Main class that starts a session
 */
public class Main {
    private static Session session;

    /**
     * main function
     * @param args TODO
     */
    public static void main(String[] args) throws SQLException {
        System.out.println("Hello Friend!");
        session = Session.getInstance();
        session.initSession();

        if (!session.computeLoan()) {
            System.out.println("Sowy but we don't have enough money for you, my friend! :*");
            System.exit(0);
        }

        session.printLoan();
    }
}
