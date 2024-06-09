package com.CodeSquad.IssueTracker.milestone.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimestampParser {
    private static final DateTimeFormatter ISO_OFFSET_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    public static LocalDateTime parseDeadline(String deadlineString) {
        return LocalDateTime.parse(deadlineString, ISO_OFFSET_DATE_TIME_FORMATTER);
    }
}
