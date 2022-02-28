package com.bridgelabz;

/**
 * Class to hold stock information
 */
class Stock {
    private String stockName;
    private int numberOfShares;
    private double sharePrice;
    private double stockValue;

    /**
     * @param stockName
     * @param numberOfShares
     * @param sharePrice
     */
    Stock(String stockName, int numberOfShares, double sharePrice){
        this.stockName = stockName;
        this.numberOfShares = numberOfShares;
        this.sharePrice = sharePrice;
    }

    public Stock() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return this.stockName.equals(stock.stockName);
    }

    public double getStockValue() {
        return stockValue;
    }

    public void setStockValue(double stockValue) {
        this.stockValue = stockValue;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public int getNumberOfShares() {
        return numberOfShares;
    }

    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    public double getSharePrice() {
        return sharePrice;
    }

    public void setSharePrice(double sharePrice) {
        this.sharePrice = sharePrice;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stockName='" + stockName + '\'' +
                ", sharePrice=" + sharePrice +
                '}';
    }
}
