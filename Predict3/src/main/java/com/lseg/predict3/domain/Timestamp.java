package com.lseg.predict3.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Timestamp {
    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private LocalDate value;

    public Timestamp() {

    }

    public LocalDate getValue() {
        return value;
    }

    public static Timestamp from(LocalDate date) {
        Timestamp result = new Timestamp();
        result.value = date;
        return result;
    }
    public static Timestamp fromDMY(String value) {
        if(value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Invalid Timestamp (is empty)");
        }

        Timestamp result;

        // Use a custom formatter to parse the Timestamp
        try {
            LocalDate date = LocalDate.parse(value, dateFormat);
            result = new Timestamp();
            result.value = date;
            return result;
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid Timestamp: " + value);
        }
    }

    public String toString() {
        return value.format(dateFormat);
    }
}
