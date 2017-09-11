package swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
class AddNodeDlg extends JDialog {
	
	public static final int OK_BUTTON = 1;
	public static final int CANCEL_BUTTON=0;
	
	private JPanel parent;
	
	private JComboBox nodeType;

	private JTextField nameField;

	public AddNodeDlg(JPanel parent){
		this(parent, true);
	}
	
	public AddNodeDlg(JPanel parent, boolean modal) {
		//super(parent, "Add a Node", modal);
		this.parent = parent;
		setResizable(false);
		setTitle("Add a node");
		setSize(new Dimension(300, 200));
		setBackground(Color.gray);
		JPanel topPanel = new JPanel();
		topPanel.setLayout(null);
		getContentPane().add(topPanel);
		// Create a field and label
		String[] types = {new String("Floder"), new String("File")};
		nodeType = new JComboBox(types);
		nodeType.setBounds(20, 40, 260, 25);
		nodeType.setName("type");
		
		// loginField.setInputVerifier(stringVerifier);
		topPanel.add(nodeType);
		JLabel label1 = new JLabel("pick a type:");
		label1.setBounds(20, 15, 260, 20);
		label1.setLabelFor(nodeType);
		topPanel.add(label1);
		
		// Create a second label and text field
		nameField = new JTextField();
		nameField.setBounds(20, 90, 260, 25);
		nameField.setFocusAccelerator('p');
		nameField.setName("password");
		nameField.setText("New Node");
		topPanel.add(nameField);
		JLabel label2 = new JLabel("Name:");
		label2.setDisplayedMnemonic('P');
		label2.setBounds(20, 65, 260, 20);
		label2.setLabelFor(nameField);
		topPanel.add(label2);
		
		// Create a button and add it to the panel
		JButton okButton = new JButton("OK");
		okButton.setBounds(40, 145, 100, 25);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ok action
				ok_action_performed();
			}
		});
		topPanel.add(okButton);
		// Create a button and add it to the panel
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(160, 145, 100, 25);
		cancelButton.setEnabled(true);
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//cancel action
				dispose();
			}
		});
		topPanel.add(cancelButton);
		okButton.setVerifyInputWhenFocusTarget(false);
		cancelButton.setVerifyInputWhenFocusTarget(false);
	}
	
	public void center(JFrame parent){
		//Util.center(parent, this);
	}
	
	public void showDialog(){
		setVisible(true);
	}
	
	public void ok_action_performed(){
		if(parent!=null && (parent instanceof NavigatorWnd)){
			File file = ((NavigatorWnd)parent).getSelectedFile();
			if(file==null) return; //is this reachable? I don't think so!
			assert file.isDirectory();
			if(nodeType.getSelectedIndex()==0){//to create a folder
				String path = file.getAbsolutePath();
				path += "\\";
				path += nameField.getText();
				new File(path).mkdir();
			}
			else{//to create a file
				String path = file.getAbsolutePath();
				path += "\\";
				path += nameField.getText();
				try{
					new File(path).createNewFile();
				}
				catch(IOException e){}
			}
			//((NavigatorWnd)parent).refresh(nameField.getText());
			dispose();
		}
	}
	
}


public class NavigatorWnd extends JPanel implements TreeSelectionListener {
	private final static long serialVersionUID = 0L;
	private JTree navTree = new JTree();

	protected JPopupMenu m_popup  = new JPopupMenu();
	private Map<String, Action> actionMap = new HashMap<String, Action>();
	private SortedTreeModel m_model = null;
		
	public NavigatorWnd(String rootName){
		
		super(new GridLayout(1,0));
		
		File top = new File(rootName);
		FileNode rootNode = new FileNode(top);
		m_model = new SortedTreeModel(rootNode);
		createModel(m_model,rootNode);
		navTree.setModel(m_model);
		navTree.add(m_popup);
		navTree.addMouseListener(new PopupTrigger(navTree,actionMap.get("Expand") ));
		JScrollPane scrollPane = new JScrollPane(navTree);
       add(scrollPane);
       
		navTree.setEditable(false);
		navTree.addTreeSelectionListener(this);
		
	}
	
