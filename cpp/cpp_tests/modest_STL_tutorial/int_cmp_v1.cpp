/*
source: http://cs.brown.edu/~jak/proglang/cpp/stltut/tut.html
desc: standard way of writing a int comp algo.
compile: g++ int_cmp_v1.cpp
runtime: type in integers for comparison. To break, type in a char other than integer.
date: 04/28/19

 * */
#include <iostream>

using namespace std;

inline int cmp(const void* a, const void* b){
	int aa = *(int*)a;
	int bb = *(int*)b;
	return (aa<bb)?-1:(aa>bb)?1:0;
}

int main(int argc, char* argv[]){
	const int size = 1000;
	int array[size];
	int n = 0;
	while(cin>>array[n++]);
	n--;
	qsort(array,n,sizeof(int),cmp);
	for(int i=0;i<n;i++){
		cout<<array[i]<<endl;
	}	
}
