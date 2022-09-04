package com.entrepidea.patterns.prototype.shape;
/**
 * Demo of prototype design pattern. Prototype is used to clone an object into multiple copies.
 * In this pattern, the task of cloning is delegated to the cloned class itself, so that loosen coupling is achieved.
 *  https://refactoring.guru/design-patterns/prototype/java/example
 *  http://entrepidea.com/wiki/index.php/Design_patterns#Creational_patterns
 *
 * Date: 12/14/21
 *
 * */

public class Main {
    public static void main(String[] args) {
        BundledShapeCache cache = new BundledShapeCache();

        Shape shape1 = cache.get("Big green circle");
        Shape shape2 = cache.get("Medium blue rectangle");
        Shape shape3 = cache.get("Medium blue rectangle");

        if (shape1 != shape2 && !shape1.equals(shape2)) {
            System.out.println("Big green circle != Medium blue rectangle (yay!)");
        } else {
            System.out.println("Big green circle == Medium blue rectangle (booo!)");
        }

        if (shape2 != shape3) {
            System.out.println("Medium blue rectangles are two different objects (yay!)");
            if (shape2.equals(shape3)) {
                System.out.println("And they are identical (yay!)");
            } else {
                System.out.println("But they are not identical (booo!)");
            }
        } else {
            System.out.println("Rectangle objects are the same (booo!)");
        }
    }
}
