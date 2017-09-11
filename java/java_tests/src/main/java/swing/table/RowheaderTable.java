package swing.table;

import java.awt.BorderLayout;
import java.awt.Component;

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
import javax.swing.table.TableModel;

public class RowheaderTable extends JTable {

	private JViewport port;
	private JTable rowHeader;
	private TableModel rowheaderModel;
	
	private String[] headValues;
	public RowheaderTable(String[] vals, TableModel model){
		
		super(model);
		
		headValues = vals;
		
		rowheaderModel = new AbstractTableModel(){
			private static final long serialVersionUID = 1L;
			@Override
			public int getRowCount() {
				return headValues.length;
			}
			@Override
			public int getColumnCount() {
				return 1;
			}
			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				return headValues[rowIndex];
			}
			@Override
			public String getColumnName(int col){
				return "Shock";
			}
		};
		rowHeader = new JTable(rowheaderModel){
			@Override
			public TableCellRenderer getCellRenderer(int row, int col){
				return new ButtonRenderer();
			}
		};
		rowHeader.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		rowHeader.getColumnModel().getColumn(0).setPreferredWidth(50);
		rowHeader.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


		port = new JViewport();
		port.setView(rowHeader);
		port.setPreferredSize(rowHeader.getPreferredSize());
		
	}
	//public APIs
	public void setHeader(JScrollPane pane){
		pane.setRowHeader(port);
		pane.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowHeader.getTableHeader());
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
		    //System.out.format("%s%n",value);
		    return this;
		  }
		}
	
	
	

	 static class MyDataModel extends AbstractTableModel{

		 static Object[][] data = {
		            {"123.43", "123.43","123.43", "123.43", "123.43"},
		            {"123.43", "123.43","123.43", "123.43", "123.43"},
		            {"123.43", "123.43","123.43", "123.43", "123.43"},
		            {"123.43", "123.43","123.43", "123.43", "123.43"},
		            {"123.43", "123.43","123.43", "123.43", "123.43"},
		            {"123.43", "123.43","123.43", "123.43", "123.43"},
		            {"123.43", "123.43","123.43", "123.43", "123.43"}		        };
		 
		 static String[] columnNames = {"1y","2y","5y","10y","20y"};
		 

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
		}	
	
	public static void main(String[] args) {
		 

		RowheaderTable table  = new RowheaderTable(new String[]{"AAA","AA","A","BBB","BB","B","CCC"},new MyDataModel());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JScrollPane pane = new JScrollPane(table);
		table.setHeader(pane);
		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(pane, BorderLayout.CENTER);
		
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				JFrame frame = new JFrame("Table with Row header");
				frame.setLayout(new BorderLayout());
				frame.getContentPane().add(panel, BorderLayout.CENTER);
				frame.pack();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
			
		});


	}

}
