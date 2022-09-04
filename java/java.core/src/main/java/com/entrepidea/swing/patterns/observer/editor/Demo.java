package com.entrepidea.jersey.swing.patterns.observer.editor;

/**
 * This demonstrates Observer pattern. The sample code is from: https://refactoring.guru/design-patterns/observer/java/example
 * 06/06/21
 *
 * */
public class Demo {
    public static void main(String[] args) {
        Editor editor = new Editor();
        editor.events.subscribe("open", new LogOpenListener("log/file.txt"));
        editor.events.subscribe("save", new EmailNotificationListener("admin@example.com"));

        try {
            editor.openFile("test.txt");
            editor.saveFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
