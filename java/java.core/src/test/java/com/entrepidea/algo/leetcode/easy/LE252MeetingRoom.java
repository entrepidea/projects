package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;
/**
 * @Desc: find out the least number of needed meeting rooms.
 * @Source: http://www.learn4master.com/interview-questions/leetcode/leetcode-meeting-rooms-ii-java
 * @Note: TODO my solution below is different from the popular solution, anything I missed?
 * @Date: 12/11/19
 * */
public class LE252MeetingRoom {

    private  int availableRooms(int[][] sessions){
        if(sessions==null){
            return -1;
        }
        if(sessions[0].length==1){
            return 1;
        }

        int roomNeeded = sessions.length;
        for(int i=0;i<sessions.length-1;i++){
            for(int j=i+1;j<sessions.length;j++){
                if(sessions[i][0]>=sessions[j][1] || sessions[i][1]<=sessions[j][0]){ //if sessions are not overlapped, one room less.
                    roomNeeded--;
                }
            }
        }
        return roomNeeded;
    }
    @Test
    public void test(){
        Assert.assertEquals(2, availableRooms(new  int[][]{{0, 30},{5, 10},{15, 20}}));
        Assert.assertEquals(1, availableRooms(new  int[][]{{7, 10},{2, 4}}));
    }
}
