package com.entrepidea.patterns.factory.GUIFact;
/**
 *
 * This is demo app for pattern "Abstract Factory". The idea is separation of concerns and abstractions
 * on both the Factory and the products that the factory produces.
 * The code worthy noting, besides the main structure, is the configuration helper class
 * code is from: https://refactoring.guru/design-patterns/abstract-factory/java/example
 *
 * Date: 12/08/21
 * */
public class Main {
    private static Application configureApplication() {
        Application app;
        GUIFactory factory;
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("mac")) {
            factory = new MacOSFactory();
        } else {
            factory = new WindowsFactory();
        }
        app = new Application(factory);
        return app;
    }

    public static void main(String[] args) {
        Application app = configureApplication();
        app.paint();
    }


    /*
     * TODO phone interview with Morgan Stanley 08/16/13 3:00PM(recruiter:IRIS)
        what's Factory pattern?
    */

    /*
     * BNP Paribas onsite, 02/18/20
     * Difference b/w Factory pattern and builder pattern?
     * */
    //I wouldn't characterize that they are different. Factory is an alternative to using regular "new" operator to construct a Java object, it normally hides away the details in constructor. Builder is more like a evolving version of Factory - constructing a object using Factory pattern and customize or update the object more at late stage, when it's needed. Related patterns include Abstract Factory and Prototype.
    //See this link for more: https://stackoverflow.com/questions/757743/what-is-the-difference-between-builder-design-pattern-and-factory-design-pattern


}
