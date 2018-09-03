package com.entrepidea.algo.tests.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

import org.junit.Before;
import org.junit.Test;

//Question:
//Blackrock, phone interview with Kenny Ma, 08/18/14
// What's Binary Search Tree in data structure?

//similar:
//10/01/14, 5:30PM, BofA phone interview with Wilson
//12. Explain Breath-first traversal and depth-first traversal

//Answer:
// Binary tree is a non-linear data structure for storage. Each node has none or at most two children.
// A tree has root; each child has only one parent; no children can point to root node or their siblings.
//Traversal of binary tree can be depth-first or breadth-first.
//Depth-first can be further categorized as pre-, in-, post-order traversal, with a order of NLR, LNR and LRN.
//N: parent node; L: left-child; R: right-child.
//depth-first traversal can be implemented using Stack, or recursively.
//Breadth-first traversal goes from top layer, followed by the level beneath it and on and on.
// Breadth-first can be done using a Queue

//@See also:
// https://en.wikipedia.org/wiki/Tree_traversal
//https://en.wikipedia.org/wiki/Binary_search_tree

public class BinaryTreeTests {

	class TreeNode {
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

	//test breadth first search using a queue
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

	//TODO test breadth first search using recursion
	@Test
    public void testBreadthFirstSearchRecursively(){

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



    //Citi onsite (Jersey City) 08/20/13, from 09AM to 11:30AM.
    //TODO Performance big O about tree. It seemed he want to sort an array but wanted to transform it to a order B-tree and traverse it. And he likes to know the performance in term of big O.

	//TODO
	//Morgan Stanley, onsite, 05/09/12
	/*
	* 2.       2*3-5, parse the string, the final string form is 23*5-, write a method, the input is a bin-tree like

                     -

                    /\

				*        \

                  /\      \

                 2 3   5

			Do the pre-order traversal, the output will be 23*5-

			This is a RPN (reverse polish notation) question.
	*
	* */
}

