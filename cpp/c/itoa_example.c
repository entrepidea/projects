/*
 *Be mindful that itoa is not ANSI standard thus not supported by most c compiler (gcc ,etc) except for (maybe) visual C++
  As a result the code below will NOT be compiled by gcc/Unix. A suggested alternative would be "snprintf"

	see also: 
		https://askubuntu.com/questions/196867/error-linking-standard-header-file-in-gcc - suggest snprintf
		https://stackoverflow.com/questions/12970439/gcc-error-undefined-reference-to-itoa - suggest sprintf

		http://www.strudel.org.uk/itoa/ - this article write serveral versions of a func mimicing itoa 

	Date: 12/10/21		

 *
 * */
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

/*code from https://fresh2refresh.com/c-programming/c-type-casting/c-itoa-function */
int main(int argc, char** argv){
		int a=54325;
    char buffer[20];
    itoa(a,buffer,2);   // here 2 means binary
    printf("Binary value = %s\n", buffer);
 
    itoa(a,buffer,10);   // here 10 means decimal
    printf("Decimal value = %s\n", buffer);
 
    itoa(a,buffer,16);   // here 16 means Hexadecimal
    printf("Hexadecimal value = %s\n", buffer);
    return 0;
}
