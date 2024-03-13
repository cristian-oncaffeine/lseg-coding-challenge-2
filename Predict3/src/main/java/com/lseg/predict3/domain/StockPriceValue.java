package com.lseg.predict3.domain;

import java.text.DecimalFormat;

public class StockPriceValue {

    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private double value;

    public StockPriceValue() {

    }

    public static StockPriceValue from(Double price) {
        StockPriceValue result = new StockPriceValue();
        result.value = price;
        return result;
    }

    public double getValue() {
        return value;
    }
    public static StockPriceValue from(String value) {
        if(value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Invalid StockPriceValue (is empty)");
        }
        try {
            double doubleValue = Double.parseDouble(value);
            StockPriceValue result = new StockPriceValue();
            result.value = doubleValue;
            return result;
        } catch (NullPointerException ex) {
            throw new IllegalArgumentException("Empty stock price value");
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("Invalid decimal stock price value " + value);
        }
    }
    public String toString() {
        return String.format("%.2f", value);
    }
}
