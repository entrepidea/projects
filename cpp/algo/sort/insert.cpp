#include <iostream>
#include <vector>
using namespace std;

/**
 * insertion sorting. Break into two sections. The left is the sorted array, while the right not.
 * iterate each element from the right, insert it to the left orderly.
 *
 * https://mp.weixin.qq.com/s?__biz=MzUyNjQxNjYyMg==&mid=2247485556&idx=1&sn=344738dd74b211e091f8f3477bdf91ee&chksm=fa0e67f5cd79eee3139d4667f3b94fa9618067efc45a797b69b41105a7f313654d0e86949607&scene=21#wechat_redirect
 *
 * Date: 12/12/21
 *
 *
 * */
void print(vector<int>&v){
		for(int i=0;i<v.size();i++){
			cout<<v[i]<<",";
		}
		cout<<endl;
}

void sort(vector<int> &v){
	int len = v.size();
	for(int i=1;i<len;i++){
		int p = 0;
		while(p<i){
			if(v[i]<v[p]){
				int tmp = v[i];							
				for(int j=i-1;j>=p;j--){
					v[j+1] = v[j];
				}
				v[p] = tmp;
			}
			p++;
		}
	}
}

int main(int argc, char** argv){
	vector<int> v = {8,2,5,9,7};
	sort(v);
	print(v);
	return 0;
}

