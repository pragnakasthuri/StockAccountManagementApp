package com.bridgelabz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StockPortfolio {

    private List<Stock> stockList = new ArrayList<>();
    private double totalStocksValue;

    public double getTotalStocksValue() {
        return totalStocksValue;
    }

    public void setTotalStocksValue(double totalStocksValue) {
        this.totalStocksValue = totalStocksValue;
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Stock Account Management Application ");

        StockPortfolio stockPortfolio = new StockPortfolio();
        stockPortfolio.fillStockList();
        stockPortfolio.calculateStockValue();
        stockPortfolio.printStockReport();
    }

    private void calculateStockValue() {
        for (Stock stock : this.stockList) {
            double stockValue = stock.getNumberOfShares() * stock.getSharePrice();
            stock.setStockValue(stockValue);
            this.totalStocksValue += stockValue;
        }
    }

    private void printStockReport() {
        // System.out.println("SNo\tStock Name\tTotal No.Of Shares\tShare Value\tTotal Stock Value");
        int cnt = 1;
        for(Stock stock : stockList) {
            System.out.print(cnt+ "\t" + stock);
            cnt++;
        }
        System.out.println("Total Stock Value :" +this.totalStocksValue);
    }

    private void fillStockList() {
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



