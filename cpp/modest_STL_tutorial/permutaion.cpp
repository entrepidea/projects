#include <vector>
#include <algorithm>
#include <iterator>
#include <iostream>

using namespace std;

int main(int argc, char* argv[]){

	int n = atoi(argv[1]);
	
	vector<int> v;

	for(int i=0;i<n;i++){
		v.push_back(i);
	}	

	random_shuffle(v.begin(),v.end());
	copy(v.begin(),v.end(),ostream_iterator<int>(cout,"\n"));

	return 0;
}
