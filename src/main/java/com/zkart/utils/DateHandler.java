package com.zkart.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateHandler {
    public static String getCurrentDate() {
        return LocalDate.now().toString();
    }
    public static String getDateAfter(int days) {
        return LocalDate.now().plusDays(days).toString();
    }
    public static boolean isValidDate(String dateString) {
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return date.isAfter(LocalDate.now());
    }
    public static String getTimeStamp(){
        return Timestamp.from(Instant.now()).toString();
    }
}
