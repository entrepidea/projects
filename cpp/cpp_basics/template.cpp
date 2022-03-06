/**
 * template in C++
 * https://www.youtube.com/watch?v=I-hZkUa9mIs&list=WL&index=2
 *
 * Date: 02/12/22
 *
 * */
#include <iostream>
#include <string>
using namespace std;

template<typename T>
void print(T value){
	cout<<value<<endl;
}

//the template is like a placeholder at compile time and then put real values in at runtime.
template<typename T, int N>
class Array{
	private:
		T buf[N];
	public:
		int get_size() const {return N; }
};


int main(int argc, char** argv){

	print(5);
	print("hello,world!");
	print(5.5f);

	Array<string, 50> array;
	cout<<"the size of an array is: "<<array.get_size()<<" !"<<endl;

	cin.get();
}
