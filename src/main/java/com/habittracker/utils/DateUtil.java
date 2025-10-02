package com.habittracker.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String todayString() {
        return LocalDate.now().format(DATE_FMT);
    }

    public static boolean isYesterday(String dateStr) {
        if (dateStr == null || dateStr.isEmpty())
            return false;
        LocalDate d = LocalDate.parse(dateStr, DATE_FMT);
        return d.equals(LocalDate.now().minusDays(1));
    }

    public static boolean isToday(String dateStr) {
        if (dateStr == null || dateStr.isEmpty())
            return false;
        LocalDate d = LocalDate.parse(dateStr, DATE_FMT);
        return d.equals(LocalDate.now());
    }

    public static boolean isConsecutiveDay(String lastDateStr) {
        return isYesterday(lastDateStr);
    }
}
