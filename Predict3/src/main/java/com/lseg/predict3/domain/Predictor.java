package com.lseg.predict3.domain;

import com.sun.jdi.InternalException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Predictor {

    public Predictor() {

    }

    public static List<StockDataPoint> predict3(List<StockDataPoint> dataPoints, Timestamp lastTimestamp) {
        if(dataPoints == null || dataPoints.isEmpty() || dataPoints.size() < 10) {
            throw new IllegalArgumentException("Invalid set of data points for prediction.\nSet should contain 10 data points");
        }

        // calculate second highest value
        List<Double> sortedDistinctValues = dataPoints.stream().map(dp -> dp.getPrice().getValue()).distinct().sorted().toList();
        if(sortedDistinctValues.size() == 1) {
            throw new InternalException("ERROR: Algorithm fails in this case -  we selected 10 identical values");
        }
        Double secondHighestValue = sortedDistinctValues.get(sortedDistinctValues.size() - 2);


        // get the Stock Exchange id from the list, since it's going to be the same in the output
        String stockExchangeId = dataPoints.get(0).getStockId().getStockExchangeId();
        // get max timestamp in the entire original dataset
        LocalDate lastDate = lastTimestamp.getValue();
        // assume the nth data point is the last data point in the dataPoints list
        Double valueOfNthDataPoint = dataPoints.get(dataPoints.size() - 1).getPrice().getValue();

        // create the n + 1 data point
        StockDataPoint first = StockDataPoint.from(stockExchangeId, lastDate.plusDays(1), secondHighestValue);

        // create the n + 2 data point having half the difference between n and n + 1
        Double secondValue = first.getPrice().getValue() +
                (valueOfNthDataPoint - first.getPrice().getValue()) / 2;
        StockDataPoint second = StockDataPoint.from(stockExchangeId, lastDate.plusDays(2), secondValue);

        // create the n + 3 data point
        Double thirdValue = second.getPrice().getValue() +
                (first.getPrice().getValue() - second.getPrice().getValue()) / 4;
        StockDataPoint third = StockDataPoint.from(stockExchangeId, lastDate.plusDays(3), thirdValue);

        List<StockDataPoint> result = new ArrayList<>();
        result.add(first);
        result.add(second);
        result.add(third);

        return result;
    }
}

