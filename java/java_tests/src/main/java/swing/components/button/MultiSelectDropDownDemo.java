package swing.components.button;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
 
public class MultiSelectDropDownDemo implements ActionListener, Runnable {
	 
    private ImageIcon imgOpen;
 
    private ImageIcon imgClosed;
 
    private JToggleButton button;
 
    private JPopupMenu popup;
 
    private ActionListener buttonListener;
 
    public MultiSelectDropDownDemo() {
        imgOpen = getImage("image/icon/ArrowDownRed.gif");
        imgClosed = getImage("image/icon/ArrowRightRed.gif");
 
        button = new JToggleButton("Options");
        buttonListener = this;
        button.addActionListener(buttonListener);
 
        popup = new JPopupMenu();
        popup.setBorder(new MatteBorder(0, 1, 1, 1, Color.DARK_GRAY));
        popup.addPopupMenuListener(new PopupMenuListener() {
 
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                button.setIcon(imgOpen);
            }
 
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                button.setIcon(imgClosed);
                EventQueue.invokeLater(new Runnable() {
 
                    @Override
                    public void run() {
                        button.getModel().setArmed(false);
                        button.setSelected(false);
                    }
                });
            }
 
            public void popupMenuCanceled(PopupMenuEvent e) {/**/}
        });
 
        popup.add(new JCheckBox("check #1"));
        popup.add(new JCheckBox("check #2"));
        popup.add(new JCheckBox("check #3"));
        popup.add(new JCheckBox("check #4"));
        popup.add(new JCheckBox("check #5"));
    }
 
    public void actionPerformed(ActionEvent event) {
        if (button.isSelected()) {
            Rectangle bounds = button.getBounds();
            popup.show(button, bounds.x, bounds.y + button.getSize().height);
        } else {
            popup.setVisible(false);
        }
    }
    
    public ImageIcon getImage(String imageName) {
        URL imageUrl = MultiSelectDropDownDemo.class.getResource(imageName);
        if (imageUrl == null) {
            System.err.println("Image file not found: " + imageName);
            return null;
        }
        return new ImageIcon(imageUrl);
    }
 
    public void run() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = f.getContentPane();
        c.setLayout(new GridLayout(4, 1));
        c.add(button);
 
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new MultiSelectDropDownDemo());
    }
}
