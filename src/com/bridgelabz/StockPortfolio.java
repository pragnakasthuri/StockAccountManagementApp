/**
 * Stock portfolio for displaying stock name, number of shares and share price
 * and total stock value as a list.
 */
package com.bridgelabz;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StockPortfolio {

    private List<Stock> stockList = new ArrayList<>();
    private double totalStocksValue;
    private double accountBalance;

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public double getTotalStocksValue() {
        return totalStocksValue;
    }

    public void setTotalStocksValue(double totalStocksValue) {
        this.totalStocksValue = totalStocksValue;
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Stock Account Management Application ");

        StockPortfolio stockPortfolio = new StockPortfolio();
        //stockPortfolio.fillStockListFromUser();
        stockPortfolio.fillStockList();
        stockPortfolio.calculateStockValue();
        stockPortfolio.printStockReport();
        stockPortfolio.debit(300);
    }

    /**
     * To read stock name, number of shares and share price and add it to stock list
     */
    public void fillStockList() {
        BufferedReader bufferedReader = null;
        try {
            String line = null;
            bufferedReader = new BufferedReader(new FileReader("/Users/nareshadla/Desktop/Pragna/Projects/StockAccountManagementApp/stocks"));
            while ((line = bufferedReader.readLine()) != null) {
                String[] stockDetails = line.split(",");
                Stock stock = new Stock(stockDetails[1], Integer.parseInt(stockDetails[2]), Double.parseDouble(stockDetails[3]));
                this.stockList.add(stock);
            }
        }catch (Exception e){
            System.err.println("IO Exception occurred"+ e.getMessage());
        }
    }

    /**
     * method to find the account balance after debiting
     */
    public void debit(double debitAmount) {
        if(this.accountBalance >= debitAmount) {
            this.accountBalance -= debitAmount;
            System.out.println("Current Account Balance: " +this.accountBalance);
        }else{
            System.out.println("Debit amount exceeded account balance ");
        }
    }

    /**
     * calculates each stock value and total stock value
     */
    private void calculateStockValue() {
        for (Stock stock : this.stockList) {
            double stockValue = stock.getNumberOfShares() * stock.getSharePrice();
            stock.setStockValue(stockValue);
            this.totalStocksValue += stockValue;
        }
    }

    /**
     * prints the stock report by iterating through for each loop
     */
    private void printStockReport() {
        System.out.printf("%5s %11s %18s %10s %10s", "SNO", "STOCK_NAME", "TOTAL_NO_OF_SHARES", "SHARE_VALUE", "TOTAL_STOCK_VALUE");
        System.out.println();
        System.out.println("-------------------------------------------------------------------------");
        int count = 1;
        for(Stock stock : stockList) {
            System.out.format("%5s %8s %10s %18s %15s", count, stock.getStockName(), stock.getNumberOfShares(), stock.getSharePrice(), stock.getStockValue());
            System.out.println();
            count++;
        }
        System.out.println();
        System.out.println("Total Stock Value :" +this.totalStocksValue);
    }

    /**
     * this method adds the user entered inputs to the list
     */
    private void fillStockListFromUser() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String userChoice = "Y";
            do {
                System.out.println("Please enter the Stock Name: ");
                String stockName = bufferedReader.readLine();
                System.out.println("Please enter Number Of Shares: ");
                int numberOfShares = Integer.parseInt(bufferedReader.readLine());
                System.out.println("Please enter Share Price: ");
                double sharePrice = Double.parseDouble(bufferedReader.readLine());

                Stock stock = new Stock(stockName, numberOfShares, sharePrice);
                this.stockList.add(stock);
                System.out.println("Do you want to continue (Y/N)");
                userChoice = bufferedReader.readLine();
            }while (userChoice.equalsIgnoreCase("Y"));
        }catch (IOException e){
            System.err.println("IO Exception occurred");
        }
    }
}



