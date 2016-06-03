package com.entrepidea.java.ui.two_d;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChartTest extends JFrame {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ChartTest();
	}

	public ChartTest() {
		super("2D Charts");
		setSize(350, 280);
		getContentPane().setLayout(new GridLayout(1, 3, 10, 0));
		getContentPane().setBackground(Color.white);

		int[] xData = new int[8];
		int[] yData = new int[8];
		for (int i = 0; i < xData.length; i++) {
			xData[i] = i;
			yData[i] = (int) (Math.random() * 100);
			if (i > 0)
				yData[i] = (yData[i - 1] + yData[i]) / 2;
		}

		ChartPane chart = new ChartPane(xData.length, xData, yData, "Pie Chart");
		ImageIcon icon = new ImageIcon("largeJava2slogo.GIF");
		chart.setForegroundImage(icon.getImage());
		chart.setEffectIndex(ChartPane.ImageEffect);
		chart.setDrawShadow(true);
		getContentPane().add(chart);

		WindowListener wndCloser = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		addWindowListener(wndCloser);

		setVisible(true);
	}
}

class ChartPane extends JPanel {
	public static final int LineChart = 0;

	public static final int ColumnChart = 1;

	public static final int PieChart = 2;

	public static final int PLainEffect = 0;

	public static final int Gradientffect = 1;

	public static final int ImageEffect = 2;

	protected JLabel titleLabel;

	protected ChartView chartView;

	protected int dataLength;

	protected int[] xData;

	protected int[] yData;

	protected int xMin;

	protected int xMax;

	protected int yMin;

	protected int yMax;

	protected double[] pieData;

	protected int m_effectIndex = PLainEffect;

	protected Stroke stroke;

	protected GradientPaint gradient;

	protected Image foregroundImage;

	protected Color lineColor = Color.black;

	protected Color columnColor = Color.blue;

	protected int columnWidth = 12;

	protected boolean drawShadow = false;

	public ChartPane(int nData, int[] xD, int[] yD, String text) {
		super(new BorderLayout());
		setBackground(Color.white);
		titleLabel = new JLabel(text, JLabel.CENTER);
		add(titleLabel, BorderLayout.NORTH);

		if (xData == null) {
			xData = new int[nData];
			for (int k = 0; k < nData; k++)
				xData[k] = k;
		}
		if (yD == null)
			throw new IllegalArgumentException("yData can't be null");
		if (nData > yD.length)
			throw new IllegalArgumentException("Insufficient yData length");
		if (nData > xD.length)
			throw new IllegalArgumentException("Insufficient xData length");
		dataLength = nData;
		xData = xD;
		yData = yD;

		xMin = xMax = 0; // To include 0 into the interval
		yMin = yMax = 0;
		for (int k = 0; k < dataLength; k++) {
			xMin = Math.min(xMin, xData[k]);
			xMax = Math.max(xMax, xData[k]);
			yMin = Math.min(yMin, yData[k]);
			yMax = Math.max(yMax, yData[k]);
		}
		if (xMin == xMax)
			xMax++;
		if (yMin == yMax)
			yMax++;

		double sum = 0;
		for (int k = 0; k < dataLength; k++) {
			yData[k] = Math.max(yData[k], 0);
			sum += yData[k];
		}
		pieData = new double[dataLength];
		for (int k = 0; k < dataLength; k++)
			pieData[k] = yData[k] * 360.0 / sum;

		chartView = new ChartView();
		add(chartView, BorderLayout.CENTER);
	}

	public void setEffectIndex(int effectIndex) {
		m_effectIndex = effectIndex;
		repaint();
	}

	public int getEffectIndex() {
		return m_effectIndex;
	}

	public void setStroke(Stroke s) {
		stroke = s;
		chartView.repaint();
	}

	public void setForegroundImage(Image img) {
		foregroundImage = img;
		repaint();
	}

	public Image getForegroundImage() {
		return foregroundImage;
	}

	public Stroke getStroke() {
		return stroke;
	}

	public void setGradient(GradientPaint g) {
		gradient = g;
		repaint();
	}

	public GradientPaint getGradient() {
		return gradient;
	}

	public void setColumnWidth(int c) {
		columnWidth = c;
		chartView.calcDimensions();
		chartView.repaint();
	}

