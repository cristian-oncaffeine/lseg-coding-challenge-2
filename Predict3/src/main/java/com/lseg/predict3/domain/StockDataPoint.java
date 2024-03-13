package com.lseg.predict3.domain;

import java.time.LocalDate;

public class StockDataPoint {

    private StockID stockId;
    private Timestamp timestamp;
    private StockPriceValue value;

    private StockDataPoint() {

    }

    public StockID getStockId() {
        return stockId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public StockPriceValue getPrice() {
        return value;
    }

    public static StockDataPoint from(String stockId, String timestamp, String price) {
        StockDataPoint result = new StockDataPoint();
        result.stockId = StockID.from(stockId);
        result.timestamp = Timestamp.fromDMY(timestamp);
        result.value = StockPriceValue.from(price);
        return result;
    }

    public static StockDataPoint from(String stockId, LocalDate date, Double price) {
        StockDataPoint result = new StockDataPoint();
        result.stockId = StockID.from(stockId);
        result.timestamp = Timestamp.from(date);
        result.value = StockPriceValue.from(price);
        return result;
    }

    public String toCSV() {
        return String.format("%s,%s,%s", stockId, timestamp, value);
    }
}
