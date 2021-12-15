#include <stdio.h>
/**
 * use bit operation to swap two numbers, w/o using temparory variables.
 * CSAPP, P55.
 *
 * Date:12/12/21
 *
 * */
void inplace_swap(int *x, int *y){
	*y = *x ^ *y;
	printf("%d\n",*y);
	*x = *x ^ *y;
	printf("%d\n",*x);
	*y = *x ^ *y;	
	printf("%d\n",*y);
}
int main(){
	int a = 1, b=2;
	inplace_swap(&a,&b);
	printf("%d,%d\n",a,b);
}
