package com.entrepidea.swing.components.button;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.MetalLookAndFeel;

/**
 *  This program is to summarize the steps for creating a customized, responsive button or other components 
 *  
 *	1. set State properties
 *	2. add listeners, update the states in their handlers
 *	3. create customized button model
 *	4. override paintComponent
 *	 
 * */

public class CombinedButton extends JButton{

	private static class State {
		private String desc = "";
		
		public State(String desc){
			this.desc = desc;
		}
		
		@Override
		public String toString(){
			return desc;
		}
	}

	private static final int SIZE = 60;
	private static final int startX = 20;
	private static final int startY = 20;
	private static final int PADDING = 10;
	
	private static final Point centP = new Point(startX+SIZE/2,startY+SIZE/2);
	
	private static final State STEP0 = new State("STEP0");
	private static final State STEP1 = new State("STEP1");
	private static final State STEP2 = new State("STEP2");
	private static final State STEP3 = new State("STEP3");
	
	private CombinedButtonModel model = null;
	
	
	public CombinedButton(){
		
		setModel(model = new CombinedButtonModel(getModel()));
		model.setState(STEP0);
		
		addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e){
				System.out.println("pressed");
				grabFocus();
				model.setPressed(true);
				model.setArmed(true);
			}
			@Override
			public void mouseReleased(MouseEvent e){
				System.out.println("released, state = "+getState());
				model.nextState();
				model.setPressed(false);
				model.setArmed(false);
			}
		});
	}
	
	public State getState(){
		return model.getState();
	}
	
	private void drawFlush3DBorder(Graphics g, int x, int y, int w, int h) {
		g.translate(x, y);
		g.setColor(MetalLookAndFeel.getControlDarkShadow());
		g.drawRect(0, 0, w - 2, h - 2);
		g.setColor(MetalLookAndFeel.getControlHighlight());
		g.drawRect(1, 1, w - 2, h - 2);
		g.setColor(MetalLookAndFeel.getControl());
		g.drawLine(0, h - 1, 1, h - 2);
		g.drawLine(w - 1, 0, w - 2, 1);
		g.translate(-x, -y);
	}
	
	private void drawPressed3DBorder(Graphics g, int x, int y, int w, int h) {
		g.translate(x, y);
		drawFlush3DBorder(g, 0, 0, w, h);
		g.setColor(MetalLookAndFeel.getControlShadow());
		g.drawLine(1, 1, 1, h - 2);
		g.drawLine(1, 1, w - 2, 1);
		g.translate(-x, -y);
	}
	
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.draw3DRect(startX, startY, SIZE, SIZE, true);
		g2d.setColor(MetalLookAndFeel.getControlShadow());
		g2d.fillRect(startX, startY, SIZE, SIZE);

		if(model.isEnabled()){
			if(model.isPressed() && model.isArmed()){
				g2d.setColor(MetalLookAndFeel.getControlShadow());
				g2d.fillRect(startX, startY, SIZE - 1, SIZE - 1);
				drawPressed3DBorder(g2d, startX, startY, SIZE, SIZE);
			}
			else{
				drawFlush3DBorder(g,startX,startY,SIZE,SIZE);
			}
			g.setColor(MetalLookAndFeel.getControlInfo());
		}
		else{
			g.setColor(MetalLookAndFeel.getControlInfo());
			g.drawRect(startX, startY, SIZE-1, SIZE-1);
		}
		
		Stroke pen = new BasicStroke (7.0F, BasicStroke.CAP_BUTT, 
                BasicStroke.JOIN_BEVEL);
		g2d.setStroke(pen);
		g2d.setColor(MetalLookAndFeel.getControlDarkShadow());
		if(getState() == STEP1){
			g2d.drawLine(startX+PADDING, startY+PADDING, startX+SIZE-PADDING,startY+PADDING);
		}
		else if(getState() == STEP2){
			g2d.drawLine(startX+PADDING, startY+PADDING, startX+SIZE-PADDING,startY+PADDING);
			g2d.drawLine(startX+SIZE-3*PADDING, startY+PADDING, startX+SIZE-3*PADDING,startY+SIZE-PADDING);
		}
		else if(getState() == STEP3){
			g2d.drawLine(startX+PADDING, startY+PADDING, startX+SIZE-PADDING,startY+PADDING);
			g2d.drawLine(startX+SIZE-3*PADDING, startY+PADDING, startX+SIZE-3*PADDING,startY+SIZE-PADDING);
			g2d.drawLine(startX+PADDING, startY+SIZE-PADDING, startX+SIZE-PADDING,startY+SIZE-PADDING);
		}
		repaint();
	}
	
	private static class CombinedButtonModel implements ButtonModel{
		
		ButtonModel model = null;
		private State currentState = null;
		
		public CombinedButtonModel(ButtonModel model){
			this.model = model;
		}

		public void setState(State s){
			currentState = s;
		}
		
		public State getState(){
			return currentState;
		}
		
		
		public void nextState(){
			if(currentState == STEP0){
				setState(STEP1);
			}
			else if(currentState == STEP1){
				setState(STEP2);
			}
			else if(currentState == STEP2){
				setState(STEP3);
			}
			else if(currentState == STEP3){
				setState(STEP0);
			}
			
			setSelected(!isSelected());
		}
		
		@Override
		public Object[] getSelectedObjects() {
			return model.getSelectedObjects();
		}

		@Override
		public boolean isArmed() {
			return model.isArmed();
		}

		@Override
		public boolean isSelected() {
			return model.isSelected();
		}

		@Override
		public boolean isEnabled() {
			return model.isEnabled();
		}

		@Override
		public boolean isPressed() {
			return model.isPressed();
		}

		@Override
		public boolean isRollover() {
			return model.isRollover();
		}

		@Override
		public void setArmed(boolean b) {
			model.setArmed(b);
		}

		@Override
		public void setSelected(boolean b) {
			model.setSelected(b);
		}

		@Override
		public void setEnabled(boolean b) {
			model.setEnabled(b);
			
		}

		@Override
		public void setPressed(boolean b) {
			model.setPressed(b);
			
		}

		@Override
		public void setRollover(boolean b) {
			model.setRollover(b);
			
		}

		@Override
		public void setMnemonic(int key) {
			model.setMnemonic(key);
		}

		@Override
		public int getMnemonic() {
			return model.getMnemonic();
		}

		@Override
		public void setActionCommand(String s) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String getActionCommand() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setGroup(ButtonGroup group) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addActionListener(ActionListener l) {
			// TODO Auto-generated method stub
		}

		@Override
		public void removeActionListener(ActionListener l) {
			// TODO Auto-generated method stub
		}

		@Override
		public void addItemListener(ItemListener l) {
			// TODO Auto-generated method stub
		}

		@Override
		public void removeItemListener(ItemListener l) {
			// TODO Auto-generated method stub
		}

		@Override
		public void addChangeListener(ChangeListener l) {
			// TODO Auto-generated method stub
		}

		@Override
		public void removeChangeListener(ChangeListener l) {
			// TODO Auto-generated method stub
		}
		
	}
	
	private static void createAndShowUI(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(250,250));
		
		panel.add(new CombinedButton(), BorderLayout.CENTER);
		
		JFrame frame = new JFrame("Customized Buttons");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel);
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