	public NavigatorWnd() {
		this("KnowledgeBase");
		//action map
		initActionMap();

		Action action = (Action)actionMap.get("Expand");
        action.putValue("Name", "Expand");
		m_popup.add(action);
		
		action = (Action)actionMap.get("Delete");
        action.putValue("Name", "Delete");
		m_popup.add(actionMap.get("Delete"));
		
		action = (Action)actionMap.get("Add");
        action.putValue("Name", "Add");
		m_popup.add(actionMap.get("Add"));
		
	}
	
	
	public void createModel(DefaultTreeModel model, FileNode parent){
		File top = parent.getAssociatedFile();
		
		if(top.isDirectory()){		
			File[] files = top.listFiles();
			for(File f : files){
				FileNode child = new FileNode(f);
				model.insertNodeInto(child, parent,parent.getChildCount());
				createModel(model, child);					
			}
		}
			
	}
	
	 private void initActionMap()
	 {
	        actionMap.put("Expand", new DummyAction(this));
	        actionMap.put("Delete", new DummyAction(this));
	        actionMap.put("Add", new DummyAction(this));
	       
	 }
	 
	 public FileNode findNodeByPath(String target){
			FileNode node = null;
			FileNode root = (FileNode)m_model.getRoot();

			if (root != null){
			    for (Enumeration e = root.breadthFirstEnumeration(); e.hasMoreElements();)
			    {
			    	FileNode current = (FileNode)e.nextElement();
			        if (target.equals(current.getFilePath()))
			        {
			            node = current;
			            break;
			        }
			    }
			}
			return node;			
		}

	public void valueChanged(TreeSelectionEvent evt) {
		FileNode node = (FileNode) navTree
				.getLastSelectedPathComponent();
		
		if (node == null){return;}
		
		if (node == null || node.getAssociatedFile().isDirectory()){ return; }
		
	}
	
	public File getSelectedFile(){
		 TreePath currentSelection = navTree.getSelectionPath();
	        if (currentSelection != null) {
	            FileNode currentNode = (FileNode)
	                         (currentSelection.getLastPathComponent());
	            return currentNode.getAssociatedFile();
	        }
	        return null;
	}
	
	public FileNode getSelectedNode(){
		TreePath currentSelection = navTree.getSelectionPath();
        if (currentSelection != null) {
            FileNode currentNode = (FileNode)
                         (currentSelection.getLastPathComponent());
            return currentNode;
        }
        return null;
	}
	
	private class PopupTrigger extends MouseAdapter
	  {
		 private JTree m_tree;
		 PopupTrigger(JTree tree, Action action){
			 m_tree = tree;
		 }		 
	    public void mouseReleased(MouseEvent e)
	    {
	    	super.mouseReleased(e);	    	
	    	if (e.isPopupTrigger())
	    	{
	    		int x = e.getX();
	    		int y = e.getY();
	    		TreePath path = m_tree.getPathForLocation(x, y);
	    		if (path != null){m_popup.show(m_tree, x, y);}
	    	}
	    }
	  }
	 
