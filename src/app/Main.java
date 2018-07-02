package app;

import database.Database;

public class Main {
    private static final String fileName = "src\\database\\db_log.txt";
    private static Database info = new Database(3, fileName);

    public static void main(String[] args) {
        //System.out.println("Hello Friend!");
        info.loadDatabase();
        info.printInfos();

    }
}
