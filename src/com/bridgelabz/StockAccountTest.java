package com.bridgelabz;

/**
 * StockAccountTest class to check the debit method
 */
public class StockAccountTest {
    public static void main(String[] args) {
        StockPortfolio portfolio = new StockPortfolio();
        portfolio.fillStockList();
        portfolio.setAccountBalance(50000.0);
        portfolio.debit(120000);
    }
}
