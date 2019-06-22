#include <algorithm>
#include <vector>
#include <iostream>

using namespace std;

int main(int argc, char* argv[]){
	vector<int> v;
	int input;
	while(cin>>input){
		v.push_back(input);
	}

	sort(v.begin(),v.end());

	for (int i=0;i<v.size();i++){
		cout<<v[i]<<endl;
	}

}
