public class TreeDemo {

	static class TreeNode{
		
		private int value;
		private TreeNode lchild;
		private TreeNode rchild;
		
		public TreeNode(int val){
			this.value = val;
		}
		
		public TreeNode(int val, TreeNode lchild, TreeNode rchild){
			value = val; this.lchild = lchild; this.rchild = rchild;
		}
		
		public int getValue(){return value;}
		
		public TreeNode getLChild(){return lchild;}
		
		public TreeNode getRChild(){return rchild;}
		
	}
	
	
	public TreeNode createTree(){
		// build up the tree.
	    //This is the tree that we experiment with
	    //          5
	    //         / \
	    //        3   6
	    //       /   / \
	    //      7   1  9
	    //              \
	    //               2

	    // Construct from lowest level to the highest level.

	    TreeNode node2 = new TreeNode(2);
	    TreeNode node9 = new TreeNode(9, null, node2);
	    TreeNode node1 = new TreeNode(1);
	    TreeNode node6 = new TreeNode(6, node1, node9);
	    
	    TreeNode node7 = new TreeNode(7);
	    TreeNode node3 = new TreeNode(3, node7, null);
	    TreeNode node5 = new TreeNode(5, node3, node6);
	 
	    TreeNode root = node5;
	    
	    return root;
	 
	}
	
	private void printNode(TreeNode node){
		if(node!=null){
			System.out.println(node.getValue());
		}
	}
	
	public void preOrder(TreeNode root){
		if(root!=null){
			printNode(root);
			preOrder(root.getLChild());
			preOrder(root.getRChild());
		}
	}
	
	public void inOrder(TreeNode root){
		if(root!=null){
			inOrder(root.getLChild());
			printNode(root);
			inOrder(root.getRChild());
		}
	}
	
	public void postOrder(TreeNode root){
		if(root!=null){
			postOrder(root.getLChild());
			postOrder(root.getRChild());
			printNode(root);
		}
	}
	
	
	public static void main(String[] args) {
		TreeDemo demo = new TreeDemo();
		TreeNode root = demo.createTree();
		
		System.out.println("pre-order");
		demo.preOrder(root);
		System.out.println("in-order");
		demo.inOrder(root);
		System.out.println("post-order");
		demo.postOrder(root);
	}
}


