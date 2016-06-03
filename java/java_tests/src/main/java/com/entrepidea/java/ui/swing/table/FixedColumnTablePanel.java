package com.entrepidea.java.ui.swing.table;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;



class FixedColTable implements ChangeListener, PropertyChangeListener {
	
	static class ToolTipTableHeader extends JLabel implements TableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if(table!=null){
				JTableHeader tblHdr = table.getTableHeader();
				if(tblHdr!=null){
					setFont(tblHdr.getFont());
					setForeground(tblHdr.getForeground());
					setBackground(tblHdr.getBackground());
					setText((String)value);
				}
			}
			String toolTip = new StringBuilder("tool tip:").append((String)value).toString();
			setToolTipText(toolTip);
			setBorder(UIManager.getBorder("TableHeader.cellBorder"));
			return this;
		}		
	} 
	
	private JScrollPane pane = null;
	private JTable mainTable = null;
	private JTable fixedColumnTbl = new JTable();
	private int fixedColumn;
	
	public FixedColTable(int fixed, JScrollPane pane){
		this.pane = pane;
		mainTable = (JTable)pane.getViewport().getView();
		fixedColumnTbl.setAutoCreateColumnsFromModel(false);
		fixedColumnTbl.setModel(mainTable.getModel());
		//fixedColumnTbl.setSelectionModel(mainTable.getSelectionModel());
		fixedColumnTbl.setFocusable(false);
		fixedColumn = fixed;

		TableColumnModel tcm = mainTable.getColumnModel();
		//set tool tips for header
		for(int i=0;i<mainTable.getColumnCount();i++){
			TableColumn tc = tcm.getColumn(i);
			tc.setHeaderRenderer(new ToolTipTableHeader());
		}
		//move the first n columns from main table to fixed column table
		for(int i=0;i<fixedColumn;i++){
			TableColumn tc = tcm.getColumn(0);
			tcm.removeColumn(tc);
			fixedColumnTbl.addColumn(tc);
		}
		
		
		
		//add fixed columns to JScrollPane
		fixedColumnTbl.setPreferredScrollableViewportSize(fixedColumnTbl.getPreferredSize());
		pane.setRowHeaderView(fixedColumnTbl);
		pane.setCorner(JScrollPane.UPPER_LEFT_CORNER, fixedColumnTbl.getTableHeader());
	}
	

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		String propName = evt.getPropertyName();
		switch(propName){
		case "selectionModel":
			//fixedColumnTbl.setSelectionModel(mainTable.getSelectionModel());
			break;
		case "model":
			fixedColumnTbl.setModel(mainTable.getModel());
			break;
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JViewport vp = (JViewport)e.getSource();
		pane.getVerticalScrollBar().setValue(vp.getViewPosition().y);
	}
	
	//public API 
	public JScrollPane getScrollTable(){
		return pane;
	}
	
}

public class FixedColumnTablePanel extends JPanel {

	private static Object[][] data = new Object[][]{
			{"1","2","M","2","2","2","2","2","2"},
			{"1","2","M","2","2","2","2","2","2"},
			{"1","2","M","2","2","2","2","2","2"},
			{"1","2","M","2","2","2","2","2","2"},
			{"1","2","M","2","2","2","2","2","2"},
			{"1","2","M","2","2","2","2","2","2"},
			{"1","2","M","2","2","2","2","2","2"},
			{"1","2","M","2","2","2","2","2","2"},
			{"1","2","M","2","2","2","2","2","2"},
			{"1","2","M","2","2","2","2","2","2"},
			{"1","2","M","2","2","2","2","2","2"},
			{"1","2","M","2","2","2","2","2","2"},
			{"1","2","M","2","2","2","2","2","2"},
			{"1","2","M","2","2","2","2","2","2"},
			{"1","2","M","2","2","2","2","2","2"},
			{"1","2","M","2","2","2","2","2","2"},
			{"1","2","M","2","2","2","2","2","2"},
			{"1","2","M","2","2","2","2","2","2"},
			{"1","2","M","2","2","2","2","2","2"},
			{"1","2","M","2","2","2","2","2","2"}
			
	};
	
	private static String[] col = new String[]{"fixed","col1","col2","col3","col4","col5","col6","col7","col8"};

	public FixedColumnTablePanel(){
		JTable table = new JTable(data, col){
			{
				setOpaque(false);
			}
			@Override
			public TableCellEditor getCellEditor(int row, int column) {
				int modelColumn = convertColumnIndexToModel( column );
				if(modelColumn==2){
					JComboBox<String> cb = new JComboBox<String>();
					cb.removeAll();
					cb.addItem("M");
					cb.addItem("F");
					return new DefaultCellEditor(cb);
				}
				else{
					return super.getCellEditor(row, column);
				}
			}
		};
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){{setOpaque(false);}});
		JScrollPane pane = new JScrollPane(table){
			
			{
				setOpaque(false);
				getViewport().setOpaque(false);
				setColumnHeader(new JViewport());
				getColumnHeader().setOpaque(false);
			}
			@Override
			public void paintComponent(Graphics g){
				BufferedImage image=null;
				try {
					image = ImageIO.read(new URL("http://www.northeastern.edu/securenu/wp-content/uploads/2013/01/Java_Logo.jpg"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(image!=null){
					g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
				}
				super.paintComponent(g);
			}
		};
		
		FixedColTable fct = new FixedColTable(1, pane);
		pane = fct.getScrollTable();
		
		setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(500,250));
		add(pane, BorderLayout.CENTER);
	}
	
	
	private static void createAndShowGUI(){
		JFrame frame = new JFrame("Fixed table demo");
		frame.getContentPane().add(new FixedColumnTablePanel());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				createAndShowGUI();
			}
		});
	}
}
