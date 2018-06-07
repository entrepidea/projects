package swing.components.checkbox;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class TristateCheckbox extends JCheckBox {

	private static class State {
		String desc = "";
		//"NOT_SELECTED","CHECKED", "CROSSED"
		private State(){}
		
		private State(String s){
			desc = s;
		}
		@Override
		public String toString(){
			return desc;
		}
	}
	
	public static final State NOT_SELECTED = new State("NOT_SELECTED");
	public static final State CHECKED = new State("CHECKED");
	public static final State CROSSED = new State("CROSSED");
	
	private TristateCheckModel model = null;
	
	public TristateCheckbox(){
		this(null);
	}
	
	public TristateCheckbox(String text){
		super(text);
		//set properties and model
		super.setIcon(new TristateIcon());
		setModel((model = new TristateCheckModel(getModel())));
		setState(NOT_SELECTED);
		
		//add listeners
		super.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e){
				TristateCheckbox.this.mousePressed();
			}
			@Override
			public void mouseReleased(MouseEvent e){
				TristateCheckbox.this.mouseReleased();
			}
		});
	}
	
	private void mousePressed(){
		System.out.println("mouse pressed");
		grabFocus();
		model.setArmed(true);
		model.setPressed(true);
	}
	
	private void mouseReleased(){
		System.out.println("mouse released");
		model.nextState();
		model.setArmed(false);
		model.setPressed(false);
	}
	
	public void doClick(){
		mousePressed();
		mouseReleased();
	}

	public void setState(State s){
		model.setState(s);
	}
	
	public State getState(){
		return model.getState();
	}
	
	
	public void setSelected(boolean selected) {
		if (selected) {
			setState(CHECKED);
		} else {
			setState(NOT_SELECTED);
		}
	}
	
	private class TristateCheckModel implements ButtonModel{

		ButtonModel model = null;
		State currentState = NOT_SELECTED;
		
		public TristateCheckModel(ButtonModel model){
			this.model = model;
		}
		
		public void setState(State s){
			currentState = s;
		};
		
		public State getState(){
			return currentState;
		}
		
		public void nextState(){
			State s = getState();
			System.out.println("current state: "+s);
			if(s==NOT_SELECTED){
				setState(CHECKED);
			}
			else if(s == CHECKED){
				setState(CROSSED);
			}
			else if(s== CROSSED){
				setState(NOT_SELECTED);
			}
			System.out.println(getState());
			model.setSelected(!model.isSelected()); //trigger the fireEvent
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
			return (currentState == CHECKED || currentState == CROSSED);
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
			try {
				setFocusable(b);	
			} catch (Exception ex) {
				ex.printStackTrace();
			}//catch
			
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
			model.setActionCommand(s);
		}

		@Override
		public String getActionCommand() {
			return model.getActionCommand();
		}

		@Override
		public void setGroup(ButtonGroup group) {
			model.setGroup(group);
		}

		@Override
		public void addActionListener(ActionListener l) {
			model.addActionListener(l);
		}

		@Override
		public void removeActionListener(ActionListener l) {
			model.removeActionListener(l);
		}

		@Override
		public void addItemListener(ItemListener l) {
			model.addItemListener(l);
		}

		@Override
		public void removeItemListener(ItemListener l) {
			model.removeItemListener(l);
		}

		@Override
		public void addChangeListener(ChangeListener l) {
			model.addChangeListener(l);
		}

		@Override
		public void removeChangeListener(ChangeListener l) {
			model.removeChangeListener(l);
		}
		
	}
	
	private class TristateIcon implements Icon, UIResource, Serializable{
 
		private static final long serialVersionUID = 1L;

		protected int getControlSize() {
			return 13;
		}
 
		public void paintIcon(Component c, Graphics g, int x, int y) {
			JCheckBox cb = (JCheckBox)c;
			TristateCheckModel model = (TristateCheckModel)cb.getModel();
			
			boolean bDrawCross = model.getState() == CROSSED;
			boolean bDrawCheck = model.getState() == CHECKED;
			
			int controlSize = getControlSize();
			
			if(model.isEnabled()){
				if(model.isPressed() && model.isArmed()){
					g.setColor(MetalLookAndFeel.getControlShadow());
					g.fillRect(x, y, controlSize - 1, controlSize - 1);
					drawPressed3DBorder(g, x, y, controlSize, controlSize);
				}
				else{
					drawFlush3DBorder(g,x,y,controlSize,controlSize);
				}
				g.setColor(MetalLookAndFeel.getControlInfo());
			}
			else{
				g.setColor(MetalLookAndFeel.getControlInfo());
				g.drawRect(x, y, controlSize-1, controlSize-1);
			}
			
			//draw cross
			if(bDrawCross){
				drawCross(c,g,x,y);
			}
			
			//draw check
			if(bDrawCheck){

				if (cb.isBorderPaintedFlat()) {
					x++;
				}
				drawCheck(c, g, x, y);
			
			}
		}// paintIcon
 

 
 
		public int getIconWidth() {
			return getControlSize();
		}
 
		public int getIconHeight() {
			return getControlSize();
		}

		protected void drawCross(Component c, Graphics g, int x, int y) {
			int controlSize = getControlSize();
			g.drawLine(x + (controlSize - 4), y + 2, x + 3, y
					+ (controlSize - 5));
			g.drawLine(x + (controlSize - 4), y + 3, x + 3, y
					+ (controlSize - 4));
			g.drawLine(x + 3, y + 2, x + (controlSize - 4), y
					+ (controlSize - 5));
			g.drawLine(x + 3, y + 3, x + (controlSize - 4), y
					+ (controlSize - 4));
		}
		
		protected void drawCheck(Component c, Graphics g, int x, int y) {
			int controlSize = getControlSize();
			g.fillRect(x + 3, y + 5, 2, controlSize - 8);
			g.drawLine(x + (controlSize - 4), y + 3, x + 5, y
					+ (controlSize - 6));
			g.drawLine(x + (controlSize - 4), y + 4, x + 5, y
					+ (controlSize - 5));
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
	}
	
	
	private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TristateCheckBoxXXXX");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        TristateCheckbox c = new TristateCheckbox("Tristate CheckBox");
        frame.getContentPane().add(c, BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); 
            }
        });
    }

}
