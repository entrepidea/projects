package com.entrepidea.jersey.swing.patterns.mediator.Notebook;

import javax.swing.*;

/**
 *
 * This is a demo program to demonstrate the Mediator design pattern. It's directly from the site: https://refactoring.guru/design-patterns/mediator/java/example
 * 05/28/21
 *
 * */
public class Demo {
    public static void main(String[] args) {
        Mediator mediator = new Editor();

        mediator.registerComponent(new Title());
        mediator.registerComponent(new TextBox());
        mediator.registerComponent(new AddButton());
        mediator.registerComponent(new DeleteButton());
        mediator.registerComponent(new SaveButton());
        mediator.registerComponent(new List(new DefaultListModel()));
        mediator.registerComponent(new Filter());

        mediator.createGUI();
    }
}
