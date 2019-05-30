/*
 *
 *source: http://cs.brown.edu/~jak/proglang/cpp/stltut/tut.html
	note: the code is a little different from that in the article. Old STL code needs to blend in standard C++ library. See "iostream iterators" on p403, C++ primier, 5ed.
	date: 04/28/19
 *
 * */
#include <iostream>
#include <iterator>
#include <vector>
#include <algorithm>


using namespace std;

int main(int argc, char* argv[]){

	istream_iterator<int> start (cin);
	istream_iterator<int> end;
	vector<int> v;
	back_insert_iterator<vector<int> > dest(v);

	copy(start, end, dest);
	sort(v.begin(),v.end());
	copy(v.begin(),v.end(), ostream_iterator<int>(cout,"\n"));	
	return 0;
}
