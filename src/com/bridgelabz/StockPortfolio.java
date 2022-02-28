package com.bridgelabz;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Class to hold user portfolio information
 */
public class StockPortfolio {

    private String fileName;
    private double accountBalance;
    private double totalStocksValue;
    private List<Stock> stockList = new ArrayList<>();
    private static List<Stock> companyStockList = new ArrayList<>();

    static {
        fillCompanyStockList("/Users/nareshadla/Desktop/Pragna/Projects/StockAccountManagementApp/ComapnyShares");
    }

    /**
     * @param fileName
     */
    StockPortfolio(String fileName){
        this.fileName = fileName;
        this.fillStockList();
    }

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


    /**
     * To read stock name, number of shares and share price from Stock file and add it to stock list
     */
    public void fillStockList() {
        BufferedReader bufferedReader = null;
        try {
            String line = null;
            bufferedReader = new BufferedReader(new FileReader(this.fileName));
            while ((line = bufferedReader.readLine()) != null) {
                String[] stockDetails = line.split(",");
                Stock stock = new Stock(stockDetails[1], Integer.parseInt(stockDetails[2]), Double.parseDouble(stockDetails[3]));
                this.stockList.add(stock);
            }
            this.calculateStockValue();
        }catch (Exception e){
            System.err.println("IO Exception occurred"+ e.getMessage());
        }
        finally {
            try {
                bufferedReader.close();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param fileName
     * To read stock name and share price from companyShares file and add it to companyStockList
     */
    public static void fillCompanyStockList(String fileName) {
        BufferedReader bufferedReader = null;
        try {
            String line = null;
            bufferedReader = new BufferedReader(new FileReader(fileName));
            while ((line = bufferedReader.readLine()) != null) {
                String[] stockDetails = line.split(",");
                Stock stock = new Stock();
                stock.setStockName(stockDetails[1]);
                stock.setSharePrice(Integer.parseInt(stockDetails[2]));
                companyStockList.add(stock);
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
    public void calculateStockValue() {
        this.totalStocksValue = 0;
        for (Stock stock : this.stockList) {
            double stockValue = stock.getNumberOfShares() * stock.getSharePrice();
            stock.setStockValue(stockValue);
            this.totalStocksValue += stockValue;
        }
    }

    /**
     * prints the stock report by iterating through for each loop
     */
    public void printStockReport() {
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
        System.out.println("Company Stock List: " +companyStockList);
    }

    /**
     * @param amount
     * @param stockName
     */
    public void buy(int amount, String stockName) {
        if(amount <= this.accountBalance) {
            Predicate<Stock> predicate = (stk) -> stk.getStockName().equals(stockName);
            Optional<Stock> optionalStock = companyStockList.stream().filter(predicate).findFirst();
            if(optionalStock.isPresent()){
                Stock stock = optionalStock.get();
                int numberOfShares = (int)(amount / stock.getSharePrice());
                Predicate<Stock> predicate1 = (stk) -> stk.getStockName().equals(stockName);
                Optional<Stock> optionalStock1 = this.stockList.stream().filter(predicate1).findFirst();
                if(optionalStock1.isPresent()) {
                    Stock stock1 = optionalStock1.get();
                    this.stockList.remove(stock1);
                    stock1.setNumberOfShares(stock1.getNumberOfShares() + numberOfShares);
                    stock1.setStockValue(stock1.getStockValue() + (stock1.getSharePrice() * numberOfShares));
                    this.stockList.add(stock1);
                } else {
                    stock = new Stock(stockName, numberOfShares, stock.getSharePrice());
                    this.stockList.add(stock);
                }
            }else {
                Stock newStock = new Stock();
                newStock.setStockName(stockName);
                newStock.setSharePrice(100);
                companyStockList.add(newStock);
                newStock.setNumberOfShares(amount/100);
                this.stockList.add(newStock);
            }
            this.accountBalance -= amount;
            System.out.println("Current Account balance after buying: " +this.accountBalance);
        }else{
            System.out.println("No sufficient funds available");
        }
    }

    /**
     * @param amount
     * @param stockName
     */
    public void sell(int amount, String stockName) {
        Predicate<Stock> predicate = (stk) -> stk.getStockName().equals(stockName);
        Optional<Stock> optionalStock = this.stockList.stream().filter(predicate).findFirst();
        if(optionalStock.isPresent()){
            Stock stock = optionalStock.get();
            this.stockList.remove(stock);
            this.accountBalance+=stock.getStockValue();
        }else {
            System.out.println("Could not find the stock in stock list");
        }
    }

    /**
     * @param fileName
     * @throws FileNotFoundException
     * Writes to Stock file
     */
    public void save(String fileName) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(new FileOutputStream(fileName));
        for (Stock stock : this.stockList) {
            printWriter.println(stock.getStockName()+"," + stock.getNumberOfShares() +"," +stock.getSharePrice());
            printWriter.flush();
        }
        printWriter.close();
    }
}



