#include <iostream>
#include <vector>
using namespace std;

/*
 *
 	selection sorting. Pick up a candidate, find the minimum from its right and replace the min with 
  the candidate. The candidate iterates from the first to the last of the array.
	https://mp.weixin.qq.com/s?__biz=MzUyNjQxNjYyMg==&mid=2247485556&idx=1&sn=344738dd74b211e091f8f3477bdf91ee&chksm=fa0e67f5cd79eee3139d4667f3b94fa9618067efc45a797b69b41105a7f313654d0e86949607&scene=21#wechat_redirect
	
	Date: 12/12/21

 * */
void sort(vector<int> &v){
	int len = v.size();
	for(int i=0;i<len;i++){
		int min = i;
		for(int j=i+1;j<len;j++){
			if(v[j]<v[min]){
				min = j;
			}
		}
		int temp = v[i];
		v[i] = v[min];
		v[min] = temp;
	}
}

int main(int argc, char** argv){
	vector<int> v = {8,2,5,9,7};
	sort(v);	
	for(int i=0;i<v.size();i++){
		cout<<v[i]<<",";
	}
	cout<<endl;

	return 0;
}
