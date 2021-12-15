#include <stdio.h>

/*
 *The code below is from: http://www.strudel.org.uk/itoa/
  under the line: "Ansi C "itoa" based on Kernighan & Ritchie's "Ansi C""
	however, when tried, it shows: 
 		*** stack smashing detected ***: <unknown> terminated
	
	NOTE: TODO need fix.

	Date: 12/10/21

 * */
void strreverse(char* begin, char* end) {
	
	char aux;
	
	while(end>begin)
	
		aux=*end, *end--=*begin, *begin++=aux;
	
}
	
void itoa(int value, char* str, int base) {
	
	static char num[] = "0123456789abcdefghijklmnopqrstuvwxyz";
	
	char* wstr=str;
	
	int sign;
	


	
	// Validate base
	
	if (base<2 || base>35){ *wstr='\0'; return; }
	

	
	// Take care of sign
	
	if ((sign=value) < 0) value = -value;
	


	
	// Conversion. Number is reversed.
	
	do *wstr++ = num[value%base]; while(value/=base);
	
	if(sign<0) *wstr++='-';
	
	*wstr='\0';
	

	
	// Reverse string

	
	strreverse(str,wstr-1);
	
}

int main(){
	int num = 4;
	char str[2] ;
	itoa(num,str,2);
	printf("%s\n",str);	
}
