#include <iostream>
using namespace std;
/*
 *
 *	bubble sorting.
	 	https://mp.weixin.qq.com/s?__biz=MzUyNjQxNjYyMg==&mid=2247485556&idx=1&sn=344738dd74b211e091f8f3477bdf91ee&chksm=fa0e67f5cd79eee3139d4667f3b94fa9618067efc45a797b69b41105a7f313654d0e86949607&scene=21#wechat_redirect

		Note: worst case of scenario, O(n^2)

	 	Date: 12/12/21

 * */

void sort(int nums[],int len){
	for(int i=0;i<len;i++){
		bool isSorted = true;//this is for optimization - if the array is already sorted,no shifting will happen.
		for(int j=0;j<len-1-i;j++){
			if(nums[j]<nums[j+1]){
				int temp = nums[j];
				nums[j] = nums[j+1];
				nums[j+1] = temp;
				isSorted = false;
			}
		}
		if(isSorted){
			break;
		}
	}				
}
int main(int argc, char** argv){
	int nums[] = {8,2,5,9,7};
	int len = sizeof(nums)/sizeof(nums[0]);
	sort(nums,len);	
	for(int i=0;i<len;i++){
		cout<<nums[i]<<",";
	}
	cout<<endl;

	return 0;
}
