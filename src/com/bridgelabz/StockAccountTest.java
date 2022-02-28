package com.bridgelabz;

/**
 * StockAccountTest class to check the debit method
 */
public class StockAccountTest {

    public static void main(String[] args) {
        System.out.println("Welcome to Stock Account Management Application\n");

        StockPortfolio stockPortfolio = new StockPortfolio("/Users/nareshadla/Desktop/Pragna/Projects/StockAccountManagementApp/stocks");
        stockPortfolio.printStockReport();
        stockPortfolio.setAccountBalance(10000);
        stockPortfolio.debit(300);

        stockPortfolio.buy(2000, "TATASKY");
        stockPortfolio.sell(1000, "TIPS");
        stockPortfolio.calculateStockValue();
        try {
            stockPortfolio.save("/Users/nareshadla/Desktop/Pragna/Projects/StockAccountManagementApp/stocks");
        }catch (Exception e) {
            System.err.println("IO Exception occurred" + e.getMessage());
        }
        stockPortfolio.printStockReport();
    }
}
