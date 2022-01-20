/**
 * use only bit-level logic operation to wirte a C expression that is equivalent to x == y
 * This is an execise from CSAPP Chapter 2, P57.
 *
 * Date: 01/17/22
 *
 * */
#include <stdlib.h>
#include <stdio.h>

/*this is my solution*/
bool eq(int x, int y){
	return !(x & ~y);					
}

/*this is from the solution in the book*/
bool eq2(int x, int y){
	return !(x^y);
}
int main(int argc, char** argv){
	int x = 1;
	int y = 0;
	printf("%d\n", eq(x,y));
	y = 1;
	printf("%d\n", eq(x,y));

	printf("%d\n",eq2(x,y));

	return 0;

}
