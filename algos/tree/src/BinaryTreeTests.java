import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

import org.junit.Before;
import org.junit.Test;

public class BinaryTreeTests {

	TreeNode root;
	@Before
	public void setUp() throws Exception {
		
		//construct a tree
		/**
		 1
		/ \
		2  3
	   / \  \
	   4  5  6
	        /
	       7
	   **/
		TreeNode curr;
		root = new TreeNode(1);
		TreeNode node = new TreeNode(2);
		root.lChild = node;
		node = new TreeNode(3);
		root.rChild = node;
		curr = root.lChild;
		node = new TreeNode(4);
		curr.lChild = node;
		node = new TreeNode(5);
		curr.rChild = node;
		curr = root.rChild;
		curr.rChild = new TreeNode(6);
		curr = curr.rChild;
		curr.lChild = new TreeNode(7);
	}

	@Test
	public void testBreadthFirstSearch() {
		Queue<TreeNode> q = new ArrayBlockingQueue<TreeNode>(16);
		q.add(root);
		
		TreeNode node = null;
		while((node=q.poll())!=null){
			System.out.print(node.val+", ");
			TreeNode n = node.lChild;
			if(n!=null)
				q.add(n);
			n = node.rChild;
			if(n!=null)
			q.add(n);
		}
		System.out.println();
		
	}
	
	/*
	@Test
	public void inorderSearch(){
		Stack<TreeNode> st = new Stack<TreeNode>();
		TreeNode cur = root;
		boolean bPoped = false;
		
		while(cur!=null){
			if(cur.lChild!=null && !bPoped)
	        {
	            st.push(cur);
	            cur = cur.lChild;
	            bPoped = false;
	        }
	        else if(cur.rChild!=null)
	        {
	            System.out.print(cur.val+" ");
	            cur = cur.rChild;
	            bPoped = false;
	        }
	        else
	        {
	        	System.out.print(cur.val+" ");
	            if(null==st.pop())
	                break;
	            bPoped = true;
	        }	
		}
		
		System.out.println();
		
	}
	*/
	
	private ArrayList<Integer> inorderTraversal(){
		 ArrayList<Integer> list = new ArrayList<Integer>();
	       
	        if (root == null)
	           return list;
	          
	        Stack<TreeNode> sta = new Stack<TreeNode>();
	        boolean backtrack = false;
	        TreeNode curr = root;
	       
	        while (true) {
	            if (backtrack == false) {
	               while (curr.lChild != null) {
	                   sta.push(curr);
	                   curr = curr.lChild;
	               }
	            }
	            //without left branch or left branch has been visited
	            list.add(curr.val);
	           
	            if (curr.rChild != null) {
	               backtrack = false;
	               curr = curr.rChild;
	            }
	            else if (!sta.isEmpty()) {
	                    backtrack = true;
	                    curr = sta.pop();
	            }
	            else break;
	        }
	       
	        return list;
	    }
	
	@Test
	public void testInorderTraversal(){
		List<Integer> l = inorderTraversal();
		for(Integer i : l){
			System.out.print(i+", ");
		}
		System.out.println();
	}
}

