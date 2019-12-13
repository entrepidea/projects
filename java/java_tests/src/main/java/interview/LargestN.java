package interview;

import org.junit.jupiter.api.Test;

/**
 * @Desc: this is a on-site coding from Normura in Nov, 2019
 * Pick up the largest 2 elements from an array
 *
 * @Note: there are multiple answers to this problem, details see below
 * */
public class LargestN {


    public void printArry(int[] a){
        for(int i : a) {
            System.out.print(i);
            System.out.print(",");
        }
        System.out.println();
    }

    /*
    * use a bubble sorting, but only the first N iteration
    * */
    private void sort(int[] arr, int n){
        for(int i=1;i<n+1;i++){
            boolean isSorted = true;
            for(int j=0;j<arr.length-1;j++){
                if(arr[j]>arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    isSorted = false;
                }
            }
            if(isSorted){
                break;
            }
            printArry(arr);
        }
    }

    @Test
    public void test(){
        int[] arr = new int[]{3,4,1,10,9,345,2,-1,300,210};
        sort(arr,2);

    }

}
