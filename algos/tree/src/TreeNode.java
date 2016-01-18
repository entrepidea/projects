public class TreeNode {
	public int val;
	public TreeNode lChild;
	public TreeNode rChild;
	
	public TreeNode(int val, TreeNode l, TreeNode r){
		this.val = val;
		lChild = l;
		rChild = r;
	}
	
	public TreeNode(int val){
		this(val,null,null);
	}
}

