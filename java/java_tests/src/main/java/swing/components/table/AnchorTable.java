/**
 * An anchor table is like that from the a excel sheet. Once a cell is selected, the value is shown 
 * in a text field on the top. 
 * 
 * There are getAnchorSelectionIndex from both the TableModel and TableSelectionModel
 * that a user can use to retrieve row index and column index; as an alternative, a user might get the same from
 * mouse event (try!). 
 *
 * Note that the ListSelectionListener won't work in picking up the value, since it won't change
 * the cell index if the sequential click takes place in the same row. 
 * 
 * see also: http://www.exampledepot.com/egs/javax.swing.table/AnchorEdit.html (be noted this article is not really 
 * accurate or correct).
 *
 * @create 10/17/12
 * @author yihai
 *
 * */
package swing.components.table;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class AnchorTable {
	
	private static class MyTableModel extends AbstractTableModel{
		private String[] columns = {"col1","col2","col3","col4"};
		private Object[][] data = {
			{"1","2","3","4"},
			{"5","6","7","8"},
			{"9","10","11","12"},
			{"13","14","15","16"},
			{"17","18","19","20"},
			{"21","22","23","24"}
		};		
		@Override public int getRowCount(){return data.length;}
		@Override public int getColumnCount(){return columns.length;}
		@Override public Class getColumnClass(int col){
			return data[0][col].getClass();
		}
		@Override public String getColumnName(int col){
			return columns[col];
		}
		@Override public Object getValueAt(int row, int col){
			return data[row][col];
		}
	}					

	/*
	private static class MyListSelectionListener implements ListSelectionListener{
		private JTable table;					
		public MyListSelectionListener(JTable table){
			this.table = table;
		}					
		@Override public void valueChanged(ListSelectionEvent e){
			int row = table.getSelectionModel().getAnchorSelectionIndex(); 
			int col = table.getColumnModel().getSelectionModel().getAnchorSelectionIndex(); 
			System.out.println(row+","+col+",["+table.getModel().getValueAt(row, col)+"]");
		}
	}*/

	private static class MyMouseListener extends MouseAdapter{
		private JTable table;	
		private JTextField textField;

		public MyMouseListener(JTable table, JTextField jf){
			this.table = table;
			this.textField = jf;
		}
		@Override public void mouseClicked(MouseEvent e){
			int row = table.getSelectionModel().getAnchorSelectionIndex();//!				
			int col = table.getColumnModel().getSelectionModel().getAnchorSelectionIndex();//!
			System.out.println(row+","+col+",["+table.getModel().getValueAt(row, col)+"]");		
			textField.setText((String)table.getModel().getValueAt(row, col));
		}
	}

	private static void createAndShowGUI(){
		JTextField jf = new JTextField(10);					
		JTable table = new JTable(new MyTableModel());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowSelectionAllowed(false);//!
		table.setCellSelectionEnabled(true);//!
		table.setPreferredScrollableViewportSize(new Dimension(260,80));
		//listeners added
		//table.getSelectionModel().addListSelectionListener(new MyListSelectionListener(table));
		table.addMouseListener(new MyMouseListener(table, jf));

		JFrame frame = new JFrame("Anchor Table");
		frame.getContentPane().add(jf, BorderLayout.PAGE_START);
		frame.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			@Override public void run(){
				createAndShowGUI();
			}
		});
	}
}
