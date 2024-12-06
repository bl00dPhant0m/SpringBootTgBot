package ru.bl00dphant0m.springboottgbot.model;



public class CryptoData {
    private String name;
    private String symbol;
    private int rank;
    private double price;
    private double percentChange24h;
    private double athPrice;
    private double percentFromAth;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPercentChange24h() {
        return percentChange24h;
    }

    public void setPercentChange24h(double percentChange24h) {
        this.percentChange24h = percentChange24h;
    }

    public double getAthPrice() {
        return athPrice;
    }

    public void setAthPrice(double athPrice) {
        this.athPrice = athPrice;
    }

    public double getPercentFromAth() {
        return percentFromAth;
    }

    public void setPercentFromAth(double percentFromAth) {
        this.percentFromAth = percentFromAth;
    }

    @Override
    public String toString() {
        return "Криптовалюта: " + name + " (" +symbol + ")" + "\n" +
                "Ранг: № " + rank + "\n" +
                "Текущая цена: $" + price + "\n" +
                "Изменение за 24 часа: " + percentChange24h + "%" + "\n" +
                "Исторический максимум: $" + athPrice + " (" + percentFromAth + "% от текущей)";
    }
}
