package com.entrepidea.core.network.supports;

import java.time.LocalDate;

public abstract sealed class Person2 permits Student {
    private final String firstName;
    private final String lastName;
    private final LocalDate dob;

    public Person2(String firstName, String lastName, LocalDate dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDob() {
        return dob;
    }

    // ...
}