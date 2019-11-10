package com.entrepidea.algo.tests.tree;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

import com.entrepidea.algo.tests.tree.supports.TreeNode;
import org.junit.Before;
import org.junit.Test;

/**
 *
Question:
Blackrock, phone interview with Kenny Ma, 08/18/14
    What's Binary Search Tree in data structure?

10/01/14, 5:30PM, BofA phone interview with Wilson
    Explain Breath-first traversal and depth-first traversal

Citi onsite (Jersey City) 08/20/13, from 09AM to 11:30AM.
    TODO Performance big O about tree. It seemed he want to sort an array but wanted to transform it to a order B-tree and traverse it. And he likes to know the performance in term of big O.

Morgan Stanley, onsite, 05/09/12
    parse the string "2*3-5", the final string form is 23*5-, write a method, the input is a bin-tree like
    Do the pre-order traversal, the output will be 23*5- . This is a RPN (reverse polish notation) question.



Answer:
Binary tree is a non-linear data structure for storage. Each node has none or at most two children.
A tree has root; each child has only one parent; no children can point to root node or their siblings.
Traversal of binary tree can be depth-first or breadth-first.
Depth-first can be further broken into pre-order(NLR), in-order(LNR), post-order(LRN) traversal (N: parent node; L: left-child; R: right-child).
depth-first traversal can be implemented using Stack(non-recursive), or recursive.
Breadth-first traversal goes from top layer, followed by the level beneath it and on and on.
Breadth-first can be done using a Queue, or recursive

@See also:
https://en.wikipedia.org/wiki/Tree_traversal
https://en.wikipedia.org/wiki/Binary_search_tree

*/
public class BinaryTreeTests {

	TreeNode root;

	@Before
	public void setUp() {
		
		//construct a tree
		/**
		  1
		 / \
		2   3
	   / \   \
	   4  5  6
	        /
	       7
         /
        8
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

		curr = curr.lChild;

		curr.lChild = new TreeNode(8);

	}

	//non-recursive BFS(breadth first search) using a queue
	@Test
	public void testBreadthFirstSearch() {
		Queue<TreeNode> q = new ArrayBlockingQueue<TreeNode>(16);
		q.add(root);
		
		TreeNode node;
		while((node=q.poll())!=null){
			System.out.print(node+", ");
			TreeNode n = node.lChild;
			if(n!=null)
				q.add(n);
			n = node.rChild;
			if(n!=null)
				q.add(n);
		}
		System.out.println();
	}

	//No difference from above, just re-practice the code
	@Test
	public void testBreadthFirstSearch2(){
		Queue<TreeNode> q = new ArrayBlockingQueue<>(16);
		q.add(root);
		TreeNode node;
		while((node=q.poll())!=null){
			System.out.print(node);
			TreeNode n = node.lChild;
			if(n!=null) q.add(n);
			n = node.rChild;
			if(n!=null) q.add(n);
		}
		System.out.println();
	}

	//breadth first search using recursion
	//Not a good idea, see this post: https://stackoverflow.com/questions/2549541/performing-breadth-first-search-recursively



    //inorder: recursive traversal
    private void inorder(TreeNode node){
	    if(node==null){
	        return;
        }
        inorder(node.lChild);
        System.out.print(node.val);
        inorder(node.rChild);
    }

    @Test
    public void testRecursiveInorderTraversal(){
	    inorder(root);
    }

    //inorder, non-recursive traversal, using stack
    //reference: https://www.geeksforgeeks.org/inorder-tree-traversal-without-recursion/
	@Test
	public void inorderTraversal(){
		Stack<TreeNode> st = new Stack<>();
		TreeNode cur = root;
        while(cur!=null || !st.isEmpty()){
            while(cur!=null) {
                st.push(cur);
                cur = cur.lChild;
            }
            cur = st.pop();
            System.out.print(cur.val);
            cur = cur.rChild;
        }
	}

	//this is another workable inorder traversal using stack
    @Test
	public void inorderTraversal2(){

	    if (root == null)
	       return;

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
            System.out.print(curr.val);

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

	    return;
	}

	//Preorder traversal
    private void preorder(TreeNode node){
	    if(node==null){
	        return;
        }
        System.out.print(node.val);
        preorder(node.lChild);
        preorder(node.rChild);

    }
	@Test
    public void testRecursivePreOrderTraversal(){
	    preorder(root);
    }

}

