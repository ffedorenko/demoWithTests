package com.example.demowithtests.util;

public class UniqueFieldChecker {
    public static <T> boolean isFieldNew(T newField, T existField) {
        return newField != null && !newField.equals(existField);
    }
}
