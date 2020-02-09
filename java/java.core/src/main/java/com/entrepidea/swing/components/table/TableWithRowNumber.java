package com.entrepidea.swing.components.table;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

public class TableWithRowNumber extends JPanel {

	private static final long serialVersionUID = 1L;

	private static String[] columnNames = {"First Name",
                                        "Last Name",
                                        "Sport",
                                        "# of Years",
                                        "Vegetarian"};
        private static Object[][] data = {
            {"Mary", "Campione",
            "Snowboarding", new Integer(5), new Boolean(false)},
            
            {"Alison", "Huml",
            "Rowing", new Integer(3), new Boolean(true)},
            
            {"Kathy", "Walrath",
            "Knitting", new Integer(2), new Boolean(false)},
            
            {"Sharon", "Zakhour",
            "Speed reading", new Integer(20), new Boolean(true)},
            
            {"Philip", "Milne",
            "Pool", new Integer(10), new Boolean(false)}
            
        };
					
	
	static class RowNumberModel extends AbstractTableModel{
		private static final long serialVersionUID = 1L;
		public RowNumberModel(){}
		@Override
		public int getRowCount(){return data.length;}
		@Override
		public int getColumnCount(){return 1;}
		@Override
		public Object getValueAt(int row, int col){
			return new Integer(row);
		}	
		@Override
		public String getColumnName(int col){
			return "";
		}
	}

	static class MyDataModel extends AbstractTableModel{
		public MyDataModel(){}
		@Override
		public int getRowCount(){ return data.length; }
		@Override
		public int getColumnCount(){ return columnNames.length;}
		@Override
		public Object getValueAt(int row, int col){
			return data[row][col];
		}	
		@Override
		public String getColumnName(int col){
			return columnNames[col];
		}
		@Override
		public Class< ?> getColumnClass(int col){
			return data[0][col].getClass();
		}
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex){
			return true;
		}
	}	
	
	static class ButtonRenderer extends JButton implements TableCellRenderer {

		private static final long serialVersionUID = 1L;

		public ButtonRenderer() {
		    setOpaque(true);
		  }

		  public Component getTableCellRendererComponent(JTable table, Object value,
		      boolean isSelected, boolean hasFocus, int row, int column) {
		    if (isSelected) {
		      setForeground(table.getSelectionForeground());
		      setBackground(table.getSelectionBackground());
		    } else {
		      setForeground(table.getForeground());
		      setBackground(UIManager.getColor("Button.background"));
		    }
			setBorder(null);  
		    setText((value == null) ? "" : value.toString());
		    System.out.format("%s%n",value);
		    return this;
		  }
		}


	public TableWithRowNumber(){
		MyDataModel model = new MyDataModel();
		JTable table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(350,120));
		//table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


		JTable fixedRow = new JTable(new RowNumberModel()){
			@Override
			public TableCellRenderer getCellRenderer(int row, int col){
				return new ButtonRenderer();
			}
		};
		
		fixedRow.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		fixedRow.getColumnModel().getColumn(0).setPreferredWidth(30);
		fixedRow.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JViewport port = new JViewport();
		port.setView(fixedRow);
		port.setPreferredSize(fixedRow.getPreferredSize());
		
		JScrollPane pane = new JScrollPane(table);
		pane.setRowHeader(port);
		pane.setCorner(JScrollPane.UPPER_LEFT_CORNER, fixedRow.getTableHeader());
		
		setLayout(new BorderLayout());
		add(pane, BorderLayout.CENTER);		
	}

	private static void createAndShowUI(){
		JFrame frame = new JFrame("Table with Row number");
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(new TableWithRowNumber(), BorderLayout.CENTER);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);	
	}	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				createAndShowUI();
			}						
		});	
	}	
}
