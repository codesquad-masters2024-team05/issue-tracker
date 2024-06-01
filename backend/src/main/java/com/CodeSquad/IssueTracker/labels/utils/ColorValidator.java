package com.CodeSquad.IssueTracker.labels.utils;

import java.util.regex.Pattern;

public class ColorValidator {
    private static final Pattern COLOR_PATTERN = Pattern.compile("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");

    public static boolean isValidColor(String color) {
        return color != null && COLOR_PATTERN.matcher(color).matches();
    }
}