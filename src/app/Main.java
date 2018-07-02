package app;

public class Main {
    private static Session session;


    public static void main(String[] args) {
        System.out.println("Hello Friend!");
        session = Session.getInstance();
        session.initSession();
        session.computeLoan();
        System.out.println("You will actually pay " + session.getCustomer().getAmountToPay());
        System.out.println("Monthly Installment = " + session.getCustomer().getMonthlyInstallment());
        System.out.println("DAE = " + session.getCustomer().getDae());
    }
}
