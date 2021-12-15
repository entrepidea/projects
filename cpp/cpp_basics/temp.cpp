#include <string.h>
#include <iostream>

using namespace std;
int main(int argc, char** argv){

	char* buffer = new char[8];
	memset(buffer,0,8);
	delete[] buffer;	
	cin.get();
	return 0;

}
