package swing.components.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class FixedColumnTable extends JPanel {

	public FixedColumnTable(){
		JTable table = new JTable(new DefaultTableModel(){
			private String[] cols = null;
			@Override
			public String getColumnName(int index){
				return cols[index];
			}
			@Override
			public int getRowCount(){
				return 30;
			}
			@Override
			public int getColumnCount(){
				if(cols == null){
					cols = new String[]{"row#","id","fName","lName","gender","addr","title"};
				}
				return cols.length;
			}
			
		});
		
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setPreferredScrollableViewportSize(new Dimension(350,250));

		JTable fixed = new JTable();
		fixed.setAutoCreateColumnsFromModel(false);
		fixed.setModel(table.getModel());
		//fixed.setSelectionModel(table.getSelectionModel());
		JTableHeader fixedHeader = fixed.getTableHeader();
		/*fixedHeader.setDefaultRenderer(new DefaultTableCellRenderer(){
			{this.setBackground(Color.RED);}
		});
		*/
		fixedHeader.setBackground(Color.RED);
		/*
		fixed.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
			{setBackground(Color.BLUE);}
		});
		*/
		fixed.setBackground(Color.BLUE);
		
		TableColumnModel tcm = table.getColumnModel();
		
		TableColumn tc = tcm.getColumn(0);
		
		fixed.getColumnModel().addColumn(tc);
		tcm.removeColumn(tc);
		
		JScrollPane sp = new JScrollPane(table);
		
		JViewport jv = new JViewport();
		jv.setView(fixed);
		jv.setPreferredSize(fixed.getPreferredSize());
		sp.setRowHeaderView(jv);
		sp.setCorner(JScrollPane.UPPER_LEFT_CORNER, fixed.getTableHeader());

		setLayout(new BorderLayout());
		add(sp,BorderLayout.CENTER);
	}
	
	private static void createAndShowUI(){
		JFrame frame = new JFrame("Fixed Column Table");
		frame.getContentPane().add(new FixedColumnTable(), BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
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
