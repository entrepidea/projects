package com.entrepidea.algo.pattern.top_k_elements;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @Desc:
 * For an arbitrary array, to pick up the top K largest elements
 * Below is the implementation using MaxHeap. It can be implemented using enhanced sorting, see the relevant category in this project for the code.
 * @Source: https://www.geeksforgeeks.org/max-heap-in-java/
 * @Date: 04/15/20
 * */
public class MaxHeap {
    private int[] Heap;
    private int size;
    private int maxsize;

    // Constructor to initialize an
    // empty max heap with given maximum
    // capacity.
    public MaxHeap(){
        this.maxsize = 10000001;
        //this.maxsize = maxsize;
        this.size = 0;
        Heap = new int[this.maxsize + 1];
        Heap[0] = Integer.MAX_VALUE;
    }

    // Returns position of parent
    private int parent(int pos)
    {
        return pos / 2;
    }

    // Below two functions return left and
    // right children.
    private int leftChild(int pos)
    {
        return (2 * pos);
    }
    private int rightChild(int pos)
    {
        return (2 * pos) + 1;
    }

    // Returns true of given node is leaf
    private boolean isLeaf(int pos)
    {
        if (pos >= (size / 2) && pos <= size) {
            return true;
        }
        return false;
    }

    private void swap(int fpos, int spos)
    {
        int tmp;
        tmp = Heap[fpos];
        Heap[fpos] = Heap[spos];
        Heap[spos] = tmp;
    }

    // A recursive function to max heapify the given
    // subtree. This function assumes that the left and
    // right subtrees are already heapified, we only need
    // to fix the root.
    private void maxHeapify(int pos)
    {
        if (isLeaf(pos))
            return;

        if (Heap[pos] < Heap[leftChild(pos)] ||
                Heap[pos] < Heap[rightChild(pos)]) {

            if (Heap[leftChild(pos)] > Heap[rightChild(pos)]) {
                swap(pos, leftChild(pos));
                maxHeapify(leftChild(pos));
            }
            else {
                swap(pos, rightChild(pos));
                maxHeapify(rightChild(pos));
            }
        }
    }

    // Inserts a new element to max heap
    public void insert(int element)
    {
        Heap[++size] = element;

        // Traverse up and fix violated property
        int current = size;
        while (Heap[current] > Heap[parent(current)]) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    public void print()
    {
        for (int i = 1; i <= size / 2; i++) {
            System.out.print(" PARENT : " + Heap[i] + " LEFT CHILD : " +
                    Heap[2 * i] + " RIGHT CHILD :" + Heap[2 * i + 1]);
            System.out.println();
        }
    }

    // Remove an element from max heap
    public int extractMax()
    {
        int popped = Heap[1];
        Heap[1] = Heap[size--];
        maxHeapify(1);
        return popped;
    }

    private final int[] arr = new int[]{15,5,3,17,10,84,19,6,22,9};
    private MaxHeap maxHeap;
    @Before
    public void setup(){
        maxHeap = new MaxHeap();
    }
    @Test
    public void test(){
        System.out.println("Construct a MaxHeap tree:");
        for(int x : arr){
            maxHeap.insert(x);
        }
        System.out.println("How does the MaxHeap Tree look like:");
        maxHeap.print();
        int topKMax = 3;
        System.out.printf("The largest %d elements in the array are:\n", topKMax);
        for (int i=0;i<topKMax;i++){
            System.out.println(maxHeap.extractMax());
        }
    }

    //discussion in this link shows some techniques to generate large unique random numbers.
    //https://stackoverflow.com/questions/8115722/generating-unique-random-numbers-in-java

    @Test
    public void test2(){
        long start = System.currentTimeMillis();

        int[] largeIntSet = ThreadLocalRandom.current().ints(0, Integer.MAX_VALUE).distinct().limit(10000000).toArray();

        System.out.printf("It takes %d millseconds to generate 10000000 integers.\n", (System.currentTimeMillis()-start));

        for(int x : largeIntSet){
            maxHeap.insert(x);
        }

        int topKMax = 50;
        System.out.printf("The largest %d elements in the array are:\n", topKMax);
        for (int i=0;i<topKMax;i++){
            System.out.println(maxHeap.extractMax());
        }
        System.out.printf("It takes %d millseconds to complete the computation.\n", (System.currentTimeMillis()-start));
    }
}
