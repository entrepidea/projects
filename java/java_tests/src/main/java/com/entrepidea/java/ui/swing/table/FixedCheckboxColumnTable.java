package com.entrepidea.java.ui.swing.table;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.entrepidea.java.ui.swing.TristateCheckbox;

class CheckBoxRenderer extends TristateCheckbox implements TableCellRenderer, MouseListener, ItemListener {

	private boolean isHeader;
	private JTable table;
	public CheckBoxRenderer(boolean isHeader, JTable table){
		this.isHeader = isHeader;
		if(isHeader){
			this.table = table;
		}
		setOpaque(true);
		setHorizontalAlignment(SwingConstants.CENTER);
	}
	

	@Override
	public void itemStateChanged(ItemEvent e){
		Object source = e.getSource();
		if(source instanceof AbstractButton == false) return; //we only interest in checkbox
		//boolean checked = e.getStateChange() == ItemEvent.SELECTED;
		/*
		TristateCheckBox tcb = (TristateCheckBox)e.getSource();
		if(tcb.getState() == TristateCheckBox.SELECTED){
			for(int i=0;i<table.getRowCount();i++){
				table.setValueAt(new Boolean(true), i, 0);
			}
		}
		else if(tcb.getState() == TristateCheckBox.NOT_SELECTED){
			for(int i=0;i<table.getRowCount();i++){
				table.setValueAt(new Boolean(false), i, 0);
			}
		}
		else if(tcb.getState() == TristateCheckBox.DONT_CARE){
			for(int i=0;i<table.getRowCount()/2;i++){
				table.setValueAt(new Boolean(false), i, 0);
			}
		}
		*/
	}
	
	private boolean isPressed = false;
	@Override
	public void mouseClicked(MouseEvent e){
		if(isPressed){
			isPressed = false;
			//doClick();
			//setState(TristateCheckBox.DONT_CARE);
			//nextState();
		}
	}
	@Override
	public void mousePressed(MouseEvent e){
		isPressed = true;
	}
	@Override
	public void mouseReleased(MouseEvent e){}
	@Override
	public void mouseEntered(MouseEvent e){}
	@Override
	public void mouseExited(MouseEvent e){}


	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if(isHeader){
			JTableHeader tableHeader = table.getTableHeader();
			tableHeader.addMouseListener(this);
			addItemListener(this);
			setForeground(tableHeader.getForeground());
			setBackground(tableHeader.getBackground());
			setFont(tableHeader.getFont());
		}
		else{
			setForeground(table.getForeground());
			setBackground(table.getBackground());
			setFont(table.getFont());
		}
		return this;
	}
	
	
	
}

public class FixedCheckboxColumnTable implements PropertyChangeListener, ChangeListener{
	private JTable main;
	private JTable fixed;
	private JScrollPane scrollPane;
	
	public	FixedCheckboxColumnTable(int fixedColumn, JScrollPane scrollPane){
		
		this.scrollPane = scrollPane;
		
		main = (JTable)scrollPane.getViewport().getView();
		main.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		main.setAutoCreateColumnsFromModel(false);
		main.addPropertyChangeListener(this);

		fixed = new JTable();
		
		
		fixed.setAutoCreateColumnsFromModel(false); //this line must be precedent the next line to be come effective
		fixed.setModel(main.getModel());
		fixed.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		
		//remove the proposed fixed columns from the main table, add them to the fixed table
		for(int i=0;i<fixedColumn;i++){
			TableColumnModel tcm = main.getColumnModel();
			TableColumn tc = tcm.getColumn(0);
			tcm.removeColumn(tc);
			fixed.getColumnModel().addColumn(tc);
			tc.setPreferredWidth(25);
		}	
		
		//change the renderer on the first column's cells
		TableColumn tc = fixed.getColumnModel().getColumn(0);
		//tc.setCellRenderer(new CheckBoxRenderer(false));
		tc.setHeaderRenderer(new CheckBoxRenderer(true, fixed));;
		//the following two lines might be another way to customize the table header
//		JTableHeader header = fixed.getTableHeader();
//		header.setDefaultRenderer(new CheckBoxRenderer());
		
		fixed.setPreferredScrollableViewportSize(fixed.getPreferredSize());
		scrollPane.setRowHeaderView(fixed);
		scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, fixed.getTableHeader());
		
		

		scrollPane.getRowHeader().addChangeListener(this);
	}

	public static void createAndShowUI(){
		JFrame frame = new JFrame("Fixed column table demo");
		JTable table = new JTable(
				 new AbstractTableModel() {
					private static final long serialVersionUID = 1L;

					private Object[][] data = new Object[][] { 
					    		{ new Boolean(false), "11", "A", "", "", "", "", "" },
					    		{new Boolean(false), "22", "", "B", "", "", "", "" },
					    		{new Boolean(false), "33", "", "", "C", "", "", "" },
					    		{new Boolean(false), "44", "", "", "", "D", "", "" },
					    		{new Boolean(false), "55", "", "", "", "", "E", "" },
					    		{new Boolean(false), "66", "", "", "", "", "", "F" } 
					    };
					    
					  private String[]  columns = new String[] { "", "a", "b", "c", "d", "e","f" };
					 
					  @Override
				      public int getColumnCount() {
				        return columns.length;
				      }
					  @Override
				      public int getRowCount() {
				        return data.length;
				      }
					  @Override
				      public String getColumnName(int col) {
				        return (String) columns[col];
				      }
					  @Override
				      public Class<?> getColumnClass(int col){
				    	  return data[0][col].getClass();
				      }
					  @Override
				      public Object getValueAt(int row, int col) {
				        return data[row][col];
				      }
					  @Override
				      public void setValueAt(Object obj, int row, int col) {
				        data[row][col] = obj;
				      }
					  @Override
				      public boolean isCellEditable(int row, int col) {
				        return true;
				      }
				    });
				
			
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(450,150));
		
		new FixedCheckboxColumnTable(1, scrollPane);
		
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
	}
	public static void main(String[] args){
		java.awt.EventQueue.invokeLater(new Runnable(){
			@Override
			public void run(){
				createAndShowUI();
			}
		});
	}

	public void stateChanged(ChangeEvent e) {
		JViewport viewPort = (JViewport)e.getSource();
		scrollPane.getVerticalScrollBar().setValue(viewPort.getViewPosition().y);
		
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if("selectionModel".equals(evt.getPropertyName())){
			fixed.setSelectionModel(main.getSelectionModel());
		}
		if("model".equals(evt.getPropertyName())){
			fixed.setModel(main.getModel());
		}
		
	}
	
}
