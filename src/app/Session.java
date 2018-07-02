package app;

import database.Database;

public class Session {
    private static Session session;
    private static final String fileName = "src\\database\\db_log.txt";
    private static Database info;

    private Session() {
        info = new Database(3, fileName);
    }

    public static Session getInstance () {
        if (session == null)
            session = new Session();
        return session;
    }
    public void initSession() {
        info.loadDatabase();
        info.readCustomer();
        info.printInfos();
    }

    public double computeAmountToPay() {
        double amountWanted = info.getCustomer().getAmount();
        double amountToPay = 0;

        for (int i = 0; i < info.getNoOfStakeholders() && amountWanted > 0; i++) {
            double rest = 0;
            double amountOwned = info.getDatabase().get(i).getAmount();

            rest = amountOwned < amountWanted ? amountOwned : amountWanted;
            amountToPay += rest + rest * info.getDatabase().get(i).getIntRate() * info.getCustomer().getPeriod() / 12;
            amountWanted -= rest;
        }
        return amountToPay;
    }
}
