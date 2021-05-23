//preprocessor __VA_ARGS__ can be used to represent vari_length parameter. In this example, it represents ["x=%d", x]
//05/22/21
#include <stdio.h>

#define LOG(...){\
	fprintf(stderr, "%s: line %d:\t", __FILE__, __LINE__); \
	fprintf(stderr, __VA_ARGS__);\
	fprintf(stderr, "\n"); \
}

int main(){
	int x = 3;
	LOG("x=%d",x);
	return 0;
}
