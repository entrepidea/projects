package com.entrepidea.algo.tests.tree;

import java.util.Stack;

public class BinarySearchTree {

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


    //create a Node class
    static class Node {
        public char value;
        public Node lChild;
        public Node rChild;

        public Node (char v, Node l, Node r){
            value = v; lChild = l; rChild = r;
        }
        public Node (char v){
            this(v, null,null);
        }
    }

    //construct a tree
    //      A
    //    /   \
    //   /     \
    //  B       C
    //   \     / \
    //    D   E   F
    //     \       \
    //      G       H
    //             / \
    //            I   J
    //           / \
    //          K   L

    private static Node construct(){
        Node a = new Node('A');
        Node b = new Node('B');
        Node c = new Node('C');
        Node d = new Node('D');
        Node e = new Node('E');

        Node f = new Node('F');
        Node g = new Node('G');
        Node h = new Node('H');
        Node i = new Node('I');
        Node j = new Node('J');

        Node k = new Node('K');
        Node l = new Node('L');

        a.lChild = b; a.rChild = c;
        b.rChild = d;
        c.lChild = e;
        c.rChild = f;
        d.rChild = g;
        f.rChild = h;
        h.lChild = i;
        h.rChild = j;
        i.lChild = k;
        i.rChild = l;


        return a;
    };


    //depth-first in-order traversal recursively
    private static void inorder(Node node){
        if(node==null){
            return;
        }
        inorder(node.lChild);
        System.out.print(node.value+"\t");
        inorder(node.rChild);
    }

    //depth-first pre-order traversal recursively
    private static void preorder(Node node){
        if(node==null){
            return;
        }
        System.out.print(node.value+"\t");
        preorder(node.lChild);
        preorder(node.rChild);
    }

    //depth-first post-order traversal recursively
    private static void postorder(Node node){
        if(node==null){
            return;
        }
        postorder(node.lChild);
        postorder(node.rChild);
        System.out.print(node.value+"\t");
    }

    public static void main(String[] args){
        Node root = construct();

/*        System.out.println("preorder traversal (recursive)");
        preorder(root);
        System.out.println();

        System.out.println("preorder traversal (non-recursive)");
        preorder2(root);
        System.out.println();*/

        System.out.println("Inordr traversal (recursive)");
        inorder(root);
        System.out.println();

        System.out.println("Inordr traversal (non recursive)");
        inorder2(root);
        System.out.println();

        /*System.out.println("postorder traversal");
        postorder(root);
        System.out.println();*/
    }

    //depth-first traversal pre-order using Stack
    private static void preorder2(Node node){
        if (node == null){
            return;
        }
        Stack<Node> s = new Stack();
        s.push(node);
        while(!s.isEmpty()){
            Node n = s.pop();
            System.out.print(n.value+"\t");
            if(n.rChild!=null){
                s.push(n.rChild);
            }
            if(n.lChild!=null){
                s.push(n.lChild);
            }
        }
    }

    //TODO incorrect, FIXIT! depth-first traversal in-order using Stack
    private static void inorder2(Node node){
        if (node == null){
            return;
        }
        Stack<Node> s = new Stack();
        s.push(node);
        while(!s.isEmpty()){
            if(node.lChild!=null){
                s.push(node.lChild);
            }
            else{
                node = s.pop();
                System.out.print(node.value+"\t");
            }
            if(node.rChild!=null){
                s.push(node.rChild);
            }
        }
    }

    //TODO breadth-first traversal recursively
    //TODO breadth-first traversal using a Queue

}
