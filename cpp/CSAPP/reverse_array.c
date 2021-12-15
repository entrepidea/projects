/**
 * problem 2.11, P55. reverse an array.
 * TODO: buggy when the array length is odd. FIX it!
 *
 * Date: 12/12/21
 *
 * */
#include <stdio.h>
void inplace_swap(int *x, int *y){
	*y = *x ^ *y;
	*x = *x ^ *y;
	*y = *x ^ *y;	
}
void reverse_array(int arr[], int cnt){
	int first,last;
	for(first=0,last=cnt-1;first<=last;first++,last--){
		inplace_swap(&arr[first],&arr[last]);
	}	
}

int main(){
	int arr[] = {1,2,3,4,5};
	int cnt = sizeof(arr)/sizeof(arr[0]);
	reverse_array(arr,cnt);
	for(int i=0;i<cnt;i++){
		printf("%d,",arr[i]);
	}
	printf("\n");
	return 0;
}
