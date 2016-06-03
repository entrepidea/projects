package com.entrepidea.java.ui.swing.table;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class ButtonTextFieldCellTest extends JPanel {

	static class ButtonTextFieldCell extends AbstractCellEditor implements
			TableCellEditor, ActionListener {

		private JPanel panel;
		private JTextField t;
		JButton b;

		public ButtonTextFieldCell() {
			this("");
		}

		public ButtonTextFieldCell(String txt) {

			panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
			t = new JTextField(50);
			t.setText(txt);
			// t.setPreferredSize(new Dimension(50,16));
			panel.add(t);
			b = new JButton("...");
			b.setPreferredSize(new Dimension(16, 16));
			b.addActionListener(this);
			panel.add(b);
			panel.add(Box.createHorizontalGlue());
		}

		@Override
		public Object getCellEditorValue() {
			return this;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			return panel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() instanceof JButton) {
				JOptionPane.showConfirmDialog(panel, "you clicked a button",
						"Info", JOptionPane.CLOSED_OPTION);
			}
		}

		@Override
		public String toString() {
			return t.getText();
		}
	}

	static class MyModel extends AbstractTableModel {

		String[] colName = new String[] { "index", "Test" };

		Object[][] data = new Object[][] { { "1", new ButtonTextFieldCell() },
				{ "2", new ButtonTextFieldCell() },
				{ "3", new ButtonTextFieldCell() },
				{ "4", new ButtonTextFieldCell() } };

		@Override
		public Class<?> getColumnClass(int col) {
			return data[0][col].getClass();
		}

		@Override
		public String getColumnName(int col) {
			return colName[col];
		}

		@Override
		public boolean isCellEditable(int arg0, int arg1) {
			return true;
		}

		@Override
		public int getColumnCount() {
			return data[0].length;
		}

		@Override
		public int getRowCount() {
			return data.length;
		}

		@Override
		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

	}

	// create a table of two columns only
	private JTable table;

	public ButtonTextFieldCellTest() {
		table = new JTable(new MyModel());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setDefaultEditor(ButtonTextFieldCell.class,
				new ButtonTextFieldCell());
		TableColumnModel tcm = table.getColumnModel();
		for (int i = 0; i < tcm.getColumnCount(); i++) {
			TableColumn tc = tcm.getColumn(i);
			if (i == 1) {
				tc.setPreferredWidth(250);
			}
		}
		add(new JScrollPane(table), BorderLayout.CENTER);

	}

	private static void createAndShowUI() {
		JFrame frame = new JFrame("hello kitty");
		frame.getContentPane().add(new ButtonTextFieldCellTest());
		frame.setSize(new Dimension(400, 300));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowUI();
			}
		});
	}

}
