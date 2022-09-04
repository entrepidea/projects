package com.entrepidea.patterns.factory.GUIFact;
public class WindowsButton implements Button {
    @Override
    public void paint() {
        System.out.println("You have created WindowsButton.");
    }
}
