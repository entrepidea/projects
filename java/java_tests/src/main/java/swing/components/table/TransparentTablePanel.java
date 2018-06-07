package swing.components.table;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class TransparentTablePanel extends JPanel {

	private class MyTableModel extends AbstractTableModel {
		private String[] columnNames = { "First Name", "Last Name", "Sport",
				"# of Years", "Vegetarian" };
		private Object[][] data = {
				{ "Mary", "Campione", "Snowboarding", new Integer(5),
						new Boolean(false) },
				{ "Alison", "Huml", "Rowing", new Integer(3), new Boolean(true) },
				{ "Kathy", "Walrath", "Knitting", new Integer(2),
						new Boolean(false) },
				{ "Sharon", "Zakhour", "Speed reading", new Integer(20),
						new Boolean(true) },
				{ "Philip", "Milne", "Pool", new Integer(10),
						new Boolean(false) } };

		@Override
		public int getRowCount() {
			return data.length;
		};

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public Class<?> getColumnClass(int col) {
			return data[0][col].getClass();
		}

		@Override
		public String getColumnName(int col) {
			return columnNames[col];
		}

		@Override
		public Object getValueAt(int row, int col) {
			return data[row][col];
		}
	}

	public TransparentTablePanel() {
		//setOpaque(false);
		JTable table = new JTable(4,8){
			
			{setOpaque(false);}
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				int w = getWidth();
				int h = getHeight();
				GradientPaint paint = new GradientPaint(0, 0, Color.RED, w, h,Color.GREEN, true);
				g2.setPaint(paint);
				g2.fillRect(0, 0, w, h);
				super.paintComponent(g);//see what happens when this is removed.
			}
			
		};
		((DefaultTableCellRenderer)table.getDefaultRenderer(Object.class)).setOpaque(false);//see what happens when this is removed.
		
		table.setPreferredScrollableViewportSize(new Dimension(400, 80));

		JScrollPane pane = new JScrollPane(table);
		add(pane);
	}


	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Transparent Window");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new TransparentTablePanel());
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}

}
