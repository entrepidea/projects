package com.entrepidea.design_pattern;

import org.junit.Test;

/**
 * @author Hai Yi
 * @description an adapter pattern translates an interface of a class to a compatible one. It allows
 * classes to work together otherwise impossible because they have different interfaces.
 * Basically, we deal with this situation by creating wrappers for respective classes, and creating a
 * common interface for these wrappers. See also: http://en.wikipedia.org/wiki/Wrapper_pattern 
 * 
 */
class LegacyLine {
	public void draw(int x1, int y1, int x2, int y2) {
		System.out.println("line from (" + x1 + ',' + y1 + ") to (" + x2 + ','
				+ y2 + ')');
	}
}

class LegacyRectangle {
	public void draw(int x, int y, int w, int h) {
		System.out.println("rectangle at (" + x + ',' + y + ") with width " + w
				+ " and height " + h);
	}
}

interface Shape {
	void draw(int x1, int y1, int x2, int y2);
}

class Line implements Shape {
	private LegacyLine adaptee = new LegacyLine();

	public void draw(int x1, int y1, int x2, int y2) {
		adaptee.draw(x1, y1, x2, y2);
	}
}

class Rectangle implements Shape {
	private LegacyRectangle adaptee = new LegacyRectangle();

	public void draw(int x1, int y1, int x2, int y2) {
		adaptee.draw(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1),
				Math.abs(y2 - y1));
	}
}

public class AdapterTests {

	@Test
	public void test() {
		Shape[] shapes = { new Line(), new Rectangle() };
		// A begin and end point from a graphical editor
		int x1 = 10, y1 = 20;
		int x2 = 30, y2 = 60;
		for (int i = 0; i < shapes.length; ++i)
			shapes[i].draw(x1, y1, x2, y2);
	}

}
