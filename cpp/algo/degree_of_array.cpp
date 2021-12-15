/*
	Given a non-empty array of non-negative integers nums, the degree of this array is defined as the maximum frequency of any one of its elements.
	Your task is to find the smallest possible length of a (contiguous) subarray of nums, that has the same degree as nums.

	NOTE: the code below replicate the source code from:
	https://leetcode.com/problems/degree-of-an-array/discuss/1617808/Easy-C%2B%2B-Solution-oror-Runtime-faster-than-97.64-oror-Memory-Usage-less-than-91.21

	Date: 12/11/21

 *
 * */

#include <iostream>
#include <vector>
#include <unordered_map>

using namespace std;

int degree(vector<int>& nums){
	unordered_map<int,int> mp;
	int temp = 0;
	for(auto &i : nums){
		mp[i]++;
		if(mp[i]>temp){
			temp = mp[i];
		}
	}
	if(temp==1){
		return 1;
	}
	int n = nums.size();
	int ans = INT8_MAX;
	for(auto &i : mp){
		if(i.second == temp){ //find the element which has the max occurrences - temp hold the max freq value. 
			int l=0, r=n-1;
			while(l<=r && nums[l] != i.first){
				l++;							
			}
			while(l<=r && nums[r] != i.first){
				r--;
			}
			ans = min(ans, r-l+1);
		}					
	}
	return ans;

};

int main(int argc, char** argv){
	//vector<int> nums{1,2,2,3,1};
	vector<int> nums{1,2,2,3,1,4,2};
	cout<<degree(nums)<<endl;	
	return 0;	
}
