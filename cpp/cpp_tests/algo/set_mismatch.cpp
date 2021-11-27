#include <vector>			//std::vector
#include <algorithm> //std::swap
#include <iostream>		//std::cout

/*
 *	You have a set of integers s, which originally contains all the numbers from 1 to n. 
 *	Unfortunately, due to some error, one of the numbers in s got duplicated to another number in the set, 
 *	which results in repetition of one number and loss of another number.
 *	https://leetcode.com/problems/set-mismatch/
 *
 *	NOTE: this is one go cpp solution: https://leetcode.com/problems/set-mismatch/discuss/1588039/c%2B%2B-one-go-answer
 *	NOTE: due to some syntax error, below is not working. Fix it! TODO
 *	NOTE: Still need to figure out the logic.
 *	NOTE: learning point: how to initialize arrays; use of vectors; algo API: swap.
 *
 *	Date: 11/25/21
 *
 * */
using namespace std;

vector<int> mismatch(const vector<int>& nums){
	for(int i=0;i<nums.size();i++){
		while(nums[i] != nums[nums[i]-1]) swap(nums[i], nums[nums[i]-1]);
	}				
	for(int i=0;i<nums.size();i++){
		if(nums[i] != i + 1){
			return {nums[i], i+1};
		}
	}

	return {-1,-1};

}

int main(int argc, char** argv){
	int nums[] = {1,2,2,4};				
	//vector<int> v(nums, nums + sizeof(nums)/sizeof(int));
	//cout<<mismatch(v)<<endl;	
	return 0;
}				
