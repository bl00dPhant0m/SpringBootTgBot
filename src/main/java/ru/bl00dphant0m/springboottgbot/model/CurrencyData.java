package ru.bl00dphant0m.springboottgbot.model;




public class CurrencyData {
    private String charCode;
    private String name;
    private double value;
    private double previous;

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getPrevious() {
        return previous;
    }

    public void setPrevious(double previous) {
        this.previous = previous;
    }

    @Override
    public String toString() {
        return "Название: " + name + "(" + charCode +")" + "\n" +
                "Текущее значение: " + value + "\n" +
                "Предыдущее значение: " + previous;
    }
}
