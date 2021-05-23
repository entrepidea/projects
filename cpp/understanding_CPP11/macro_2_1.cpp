//check the environmental support for macro
//date: 05/22/21
#include <iostream>
using namespace std;

int main(){
	cout<< "Standard C lib: "<< __STDC_HOSTED__ << endl;
	cout << " Standard C: " << __STDC__ << endl ;
	cout << " ISO / IEC " << __STDC_ISO_10646__ << endl ;
	return 0;
}