	public int setColumnWidth() {
		return columnWidth;
	}

	public void setColumnColor(Color c) {
		columnColor = c;
		chartView.repaint();
	}

	public Color getColumnColor() {
		return columnColor;
	}

	public void setLineColor(Color c) {
		lineColor = c;
		chartView.repaint();
	}

	public Color getLineColor() {
		return lineColor;
	}

	public void setDrawShadow(boolean d) {
		drawShadow = d;
		chartView.repaint();
	}

	public boolean getDrawShadow() {
		return drawShadow;
	}

	class ChartView extends JComponent {
		int xMargin = 5;

		int yMargin = 5;

		int pieGap = 10;

		int m_x;

		int m_y;

		int m_w;

		int m_h;

		ChartView() {
			enableEvents(ComponentEvent.COMPONENT_RESIZED);
		}

		protected void processComponentEvent(ComponentEvent e) {
			calcDimensions();
		}

		public void calcDimensions() {
			Dimension d = getSize();
			m_x = xMargin;
			m_y = yMargin;
			m_w = d.width - 2 * xMargin;
			m_h = d.height - 2 * yMargin;

			m_x += columnWidth / 2;
			m_w -= columnWidth;

		}

		public int xChartToScreen(int x) {
			return m_x + (x - xMin) * m_w / (xMax - xMin);
		}

		public int yChartToScreen(int y) {
			return m_y + (yMax - y) * m_h / (yMax - yMin);
		}

		public void paintComponent(Graphics g) {
			int x0 = 0;
			int y0 = 0;
			g.setColor(Color.black);
			x0 = xChartToScreen(0);
			g.drawLine(x0, m_y, x0, m_y + m_h);
			y0 = yChartToScreen(0);
			g.drawLine(m_x, y0, m_x + m_w, y0);
			Graphics2D g2 = (Graphics2D) g;

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);

			if (stroke != null) {
				g2.setStroke(stroke);
			}
			GeneralPath path = new GeneralPath();

			double start = 0.0;
			double finish = 0.0;
			int ww = m_w - 2 * pieGap;
			int hh = m_h - 2 * pieGap;
			if (drawShadow) {
				ww -= pieGap;
				hh -= pieGap;
			}

			for (int i = 0; i < dataLength; i++) {
				finish = start + pieData[i];
				double f1 = Math.min(90 - start, 90 - finish);
				double f2 = Math.max(90 - start, 90 - finish);
				Shape shp = new Arc2D.Double(m_x, m_y, ww, hh, f1, f2 - f1,
						Arc2D.PIE);
				double f = (f1 + f2) / 2 * Math.PI / 180;
				AffineTransform s1 = AffineTransform.getTranslateInstance(
						pieGap * Math.cos(f), -pieGap * Math.sin(f));
				s1.translate(pieGap, pieGap);
				Shape piece = s1.createTransformedShape(shp);
				path.append(piece, false);
				start = finish;
			}

			if (drawShadow) {
				AffineTransform s0 = AffineTransform.getTranslateInstance(
						pieGap, pieGap);
				g2.setColor(Color.gray);
				Shape shadow = s0.createTransformedShape(path);
				g2.fill(shadow);
			}

			if (m_effectIndex == Gradientffect && gradient != null) {
				g2.setPaint(gradient);
				g2.fill(path);
			} else if (m_effectIndex == ImageEffect && foregroundImage != null)
				fillByImage(g2, path, 0);
			else {
				g2.setColor(columnColor);
				g2.fill(path);
			}

			g2.setColor(lineColor);
			g2.draw(path);
		}

		protected void fillByImage(Graphics2D g2, Shape shape, int xOffset) {
			if (foregroundImage == null)
				return;
			int wImg = foregroundImage.getWidth(this);
			int hImg = foregroundImage.getHeight(this);
			if (wImg <= 0 || hImg <= 0)
				return;
			g2.setClip(shape);
			Rectangle bounds = shape.getBounds();
			for (int i = bounds.x + xOffset; i < bounds.x + bounds.width; i += wImg)
				for (int j = bounds.y; j < bounds.y + bounds.height; j += hImg)
					g2.drawImage(foregroundImage, i, j, this);
		}
	}
}
