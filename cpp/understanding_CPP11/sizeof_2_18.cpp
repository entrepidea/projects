#include <iostream>
using namespace std;

struct People {
	public:
		int hand;
		static People * all;
};

int main(){
	People p;
	cout<< sizeof(p.hand)<< endl;
	cout<< sizeof(People::all) << endl;
	cout<< sizeof(People::hand) << endl; // according to the book this line won't compile under c++98 'cause its non-static variable, c++11 loose this restriction. However it passed anyway from my test - maybe it's c++11 by default.
	return 0;
}



