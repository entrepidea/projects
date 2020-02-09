package com.entrepidea.swing.components.tree;

import java.awt.Component;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultTreeCellRenderer;

public class TreeTooltipDemo {

	/**
	 * @param args
	 */
	private static void createAndShowUI(){
		JFrame f = new JFrame("TreeTooltipTest ");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTree tree = new JTree();
        tree.setCellRenderer(new MyTreeCellRenderer());
        ToolTipManager.sharedInstance().registerComponent(tree);
        f.add(new JScrollPane(tree));
        f.setSize(400,300);
        f.setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				createAndShowUI();
			}
		});
	}
	
	private static class MyTreeCellRenderer extends DefaultTreeCellRenderer {
		 
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            if (row % 2 == 0) {
                setToolTipText("Even row: " + row);
            } else {
                setToolTipText("Odd row: " + row);
            }
            return this;
        }
 
        public String getToolTipText(MouseEvent event) {
            return this.getToolTipText();
        }
    }
}
