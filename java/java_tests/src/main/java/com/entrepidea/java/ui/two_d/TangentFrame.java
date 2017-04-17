package com.entrepidea.java.ui.two_d;

//Purpose: given two circles (c1.x, c1.y, c1.r) and (c2.x, c2.y, c2.r),
//without loss of generality, let c1.r >= c2.r.
//find the tangent lines of these two circles.
//Note that if a circle is inside another circle, there is no tangent line between them.

import java.awt.*;
import javax.swing.*;

public class TangentFrame extends JFrame {

	Circle c1, c2;
	Circle c3, c4;
	Circle c5, c6;
	JLabel status;

	public TangentFrame(Circle c1, Circle c2) {
		super("Draw tangent lines of two circles.");
		setBackground(Color.WHITE);

		// c1 and c2 are disjoint.
		// Let c1.r >= c2.r.
		if (c1.r > c2.r) {
			this.c1 = c1;
			this.c2 = c2;
		} else {
			this.c1 = c2;
			this.c2 = c1;
		}

		add(new TangentPanel(), BorderLayout.CENTER);

		status = new JLabel();
		add(status, BorderLayout.SOUTH);

		setSize(1200, 800);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// class line represents a line.
	public class Line {
		double x1; // x coordinate of the first point
		double y1; // y coordinate of the first point
		double x2; // x coordinate of the second point
		double y2; // y coordinate of the second point

		public Line(double x1, double y1, double x2, double y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
	}

	// class TangentPanel is to draw the circles.
	// If the tangent lines are available, draw the four tangent lines.
	public class TangentPanel extends JPanel {
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;

			// Set current color to be red.
			g2d.setColor(Color.RED);
			// Draw circle 1.
			g2d.drawOval((int) (c1.x - c1.r), (int) (c1.y - c1.r),
					(int) (2 * c1.r), (int) (2 * c1.r));
			// Draw circle 2.
			g2d.drawOval((int) (c2.x - c2.r), (int) (c2.y - c2.r),
					(int) (2 * c2.r), (int) (2 * c2.r));

			// Draw tangent lines of c1 and c2.
			// When two circles are disjoint, there are four tangent lines.
			// When two circles intersect, there are two tangent lines.
			// When one circle is inside the other, there is no tangent line.
			try {
				Line[] tangentLines = calTangentLines(c1, c2);

				for (int i = 0; i < tangentLines.length; i++) {
					// If there are four tangent lines,
					// the first two are outer ones and are drawn in green;
					// the rest two are inner ones and are drawn in orange.
					if (i < 2)
						g2d.setColor(Color.GREEN);
					else
						g2d.setColor(Color.ORANGE);
					// Draw a tangent line.
					g2d.drawLine((int) tangentLines[i].x1,
							(int) tangentLines[i].y1, (int) tangentLines[i].x2,
							(int) tangentLines[i].y2);
				}
			} catch (Exception e) {
				g2d.setFont(new Font("Serif", Font.BOLD, 16));
				g2d.setColor(new Color(100, 255, 255));
				g2d.drawString(
						"No tangent lines if a circle is inside the other.", 0,
						700);
			}
		}
	}

	public Line[] calTangentLines(Circle c1, Circle c2) throws Exception {
		// Variable s is the distance between the centers of two circles.
		double s = Math.sqrt((c1.x - c2.x) * (c1.x - c2.x) + (c1.y - c2.y)
				* (c1.y - c2.y));

		// Variable alpha is the angle between
		// the line connecting the centers of two circles and
		// the line connecting a center to a tangent point in that circle.
		double alpha = Math.acos((c1.r - c2.r) / s);

		// Variable rho is the angle between
		// the line connecting the centers of the two angles and an inner
		// tangent line.
		double rho = Math.acos((c1.r + c2.r) / s);

		// Variable beta is the angle between
		// the line from the center of larger circle to the center of the
		// smaller one
		// and the horizontal line. That is, assume that c1.r >= c2.r.
		// (c2.x, c2.y)
		// /
		// /
		// /beta
		// /-------------
		// (c1.x, c1.y)
		double beta = Math.atan((c1.y - c2.y) / (c1.x - c2.x));

		// Note that the actual value of beta is dependent on the relative
		// position of the two circles.
		if (c2.x <= c1.x && c2.y >= c1.y) // arctan(beta) is in [-90, 0] and
											// beta is in [90, 180]
			// (c2.x, c2.y)
			// \
			// \
			// \
			// (c1.x, c1.y)
			beta = Math.PI + beta;
		else if (c2.x <= c1.x && c2.y <= c1.y) // arctan(beta) is in [0, 90] and
												// beta is in [180, 270].
			// (c1.x, c1.y)
			// /
			// /
			// /
			// (c2.x, c2.y)
			beta = Math.PI + beta;
		else if (c2.x >= c1.x && c2.y <= c1.y) // arctan(beta) is in [-90, 0]
												// and beta is in [270, 360]
			// (c1.x, c1.y)
			// \
			// \
			// \
			// (c2.x, c2.y)
			beta = 2 * Math.PI + beta;

		// If one circle is inside the other,
		// that is, the distance between the centers of two circles is smaller
		// than the difference of two radius,
		// then there is no tangent line.
		// By assumption, we have c1.r >= c2.r.
		if (s < c1.r - c2.r)
			throw new Exception();
		// If the distance between the centers of two circles is larger than the
		// bigger radius,
		// then there are tangent lines between the two circles.
		else {// Two circles have tangent lines.
			// outer tangent line (a1, b1) -- (u1, w1)
			double a1 = c1.x + c1.r * Math.cos(alpha + beta);
			double b1 = c1.y + c1.r * Math.sin(alpha + beta);
			double u1 = c2.x + c2.r * Math.cos(alpha + beta);
			double w1 = c2.y + c2.r * Math.sin(alpha + beta);

			// outer tangent line (a2, b2) -- (u2, w2)
			double a2 = c1.x + c1.r * Math.cos(alpha - beta);
			double b2 = c1.y - c1.r * Math.sin(alpha - beta);
			double u2 = c2.x + c2.r * Math.cos(alpha - beta);
			double w2 = c2.y - c2.r * Math.sin(alpha - beta);

			if (s <= c1.r + c2.r) {
				// Two circles intersect: only outer tangent lines available.
				Line[] twoTangentLines = new Line[2];

				// tangent line (a1, b1) -- (u1, w1)
				twoTangentLines[0] = new Line(a1, b1, u1, w1);

				twoTangentLines[1] = new Line(a2, b2, u2, w2);

				return twoTangentLines;
			} else {// Two circles are disjoint: both inner and outer tangent
					// lines available.
				Line[] fourTangentLines = new Line[4];

				// Warning: cannot write this as:
				// fourTangentLines[0].x1 = a1; // Wrong
				// fourTangentLines[0].y1 = b1; // Wrong
				// fourTangentLines[0].x2 = u1; // Wrong
				// fourTangentLines[0].y2 = w1; // Wrong

				// outer tangent line (a1, b1) -- (u1, w1)
				fourTangentLines[0] = new Line(a1, b1, u1, w1);

				// outer tangent line (a2, b2) -- (u2, w2)
				fourTangentLines[1] = new Line(a2, b2, u2, w2);

				// inner tangent line (a3, b3) -- (u3, w3)
				double a3 = c1.x + c1.r * Math.cos(rho + beta);
				double b3 = c1.y + c1.r * Math.sin(rho + beta);
				double u3 = c2.x - c2.r * Math.cos(rho + beta);
				double w3 = c2.y - c2.r * Math.sin(rho + beta);

				fourTangentLines[2] = new Line(a3, b3, u3, w3);

				// inner tangent line (a4, b4) -- (u4, w4)
				double a4 = c1.x + c1.r * Math.cos(rho - beta);
				double b4 = c1.y - c1.r * Math.sin(rho - beta);
				double u4 = c2.x - c2.r * Math.cos(rho - beta);
				double w4 = c2.y + c2.r * Math.sin(rho - beta);

				fourTangentLines[3] = new Line(a4, b4, u4, w4);

				return fourTangentLines;
			}
		}
	}

	public static void main(String args[]) {
		// double data[][] = { {200, 200, 100}, {600, 100, 50} }; // two
		// disjoint circles
		// double data[][] = { {800, 500, 120}, {500, 200, 50} }; // two
		// disjoint circles
		double data[][] = { { 800, 500, 120 }, { 800, 200, 50 } }; // two
																	// disjoint
																	// circles
		// double data[][] = { {800, 400, 150}, {900, 300, 100} }; // two
		// intersected circles
		// double data[][] = { {400, 500, 100}, {450, 550, 30} }; // one circle
		// is inside the other.

		new TangentFrame(new Circle(data[0]), new Circle(data[1]));
	}
}

class Circle {
	double x, y, r;

	Circle(double x, double y, double r) {
		this.x = x;
		this.y = y;
		this.r = r;
	}

	Circle(double a[]) {
		this.x = a[0];
		this.y = a[1];
		this.r = a[2];
	}
}
