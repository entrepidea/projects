package com.entrepidea.core.network.supports;

import java.time.LocalDate;

public final class Student extends Person2 {
    private final LocalDate graduation;

    private Student(String firstName, String lastName, LocalDate dob, LocalDate graduation) {
        super(firstName, lastName, dob);
        this.graduation = graduation;
    }

    // Simple factory method
    public static Student of(String firstName, String lastName, LocalDate dob, LocalDate graduation) {
        return new Student(firstName, lastName, dob, graduation);
    }

    public LocalDate getGraduation() {
        return graduation;
    }

    // equals, hashcode, and toString elided
}