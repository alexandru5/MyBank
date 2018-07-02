package app;

import database.Database;

public class Main {
    private static final Session session = Session.getInstance();


    public static void main(String[] args) {
        System.out.println("Hello Friend!");
        session.initSession();
        System.out.println(session.computeAmountToPay());
    }
}
