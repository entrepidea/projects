package com.entrepidea.java.ui.two_d;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.metal.*;

class CustomizedCheckboxDemo extends JFrame {
	public CustomizedCheckboxDemo() {
		UIManager.put("CheckBox.disabledText", Color.BLACK);
		setLocation(400, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JCheckBox cbx = new JCheckBox("Disabled", new CheckBoxIcon(), true);
		getContentPane().add(cbx, BorderLayout.NORTH);
		getContentPane()
				.add(new JCheckBox("Enabled", true), BorderLayout.SOUTH);
		pack();
		cbx.setEnabled(false);
	}

	public static void main(String[] args) {
		new CustomizedCheckboxDemo().setVisible(true);
	}
}

class CheckBoxIcon implements Icon {
	protected int getControlSize() {
		return 13;
	}

	private void paintOceanIcon(Component c, Graphics g, int x, int y) {
		ButtonModel model = ((JCheckBox) c).getModel();
		g.translate(x, y);
		int w = getIconWidth();
		int h = getIconHeight();
		if (model.isPressed() && model.isArmed()) {
			g.setColor(MetalLookAndFeel.getControlShadow());
			g.fillRect(0, 0, w, h);
			g.setColor(MetalLookAndFeel.getControlDarkShadow());
			g.fillRect(0, 0, w, 2);
			g.fillRect(0, 2, 2, h - 2);
			g.fillRect(w - 1, 1, 1, h - 1);
			g.fillRect(1, h - 1, w - 2, 1);
		}
		g.setColor(MetalLookAndFeel.getControlInfo());
		g.translate(-x, -y);
		if (model.isSelected()) {
			drawCheck(c, g, x, y);
		}
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		ButtonModel model = ((JCheckBox) c).getModel();
		int controlSize = getControlSize();
		if (model.isPressed() && model.isArmed()) {
			g.setColor(MetalLookAndFeel.getControlShadow());
			g.fillRect(x, y, controlSize - 1, controlSize - 1);
			drawPressed3DBorder(g, x, y, controlSize, controlSize);
		} else {
			drawFlush3DBorder(g, x, y, controlSize, controlSize);
		}
		g.setColor(MetalLookAndFeel.getControlInfo());
		if (model.isSelected()) {
			drawCheck(c, g, x, y);
		}
	}

	private void drawFlush3DBorder(Graphics g, int x, int y, int w, int h) {
		g.translate(x, y);
		g.setColor(MetalLookAndFeel.getControlDarkShadow());
		g.drawRect(0, 0, w - 2, h - 2);
		g.setColor(MetalLookAndFeel.getControlHighlight());
		g.drawRect(1, 1, w - 2, h - 2);
		g.setColor(MetalLookAndFeel.getControl());
		g.drawLine(0, h - 1, 1, h - 2);
		g.drawLine(w - 1, 0, w - 2, 1);
		g.translate(-x, -y);
	}

	private void drawPressed3DBorder(Graphics g, int x, int y, int w, int h) {
		g.translate(x, y);
		drawFlush3DBorder(g, 0, 0, w, h);
		g.setColor(MetalLookAndFeel.getControlShadow());
		g.drawLine(1, 1, 1, h - 2);
		g.drawLine(1, 1, w - 2, 1);
		g.translate(-x, -y);
	}

	protected void drawCheck(Component c, Graphics g, int x, int y) {
		int controlSize = getControlSize();
		g.fillRect(x + 3, y + 5, 2, controlSize - 8);
		g.drawLine(x + (controlSize - 4), y + 3, x + 5, y + (controlSize - 6));
		g.drawLine(x + (controlSize - 4), y + 4, x + 5, y + (controlSize - 5));
	}

	public int getIconWidth() {
		return getControlSize();
	}

	public int getIconHeight() {
		return getControlSize();
	}
}
