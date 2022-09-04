package com.entrepidea.jersey.swing.components.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public class StocksTablePanel extends JPanel {
	protected JTable m_table;
	protected StockTableData m_data;

	public StocksTablePanel() {
		setSize(600, 340);
		m_data = new StockTableData();
		
		m_table = new JTable();
		m_table.setAutoCreateColumnsFromModel(false);
		m_table.setModel(m_data);
		for (int k = 0; k < StockTableData.m_columns.length; k++) {
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setHorizontalAlignment(StockTableData.m_columns[k].m_alignment);
			TableColumn column = new TableColumn(k,
					StockTableData.m_columns[k].m_width, renderer, null);
			m_table.addColumn(column);
		}
		JTableHeader header = m_table.getTableHeader();
		header.setUpdateTableInRealTime(false);
	
		add(m_table);

	}
	
	public static void createAndShowUI(){
		JFrame frame = new JFrame("Stocks Table");
		JLabel m_title = new JLabel("Stock Quotes", new ImageIcon("money.gif"),
				SwingConstants.LEFT);
		m_title.setFont(new Font("TimesRoman", Font.BOLD, 24));
		m_title.setForeground(Color.black);
		frame.getContentPane().add(m_title, BorderLayout.NORTH);
		JScrollPane ps = new JScrollPane(new StocksTablePanel());
		frame.getContentPane().add(ps, BorderLayout.CENTER);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String argv[]) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				createAndShowUI();
			}
		});
	}
}

class StockData {
	public String m_symbol;
	public String m_name;
	public Double m_last;
	public Double m_open;
	public Double m_change;
	public Double m_changePr;
	public Long m_volume;

	public StockData(String symbol, String name, double last, double open,
			double change, double changePr, long volume) {
		m_symbol = symbol;
		m_name = name;
		m_last = new Double(last);
		m_open = new Double(open);

		m_change = new Double(change);
		m_changePr = new Double(changePr);
		m_volume = new Long(volume);
	}
}

class ColumnData {
	public String m_title;
	public int m_width;
	public int m_alignment;

	public ColumnData(String title, int width, int alignment) {
		m_title = title;
		m_width = width;
		m_alignment = alignment;
	}
}

class StockTableData extends AbstractTableModel {
	static final public ColumnData m_columns[] = {
			new ColumnData("Symbol", 100, JLabel.LEFT),
			new ColumnData("Name", 150, JLabel.LEFT),
			new ColumnData("Last", 100, JLabel.RIGHT),
			new ColumnData("Open", 100, JLabel.RIGHT),
			new ColumnData("Change", 100, JLabel.RIGHT),
			new ColumnData("Change %", 100, JLabel.RIGHT),
			new ColumnData("Volume", 100, JLabel.RIGHT) };
	protected SimpleDateFormat m_frm;
	protected Vector<StockData> m_vector;
	protected Date m_date;

	public StockTableData() {
		m_frm = new SimpleDateFormat("MM/dd/yyyy");
		m_vector = new Vector<StockData>();
		setDefaultData();
	}

	public void setDefaultData() {
		try {
			m_date = m_frm.parse("04/06/1999");
		} catch (java.text.ParseException ex) {
			m_date = null;
		}
		m_vector.removeAllElements();
		m_vector.addElement(new StockData("ORCL", "Oracle Corp.", 23.6875,
				25.375, -1.6875, -6.42, 24976600));
		m_vector.addElement(new StockData("EGGS", "Egghead.com", 17.25,
				17.4375, -0.1875, -1.43, 2146400));
		m_vector.addElement(new StockData("T", "AT&T", 65.1875, 66, -0.8125,
				-0.10, 554000));
		m_vector.addElement(new StockData("LU", "Lucent Technology", 64.625,
				59.9375, 4.6875, 9.65, 29856300));
		m_vector.addElement(new StockData("FON", "Sprint", 104.5625, 106.375,
				-1.8125, -1.82, 1135100));
		m_vector.addElement(new StockData("ENML", "Enamelon Inc.", 4.875, 5,
				-0.125, 0, 35900));
		m_vector.addElement(new StockData("CPQ", "Compaq Computers", 30.875,
				31.25, -0.375, -2.18, 11853900));
		m_vector.addElement(new StockData("MSFT", "Microsoft Corp.", 94.0625,
				95.1875, -1.125, -0.92, 19836900));
		m_vector.addElement(new StockData("DELL", "Dell Computers", 46.1875,
				44.5, 1.6875, 6.24, 47310000));
		m_vector.addElement(new StockData("SUNW", "Sun Microsystems", 140.625,
				130.9375, 10, 10.625, 17734600));
		m_vector.addElement(new StockData("IBM", "Intl. Bus. Machines", 183,
				183.125, -0.125, -0.51, 4371400));
		m_vector.addElement(new StockData("HWP", "Hewlett-Packard", 70,
				71.0625, -1.4375, -2.01, 2410700));
		m_vector.addElement(new StockData("UIS", "Unisys Corp.", 28.25, 29,
				-0.75, -2.59, 2576200));
		m_vector.addElement(new StockData("SNE", "Sony Corp.", 96.1875, 95.625,
				1.125, 1.18, 330600));
		m_vector.addElement(new StockData("NOVL", "Novell Inc.", 24.0625,
				24.375, -0.3125, -3.02, 6047900));
		m_vector.addElement(new StockData("HIT", "Hitachi, Ltd.", 78.5, 77.625,
				0.875, 1.12, 49400));
	}

	@Override
	public int getRowCount() {
		return m_vector == null ? 0 : m_vector.size();
	}
	@Override
	public int getColumnCount() {
		return m_columns.length;
	}
	@Override
	public String getColumnName(int column) {
		return m_columns[column].m_title;
	}
	@Override
	public boolean isCellEditable(int nRow, int nCol) {
		return false;
	}
	@Override
	public Object getValueAt(int nRow, int nCol) {
		if (nRow < 0 || nRow >= getRowCount())
			return "";
		StockData row = (StockData) m_vector.elementAt(nRow);
		switch (nCol) {
		case 0:
			return row.m_symbol;
		case 1:
			return row.m_name;
		case 2:
			return row.m_last;
		case 3:
			return row.m_open;
		case 4:
			return row.m_change;
		case 5:
			return row.m_changePr;
		case 6:
			return row.m_volume;
		}
		return "";
	}
	
}
