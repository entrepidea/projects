package com.entrepidea.algo.data_structure.sort;

import org.junit.Test;
/*
  desc: sort an array basing on the occurrence frequency .
* source: https://www.geeksforgeeks.org/sorting-algorithms/#problems
* Date: 01/03/19
* */
public class SortingElementByFreq {

    class Node {
        int val;
        int orgIdx;
        int freq;

        public Node(int v, int idx){
            this(v,idx,0);
        }
        public Node(int v, int idx, int f){
            val = v;
            orgIdx = idx;
            freq = f;
        }
    }

    public void bubbleSort(Node[] data)
    {
        for (int k = 0; k < data.length - 1; k++)
        {
            boolean isSorted = true;

            for (int i = 1; i < data.length - k; i++)
            {
                if (data[i].val < data[i - 1].val)
                {
                    Node tempVariable = data[i];
                    data[i] = data[i - 1];
                    data[i - 1] = tempVariable;
                    isSorted = false;
                }
            }
            if (isSorted)
                break;
        }
    }

    public void bubbleSort2(Node[] data)
    {
        for (int k = 0; k < data.length - 1; k++)
        {
            boolean isSorted = true;

            for (int i = 1; i < data.length - k; i++)
            {
                if (data[i].freq > data[i - 1].freq)
                {
                    Node tempVariable = data[i];
                    data[i] = data[i - 1];
                    data[i - 1] = tempVariable;
                    isSorted = false;
                }
            }
            if (isSorted)
                break;
        }
    }


    public void bubbleSort3(Node[] data)
    {
        for (int k = 0; k < data.length - 1; k++)
        {
            boolean isSorted = true;

            for (int i = 1; i < data.length - k; i++)
            {
                if (data[i].freq == data[i - 1].freq && data[i].orgIdx < data[i - 1].orgIdx)
                {
                    Node tempVariable = data[i];
                    data[i] = data[i - 1];
                    data[i - 1] = tempVariable;
                    isSorted = false;
                }
            }
            if (isSorted)
                break;
        }
    }

    private void print(Node[] arr){
        for(int i=0;i<arr.length;i++){
            System.out.print(arr[i].val);
            System.out.print(",");
        }
        System.out.println();
    }

    @Test
    public void test(){
        //int[] arr = new int[]{2, 5, 2, 5, 8, 6, 8, 8};
        //int[] arr = {5, 2, 6,2, -1, 9999999, 5, 8, 8, 8};
        int[] arr = {2, 3, 2, 4, 5, 12, 2, 3, 3, 3, 12};
        Node[] arr2 = new Node[arr.length];
        for(int i=0;i<arr.length;i++){
            arr2[i] = new Node(arr[i],i);
        }

        bubbleSort(arr2);

        print(arr2);

        Node[] arr3 = new Node[arr2.length];
        for(int i=0;i<arr3.length;i++){
            Node x = new Node(-1,-1,-1);
            arr3[i] = x;
        }

        int count = 0;
        Node temp=arr2[0];
        temp.freq=1;
        temp.orgIdx = arr2[0].orgIdx;
        for(int i=1;i<arr2.length;i++){
            if(temp.val==arr2[i].val){
                temp.freq++;
            }
            else{
                arr3[count++] = temp;
                temp = arr2[i];
                temp.orgIdx = arr2[i].orgIdx;
                temp.freq=1;
            }
        }
        arr3[count++] = temp;
        Node[] arr4 = new Node[count];
        count =0;
        for(int i =0;i<arr3.length;i++){
            if(arr3[i].freq==-1) continue;
            arr4[count++] = arr3[i];
        }
        bubbleSort2(arr4);
        bubbleSort3(arr4);
        int[] arr5 = new int[arr.length];
        count=0;
        for(int i=0;i<arr4.length;i++){
            for(int f=0;f<arr4[i].freq;f++){
                arr5[count++] = arr4[i].val;
            }
        }

        for(int i=0;i<arr5.length;i++){
            System.out.print(arr5[i]);
            System.out.print(",");
        }
        System.out.println();
    }
}
