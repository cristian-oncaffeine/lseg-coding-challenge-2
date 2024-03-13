package com.lseg.predict3.domain;

public class StockID {
    private String value;

    public StockID() {

    }

    public String getStockExchangeId() {
        return value;
    }
    public static StockID from(String value) {
        if(value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Invalid StockID (is empty)");
        }
        StockID result = new StockID();
        result.value = value;
        return result;
    }

    public String toString() {
        return value;
    }
}
