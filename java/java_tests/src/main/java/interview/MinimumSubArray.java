package interview;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @Desc: this is a Goldman interview question.
 * Give an array of integers and a target number, find out the length of the shortest sub array the sum of which is no less than the target number.
 *
 * Created by jonat on 10/25/2019.
 */
public class MinimumSubArray {

    private int subArr(int[] arr, int target){
        //my solution
        Arrays.sort(arr);
        int count = 0;
        int idx = arr.length-1;
        while(idx>=0){
            target -= arr[idx];
            if(target<=0){
                return count+1;
            }
            count++;
            idx--;
        }
        return 0;
    }
    @Test
    public void test(){
        int[] arr = {1,2,3,4};
        Assert.assertEquals(subArr(arr, 6),2);
        Assert.assertEquals(subArr(arr, 5),2);
        Assert.assertEquals(subArr(arr, 4),1);
        Assert.assertEquals(subArr(arr, 10),4);
        Assert.assertEquals(subArr(arr, 9),3);
        Assert.assertEquals(subArr(arr, 8),3);
    }


    //TODO rework needed.
    private int subArr2(int[] arr, int target){
        int minLen = arr.length+1;
        for(int i=0;i<arr.length;i++){
            int remain = target - arr[i];
            if(remain<=0){
                minLen = 1;
                break;
            }
            int sum = 0;
            int len = 1;
            for(int j=0;j<arr.length;j++){
                if(i==j){
                    continue;
                }
                sum += arr[j];
                len++;
                if(sum>=remain){
                    if(len<minLen){
                        minLen = len;
                    }
                    //break;
                }

            }
        }
        return minLen;
    }

    @Test
    public void test2(){
        int[] arr = {1,2,3,4};
        Assert.assertEquals(subArr2(arr, 6),2);
        Assert.assertEquals(subArr2(arr, 5),2);
        Assert.assertEquals(subArr2(arr, 4),1);
        Assert.assertEquals(subArr2(arr, 10),4);
        Assert.assertEquals(subArr2(arr, 9),3);
        Assert.assertEquals(subArr2(arr, 8),3);
    }
}
