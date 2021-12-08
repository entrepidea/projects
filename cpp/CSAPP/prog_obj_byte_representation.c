/*
 code to print the byte representation of program objects. This code uses casting to circumvent the type system.
 CSAPP, p45. 
 
 Date: 12/08/21

 * */
#include <stdio.h>
#include <stdint.h>

typedef unsigned char *byte_pointer;

void show_bytes(byte_pointer start, size_t len){
	size_t i;
	for(i=0;i<len;i++){
		printf(" %.2x", start[i]);
	}	
	printf("\n");
}

void show_int(int x){
	show_bytes((byte_pointer)&x,sizeof(int));				
}
void show_float(float x){
	show_bytes((byte_pointer)&x,sizeof(float));				
}
void show_pointer(void* x){
	show_bytes((byte_pointer)&x, sizeof(void *));				
}

int main(int argc, char** argv){
	int ival = 12345;
	float fval = (float)ival;
	int *pval = &ival;	
	show_int(ival);				
	show_float(fval);				
	show_pointer(pval);
}