	 private class DummyAction extends AbstractAction {
		 JPanel parent=null;
		 DummyAction(JPanel p){
			 parent=p;
		 }
		public void actionPerformed(ActionEvent e){
			if(parent!=null && parent instanceof NavigatorWnd){
				TreePath currentSelection = navTree.getSelectionPath();
		        if (currentSelection != null) {
		            FileNode currentNode = (FileNode)
		                         (currentSelection.getLastPathComponent());
		            if(currentNode.isLeaf()){
		            	System.out.println("Can't add an item beneath a leaf! ");
		            }
		            else{
		            	//pop up a dialog box
		            	AddNodeDlg dlg  = new AddNodeDlg(parent);
		        		//dlg.center(frame);
		        		dlg.showDialog();
		        		
		        		//TODO: 1 create a folder or a file physically
		        		
		        		//TODO: 2 renew the tree's look
		        		//((NavigatorWnd)parent).addObject("New Node ");
		            }
		        }
				
			}
		}
	 }
	 
	 
	 /** Remove the currently selected node. */
	 /*   public void removeCurrentNode() {
	        TreePath currentSelection = navTree.getSelectionPath();
	        if (currentSelection != null) {
	            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
	                         (currentSelection.getLastPathComponent());
	            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
	            if (parent != null) {
	            	SortedTreeModel model = (SortedTreeModel)navTree.getModel();
	                model.removeNodeFromParent(currentNode);
	                return;
	            }
	        } 
	    }*/
	    
	    
	    /** Add child to the currently selected node. */
	 /*   public FileNode addObject(Object child) {
	        FileNode parentNode = null;
	        TreePath parentPath = navTree.getSelectionPath();

	        if (parentPath == null) {
	            parentNode = rootNode;
	        } else {
	            parentNode = (FileNode)
	                         (parentPath.getLastPathComponent());
	        }

	        return addObject(parentNode, child, true);
	    }

	    public FileNode addObject(FileNode parent,
	                                            Object child) {
	        return addObject(parent, child, false);
	    }

	    public FileNode addObject(FileNode parent,
	                                            Object child, 
	                                            boolean shouldBeVisible) {
	    	FileNode childNode = 
	                new FileNode(child);

	        if (parent == null) {
	            parent = rootNode;
	        }
	        SortedTreeModel model = (SortedTreeModel)navTree.getModel();
	        model.insertNodeInto(childNode, parent, 
	                                 parent.getChildCount());

	        //Make sure the user can see the lovely new node.
	        if (shouldBeVisible) {
	        	navTree.scrollPathToVisible(new TreePath(childNode.getPath()));
	        }
	        return childNode;
	    }*/
 
	 
	 public static void main(String[] args){
		 JFrame frame = new JFrame("demo");
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.setSize(new Dimension(350,250));
		 frame.getContentPane().add(new NavigatorWnd(), BorderLayout.CENTER);
		 frame.setVisible(true);
		 		 
	 }
	 
	 
    //tree model
    private final class SortedTreeModel extends DefaultTreeModel implements Comparator
    {
    	private final static long serialVersionUID = 0L;

        public SortedTreeModel(FileNode node)
        {
            super(node);
        }

        public SortedTreeModel(FileNode node, boolean asksAllowsChildren)
        {
            super(node, asksAllowsChildren);
        }

        public void insertNodeInto(FileNode child, FileNode parent)
        {
            int index = findIndexFor(child, parent);
            super.insertNodeInto(child, parent, index);
        }

        public void insertNodeInto(FileNode child, FileNode par, int i)
        {
            insertNodeInto(child, par);
        }

        private int findIndexFor(FileNode child, FileNode parent)
        {
            int cc = parent.getChildCount();
            if(cc == 0)
                return 0;
            if(cc == 1)
                return compare(child, parent.getChildAt(0)) > 0 ? 1 : 0;
            else
                return findIndexFor(child, parent, 0, cc - 1);
        }

        private int findIndexFor(FileNode child, FileNode parent, int i1, int i2)
        {
            if(i1 == i2)
                return compare(child, parent.getChildAt(i1)) > 0 ? i1 + 1 : i1;
            int half = (i1 + i2) / 2;
            if(compare(child, parent.getChildAt(half)) <= 0)
                return findIndexFor(child, parent, i1, half);
            else
                return findIndexFor(child, parent, half + 1, i2);
        }
        
        public int compare(Object o1, Object o2)
        {
            if(!(o1 instanceof FileNode) || !(o2 instanceof FileNode))
            {
                throw new IllegalArgumentException("Can only compare FileNode objects");
            } else
            {
                String s1 = ((FileNode)o1).getFileName();
                String s2 = ((FileNode)o2).getFileName();
                return s1.compareToIgnoreCase(s2);
            }
        }    
    }
	
	
	// user object
	private final class FileNode extends DefaultMutableTreeNode
	{
		private static final long serialVersionUID = 0L;
		File file;
		FileNode(File f){
			file = f;
		}	
		public File getAssociatedFile(){
			return file;
		}
		
		public URL toURL() throws MalformedURLException{
			return file.toURL();
		}
	     public String getFilePath()
	     {
	         return file.getPath();
	     }
	     public String getFileName()
	     {
	         return file.getName();
	     }
	     public String toString(){
	    	 return file.getName();
	     }
	}
}

